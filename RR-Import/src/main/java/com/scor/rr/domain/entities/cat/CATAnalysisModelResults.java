package com.scor.rr.domain.entities.cat;

import com.scor.rr.domain.entities.references.cat.ModelResultsDataSource;
import com.scor.rr.domain.entities.references.cat.Narrative;
import com.scor.rr.domain.entities.references.omega.PeriodBasis;
import com.scor.rr.domain.entities.references.plt.FinancialPerspective;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the CATAnalysisModelResults database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "CATAnalysisModelResults")
@Data
public class CATAnalysisModelResults {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATAnalysisModelResultsId")
    private String CATAnalysisModelResultsId;
    @Column(name = "Version")
    private Integer version;
    @Column(name = "DivisionNumber")
    private Integer divisionNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NarrativeId")
    private Narrative narrative;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CatRequestId")
    private CATRequest catRequest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CatAnalysisId")
    private CATAnalysis catAnalysis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PeriodBasisId")
    private PeriodBasis periodBasis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModelDatasourceId")
    private ModelResultsDataSource modelDatasource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FinancialPerspectiveELTId")
    private FinancialPerspective financialPerspectiveELT;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FinancialPerspectiveStatsId")
    private FinancialPerspective financialPerspectiveStats;
}
