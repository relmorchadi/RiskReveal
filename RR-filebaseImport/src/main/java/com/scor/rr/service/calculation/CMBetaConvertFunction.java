package com.scor.rr.service.calculation;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.random.Well19937c;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMBetaConvertFunction extends BetaConvertFunction {
    private BetaDistribution betaDistribution;
    private static Well19937c randomGen=new Well19937c();

    private static Logger log = LoggerFactory.getLogger(CMBetaConvertFunction.class);

    public CMBetaConvertFunction(Double meanLoss, Double stdDevC, Double stdDevU, Double stdDevUSq, Double exposureValue, Double minLayerAtt) {
        super(meanLoss, stdDevC, stdDevU, stdDevUSq, exposureValue, minLayerAtt);
        try {
            // reuse same random generator to avoid 800k generator in memory
//            betaDistribution = new BetaDistribution(randomGen,getAlpha(), getBeta(),1e-5);
            betaDistribution = new BetaDistribution(getAlpha(), getBeta());
            minQuantile = 0.0d;
            if (minLayerAtt > 0) {
                double d = minLayerAtt / exposureValue;
                minQuantile = betaDistribution.cumulativeProbability(d > 1 ? 1 : d);
            }
        } catch (OutOfRangeException e) {
            betaDistribution=null;
        }
    }

    @Override
    public Double getLossOverMinQuantile(Double quantile){
        if(quantile<minQuantile)
            return 0.0d;

        return betaDistribution.inverseCumulativeProbability(quantile)* exposureValue;
    }

    @Override
    public Double getLoss(Double quantile){
        try {
            return betaDistribution.inverseCumulativeProbability(quantile) * exposureValue;
        } catch (Throwable t) {
//            log.debug("Exception in beta function stdDev = {} expVal = {}", getStdDev(), getExposureValue());
            return 0.0d;
        }
    }
}
