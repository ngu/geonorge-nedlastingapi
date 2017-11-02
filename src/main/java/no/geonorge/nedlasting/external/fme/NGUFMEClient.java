package no.geonorge.nedlasting.external.fme;

import java.io.IOException;
import java.util.Map;

import no.geonorge.nedlasting.data.client.Format;
import no.geonorge.nedlasting.data.client.Projection;

public class NGUFMEClient extends FMEClient {

    public static final String PARAMETER_TYPE_VALUE = "ngufme";

    public NGUFMEClient(String urlPrefix, String username, String password, String repository, String workspace) {
        super(urlPrefix, username, password, repository, workspace);
        configure();
    }

    public NGUFMEClient(Map<String, String> parameters) {
        super(parameters);
        configure();
    }

    private void configure() {
        setFMEProjectionName("ETRS89.UTM-32N", "25832");
        setFMEProjectionName("ETRS89.UTM-33N", "25833");
        setFMEProjectionName("ETRS89.UTM-35N", "25835");
        setFMEProjectionName("UTM84-32N", "32632");
        setFMEProjectionName("UTM84-33N", "32633");
        setFMEProjectionName("UTM84-35N", "32625");
        setFMEProjectionName("LL-WGS84", "4326");
    }

    @Override
    public String getProjectionParameterName() {
        return "LeveranseKoordSystem";
    }

    @Override
    public String getFormatParameterName() {
        return "Dataformat";
    }

    @Override
    public String getCoordinatesParameterName() {
        return "koordinatListe";
    }

}
