package com.scor.rr.entity;


import lombok.Data;

import javax.persistence.*;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringContractLayerParam", schema = "dbo", catalog = "RiskReveal")
public class InuringContractLayerParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringContractParamId", nullable = false)
    private long inuringContractParamId;

    @Column(name = "RREntity")
    private int entity;

    @Column(name = "InuringContractLayerId", nullable = false)
    private long inuringContractLayerId;

    @Column(name = "ParamName")
    private String paramName;

    @Column(name = "ParamType")
    private String paramType;

    @Column(name = "ParamValue")
    private String paramValue;

    public InuringContractLayerParam() {
    }

    public InuringContractLayerParam(long inuringContractLayerId, String paramName, String paramType, String paramValue) {
        this.entity = 1;
        this.inuringContractLayerId = inuringContractLayerId;
        this.paramName = paramName;
        this.paramType = paramType;
        this.paramValue = paramValue;
    }
}
