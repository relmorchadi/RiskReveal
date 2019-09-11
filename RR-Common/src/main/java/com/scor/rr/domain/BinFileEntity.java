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
    @Column(name = "BinFileId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getBinFileId() {
        return binFileId;
    }

    public void setBinFileId(int binFileId) {
        this.binFileId = binFileId;
    }

    @Basic
    @Column(name = "fileName", nullable = false, length = 255)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "path", nullable = false, length = 255)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "fqn", nullable = false, length = 255)
    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinFileEntity that = (BinFileEntity) o;
        return binFileId == that.binFileId &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(path, that.path) &&
                Objects.equals(fqn, that.fqn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(binFileId, fileName, path, fqn);
    }
}
