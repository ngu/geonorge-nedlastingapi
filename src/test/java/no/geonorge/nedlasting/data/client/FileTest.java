package no.geonorge.nedlasting.data.client;

import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;

import junit.framework.TestCase;
import no.geonorge.nedlasting.utils.GsonCreator;

public class FileTest extends TestCase {

    public void testFileDateEncoding() throws IOException {
        Gson gson = GsonCreator.create();
        File file1 = new File();
        file1.setFileDate(new Date());
        String json = gson.toJson(file1);
        System.out.println(json);
        assertTrue(json.contains("-"));
        assertTrue(json.endsWith("Z\"}"));
        File file2 = gson.fromJson(json, File.class);
        assertNotNull(file2.getFileDate());
        assertEquals(file1.getFileDate().getTime(), file2.getFileDate().getTime(), 2000);
    }

}
