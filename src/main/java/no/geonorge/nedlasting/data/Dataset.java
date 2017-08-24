package no.geonorge.nedlasting.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import no.geonorge.nedlasting.data.auto._Dataset;
import no.geonorge.nedlasting.data.client.Area;
import no.geonorge.nedlasting.data.client.Format;
import no.geonorge.nedlasting.data.client.Projection;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.CapabilitiesType;

public class Dataset extends _Dataset {

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

    public CapabilitiesType getCapabilities() {
        CapabilitiesType ct = new CapabilitiesType();
        ct.setSupportsAreaSelection(isSupportsAreaSelection());
        ct.setSupportsFormatSelection(isSupportsFormatSelection());
        ct.setSupportsPolygonSelection(isSupportsPolygonSelection());
        ct.setSupportsProjectionSelection(isSupportsProjectionSelection());
        return ct;
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
            projectionTypes.add(file.getProjection());
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
            areaType.addProjection(file.getProjection());
        }

        List<Area> areas = new ArrayList<>(areaTypeByAreaKey.values());
        return Collections.unmodifiableList(areas);
    }

}
