package com.scor.rr.domain.entities.ihub;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the RRLinkedDataSet database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRLinkedDataSet")
@Data
public class RRLinkedDataSet {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "RRLinkedDataSetId")
    private String rrLinkedDataSetId;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "ProjectImportRunId")
    private String projectImportRunId;
    @Column(name = "Name")
    private String name;

    // TODO : to review

    @Transient
    private List<String> linkedAnalysis;
    @Transient
    private List<String> linkedPortfolio;
}
