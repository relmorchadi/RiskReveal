package com.scor.rr.domain.entities.cat;

import com.scor.rr.domain.entities.references.cat.ModelResultsDataSource;
import com.scor.rr.domain.entities.references.cat.Narrative;
import com.scor.rr.domain.entities.references.omega.PeriodBasis;
import com.scor.rr.domain.entities.references.plt.FinancialPerspective;
import com.scor.rr.domain.utils.cat.ModellingResult;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;

/**
 * The persistent class for the CATObjectGroup database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "CATObjectGroup")
@Data
public class CATObjectGroup {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATObjectGroupId")
    private String catObjectGroupId;

    //TODO : Check String or Integer
    @Column(name = "Version")
    private String version;
    //TODO : Check String or Integer
    @Column(name = "DivisionNumber")
    private String divisionNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NarrativeId")
    private Narrative narrative;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CatRequestId")
    private CATRequest catRequest;
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

    @Transient
    private Map<String, ModellingResult> modellingResultsByRegionPeril;

    public CATObjectGroup() {
    }

    public CATObjectGroup(String version, String divisionNumber, CATRequest catRequest, PeriodBasis periodBasis) {
        this.version = version;
        this.divisionNumber = divisionNumber;
        this.catRequest = catRequest;
        this.periodBasis = periodBasis;
    }


    public static String buildId(String carId, String division, String periodBasis, String dsName) {
        return carId + "_" + division + "_" + periodBasis + "_" + dsName;
    }
}
