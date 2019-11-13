package com.scor.rr.domain.dto;

import java.io.File;
import java.util.Objects;

public class BinFile {
    private String fileName;
    private String path;
    private String fqn;

    public BinFile() {
    }

    public BinFile(File file) {
        this.fileName = file.getName();
        this.path = file.getParent();
    }

    public BinFile(String fileName, String path, String fqn) {
        this.fileName = fileName;
        this.path = path;
        this.fqn = fqn;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getFqn() {
        return fqn;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fqn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BinFile other = (BinFile) obj;
        return Objects.equals(this.fqn, other.fqn);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("File{");
        sb.append("fileName='").append(fileName).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", fqn='").append(fqn).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Boolean isValid() {
        return fileName != null && path != null;
    }
}
