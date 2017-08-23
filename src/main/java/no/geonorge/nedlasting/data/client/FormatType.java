package no.geonorge.nedlasting.data.client;

public class FormatType {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FormatType)) {
            return false;
        }
        if (name == null) {
            return false;
        }
        return name.equals(((FormatType) obj).name);
    }

    @Override
    public int hashCode() {
        if (name == null) {
            return -1;
        }
        return name.hashCode();
    }

}
