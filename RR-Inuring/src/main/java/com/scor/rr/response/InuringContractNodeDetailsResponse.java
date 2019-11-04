package com.scor.rr.response;

import com.scor.rr.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class InuringContractNodeDetailsResponse {

    private InuringContractNode inuringContractNode;
    private List<InuringContractLayerDetailsResponse> inuringContractLayerDetailsResponseList;



}
