package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureViewVersion {

    @Id
    private Long exposureViewVersionId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Number")
    private Integer number;
    @Column(name = "Current")
    private boolean current;

    @ManyToOne
    @JoinColumn(name = "ExposureViewDefinitionId")
    private ExposureViewDefinition exposureViewDefinition;
}
