package no.geonorge.nedlasting.data.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OrderReceipt {

    private String referenceNumber;
    private List<File> files;
    private String email;
    private Date orderDate;
    private List<Link> _links;

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void addFile(File file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
    }

    public List<File> getFiles() {
        if (files == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(files);
    }

    public List<Link> getLinks() {
        if (_links == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(_links);
    }

}
