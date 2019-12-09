package com.scor.rr.domain.entities.Project;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ImportedProject")
@AllArgsConstructor
public class ImportedProject extends NumberOfEntityForProject {
    public ImportedProject(Long projectId, int count) {
        super(projectId, count);
    }
}
