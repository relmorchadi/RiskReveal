package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringContractLayerPerilLimit")
public class InuringContractLayerPerilLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringContractLayerPerilLimitId", nullable = false)
    private long inuringContractLayerPerilLimitId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringContractLayerId", nullable = false)
    private long inuringContractLayerId;

    @Column(name = "Peril")
    private String peril;

    @Column(name = "Limit")
    private BigDecimal limit;

    public InuringContractLayerPerilLimit() {
    }

    public InuringContractLayerPerilLimit(long inuringContractLayerId, String peril, BigDecimal limit) {
        this.entity = 1;
        this.inuringContractLayerId = inuringContractLayerId;
        this.peril = peril;
        this.limit = limit;
    }
}
