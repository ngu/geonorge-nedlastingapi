package no.geonorge.nedlasting.data;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;

import no.geonorge.nedlasting.data.auto._User;
import no.geonorge.nedlasting.utils.SHA512;

public class User extends _User {
    
    public static User get(ObjectContext ctxt, String username) {
        return Cayenne.objectForPK(ctxt, User.class, username);
    }
    
    public boolean isValidPassword(String candidate) {
        if (candidate == null) {
            return false;
        }
        return SHA512.hash(candidate).equals(getPasswordSha512());
    }
    
    public void setPassword(String password) {
        setPasswordSha512(SHA512.hash(password));
    }

}
