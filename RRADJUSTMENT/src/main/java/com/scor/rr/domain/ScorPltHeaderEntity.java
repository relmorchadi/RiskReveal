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

    public ScorPltHeaderEntity() {
    }

    @Id
    @Column(name = "id_scorpltheader", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getScorPltHeaderId() {
        return scorPltHeaderId;
    }

    public void setScorPltHeaderId(int scorPltHeaderId) {
        this.scorPltHeaderId = scorPltHeaderId;
    }

    @Basic
    @Column(name = "pltType", length = 255)
    public String getPltType() {
        return pltType;
    }

    public void setPltType(String pltType) {
        this.pltType = pltType;
    }

    @Basic
    @Column(name = "publishToPricing")
    public Boolean getPublishToPricing() {
        return publishToPricing;
    }

    public void setPublishToPricing(Boolean publishToPricing) {
        this.publishToPricing = publishToPricing;
    }

    @Basic
    @Column(name = "pltSimulationPeriods")
    public Integer getPltSimulationPeriods() {
        return pltSimulationPeriods;
    }

    public void setPltSimulationPeriods(Integer pltSimulationPeriods) {
        this.pltSimulationPeriods = pltSimulationPeriods;
    }

    @Basic
    @Column(name = "generatedFromDefaultAdjustement")
    public Boolean getGeneratedFromDefaultAdjustement() {
        return generatedFromDefaultAdjustement;
    }

    public void setGeneratedFromDefaultAdjustement(Boolean generatedFromDefaultAdjustement) {
        this.generatedFromDefaultAdjustement = generatedFromDefaultAdjustement;
    }

    @Basic
    @Column(name = "ccyCode", length = 255)
    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    @Basic
    @Column(name = "geoCode", length = 255)
    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    @Basic
    @Column(name = "geoDescription", length = 255)
    public String getGeoDescription() {
        return geoDescription;
    }

    public void setGeoDescription(String geoDescription) {
        this.geoDescription = geoDescription;
    }

    @Basic
    @Column(name = "rmsSimulationSet")
    public Integer getRmsSimulationSet() {
        return rmsSimulationSet;
    }

    public void setRmsSimulationSet(Integer rmsSimulationSet) {
        this.rmsSimulationSet = rmsSimulationSet;
    }

    @Basic
    @Column(name = "importSequence")
    public Integer getImportSequence() {
        return importSequence;
    }

    public void setImportSequence(Integer importSequence) {
        this.importSequence = importSequence;
    }

    @Basic
    @Column(name = "threadName", length = 255)
    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Basic
    @Column(name = "udName", length = 255)
    public String getUdName() {
        return udName;
    }

    public void setUdName(String udName) {
        this.udName = udName;
    }

    @Basic
    @Column(name = "userOccurrenceBasis", length = 255)
    public String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }

    @Basic
    @Column(name = "defaultPltName", length = 255)
    public String getDefaultPltName() {
        return defaultPltName;
    }

    public void setDefaultPltName(String defaultPltName) {
        this.defaultPltName = defaultPltName;
    }

    @Basic
    @Column(name = "truncationThreshold", length = 255)
    public String getTruncationThreshold() {
        return truncationThreshold;
    }

    public void setTruncationThreshold(String truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
    }

    @Basic
    @Column(name = "truncationExchangeRate", length = 255)
    public String getTruncationExchangeRate() {
        return truncationExchangeRate;
    }

    public void setTruncationExchangeRate(String truncationExchangeRate) {
        this.truncationExchangeRate = truncationExchangeRate;
    }

    @Basic
    @Column(name = "truncationCurrency", length = 255)
    public String getTruncationCurrency() {
        return truncationCurrency;
    }

    public void setTruncationCurrency(String truncationCurrency) {
        this.truncationCurrency = truncationCurrency;
    }

    @Basic
    @Column(name = "sourceLossModelingBasis", length = 255)
    public String getSourceLossModelingBasis() {
        return sourceLossModelingBasis;
    }

    public void setSourceLossModelingBasis(String sourceLossModelingBasis) {
        this.sourceLossModelingBasis = sourceLossModelingBasis;
    }

    @Basic
    @Column(name = "deletedOn")
    public Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Basic
    @Column(name = "deletedDue", length = 255)
    public String getDeletedDue() {
        return deletedDue;
    }

    public void setDeletedDue(String deletedDue) {
        this.deletedDue = deletedDue;
    }

    @Basic
    @Column(name = "deletedBy", length = 255)
    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Basic
    @Column(name = "sourceLossEntityingBasis", length = 255)
    public String getSourceLossEntityingBasis() {
        return sourceLossEntityingBasis;
    }

    public void setSourceLossEntityingBasis(String sourceLossEntityingBasis) {
        this.sourceLossEntityingBasis = sourceLossEntityingBasis;
    }

    @Basic
    @Column(name = "created_by", length = 255)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_on")
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "last_modified_by", length = 255)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_on", length = 255)
    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Basic
    @Column(name = "last_generated")
    public Timestamp getLastGenerated() {
        return lastGenerated;
    }

    public void setLastGenerated(Timestamp lastGenerated) {
        this.lastGenerated = lastGenerated;
    }

    @Basic
    @Column(name = "basis_changed")
    public Boolean getBasisChanged() {
        return basisChanged;
    }

    public void setBasisChanged(Boolean basisChanged) {
        this.basisChanged = basisChanged;
    }

    @Basic
    @Column(name = "narrative_changed")
    public Boolean getNarrativeChanged() {
        return narrativeChanged;
    }

    public void setNarrativeChanged(Boolean narrativeChanged) {
        this.narrativeChanged = narrativeChanged;
    }

    @Basic
    @Column(name = "previous_narrative")
    public Integer getPreviousNarrative() {
        return previousNarrative;
    }

    public void setPreviousNarrative(Integer previousNarrative) {
        this.previousNarrative = previousNarrative;
    }

    @Basic
    @Column(name = "current_narrative")
    public Integer getCurrentNarrative() {
        return currentNarrative;
    }

    public void setCurrentNarrative(Integer currentNarrative) {
        this.currentNarrative = currentNarrative;
    }

    @Basic
    @Column(name = "createdDate")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "inuringPackageId")
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Basic
    @Column(name = "perilCode", length = 255)
    public String getPerilCode() {
        return perilCode;
    }

    public void setPerilCode(String perilCode) {
        this.perilCode = perilCode;
    }

    @Basic
    @Column(name = "pltLossDataFileName", length = 255)
    public String getPltLossDataFileName() {
        return pltLossDataFileName;
    }

    public void setPltLossDataFileName(String pltLossDataFileName) {
        this.pltLossDataFileName = pltLossDataFileName;
    }

    @Basic
    @Column(name = "pltLossDataFilePath", length = 255)
    public String getPltLossDataFilePath() {
        return pltLossDataFilePath;
    }

    public void setPltLossDataFilePath(String pltLossDataFilePath) {
        this.pltLossDataFilePath = pltLossDataFilePath;
    }

    @Basic
    @Column(name = "engine_type", length = 255)
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(scorPltHeaderId, pltType, publishToPricing, pltSimulationPeriods, generatedFromDefaultAdjustement, ccyCode, geoCode, geoDescription, rmsSimulationSet, importSequence, threadName, udName, userOccurrenceBasis, defaultPltName, truncationThreshold, truncationExchangeRate, truncationCurrency, sourceLossModelingBasis, deletedOn, deletedDue, deletedBy, sourceLossEntityingBasis, createdBy, createdOn, lastModifiedBy, lastModifiedOn, lastGenerated, basisChanged, narrativeChanged, previousNarrative, currentNarrative, createdDate, inuringPackageId, perilCode, pltLossDataFileName, pltLossDataFilePath, engineType, entity);
    }

    @ManyToOne
    @JoinColumn(name = "cloningSourceId", referencedColumnName = "id_scorpltheader")
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
    @JoinColumn(name = "id_bin_file", referencedColumnName = "binFile_Id", table = "")
    public BinFileEntity getBinFile() {
        return binFile;
    }

    public void setBinFile(BinFileEntity binFile) {
        this.binFile = binFile;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "rrAnalysisId", referencedColumnName = "rrAnalysisId", table = "")
    public RrAnalysisNewEntity getRrAnalysis() {
        return rrAnalysis;
    }

    public void setRrAnalysis(RrAnalysisNewEntity rrAnalysisNewByRrAnalysisId) {
        this.rrAnalysis = rrAnalysisNewByRrAnalysisId;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "id_target_rap", referencedColumnName = "targetRapId", table = "")
    public TargetRapEntity getTargetRap() {
        return targetRap;
    }

    public void setTargetRap(TargetRapEntity targetRap) {
        this.targetRap = targetRap;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "regionPerilId", referencedColumnName = "regionPerilId", table = "")
    public RegionPerilEntity getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(RegionPerilEntity regionPeril) {
        this.regionPeril = regionPeril;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "projectId", referencedColumnName = "projectId", table = "")
    public ProjectEntity getProject() {
        return projectByProjectId;
    }

    public void setProject(ProjectEntity projectByProjectId) {
        this.projectByProjectId = projectByProjectId;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "previous_basis", referencedColumnName = "code", table = "")
    public AdjustmentBasisEntity getAdjustmentBasisPrevious() {
        return adjustmentBasisPrevious;
    }

    public void setAdjustmentBasisPrevious(AdjustmentBasisEntity adjustmentBasisByPreviousBasis) {
        this.adjustmentBasisPrevious = adjustmentBasisByPreviousBasis;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "current_basis", referencedColumnName = "code", table = "")
    public AdjustmentBasisEntity getAdjustmentBasisCurrent() {
        return adjustmentBasisCurrent;
    }

    public void setAdjustmentBasisCurrent(AdjustmentBasisEntity adjustmentBasisByCurrentBasis) {
        this.adjustmentBasisCurrent = adjustmentBasisByCurrentBasis;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ScorPltHeaderEntity(ScorPltHeaderEntity scorPltHeaderEntity) {
        this.pltType = scorPltHeaderEntity.pltType;
        this.publishToPricing = scorPltHeaderEntity.publishToPricing;
        this.pltSimulationPeriods = scorPltHeaderEntity.pltSimulationPeriods;
        this.generatedFromDefaultAdjustement = scorPltHeaderEntity.generatedFromDefaultAdjustement;
        this.ccyCode = scorPltHeaderEntity.ccyCode;
        this.geoCode = scorPltHeaderEntity.geoCode;
        this.geoDescription = scorPltHeaderEntity.geoDescription;
        this.rmsSimulationSet = scorPltHeaderEntity.rmsSimulationSet;
        this.importSequence = scorPltHeaderEntity.importSequence;
        this.threadName = scorPltHeaderEntity.threadName;
        this.udName = scorPltHeaderEntity.udName;
        this.userOccurrenceBasis = scorPltHeaderEntity.userOccurrenceBasis;
        this.defaultPltName = scorPltHeaderEntity.defaultPltName;
        this.truncationThreshold = scorPltHeaderEntity.truncationThreshold;
        this.truncationExchangeRate = scorPltHeaderEntity.truncationExchangeRate;
        this.truncationCurrency = scorPltHeaderEntity.truncationCurrency;
        this.sourceLossModelingBasis = scorPltHeaderEntity.sourceLossModelingBasis;
        this.sourceLossEntityingBasis = scorPltHeaderEntity.sourceLossEntityingBasis;
        this.basisChanged = scorPltHeaderEntity.basisChanged;
        this.narrativeChanged = scorPltHeaderEntity.narrativeChanged;
        this.previousNarrative = scorPltHeaderEntity.previousNarrative;
        this.currentNarrative = scorPltHeaderEntity.currentNarrative;
        this.inuringPackageId = scorPltHeaderEntity.inuringPackageId;
        this.perilCode = scorPltHeaderEntity.perilCode;
        this.pltLossDataFileName = scorPltHeaderEntity.pltLossDataFileName;
        this.pltLossDataFilePath = scorPltHeaderEntity.pltLossDataFilePath;
        this.engineType = scorPltHeaderEntity.engineType;
        this.rrAnalysis = scorPltHeaderEntity.rrAnalysis;
        this.targetRap = scorPltHeaderEntity.targetRap;
        this.regionPeril = scorPltHeaderEntity.regionPeril;
        this.projectByProjectId = scorPltHeaderEntity.projectByProjectId;
        this.scorPltHeaderByCloningSourceId = scorPltHeaderEntity.scorPltHeaderByCloningSourceId;
        this.entity = scorPltHeaderEntity.entity;
        this.marketChannel = scorPltHeaderEntity.marketChannel;
        this.adjustmentBasisPrevious = scorPltHeaderEntity.adjustmentBasisPrevious;
        this.adjustmentBasisCurrent = scorPltHeaderEntity.adjustmentBasisCurrent;
    }
}
