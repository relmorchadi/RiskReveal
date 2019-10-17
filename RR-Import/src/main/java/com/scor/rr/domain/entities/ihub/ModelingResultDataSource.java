package com.scor.rr.domain.entities.ihub;

import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.entities.rms.RmsProjectImportConfig;
import com.scor.rr.domain.entities.workspace.AssociationVersion;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ModelingResultDataSource database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModelingResultDataSource")
@Data
public class ModelingResultDataSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModelingResultDataSourceId")
    private Long modelingResultDataSourceId;
    @Column(name = "Alias")
    private String alias;
    @Column(name = "Source")
    private String source;
    @Column(name = "Tags")
    private String tags;
    @Column(name = "AllTags")
    private String allTags;
    @Column(name = "Notes")
    private String notes;
//    @Column(name = "RmsProjectImportConfigId")
//    private String rmsProjectImportConfigId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssociationVersionId")
    private AssociationVersion associationVersion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsModelDataSourceId")
    private RmsModelDatasource rmsModelDataSource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SelectedAssociationBagId")
    private SelectedAssociationBag selectedAssociationBag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsProjectImportConfigId")
    private RmsProjectImportConfig rmsProjectImportConfig;

}
