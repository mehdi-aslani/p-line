package ir.peeco.pline.pline.fileGenratorTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ir.peeco.pline.models.TblSipGlobals;
import ir.peeco.pline.models.TblSipSystems;
import ir.peeco.pline.pline.InfoConfiguration;
import ir.peeco.pline.pline.PlineTools;
import ir.peeco.pline.repositories.SipGlobalsRepository;
import ir.peeco.pline.repositories.SipProfileDetailsRepository;
import ir.peeco.pline.repositories.SipProfilesRepository;
import ir.peeco.pline.repositories.SipSystemsRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PjsipFileGenrator {

  private PlineTools plineTools;
  private SipProfileDetailsRepository sipProfileDetailsRepository;
  private SipProfilesRepository profilesRepository;
  private SipGlobalsRepository sipGlobalsRepository;
  private SipSystemsRepository sipSystemsRepository;

  @Autowired
  public void setSipSystemsRepository(SipSystemsRepository sipSystemsRepository) {
    this.sipSystemsRepository = sipSystemsRepository;
  }

  @Autowired
  public void setPlineTools(PlineTools plineTools) {
    this.plineTools = plineTools;
  }

  @Autowired
  public void setSipProfileDetailsRepository(SipProfileDetailsRepository sipProfileDetailsRepository) {
    this.sipProfileDetailsRepository = sipProfileDetailsRepository;
  }

  @Autowired
  public void setProfilesRepository(SipProfilesRepository profilesRepository) {
    this.profilesRepository = profilesRepository;
  }

  @Autowired
  public void setSipGlobalsRepository(SipGlobalsRepository sipGlobalsRepository) {
    this.sipGlobalsRepository = sipGlobalsRepository;
  }

  public void run() {
    this.createGlobalSipSettings();
    this.createSipProfile();
    plineTools.reloadConfigurations();
  }

  private void createGlobalSipSettings() {
    List<InfoConfiguration> pjsip = new ArrayList<>();

    Optional<TblSipGlobals> sipGlobals = sipGlobalsRepository.findById(1L);
    TblSipGlobals globals;
    if (sipGlobals.isEmpty()) {
      globals = new TblSipGlobals();
    } else {
      globals = sipGlobals.get();
    }

    plineTools.plineLogger("Pjsip: Start create global context");
    InfoConfiguration ic = new InfoConfiguration("global");
    ic.setBanner(true);
    ic.addElement("contact_expiration_check_interval", globals.getContactExpirationCheckInterval());
    ic.addElement("debug", globals.isDebug());
    ic.addElement("default_from_user", globals.getDefaultFromUser());
    ic.addElement("default_outbound_endpoint", globals.getDefaultOutboundEndpoint());
    ic.addElement("default_realm", globals.getDefaultRealm());
    ic.addElement("default_voicemail_extension", globals.getDefaultVoicemailExtension());
    ic.addElement("disable_multi_domain", globals.isDisableMultiDomain());
    ic.addElement("endpoint_identifier_order", globals.getEndpointIdentifierOrder());
    ic.addElement("ignore_uri_user_options", globals.isIgnoreUriUserOptions());
    ic.addElement("keep_alive_interval", globals.getKeepAliveInterval());
    ic.addElement("max_forwards", globals.getMaxForwards());
    ic.addElement("max_initial_qualify_time", globals.getMaxInitialQualifyTime());
    ic.addElement("mwi_disable_initial_unsolicited", globals.isMwiDisableInitialUnsolicited());
    ic.addElement("mwi_tps_queue_high", globals.getMwiTpsQueueHigh());
    ic.addElement("mwi_tps_queue_low", globals.getMwiTpsQueueLow());
    ic.addElement("norefersub", globals.isNoReferSub());
    ic.addElement("regcontext", globals.getRegcontext());
    ic.addElement("send_contact_status_on_update_registration",
        globals.isSendContactStatusOnUpdateRegistration());
    ic.addElement("taskprocessor_overload_trigger", globals.getTaskProcessorOverloadTrigger());
    ic.addElement("unidentified_request_count", globals.getUnidentifiedRequestCount());
    ic.addElement("unidentified_request_period", globals.getUnidentifiedRequestPeriod());
    ic.addElement("unidentified_request_prune_interval", globals.getUnidentifiedRequestPruneInterval());
    ic.addElement("use_callerid_contact", globals.isUseCalleridContact());
    ic.addElement("user_agent", globals.getUserAgent());
    pjsip.add(ic);
    plineTools.plineLogger("Pjsip: Finish create global context");

    Optional<TblSipSystems> sipSetting = sipSystemsRepository.findById(1L);
    TblSipSystems systems;
    if (sipGlobals.isEmpty()) {
      systems = new TblSipSystems();
    } else {
      systems = sipSetting.get();
    }

    plineTools.plineLogger("Pjsip: Start create system context");
    InfoConfiguration icsetting = new InfoConfiguration("system");
    icsetting.addElement("accept_multiple_sdp_answers", systems.isAcceptMultipleSdpAnswers());
    icsetting.addElement("compact_headers", systems.isCompactHeaders());
    icsetting.addElement("disable_rport", systems.isDisableRport());
    icsetting.addElement("disable_tcp_switch", systems.isDisableTcpSwitch());
    icsetting.addElement("follow_early_media_fork", systems.isFollowEarlyMediaFork());
    icsetting.addElement("threadpool_auto_increment", systems.getThreadPoolAutoIncrement());
    icsetting.addElement("threadpool_idle_timeout", systems.getThreadPoolIdleTimeout());
    icsetting.addElement("threadpool_initial_size", systems.getThreadPoolInitialSize());
    icsetting.addElement("threadpool_max_size", systems.getThreadPoolMaxSize());
    icsetting.addElement("timer_b", systems.getTimerB());
    icsetting.addElement("timer_t1", systems.getTimerT1());
    pjsip.add(icsetting);
    plineTools.plineLogger("Pjsip: Finish create system context");
    plineTools.plineLogger("Pjsip: write in file -> " +
        plineTools.writeinfoFile("", "pjsip", pjsip));
  }

  private void createSipProfile() {
    String[] elementPjSip = new String[] { "transport", "endpoint", "auth", "contact", "aor" };
    String folder = "sip-profiles/";
    String path = plineTools.getConfigPath() + folder;

    if (new File(path).exists()) {
      plineTools.deleteAllFileInFolder(path);
    }

    profilesRepository.findAll().forEach(p -> {
      ArrayList<InfoConfiguration> infoConfigurations = new ArrayList<>();

      for (String pjSip : elementPjSip) {

        InfoConfiguration configuration = new InfoConfiguration(pjSip + "-" + p.id);
        configuration.setBanner(true);

        if (pjSip == "transport") {
          configuration.setDescription(
              "Profile Name: " + p.name + "\n" + "Profile Description: " + p.description);
        } else {
          configuration.setTemplate("!");
        }

        if (pjSip == "endpoint") {
          configuration.addElement("disallow", "all");
        }

        sipProfileDetailsRepository.getBySipProfileIdAndType(p.id, pjSip).forEach(d -> {
          String value = d.value;
          if (value.startsWith("[") && value.endsWith("]")) {
            value = value.replace("[", "").replace("]", "").replace("\"", "").replace(" ", "");
          }
          configuration.addElement(d.key, value);
        });
        infoConfigurations.add(configuration);

      }

      plineTools.writeinfoFile("sip-profiles", "sip-profile-" + p.id, infoConfigurations);
    });
  }

}
