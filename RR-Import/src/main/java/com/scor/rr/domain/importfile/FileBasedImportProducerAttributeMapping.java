package com.scor.rr.domain.importfile;

import javax.persistence.*;

@Entity
@Table(name = "FileBasedImportProducerAttributeMapping", schema = "dbo")
public class FileBasedImportProducerAttributeMapping {
    private int id;
    private int metadataHeaderSectionId;
    private int fileBasedImportProducerId;

    @Id
    @Basic
    @Column(name = "FileBasedImportProducerAttributeMappingId", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MetadataHeaderSectionId", nullable = true, length = 200)
    public int getMetadataHeaderSectionId() {
        return metadataHeaderSectionId;
    }

    public void setMetadataHeaderSectionId(int metadataHeaderSectionId) {
        this.metadataHeaderSectionId = metadataHeaderSectionId;
    }

    @Basic
    @Column(name = "FileBasedImportProducerId", nullable = true, length = 200)
    public int getFileBasedImportProducerId() {
        return fileBasedImportProducerId;
    }

    public void setFileBasedImportProducerId(int fileBasedImportProducerId) {
        this.fileBasedImportProducerId = fileBasedImportProducerId;
    }
}
