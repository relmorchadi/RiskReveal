package com.scor.rr.domain.entities.ihub;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the RRLossTable database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRLossTable")
@Data
public class RRLossTable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "RRLossTableId")
    private String rrLossTableId;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "RRAnalysisId")
    private String rrAnalysisId;
    @Column(name = "LossTableType")
    private String lossTableType;
    @Column(name = "OriginalTarget")
    private String originalTarget;
    @Column(name = "Currency")
    private String currency;
    @Column(name = "ExchangeRate")
    private Double exchangeRate;
    @Column(name = "Proportion")
    private Double proportion;
    @Column(name = "UnitsMultiplier")
    private Double unitsMultiplier;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "RegionPeril")
    private String regionPeril;
    @Column(name = "UserSelectedGrain")
    private String userSelectedGrain;
    @Column(name = "Note")
    private String note;
    @Column(name = "RRRepresentationDatasetId")
    private String rrRepresentationDatasetId;
    @Column(name = "SimulationPeriod")
    private String simulationPeriod;
    @Column(name = "FileDataFormat")
    private String fileDataFormat;
    @Column(name = "FileType")
    private String fileType;
    @Column(name = "CloningSourceId")
    private String cloningSourceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APSFileId")
    private LossDataFile apsFile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LossDataFileId")
    private LossDataFile lossDataFile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FinancialPerspectiveId")
    private RRFinancialPerspective financialPerspective;
    @OneToMany(mappedBy = "rrLossTable")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StatFile> statFiles;

}
