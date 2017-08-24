package no.geonorge.nedlasting.data.client;

public class Projection {

    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Projection)) {
            return false;
        }
        if (code == null) {
            return false;
        }
        return code.equals(((Projection) obj).code);
    }

    @Override
    public int hashCode() {
        if (code == null) {
            return -1;
        }
        return code.hashCode();
    }

}
