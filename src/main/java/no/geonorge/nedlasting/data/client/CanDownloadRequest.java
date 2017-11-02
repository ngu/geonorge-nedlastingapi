package no.geonorge.nedlasting.data.client;

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

}
