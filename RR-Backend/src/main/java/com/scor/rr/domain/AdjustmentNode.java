package com.scor.rr.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdjustmentNode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private String category;
    private String adjustmentBasis;
    private int sequence;
    private String exposureFlag;
    private boolean isExposureGrowth;
    private String value;
    private int idAdjustmentType;
    private boolean linear;

    public AdjustmentNode() {
    }

    public AdjustmentNode(String name, String description, String category, String adjustmentBasis, int sequence, String exposureFlag, boolean isExposureGrowth, String value, int idAdjustmentType, boolean linear) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.adjustmentBasis = adjustmentBasis;
        this.sequence = sequence;
        this.exposureFlag = exposureFlag;
        this.isExposureGrowth = isExposureGrowth;
        this.value = value;
        this.idAdjustmentType = idAdjustmentType;
        this.linear = linear;
    }
}
