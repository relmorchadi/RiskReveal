package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ScorPLTHeader", schema = "dbo", catalog = "RiskReveal")
public class ScorPltHeaderEntity {
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
    private Integer inuringPackageId;
    private String perilCode;
    private String pltLossDataFileName;
    private String pltLossDataFilePath;
    private String engineType;
    private Integer fkBinFileId;
    private int pkScorPltHeaderId;
    private Integer projectId;
    private Integer regionPerilId;
    private Integer rrAnalysisId;
    private RrAnalysisNewEntity rrAnalysisNew;
    private TargetRapEntity targetRap;
    private RegionPerilEntity regionPeril;
    private ProjectEntity projectByFkProjectId;
    private AdjustmentBasisEntity adjustmentBasisByFkPreviousBasisId;
    private AdjustmentBasisEntity adjustmentBasisByFkCurrentBasisId;
    private EntityEntity entity;
    private MarketChannelEntity marketChannel;
    private ScorPltHeaderEntity scorPltHeader;
    private BinFileEntity binFileEntity;

    public ScorPltHeaderEntity(ScorPltHeaderEntity other) {
        this.pltType = other.pltType;
        this.publishToPricing = other.publishToPricing;
        this.pltSimulationPeriods = other.pltSimulationPeriods;
        this.generatedFromDefaultAdjustement = other.generatedFromDefaultAdjustement;
        this.ccyCode = other.ccyCode;
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
        this.deletedOn = other.deletedOn;
        this.deletedDue = other.deletedDue;
        this.deletedBy = other.deletedBy;
        this.sourceLossEntityingBasis = other.sourceLossEntityingBasis;
        this.createdBy = other.createdBy;
        this.createdOn = other.createdOn;
        this.lastModifiedBy = other.lastModifiedBy;
        this.lastModifiedOn = other.lastModifiedOn;
        this.lastGenerated = other.lastGenerated;
        this.basisChanged = other.basisChanged;
        this.narrativeChanged = other.narrativeChanged;
        this.previousNarrative = other.previousNarrative;
        this.currentNarrative = other.currentNarrative;
        this.createdDate = other.createdDate;
        this.inuringPackageId = other.inuringPackageId;
        this.perilCode = other.perilCode;
        this.pltLossDataFileName = other.pltLossDataFileName;
        this.pltLossDataFilePath = other.pltLossDataFilePath;
        this.engineType = other.engineType;
        this.fkBinFileId = other.fkBinFileId;
        this.pkScorPltHeaderId = other.pkScorPltHeaderId;
        this.projectId = other.projectId;
        this.regionPerilId = other.regionPerilId;
        this.rrAnalysisId = other.rrAnalysisId;
        this.rrAnalysisNew = other.rrAnalysisNew;
        this.targetRap = other.targetRap;
        this.regionPeril = other.regionPeril;
        this.projectByFkProjectId = other.projectByFkProjectId;
        this.adjustmentBasisByFkPreviousBasisId = other.adjustmentBasisByFkPreviousBasisId;
        this.adjustmentBasisByFkCurrentBasisId = other.adjustmentBasisByFkCurrentBasisId;
        this.entity = other.entity;
        this.marketChannel = other.marketChannel;
        this.scorPltHeader = other.scorPltHeader;
    }

