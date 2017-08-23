package no.geonorge.nedlasting.data;

import no.geonorge.nedlasting.data.auto._DatasetFile;
import no.geonorge.nedlasting.data.client.FormatType;
import no.geonorge.nedlasting.data.client.Projection;

public class DatasetFile extends _DatasetFile {

    public Projection getProjection() {
        Projection projectonType = new Projection();
        projectonType.setCode(Integer.toString(getSrid()));
        return projectonType;
    }

    public FormatType getFormatType() {
        FormatType formatType = new FormatType();
        formatType.setName(getFormat());
        return formatType;
    }

    String getAreaKey() {
        return getAreaType() + "-" + getAreaCode();
    }

    public void setArea(String type, String code, String name) {
        setAreaType(type);
        setAreaCode(code);
        setAreaName(name);
    }

}
