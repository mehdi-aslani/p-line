package ir.peeco.pline.pline.fileGenratorTools;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileGenrator {

  private PjsipFileGenrator pjsipFileGenrator;
  private AclGenerator aclGenerator;

  @Autowired
  public void setPjsipFileGenrator(PjsipFileGenrator pjsipFileGenrator) {
    this.pjsipFileGenrator = pjsipFileGenrator;
  }

  @Autowired
  public void setAclGenerator(AclGenerator aclGenerator) {
    this.aclGenerator = aclGenerator;
  }

  public void GratePjsip() {
    aclGenerator.run();
    pjsipFileGenrator.run();
  }
}
