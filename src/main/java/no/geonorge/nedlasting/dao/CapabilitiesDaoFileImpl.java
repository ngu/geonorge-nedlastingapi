package no.geonorge.nedlasting.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.CapabilitiesType;

public class CapabilitiesDaoFileImpl implements CapabilitiesDao {

	/**
	 * This file-based dao should find capabilities in property files
	 * on the form /etc/geonorge/{metadatauuid}.conf
	 */
	public CapabilitiesType getCapabilities(String metadatauuid) {
		CapabilitiesType cap = new CapabilitiesType();
		Properties prop = new Properties();
		InputStream reader = null;
		try {
			reader = new FileInputStream("/etc/geonorge/" + metadatauuid);
			prop.load(reader);						
			cap.setSupportsAreaSelection(prop.getProperty("supportsAreaSelection").equalsIgnoreCase("true"));
			cap.setSupportsFormatSelection(prop.getProperty("supportsFormatSelection").equalsIgnoreCase("true"));
			cap.setSupportsPolygonSelection(prop.getProperty("supportsPolygonSelection").equalsIgnoreCase("true"));
			cap.setSupportsProjectionSelection(prop.getProperty("supportsProjectionSelection").equalsIgnoreCase("true"));
			// Generate capabilities links
			
		} catch (IOException ie) {
			ie.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return cap;
	}

}
