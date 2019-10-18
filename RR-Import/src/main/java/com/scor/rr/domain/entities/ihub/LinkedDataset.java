package com.scor.rr.domain.entities.ihub;

import com.scor.rr.domain.entities.workspace.Portfolio;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the LinkedDataset database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "LinkedDataset")
@Data
public class LinkedDataset {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "LinkedDatasetId")
    private String linkedDatasetId;
    @Column(name = "RmsProjectImportConfigId")
    private String rmsProjectImportConfigId;
    @Column(name = "Name")
    private String name;
    @Column(name = "IncrementName")
    private Integer incrementName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SelectedAssociationBagId")
    private SelectedAssociationBag selectedAssociationBag;
    @OneToMany(mappedBy = "linkedDataset")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SourceResult> linkedSourceResult;
    @OneToMany(mappedBy = "linkedDataset")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Portfolio> linkedPortfolio;
}
