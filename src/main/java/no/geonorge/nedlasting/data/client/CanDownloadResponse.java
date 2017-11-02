package no.geonorge.nedlasting.data.client;

public class CanDownloadResponse {

    private boolean canDownload;

    public CanDownloadResponse() {

    }

    public CanDownloadResponse(boolean canDownload) {
        this.canDownload = canDownload;
    }

    public boolean isCanDownload() {
        return canDownload;
    }

    public void setCanDownload(boolean canDownload) {
        this.canDownload = canDownload;
    }

}
