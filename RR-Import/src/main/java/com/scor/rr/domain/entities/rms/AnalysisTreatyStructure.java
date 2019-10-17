package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.ihub.SourceResult;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the AnalysisTreatyStructure database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AnalysisTreatyStructure")
@Data
public class AnalysisTreatyStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnalysisTreatyStructureId")
    private Long analysisTreatyStructureId;
    @Column(name = "AnalysisId")
    private Long analysisId;
    @Column(name = "TreatyId")
    private Integer treatyId;
    @Column(name = "TreatyNum")
    private String treatyNum;
    @Column(name = "TreatyName")
    private String treatyName;
    @Column(name = "TreatyType")
    private String treatyType;
    @Column(name = "RiskLimit")
    private Float riskLimit;
    @Column(name = "OccurenceLimit")
    private Float occurenceLimit;
    @Column(name = "AttachmentPoint")
    private Float attachmentPoint;
    @Column(name = "Lob")
    private String lob;
    @Column(name = "Cedant")
    private String cedant;
    @Column(name = "PctCovered")
    private Float pctCovered;
    @Column(name = "PctPlaced")
    private Float pctPlaced;
    @Column(name = "PctRiShared")
    private Float pctRiShared;
    @Column(name = "PctRetention")
    private Float pctRetention;
    @Column(name = "NoofReinstatements")
    private Integer noofReinstatements;
    @Column(name = "InuringPriority")
    private Integer inuringPriority;
    @Column(name = "CcyCode")
    private String ccyCode;
    @Column(name = "AttachmentBasis")
    private String attachmentBasis;
    @Column(name = "ExposureLevel")
    private String exposureLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsAnalysisId")
    private RmsAnalysis rmsAnalysis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceResultId")
    private SourceResult sourceResult;
}
