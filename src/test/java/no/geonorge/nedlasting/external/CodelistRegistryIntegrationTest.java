package no.geonorge.nedlasting.external;

import junit.framework.TestCase;
import no.geonorge.nedlasting.external.data.RegisterItem;
import no.geonorge.nedlasting.external.CodelistRegistry;

import java.io.IOException;
import java.util.List;

/**
 * Integration tests for Codelist Registry from Geonorge
 */
public class CodelistRegistryIntegrationTest extends TestCase {

    public void testGetVectorFormatItems() throws IOException{
        CodelistRegistry clReg = new CodelistRegistry();
        List<RegisterItem> vectorFormats = clReg.getVectorFormats();
        assertTrue(vectorFormats.size()>1);
    }

    public void testGetAreas() throws IOException {
        CodelistRegistry clReg = new CodelistRegistry();
        List<RegisterItem> areas = clReg.getAreaCategories();
        assertTrue(areas.size() > 1);
    }

    public void testGetProjections() throws IOException {
        CodelistRegistry clReg = new CodelistRegistry();
        List<RegisterItem> projections = clReg.getCrsCodes();
        assertTrue(projections.size() > 1);
    }

}
