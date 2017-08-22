package no.geonorge.rest;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.cayenne.ObjectContext;

import no.geonorge.junit.DbTestCase;
import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.Dataset;
import no.geonorge.nedlasting.data.DatasetFile;

public class DownloadServiceTest extends DbTestCase {

    public void testCapabilities() throws IOException {
        DownloadService ds = new DownloadService();

        String uuid = UUID.randomUUID().toString();
        assertEquals(HttpServletResponse.SC_NOT_FOUND, ds.returnCapabilities(uuid).getStatus());

        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = ctxt.newObject(Dataset.class);
        dataset.setMetadataUuid(uuid);
        ctxt.commitChanges();

        assertEquals(HttpServletResponse.SC_OK, ds.returnCapabilities(uuid).getStatus());
    }

    public void testFormats() throws IOException {
        DownloadService ds = new DownloadService();

        String uuid = UUID.randomUUID().toString();
        assertEquals(HttpServletResponse.SC_NOT_FOUND, ds.returnFormats(uuid).getStatus());

        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = ctxt.newObject(Dataset.class);
        dataset.setMetadataUuid(uuid);
        DatasetFile datasetFile = ctxt.newObject(DatasetFile.class);
        datasetFile.setDataset(dataset);
        datasetFile.setFormat("SOSI");
        datasetFile.setUrl("http://a.url.com");
        ctxt.commitChanges();

        assertEquals(HttpServletResponse.SC_OK, ds.returnFormats(uuid).getStatus());
    }

}
