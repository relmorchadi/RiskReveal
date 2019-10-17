package com.scor.rr.domain.entities.ihub;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the RRRepresentationDataset database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRRepresentationDataset")
@Data
public class RRRepresentationDataset implements Cloneable {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RRRepresentationDatasetId")
    private String rrRepresentationDatasetId;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "Name")
    private String name;
    @Column(name = "IncrementName")
    private Integer incrementName;


    @Transient
    private List<String> representationAnalysis;
    @Transient
    private List<String> representationPortfolio;

}
