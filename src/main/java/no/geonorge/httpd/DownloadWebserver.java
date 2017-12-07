package no.geonorge.httpd;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.ForwardedRequestCustomizer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import no.geonorge.nedlasting.config.Config;

public class DownloadWebserver {

    /**
     * Geonorge Download Embedded Webserver running Jetty
     *
     */
    public static void main(String[] args) throws Exception {
        Config.readConfiguration();

        Server server = new Server();
        HandlerCollection handlers = new HandlerCollection();
        ServletContextHandler context = new ServletContextHandler();
        ResourceConfig pconfig = new ResourceConfig();
        pconfig.packages("no.geonorge.rest"); // Tell Jersey where to find our
                                              // Annotated REST services

        // Add CORS-filter
        FilterHolder cors = context.addFilter(CrossOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, Config.getCors());
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
        
        // Authentication for /api/internal
        context.addFilter(AuthenticationFilter.class, "/api/internal/*",
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));

        ServletHolder publicRestApiServlet = new ServletHolder(new ServletContainer(pconfig));
        publicRestApiServlet.setInitOrder(1);
        context.addServlet(publicRestApiServlet, "/*");
        
        context.setBaseResource(Resource.newClassPathResource("/webroot"));
        context.addServlet(DefaultServlet.class, "/api/docs/*");
        
        HttpConfiguration config = new HttpConfiguration();
        config.addCustomizer(new ForwardedRequestCustomizer());
        HttpConnectionFactory http = new HttpConnectionFactory(config);
        ServerConnector httpConnector = new ServerConnector(server, http);
        httpConnector.setPort(Config.getServerPort());
        server.addConnector(httpConnector);
        handlers.addHandler(context);
        server.setHandler(handlers);

        server.start();
        server.join();
    }

}
