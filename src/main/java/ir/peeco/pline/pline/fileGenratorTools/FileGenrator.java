package ir.peeco.pline.pline.fileGenratorTools;

import org.springframework.beans.factory.annotation.Autowired;
import ir.peeco.pline.pline.PlineTools;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileGenrator {

  private PlineTools plineTools;
  private PjsipFileGenrator fileGenrator;

  @Autowired
  public void setFileGenrator(PjsipFileGenrator fileGenrator) {
    this.fileGenrator = fileGenrator;
  }

  @Autowired
  public void setPlineTools(PlineTools plineTools) {
    this.plineTools = plineTools;
  }

  public void GratePjsip() {
    fileGenrator.run();
  }
}
