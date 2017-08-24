package no.geonorge.junit;

import org.apache.cayenne.ObjectContext;
import org.apache.commons.dbcp2.BasicDataSource;

import junit.framework.TestCase;
import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.Projection;

public abstract class DbTestCase extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:derby:./target/testdb;create=true");
        Config.setDataSource(ds);
    }
    
    protected Projection createOrFind(ObjectContext ctxt, int srid) {
        Projection p = Projection.getForSrid(ctxt, srid);
        if (p == null) {
            p = ctxt.newObject(Projection.class);
            p.setName("junit");
            p.setCodespace("http://whatever");
            p.setSrid(srid);
        }
        return p;
    }

}
