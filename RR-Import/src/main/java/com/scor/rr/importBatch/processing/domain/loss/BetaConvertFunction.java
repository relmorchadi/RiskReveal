package com.scor.rr.importBatch.processing.domain.loss;

/**
 * Created by U002629 on 10/07/2015.
 */
public abstract class BetaConvertFunction implements ConvertFunction {
    double minQuantile;
    private double mu;
    private double sigma;
    private double alpha;
    private double beta;
    double exposureValue;
    double meanLoss;
    double stdDev;

    public BetaConvertFunction(double meanLoss, double stdDevC, double stdDevU, double stdDevUSq, double exposureValue, double minLayerAtt) {
        // TODO look if possible to factorize out the DIVs
        mu = meanLoss / exposureValue;
        double stdDev = stdDevC + stdDevU;
        //NOTE: we need to handle 2 boundary cases: when stdevI + stdevU  is close to zero and exposure summary
        //This implementation is correct, but first it need to be accepted by the bussiness. For now use the same implementation as MAT-R
//        sigma = stdDev / exposureValue;
//        if (stdDev <  1e-15)
//            sigma = 1e-5;
//        else if (sigma >= 1.86e-3)
//            sigma = 1.85e-3;

        sigma = stdDev / exposureValue;
        sigma = Math.max(sigma, 1e-5);
//        sigma = Math.max(sigma, 1e-10);
//        mu = Math.min(mu, 1 - 0.5*1e-10);

        alpha = (((mu * mu) * (1 - mu)) / (sigma * sigma)) - mu;
        beta = (alpha * (1 - mu)) / mu;
        this.exposureValue = exposureValue;
        this.meanLoss = meanLoss;
        this.stdDev = stdDevC + stdDevU;
    }

    public double getMu() {
        return mu;
    }

    public double getSigma() {
        return sigma;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getExposureValue() {
        return exposureValue;
    }

    public double getMeanLoss() {
        return meanLoss;
    }

    public double getStdDev() {
        return stdDev;
    }
}
