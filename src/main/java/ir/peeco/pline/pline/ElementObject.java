package ir.peeco.pline.pline;

import lombok.Data;

@Data
public class ElementObject {
  private String key;
  private Object value;

  public ElementObject(String key, Object value) {
    this.key = key;
    this.value = value;
  }
}
