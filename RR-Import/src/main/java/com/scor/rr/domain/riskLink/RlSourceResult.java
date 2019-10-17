package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.RdmAnalysis;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Getter
@Setter
@Entity
@Table(name = "RLSourceResult")
public class RlSourceResult {

    @Id
    @GeneratedValue
    private Integer rlSourceResultId;
    private Integer entity;
    private Integer projectId;
    private Integer rlAnalysisId;
    private String targetCurrency;
    private String targetRegionPeril;
    private String overrideRegionPerilBasis;
//    To check from SP Result
    private String occurrenceBasis;
    private String financialPerspective;
    private Number unitMultiplier;
    private Number proportion;
    private String targetRAPCode;

    public RlSourceResult(RdmAnalysis analysis, Integer projectId){
        //TODO : Map data
        this.entity=1;
        projectId = projectId;
        this.targetCurrency = analysis.getAnalysisCurrency();
        //this.targetRegionPeril; // TODO: Calculate
        this.overrideRegionPerilBasis = null;
//        this.occurrenceBasis = analysis.
//        To be defaulted
        this.financialPerspective = null;
    }


}
