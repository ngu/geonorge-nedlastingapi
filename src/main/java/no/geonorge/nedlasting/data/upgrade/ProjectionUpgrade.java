package no.geonorge.nedlasting.data.upgrade;

import org.apache.cayenne.ObjectContext;

import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.Projection;

public class ProjectionUpgrade extends EntityUpgrade {

    @Override
    public void upgrade() {
        ObjectContext ctxt = Config.getObjectContext();
        addProjectionIfMissing(ctxt, 25833, "http://www.opengis.net/def/crs/EPSG/0/25833", "EUREF89 UTM sone 33, 2d");
        addProjectionIfMissing(ctxt, 32632, "http://www.opengis.net/def/crs/EPSG/0/32632",
                "EPSG:WGS 84 / UTM zone 32N");
        ctxt.commitChanges();
    }

    private void addProjectionIfMissing(ObjectContext ctxt, int srid, String codespace, String name) {
        Projection p = Projection.getForSrid(ctxt, srid);
        if (p != null) {
            return;
        }
        p = ctxt.newObject(Projection.class);
        p.setSrid(srid);
        p.setCodespace(codespace);
        p.setName(name);
    }

}
