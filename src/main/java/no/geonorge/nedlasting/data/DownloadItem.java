package no.geonorge.nedlasting.data;

import java.io.IOException;

import no.geonorge.nedlasting.data.auto._DownloadItem;
import no.geonorge.nedlasting.data.client.File;
import no.geonorge.nedlasting.external.External;
import no.geonorge.nedlasting.external.ExternalStatus;

public class DownloadItem extends _DownloadItem {

    public File toFile() throws IOException {
        Dataset dataset = getDataset();

        // see if external status should be fetched
        if (getUrl() == null && getExternalJobId() != null && dataset != null && dataset.isExternal()) {
            External external = dataset.getExternal();
            ExternalStatus status = external.status(getExternalJobId());
            setUrl(status.getDownloadUrl());
            getObjectContext().commitChanges();
        }

        File file = new File();
        file.setFileId(getFileId());
        file.setMetadataUuid(getMetadataUuid());
        file.setDownloadUrl(getUrl());
        file.setStatus(File.STATUS_READY_FOR_DOWNLOAD);
        file.setProjection(getSrid().toString());
        if (getProjection() != null) {
            file.setProjectionName(getProjection().getName());
        }
        file.setName(getFileName());
        file.setCoordinates(getCoordinates());

        if (getUrl() != null) {
            file.setStatus(File.STATUS_READY_FOR_DOWNLOAD);
        } else if (getExternalJobId() != null) {
            file.setStatus(File.STATUS_WAITING_FOR_PROCESSING);
        }

        if (dataset != null) {
            file.setMetadataName(dataset.getTitle());
        }

        /*
         * file.setFormat(getFormatName()); file.setArea(getAreaCode());
         * file.setAreaName(getAreaName());
         */

        /*
         * example with
         * 
         * "fileId": "e7782d3f-1499-4512-a93b-4a787d5e9402",
         * 
         * "metadataUuid": "73f863ba-628f-48af-b7fa-30d3ab331b8d",
         * 
         * "coordinates":
         * "344754 7272921 404330 7187619 304134 7156477 344754 7272921",
         * 
         * "projection": "25832",
         * 
         * "projectionName": "EUREF89 UTM sone 32, 2d",
         * 
         * "format": "GML 3.2.1",
         * 
         * "status": "WaitingForProcessing",
         * 
         * "metadataName": "Brannsmitteomrader"
         * 
         * }
         */

        return file;
    }

    public Dataset getDataset() {
        return Dataset.forMetadataUUID(getObjectContext(), getMetadataUuid());
    }

    public void setProjection(Projection projection) {
        setSrid(projection == null ? null : projection.getSrid());
    }

    private Projection getProjection() {
        return Projection.getForSrid(getObjectContext(), getSrid());
    }

}
