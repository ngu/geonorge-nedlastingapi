package no.geonorge.rest;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;

import com.google.gson.Gson;

import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.Dataset;
import no.geonorge.nedlasting.data.DatasetFile;
import no.geonorge.nedlasting.data.client.Area;
import no.geonorge.nedlasting.data.client.Capabilities;
import no.geonorge.nedlasting.data.client.File;
import no.geonorge.nedlasting.data.client.Format;
import no.geonorge.nedlasting.data.client.Order;
import no.geonorge.nedlasting.data.client.OrderLine;
import no.geonorge.nedlasting.data.client.OrderReceipt;
import no.geonorge.nedlasting.data.client.Projection;
import no.geonorge.nedlasting.utils.SHA1Helper;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.CanDownloadResponseType;




/**
 * This REST api implements the Norway Digital (Geonorge) Download API
 * For more information: http://nedlasting.geonorge.no/Help
 */

@Path("api")
public class DownloadService {
    
    @Context
    UriInfo uri;
    
    private static final Logger log = Logger.getLogger(DownloadService.class.getName());
    
    private String getUrlPrefix() {
        if (uri == null) {
            return "";
        }
        return uri.getBaseUri().toString() + "api/";
    }

    @GET
    @Path("status")
    @Produces(MediaType.TEXT_PLAIN)
    public Response returnStatus() throws IOException {
        try {
            ObjectContext ctxt = Config.getObjectContext();
            SelectQuery query = new SelectQuery(Dataset.class);
            query.setFetchLimit(1);
            ctxt.performQuery(query);
        } catch (RuntimeException e) {
            return Response.serverError().build();
        }

        return Response.ok("ok", MediaType.TEXT_PLAIN).build();
    }

    @GET
    @Path("capabilities/{metadataUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnCapabilities(@PathParam("metadataUuid") String metadataUuid) throws IOException {
        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = Dataset.forMetadataUUID(ctxt, metadataUuid);
        if (dataset == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Capabilities ct = dataset.getCapabilities(getUrlPrefix());
        String json = new Gson().toJson(ct);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
	
	/**
	 * 
	 * @param metadataUuid
	 * @return json of valid file formats of a given metadataUuid
	 * @throws Exception
	 */
    @GET
    @Path("v2/codelists/format/{metadataUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnFormats(@PathParam("metadataUuid") String metadataUuid) throws IOException {
        /*
         * http://nedlasting.geonorge.no/Help/Api/GET-api-codelists-format-
         * metadataUuid
         */
        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = Dataset.forMetadataUUID(ctxt, metadataUuid);
        if (dataset == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Collection<Format> formats = dataset.getFormats();
        Gson gson = new Gson();
        String json = gson.toJson(formats);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * 
     * @param metadataUuid
     * @return json of valid areas of a given metadataUuid
     * @throws Exception
     */
    @GET
    @Path("v2/codelists/area/{metadataUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnAreas(@PathParam("metadataUuid") String metadataUuid) throws IOException {
        /*
         * http://nedlasting.geonorge.no/Help/Api/GET-api-codelists-area-
         * metadataUuid
         */
        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = Dataset.forMetadataUUID(ctxt, metadataUuid);
        if (dataset == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Area> areas = dataset.getAreas();
        String json = new Gson().toJson(areas);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * 
     * @param metadataUuid
     * @return json of valid projections of a given metadataUuid
     * @throws Exception
     */
    @GET
    @Path("v2/codelists/projection/{metadataUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnProjections(@PathParam("metadataUuid") String metadataUuid) throws IOException {
        /*
         * http://nedlasting.geonorge.no/Help/Api/GET-api-codelists-projection-
         * metadataUuid
         */
        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = Dataset.forMetadataUUID(ctxt, metadataUuid);
        if (dataset == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Collection<Projection> projections = dataset.getProjections();
        String json = new Gson().toJson(projections);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
	
	/**
	 * 
	 * @param metadataUuid
	 * @return json of valid projections of a given metadataUuid
	 * @throws Exception
	 */
	@POST
	@Path("v2/can-download")
	@Produces(MediaType.APPLICATION_JSON)	
	@Consumes(MediaType.APPLICATION_JSON)
	public String canDownload(String jsonRequest) throws Exception {	
	    log.info("can-download request: " + jsonRequest);
		/* Sample JSON HTTP-POST
		 * {"metadataUuid":"73f863ba-628f-48af-b7fa-30d3ab331b8d","coordinates":"344754 7272921 404330 7187619 304134 7156477 344754 7272921","coordinateSystem":"32633"}
		 */
		boolean is_valid_area = false;
		// FIXME: Parse JSON-string and generate a Polygon object		
		// FIXME: Calculate Polygon area and validate against dataset configuration. 
		// Possibly even a system default in /etc/geonorge.conf if always enabled.		
		CanDownloadResponseType canDownload = new CanDownloadResponseType();
		canDownload.setCanDownload(is_valid_area);
		Gson gson = new Gson();
		String json = gson.toJson(canDownload);		
		return json;
		
	}
	/**
	 * 
	 * @param metadataUuid
	 * @return json of valid projections of a given metadataUuid
	 * @throws Exception
	 */
	@GET
	@Path("v2/order/{referenceNumber}")
	@Produces(MediaType.APPLICATION_JSON)		
	public String getOrderInfo(@PathParam("referenceNumber") String referenceNumber) throws Exception {			
		// FIXME: Complete the method with potential lookups in orderdetail tables in rdbms
		// JSON structure is {"referenceNumber": , "files": [], "email": "foo@bar.baz", "orderDate": "YYYY-MM-DDThh:mm:ss.ms"}
		return "{}";		
	}

    /**
     * 
     *
     * @return String json with reference to order
     * @throws Exception
     */
    @POST
    @Path("v2/order")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response orderDownload(String jsonRequest) throws IOException {
        /* https://nedlasting.geonorge.no/Help/Api/POST-api-v2-order */
        Order order = new Gson().fromJson(jsonRequest, Order.class);
        OrderReceipt orderReceipt = new OrderReceipt();
        orderReceipt.setReferenceNumber(UUID.randomUUID().toString());

        ObjectContext ctxt = Config.getObjectContext();
        for (OrderLine orderLine : order.getOrderLines()) {
            for (DatasetFile datasetFile : DatasetFile.findForOrderLine(ctxt, orderLine)) {
                File file = new File();
                file.setFileId(SHA1Helper.sha1String(datasetFile.getUrl()));
                file.setDownloadUrl(datasetFile.getUrl());
                file.setName(datasetFile.getFileName());
                file.setMetadataUuid(orderLine.getMetadataUuid());
                file.setMetadataName(datasetFile.getDataset().getTitle());
                file.setFormat(datasetFile.getFormatName());
                file.setProjection(datasetFile.getProjection().getSrid().toString());
                file.setProjectionName(datasetFile.getProjection().getName());
                file.setArea(datasetFile.getAreaCode());
                file.setAreaName(datasetFile.getAreaName());
                file.setStatus("ReadyForDownload");
                orderReceipt.addFile(file);
            }
        }

        String json = new Gson().toJson(orderReceipt);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

}
