package com.scor.rr.domain.entities.workspace;

import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the DataModel database table
 *
 * @author HADDINI Zakariyae && Hamiani Mohammed
 */
@Entity
@Table(name = "DataModel")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class DataModel {
    @Id
    @Column(name = "DataModelId")
    private String dataModelId;
    @Column(name = "Tags")
    private String tags;
    @Column(name = "Notes")
    private String notes;
    @Column(name = "Alias")
    private String alias;
    @Column(name = "Source")
    private String source;
    private String allTags;
    @OneToOne
    private Project project;
    @OneToOne
    private AssociationVersion associationVersion;
    @OneToOne
    private RmsModelDatasource rmsModelDataSource;


}
