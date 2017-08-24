package no.geonorge.nedlasting.data.client;

import java.util.Collections;
import java.util.List;

public class OrderLine {

    private List<OrderArea> areas;
    private List<Format> formats;
    private String metadataUuid;
    private List<Projection> projections;
    private String coordinates;
    private String coordinatesystem;
    private List<Link> _links;

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

    public String getCoordinatesystem() {
        return coordinatesystem;
    }

    public void setCoordinatesystem(String coordinatesystem) {
        this.coordinatesystem = coordinatesystem;
    }

    public List<OrderArea> getAreas() {
        if (areas == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(areas);
    }

    public List<Projection> getProjections() {
        if (projections == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(projections);
    }

    public List<Format> getFormats() {
        if (formats == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(formats);
    }

    public List<Link> getLinks() {
        if (_links == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(_links);
    }

}
