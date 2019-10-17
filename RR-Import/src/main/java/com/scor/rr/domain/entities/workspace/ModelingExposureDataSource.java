package com.scor.rr.domain.entities.workspace;

import com.scor.rr.domain.entities.ihub.SelectedAssociationBag;
import com.scor.rr.domain.entities.rms.RmsProjectImportConfig;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ModelingExposureDataSource database table
 *
 * @author HADDINI Zakariyae  && HAMIANI Mohammed
		*/
@Entity
@Table(name = "ModelingExposureDataSource")
@Data
public class ModelingExposureDataSource extends DataModel {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "RmsProjectImportConfigId")
//    private String rmsProjectImportConfigId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SelectedAssociationBagId")
    private SelectedAssociationBag selectedAssociationBag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsProjectImportConfigId")
    private RmsProjectImportConfig rmsProjectImportConfig;
}
