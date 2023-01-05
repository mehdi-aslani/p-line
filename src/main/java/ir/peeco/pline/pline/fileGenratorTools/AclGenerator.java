package ir.peeco.pline.pline.fileGenratorTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ir.peeco.pline.pline.InfoConfiguration;
import ir.peeco.pline.pline.PlineTools;
import ir.peeco.pline.repositories.SipTrunksRepository;
import ir.peeco.pline.repositories.SipUsersRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AclGenerator {

  private PlineTools plineTools;
  private SipTrunksRepository sipTrunksRepository;
  private SipUsersRepository sipUsersRepository;

  @Autowired
  public void setSipUsersRepository(SipUsersRepository sipUsersRepository) {
    this.sipUsersRepository = sipUsersRepository;
  }

  @Autowired
  public void setSipTrunksRepository(SipTrunksRepository sipTrunksRepository) {
    this.sipTrunksRepository = sipTrunksRepository;
  }

  @Autowired
  public void setPlineTools(PlineTools plineTools) {
    this.plineTools = plineTools;
  }

  public void run() {
    String path = plineTools.getConfigPath() + "acl/";
    if (new File(path).exists()) {
      plineTools.deleteAllFileInFolder(path);
    }
    sipTrunkAclGenrate();
    sipUserAclGenrate();
    createAcl();
    plineTools.reloadAcl();
  }

  private void sipTrunkAclGenrate() {
    var listTrunks = sipTrunksRepository.findAllEnableProfiles(true);
    List<InfoConfiguration> ics = new ArrayList<>();

    listTrunks.forEach(x -> {
      if (x.getAcl().trim().isEmpty())
        return;
      var aclName = "acl-sip-trunk" + x.getId();
      String[] aclList = x.getAcl().split("\n|,");
      if (aclList.length > 0) {
        InfoConfiguration acl = new InfoConfiguration(aclName);
        acl.addElement("deny", "0.0.0.0/0");
        for (String item : aclList) {
          acl.addElement("permit", item);
        }
        if (ics.isEmpty())
          acl.setBanner(true);
        acl.setDescription("SIP trunk: " + x.getName());
        ics.add(acl);
      }
    });

    if (!ics.isEmpty()) {
      plineTools.writeinfoFile("acl", "psjsip-trunk-acl", ics);
    }

  }

  /********************************************************************************************** */
  private void sipUserAclGenrate() {
    var listUsers = sipUsersRepository.findAllEnableProfiles(true);
    List<InfoConfiguration> ics = new ArrayList<>();

    listUsers.forEach(x -> {
      var aclName = "acl-sip-user" + x.getId();
      String[] aclList = x.getAcl().split("\n|,");
      if (aclList.length > 0) {
        InfoConfiguration acl = new InfoConfiguration(aclName);
        acl.addElement("deny", "0.0.0.0/0");
        for (String item : aclList) {
          acl.addElement("permit", item);
        }
        if (ics.isEmpty())
          acl.setBanner(true);
        acl.setDescription("SIP User: " + x.getUid());
        ics.add(acl);
      }
    });

    if (!ics.isEmpty()) {
      plineTools.writeinfoFile("acl", "acl-sip-user-acl", ics);
    }

  }

  private void createAcl() {
    InfoConfiguration info = new InfoConfiguration(null);
    info.setBanner(true);
    String path = plineTools.getConfigPath() + "acl/";
    if ((new File(path)).listFiles().length > 0)
      info.addElement("include", "acl/*.conf");
    plineTools.writeinfoFile("", "acl", info);
  }

}
