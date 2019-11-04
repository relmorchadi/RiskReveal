package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringContractLayerReinstatementDetail", schema = "dbo", catalog = "RiskReveal")
public class InuringContractLayerReinstatementDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringContractParamId", nullable = false)
    private int inuringContractLayerReinstatementDetailId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringContractLayerId")
    private int inuringContractLayerId;

    @Column(name = "ReinstatementsRank")
    private int reinstatementsRank;

    @Column(name = "ReinstatementsNumber")
    private int reinstatementsNumber;

    @Column(name = "ReinstatementsCharge")
    private BigDecimal reinstatementsCharge;

    public InuringContractLayerReinstatementDetail(int inuringContractLayerId, int reinstatementsRank, int reinstatementsNumber, BigDecimal reinstatementsCharge) {
        this.entity = 1;
        this.inuringContractLayerId = inuringContractLayerId;
        this.reinstatementsRank = reinstatementsRank;
        this.reinstatementsNumber = reinstatementsNumber;
        this.reinstatementsCharge = reinstatementsCharge;
    }
}
