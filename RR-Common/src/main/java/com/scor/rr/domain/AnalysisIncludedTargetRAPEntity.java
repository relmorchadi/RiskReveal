package com.scor.rr.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "AnalysisIncludedTargetRAP")
@Data
@AllArgsConstructor
public class AnalysisIncludedTargetRAPEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnalysisIncludedTargetRAPid")
    private Long analysisIncludedTargetRAPid;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "ModelAnalysisId")
    private Long modelAnalysisId;
    @Column(name = "TargetRAPId")
    private Long targetRAPId;

    public AnalysisIncludedTargetRAPEntity(){
        this.entity=1;
    }
}
