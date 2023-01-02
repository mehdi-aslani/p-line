package ir.peeco.pline.coreController;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class ExtensionCall extends BaseAgiScript {

  @Override
  public void service(AgiRequest request, AgiChannel channel) throws AgiException {
    request.getRequest().forEach((x, y) -> {
      System.out.println(x + "->" + y);
    });

    try {
      channel.answer();
      channel.playMusicOnHold();
      // channel.sayDigits("12345678");
      channel.waitForDigit(100000);
      channel.hangup();
    } catch (Exception ex) {
    }
  }

}
