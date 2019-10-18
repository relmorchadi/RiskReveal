package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "AdjustmentNode", schema = "dr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdjustmentNodeId")
    private Integer adjustmentNodeId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "AdjustmentThreadId")
    private Integer adjustmentThreadId;

    @Column(name = "AdjustmentProcessingRecapId")
    private Integer adjustmentProcessingRecapId;

    @Column(name = "AdjustmentNodeState")
    private Integer adjustmentNodeState;

    @Column(name = "AdjustmentBasisCode")
    private Integer adjustmentBasisCode;

    @Column(name = "AdjustmentCategoryCode")
    private Integer adjustmentCategoryCode;

    @Column(name = "AdjustmentTypeCode")
    private Integer adjustmentTypeCode;

    @Column(name = "Capped")
    private Integer capped;

    @Column(name = "UserNarrative")
    private Integer userNarrative;

}
