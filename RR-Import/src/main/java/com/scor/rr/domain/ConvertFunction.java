package com.scor.rr.domain;

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
