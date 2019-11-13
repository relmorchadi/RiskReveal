package com.scor.rr.domain.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class AnalysisIncludedTargetRAP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analysisIncludedTargetRAPid;
    private Integer entity;
    private Long modelAnalysisId;
    private Long targetRAPId;

    public AnalysisIncludedTargetRAP(){
        this.entity=1;
    }
}
