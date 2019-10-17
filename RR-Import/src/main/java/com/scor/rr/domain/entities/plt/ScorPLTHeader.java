package com.scor.rr.domain.entities.plt;

import com.scor.rr.domain.entities.cat.meta.CatAnalysisDefinition;
import com.scor.rr.domain.entities.plt.meta.PLTModelingBasis;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.references.TargetRAP;
import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.references.plt.TTFinancialPerspective;
import com.scor.rr.domain.entities.stat.RRStatisticHeader;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.PLTStatus;
import com.scor.rr.domain.enums.PLTType;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the ScorPLTHeader database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "TTScorPLTHeader")
@Data
public class ScorPLTHeader implements Cloneable {
    @Id
    @Column(name = "ScorPLTHeaderId")
    private String scorPLTHeaderId;
    @Column(name = "PLTType")
    private PLTType pltType;
    @Column(name = "PublishToPricing")
    private Boolean publishToPricing;
    @Column(name = "Note")
    private String note;
    @Column(name = "PLTSimulationPeriods")
    private Integer pltSimulationPeriods;
    @Column(name = "GeneratedFromDefaultAdjustement")
    private Boolean generatedFromDefaultAdjustement;
    @Column(name = "PLTLossDataFilePath")
    private String pltLossDataFilePath;
    @Column(name = "PLTLossDataFileName")
    private String pltLossDataFileName;
    @Column(name = "CreationDate")
    private Date creationDate;
    @Column(name = "RmsSimulationSet")
    private Integer rmsSimulationSet;
    @Column(name = "ImportSequence")
    private Long importSequence;
    @Column(name = "truncationCurrency")
    private String truncationCurrency;
    @Column(name = "ThreadName")
    private String threadName;
    @Column(name = "UdName")
    private String udName;
    @Column(name = "UserOccurrenceBasis")
    private String userOccurrenceBasis;
    @Column(name = "DefaultPltName")
    private String defaultPltName;
    @Column(name = "TruncationThreshold")
    private Double truncationThreshold;
    @Column(name = "truncationThresholdEur")
    private Double truncationThresholdEur;
    @Column(name = "TruncationExchangeRate")
    private String truncationExchangeRate;
    @Column(name = "TruncationCcy")
    private String truncationCcy;
    @Column(name = "SourceLossModellingBasis")
    private String sourceLossModellingBasis;
    @Column(name = "DeletedOn")
    private Date deletedOn;
    @Column(name = "DeletedDue")
    private String deletedDue;
    @JoinColumn(name = "RRAnalysisId")
    private String rrAnalysisId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TargetRAPId")
    private TargetRAP targetRAP;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RegionPerilId")
    private RegionPeril regionPeril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    //@Column(name = "FinancialPerspective")
    @OneToOne
    private TTFinancialPerspective financialPerspective;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CcyId")
    private Currency ccy;
    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "InuringPackageId")
//    private InuringPackage inuringPackage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DeletedBy")
    private User deletedBy;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceScorPLTHeaderId")
    private List<ScorPLTHeader> sourceScorPLTHeader;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "sourceScorPLTHeader")
//    @JoinTable(name = "Cloned_PLTS", joinColumns = {
//            @JoinColumn(name = "SourceScorPLTHeaderId")}, inverseJoinColumns = {
//            @JoinColumn(name = "ClonedScorPLTHeaderId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ScorPLTHeader> clonedScorPLTHeaders;
    @Transient
    private BinFile pltLossDataFile;
    @Transient
    private BinFile peqtFile;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "RRStatisticHeader")
    private List<RRStatisticHeader> pltStatisticList;
    //    private PLTGrouping pltGrouping;
//    private PLTInuring pltInuring = PLTInuring.None;
    @Transient
    private PLTStatus pltStatus;
    //    private InuringPackage inuringPackageDefinition;
    private String rrRepresentationDatasetId;
    private String rrLossTableId;
    @ManyToOne
    private Currency currency;
    //    private AdjustmentStructure adjustmentStructure;
    // FIXME : define relationship to CatAnalysisDefinition
    @OneToOne
    private CatAnalysisDefinition catAnalysisDefinition;
    private String systemShortName;
    private String userShortName;
    // NOTA tags must be copy from Analysis
    @Transient
    private List<String> tags;
    private Date xActPublicationDate = null;
    private Boolean xActUsed;
    private Boolean xActAvailable;
    private String xActModelVersion;
    private String userSelectedGrain;
    private Boolean exportedDPM;
    private String geoCode;
    private String geoDescription;
    private String perilCode;
    private String engineType;
    private String instanceId;
    private PLTModelingBasis sourceLossModelingBasis;
    private String pltName;
    private Boolean addedToBasket = null;
    private Boolean publishToArc = null;
    @OneToOne
    private BinFile pltLossDataFileHadoop = null;

    /**
     * clone ScorPLTHeader
     */
    public Object clone() throws CloneNotSupportedException {
        ScorPLTHeader clone = (ScorPLTHeader) super.clone();
        clone.setScorPLTHeaderId(null);
        clone.setXActPublicationDate(null);
        clone.setAddedToBasket(null);
        clone.setPublishToArc(null);
        clone.setPltLossDataFileHadoop(null);
        return clone;
    }

}
