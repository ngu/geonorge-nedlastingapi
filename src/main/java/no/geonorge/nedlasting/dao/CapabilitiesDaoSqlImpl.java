package no.geonorge.nedlasting.dao;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.JAXBElement;

import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.*;
import no.kxml.rest._1.LinkListeType;

public class CapabilitiesDaoSqlImpl implements CapabilitiesDao {

	Connection connection = null;
	PreparedStatement stmt = null;
	ResultSet resultSet = null;
	String schema = null;
	
	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}
	
	
	public CapabilitiesType getCapabilities(String metadatauuid) {
		CapabilitiesType cap = new CapabilitiesType();
		Properties prop = new Properties();
		InputStream reader = null;
		try {
			reader = new FileInputStream("/etc/geonorge/geonorge.conf");
			prop.load(reader);						
			this.schema = prop.getProperty("database.schema");			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException np) {
			np.printStackTrace();
		}
		
		// Query database for areas,polygon clipping,format select and projection selection.
		try {
			String query = "SELECT * FROM ?.dataset WHERE metadatauuid=?";
			connection = getConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(0, schema);
			stmt.setString(1, metadatauuid);
			resultSet = stmt.executeQuery();
			int numFound = 0;
			while (resultSet.next()) {
				numFound++;
				/* Oracle do not support boolean datatype. Use SQL string value string instead */
				if (resultSet.getString("supportsareaselection").equalsIgnoreCase("true")) {
					cap.setSupportsAreaSelection(true);
				} else {
					cap.setSupportsAreaSelection(false);
				}
				if (resultSet.getString("supportsformatselection").equalsIgnoreCase("true")) {
					cap.setSupportsFormatSelection(true);
				} else {
					cap.setSupportsFormatSelection(false);
				}
				if (resultSet.getString("supportspolygonselection").equalsIgnoreCase("true")) {
					 cap.setSupportsPolygonSelection(true);
				} else {
					cap.setSupportsPolygonSelection(false);
				}
				if (resultSet.getString("supportsprojectionselection").equalsIgnoreCase("true")) {
					 cap.setSupportsProjectionSelection(true);
				} else {
					cap.setSupportsProjectionSelection(false);
				}
				cap.setMapSelectionLayer(resultSet.getString("mapselectionlayer"));
				// FIXME: Get links
				
			}
			System.out.println("Found " + numFound + " datasets matching uuid=" + metadatauuid);
		} catch (SQLException e) {
			
		} finally {
			try {
				resultSet.close();
			} catch (SQLException ignored) {}
			try {
				stmt.close();
			} catch (SQLException ignored) {}	
			try {
				connection.close();
			} catch (SQLException ignored) {}						
		}
		return cap;
	}

	

}
