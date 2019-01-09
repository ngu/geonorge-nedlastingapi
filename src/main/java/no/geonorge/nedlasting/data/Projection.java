package no.geonorge.nedlasting.data;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;

import no.geonorge.nedlasting.data.auto._Projection;

public class Projection extends _Projection {
    
    public no.geonorge.nedlasting.data.client.Projection forClient() {
        no.geonorge.nedlasting.data.client.Projection p = new no.geonorge.nedlasting.data.client.Projection();
        p.setCode(getSrid().toString());
        p.setCodespace(getCodespace());
        p.setName(getName());
        return p;
    }
    
    public String getAuthorityAndCode() {
        return "EPSG:" + getSrid();
    }
    
    public String getScheme() {
        return "http://www.opengis.net/def/crs/";
    }
    
    public static Projection getForSrid(ObjectContext ctxt, int srid) {
        return Cayenne.objectForPK(ctxt, Projection.class, srid);
    }

}
