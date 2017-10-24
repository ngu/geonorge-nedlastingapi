package no.geonorge.nedlasting.external.fme.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parameter {

    private List<ListOption> listOptions = new ArrayList<>();
    private String defaultValue;
    private String name;
    private String description;
    private String model;
    private String type;

    public List<ListOption> getListOptions() {
        return Collections.unmodifiableList(listOptions);
    }

    public void setListOptions(List<ListOption> listOptions) {
        this.listOptions.clear();
        this.listOptions.addAll(listOptions);
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
