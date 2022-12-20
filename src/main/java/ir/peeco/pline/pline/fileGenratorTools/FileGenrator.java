package ir.peeco.pline.pline.fileGenratorTools;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileGenrator {

  private PjsipFileGenrator fileGenrator;

  @Autowired
  public void setFileGenrator(PjsipFileGenrator fileGenrator) {
    this.fileGenrator = fileGenrator;
  }

  public void GratePjsip() {
    fileGenrator.run();
  }
}
