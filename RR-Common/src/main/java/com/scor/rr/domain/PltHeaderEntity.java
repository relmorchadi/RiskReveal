package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PLTHeader")
public class PltHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "PLTType")
    private String pltType;

    @Column(name = "ModelAnalysisId")
    private Long modelAnalysisId;

    @Column(name = "TargetRAPId")
    private Long targetRAPId;

    // @Todo check regionPeril table id is Long
    @Column(name = "RegionPerilId")
    private Long regionPerilId;

    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "Locked")
    private Boolean locked;

    @Column(name = "PLTSimulationPeriods")
    private Integer pltSimulationPeriods;

    @Column(name = "GeneratedFromDefaultAdjustment")
    private Boolean generatedFromDefaultAdjustment;

    // @Todo check Plt table id is Long
    @Column(name = "CloningSourceId")
    private Long cloningSourceId;

    @Column(name = "LossDataFilePath")
    private String lossDataFilePath;

    @Column(name = "LossDataFileName")
    private String lossDataFileName;

    @Column(name = "CurrencyCode")
    private String currencyCode;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "PerilCode")
    private String perilCode;

    @Column(name = "GeoCode")
    private String geoCode;

    @Column(name = "GeoDescription")
    private String geoDescription;

    @Column(name = "RmsSimulationSet")
    private Integer rmsSimulationSet;

    @Column(name = "ImportSequence")
    private Integer importSequence;

    @Column(name = "ThreadName")
    private String threadName;

    @Column(name = "UDName")
    private String udName;

    @Column(name = "UserOccurrenceBasis")
    private String userOccurrenceBasis;

    @Column(name = "DefaultPltName")
    private String defaultPltName;

    @Column(name = "TruncationThreshold")
    private Double truncationThreshold;

    // @TODO Review the type
    @Column(name = "TruncationExchangeRate")
    private String truncationExchangeRate;

    @Column(name = "TruncationCurrency")
    private String truncationCurrency;

    // @TODO Check inuring Package ID
    @Column(name = "InuringPackageId")
    private Long inuringPackageId;

    // @TODO Review / Removed from Datamodel
    @Column(name = "Currencyid")
    private String currencyid;

    @Column(name = "SourceLossModelingBasis")
    private String sourceLossModelingBasis;

    @Column(name = "GroupedPLT")
    private Boolean groupedPLT;

    @Column(name = "DeletedOn")
    private Date deletedOn;

    @Column(name = "DeletedDue")
    private String deletedDue;

    @Column(name = "DeletedBy")
    private String deletedBy;

    @Column(name = "Archived")
    private Boolean archived;

    @Column(name = "ArchivedDate")
    private Date archivedDate;

    @Column(name = "CreatedBy")
    private Long createdBy;

    public PltHeaderEntity(Long pltHeaderId, String deletedBy) {
        this.pltHeaderId = pltHeaderId;
        this.deletedBy = deletedBy;
    }

    public PltHeaderEntity(PltHeaderEntity other) {
        this.pltType = other.pltType;
        this.locked = other.locked;
        this.pltSimulationPeriods = other.pltSimulationPeriods;
        this.generatedFromDefaultAdjustment = other.generatedFromDefaultAdjustment;
        this.currencyCode = other.getCurrencyCode();
        this.geoCode = other.geoCode;
        this.geoDescription = other.geoDescription;
        this.rmsSimulationSet = other.rmsSimulationSet;
        this.importSequence = other.importSequence;
        this.threadName = other.threadName;
        this.udName = other.udName;
        this.userOccurrenceBasis = other.userOccurrenceBasis;
        this.defaultPltName = other.defaultPltName;
        this.truncationThreshold = other.truncationThreshold;
        this.truncationExchangeRate = other.truncationExchangeRate;
        this.truncationCurrency = other.truncationCurrency;
        this.sourceLossModelingBasis = other.sourceLossModelingBasis;
//        this.basisChanged = other.basisChanged;
        this.inuringPackageId = other.inuringPackageId;
//        this.engineType = other.engineType;
        this.modelAnalysisId = other.modelAnalysisId;
        this.targetRAPId = other.getTargetRAPId();
        this.regionPerilId = other.regionPerilId;
        this.projectId = other.projectId;
//        this.adjustmentBasisByFkPreviousBasisId = other.adjustmentBasisByFkPreviousBasisId;
//        this.adjustmentBasisByFkCurrentBasisId = other.adjustmentBasisByFkCurrentBasisId;
        this.entity = other.entity;
//        this.marketChannel = other.marketChannel;
    }
}

