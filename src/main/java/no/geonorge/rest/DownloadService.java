package no.geonorge.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;

import com.google.gson.Gson;

import no.geonorge.nedlasting.config.Config;
import no.geonorge.nedlasting.data.Dataset;
import no.geonorge.nedlasting.data.client.Area;
import no.geonorge.nedlasting.data.client.Format;
import no.geonorge.nedlasting.data.client.Projection;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.CanDownloadResponseType;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.CapabilitiesType;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.FileListe;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.FileType;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.OrderReceiptType;




/**
 * This REST api implements the Norway Digital (Geonorge) Download API
 * For more information: http://nedlasting.geonorge.no/Help
 */

@Path("api")
public class DownloadService {

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
        CapabilitiesType ct = dataset.getCapabilities();
        Gson gson = new Gson();
        String json = gson.toJson(ct);
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
	@Path("order")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String orderDownload(String jsonRequest) throws Exception {
		/* http://nedlasting.geonorge.no/Help/Api/POST-api-order */
		/**
		 * {
			"email": "foo@bar.bazo",
			"orderLines": [{
			  "areas": [{
				"code": "1622",
				"type": "kommune",
				"name": null
			  }],
			  "formats": [{
				"name": "SOSI",
				"version": null
			  }],
			  "metadataUuid": "a5c76d05-33bd-4a1d-b28b-81575092e468",
			  "coordinates": null,
			  "coordinatesystem": null,
			  "projections": [{
				"code": "25832",
				"name": null,
				"codespace": null
				}]
			  }]
		   }
		 */

		/*
		 * FIXME: complete the order and return a file list
		 */
     	OrderReceiptType order = new OrderReceiptType();
     	FileType noFiles = new FileType();
     	ArrayList<FileListe> files = new ArrayList<FileListe>();

		
     	// Convert OrderReceiptType to JSON
		Gson gson = new Gson();		
		return gson.toJson(order);
	}
	
	
}
