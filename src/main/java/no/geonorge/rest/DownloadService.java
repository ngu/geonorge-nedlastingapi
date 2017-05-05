package no.geonorge.rest;

import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;




/**
 * This REST api implements the Norway Digital (Geonorge) Download API
 * For more information: http://nedlasting.geonorge.no/Help
 */

@Path("api")
public class DownloadService {
		
	@GET
	@Path("capabilities/{metadataUuid}")
	@Produces(MediaType.APPLICATION_JSON)	
	public String returnCapabilities(@PathParam("metadataUuid") String metadataUuid) throws Exception {
		CapabilitiesType ct = new CapabilitiesType();	
		// FIXME: Fetch capapbilities for dataset
		Gson gson = new Gson();
		String json = gson.toJson(ct);
		return json;		
	}

	
	/**
	 * 
	 * @param metadataUuid
	 * @return json of valid file formats of a given metadataUuid
	 * @throws Exception
	 */
	@GET
	@Path("codelists/format/{metadataUuid}")
	@Produces(MediaType.APPLICATION_JSON)	
	public String returnFormats(@PathParam("metadataUuid") String metadataUuid) throws Exception {	
		/* http://nedlasting.geonorge.no/Help/Api/GET-api-codelists-format-metadataUuid */		 
		List<FormatType> formats = new ArrayList<FormatType>();		
		Gson gson = new Gson();
		String json = gson.toJson(formats);		
		return json;
	}
	
	/**
	 * 
	 * @param metadataUuid
	 * @return json of valid areas of a given metadataUuid
	 * @throws Exception
	 */
	@GET
	@Path("codelists/area/{metadataUuid}")
	@Produces(MediaType.APPLICATION_JSON)	
	public String returnAreas(@PathParam("metadataUuid") String metadataUuid) throws Exception {	
		/* http://nedlasting.geonorge.no/Help/Api/GET-api-codelists-area-metadataUuid */		 
		List<AreaType> areas = new ArrayList<AreaType>();	
		Gson gson = new Gson();
		String json = gson.toJson(areas);
		System.out.println("json = " + json);
		return json;
	}
	
	
	/**
	 * 
	 * @param metadataUuid
	 * @return json of valid projections of a given metadataUuid
	 * @throws Exception
	 */
	@GET
	@Path("codelists/projection/{metadataUuid}")
	@Produces(MediaType.APPLICATION_JSON)	
	public String returnProjections(@PathParam("metadataUuid") String metadataUuid) throws Exception {	
		/* http://nedlasting.geonorge.no/Help/Api/GET-api-codelists-projection-metadataUuid
		 */
		List<ProjectionType> projections = new ArrayList<ProjectionType>();
		Gson gson = new Gson();
		String json = gson.toJson(projections);
		System.out.println("json = " + json);
		return json;
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
