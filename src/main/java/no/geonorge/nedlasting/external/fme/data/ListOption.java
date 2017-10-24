package no.geonorge.nedlasting.external.fme.data;

public class ListOption {

    private String caption;
    private String value;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return getCaption().replace("(", "- ").replace(")", "");
    }

}
