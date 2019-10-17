package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;

/**
 * The persistent class for the BinFile database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "BinFile")
@Data
public class BinFile {
    @Id
    @Column(name = "BinFileId")
    private Long binFileId;
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "Path")
    private String path;
    @Column(name = "Fqn")
    private String fqn;

    public BinFile() {
    }

    public BinFile(String fileName, String path, String fqn) {
        // @formatter:off
        this.fileName = fileName;
        this.path = path;
        this.fqn = fqn;
        // @formatter:on
    }
    public BinFile(File file) {
        this.fileName = file.getName();
        this.path = file.getParent();
    }

    public Boolean isValid() {
        return fileName != null && path != null;
    }
}
