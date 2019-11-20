package com.scor.rr.service.calculation;

import com.scor.rr.domain.ConvertFunction;

public class CMBetaConvertFunctionFactory implements ConvertFunctionFactory {
    @Override
    public ConvertFunction buildFunction(Double meanLoss, Double stdDevC, Double stdDevU, Double stdDevUSq, Double exposureValue, Double minLayerAtt) {
        return new CMBetaConvertFunction(meanLoss, stdDevC, stdDevU, stdDevUSq, exposureValue, minLayerAtt);
    }
}
