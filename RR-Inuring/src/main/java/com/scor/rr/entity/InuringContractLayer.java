package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringContractLayer", schema = "dbo", catalog = "RiskReveal")
public class InuringContractLayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "InuringContractLayerId", nullable = false)
    private int inuringContractLayerId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringContractNodeId", nullable = false)
    private int inuringContractNodeId;

    @Column(name = "LayerNumber")
    private int layerNumber;

    @Column(name = "LayerSequence")
    private int layerSequence;

    @Column(name = "LayerCode", nullable = false)
    private String layerCode;

    @Column(name = "LayerCurrency")
    private String layerCurrency;

    @Column(name = "LayerDescription")
    private String layerDescription;

    public InuringContractLayer() {
    }
    public InuringContractLayer( int layerNumber,int inuringContractNodeId, int layerSequence, String layerCode, String layerDescription){

        this.entity = 1;
        this.inuringContractNodeId = inuringContractNodeId;
        this.layerNumber = layerNumber;
        this.layerSequence = layerSequence;
        this.layerCode = layerCode;
        this.layerDescription = layerDescription;
    }
}
