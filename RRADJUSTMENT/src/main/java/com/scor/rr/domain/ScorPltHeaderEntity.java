package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ScorPLTHeader", schema = "dbo", catalog = "RiskReveal")
public class ScorPltHeaderEntity {
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
    private String marketChannel;
    private String engineType;
    private String entity;
    private RrAnalysisNewEntity rrAnalysis;
    private TargetRapEntity targetRapByTargetRapId;
    private RegionPerilEntity regionPerilByRegionPerilId;
    private ProjectEntity projectByProjectId;
    private ScorPltHeaderEntity scorPltHeaderByCloningSourceId;
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
    public java.lang.String getPltType() {
        return pltType;
    }

    public void setPltType(java.lang.String pltType) {
        this.pltType = pltType;
    }

    @Basic
    @Column(name = "publishToPricing", nullable = true)
    public java.lang.Boolean getPublishToPricing() {
        return publishToPricing;
    }

    public void setPublishToPricing(java.lang.Boolean publishToPricing) {
        this.publishToPricing = publishToPricing;
    }

    @Basic
    @Column(name = "pltSimulationPeriods", nullable = true)
    public java.lang.Integer getPltSimulationPeriods() {
        return pltSimulationPeriods;
    }

    public void setPltSimulationPeriods(java.lang.Integer pltSimulationPeriods) {
        this.pltSimulationPeriods = pltSimulationPeriods;
    }

    @Basic
    @Column(name = "generatedFromDefaultAdjustement", nullable = true)
    public java.lang.Boolean getGeneratedFromDefaultAdjustement() {
        return generatedFromDefaultAdjustement;
    }

    public void setGeneratedFromDefaultAdjustement(java.lang.Boolean generatedFromDefaultAdjustement) {
        this.generatedFromDefaultAdjustement = generatedFromDefaultAdjustement;
    }

    @Basic
    @Column(name = "ccyCode", nullable = true, length = 255)
    public java.lang.String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(java.lang.String ccyCode) {
        this.ccyCode = ccyCode;
    }

    @Basic
    @Column(name = "geoCode", nullable = true, length = 255)
    public java.lang.String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(java.lang.String geoCode) {
        this.geoCode = geoCode;
    }

    @Basic
    @Column(name = "geoDescription", nullable = true, length = 255)
    public java.lang.String getGeoDescription() {
        return geoDescription;
    }

    public void setGeoDescription(java.lang.String geoDescription) {
        this.geoDescription = geoDescription;
    }

    @Basic
    @Column(name = "rmsSimulationSet", nullable = true)
    public java.lang.Integer getRmsSimulationSet() {
        return rmsSimulationSet;
    }

    public void setRmsSimulationSet(java.lang.Integer rmsSimulationSet) {
        this.rmsSimulationSet = rmsSimulationSet;
    }

    @Basic
    @Column(name = "importSequence", nullable = true)
    public java.lang.Integer getImportSequence() {
        return importSequence;
    }

    public void setImportSequence(java.lang.Integer importSequence) {
        this.importSequence = importSequence;
    }

    @Basic
    @Column(name = "threadName", nullable = true, length = 255)
    public java.lang.String getThreadName() {
        return threadName;
    }

    public void setThreadName(java.lang.String threadName) {
        this.threadName = threadName;
    }

    @Basic
    @Column(name = "udName", nullable = true, length = 255)
    public java.lang.String getUdName() {
        return udName;
    }

    public void setUdName(java.lang.String udName) {
        this.udName = udName;
    }

    @Basic
    @Column(name = "userOccurrenceBasis", nullable = true, length = 255)
    public java.lang.String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(java.lang.String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }

    @Basic
    @Column(name = "defaultPltName", nullable = true, length = 255)
    public java.lang.String getDefaultPltName() {
        return defaultPltName;
    }

    public void setDefaultPltName(java.lang.String defaultPltName) {
        this.defaultPltName = defaultPltName;
    }