    public ScorPltHeaderEntity() {

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

    @Basic
    @Column(name = "FKBinFileId", nullable = true)
    public Integer getFkBinFileId() {
        return fkBinFileId;
    }

    public void setFkBinFileId(Integer fkBinFileId) {
        this.fkBinFileId = fkBinFileId;
    }

    @Id
    @Column(name = "PKScorPltHeaderId", nullable = false)
    public int getPkScorPltHeaderId() {
        return pkScorPltHeaderId;
    }

    public void setPkScorPltHeaderId(int pkScorPltHeaderId) {
        this.pkScorPltHeaderId = pkScorPltHeaderId;
    }

    @Basic
    @Column(name = "projectId", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "regionPerilId", nullable = true)
    public Integer getRegionPerilId() {
        return regionPerilId;
    }

    public void setRegionPerilId(Integer regionPerilId) {
        this.regionPerilId = regionPerilId;
    }

    @Basic
    @Column(name = "rrAnalysisId", nullable = true)
    public Integer getRrAnalysisId() {
        return rrAnalysisId;
    }

    public void setRrAnalysisId(Integer rrAnalysisId) {
        this.rrAnalysisId = rrAnalysisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScorPltHeaderEntity that = (ScorPltHeaderEntity) o;
        return pkScorPltHeaderId == that.pkScorPltHeaderId &&
                Objects.equals(pltType, that.pltType) &&
                Objects.equals(publishToPricing, that.publishToPricing) &&
                Objects.equals(pltSimulationPeriods, that.pltSimulationPeriods) &&
                Objects.equals(generatedFromDefaultAdjustement, that.generatedFromDefaultAdjustement) &&
                Objects.equals(ccyCode, that.ccyCode) &&
                Objects.equals(geoCode, that.geoCode) &&
                Objects.equals(geoDescription, that.geoDescription) &&
                Objects.equals(rmsSimulationSet, that.rmsSimulationSet) &&
                Objects.equals(importSequence, that.importSequence) &&
                Objects.equals(threadName, that.threadName) &&
                Objects.equals(udName, that.udName) &&
                Objects.equals(userOccurrenceBasis, that.userOccurrenceBasis) &&
                Objects.equals(defaultPltName, that.defaultPltName) &&
                Objects.equals(truncationThreshold, that.truncationThreshold) &&
                Objects.equals(truncationExchangeRate, that.truncationExchangeRate) &&
                Objects.equals(truncationCurrency, that.truncationCurrency) &&
                Objects.equals(sourceLossModelingBasis, that.sourceLossModelingBasis) &&
                Objects.equals(deletedOn, that.deletedOn) &&
                Objects.equals(deletedDue, that.deletedDue) &&
                Objects.equals(deletedBy, that.deletedBy) &&
                Objects.equals(sourceLossEntityingBasis, that.sourceLossEntityingBasis) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedOn, that.lastModifiedOn) &&
                Objects.equals(lastGenerated, that.lastGenerated) &&
                Objects.equals(basisChanged, that.basisChanged) &&
                Objects.equals(narrativeChanged, that.narrativeChanged) &&
                Objects.equals(previousNarrative, that.previousNarrative) &&
                Objects.equals(currentNarrative, that.currentNarrative) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(inuringPackageId, that.inuringPackageId) &&
                Objects.equals(perilCode, that.perilCode) &&
                Objects.equals(pltLossDataFileName, that.pltLossDataFileName) &&
                Objects.equals(pltLossDataFilePath, that.pltLossDataFilePath) &&
                Objects.equals(engineType, that.engineType) &&
                Objects.equals(fkBinFileId, that.fkBinFileId) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(regionPerilId, that.regionPerilId) &&
                Objects.equals(rrAnalysisId, that.rrAnalysisId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pltType, publishToPricing, pltSimulationPeriods, generatedFromDefaultAdjustement, ccyCode, geoCode, geoDescription, rmsSimulationSet, importSequence, threadName, udName, userOccurrenceBasis, defaultPltName, truncationThreshold, truncationExchangeRate, truncationCurrency, sourceLossModelingBasis, deletedOn, deletedDue, deletedBy, sourceLossEntityingBasis, createdBy, createdOn, lastModifiedBy, lastModifiedOn, lastGenerated, basisChanged, narrativeChanged, previousNarrative, currentNarrative, createdDate, inuringPackageId, perilCode, pltLossDataFileName, pltLossDataFilePath, engineType, fkBinFileId, pkScorPltHeaderId, projectId, regionPerilId, rrAnalysisId);
    }

