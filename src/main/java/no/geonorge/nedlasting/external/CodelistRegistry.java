package no.geonorge.nedlasting.external;

import com.fasterxml.jackson.databind.JsonNode;
import no.geonorge.nedlasting.external.data.RegisterItem;
import no.geonorge.nedlasting.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Grotan_Bjorn_Ove on 11.01.2019.
 */
public class CodelistRegistry {
    private String areaCategoryUrl = "https://register.geonorge.no/api/metadata-kodelister/geografisk-distribusjonsinndeling.json";
    private String crsUrl = "https://register.geonorge.no/api/register/epsg-koder.json";
    private String vectorFormatsUrl = "https://register.geonorge.no/api/metadata-kodelister/vektorformater.json";

    /**
     * Fetch codelist items from Geonorge Codelist Registry
     * @return List<RegisterItem>
     * @throws IOException
     */
    public List<RegisterItem> getVectorFormats() throws IOException {
        return getRegisterItems(vectorFormatsUrl);
    }

    public List<RegisterItem> getCrsCodes() throws IOException {
        return getRegisterItems(crsUrl);
    }

    public List<RegisterItem> getAreaCategories() throws IOException {
        return getRegisterItems(areaCategoryUrl);
    }
    private List<RegisterItem> getRegisterItems(String url) throws IOException {
        List<RegisterItem> items = new ArrayList<RegisterItem>();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = httpGET(url);
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode containedItems = jsonNode.get("containeditems");
        for (JsonNode containedItem:containedItems) {
            RegisterItem regItem = new RegisterItem();
            regItem.setId(containedItem.get("id").asText());
            regItem.setLabel(containedItem.get("label").asText());
            regItem.setDescription(containedItem.get("description").asText());
            items.add(regItem);
        }
        return items;
    }

    private String httpGET(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        if (conn.getResponseCode() != 200) {
            throw new IOException(
                    "GET got " + conn.getResponseCode() + " " + conn.getResponseMessage() + " from " + url);
        }
        String s = IOUtils.toString(conn.getInputStream());
        return s;
    }
}
