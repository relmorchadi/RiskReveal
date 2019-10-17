package com.scor.rr.domain.entities.plt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The persistent class for the File database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "File")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ALMFFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "Path")
    private String path;
    @Column(name = "FQN")
    private String fqn;

    public ALMFFile(String fileName, String path, String fqn) {
        this.fileName = fileName;
        this.path = path;
        this.fqn = fqn;
    }
}
