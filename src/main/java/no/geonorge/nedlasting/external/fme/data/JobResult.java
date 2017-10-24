package no.geonorge.nedlasting.external.fme.data;

public class JobResult {

    private Integer id;
    private String statusMessage;
    private String resultDatasetDownloadUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getResultDatasetDownloadUrl() {
        return resultDatasetDownloadUrl;
    }

    public void setResultDatasetDownloadUrl(String resultDatasetDownloadUrl) {
        this.resultDatasetDownloadUrl = resultDatasetDownloadUrl;
    }

}
