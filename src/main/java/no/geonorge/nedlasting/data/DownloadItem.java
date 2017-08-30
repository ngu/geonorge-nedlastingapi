package no.geonorge.nedlasting.data;

import no.geonorge.nedlasting.data.auto._DownloadItem;
import no.geonorge.nedlasting.data.client.File;

public class DownloadItem extends _DownloadItem {

    public File toFile() {
        File file = new File();
        file.setFileId(getFileId());
        file.setMetadataUuid(getMetadataUuid());
        file.setDownloadUrl(getUrl());
        file.setStatus("ReadyForDownload");
        file.setProjection(getProjection().getSrid().toString());
        file.setProjectionName(getProjection().getName());
        file.setName(getFileName());

        /*
         * file.setMetadataName(getDataset().getTitle());
         * file.setFormat(getFormatName()); file.setArea(getAreaCode());
         * file.setAreaName(getAreaName());
         */

        return file;
    }

}
