package no.geonorge.nedlasting.data.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import no.geonorge.nedlasting.data.DatasetFile;

/**
 * Class _Projection was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Projection extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String CODESPACE_PROPERTY = "codespace";
    public static final String NAME_PROPERTY = "name";
    public static final String SRID_PROPERTY = "srid";
    public static final String FILES_PROPERTY = "files";

    public static final String SRID_PK_COLUMN = "SRID";

    public static final Property<String> CODESPACE = Property.create("codespace", String.class);
    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<Integer> SRID = Property.create("srid", Integer.class);
    public static final Property<List<DatasetFile>> FILES = Property.create("files", List.class);

    protected String codespace;
    protected String name;
    protected Integer srid;

    protected Object files;

    public void setCodespace(String codespace) {
        beforePropertyWrite("codespace", this.codespace, codespace);
        this.codespace = codespace;
    }

    public String getCodespace() {
        beforePropertyRead("codespace");
        return this.codespace;
    }

    public void setName(String name) {
        beforePropertyWrite("name", this.name, name);
        this.name = name;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
    }

    public void setSrid(Integer srid) {
        beforePropertyWrite("srid", this.srid, srid);
        this.srid = srid;
    }

    public Integer getSrid() {
        beforePropertyRead("srid");
        return this.srid;
    }

    public void addToFiles(DatasetFile obj) {
        addToManyTarget("files", obj, true);
    }

    public void removeFromFiles(DatasetFile obj) {
        removeToManyTarget("files", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<DatasetFile> getFiles() {
        return (List<DatasetFile>)readProperty("files");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "codespace":
                return this.codespace;
            case "name":
                return this.name;
            case "srid":
                return this.srid;
            case "files":
                return this.files;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "codespace":
                this.codespace = (String)val;
                break;
            case "name":
                this.name = (String)val;
                break;
            case "srid":
                this.srid = (Integer)val;
                break;
            case "files":
                this.files = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.codespace);
        out.writeObject(this.name);
        out.writeObject(this.srid);
        out.writeObject(this.files);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.codespace = (String)in.readObject();
        this.name = (String)in.readObject();
        this.srid = (Integer)in.readObject();
        this.files = in.readObject();
    }

}
