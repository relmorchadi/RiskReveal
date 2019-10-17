package com.scor.rr.request;

import com.scor.rr.dto.InuringContractLayerDto;

import java.util.List;

public class InuringContractLayerCreationRequest {

    private int inuringContractNodeId;
    private int contractTypeCode;

    private List<InuringContractLayerDto> listOfLayers;

    public InuringContractLayerCreationRequest(int inuringContractNodeId, int contractTypeCode, List<InuringContractLayerDto> listOfLayers) {
        this.inuringContractNodeId = inuringContractNodeId;
        this.contractTypeCode = contractTypeCode;
        this.listOfLayers = listOfLayers;
    }

    public InuringContractLayerCreationRequest() {
    }

    public int getInuringContractNodeId() {
        return inuringContractNodeId;
    }

    public int getContractTypeCode() {
        return contractTypeCode;
    }

    public List<InuringContractLayerDto> getListOfLayers() {
        return listOfLayers;
    }
}
