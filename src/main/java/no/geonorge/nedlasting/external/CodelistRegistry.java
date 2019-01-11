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
 * Wrapper class for Geonorge Codelist Registry
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
    public List<RegisterItem> getVectorFormats()  {
        return getRegisterItems(vectorFormatsUrl);
    }

    public List<RegisterItem> getCrsCodes()  {
        return getRegisterItems(crsUrl);
    }

    public List<RegisterItem> getAreaCategories()  {
        return getRegisterItems(areaCategoryUrl);
    }
    private List<RegisterItem> getRegisterItems(String url)  {
        List<RegisterItem> items = new ArrayList<RegisterItem>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = httpGET(url);
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode containedItems = jsonNode.get("containeditems"); // Register Items are found in containeditems
            for (JsonNode containedItem : containedItems) {
                RegisterItem regItem = new RegisterItem();
                regItem.setId(containedItem.get("id").asText());
                regItem.setLabel(containedItem.get("label").asText());
                JsonNode _description = containedItem.get("description");
                if (_description != null) { regItem.setDescription(_description.asText()); }
                JsonNode _codeValue = containedItem.get("codevalue");
                if (_codeValue != null) { regItem.setCodevalue(_codeValue.asText()); }
                JsonNode _epsgCode = containedItem.get("epsgcode");
                if (_epsgCode != null) { regItem.setCodevalue(_epsgCode.asText());}
                items.add(regItem);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
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
