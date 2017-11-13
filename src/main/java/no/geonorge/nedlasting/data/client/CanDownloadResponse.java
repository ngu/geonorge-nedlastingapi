package no.geonorge.nedlasting.data.client;

public class CanDownloadResponse {

    private boolean canDownload;
    private String message;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
