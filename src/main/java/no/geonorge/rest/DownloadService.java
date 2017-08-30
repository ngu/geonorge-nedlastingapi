package no.geonorge.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import no.geonorge.nedlasting.data.DownloadItem;
import no.geonorge.nedlasting.data.DownloadOrder;
import no.geonorge.nedlasting.data.client.Area;
import no.geonorge.nedlasting.data.client.Capabilities;
import no.geonorge.nedlasting.data.client.File;
import no.geonorge.nedlasting.data.client.Format;
import no.geonorge.nedlasting.data.client.Order;
import no.geonorge.nedlasting.data.client.OrderLine;
import no.geonorge.nedlasting.data.client.OrderReceipt;
import no.geonorge.nedlasting.data.client.Projection;
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
        log.info("order request: " + jsonRequest);
        Order order = new Gson().fromJson(jsonRequest, Order.class);
        OrderReceipt orderReceipt = new OrderReceipt();
        orderReceipt.setReferenceNumber(UUID.randomUUID().toString());

        ObjectContext ctxt = Config.getObjectContext();

        DownloadOrder downloadOrder = ctxt.newObject(DownloadOrder.class);
        downloadOrder.setEmail(order.getEmail());
        downloadOrder.setReferenceNumber(orderReceipt.getReferenceNumber());
        downloadOrder.setStartTime(new Date());

        for (OrderLine orderLine : order.getOrderLines()) {
            for (DatasetFile datasetFile : DatasetFile.findForOrderLine(ctxt, orderLine)) {
                File file = datasetFile.forClient();
                file.setStatus("ReadyForDownload");
                orderReceipt.addFile(file);

                DownloadItem downloadItem = ctxt.newObject(DownloadItem.class);
                downloadItem.setSrid(datasetFile.getProjection().getSrid());
                downloadItem.setUrl(datasetFile.getUrl());
                downloadOrder.addToItems(downloadItem);
            }
        }

        ctxt.commitChanges();

        String json = new Gson().toJson(orderReceipt);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("internal/dataset")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDatasets() {
        ObjectContext ctxt = Config.getObjectContext();
        List<no.geonorge.nedlasting.data.client.Dataset> datasetViews = new ArrayList<>();
        for (Dataset dataset : Dataset.getAll(ctxt)) {
            datasetViews.add(dataset.forClientWithoutFiles());
        }
        String json = new Gson().toJson(datasetViews);
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("internal/dataset/{metadataUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataset(@PathParam("metadataUuid") String metadataUuid) {
        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = Dataset.forMetadataUUID(ctxt, metadataUuid);
        if (dataset == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String json = new Gson().toJson(dataset.forClient());
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Path("internal/dataset/{metadataUuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putDataset(@PathParam("metadataUuid") String metadataUuid, String jsonRequest) {
        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = Dataset.forMetadataUUID(ctxt, metadataUuid);
        if (dataset == null) {
            dataset = ctxt.newObject(Dataset.class);
            dataset.setMetadataUuid(metadataUuid);
        }

        no.geonorge.nedlasting.data.client.Dataset requestDataset = new Gson().fromJson(jsonRequest,
                no.geonorge.nedlasting.data.client.Dataset.class);
        dataset.setTitle(requestDataset.getTitle());

        if (!requestDataset.ignoreFiles()) {
            Set<String> restFileIds = new HashSet<>(dataset.getFileIds());
            for (File file : requestDataset.getFiles()) {
                restFileIds.remove(file.getFileId());
                DatasetFile datasetFile = dataset.getFile(file.getFileId());
                if (datasetFile == null) {
                    datasetFile = ctxt.newObject(DatasetFile.class);
                    datasetFile.setFileId(file.getFileId());
                    dataset.addToFiles(datasetFile);
                }
                datasetFile.setUrl(file.getDownloadUrl());
                datasetFile.setAreaCode(file.getArea());
                datasetFile.setAreaName(file.getAreaName());
                datasetFile.setProjection(no.geonorge.nedlasting.data.Projection.getForSrid(ctxt,
                        Integer.parseInt(file.getProjection())));
                datasetFile.setFormatName(file.getFormat());
                datasetFile.setFileName(file.getName());
            }
            for (String fileId : restFileIds) {
                DatasetFile file = dataset.getFile(fileId);
                if (file != null) {
                    ctxt.deleteObject(file);
                }
            }
        }

        ctxt.commitChanges();
    }

    @POST
    @Path("internal/dataset/{metadataUuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postDataset(@PathParam("metadataUuid") String metadataUuid, String jsonRequest) {
        putDataset(metadataUuid, jsonRequest);
    }

    @DELETE
    @Path("internal/dataset/{metadataUuid}")
    public Response deleteDataset(@PathParam("metadataUuid") String metadataUuid) {
        ObjectContext ctxt = Config.getObjectContext();
        Dataset dataset = Dataset.forMetadataUUID(ctxt, metadataUuid);
        if (dataset == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ctxt.deleteObject(dataset);
        ctxt.commitChanges();
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
}
