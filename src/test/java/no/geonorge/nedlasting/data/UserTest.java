package no.geonorge.nedlasting.data;

import java.util.UUID;

import org.apache.cayenne.ObjectContext;

import no.geonorge.junit.DbTestCase;
import no.geonorge.nedlasting.config.Config;

public class UserTest extends DbTestCase {
    
    public void testPassword() {
        ObjectContext ctxt = Config.getObjectContext();
        User user = ctxt.newObject(User.class);
        user.setUsername("junit-" + UUID.randomUUID().toString());
        ctxt.commitChanges();
        assertFalse(user.isValidPassword("hello"));

        user.setPassword("hello");
        ctxt.commitChanges();
        assertTrue(user.isValidPassword("hello"));
        assertFalse(user.isValidPassword("hello world"));
        
        ctxt.deleteObject(user);
        ctxt.commitChanges();
    }

}
