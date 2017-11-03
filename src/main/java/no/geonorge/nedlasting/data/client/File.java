package no.geonorge.nedlasting.data.client;

import java.util.Collections;
import java.util.List;

public class File {

    private String downloadUrl;
    private String fileSize;
    private String name;
    private String fileId;
    private String metadataUuid;
    private String area;
    private String areaName;
    private String coordinates;
    private String projection;
    private String projectionName;
    private String format;
    private String status;
    private String metadataName;
    
    public static final String STATUS_READY_FOR_DOWNLOAD = "ReadyForDownload";
    public static final String STATUS_WAITING_FOR_PROCESSING = "WaitingForProcessing";
    
    /*
    {

        "fileId": "e7782d3f-1499-4512-a93b-4a787d5e9402",

        "metadataUuid": "73f863ba-628f-48af-b7fa-30d3ab331b8d",

        "coordinates": "344754 7272921 404330 7187619 304134 7156477 344754 7272921",

        "projection": "25832",

        "projectionName": "EUREF89 UTM sone 32, 2d",

        "format": "GML 3.2.1",

        "status": "WaitingForProcessing",

        "metadataName": "Brannsmitteomrader"

      }
      */

    private List<Link> _links;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getMetadataUuid() {
        return metadataUuid;
    }

    public void setMetadataUuid(String metadataUuid) {
        this.metadataUuid = metadataUuid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getProjection() {
        return projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }

    public String getProjectionName() {
        return projectionName;
    }

    public void setProjectionName(String projectionName) {
        this.projectionName = projectionName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMetadataName() {
        return metadataName;
    }

    public void setMetadataName(String metadataName) {
        this.metadataName = metadataName;
    }

    public List<Link> getLinks() {
        if (_links == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(_links);
    }

}
