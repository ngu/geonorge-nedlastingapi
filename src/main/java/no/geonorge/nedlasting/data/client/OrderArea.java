package no.geonorge.nedlasting.data.client;

import no.geonorge.nedlasting.utils.CompareUtils;

public class OrderArea {

    private String code;
    private String type;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OrderArea)) {
            return false;
        }
        OrderArea o = (OrderArea) obj;
        return CompareUtils.safeEquals(type, o.type, false) && CompareUtils.safeEquals(code, o.code, false)
                && CompareUtils.safeEquals(name, o.name, false);
    }

}