    @ManyToOne
    @JoinColumn(name = "FKRRAnalysisId", referencedColumnName = "rrAnalysisId")
    public RrAnalysisNewEntity getRrAnalysisNew() {
        return rrAnalysisNew;
    }

    public void setRrAnalysisNew(RrAnalysisNewEntity rrAnalysisNew) {
        this.rrAnalysisNew = rrAnalysisNew;
    }

    @ManyToOne
    @JoinColumn(name = "FKTargetRapId", referencedColumnName = "targetRapId")
    public TargetRapEntity getTargetRap() {
        return targetRap;
    }

    public void setTargetRap(TargetRapEntity targetRap) {
        this.targetRap = targetRap;
    }

    @ManyToOne
    @JoinColumn(name = "FKRegionPerilId", referencedColumnName = "regionPerilId" ,insertable = false,updatable = false)
    public RegionPerilEntity getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(RegionPerilEntity regionPeril) {
        this.regionPeril = regionPeril;
    }

    @ManyToOne
    @JoinColumn(name = "FKProjectId", referencedColumnName = "projectId")
    public ProjectEntity getProjectByFkProjectId() {
        return projectByFkProjectId;
    }

    public void setProjectByFkProjectId(ProjectEntity projectByFkProjectId) {
        this.projectByFkProjectId = projectByFkProjectId;
    }

    @ManyToOne
    @JoinColumn(name = "FKPreviousBasisId", referencedColumnName = "AdjustmentBasisId")
    public AdjustmentBasisEntity getAdjustmentBasisByFkPreviousBasisId() {
        return adjustmentBasisByFkPreviousBasisId;
    }

    public void setAdjustmentBasisByFkPreviousBasisId(AdjustmentBasisEntity adjustmentBasisByFkPreviousBasisId) {
        this.adjustmentBasisByFkPreviousBasisId = adjustmentBasisByFkPreviousBasisId;
    }

    @ManyToOne
    @JoinColumn(name = "FKCurrentBasisId", referencedColumnName = "AdjustmentBasisId")
    public AdjustmentBasisEntity getAdjustmentBasisByFkCurrentBasisId() {
        return adjustmentBasisByFkCurrentBasisId;
    }

    public void setAdjustmentBasisByFkCurrentBasisId(AdjustmentBasisEntity adjustmentBasisByFkCurrentBasisId) {
        this.adjustmentBasisByFkCurrentBasisId = adjustmentBasisByFkCurrentBasisId;
    }

    @ManyToOne
    @JoinColumn(name = "FKEntityId", referencedColumnName = "EntityId",insertable = false,updatable = false)
    public EntityEntity getEntity() {
        return entity;
    }

    public void setEntity(EntityEntity entity) {
        this.entity = entity;
    }

    @ManyToOne
    @JoinColumn(name = "FKMarketChannelId", referencedColumnName = "MarketChannelID",insertable = false,updatable = false)
    public MarketChannelEntity getMarketChannel() {
        return marketChannel;
    }

    public void setMarketChannel(MarketChannelEntity marketChannel) {
        this.marketChannel = marketChannel;
    }

    @ManyToOne
    @JoinColumn(name = "FKCloningSourceId", referencedColumnName = "PKScorPltHeaderId",insertable = false,updatable = false)
    public ScorPltHeaderEntity getScorPltHeader() {
        return scorPltHeader;
    }

    public void setScorPltHeader(ScorPltHeaderEntity scorPltHeader) {
        this.scorPltHeader = scorPltHeader;
    }
    @ManyToOne
    @JoinColumn(name = "FKBinFileId", referencedColumnName = "BinFileId",insertable = false,updatable = false)
    public BinFileEntity getBinFileEntity() {
        return binFileEntity;
    }

    public void setBinFileEntity(BinFileEntity binFileEntity) {
        this.binFileEntity = binFileEntity;
    }
}
