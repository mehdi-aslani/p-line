package ir.peeco.pline.pline;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ApiResult {
  private boolean hasError;
  private List<String> messages = new ArrayList<>();

  public void addMessage(String message) {
    this.messages.add(message);
  }
}
