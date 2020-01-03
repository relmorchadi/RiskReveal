package com.scor.rr.request;

import com.scor.rr.dto.InuringContractLayerParamDto;

import java.util.List;

public class InuringContractLayerUpdateRequest {

    private long layerId;
    private List<InuringContractLayerParamDto> listOfAttributes;

    public InuringContractLayerUpdateRequest() {
    }

    public InuringContractLayerUpdateRequest(long layerId, List<InuringContractLayerParamDto> listOfAttributes) {
        this.layerId = layerId;
        this.listOfAttributes = listOfAttributes;
    }

    public long getLayerId() {
        return layerId;
    }

    public void setLayerId(long layerId) {
        this.layerId = layerId;
    }

    public List<InuringContractLayerParamDto> getListOfAttributes() {
        return listOfAttributes;
    }

    public void setListOfAttributes(List<InuringContractLayerParamDto> listOfAttributes) {
        this.listOfAttributes = listOfAttributes;
    }
}
