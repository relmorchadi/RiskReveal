package com.scor.rr.domain.entities.cat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Hamiani Mohammed
 * creation date  17/09/2019 at 14:22
 **/
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {

    @Id
    private int id;
    private String fileName;
    private String path;
    private String fqn;

    public File(String fileName, String path, String fqn) {
        this.fileName = fileName;
        this.path = path;
        this.fqn = fqn;
    }
}
