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
        Point centroid = geom.getCentroid();
        try {
            CoordinateReferenceSystem pcrs = CRS.decode("EPSG:" + srid);
            // TODO: oups, this is not good for UTM type zones
            String code = "AUTO:42001," + centroid.getX() + "," + centroid.getY();
            CoordinateReferenceSystem auto = CRS.decode(code);

            MathTransform transform = CRS.findMathTransform(pcrs, auto);

            Geometry projected = JTS.transform(geom, transform);
            return Measure.valueOf(projected.getArea(), SI.SQUARE_METRE);
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
