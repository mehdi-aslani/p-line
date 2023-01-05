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
import ir.peeco.pline.repositories.SipTrunksRepository;
import ir.peeco.pline.repositories.SipUsersRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PjsipFileGenerator {

  private PlineTools plineTools;
  private SipProfileDetailsRepository sipProfileDetailsRepository;
  private SipProfilesRepository sipProfilesRepository;
  private SipGlobalsRepository sipGlobalsRepository;
  private SipSystemsRepository sipSystemsRepository;
  private SipTrunksRepository sipTrunkRepository;
  private SipUsersRepository sipUsersRepository;

  @Autowired
  public void setSipUsersRepository(SipUsersRepository sipUsersRepository) {
    this.sipUsersRepository = sipUsersRepository;
  }

  @Autowired
  public void setSipTrunkRepository(SipTrunksRepository sipTrunkRepository) {
    this.sipTrunkRepository = sipTrunkRepository;
  }

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
  public void setSipProfilesRepository(SipProfilesRepository sipProfilesRepository) {
    this.sipProfilesRepository = sipProfilesRepository;
  }

  @Autowired
  public void setSipGlobalsRepository(SipGlobalsRepository sipGlobalsRepository) {
    this.sipGlobalsRepository = sipGlobalsRepository;
  }

  public void run() {
    this.createGlobalSipSettings();
    this.createSipProfile();
    this.createPjSip();
    this.createPjSipTrunks();
    this.createPjsipUsers();
    plineTools.reloadPjsip();
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

    sipProfilesRepository.findAll().forEach(p -> {
      ArrayList<InfoConfiguration> infoConfigurations = new ArrayList<>();

      for (String pjSip : elementPjSip) {

        InfoConfiguration configuration = new InfoConfiguration(pjSip + "-" + p.getId());
        configuration.setBanner(true);

        if (pjSip == "transport") {
          configuration.setDescription(
              "Profile Name: " + p.getName() + "\n" + "Profile Description: " + p.getDescription());
          configuration.addElement("type", "transport");
          // configuration.setTemplate("!");

        } else {
          configuration.setTemplate("!");
        }

        if (pjSip == "endpoint") {
          configuration.addElement("disallow", "all");
        }

        sipProfileDetailsRepository.getBySipProfileIdAndType(p.getId(), pjSip).forEach(d -> {
          String value = d.value;
          if (value.startsWith("[") && value.endsWith("]")) {
            value = value.replace("[", "").replace("]", "").replace("\"", "").replace(" ", "");
          }
          configuration.addElement(d.key, value);
        });
        infoConfigurations.add(configuration);

      }

      plineTools.writeinfoFile("sip-profiles", "sip-profile-" + p.getId(), infoConfigurations);
    });
  }

  private void createPjSipTrunks() {

    var listTrunks = sipTrunkRepository.findAllEnableProfiles(true);
    String path = plineTools.getConfigPath() + "pjsip-trunks/";
    if (new File(path).exists()) {
      plineTools.deleteAllFileInFolder(path);
    }

    listTrunks.forEach(x -> {

      List<InfoConfiguration> ics = new ArrayList<>();
      switch (x.getRegisterMode()) {
        case Register: {
          InfoConfiguration auth = new InfoConfiguration("auth-" + x.getUsername());
          auth.setBanner(true);
          auth.setDescription("Trunk Name: " + x.getName() + "\n" + "Profile Description: " + x.getDescription());
          auth.setTemplate("auth-" + x.getSipProfile().getId());
          auth.addElement("type", "auth");
          auth.addElement("auth_type", "userpass");
          auth.addElement("username", x.getUsername());
          auth.addElement("password", x.getPassword());
          ics.add(auth);
          /******************************************************* */
          InfoConfiguration aor = new InfoConfiguration(x.getUsername());
          aor.setTemplate("aor-" + x.getSipProfile().getId());
          aor.addElement("type", "aor");
          aor.addElement("contact", "sip:" + x.getProxy());
          ics.add(aor);
          /******************************************************* */
          InfoConfiguration endpoint = new InfoConfiguration(x.getUsername());
          endpoint.setTemplate("endpoint-" + x.getSipProfile().getId());
          endpoint.addElement("type", "endpoint");
          endpoint.addElement("context", "pjsip-trunk-" + x.getId());
          endpoint.addElement("aors", x.getUsername());
          endpoint.addElement("outbound_auth", "auth-" + x.getUsername());
          endpoint.addElement("transport", "transport-" + x.getSipProfile().getId());

          // if (!x.getFromUser().trim().isEmpty())
          endpoint.addElement("from_user", x.getFromUser());

          // if (!x.getFromDomain().trim().isEmpty())
          endpoint.addElement("from_domain", x.getFromDomain());

          if (!x.getAcl().trim().isEmpty()) {
            endpoint.addElement("acl", "acl-sip-trunk" + x.getId());
          }
          ics.add(endpoint);
          /******************************************************* */
          InfoConfiguration registeration = new InfoConfiguration(x.getUsername());
          registeration.addElement("type", "registration");
          registeration.addElement("outbound_auth", "auth-" + x.getUsername());
          registeration.addElement("server_uri", "sip:" + x.getProxy());
          registeration.addElement("client_uri", "sip:" + x.getUsername() + "@" + x.getProxy());
          registeration.addElement("retry_interval", 60);
          ics.add(registeration);
          /******************************************************* */
          InfoConfiguration identify = new InfoConfiguration(x.getUsername());
          identify.addElement("type", "identify");
          identify.addElement("match", x.getProxy());
          identify.addElement("endpoint", x.getUsername());
          ics.add(identify);
        }
          break;

        case Registrable: {
          InfoConfiguration auth = new InfoConfiguration("auth" + x.getUsername());
          auth.setBanner(true);
          auth.setDescription("Trunk Name: " + x.getName() + "\n" + "Profile Description: " + x.getDescription());
          auth.setTemplate("auth-" + x.getSipProfile().getId());
          auth.addElement("type", "auth");
          auth.addElement("auth_type", "userpass");
          auth.addElement("username", x.getUsername());
          auth.addElement("password", x.getPassword());
          ics.add(auth);
          /******************************************************* */
          InfoConfiguration aor = new InfoConfiguration(x.getUsername());
          aor.setTemplate("aor-" + x.getSipProfile().getId());
          aor.addElement("type", "aor");
          aor.addElement("max_contacts", 1);
          aor.addElement("remove_existing", "yes");
          ics.add(aor);
          /******************************************************* */
          InfoConfiguration endpoint = new InfoConfiguration(x.getUsername());
          endpoint.setTemplate("endpoint-" + x.getSipProfile().getId());
          endpoint.addElement("type", "endpoint");
          endpoint.addElement("context", "pjsip-trunk-" + x.getId());
          endpoint.addElement("rewrite_contact", "yes");
          endpoint.addElement("aors", x.getUsername());
          endpoint.addElement("auth", "auth" + x.getUsername());
          endpoint.addElement("transport", "transport-" + x.getSipProfile().getId());
          if (!x.getAcl().trim().isEmpty()) {
            endpoint.addElement("acl", "acl-sip-trunk" + x.getId());
          }
          ics.add(endpoint);

        }
          break;

        case NoRegister: {
          String user = "trunk" + x.getId();
          InfoConfiguration aor = new InfoConfiguration(user);
          aor.setBanner(true);
          aor.setDescription("Trunk Name: " + x.getName() + "\n" + "Profile Description: " + x.getDescription());
          aor.setTemplate("aor-" + x.getSipProfile().getId());
          aor.addElement("type", "aor");
          aor.addElement("contact", "sip:" + x.getProxy());
          ics.add(aor);
          /******************************************************* */
          InfoConfiguration endpoint = new InfoConfiguration(user);
          endpoint.setTemplate("endpoint-" + x.getSipProfile().getId());
          endpoint.addElement("type", "endpoint");
          endpoint.addElement("transport", "transport-" + x.getSipProfile().getId());
          endpoint.addElement("context", "pjsip-trunk-" + x.getId());
          endpoint.addElement("aors", user);
          endpoint.addElement("from_user", x.getFromUser());
          endpoint.addElement("from_domain", x.getFromDomain());
          if (!x.getAcl().trim().isEmpty()) {
            endpoint.addElement("acl", "acl-sip-trunk" + x.getId());
          }
          ics.add(endpoint);
          /******************************************************* */
          InfoConfiguration identify = new InfoConfiguration(user);
          identify.addElement("type", "identify");
          identify.addElement("match", x.getProxy());
          identify.addElement("endpoint", user);
          ics.add(identify);
        }
          break;

        default:
          plineTools.plineLogger("Register Mode value not valid.");
          break;
      }
      plineTools.writeinfoFile("pjsip-trunks", "psjsip-trunk-" + x.getId(), ics);
    });
  }

  private void createPjSip() {

    List<String> configurations = new ArrayList<>();
    if (sipProfileDetailsRepository.count() > 0)
      configurations.add("sip-profiles/*.conf");
    if (sipTrunkRepository.count() > 0)
      configurations.add("pjsip-trunks/*.conf");
    if (sipUsersRepository.count() > 0)
      configurations.add("pjsip-users/*.conf");
    plineTools.IncludeConfigFile("", "pjsip", configurations);
  }

  private void createPjsipUsers() {

    var listExtensions = sipUsersRepository.findAllEnableProfiles(true);
    String path = plineTools.getConfigPath() + "pjsip-users/";
    if (new File(path).exists()) {
      plineTools.deleteAllFileInFolder(path);
    }

    listExtensions.forEach(x -> {
      List<InfoConfiguration> ics = new ArrayList<>();
      InfoConfiguration auth = new InfoConfiguration("auth" + x.getUid());
      auth.setBanner(true);
      auth.setTemplate("auth-" + x.getSipProfile().getId());
      auth.addElement("type", "auth");
      auth.addElement("auth_type", "userpass");
      auth.addElement("username", x.getUid());
      auth.addElement("password", x.getPassword());
      ics.add(auth);
      /******************************************************* */
      InfoConfiguration aor = new InfoConfiguration(x.getUid());
      aor.setTemplate("aor-" + x.getSipProfile().getId());
      aor.addElement("type", "aor");
      aor.addElement("max_contacts", 1);
      aor.addElement("remove_existing", "yes");
      ics.add(aor);
      /******************************************************* */
      InfoConfiguration endpoint = new InfoConfiguration(x.getUid());
      endpoint.setTemplate("endpoint-" + x.getSipProfile().getId());
      endpoint.addElement("type", "endpoint");
      endpoint.addElement("context", "sip-user-" + x.getUid());
      endpoint.addElement("rewrite_contact", "yes");
      endpoint.addElement("aors", x.getUid());
      endpoint.addElement("auth", "auth" + x.getUid());
      endpoint.addElement("transport", "transport-" + x.getSipProfile().getId());
      if (!x.getAcl().trim().isEmpty()) {
        endpoint.addElement("acl", "acl-sip-user" + x.getId());
      }
      ics.add(endpoint);
      plineTools.writeinfoFile("pjsip-users", x.getUid(), ics);
    });

  }
}
