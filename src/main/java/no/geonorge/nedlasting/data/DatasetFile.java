package no.geonorge.nedlasting.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.cayenne.ObjectContext;

import no.geonorge.nedlasting.data.auto._DatasetFile;
import no.geonorge.nedlasting.data.client.File;
import no.geonorge.nedlasting.data.client.Format;
import no.geonorge.nedlasting.data.client.OrderArea;
import no.geonorge.nedlasting.data.client.OrderLine;
import no.geonorge.nedlasting.security.FileProxyURLGenerator;

public class DatasetFile extends _DatasetFile {

    public Format getFormat() {
        Format format = new Format();
        format.setName(getFormatName());
        format.setVersion(getFormatVersion());
        return format;
    }

    String getAreaKey() {
        return getAreaType() + "-" + getAreaCode();
    }

    public void setArea(String type, String code, String name) {
        setAreaType(type);
        setAreaCode(code);
        setAreaName(name);
    }

    public OrderArea getOrderArea() {
        OrderArea oa = new OrderArea();
        oa.setType(getAreaType());
        oa.setCode(getAreaCode());
        oa.setName(getAreaName());
        return oa;
    }

    @Override
    public String getFileName() {
        String fn = super.getFileName();
        if (fn != null) {
            return fn;
        }

        // try to guess fro url
        String u = getUrl();
        int lp = u.lastIndexOf('/');
        return u.substring(lp + 1);
    }

    public File forClient(String urlPrefix) {
        File file = new File();
        file.setFileId(getFileId());
        file.setDownloadUrl(FileProxyURLGenerator.createUrl(urlPrefix, this));
        file.setName(getFileName());
        file.setMetadataUuid(getDataset().getMetadataUuid());
        file.setMetadataName(getDataset().getTitle());
        file.setFormat(getFormatName());
        file.setFormatVersion(getFormatVersion());
        file.setProjection(getProjection().getSrid().toString());
        file.setProjectionName(getProjection().getName());
        file.setArea(getAreaCode());
        file.setAreaName(getAreaName());
        file.setAreaType(getAreaType());
        file.setFileDate(getFileDate());
        return file;
    }

    public static List<DatasetFile> findForOrderLine(ObjectContext ctxt, OrderLine orderLine) {
        if (orderLine.hasCoordinates()) {
            return Collections.emptyList();
        }
        Dataset dataset = Dataset.forMetadataUUID(ctxt, orderLine.getMetadataUuid());
        if (dataset == null) {
            return Collections.emptyList();
        }

        List<DatasetFile> files = new ArrayList<>();
        for (DatasetFile file : dataset.getFiles()) {

            if (!orderLine.getFormats().contains(file.getFormat())) {
                continue;
            }

            if (!orderLine.getProjections().contains(file.getProjection().forClient())) {
                continue;
            }

            if (!orderLine.getAreas().contains(file.getOrderArea())) {
                continue;
            }

            files.add(file);
        }

        return Collections.unmodifiableList(files);
    }

}
