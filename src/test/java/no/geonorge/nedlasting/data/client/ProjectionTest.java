package no.geonorge.nedlasting.data.client;

import junit.framework.TestCase;

public class ProjectionTest extends TestCase {

    public void testCreate() {
        Projection p1 = Projection.create(25833);
        assertEquals("25833", p1.getCode());
        assertEquals("http://www.opengis.net/def/crs/EPSG/0/25833", p1.getCodespace());
        // assertEquals("EUREF89 UTM sone 33, 2d", p1.getName());
        assertEquals("EPSG:ETRS89 / UTM zone 33N", p1.getName());
    }

}
