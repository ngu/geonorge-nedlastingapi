package no.geonorge.nedlasting.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A helper class for basic SHA1 operations.
 */
public class SHA1Helper {

    public static String sha1String(String clrtxt) {
        try {
            return sha1String(clrtxt.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static String sha1String(byte[] clr) {
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance("SHA-1");
            sha.update(clr);
            byte[] digest = sha.digest();
            StringBuffer hexStringBuf = new StringBuffer();
            int digestLength = digest.length;
            for (int i = 0; i < digestLength; i++) {
                hexStringBuf.append(hexDigit(digest[i]));
            }
            String hash = hexStringBuf.toString();
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String hexDigit(byte x) {
        StringBuffer sb = new StringBuffer();
        char c;
        // First nibble
        c = (char) ((x >> 4) & 0xf);
        if (c > 9) {
            c = (char) ((c - 10) + 'a');
        } else {
            c = (char) (c + '0');
        }
        sb.append(c);
        // Second nibble
        c = (char) (x & 0xf);
        if (c > 9) {
            c = (char) ((c - 10) + 'a');
        } else {
            c = (char) (c + '0');
        }
        sb.append(c);
        return sb.toString();
    }

}