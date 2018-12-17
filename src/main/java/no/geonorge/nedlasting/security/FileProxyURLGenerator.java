package no.geonorge.nedlasting.security;

import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.DatasetFile;
import no.geonorge.nedlasting.data.DownloadItem;

public class FileProxyURLGenerator {

    public static String createUrl(String urlPrefix, DownloadItem downloadItem) {
        if (downloadItem.getUrl() != null && !Config.isEnableFileProxy()) {
            return downloadItem.getUrl();
        }
        return urlPrefix + "v2/download/order/" + downloadItem.getOrder().getReferenceNumber() + "/"
                + downloadItem.getFileId();
    }

    public static String createUrl(String urlPrefix, DatasetFile datasetFile) {
        if (datasetFile.getUrl() != null && !Config.isEnableFileProxy()) {
            return datasetFile.getUrl();
        }
        return urlPrefix + "fileproxy/" + datasetFile.getDataset().getMetadataUuid() + "/" + datasetFile.getFileId();
    }

}
