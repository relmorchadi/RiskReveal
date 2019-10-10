package com.scor.rr.domain.importFile;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "FileBasedImportProducer", schema = "dbo", catalog = "RiskReveal")
public class FileBasedImportProducer {
    private int id;
    private String lossTableHeaderProducer;
    private String lossTableHeaderFormat;
    private String fileFormatVersion;

    @Id
    @Basic
    @Column(name = "FileBasedImportProducerId", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "LossTableHeaderProducer", nullable = true, length = 200)
    public String getLossTableHeaderProducer() {
        return lossTableHeaderProducer;
    }

    public void setLossTableHeaderProducer(String lossTableHeaderProducer) {
        this.lossTableHeaderProducer = lossTableHeaderProducer;
    }

    @Basic
    @Column(name = "LossTableHeaderFormat", nullable = true, length = 200)
    public String getLossTableHeaderFormat() {
        return lossTableHeaderFormat;
    }

    public void setLossTableHeaderFormat(String lossTableHeaderFormat) {
        this.lossTableHeaderFormat = lossTableHeaderFormat;
    }

    @Basic
    @Column(name = "FileFormatVersion", nullable = true, length = 200)
    public String getFileFormatVersion() {
        return fileFormatVersion;
    }

    public void setFileFormatVersion(String fileFormatVersion) {
        this.fileFormatVersion = fileFormatVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileBasedImportProducer that = (FileBasedImportProducer) o;
        return id == that.id &&
                Objects.equals(lossTableHeaderProducer, that.lossTableHeaderProducer) &&
                Objects.equals(lossTableHeaderFormat, that.lossTableHeaderFormat) &&
                Objects.equals(fileFormatVersion, that.fileFormatVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lossTableHeaderProducer, lossTableHeaderFormat, fileFormatVersion);
    }
}
