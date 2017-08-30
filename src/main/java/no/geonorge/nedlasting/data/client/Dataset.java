package no.geonorge.nedlasting.data.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dataset {

    private String metadataUuid;
    private String title;
    private List<File> files;

    public String getMetadataUuid() {
        return metadataUuid;
    }

    public void setMetadataUuid(String metadataUuid) {
        this.metadataUuid = metadataUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<File> getFiles() {
        if (files == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(files);
    }

    public void addFile(File file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
    }

}
