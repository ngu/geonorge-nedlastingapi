package no.geonorge.nedlasting.utils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.logging.Logger;

public class UuidUtils {

    private static final Logger log = Logger.getLogger(UuidUtils.class.getName());

    public static String getUuid(String datasetTitle, String fileId) throws UnsupportedEncodingException {
        String val = datasetTitle+fileId;
        byte[] bytes = val.getBytes("UTF-8");
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        return uuid.toString();
    }

}
