package no.geonorge.nedlasting.data;

import java.io.IOException;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import no.geonorge.nedlasting.data.auto._DownloadOrder;
import no.geonorge.nedlasting.data.client.OrderReceipt;

public class DownloadOrder extends _DownloadOrder {

    public OrderReceipt getOrderReceipt(String urlPrefix) throws IOException {
        OrderReceipt orderReceipt = new OrderReceipt();
        orderReceipt.setReferenceNumber(getReferenceNumber());
        orderReceipt.setEmail(getEmail());
        orderReceipt.setOrderDate(getStartTime());

        for (DownloadItem item : getItems()) {
            orderReceipt.addFile(item.toOrderReceiptFile(urlPrefix));
        }

        return orderReceipt;
    }
    
    public DownloadItem getItemForFileId(String fileId) {
        for (DownloadItem item : getItems()) {
            if (item.getFileId().equals(fileId)) {
                return item;
            }
        }
        return null;
    }

    public static DownloadOrder get(ObjectContext ctxt, String referenceNumber) {
        if (referenceNumber == null) {
            return null;
        }
        Expression qual = ExpressionFactory.matchExp(REFERENCE_NUMBER_PROPERTY, referenceNumber);
        SelectQuery query = new SelectQuery(DownloadOrder.class, qual);
        @SuppressWarnings("unchecked")
        List<DownloadOrder> downloads = ctxt.performQuery(query);
        if (downloads.isEmpty()) {
            return null;
        }
        if (downloads.size() > 1) {
            throw new IllegalStateException("multiple downloads with reference number " + referenceNumber);
        }
        return downloads.get(0);
    }
    
}
