package com.scor.rr.dto;

import java.util.List;

public class InuringContractLayerDto {

    private int layerNumber;
    private int layerSequence;
    private String layerCode;
    private String layerCurrency;
    private String layerDescription;

    private List<InuringContractLayerParamDto> listOfAttributes;

    public InuringContractLayerDto(int layerNumber, int layerSequence, String layerCode, String layerCurrency, String layerDescription, List<InuringContractLayerParamDto> listOfAttributes) {
        this.layerNumber = layerNumber;
        this.layerSequence = layerSequence;
        this.layerCode = layerCode;
        this.layerCurrency = layerCurrency;
        this.layerDescription = layerDescription;
        this.listOfAttributes = listOfAttributes;
    }

    public InuringContractLayerDto() {
    }

    public int getLayerNumber() {
        return layerNumber;
    }

    public void setLayerNumber(int layerNumber) {
        this.layerNumber = layerNumber;
    }

    public int getLayerSequence() {
        return layerSequence;
    }

    public void setLayerSequence(int layerSequence) {
        this.layerSequence = layerSequence;
    }

    public String getLayerCode() {
        return layerCode;
    }

    public void setLayerCode(String layerCode) {
        this.layerCode = layerCode;
    }

    public String getLayerCurrency() {
        return layerCurrency;
    }

    public void setLayerCurrency(String layerCurrency) {
        this.layerCurrency = layerCurrency;
    }

    public String getLayerDescription() {
        return layerDescription;
    }

    public void setLayerDescription(String layerDescription) {
        this.layerDescription = layerDescription;
    }

    public List<InuringContractLayerParamDto> getListOfAttributes() {
        return listOfAttributes;
    }

    public void setListOfAttributes(List<InuringContractLayerParamDto> listOfAttributes) {
        this.listOfAttributes = listOfAttributes;
    }
}
