package no.geonorge.rest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.cayenne.ObjectContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import no.geonorge.junit.DbTestCase;
import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.Dataset;
import no.geonorge.nedlasting.data.DatasetFile;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.FormatType;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.ProjectionType;

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
        datasetFile.setSrid(4326);
        ctxt.commitChanges();

        assertEquals(HttpServletResponse.SC_OK, ds.returnFormats(uuid).getStatus());

        Type type = new TypeToken<List<FormatType>>() {
        }.getType();
        List<FormatType> formats = new Gson().fromJson(ds.returnFormats(uuid).getEntity().toString(), type);
        assertNotNull(formats);
        assertEquals(1, formats.size());
        FormatType format = formats.get(0);
        assertEquals("SOSI", format.getName());
    }

    public void testProjections() throws IOException {
        DownloadService ds = new DownloadService();

        String uuid = UUID.randomUUID().toString();
        assertEquals(HttpServletResponse.SC_NOT_FOUND, ds.returnProjections(uuid).getStatus());

        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = ctxt.newObject(Dataset.class);
        dataset.setMetadataUuid(uuid);
        DatasetFile datasetFile = ctxt.newObject(DatasetFile.class);
        datasetFile.setDataset(dataset);
        datasetFile.setFormat("SOSI");
        datasetFile.setUrl("http://a.url.com");
        datasetFile.setSrid(4326);
        ctxt.commitChanges();

        assertEquals(HttpServletResponse.SC_OK, ds.returnProjections(uuid).getStatus());

        Type type = new TypeToken<List<ProjectionType>>() {
        }.getType();
        List<ProjectionType> projections = new Gson().fromJson(ds.returnProjections(uuid).getEntity().toString(), type);
        assertNotNull(projections);
        assertEquals(1, projections.size());
        ProjectionType projection = projections.get(0);
        assertEquals("4326", projection.getCode());
    }

}