    @Basic
    @Column(name = "truncationThreshold", nullable = true, length = 255)
    public java.lang.String getTruncationThreshold() {
        return truncationThreshold;
    }

    public void setTruncationThreshold(java.lang.String truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
    }

    @Basic
    @Column(name = "truncationExchangeRate", nullable = true, length = 255)
    public java.lang.String getTruncationExchangeRate() {
        return truncationExchangeRate;
    }

    public void setTruncationExchangeRate(java.lang.String truncationExchangeRate) {
        this.truncationExchangeRate = truncationExchangeRate;
    }

    @Basic
    @Column(name = "truncationCurrency", nullable = true, length = 255)
    public java.lang.String getTruncationCurrency() {
        return truncationCurrency;
    }

    public void setTruncationCurrency(java.lang.String truncationCurrency) {
        this.truncationCurrency = truncationCurrency;
    }

    @Basic
    @Column(name = "sourceLossModelingBasis", nullable = true, length = 255)
    public java.lang.String getSourceLossModelingBasis() {
        return sourceLossModelingBasis;
    }

    public void setSourceLossModelingBasis(java.lang.String sourceLossModelingBasis) {
        this.sourceLossModelingBasis = sourceLossModelingBasis;
    }

    @Basic
    @Column(name = "deletedOn", nullable = true)
    public java.sql.Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(java.sql.Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Basic
    @Column(name = "deletedDue", nullable = true, length = 255)
    public java.lang.String getDeletedDue() {
        return deletedDue;
    }

    public void setDeletedDue(java.lang.String deletedDue) {
        this.deletedDue = deletedDue;
    }

    @Basic
    @Column(name = "deletedBy", nullable = true, length = 255)
    public java.lang.String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(java.lang.String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Basic
    @Column(name = "sourceLossEntityingBasis", nullable = true, length = 255)
    public java.lang.String getSourceLossEntityingBasis() {
        return sourceLossEntityingBasis;
    }

    public void setSourceLossEntityingBasis(java.lang.String sourceLossEntityingBasis) {
        this.sourceLossEntityingBasis = sourceLossEntityingBasis;
    }

    @Basic
    @Column(name = "created_by", nullable = true, length = 255)
    public java.lang.String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_on", nullable = true)
    public java.sql.Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(java.sql.Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "last_modified_by", nullable = true, length = 255)
    public java.lang.String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(java.lang.String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_on", nullable = true, length = 255)
    public java.lang.String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(java.lang.String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Basic
    @Column(name = "last_generated", nullable = true)
    public java.sql.Timestamp getLastGenerated() {
        return lastGenerated;
    }

    public void setLastGenerated(java.sql.Timestamp lastGenerated) {
        this.lastGenerated = lastGenerated;
    }

    @Basic
    @Column(name = "basis_changed", nullable = true)
    public java.lang.Boolean getBasisChanged() {
        return basisChanged;
    }

    public void setBasisChanged(java.lang.Boolean basisChanged) {
        this.basisChanged = basisChanged;
    }

    @Basic
    @Column(name = "narrative_changed", nullable = true)
    public java.lang.Boolean getNarrativeChanged() {
        return narrativeChanged;
    }

    public void setNarrativeChanged(java.lang.Boolean narrativeChanged) {
        this.narrativeChanged = narrativeChanged;
    }

    @Basic
    @Column(name = "previous_narrative", nullable = true)
    public java.lang.Integer getPreviousNarrative() {
        return previousNarrative;
    }

    public void setPreviousNarrative(java.lang.Integer previousNarrative) {
        this.previousNarrative = previousNarrative;
    }

    @Basic
    @Column(name = "current_narrative", nullable = true)
    public java.lang.Integer getCurrentNarrative() {
        return currentNarrative;
    }

    public String getMarketChannel() {
        return marketChannel;
    }

    public void setMarketChannel(String marketChannel) {
        this.marketChannel = marketChannel;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setCurrentNarrative(java.lang.Integer currentNarrative) {
        this.currentNarrative = currentNarrative;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        ScorPltHeaderEntity that = (ScorPltHeaderEntity) object;

        if (scorPltHeaderId != that.scorPltHeaderId) return false;
        if (pltType != null ? !pltType.equals(that.pltType) : that.pltType != null) return false;
        if (publishToPricing != null ? !publishToPricing.equals(that.publishToPricing) : that.publishToPricing != null)
            return false;
        if (pltSimulationPeriods != null ? !pltSimulationPeriods.equals(that.pltSimulationPeriods) : that.pltSimulationPeriods != null)
            return false;
        if (generatedFromDefaultAdjustement != null ? !generatedFromDefaultAdjustement.equals(that.generatedFromDefaultAdjustement) : that.generatedFromDefaultAdjustement != null)
            return false;
        if (ccyCode != null ? !ccyCode.equals(that.ccyCode) : that.ccyCode != null) return false;
        if (geoCode != null ? !geoCode.equals(that.geoCode) : that.geoCode != null) return false;
        if (geoDescription != null ? !geoDescription.equals(that.geoDescription) : that.geoDescription != null)
            return false;
        if (rmsSimulationSet != null ? !rmsSimulationSet.equals(that.rmsSimulationSet) : that.rmsSimulationSet != null)
            return false;
        if (importSequence != null ? !importSequence.equals(that.importSequence) : that.importSequence != null)
            return false;
        if (threadName != null ? !threadName.equals(that.threadName) : that.threadName != null) return false;
        if (udName != null ? !udName.equals(that.udName) : that.udName != null) return false;
        if (userOccurrenceBasis != null ? !userOccurrenceBasis.equals(that.userOccurrenceBasis) : that.userOccurrenceBasis != null)
            return false;
        if (defaultPltName != null ? !defaultPltName.equals(that.defaultPltName) : that.defaultPltName != null)
            return false;
        if (truncationThreshold != null ? !truncationThreshold.equals(that.truncationThreshold) : that.truncationThreshold != null)
            return false;
        if (truncationExchangeRate != null ? !truncationExchangeRate.equals(that.truncationExchangeRate) : that.truncationExchangeRate != null)
            return false;
        if (truncationCurrency != null ? !truncationCurrency.equals(that.truncationCurrency) : that.truncationCurrency != null)
            return false;
        if (sourceLossModelingBasis != null ? !sourceLossModelingBasis.equals(that.sourceLossModelingBasis) : that.sourceLossModelingBasis != null)
            return false;
        if (deletedOn != null ? !deletedOn.equals(that.deletedOn) : that.deletedOn != null) return false;
        if (deletedDue != null ? !deletedDue.equals(that.deletedDue) : that.deletedDue != null) return false;
        if (deletedBy != null ? !deletedBy.equals(that.deletedBy) : that.deletedBy != null) return false;
        if (sourceLossEntityingBasis != null ? !sourceLossEntityingBasis.equals(that.sourceLossEntityingBasis) : that.sourceLossEntityingBasis != null)
            return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (lastModifiedBy != null ? !lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy != null)
            return false;
        if (lastModifiedOn != null ? !lastModifiedOn.equals(that.lastModifiedOn) : that.lastModifiedOn != null)
            return false;
        if (lastGenerated != null ? !lastGenerated.equals(that.lastGenerated) : that.lastGenerated != null)
            return false;
        if (basisChanged != null ? !basisChanged.equals(that.basisChanged) : that.basisChanged != null) return false;
        if (narrativeChanged != null ? !narrativeChanged.equals(that.narrativeChanged) : that.narrativeChanged != null)
            return false;
        if (previousNarrative != null ? !previousNarrative.equals(that.previousNarrative) : that.previousNarrative != null)
            return false;
        if (currentNarrative != null ? !currentNarrative.equals(that.currentNarrative) : that.currentNarrative != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + scorPltHeaderId;
        result = 31 * result + (pltType != null ? pltType.hashCode() : 0);
        result = 31 * result + (publishToPricing != null ? publishToPricing.hashCode() : 0);
        result = 31 * result + (pltSimulationPeriods != null ? pltSimulationPeriods.hashCode() : 0);
        result = 31 * result + (generatedFromDefaultAdjustement != null ? generatedFromDefaultAdjustement.hashCode() : 0);
        result = 31 * result + (ccyCode != null ? ccyCode.hashCode() : 0);
        result = 31 * result + (geoCode != null ? geoCode.hashCode() : 0);
        result = 31 * result + (geoDescription != null ? geoDescription.hashCode() : 0);
        result = 31 * result + (rmsSimulationSet != null ? rmsSimulationSet.hashCode() : 0);
        result = 31 * result + (importSequence != null ? importSequence.hashCode() : 0);
        result = 31 * result + (threadName != null ? threadName.hashCode() : 0);
        result = 31 * result + (udName != null ? udName.hashCode() : 0);
        result = 31 * result + (userOccurrenceBasis != null ? userOccurrenceBasis.hashCode() : 0);
        result = 31 * result + (defaultPltName != null ? defaultPltName.hashCode() : 0);
        result = 31 * result + (truncationThreshold != null ? truncationThreshold.hashCode() : 0);
        result = 31 * result + (truncationExchangeRate != null ? truncationExchangeRate.hashCode() : 0);
        result = 31 * result + (truncationCurrency != null ? truncationCurrency.hashCode() : 0);
        result = 31 * result + (sourceLossModelingBasis != null ? sourceLossModelingBasis.hashCode() : 0);
        result = 31 * result + (deletedOn != null ? deletedOn.hashCode() : 0);
        result = 31 * result + (deletedDue != null ? deletedDue.hashCode() : 0);
        result = 31 * result + (deletedBy != null ? deletedBy.hashCode() : 0);
        result = 31 * result + (sourceLossEntityingBasis != null ? sourceLossEntityingBasis.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
        result = 31 * result + (lastModifiedOn != null ? lastModifiedOn.hashCode() : 0);
        result = 31 * result + (lastGenerated != null ? lastGenerated.hashCode() : 0);
        result = 31 * result + (basisChanged != null ? basisChanged.hashCode() : 0);
        result = 31 * result + (narrativeChanged != null ? narrativeChanged.hashCode() : 0);
        result = 31 * result + (previousNarrative != null ? previousNarrative.hashCode() : 0);
        result = 31 * result + (currentNarrative != null ? currentNarrative.hashCode() : 0);
        return result;
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
    @JoinColumn(name = "targetRapId", referencedColumnName = "targetRapId", nullable = true, table = "")
    public TargetRapEntity getTargetRapByTargetRapId() {
        return targetRapByTargetRapId;
    }

    public void setTargetRapByTargetRapId(TargetRapEntity targetRapByTargetRapId) {
        this.targetRapByTargetRapId = targetRapByTargetRapId;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "regionPerilId", referencedColumnName = "regionPerilId", nullable = true, table = "")
    public RegionPerilEntity getRegionPerilByRegionPerilId() {
        return regionPerilByRegionPerilId;
    }

    public void setRegionPerilByRegionPerilId(RegionPerilEntity regionPerilByRegionPerilId) {
        this.regionPerilByRegionPerilId = regionPerilByRegionPerilId;
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
    @JoinColumn(name = "cloningSourceId", referencedColumnName = "scorPLTHeaderId", nullable = true, table = "")
    public ScorPltHeaderEntity getScorPltHeaderByCloningSourceId() {
        return scorPltHeaderByCloningSourceId;
    }

    public void setScorPltHeaderByCloningSourceId(ScorPltHeaderEntity scorPltHeaderByCloningSourceId) {
        this.scorPltHeaderByCloningSourceId = scorPltHeaderByCloningSourceId;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "fk_id_bin_file", referencedColumnName = "binFile_Id", nullable = true, table = "")
    public BinFileEntity getBinFile() {
        return binFile;
    }

    public void setBinFile(BinFileEntity binFile) {
        this.binFile = binFile;
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
