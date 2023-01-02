package ir.peeco.pline.pline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.peeco.pline.tools.ElementObject;
import lombok.Data;

@Data
public class InfoConfiguration {

    private List<String> templates = new ArrayList<>();
    private final String context;
    private List<ElementObject> elements;
    private boolean banner = false;
    private String description = "";

    public void addElement(String key, Object value) {
        ElementObject e;
        if (value instanceof Boolean) {
            e = new ElementObject(key, (boolean) value ? "yes" : "no");
        } else {
            e = new ElementObject(key, value);
        }
        elements.add(e);
    }

    public void setTemplate(String template) {
        if (!templates.isEmpty())
            this.templates.clear();
        this.templates.add(template);
    }

    public String getTemplate() {
        if (templates.isEmpty())
            return "";
        return templates.get(0);
    }

    public void addTemplate(String template) {
        this.templates.add(template);
    }

    public InfoConfiguration(String context) {
        this.context = context;
        this.elements = new ArrayList<>();
    }

    public InfoConfiguration() {
        this.context = null;
        this.elements = new ArrayList<>();
    }

}
