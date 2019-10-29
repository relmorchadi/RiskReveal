package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PLTHeader", schema = "dr")
public class PLTHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLTHeaderId")
    private Integer pltHeaderId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "PLTType")
    private String pltType;

    @Column(name = "RRAnalysisId")
    private Long rrAnalysisId;

    @Column(name = "TargetRAPId")
    private Long targetRAPId;

    @Column(name = "RegionPerilId")
    private Integer regionPerilId;

    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "Locked")
    private Boolean locked;

    @Column(name = "PLTSimulationPeriods")
    private Integer pltSimulationPeriods;

    @Column(name = "GeneratedFromDefaultAdjustment")
    private Boolean generatedFromDefaultAdjustment;

    @Column(name = "CloningSourceId")
    private Integer cloningSourceId;

    @Column(name = "PLTLossDataFilePath")
    private String pltLossDataFilePath;

    @Column(name = "PLTLossDataFileName")
    private String pltLossDataFileName;

    @Column(name = "CcyCode")
    private String ccyCode;

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

    @Column(name = "TruncationExchangeRate")
    private String truncationExchangeRate;

    @Column(name = "TruncationCurrency")
    private String truncationCurrency;

    @Column(name = "InuringPackageId")
    private Integer inuringPackageId;

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


    public PLTHeader(Integer pltHeaderId, String deletedBy) {
        this.pltHeaderId = pltHeaderId;
        this.deletedBy = deletedBy;
    }
}

