package no.geonorge.nedlasting.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataDomain;
import org.apache.cayenne.access.DataNode;
import org.apache.cayenne.configuration.DataNodeDescriptor;
import org.apache.cayenne.configuration.server.DataSourceFactory;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.dba.DbAdapter;
import org.apache.cayenne.map.DataMap;
import org.apache.cayenne.merge.AbstractToDbToken;
import org.apache.cayenne.merge.DbMerger;
import org.apache.cayenne.merge.DropColumnToDb;
import org.apache.cayenne.merge.DropTableToDb;
import org.apache.cayenne.merge.ExecutingMergerContext;
import org.apache.cayenne.merge.MergerToken;
import org.apache.commons.dbcp2.BasicDataSource;

import no.geonorge.nedlasting.data.upgrade.DbUpgrade;
import no.geonorge.nedlasting.utils.IOUtils;

public class Config implements DataSourceFactory {

    private static final String KEY_SERVER_PORT = "server.port";
    private static final String KEY_JETTY_PORT = "jetty.port";

    private static final String KEY_DATABASE_DRIVER = "database.driver";
    private static final String KEY_DATABASE_URL = "database.url";
    private static final String KEY_DATABASE_USERNAME = "database.username";
    private static final String KEY_DATABASE_PASSWORD = "database.password";
    
    private static final String KEY_CORS = "cors";

    private static ServerRuntime runtime;
    private static boolean inited = false;
    private static int serverPort = 10000;
    private static DataSource dataSource;
    private static String cors;

    private static final Logger log = Logger.getLogger(Config.class.getName());

    static {
        log.info("setting up cayenne ServerRuntime");
        runtime = new ServerRuntime("cayenne-domain.xml");
    }

    public static void readConfiguration() throws IOException {

        Properties prop = new Properties();

        for (String file : Arrays.asList("/etc/geonorge.properties", "geonorge.properties")) {
            File configFile = new File(file);
            if (!configFile.canRead()) {
                continue;
            }
            InputStream input = null;
            try {
                input = new FileInputStream(configFile);
                prop.load(input);
                log.info("read configuration from " + configFile);
            } finally {
                IOUtils.close(input);
            }
        }

        // look for two different variables for port number. This is to both
        // be compatible with geonorge.properties and 
        // https://github.com/ElectronicChartCentre/deploy-scripts
        Config.serverPort = getProperty(prop, KEY_SERVER_PORT, Config.serverPort);
        Config.serverPort = getProperty(prop, KEY_JETTY_PORT, Config.serverPort);
        
        Config.cors = getProperty(prop, KEY_CORS, "*");

        BasicDataSource nds = new BasicDataSource();
        nds.setUrl(getRequiredProperty(prop, KEY_DATABASE_URL));
        nds.setDriverClassName(getRequiredProperty(prop, KEY_DATABASE_DRIVER));
        nds.setUsername(getProperty(prop, KEY_DATABASE_USERNAME));
        nds.setPassword(getProperty(prop, KEY_DATABASE_PASSWORD));
        setDataSource(nds);
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setDataSource(DataSource ds) {
        Config.dataSource = ds;
        init();
    }

    public static DataSource getDataSource() {
        if (Config.dataSource == null) {
            throw new IllegalStateException("A jdbc data source not configured");
        }
        return Config.dataSource;
    }

    public static ObjectContext getObjectContext() {
        return runtime.getContext();
    }

    private static void init() {
        if (inited) {
            return;
        }
        inited = true;

        log.info("init geonorge download..");

        if (dataSource == null) {
            log.info("missing data source. not good.");
        }

        DataDomain domain = runtime.getDataDomain();
        DataMap dataMap = domain.getDataMap("datamap");
        DataNode dataNode = domain.getDataNode("datanode");
        DbAdapter adapter = dataNode.getAdapter();

        DbMerger merger = new DbMerger();
        List<MergerToken> tokens = merger.createMergeTokens(dataNode, dataMap);
        List<AbstractToDbToken> toDbTokens = new ArrayList<AbstractToDbToken>();
        for (MergerToken token : tokens) {
            if (token.getDirection().isToModel()) {
                token = token.createReverse(adapter.mergerFactory());
            }
            if (token instanceof DropTableToDb) {
                continue;
            }
            if (token instanceof DropColumnToDb) {
                continue;
            }
            if (token instanceof AbstractToDbToken) {
                AbstractToDbToken dbToken = (AbstractToDbToken) token;
                toDbTokens.add(dbToken);
            }
        }

        ExecutingMergerContext mc = new ExecutingMergerContext(dataMap, dataNode);
        for (AbstractToDbToken token : toDbTokens) {
            token.execute(mc);
        }
        
        DbUpgrade.upgrade();

        log.info("inited geonorge download..");
    }

    private static String getProperty(Properties prop, String key) {
        String v = prop.getProperty(key);
        if (v == null) {
            v = System.getProperty(key);
        }
        if (v == null) {
            v = System.getenv(key);
        }
        return v;
    }

    private static String getRequiredProperty(Properties prop, String key) {
        String v = getProperty(prop, key);
        if (v == null) {
            throw new IllegalStateException("missing required configuration parameter: " + key);
        }
        return v;
    }

    private static int getProperty(Properties prop, String key, int defaultValue) {
        String v = getProperty(prop, key);
        return v == null ? defaultValue : Integer.parseInt(v);
    }
    
    private static String getProperty(Properties prop, String key, String defaultValue) {
        String v = getProperty(prop, key);
        return v == null ? defaultValue : v;
    }

    public DataSource getDataSource(DataNodeDescriptor desc) throws Exception {
        return dataSource;
    }
    
    public static String getCors() {
        return cors;
    }

}
