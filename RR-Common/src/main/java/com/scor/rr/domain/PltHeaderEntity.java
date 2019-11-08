package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PLTHeader", schema = "dbo", catalog = "RiskReveal")
public class PltHeaderEntity {
    private int pltHeaderId;
    private EntityEntity entity;
    private String pltType;
    private RrAnalysisEntity rrAnalysisEntity;
    private TargetRapEntity targetRap;
    private RegionPerilEntity regionPeril;
    private ProjectEntity projectEntity;
    private Boolean locked;
    private Integer pltSimulationPeriods;
    private Boolean generatedFromDefaultAdjustment;
    private PltHeaderEntity cloningSource;
    private String ccyCode;
    private Date createdDate;
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
    private Integer inuringPackageId;
    private String sourceLossModelingBasis;
    private Timestamp deletedOn;
    private String deletedDue;
    private String deletedBy;
//    private String engineType;
//    private String createdBy;
//    private Timestamp createdOn;
//    private String lastModifiedBy;
//    private String lastModifiedOn;
//    private Timestamp lastGenerated;
//    private Boolean basisChanged;
//    private Boolean narrativeChanged;
//    private Integer previousNarrative;
//    private Integer currentNarrative;
//    private AdjustmentBasisEntity adjustmentBasisByFkPreviousBasisId;
//    private AdjustmentBasisEntity adjustmentBasisByFkCurrentBasisId;
    private MarketChannelEntity marketChannel;
    private BinFileEntity binFileEntity;
//    private WorkspaceEntity workspaceEntity;

    public PltHeaderEntity(PltHeaderEntity other) {
        this.pltType = other.pltType;
        this.locked = other.locked;
        this.pltSimulationPeriods = other.pltSimulationPeriods;
        this.generatedFromDefaultAdjustment = other.generatedFromDefaultAdjustment;
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
//        this.basisChanged = other.basisChanged;
        this.inuringPackageId = other.inuringPackageId;
//        this.engineType = other.engineType;
        this.rrAnalysisEntity = other.rrAnalysisEntity;
        this.targetRap = other.targetRap;
        this.regionPeril = other.regionPeril;
        this.projectEntity = other.projectEntity;
//        this.adjustmentBasisByFkPreviousBasisId = other.adjustmentBasisByFkPreviousBasisId;
//        this.adjustmentBasisByFkCurrentBasisId = other.adjustmentBasisByFkCurrentBasisId;
        this.entity = other.entity;
        this.marketChannel = other.marketChannel;
    }

    public PltHeaderEntity() {

    }

    @Basic
    @Column(name = "PLTType", length = 255)
    public String getPltType() {
        return pltType;
    }

    public void setPltType(String pltType) {
        this.pltType = pltType;
    }

    @Basic
    @Column(name = "Locked")
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Basic
    @Column(name = "PltSimulationPeriods")
    public Integer getPltSimulationPeriods() {
        return pltSimulationPeriods;
    }

    public void setPltSimulationPeriods(Integer pltSimulationPeriods) {
        this.pltSimulationPeriods = pltSimulationPeriods;
    }

    @Basic
    @Column(name = "GeneratedFromDefaultAdjustment")
    public Boolean getGeneratedFromDefaultAdjustment() {
        return generatedFromDefaultAdjustment;
    }

    public void setGeneratedFromDefaultAdjustment(Boolean generatedFromDefaultAdjustment) {
        this.generatedFromDefaultAdjustment = generatedFromDefaultAdjustment;
    }

    @Basic
    @Column(name = "CcyCode", length = 255)
    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    @Basic
    @Column(name = "GeoCode", length = 255)
    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    @Basic
    @Column(name = "GeoDescription", length = 255)
    public String getGeoDescription() {
        return geoDescription;
    }

    public void setGeoDescription(String geoDescription) {
        this.geoDescription = geoDescription;
    }

    @Basic
    @Column(name = "RmsSimulationSet")
    public Integer getRmsSimulationSet() {
        return rmsSimulationSet;
    }

    public void setRmsSimulationSet(Integer rmsSimulationSet) {
        this.rmsSimulationSet = rmsSimulationSet;
    }

    @Basic
    @Column(name = "ImportSequence")
    public Integer getImportSequence() {
        return importSequence;
    }

    public void setImportSequence(Integer importSequence) {
        this.importSequence = importSequence;
    }

    @Basic
    @Column(name = "ThreadName", length = 255)
    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Basic
    @Column(name = "UDName", length = 255)
    public String getUdName() {
        return udName;
    }

    public void setUdName(String udName) {
        this.udName = udName;
    }

