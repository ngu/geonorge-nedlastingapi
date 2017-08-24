package no.geonorge.nedlasting.data.client;

public class Projection {

    private String code;
    private String name;
    private String codespace;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodespace() {
        return codespace;
    }

    public void setCodespace(String codespace) {
        this.codespace = codespace;
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
