package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.RdmAllAnalysisSummaryStats;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RLSourceEPHeader")
@Data
@NoArgsConstructor
public class RLSourceEpHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rLSourceEPHeaderId;
    @Column(name = "RLModelAnalysisId")
    private Long rLAnalysisId;
    @Column(name = "FinancialPerspective")
    private String financialPerspective;
    @Column(name = "OEP10")
    private Double oEP10;
    @Column(name = "OEP50")
    private Double oEP50;
    @Column(name = "OEP100")
    private Double oEP100;
    @Column(name = "OEP250")
    private Double oEP250;
    @Column(name = "OEP500")
    private Double oEP500;
    @Column(name = "OEP1000")
    private Double oEP1000;
    @Column(name = "AEP10")
    private Double aEP10;
    @Column(name = "AEP50")
    private Double aEP50;
    @Column(name = "AEP100")
    private Double aEP100;
    @Column(name = "AEP250")
    private Double aEP250;
    @Column(name = "AEP500")
    private Double aEP500;
    @Column(name = "AEP1000")
    private Double aEP1000;
    @Column(name = "PurePremium")
    private Double purePremium;
    @Column(name = "StdDev")
    private Double stdDev;
    @Column(name = "CoV")
    private Double coV;

    public RLSourceEpHeader(RdmAllAnalysisSummaryStats stat) {
        financialPerspective = stat.getFinPerspCode();
        purePremium = stat.getPurePremium();
        stdDev = stat.getStdDev();
        coV = stat.getCov();
    }

}
