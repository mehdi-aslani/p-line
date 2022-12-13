package ir.peeco.pline.pline;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

@Data
public class InfoConfiguration {

    private String template = "";
    private final String context;
    private Map<String, Object> elements;
    private boolean banner = false;
    private String description = "";

    public void addElement(String key, Object value) {
        if (value instanceof Boolean)
            elements.put(key, (boolean) value ? "yes" : "no");
        else
            elements.put(key, value);
    }

    public InfoConfiguration(String context) {
        this.context = context;
        this.elements = new LinkedHashMap<>();
    }

}
