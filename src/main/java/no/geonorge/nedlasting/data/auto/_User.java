package no.geonorge.nedlasting.data.auto;

import org.apache.cayenne.CayenneDataObject;

/**
 * Class _User was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _User extends CayenneDataObject {

    public static final String PASSWORD_SHA512_PROPERTY = "passwordSha512";
    public static final String USERNAME_PROPERTY = "username";

    public static final String USERNAME_PK_COLUMN = "USERNAME";

    public void setPasswordSha512(String passwordSha512) {
        writeProperty(PASSWORD_SHA512_PROPERTY, passwordSha512);
    }
    public String getPasswordSha512() {
        return (String)readProperty(PASSWORD_SHA512_PROPERTY);
    }

    public void setUsername(String username) {
        writeProperty(USERNAME_PROPERTY, username);
    }
    public String getUsername() {
        return (String)readProperty(USERNAME_PROPERTY);
    }

}