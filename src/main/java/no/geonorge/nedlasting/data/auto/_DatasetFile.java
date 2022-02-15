package no.geonorge.nedlasting.data.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import no.geonorge.nedlasting.data.Dataset;
import no.geonorge.nedlasting.data.Projection;

/**
 * Class _DatasetFile was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _DatasetFile extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String AREA_CODE_PROPERTY = "areaCode";
    public static final String AREA_NAME_PROPERTY = "areaName";
    public static final String AREA_TYPE_PROPERTY = "areaType";
    public static final String FILE_DATE_PROPERTY = "fileDate";
    public static final String FILE_ID_PROPERTY = "fileId";
    public static final String FILE_NAME_PROPERTY = "fileName";
    public static final String FORMAT_NAME_PROPERTY = "formatName";
    public static final String FORMAT_VERSION_PROPERTY = "formatVersion";
    public static final String URL_PROPERTY = "url";
    public static final String DATASET_PROPERTY = "dataset";
    public static final String PROJECTION_PROPERTY = "projection";

    public static final String DATASET_ID_PK_COLUMN = "DATASET_ID";
    public static final String FILE_ID_PK_COLUMN = "FILE_ID";

    public static final Property<String> AREA_CODE = Property.create("areaCode", String.class);
    public static final Property<String> AREA_NAME = Property.create("areaName", String.class);
    public static final Property<String> AREA_TYPE = Property.create("areaType", String.class);
    public static final Property<Date> FILE_DATE = Property.create("fileDate", Date.class);
    public static final Property<String> FILE_ID = Property.create("fileId", String.class);
    public static final Property<String> FILE_NAME = Property.create("fileName", String.class);
    public static final Property<String> FORMAT_NAME = Property.create("formatName", String.class);
    public static final Property<String> FORMAT_VERSION = Property.create("formatVersion", String.class);
    public static final Property<String> URL = Property.create("url", String.class);
    public static final Property<Dataset> DATASET = Property.create("dataset", Dataset.class);
    public static final Property<Projection> PROJECTION = Property.create("projection", Projection.class);

    protected String areaCode;
    protected String areaName;
    protected String areaType;
    protected Date fileDate;
    protected String fileId;
    protected String fileName;
    protected String formatName;
    protected String formatVersion;
    protected String url;

    protected Object dataset;
    protected Object projection;

    public void setAreaCode(String areaCode) {
        beforePropertyWrite("areaCode", this.areaCode, areaCode);
        this.areaCode = areaCode;
    }

    public String getAreaCode() {
        beforePropertyRead("areaCode");
        return this.areaCode;
    }

    public void setAreaName(String areaName) {
        beforePropertyWrite("areaName", this.areaName, areaName);
        this.areaName = areaName;
    }

    public String getAreaName() {
        beforePropertyRead("areaName");
        return this.areaName;
    }

    public void setAreaType(String areaType) {
        beforePropertyWrite("areaType", this.areaType, areaType);
        this.areaType = areaType;
    }

    public String getAreaType() {
        beforePropertyRead("areaType");
        return this.areaType;
    }

    public void setFileDate(Date fileDate) {
        beforePropertyWrite("fileDate", this.fileDate, fileDate);
        this.fileDate = fileDate;
    }

    public Date getFileDate() {
        beforePropertyRead("fileDate");
        return this.fileDate;
    }

    public void setFileId(String fileId) {
        beforePropertyWrite("fileId", this.fileId, fileId);
        this.fileId = fileId;
    }

    public String getFileId() {
        beforePropertyRead("fileId");
        return this.fileId;
    }

    public void setFileName(String fileName) {
        beforePropertyWrite("fileName", this.fileName, fileName);
        this.fileName = fileName;
    }

    public String getFileName() {
        beforePropertyRead("fileName");
        return this.fileName;
    }

    public void setFormatName(String formatName) {
        beforePropertyWrite("formatName", this.formatName, formatName);
        this.formatName = formatName;
    }

    public String getFormatName() {
        beforePropertyRead("formatName");
        return this.formatName;
    }

    public void setFormatVersion(String formatVersion) {
        beforePropertyWrite("formatVersion", this.formatVersion, formatVersion);
        this.formatVersion = formatVersion;
    }

    public String getFormatVersion() {
        beforePropertyRead("formatVersion");
        return this.formatVersion;
    }

    public void setUrl(String url) {
        beforePropertyWrite("url", this.url, url);
        this.url = url;
    }

    public String getUrl() {
        beforePropertyRead("url");
        return this.url;
    }

    public void setDataset(Dataset dataset) {
        setToOneTarget("dataset", dataset, true);
    }

    public Dataset getDataset() {
        return (Dataset)readProperty("dataset");
    }

    public void setProjection(Projection projection) {
        setToOneTarget("projection", projection, true);
    }

    public Projection getProjection() {
        return (Projection)readProperty("projection");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "areaCode":
                return this.areaCode;
            case "areaName":
                return this.areaName;
            case "areaType":
                return this.areaType;
            case "fileDate":
                return this.fileDate;
            case "fileId":
                return this.fileId;
            case "fileName":
                return this.fileName;
            case "formatName":
                return this.formatName;
            case "formatVersion":
                return this.formatVersion;
            case "url":
                return this.url;
            case "dataset":
                return this.dataset;
            case "projection":
                return this.projection;
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
            case "areaCode":
                this.areaCode = (String)val;
                break;
            case "areaName":
                this.areaName = (String)val;
                break;
            case "areaType":
                this.areaType = (String)val;
                break;
            case "fileDate":
                this.fileDate = (Date)val;
                break;
            case "fileId":
                this.fileId = (String)val;
                break;
            case "fileName":
                this.fileName = (String)val;
                break;
            case "formatName":
                this.formatName = (String)val;
                break;
            case "formatVersion":
                this.formatVersion = (String)val;
                break;
            case "url":
                this.url = (String)val;
                break;
            case "dataset":
                this.dataset = val;
                break;
            case "projection":
                this.projection = val;
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
        out.writeObject(this.areaCode);
        out.writeObject(this.areaName);
        out.writeObject(this.areaType);
        out.writeObject(this.fileDate);
        out.writeObject(this.fileId);
        out.writeObject(this.fileName);
        out.writeObject(this.formatName);
        out.writeObject(this.formatVersion);
        out.writeObject(this.url);
        out.writeObject(this.dataset);
        out.writeObject(this.projection);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.areaCode = (String)in.readObject();
        this.areaName = (String)in.readObject();
        this.areaType = (String)in.readObject();
        this.fileDate = (Date)in.readObject();
        this.fileId = (String)in.readObject();
        this.fileName = (String)in.readObject();
        this.formatName = (String)in.readObject();
        this.formatVersion = (String)in.readObject();
        this.url = (String)in.readObject();
        this.dataset = in.readObject();
        this.projection = in.readObject();
    }

}
