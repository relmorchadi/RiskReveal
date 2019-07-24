package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ScorPLTHeader", schema = "dbo", catalog = "RiskReveal")
@JsonIgnoreProperties(value = {"rrAnalysis","targetRap",
        "regionPeril","projectByProjectId",
        "scorPltHeaderByCloningSourceId","binFile",
        "adjustmentBasisPrevious","adjustmentBasisCurrent"},ignoreUnknown = true)
public class ScorPltHeaderEntity implements Serializable {
    private int scorPltHeaderId;
    private String pltType;
    private Boolean publishToPricing;
    private Integer pltSimulationPeriods;
    private Boolean generatedFromDefaultAdjustement;
    private String ccyCode;
    private String geoCode;
    private String geoDescription;
    private Integer rmsSimulationSet;
    private Integer importSequence;
    private String threadName;
    private String udName;
    private String userOccurrenceBasis;
    private String defaultPltName;
    private String truncationThreshold;
    private String truncationExchangeRate;
    private String truncationCurrency;
    private String sourceLossModelingBasis;
    private Timestamp deletedOn;
    private String deletedDue;
    private String deletedBy;
    private String sourceLossEntityingBasis;
    private String createdBy;
    private Timestamp createdOn;
    private String lastModifiedBy;
    private String lastModifiedOn;
    private Timestamp lastGenerated;
    private Boolean basisChanged;
    private Boolean narrativeChanged;
    private Integer previousNarrative;
    private Integer currentNarrative;
    private Date createdDate;
    private String e;
    private Integer inuringPackageId;
    private String perilCode;
    private String pltLossDataFileName;
    private String pltLossDataFilePath;
    private String engineType;
    private RrAnalysisNewEntity rrAnalysis;
    private TargetRapEntity targetRap;
    private RegionPerilEntity regionPeril;
    private ProjectEntity projectByProjectId;
    private ScorPltHeaderEntity scorPltHeaderByCloningSourceId;
    private EntityEntity entity;
    private MarketChannelEntity marketChannel;
    private BinFileEntity binFile;
    private AdjustmentBasisEntity adjustmentBasisPrevious;
    private AdjustmentBasisEntity adjustmentBasisCurrent;

    @Id
    @Column(name = "scorPLTHeaderId", nullable = false)
    public int getScorPltHeaderId() {
        return scorPltHeaderId;
    }

    public void setScorPltHeaderId(int scorPltHeaderId) {
        this.scorPltHeaderId = scorPltHeaderId;
    }

    @Basic
    @Column(name = "pltType", nullable = true, length = 255)
    public String getPltType() {
        return pltType;
    }

    public void setPltType(String pltType) {
        this.pltType = pltType;
    }

    @Basic
    @Column(name = "publishToPricing", nullable = true)
    public Boolean getPublishToPricing() {
        return publishToPricing;
    }

    public void setPublishToPricing(Boolean publishToPricing) {
        this.publishToPricing = publishToPricing;
    }

    @Basic
    @Column(name = "pltSimulationPeriods", nullable = true)
    public Integer getPltSimulationPeriods() {
        return pltSimulationPeriods;
    }

    public void setPltSimulationPeriods(Integer pltSimulationPeriods) {
        this.pltSimulationPeriods = pltSimulationPeriods;
    }

    @Basic
    @Column(name = "generatedFromDefaultAdjustement", nullable = true)
    public Boolean getGeneratedFromDefaultAdjustement() {
        return generatedFromDefaultAdjustement;
    }

    public void setGeneratedFromDefaultAdjustement(Boolean generatedFromDefaultAdjustement) {
        this.generatedFromDefaultAdjustement = generatedFromDefaultAdjustement;
    }

    @Basic
    @Column(name = "ccyCode", nullable = true, length = 255)
    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    @Basic
    @Column(name = "geoCode", nullable = true, length = 255)
    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    @Basic
    @Column(name = "geoDescription", nullable = true, length = 255)
    public String getGeoDescription() {
        return geoDescription;
    }

    public void setGeoDescription(String geoDescription) {
        this.geoDescription = geoDescription;
    }

    @Basic
    @Column(name = "rmsSimulationSet", nullable = true)
    public Integer getRmsSimulationSet() {
        return rmsSimulationSet;
    }

    public void setRmsSimulationSet(Integer rmsSimulationSet) {
        this.rmsSimulationSet = rmsSimulationSet;
    }

    @Basic
    @Column(name = "importSequence", nullable = true)
    public Integer getImportSequence() {
        return importSequence;
    }

