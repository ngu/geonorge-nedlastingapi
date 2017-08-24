package no.geonorge.nedlasting.data.client;

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