    @Basic
    @Column(name = "UserOccurrenceBasis", length = 255)
    public String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }

    @Basic
    @Column(name = "DefaultPltName", length = 255)
    public String getDefaultPltName() {
        return defaultPltName;
    }

    public void setDefaultPltName(String defaultPltName) {
        this.defaultPltName = defaultPltName;
    }

    @Basic
    @Column(name = "TruncationThreshold", length = 255)
    public String getTruncationThreshold() {
        return truncationThreshold;
    }

    public void setTruncationThreshold(String truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
    }

    @Basic
    @Column(name = "TruncationExchangeRate", length = 255)
    public String getTruncationExchangeRate() {
        return truncationExchangeRate;
    }

    public void setTruncationExchangeRate(String truncationExchangeRate) {
        this.truncationExchangeRate = truncationExchangeRate;
    }

    @Basic
    @Column(name = "TruncationCurrency", length = 255)
    public String getTruncationCurrency() {
        return truncationCurrency;
    }

    public void setTruncationCurrency(String truncationCurrency) {
        this.truncationCurrency = truncationCurrency;
    }

    @Basic
    @Column(name = "SourceLossModelingBasis", length = 255)
    public String getSourceLossModelingBasis() {
        return sourceLossModelingBasis;
    }

    public void setSourceLossModelingBasis(String sourceLossModelingBasis) {
        this.sourceLossModelingBasis = sourceLossModelingBasis;
    }

    @Basic
    @Column(name = "DeletedOn")
    public Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Basic
    @Column(name = "DeletedDue", length = 255)
    public String getDeletedDue() {
        return deletedDue;
    }

    public void setDeletedDue(String deletedDue) {
        this.deletedDue = deletedDue;
    }

    @Basic
    @Column(name = "DeletedBy", length = 255)
    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }


//    @Basic
//    @Column(name = "created_by", length = 255)
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    @Basic
//    @Column(name = "created_on")
//    public Timestamp getCreatedOn() {
//        return createdOn;
//    }
//
//    public void setCreatedOn(Timestamp createdOn) {
//        this.createdOn = createdOn;
//    }
//
//    @Basic
//    @Column(name = "last_modified_by", length = 255)
//    public String getLastModifiedBy() {
//        return lastModifiedBy;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }
//
//    @Basic
//    @Column(name = "last_modified_on", length = 255)
//    public String getLastModifiedOn() {
//        return lastModifiedOn;
//    }
//
//    public void setLastModifiedOn(String lastModifiedOn) {
//        this.lastModifiedOn = lastModifiedOn;
//    }
//
//    @Basic
//    @Column(name = "last_generated")
//    public Timestamp getLastGenerated() {
//        return lastGenerated;
//    }
//
//    public void setLastGenerated(Timestamp lastGenerated) {
//        this.lastGenerated = lastGenerated;
//    }

//    @Basic
//    @Column(name = "basis_changed")
//    public Boolean getBasisChanged() {
//        return basisChanged;
//    }
//
//    public void setBasisChanged(Boolean basisChanged) {
//        this.basisChanged = basisChanged;
//    }
//
//    @Basic
//    @Column(name = "narrative_changed")
//    public Boolean getNarrativeChanged() {
//        return narrativeChanged;
//    }
//
//    public void setNarrativeChanged(Boolean narrativeChanged) {
//        this.narrativeChanged = narrativeChanged;
//    }
//
//    @Basic
//    @Column(name = "previous_narrative")
//    public Integer getPreviousNarrative() {
//        return previousNarrative;
//    }
//
//    public void setPreviousNarrative(Integer previousNarrative) {
//        this.previousNarrative = previousNarrative;
//    }
//
//    @Basic
//    @Column(name = "current_narrative")
//    public Integer getCurrentNarrative() {
//        return currentNarrative;
//    }
//
//    public void setCurrentNarrative(Integer currentNarrative) {
//        this.currentNarrative = currentNarrative;
//    }

    @Basic
    @Column(name = "CreatedDate")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "InuringPackageId")
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

//    @Basic
//    @Column(name = "pltLossDataFileName", length = 255)
//    public String getPltLossDataFileName() {
//        return pltLossDataFileName;
//    }
//
//    public void setPltLossDataFileName(String pltLossDataFileName) {
//        this.pltLossDataFileName = pltLossDataFileName;
//    }
//
//    @Basic
//    @Column(name = "pltLossDataFilePath", length = 255)
//    public String getPltLossDataFilePath() {
//        return pltLossDataFilePath;
//    }
//
//    public void setPltLossDataFilePath(String pltLossDataFilePath) {
//        this.pltLossDataFilePath = pltLossDataFilePath;
//    }

