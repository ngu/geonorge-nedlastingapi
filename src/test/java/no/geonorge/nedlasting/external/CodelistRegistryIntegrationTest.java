package no.geonorge.nedlasting.external;

import junit.framework.TestCase;
import no.geonorge.nedlasting.external.data.RegisterItem;
import no.geonorge.nedlasting.external.CodelistRegistry;

import java.io.IOException;
import java.util.List;

/**
 * Created by Grotan_Bjorn_Ove on 11.01.2019.
 */
public class CodelistRegistryIntegrationTest extends TestCase {
    public void testGetVectorFormatItems() throws IOException{
        CodelistRegistry clReg = new CodelistRegistry();
        List<RegisterItem> vectorFormats = clReg.getVectorFormats();
        assertTrue(vectorFormats.size()>1);
    }
}
