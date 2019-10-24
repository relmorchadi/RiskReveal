package com.scor.rr.request;

import com.scor.rr.enums.InuringFinancialPerspective;
import com.scor.rr.enums.InuringFinancialTreatment;
import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */
@Data
public class InuringEdgeUpdateRequest {
    private int inuringEdgeId;
    private InuringFinancialPerspective outputPerspective;
    private InuringFinancialTreatment financialTreatment;
    private boolean outputAtLayerLevel;

}