    public void setImportSequence(Integer importSequence) {
        this.importSequence = importSequence;
    }

    @Basic
    @Column(name = "threadName", nullable = true, length = 255)
    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Basic
    @Column(name = "udName", nullable = true, length = 255)
    public String getUdName() {
        return udName;
    }

    public void setUdName(String udName) {
        this.udName = udName;
    }

    @Basic
    @Column(name = "userOccurrenceBasis", nullable = true, length = 255)
    public String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }

    @Basic
    @Column(name = "defaultPltName", nullable = true, length = 255)
    public String getDefaultPltName() {
        return defaultPltName;
    }

    public void setDefaultPltName(String defaultPltName) {
        this.defaultPltName = defaultPltName;
    }

    @Basic
    @Column(name = "truncationThreshold", nullable = true, length = 255)
    public String getTruncationThreshold() {
        return truncationThreshold;
    }

    public void setTruncationThreshold(String truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
    }

    @Basic
    @Column(name = "truncationExchangeRate", nullable = true, length = 255)
    public String getTruncationExchangeRate() {
        return truncationExchangeRate;
    }

    public void setTruncationExchangeRate(String truncationExchangeRate) {
        this.truncationExchangeRate = truncationExchangeRate;
    }

    @Basic
    @Column(name = "truncationCurrency", nullable = true, length = 255)
    public String getTruncationCurrency() {
        return truncationCurrency;
    }

    public void setTruncationCurrency(String truncationCurrency) {
        this.truncationCurrency = truncationCurrency;
    }

    @Basic
    @Column(name = "sourceLossModelingBasis", nullable = true, length = 255)
    public String getSourceLossModelingBasis() {
        return sourceLossModelingBasis;
    }

    public void setSourceLossModelingBasis(String sourceLossModelingBasis) {
        this.sourceLossModelingBasis = sourceLossModelingBasis;
    }

    @Basic
    @Column(name = "deletedOn", nullable = true)
    public Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Basic
    @Column(name = "deletedDue", nullable = true, length = 255)
    public String getDeletedDue() {
        return deletedDue;
    }

    public void setDeletedDue(String deletedDue) {
        this.deletedDue = deletedDue;
    }

    @Basic
    @Column(name = "deletedBy", nullable = true, length = 255)
    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Basic
    @Column(name = "sourceLossEntityingBasis", nullable = true, length = 255)
    public String getSourceLossEntityingBasis() {
        return sourceLossEntityingBasis;
    }

    public void setSourceLossEntityingBasis(String sourceLossEntityingBasis) {
        this.sourceLossEntityingBasis = sourceLossEntityingBasis;
    }

    @Basic
    @Column(name = "created_by", nullable = true, length = 255)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_on", nullable = true)
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "last_modified_by", nullable = true, length = 255)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_on", nullable = true, length = 255)
    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Basic
    @Column(name = "last_generated", nullable = true)
    public Timestamp getLastGenerated() {
        return lastGenerated;
    }

    public void setLastGenerated(Timestamp lastGenerated) {
        this.lastGenerated = lastGenerated;
    }

    @Basic
    @Column(name = "basis_changed", nullable = true)
    public Boolean getBasisChanged() {
        return basisChanged;
    }

    public void setBasisChanged(Boolean basisChanged) {
        this.basisChanged = basisChanged;
    }

    @Basic
    @Column(name = "narrative_changed", nullable = true)
    public Boolean getNarrativeChanged() {
        return narrativeChanged;
    }

    public void setNarrativeChanged(Boolean narrativeChanged) {
        this.narrativeChanged = narrativeChanged;
    }

    @Basic
    @Column(name = "previous_narrative", nullable = true)
    public Integer getPreviousNarrative() {
        return previousNarrative;
    }

    public void setPreviousNarrative(Integer previousNarrative) {
        this.previousNarrative = previousNarrative;
    }

    @Basic
    @Column(name = "current_narrative", nullable = true)
    public Integer getCurrentNarrative() {
        return currentNarrative;
    }

    public void setCurrentNarrative(Integer currentNarrative) {
        this.currentNarrative = currentNarrative;
    }

    @Basic
    @Column(name = "createdDate", nullable = true)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "e", nullable = true, length = 255)
    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    @Basic
    @Column(name = "inuringPackageId", nullable = true)
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Basic
    @Column(name = "perilCode", nullable = true, length = 255)
    public String getPerilCode() {
        return perilCode;
    }

    public void setPerilCode(String perilCode) {
        this.perilCode = perilCode;
    }

    @Basic
    @Column(name = "pltLossDataFileName", nullable = true, length = 255)
    public String getPltLossDataFileName() {
        return pltLossDataFileName;
    }

    public void setPltLossDataFileName(String pltLossDataFileName) {
        this.pltLossDataFileName = pltLossDataFileName;
    }

    @Basic
    @Column(name = "pltLossDataFilePath", nullable = true, length = 255)
    public String getPltLossDataFilePath() {
        return pltLossDataFilePath;
    }

    public void setPltLossDataFilePath(String pltLossDataFilePath) {
        this.pltLossDataFilePath = pltLossDataFilePath;
    }

    @Basic
    @Column(name = "engine_type", nullable = true, length = 255)
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(scorPltHeaderId, pltType, publishToPricing, pltSimulationPeriods, generatedFromDefaultAdjustement, ccyCode, geoCode, geoDescription, rmsSimulationSet, importSequence, threadName, udName, userOccurrenceBasis, defaultPltName, truncationThreshold, truncationExchangeRate, truncationCurrency, sourceLossModelingBasis, deletedOn, deletedDue, deletedBy, sourceLossEntityingBasis, createdBy, createdOn, lastModifiedBy, lastModifiedOn, lastGenerated, basisChanged, narrativeChanged, previousNarrative, currentNarrative, createdDate, e, inuringPackageId, perilCode, pltLossDataFileName, pltLossDataFilePath, engineType, entity);
    }

    @ManyToOne
    @JoinColumn(name = "cloningSourceId", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByCloningSourceId() {
        return scorPltHeaderByCloningSourceId;
    }

    public void setScorPltHeaderByCloningSourceId(ScorPltHeaderEntity scorPltHeaderByCloningSourceId) {
        this.scorPltHeaderByCloningSourceId = scorPltHeaderByCloningSourceId;
    }

    @ManyToOne
    @JoinColumn(name = "id_entity", referencedColumnName = "id_entity")
    public EntityEntity getEntity() {
        return entity;
    }

    public void setEntity(EntityEntity entity) {
        this.entity = entity;
    }

    @ManyToOne
    @JoinColumn(name = "id_market_channel", referencedColumnName = "id_market_channel",insertable = false, updatable = false)
    public MarketChannelEntity getMarketChannel() {
        return marketChannel;
    }

    public void setMarketChannel(MarketChannelEntity marketChannel) {
        this.marketChannel = marketChannel;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "id_bin_file", referencedColumnName = "binFile_Id", nullable = true, table = "")
    public BinFileEntity getBinFile() {
        return binFile;
    }

    public void setBinFile(BinFileEntity binFile) {
        this.binFile = binFile;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "rrAnalysisId", referencedColumnName = "rrAnalysisId", nullable = true, table = "")
    public RrAnalysisNewEntity getRrAnalysis() {
        return rrAnalysis;
    }

    public void setRrAnalysis(RrAnalysisNewEntity rrAnalysisNewByRrAnalysisId) {
        this.rrAnalysis = rrAnalysisNewByRrAnalysisId;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "id_target_rap", referencedColumnName = "targetRapId", nullable = true, table = "")
    public TargetRapEntity getTargetRap() {
        return targetRap;
    }

    public void setTargetRap(TargetRapEntity targetRap) {
        this.targetRap = targetRap;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "regionPerilId", referencedColumnName = "regionPerilId", nullable = true, table = "")
    public RegionPerilEntity getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(RegionPerilEntity regionPeril) {
        this.regionPeril = regionPeril;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "projectId", referencedColumnName = "projectId", nullable = true, table = "")
    public ProjectEntity getProject() {
        return projectByProjectId;
    }

    public void setProject(ProjectEntity projectByProjectId) {
        this.projectByProjectId = projectByProjectId;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "previous_basis", referencedColumnName = "code", nullable = true, table = "")
    public AdjustmentBasisEntity getAdjustmentBasisPrevious() {
        return adjustmentBasisPrevious;
    }

    public void setAdjustmentBasisPrevious(AdjustmentBasisEntity adjustmentBasisByPreviousBasis) {
        this.adjustmentBasisPrevious = adjustmentBasisByPreviousBasis;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "current_basis", referencedColumnName = "code", nullable = true, table = "")
    public AdjustmentBasisEntity getAdjustmentBasisCurrent() {
        return adjustmentBasisCurrent;
    }

    public void setAdjustmentBasisCurrent(AdjustmentBasisEntity adjustmentBasisByCurrentBasis) {
        this.adjustmentBasisCurrent = adjustmentBasisByCurrentBasis;
    }
}
