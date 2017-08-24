package no.geonorge.nedlasting.data.client;

import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

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
    
    public static Projection create(int srid) {
        Projection p = new Projection();
        p.setCode(Integer.toString(srid));
        p.setCodespace("http://www.opengis.net/def/crs/EPSG/0/" + srid);
        
        try {
            CoordinateReferenceSystem crs = CRS.decode("EPSG:" + srid);
            if (crs != null) {
                p.setName(crs.getName().toString());
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }        

        return p;
    }

}
