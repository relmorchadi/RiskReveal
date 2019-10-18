package com.scor.rr.response;

import com.scor.rr.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class InuringContractLayerDetailsResponse {

    private InuringContractLayer inuringContractLayer;
    private List<InuringContractLayerParam> inuringContractLayerParamsList;
    private List<InuringFilterCriteria> inuringFilterCriteriaList;
    private List<InuringContractLayerReinstatementDetail> inuringContractLayerReinstatementDetailList;
    private List<InuringContractLayerPerilLimit> inuringContractLayerPerilLimitList;
}
