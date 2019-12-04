package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.RdmAllAnalysisSummaryStats;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.ArrayUtils;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;

@Entity
@Table(name = "RLSourceEPHeader")
@Data
@NoArgsConstructor
@Builder
public class RLSourceEpHeader {

    private static int[] returnPeriods=new int[]{10,50,100,250,500,1000};
    public static boolean isValidReturnPeriod(int returnPeriod){
        return ArrayUtils.contains(returnPeriods, returnPeriod);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rLSourceEPHeaderId;
    private Long rLAnalysisId;
    private String financialPerpective;
    private Double oEP10;
    private Double oEP50;
    private Double oEP100;
    private Double oEP250;
    private Double oEP500;
    private Double oEP1000;
    private Double aEP10;
    private Double aEP50;
    private Double aEP100;
    private Double aEP250;
    private Double aEP500;
    private Double aEP1000;
    private Double purePremium;
    private Double stdDev;
    private Double coV;

    public RLSourceEpHeader(RdmAllAnalysisSummaryStats stat) {
        rLAnalysisId= stat.getAnalysisId();
        financialPerpective= stat.getFinPerspCode();
        purePremium=ofNullable(stat.getPurePremium()).map(BigDecimal::doubleValue).orElse(null);
        stdDev=ofNullable(stat.getStdDev()).map(BigDecimal::doubleValue).orElse(null);
        coV=ofNullable(stat.getCov()).map(BigDecimal::doubleValue).orElse(null);
    }

    public void setStatistic(Integer statisticMetric, Integer returnPeriod){

    }
}
