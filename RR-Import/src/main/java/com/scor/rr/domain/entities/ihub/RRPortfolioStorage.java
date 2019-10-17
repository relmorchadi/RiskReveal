package com.scor.rr.domain.entities.ihub;

import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.domain.enums.ExposureSummaryExtractType;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the RRPortfolioStorage database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRPortfolioStorage")
@Data
public class RRPortfolioStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RRPortfolioStorageId")
    private Long rrPortfolioStorageId;
    @Column(name = "OriginalExposureHeader")
    private String originalExposureHeader;
    @Column(name = "DataSubType")
    private String dataSubType;
    @Column(name = "OriginalTarget")
    private String originalTarget;
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "FilePath")
    private String filePath;
    @Column(name = "SimulationPeriod")
    private String simulationPeriod;
    @Column(name = "IsPublished")
    private Boolean isPublished;
    @Column(name = "PublishDate")
    private Date publishDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RRPortfolioId")
    private RRPortfolio rrPortfolio;
    @Column(name = "DataType")
    private ExposureSummaryExtractType dataType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PublishBy")
    private User publishBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Currency")
    private Currency currency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
}
