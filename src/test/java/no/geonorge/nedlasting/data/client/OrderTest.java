package no.geonorge.nedlasting.data.client;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import junit.framework.TestCase;
import no.geonorge.nedlasting.utils.GsonCreator;
import no.geonorge.nedlasting.utils.IOUtils;

public class OrderTest extends TestCase {

    public void testDeserialize() throws IOException {
        Reader reader = null;
        try {
            reader = new FileReader("src/test/resources/order.json");
            Order order = GsonCreator.create().fromJson(reader, Order.class);
            assertNotNull(order);
        } finally {
            IOUtils.close(reader);
        }
    }

}
