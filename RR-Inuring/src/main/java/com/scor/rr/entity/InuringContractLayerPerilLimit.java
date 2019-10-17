package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringContractLayerPerilLimit", schema = "dbo", catalog = "RiskReveal")
public class InuringContractLayerPerilLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "InuringContractParamId", nullable = false)
    private int inuringContractLayerPerilLimitId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringContractLayerId", nullable = false)
    private int inuringContractLayerId;

    @Column(name = "Peril")
    private String peril;

    @Column(name = "Limit")
    private BigDecimal limit;

    public InuringContractLayerPerilLimit(int inuringContractLayerId, String peril, BigDecimal limit) {
        this.entity = 1;
        this.inuringContractLayerId = inuringContractLayerId;
        this.peril = peril;
        this.limit = limit;
    }
}