//    @Basic
//    @Column(name = "EngineType", length = 255)
//    public String getEngineType() {
//        return engineType;
//    }
//
//    public void setEngineType(String engineType) {
//        this.engineType = engineType;
//    }

    @Id
    @Column(name = "PltHeaderId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getPltHeaderId() {
        return pltHeaderId;
    }

    public void setPltHeaderId(int pltHeaderId) {
        this.pltHeaderId = pltHeaderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PltHeaderEntity that = (PltHeaderEntity) o;
        return pltHeaderId == that.pltHeaderId &&
                Objects.equals(pltType, that.pltType) &&
                Objects.equals(locked, that.locked) &&
                Objects.equals(pltSimulationPeriods, that.pltSimulationPeriods) &&
                Objects.equals(generatedFromDefaultAdjustment, that.generatedFromDefaultAdjustment) &&
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
//                Objects.equals(createdBy, that.createdBy) &&
//                Objects.equals(createdOn, that.createdOn) &&
//                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
//                Objects.equals(lastModifiedOn, that.lastModifiedOn) &&
//                Objects.equals(lastGenerated, that.lastGenerated) &&
//                Objects.equals(basisChanged, that.basisChanged) &&
//                Objects.equals(narrativeChanged, that.narrativeChanged) &&
//                Objects.equals(previousNarrative, that.previousNarrative) &&
//                Objects.equals(currentNarrative, that.currentNarrative) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(inuringPackageId, that.inuringPackageId);
//                Objects.equals(pltLossDataFileName, that.pltLossDataFileName) &&
//                Objects.equals(pltLossDataFilePath, that.pltLossDataFilePath) &&
//                Objects.equals(engineType, that.engineType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pltType, locked, pltSimulationPeriods, generatedFromDefaultAdjustment, ccyCode, geoCode, geoDescription, rmsSimulationSet, importSequence, threadName, udName, userOccurrenceBasis, defaultPltName, truncationThreshold, truncationExchangeRate, truncationCurrency, sourceLossModelingBasis, deletedOn, deletedDue, deletedBy, createdDate, inuringPackageId, pltHeaderId);
    }

    @ManyToOne
    @JoinColumn(name = "RRAnalysisId", referencedColumnName = "rrAnalysisId")
    public RrAnalysisEntity getRrAnalysisEntity() {
        return rrAnalysisEntity;
    }

    public void setRrAnalysisEntity(RrAnalysisEntity rrAnalysisEntity) {
        this.rrAnalysisEntity = rrAnalysisEntity;
    }

    @ManyToOne
    @JoinColumn(name = "TargetRapId", referencedColumnName = "targetRapId")
    public TargetRapEntity getTargetRap() {
        return targetRap;
    }

    public void setTargetRap(TargetRapEntity targetRap) {
        this.targetRap = targetRap;
    }

    @ManyToOne
    @JoinColumn(name = "RegionPerilId", referencedColumnName = "regionPerilId" ,insertable = false,updatable = false)
    public RegionPerilEntity getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(RegionPerilEntity regionPeril) {
        this.regionPeril = regionPeril;
    }

    @ManyToOne
    @JoinColumn(name = "ProjectId", referencedColumnName = "projectId")
    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

//    @ManyToOne
//    @JoinColumn(name = "PreviousBasisId", referencedColumnName = "AdjustmentBasisId")
//    public AdjustmentBasisEntity getAdjustmentBasisByFkPreviousBasisId() {
//        return adjustmentBasisByFkPreviousBasisId;
//    }
//
//    public void setAdjustmentBasisByFkPreviousBasisId(AdjustmentBasisEntity adjustmentBasisByFkPreviousBasisId) {
//        this.adjustmentBasisByFkPreviousBasisId = adjustmentBasisByFkPreviousBasisId;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "CurrentBasisId", referencedColumnName = "AdjustmentBasisId")
//    public AdjustmentBasisEntity getAdjustmentBasisByFkCurrentBasisId() {
//        return adjustmentBasisByFkCurrentBasisId;
//    }
//
//    public void setAdjustmentBasisByFkCurrentBasisId(AdjustmentBasisEntity adjustmentBasisByFkCurrentBasisId) {
//        this.adjustmentBasisByFkCurrentBasisId = adjustmentBasisByFkCurrentBasisId;
//    }

    @ManyToOne
    @JoinColumn(name = "EntityId", referencedColumnName = "EntityId",insertable = false,updatable = false)
    public EntityEntity getEntity() {
        return entity;
    }

    public void setEntity(EntityEntity entity) {
        this.entity = entity;
    }

    @ManyToOne
    @JoinColumn(name = "MarketChannelId", referencedColumnName = "MarketChannelID",insertable = false,updatable = false)
    public MarketChannelEntity getMarketChannel() {
        return marketChannel;
    }

    public void setMarketChannel(MarketChannelEntity marketChannel) {
        this.marketChannel = marketChannel;
    }

    @ManyToOne
    @JoinColumn(name = "CloningSourceId", referencedColumnName = "PltHeaderId",insertable = false,updatable = false)
    public PltHeaderEntity getCloningSource() {
        return cloningSource;
    }

    public void setCloningSource(PltHeaderEntity cloningSource) {
        this.cloningSource = cloningSource;
    }
    @ManyToOne
    @JoinColumn(name = "BinFileId", referencedColumnName = "BinFileId",insertable = false,updatable = false)
    public BinFileEntity getBinFileEntity() {
        return binFileEntity;
    }

    public void setBinFileEntity(BinFileEntity binFileEntity) {
        this.binFileEntity = binFileEntity;
    }

//    @ManyToOne
//    @JoinColumn(name = "workspaceId", referencedColumnName = "workspaceId",insertable = false,updatable = false)
//    public WorkspaceEntity getWorkspaceEntity() {
//        return workspaceEntity;
//    }
//
//    public void setWorkspaceEntity(WorkspaceEntity workspaceEntity) {
//        this.workspaceEntity = workspaceEntity;
//    }
}
