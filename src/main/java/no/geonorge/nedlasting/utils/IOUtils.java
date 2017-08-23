package no.geonorge.nedlasting.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOUtils {

    private static final Logger log = Logger.getLogger(IOUtils.class.getName());

    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException e) {
            log.log(Level.INFO, "could not close", e);
        }

    }

}
