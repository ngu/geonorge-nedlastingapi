package no.geonorge.nedlasting.external.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pojo for Codelist Register objects
 */
public class RegisterItem {

    private String id;
    private String label;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
