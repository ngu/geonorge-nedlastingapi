package no.geonorge.nedlasting.data.client;

import java.util.ArrayList;
import java.util.List;

public class Capabilities {

    private boolean supportsProjectionSelection;
    private boolean supportsFormatSelection;
    private boolean supportsPolygonSelection;
    private boolean supportsAreaSelection;
    private boolean supportsDownloadBundling;
    private String distributedBy;
    private boolean deliveryNotificationByEmail;
    private String mapSelectionLayer;

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

    public boolean isSupportsDownloadBundling() { return supportsDownloadBundling;}

    public void setSupportsDownloadBundling(boolean supportsDownloadBundling) {
        this.supportsDownloadBundling = supportsDownloadBundling;
    }

    public String getDistributedBy() { return distributedBy; }

    public void setDistributedBy(String distributedBy) {
        this.distributedBy = distributedBy;
    }

    public boolean isDeliveryNotificationByEmail() { return deliveryNotificationByEmail; }

    public void setDeliveryNotificationByEmail(boolean deliveryNotificationByEmail) {
        this.deliveryNotificationByEmail = deliveryNotificationByEmail;
    }

    public void setMapSelectionLayer(String mapSelectionLayer) {
        this.mapSelectionLayer = mapSelectionLayer;
    }

    public String getMapSelectionLayer() {return mapSelectionLayer;}

    public void addLink(Link link) {
        _links.add(link);
    }

}
