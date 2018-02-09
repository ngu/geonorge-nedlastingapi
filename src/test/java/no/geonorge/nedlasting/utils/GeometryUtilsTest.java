package no.geonorge.nedlasting.utils;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKTReader;

import junit.framework.TestCase;

public class GeometryUtilsTest extends TestCase {

    public void testArea() throws Exception {
        WKTReader w = new WKTReader();

        // drawn with geojson.io roughly around Jostedalsbreen and exported to
        // WKT. decode and calculate area.
        Geometry geom = w.read(
                "POLYGON ((6.75933837890625 61.65989901616311, 6.5972900390625 61.50697577853939, 6.76483154296875 61.43745433152202, 7.0257568359375 61.54887590143483, 7.58880615234375 61.82763395319929, 7.374572753906249 61.92214947755915, 6.913146972656249 61.753631169518556, 6.75933837890625 61.65989901616311))");
        double area4326 = GeometryUtils.calculateAreaSquareKilometer(geom, 4326);
        assertEquals(1072, area4326, 1.0);

        // convert to UTM and calculate area
        CoordinateReferenceSystem ll = CRS.decode("EPSG:4326", true);
        CoordinateReferenceSystem utm = CRS.decode("EPSG:32633");
        MathTransform transform = CRS.findMathTransform(ll, utm);
        Geometry geomutm = JTS.transform(geom, transform);
        double areautm = GeometryUtils.calculateAreaSquareKilometer(geomutm, 32633);
        assertEquals(area4326, areautm, 1.0);

    }

}
