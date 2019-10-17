package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.meta.SourceEpHeader;
import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the RmsAnalysisBasic database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsAnalysisBasic")
@Data
public class RmsAnalysisBasic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsAnalysisBasicId")
    private Long rmsAnalysisBasicId;
    @Column(name = "RDMId")
    private Long rdmId;
    @Column(name = "RDMName")
    private String rdmName;
    @Column(name = "AnalysisId")
    private Long analysisId;
    @Column(name = "AnalysisName")
    private String analysisName;
    @Column(name = "Description")
    private String description;
    @Column(name = "EngineVersion")
    private String engineVersion;
    @Column(name = "GroupTypeName")
    private String groupTypeName;
    @Column(name = "Cedant")
    private String cedant;
    @Column(name = "LobName")
    private String LobName;
    @Column(name = "Grouping")
    private Boolean grouping;
    @Column(name = "EngineType")
    private String engineType;
    @Column(name = "RunDate")
    private Date runDate;
    @Column(name = "TypeName")
    private String typeName;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "SubPeril")
    private String subPeril;
    @Column(name = "LossAmplification")
    private String lossAmplification;
    @Column(name = "Region")
    private String region;
    @Column(name = "RegionName")
    private String regionName;
    @Column(name = "ModeName")
    private String modeName;
    @Column(name = "User1")
    private String user1;
    @Column(name = "User2")
    private String user2;
    @Column(name = "User3")
    private String user3;
    @Column(name = "User4")
    private String user4;
    @Column(name = "AnalysisCurrency")
    private String analysisCurrency;
    @Column(name = "StatusDescription")
    private String StatusDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ImportDecisionId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsModelDatasourceId")
    private RmsModelDatasource rmsModelDatasource;
    @OneToMany(mappedBy = "rmsAnalysisBasic")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SourceEpHeader> sourceEpHeader;
    @OneToMany(mappedBy = "rmsAnalysisBasic")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RmsAnalysisProfileRegion> sourceResultProfileRegions;

}
