package com.scor.rr.domain.dto;


import com.scor.rr.domain.RdmAllAnalysisSummaryStats;
import lombok.Data;

@Data
public class ExpectedFinancialPerspective {
    private String fpCode;
    private String occurrenceBasis;

    public ExpectedFinancialPerspective(RdmAllAnalysisSummaryStats stat) {
        this.fpCode= stat.getFinPerspCode();
        this.occurrenceBasis= stat.getOccurrenceBasis();
    }
}
