package com.scor.rr.request;

import com.scor.rr.enums.InuringFinancialPerspective;
import com.scor.rr.enums.InuringFinancialTreatment;

/**
 * Created by u004602 on 16/09/2019.
 */
public class InuringEdgeUpdateRequest {
    private int inuringEdgeId;
    private InuringFinancialPerspective outputPerspective;
    private InuringFinancialTreatment financialTreatment;
    private boolean outputAtLayerLevel;

    public InuringEdgeUpdateRequest(int inuringEdgeId, InuringFinancialPerspective outputPerspective, InuringFinancialTreatment financialTreatment, boolean outputAtLayerLevel) {
        this.inuringEdgeId = inuringEdgeId;
        this.outputPerspective = outputPerspective;
        this.financialTreatment = financialTreatment;
        this.outputAtLayerLevel = outputAtLayerLevel;
    }

    public int getInuringEdgeId() {
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
