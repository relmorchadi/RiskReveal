package com.scor.rr.request;

import com.scor.rr.enums.InuringFinancialPerspective;
import com.scor.rr.enums.InuringFinancialTreatment;
import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */

public class InuringEdgeUpdateRequest {
    private long inuringEdgeId;
    private InuringFinancialPerspective outputPerspective;
    private InuringFinancialTreatment financialTreatment;
    private boolean outputAtLayerLevel;

    public InuringEdgeUpdateRequest(long inuringEdgeId, InuringFinancialPerspective outputPerspective, InuringFinancialTreatment financialTreatment, boolean outputAtLayerLevel) {
        this.inuringEdgeId = inuringEdgeId;
        this.outputPerspective = outputPerspective;
        this.financialTreatment = financialTreatment;
        this.outputAtLayerLevel = outputAtLayerLevel;
    }

    public InuringEdgeUpdateRequest() {
    }

    public long getInuringEdgeId() {
        return inuringEdgeId;
    }

    public InuringFinancialPerspective getOutputPerspective() {
        return outputPerspective;
    }

    public InuringFinancialTreatment getFinancialTreatment() {
        return financialTreatment;
    }

    public boolean isOutputAtLayerLevel() {
        return outputAtLayerLevel;
    }
}
