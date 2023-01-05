package ir.peeco.pline.pline.fileGenratorTools;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileGenrator {

  private PjsipFileGenerator pjsipFileGenerator;
  private AclGenerator aclGenerator;

  @Autowired
  public void setpjsipFileGenerator(PjsipFileGenerator pjsipFileGenerator) {
    this.pjsipFileGenerator = pjsipFileGenerator;
  }

  @Autowired
  public void setAclGenerator(AclGenerator aclGenerator) {
    this.aclGenerator = aclGenerator;
  }

  public void GratePjsip() {
    aclGenerator.run();
    pjsipFileGenerator.run();
  }
}
