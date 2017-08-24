package no.geonorge.nedlasting.data.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    private String email;
    private List<OrderLine> orderLines;
    private List<Link> _links;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    public void addOrderLine(OrderLine orderLine) {
        if (orderLines == null) {
            orderLines = new ArrayList<>();
        }
        orderLines.add(orderLine);
    }

    public List<OrderLine> getOrderLines() {
        if (orderLines == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(orderLines);
    }

    public List<Link> getLinks() {
        if (_links == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(_links);
    }

}
