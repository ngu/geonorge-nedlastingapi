package no.geonorge.nedlasting.data.client;

import com.vividsolutions.jts.geom.Geometry;

import no.geonorge.nedlasting.utils.GeometryUtils;

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
        return GeometryUtils.parseCoordList(getCoordinates());
    }

}
