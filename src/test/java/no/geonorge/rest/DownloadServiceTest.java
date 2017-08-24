package no.geonorge.rest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.cayenne.ObjectContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import no.geonorge.junit.DbTestCase;
import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.Dataset;
import no.geonorge.nedlasting.data.DatasetFile;
import no.geonorge.nedlasting.data.client.Area;
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

    public void testAreas() throws IOException {
        DownloadService ds = new DownloadService();

        String uuid = UUID.randomUUID().toString();
        assertEquals(HttpServletResponse.SC_NOT_FOUND, ds.returnAreas(uuid).getStatus());

        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = ctxt.newObject(Dataset.class);
        dataset.setMetadataUuid(uuid);
        DatasetFile datasetFile1 = ctxt.newObject(DatasetFile.class);
        datasetFile1.setArea("fylke", "02", "Akershus");
        datasetFile1.setDataset(dataset);
        datasetFile1.setFormat("SOSI");
        datasetFile1.setUrl("http://a.url.com/1");
        datasetFile1.setSrid(4326);
        DatasetFile datasetFile2 = ctxt.newObject(DatasetFile.class);
        datasetFile2.setArea("fylke", "02", "Akershus");
        datasetFile2.setDataset(dataset);
        datasetFile2.setFormat("GML");
        datasetFile2.setUrl("http://a.url.com/2");
        datasetFile2.setSrid(4326);
        DatasetFile datasetFile3 = ctxt.newObject(DatasetFile.class);
        datasetFile3.setArea("fylke", "02", "Akershus");
        datasetFile3.setDataset(dataset);
        datasetFile3.setFormat("SOSI");
        datasetFile3.setUrl("http://a.url.com/3");
        datasetFile3.setSrid(32633);
        DatasetFile datasetFile4 = ctxt.newObject(DatasetFile.class);
        datasetFile4.setArea("fylke", "02", "Akershus");
        datasetFile4.setDataset(dataset);
        datasetFile4.setFormat("GML");
        datasetFile4.setUrl("http://a.url.com/4");
        datasetFile4.setSrid(32633);
        DatasetFile datasetFile5 = ctxt.newObject(DatasetFile.class);
        datasetFile5.setArea("fylke", "03", "Whatever");
        datasetFile5.setDataset(dataset);
        datasetFile5.setFormat("GML");
        datasetFile5.setUrl("http://a.url.com/5");
        datasetFile5.setSrid(32633);
        ctxt.commitChanges();

        assertEquals(HttpServletResponse.SC_OK, ds.returnAreas(uuid).getStatus());

        Type type = new TypeToken<List<Area>>() {
        }.getType();
        List<Area> areas = new Gson().fromJson(ds.returnAreas(uuid).getEntity().toString(), type);
        assertNotNull(areas);
        assertEquals(2, areas.size());

        Map<String, Area> areaByCode = new HashMap<>();
        for (Area area : areas) {
            areaByCode.put(area.getCode(), area);
        }
        assertEquals(2, areaByCode.size());

        Area area02 = areaByCode.get("02");
        assertNotNull(area02);
        assertEquals("Akershus", area02.getName());
        assertEquals(2, area02.getProjections().size());
        assertEquals(2, area02.getFormats().size());

        Area area03 = areaByCode.get("03");
        assertNotNull(area03);
        assertEquals("Whatever", area03.getName());
        assertEquals(1, area03.getProjections().size());
        assertEquals(1, area03.getFormats().size());

    }

}
