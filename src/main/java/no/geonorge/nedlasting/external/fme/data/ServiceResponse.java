package no.geonorge.nedlasting.external.fme.data;

public class ServiceResponse {

    private Integer jobID;
    private StatusInfo statusInfo;

    public Integer getJobID() {
        return jobID;
    }

    public void setJobID(Integer jobID) {
        this.jobID = jobID;
    }

    public StatusInfo getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(StatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }

}
