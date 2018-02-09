package no.geonorge.nedlasting.data.client;

import java.util.Arrays;
import java.util.List;

public class DownloadErrorResponse {

    private OrderLine orderLine;

    private List<OrderArea> areaRest = null;
    private List<Format> formatRest = null;
    private List<Projection> projectionRest = null;

    public DownloadErrorResponse(OrderLine orderLine) {
        this.orderLine = orderLine;
    }

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public void setAreaRest(List<OrderArea> areaRest) {
        if (areaRest != null && !areaRest.isEmpty()) {
            this.areaRest = areaRest;
        }
    }

    public void setFormatRest(List<Format> formatRest) {
        if (formatRest != null && !formatRest.isEmpty()) {
            this.formatRest = formatRest;
        }
    }

    public void setProjectionRest(List<Projection> projectionRest) {
        if (projectionRest != null && !projectionRest.isEmpty()) {
            this.projectionRest = projectionRest;
        }
    }

    public boolean hasRest() {
        for (List<?> rest : Arrays.asList(areaRest, formatRest, projectionRest)) {
            if (rest != null && !rest.isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
