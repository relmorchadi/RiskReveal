package com.scor.rr.importBatch.processing.domain.loss;

/**
 * Created by U002629 on 10/07/2015.
 */
public class CMBetaConvertFunctionFactory implements ConvertFunctionFactory {
    @Override
    public ConvertFunction buildFunction(Double meanLoss, Double stdDevC, Double stdDevU, Double stdDevUSq, Double exposureValue, Double minLayerAtt) {
        return new CMBetaConvertFunction(meanLoss, stdDevC, stdDevU, stdDevUSq, exposureValue, minLayerAtt);
    }
}
