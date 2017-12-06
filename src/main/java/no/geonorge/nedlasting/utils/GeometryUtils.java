package no.geonorge.nedlasting.utils;

import javax.measure.Measure;
import javax.measure.quantity.Area;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

public class GeometryUtils {

    public static final Measure<Double, Area> calculateArea(Geometry geom, int srid) {
        try {
            CoordinateReferenceSystem originalCrs = CRS.decode("EPSG:" + srid, true);

            // convert to lat lon to find center lat lon
            CoordinateReferenceSystem ll = CRS.decode("EPSG:4326", true);
            MathTransform originalToLl = CRS.findMathTransform(originalCrs, ll);
            Geometry geomLl = JTS.transform(geom, originalToLl);
            Point centerLl = geomLl.getCentroid();

            // convert to a crs with meter based based coordinates
            String code = "AUTO:42001," + centerLl.getX() + "," + centerLl.getY();
            CoordinateReferenceSystem meterCrs = CRS.decode(code);
            MathTransform transform = CRS.findMathTransform(originalCrs, meterCrs);
            Geometry geomMeter = JTS.transform(geom, transform);

            return Measure.valueOf(geomMeter.getArea(), SI.SQUARE_METRE);
        } catch (MismatchedDimensionException | TransformException | FactoryException e) {
            throw new RuntimeException(e);
        }
    }

    public static final double calculateAreaSquareKilometer(Geometry geom, int srid) {
        Measure<Double, Area> area = calculateArea(geom, srid);
        @SuppressWarnings("unchecked")
        Unit<Area> sqkvm = (Unit<Area>) SI.KILOMETER.times(SI.KILOMETER);
        return area.doubleValue(sqkvm);
    }

}
