package no.geonorge.nedlasting.data.client;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Area {

    private String code;
    private String type;
    private String name;

    private Collection<Projection> projections;
    private Collection<FormatType> formats;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addProjection(Projection projection) {
        if (projections == null) {
            projections = new HashSet<>();
        }
        projections.add(projection);
    }

    public Collection<Projection> getProjections() {
        if (projections == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(projections);
    }

    public void addFormat(FormatType format) {
        if (formats == null) {
            formats = new HashSet<>();
        }
        formats.add(format);
    }

    public Collection<FormatType> getFormats() {
        if (formats == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(formats);
    }

}
