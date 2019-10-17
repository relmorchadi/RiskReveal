package com.scor.rr.importBatch.processing.domain.loss;

/**
 * Created by U002629 on 10/07/2015.
 */
public interface ConvertFunctionFactory {
    ConvertFunction buildFunction(Double meanLoss, Double stdDevC, Double stdDevU, Double stdDevUSq, Double exposureValue, Double minLayerAtt);
}
