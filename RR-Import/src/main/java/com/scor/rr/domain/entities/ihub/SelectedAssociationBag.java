package com.scor.rr.domain.entities.ihub;

import com.scor.rr.domain.entities.rms.RmsProjectImportConfig;
import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the SelectedAssociationBag database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SelectedAssociationBag")
@Data
public class SelectedAssociationBag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SelectedAssociationBagId")
    private Long selectedAssociationBagId;
    @Column(name = "AssociationBagID")
    private String associationBagID;
    @Column(name = "AssociationBagName")
    private String associationBagName;
    @Column(name = "IncrementName")
    private Integer incrementName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsProjectImportConfigId")
    private RmsProjectImportConfig rmsProjectImportConfig;
    @OneToMany(mappedBy = "selectedAssociationBag")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ModelingExposureDataSource> modelingExposureDataSources;
    @OneToMany(mappedBy = "selectedAssociationBag")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ModelingResultDataSource> modelingResultDataSources;

    @OneToMany(mappedBy = "selectedAssociationBag")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LinkedDataset> linkedDatasets;
    @OneToMany(mappedBy = "selectedAssociationBag")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RepresentationDataset> representationDatasets;

}
