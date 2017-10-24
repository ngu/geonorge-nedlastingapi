package no.geonorge.nedlasting.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static final void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int bytesRead = 0;
        while ((bytesRead = in.read(buf)) != -1) {
            out.write(buf, 0, bytesRead);
        }
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out.toByteArray();
    }

    public static String toString(InputStream in) throws IOException {
        return new String(toByteArray(in), "UTF-8");
    }

}
