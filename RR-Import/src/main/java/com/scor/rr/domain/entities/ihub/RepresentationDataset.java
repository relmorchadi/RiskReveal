package com.scor.rr.domain.entities.ihub;

import com.scor.rr.domain.entities.workspace.ImportDecision;
import com.scor.rr.domain.entities.workspace.Portfolio;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the RepresentationDataset database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RepresentationDataset")
@Data
public class RepresentationDataset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RepresentationDatasetId")
    private Long representationDatasetId;
    @Column(name = "RmsProjectImportConfigId")
    private String rmsProjectImportConfigId;
    @Column(name = "Name")
    private String name;
    @Column(name = "IncrementName")
    private Integer incrementName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ImportDecisionId")
    private ImportDecision importDecision;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SelectedAssociationBagId")
    private SelectedAssociationBag selectedAssociationBag;
    @OneToMany(mappedBy = "representationDataset")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SourceResult> representedSourceResults;
    @OneToMany(mappedBy = "representationDataset")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Portfolio> representedPortfolios;
}
