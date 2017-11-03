package no.geonorge.nedlasting.external.fme;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import no.geonorge.nedlasting.data.client.Format;
import no.geonorge.nedlasting.data.client.Projection;
import no.geonorge.nedlasting.external.ExternalStatus;
import no.geonorge.nedlasting.external.fme.FMEClient;
import no.geonorge.nedlasting.external.fme.NGUFMEClient;
import no.geonorge.nedlasting.external.fme.data.JobInfo;
import no.geonorge.nedlasting.external.fme.data.JobResult;

public class NGUFMEClientTestDisabled extends TestCase {

    private String urlPrefix = System.getProperty("fme.urlPrefix");
    private String username = System.getProperty("fme.username");
    private String password = System.getProperty("fme.password");

    private String repository = "Datanedlasting";
    private String workspace = "GrunnvannBorehull_Nedlasting.fmw";

    private FMEClient fme() {
        return new NGUFMEClient(urlPrefix, username, password, repository, workspace);
    }

    public void testCreateToken() throws IOException {
        FMEClient fme = fme();
        String token = fme.fetchToken();
        System.out.println("token: " + token);
        assertNotNull(token);
        assertEquals(40, token.length());
    }

    public void testProjections() throws IOException {
        List<Projection> projections = fme().getProjections();
        assertFalse(projections.isEmpty());
        Set<Integer> srids = new HashSet<>();
        for (Projection projection : projections) {
            srids.add(projection.getSrid());
        }
        assertFalse(srids.isEmpty());
        assertTrue(srids.contains(Integer.valueOf(32633)));
    }

    public void testFormats() throws IOException {
        List<Format> formats = fme().getFormats();
        assertFalse(formats.isEmpty());
    }
    
    public void testSubmitJob() throws IOException, InterruptedException {
        FMEClient fme = fme();
        List<Format> formats = fme.getFormats();
        List<Projection> projections = fme.getProjections();
        String jobId = fme.submitJob(formats.get(0), projections.get(0), null, null);
        assertNotNull(jobId);

        ExternalStatus r = null;
        for (int i = 0; i < 100; i++) {
            Thread.sleep(5000);

            r = fme.status(jobId);
            assertNotNull(r);
            if (r.getDownloadUrl() != null) {
                break;
            }
        }

        assertNotNull(r);
        assertTrue(r.getStatusMessage().contains("Successful"));
        assertNotNull(r.getDownloadUrl());
    }

}
