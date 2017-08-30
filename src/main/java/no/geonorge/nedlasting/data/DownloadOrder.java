package no.geonorge.nedlasting.data;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;

import no.geonorge.nedlasting.data.auto._DownloadOrder;
import no.geonorge.nedlasting.data.client.OrderReceipt;

public class DownloadOrder extends _DownloadOrder {

    public Integer getOrderId() {
        return Cayenne.intPKForObject(this);
    }

    public OrderReceipt getOrderReceipt() {
        OrderReceipt orderReceipt = new OrderReceipt();
        orderReceipt.setReferenceNumber(getOrderId().toString());
        orderReceipt.setEmail(getEmail());
        orderReceipt.setOrderDate(getStartTime());

        for (DownloadItem item : getItems()) {
            orderReceipt.addFile(item.toFile());
        }

        return orderReceipt;
    }

    public static DownloadOrder get(ObjectContext ctxt, Integer orderId) {
        return Cayenne.objectForPK(ctxt, DownloadOrder.class, orderId);
    }

    public static DownloadOrder get(ObjectContext ctxt, String referenceNumber) {
        try {
            return get(ctxt, Integer.valueOf(referenceNumber));
        } catch (RuntimeException e) {
            return null;
        }
    }

}
