package no.geonorge.nedlasting.dao;

import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.CapabilitiesType;

public interface CapabilitiesDao {
		
	public CapabilitiesType getCapabilities(String metadatauuid);

}
