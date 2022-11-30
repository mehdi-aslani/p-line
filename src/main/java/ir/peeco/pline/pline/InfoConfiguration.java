package ir.peeco.pline.pline;

import java.util.LinkedHashMap;
import java.util.Map;

public class InfoConfiguration {

    private String template = "";
    private final String context;
    private Map<String, Object> elements;
    private boolean banner = false;
    private String description = "";

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setBanner(boolean banner) {
        this.banner = banner;
    }

    public boolean getBanner() {
        return banner;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public String getContext() {
        return context;
    }

    public Map<String, Object> getElements() {
        return elements;
    }

    public void setElements(Map<String, Object> elements) {
        this.elements = elements;
    }

    public void addElement(String key, Object value) {
        elements.put(key, value);
    }

    public InfoConfiguration(String context) {
        this.context = context;
        this.elements = new LinkedHashMap<>();
    }

}
