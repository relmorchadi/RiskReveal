package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "BinFile", schema = "dbo", catalog = "RiskReveal")
public class BinFileEntity {
    private int binFileId;
    private String fileName;
    private String path;
    private String fqn;

    @Id
    @Column(name = "BinFile_Id", nullable = false, precision = 0)
    public int getBinFileId() {
        return binFileId;
    }

    @Basic
    @Column(name = "fileName", nullable = true, length = 255)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "path", nullable = true, length = 255)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "fqn", nullable = true, length = 255)
    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public void setBinFileId(int binFileId) {
        this.binFileId = binFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinFileEntity that = (BinFileEntity) o;
        return binFileId == that.binFileId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(binFileId);
    }
}
