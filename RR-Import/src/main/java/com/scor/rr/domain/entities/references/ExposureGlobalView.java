package com.scor.rr.domain.entities.references;

import com.scor.rr.domain.entities.exposure.ExposureViewDefinition;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ExposureGlobalViews")
@Data
public class ExposureGlobalView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany
    private List<ExposureViewDefinition> definitions;
}