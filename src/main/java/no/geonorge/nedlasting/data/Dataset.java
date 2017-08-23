package no.geonorge.nedlasting.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import no.geonorge.nedlasting.data.auto._Dataset;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.CapabilitiesType;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.FormatType;
import no.geonorge.skjema.sosi.tjenestespesifikasjon.nedlastingapi._2.ProjectionType;

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

    public List<FormatType> getFormatTypes() {
        Set<String> formats = new HashSet<String>();
        for (DatasetFile file : getFiles()) {
            String format = file.getFormat();
            if (format == null) {
                continue;
            }
            formats.add(format);
        }
        List<FormatType> formatTypes = new ArrayList<FormatType>();
        for (String format : formats) {
            FormatType formatType = new FormatType();
            formatType.setName(format);
            formatTypes.add(formatType);
        }
        return Collections.unmodifiableList(formatTypes);
    }

    public List<ProjectionType> getProjectionTypes() {
        Set<Integer> srids = new HashSet<Integer>();
        for (DatasetFile file : getFiles()) {
            srids.add(file.getSrid());
        }
        List<ProjectionType> projectionTypes = new ArrayList<ProjectionType>();
        for (Integer srid : srids) {
            ProjectionType projectonType = new ProjectionType();
            projectonType.setCode(srid.toString());
            projectionTypes.add(projectonType);
        }
        return Collections.unmodifiableList(projectionTypes);
    }

}
