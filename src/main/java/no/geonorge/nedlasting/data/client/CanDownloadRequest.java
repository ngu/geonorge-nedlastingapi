package no.geonorge.nedlasting.data.client;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.simplify.VWSimplifier;

public class CanDownloadRequest {

    private String metadataUuid;
    private String coordinates;
    private String coordinateSystem;

    public String getMetadataUuid() {
        return metadataUuid;
    }

    public void setMetadataUuid(String metadataUuid) {
        this.metadataUuid = metadataUuid;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getCoordinateSystem() {
        return coordinateSystem;
    }

    public void setCoordinateSystem(String coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    public Integer getSrid() {
        return Integer.decode(getCoordinateSystem());
    }

    public boolean hasCoordinates() {
        return getCoordinates() != null && getCoordinates().length() > 0;
    }

    /**
     * @return a {@link Geometry} from the coordinates without any
     *         re-projecting.
     */
    public Geometry getGeometry() {
        if (!hasCoordinates()) {
            return null;
        }

        List<Double> values = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(getCoordinates());
        while (st.hasMoreTokens()) {
            values.add(Double.valueOf(st.nextToken()));
        }
        if (values.size() % 2 != 0) {
            throw new RuntimeException("even number coordinate values not supported");
        }

        Coordinate[] coordinates = new Coordinate[values.size() / 2];
        for (int i = 0; i < coordinates.length; i++) {
            Coordinate coordinate = new Coordinate();
            coordinate.x = values.get(i * 2).doubleValue();
            coordinate.y = values.get((i * 2) + 1).doubleValue();
            coordinates[i] = coordinate;
        }

        Geometry geom = new GeometryFactory().createPolygon(coordinates);
        System.out.println(geom);
        
        if (!geom.isValid()) {
            //geom = VWSimplifier.simplify(geom, 0.0d);
            geom = geom.norm();
        }
        
        return geom;
    }

}
