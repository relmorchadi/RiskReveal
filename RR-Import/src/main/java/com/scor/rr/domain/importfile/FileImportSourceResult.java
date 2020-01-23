package com.scor.rr.domain.importfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "FileImportSourceResult", schema = "dbonew", catalog = "RiskReveal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileImportSourceResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "FileImportSourceResultId", nullable = false)
    private int fileImportSourceResultId;

    @Column(name = "FileBasedImportConfig")
    private int fileBasedImportConfig;

    @Column(name = "ResultName")
    private String resultName;

    @Column(name = "FileBasedImportConfigId")
    private int fileBasedImportConfigId;

    @Column(name = "TargetRAPCode", length = 20)
    private String targetRAPCode;

    @Column(name = "SourceFileImportId")
    private int sourceFileImportId;

    @Column(name = "ProjectId")
    private int projectId;

    @Column(name = "AvailableRegionPerils")
    private String availableRegionPerils;

    @Column(name = "TargetCurrency", length = 3)
    private String targetCurrency;

    @Column(name = "FinancialPerspective", length = 125)
    private String financialPerspective;

    @Column(name = "UnitMultiplier", precision = 20, scale = 8)
    private BigDecimal unitMultiplier;

    @Column(name = "Proportion", precision = 20, scale = 8)
    private BigDecimal proportion;

    @Column(name = "ModelVersion", length = 10)
    private String modelVersion;

    @Column(name = "SelectedRegionPerilCode", length = 15)
    private String selectedRegionPerilCode;

    @Column(name = "SourceCurrency", length = 3)
    private String sourceCurrency;

    @Column(name = "TargetCurrencyBasis", length = 15)
    private String targetCurrencyBasis;

    @Column(name = "DataSource")
    private String dataSource;

    @Column(name = "FileName")
    private String fileName;

    @Column(name = "ImportUser")
    private int importUser;

    @Column(name = "PLTSimulationPeriods")
    private int PLTSimulationPeriods;

    @Column(name = "Grain", length = 100)
    private String grain;

    @Column(name = "OverrideReasonText")
    private String overrideReasonText;

    @Column(name = "ImportedAtLeastOnce")
    private boolean importedAtLeastOnce;

    @Column(name = "ImportStatus", length = 15)
    private String importStatus;

    @Column(name = "FilePath")
    private String filePath;
}
