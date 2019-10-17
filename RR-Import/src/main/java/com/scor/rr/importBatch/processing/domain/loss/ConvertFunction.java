package com.scor.rr.importBatch.processing.domain.loss;

/**
 * Created by U002629 on 10/07/2015.
 */
public interface ConvertFunction {
    Double getLoss(Double quantile);
    Double getLossOverMinQuantile(Double quantile);
    double getMu();
    double getSigma();
    double getAlpha();
    double getBeta();
    double getExposureValue();
    double getMeanLoss();
}
