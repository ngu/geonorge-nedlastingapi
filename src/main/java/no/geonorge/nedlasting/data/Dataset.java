package no.geonorge.nedlasting.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import no.geonorge.nedlasting.data.auto._Dataset;
import no.geonorge.nedlasting.data.client.Area;
import no.geonorge.nedlasting.data.client.Capabilities;
import no.geonorge.nedlasting.data.client.Format;
import no.geonorge.nedlasting.data.client.Link;
import no.geonorge.nedlasting.data.client.Projection;

public class Dataset extends _Dataset {

    public Integer getDatasetId() {
        return Cayenne.intPKForObject(this);
    }

    public static List<Dataset> getAll(ObjectContext ctxt) {
        SelectQuery query = new SelectQuery(Dataset.class);
        @SuppressWarnings("unchecked")
        List<Dataset> dataset = ctxt.performQuery(query);
        return dataset;
    }

    public static Dataset forMetadataUUID(ObjectContext ctxt, String metadataUuid) {
        Expression qual = ExpressionFactory.matchExp(METADATA_UUID_PROPERTY, metadataUuid);
        SelectQuery query = new SelectQuery(Dataset.class, qual);
        @SuppressWarnings("unchecked")
        List<Dataset> dataset = ctxt.performQuery(query);
        if (dataset.isEmpty()) {
            return null;
        }
        if (dataset.size() > 1) {
            throw new IllegalStateException("" + dataset.size() + "datasets with metadata uuid " + metadataUuid);
        }
        return dataset.get(0);
    }

    public Capabilities getCapabilities(String urlPrefix) {
        Capabilities ct = new Capabilities();
        ct.setSupportsAreaSelection(isSupportsAreaSelection());
        ct.setSupportsFormatSelection(isSupportsFormatSelection());
        ct.setSupportsPolygonSelection(isSupportsPolygonSelection());
        ct.setSupportsProjectionSelection(isSupportsProjectionSelection());

        String p = urlPrefix;
        String u = getMetadataUuid();
        ct.addLink(new Link("http://rel.geonorge.no/download/projection", p + "v2/codelists/projection/" + u));
        ct.addLink(new Link("http://rel.geonorge.no/download/format", p + "v2/codelists/format/" + u));
        ct.addLink(new Link("http://rel.geonorge.no/download/area", p + "v2/codelists/area/" + u));
        ct.addLink(new Link("http://rel.geonorge.no/download/order", p + "v2/order"));
        ct.addLink(new Link("self", p + "capabilities/" + u));
        ct.addLink(new Link("http://rel.geonorge.no/download/can-download", p + "v2/can-download"));

        return ct;
    }

    public no.geonorge.nedlasting.data.client.Dataset forClient() {
        no.geonorge.nedlasting.data.client.Dataset d = new no.geonorge.nedlasting.data.client.Dataset();
        d.setMetadataUuid(getMetadataUuid());
        d.setTitle(getTitle());
        for (DatasetFile file : getFiles()) {
            d.addFile(file.forClient());
        }
        return d;
    }

    public no.geonorge.nedlasting.data.client.Dataset forClientWithoutFiles() {
        no.geonorge.nedlasting.data.client.Dataset d = new no.geonorge.nedlasting.data.client.Dataset();
        d.setMetadataUuid(getMetadataUuid());
        d.setTitle(getTitle());
        return d;
    }

    public Collection<Format> getFormats() {
        Set<Format> formatTypes = new HashSet<>();
        for (DatasetFile file : getFiles()) {
            formatTypes.add(file.getFormat());
        }
        return Collections.unmodifiableCollection(formatTypes);
    }

    public Collection<Projection> getProjections() {
        Set<Projection> projectionTypes = new HashSet<>();
        for (DatasetFile file : getFiles()) {
            projectionTypes.add(file.getProjection().forClient());
        }
        return Collections.unmodifiableCollection(projectionTypes);
    }

    public List<Area> getAreas() {
        Map<String, Area> areaTypeByAreaKey = new HashMap<>();
        for (DatasetFile file : getFiles()) {
            String areaKey = file.getAreaKey();
            Area areaType = areaTypeByAreaKey.get(areaKey);
            if (areaType == null) {
                areaType = new Area();
                areaType.setCode(file.getAreaCode());
                areaType.setType(file.getAreaType());
                areaType.setName(file.getAreaName());
                areaTypeByAreaKey.put(areaKey, areaType);
            }
            areaType.addFormat(file.getFormat());
            areaType.addProjection(file.getProjection().forClient());
        }

        List<Area> areas = new ArrayList<>(areaTypeByAreaKey.values());
        return Collections.unmodifiableList(areas);
    }

    public DatasetFile getFile(String fileId) {
        if (fileId == null) {
            return null;
        }
        if (getObjectId().isTemporary()) {
            for (DatasetFile file : getFiles()) {
                if (fileId.equals(file.getFileId())) {
                    return file;
                }
            }
            return null;
        }
        Map<String, Object> pk = new HashMap<>(2);
        pk.put(DatasetFile.DATASET_ID_PK_COLUMN, getDatasetId());
        pk.put(DatasetFile.FILE_ID_PK_COLUMN, fileId);
        return Cayenne.objectForPK(getObjectContext(), DatasetFile.class, pk);
    }
    
    public Set<String> getFileIds() {
        Set<String> fileIds = new HashSet<>();
        for (DatasetFile file : getFiles()) {
            fileIds.add(file.getFileId());
        }
        return Collections.unmodifiableSet(fileIds);
    }

}
