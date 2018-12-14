package no.geonorge.nedlasting.data.client;

import no.geonorge.nedlasting.utils.CompareUtils;

public class Format {

    private String name;

    private String version;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getNameAndVersion() {
        if (getName() == null) {
            return null;
        }
        StringBuilder format = new StringBuilder(getName());
        if (getVersion() != null) {
            format.append(' ');
            format.append(getVersion());
        }
        return format.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Format)) {
            return false;
        }
        if (name == null) {
            return false;
        }
        Format o = (Format) obj;
        return CompareUtils.safeEquals(name, o.name, false) && CompareUtils.safeEquals(version, o.version, true);
    }

    @Override
    public int hashCode() {
        if (name == null) {
            return -1;
        }
        return name.hashCode();
    }

}
