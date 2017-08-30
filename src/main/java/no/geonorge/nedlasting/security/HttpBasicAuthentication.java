package no.geonorge.nedlasting.security;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cayenne.ObjectContext;
import org.apache.commons.codec.binary.Base64;

import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.User;

public class HttpBasicAuthentication {

    public static final String AUTH_TYPE = "basic";

    private static String realm(HttpServletRequest request) {
        return request.getServerName();
    }

    public static void askForPassword(HttpServletRequest request, HttpServletResponse response) {
        String s = "Basic realm=\"" + realm(request) + "\"";
        response.setHeader("WWW-Authenticate", s);
        response.setStatus(401);
    }

    public static User getUser(ServletRequest request) {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null) {
            authHeader = servletRequest.getHeader("authorization");
        }

        if (authHeader == null) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(authHeader);
        if (!st.hasMoreTokens()) {
            return null;
        }
        String basic = st.nextToken();

        if (basic.equalsIgnoreCase("Basic") && st.hasMoreTokens()) {
            String credentials = st.nextToken();

            for (String userPass : decodeBase64Variants(credentials)) {

                // The decoded string is in the form
                // "userID:password".

                int p = userPass.indexOf(":");
                if (p != -1) {
                    String userID = userPass.substring(0, p);
                    String password = userPass.substring(p + 1);

                    ObjectContext ctxt = Config.getObjectContext();
                    User user = User.get(ctxt, userID);
                    if (user == null) {
                        continue;
                    }
                    if (user.isValidPassword(password)) {
                        return user;
                    }
                }
            }
        }

        return null;
    }

    static Collection<String> decodeBase64Variants(String encoded) {
        byte[] decoded = Base64.decodeBase64(encoded);
        Set<String> decodedVariants = new HashSet<>(2);
        for (String charset : Arrays.asList("UTF-8", "ISO-8859-1")) {
            try {
                decodedVariants.add(new String(decoded, charset));
            } catch (IOException e) {
                // do not care.
            }
        }
        return Collections.unmodifiableCollection(decodedVariants);
    }

    public static void sendAuth(HttpURLConnection conn, String username, String password) throws IOException {
        if (username == null || password == null) {
            return;
        }

        String userpass = username + ":" + password;
        sendAuth(conn, userpass);
    }

    public static void sendAuth(HttpURLConnection conn, String userpass) throws IOException {
        if (userpass == null) {
            return;
        }

        String encoded = Base64.encodeBase64String(userpass.getBytes("UTF-8"));
        conn.setRequestProperty("Authorization", "Basic " + encoded.trim());
    }

}
