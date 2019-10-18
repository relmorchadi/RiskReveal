package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.meta.SourceEpHeader;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the RmsAnalysis database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsAnalysis")
@Data
public class RmsAnalysis {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "RmsAnalysisId")
    private String rmsAnalysisId;
    @Column(name = "RDMId")
    private Long rdmId;
    @Column(name = "RDMName")
    private String rdmName;
    @Column(name = "AnalysisId")
    private String analysisId;
    @Column(name = "AnalysisName")
    private String analysisName;
    @Column(name = "Description")
    private String description;
    @Column(name = "DefaultGrain")
    private String defaultGrain;
    @Column(name = "ExposureType")
    private String exposureType;
    @Column(name = "ExposureTypeCode")
    private Integer exposureTypeCode;
    @Column(name = "EDMNameSourceLink")
    private String edmNameSourceLink;
    @Column(name = "ExposureId")
    private Long exposureId;
    @Column(name = "AnalysisCurrency")
    private String analysisCurrency;
    @Column(name = "RmsExchangeRate")
    private BigDecimal rmsExchangeRate;
    @Column(name = "TypeCode")
    private Integer typeCode;
    @Column(name = "AnalysisType")
    private String analysisType;
    @Column(name = "RunDate")
    private Date runDate;
    @Column(name = "Region")
    private String region;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "RPCode")
    private String rpCode;
    @Column(name = "SubPeril")
    private String subPeril;
    @Column(name = "LossAmplification")
    private String lossAmplification;
    @Column(name = "Status")
    private Long status;
    @Column(name = "AnalysisMode")
    private Integer analysisMode;
    @Column(name = "EngineTypeCode")
    private Integer engineTypeCode;
    @Column(name = "EngineType")
    private String engineType;
    @Column(name = "EngineVersion")
    private String engineVersion;
    @Column(name = "EngineVersionMajor")
    private String engineVersionMajor;
    @Column(name = "ProfileName")
    private String profileName;
    @Column(name = "ProfileKey")
    private String profileKey;
    @Column(name = "IsValidForExtract")
    private Boolean isValidForExtract;
    @Column(name = "NotValidReason")
    private String notValidReason;
    @Column(name = "PurePremium")
    private BigDecimal purePremium;
    @Column(name = "ExposureTiv")
    private Double exposureTiv;
    @Column(name = "GeoCode")
    private String geoCode;
    @Column(name = "GeoDescription")
    private String geoDescription;
    @Column(name = "User1")
    private String user1;
    @Column(name = "User2")
    private String user2;
    @Column(name = "User3")
    private String user3;
    @Column(name = "User4")
    private String user4;
    @Column(name = "StatusDescription")
    private String StatusDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsModelDatasourceId")
    private RmsModelDatasource rmsModelDatasource;
    @OneToMany(mappedBy = "rmsAnalysis")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SourceEpHeader> sourceEpHeader;
    @OneToMany(mappedBy = "rmsAnalysis")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AnalysisTreatyStructure> analysisTreatyStructures;
    @OneToMany(mappedBy = "rmsAnalysis")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RmsAnalysisProfileRegion> sourceResultProfileRegions;

    public RmsAnalysis() {
    }
}
