package com.scor.rr.request;

import com.scor.rr.dto.InuringContractLayerDto;
import lombok.Data;

import java.util.List;

@Data
public class InuringContractLayerCreationRequest {

    private int inuringContractNodeId;
    private int contractTypeCode;

    private List<InuringContractLayerDto> listOfLayers;

}
