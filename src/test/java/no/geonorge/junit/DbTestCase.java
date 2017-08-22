package no.geonorge.junit;

import org.apache.commons.dbcp2.BasicDataSource;

import junit.framework.TestCase;
import no.geonorge.nedlasting.config.Config;

public abstract class DbTestCase extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:derby:./target/testdb;create=true");
        Config.setDataSource(ds);
    }

}
