package com.scor.rr.domain.importfile;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ImportedFile", schema = "dbo", catalog = "RiskReveal")
public class ImportedFileEntity {
    private int importedFileId;
    private String fileName;
    private String path;
    private String status;
    private String error;

    public ImportedFileEntity(String fileName, String path, String status,String error) {
        this.fileName = fileName;
        this.path = path;
        this.status = status;
        this.error = error;
    }

    public ImportedFileEntity() {

    }

    @Id
    @Column(name = "importedFileId", nullable = false)
    public int getImportedFile() {
        return importedFileId;
    }

    public void setImportedFile(int importedFile) {
        this.importedFileId = importedFile;
    }

    @Basic
    @Column(name = "fileName", length = 200)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "path", length = 200)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Basic
    @Column(name = "error", length = 500)
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportedFileEntity that = (ImportedFileEntity) o;
        return importedFileId == that.importedFileId &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(path, that.path) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importedFileId, fileName, path, status);
    }
}
