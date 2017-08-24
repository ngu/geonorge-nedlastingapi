package no.geonorge.nedlasting.data.client;

import java.util.ArrayList;
import java.util.List;

public class Capabilities {

    private boolean supportsProjectionSelection;
    private boolean supportsFormatSelection;
    private boolean supportsPolygonSelection;
    private boolean supportsAreaSelection;

    private List<Link> _links = new ArrayList<>();

    public boolean isSupportsProjectionSelection() {
        return supportsProjectionSelection;
    }

    public void setSupportsProjectionSelection(boolean supportsProjectionSelection) {
        this.supportsProjectionSelection = supportsProjectionSelection;
    }

    public boolean isSupportsFormatSelection() {
        return supportsFormatSelection;
    }

    public void setSupportsFormatSelection(boolean supportsFormatSelection) {
        this.supportsFormatSelection = supportsFormatSelection;
    }

    public boolean isSupportsPolygonSelection() {
        return supportsPolygonSelection;
    }

    public void setSupportsPolygonSelection(boolean supportsPolygonSelection) {
        this.supportsPolygonSelection = supportsPolygonSelection;
    }

    public boolean isSupportsAreaSelection() {
        return supportsAreaSelection;
    }

    public void setSupportsAreaSelection(boolean supportsAreaSelection) {
        this.supportsAreaSelection = supportsAreaSelection;
    }

    public void addLink(Link link) {
        _links.add(link);
    }

}
