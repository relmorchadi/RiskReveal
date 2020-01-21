package com.scor.rr.request;

import com.scor.rr.domain.dto.InuringContractLayerDto;

import java.util.List;


public class InuringContractLayerCreationRequest {

    private long inuringContractNodeId;
    private int contractTypeCode;

    private List<InuringContractLayerDto> listOfLayers;

    public InuringContractLayerCreationRequest(long inuringContractNodeId, int contractTypeCode, List<InuringContractLayerDto> listOfLayers) {
        this.inuringContractNodeId = inuringContractNodeId;
        this.contractTypeCode = contractTypeCode;
        this.listOfLayers = listOfLayers;
    }

    public InuringContractLayerCreationRequest() {
    }

    public long getInuringContractNodeId() {
        return inuringContractNodeId;
    }

    public int getContractTypeCode() {
        return contractTypeCode;
    }

    public List<InuringContractLayerDto> getListOfLayers() {
        return listOfLayers;
    }
}
