package com.scor.rr.importBatch.processing.domain.loss;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.exception.OutOfRangeException;

/**
 * Created by U002629 on 10/07/2015.
 */
public class JSCBetaConvertFunction extends BetaConvertFunction {
    private BetaDistribution betaDistribution;

    public JSCBetaConvertFunction(Double meanLoss, Double stdDevC, Double stdDevU, Double stdDevUSq,
                                  Double exposureValue, Double minLayerAtt) {
        // @formatter:off
        super(meanLoss, stdDevC, stdDevU, stdDevUSq, exposureValue, minLayerAtt);

        try {
            betaDistribution = new BetaDistribution(getAlpha(), getBeta());
            minQuantile 	 = 0.0d;

            if (minLayerAtt > 0) {
                double d 	= minLayerAtt / exposureValue;

                minQuantile = betaDistribution.cumulativeProbability(d > 1 ? 1 : d);
            }
        } catch (OutOfRangeException e) {
            betaDistribution = null;
        }
        // @formatter:on
    }

    /**
     * {@inheritDoc}
     */
    public Double getLossOverMinQuantile(Double quantile) {
        if (quantile < minQuantile)
            return 0.0d;

        return betaDistribution.inverseCumulativeProbability(quantile) * exposureValue;
    }

    /**
     * {@inheritDoc}
     */
    public Double getLoss(Double quantile) {
        try {
            return betaDistribution.inverseCumulativeProbability(quantile) * exposureValue;
        } catch (Throwable t) {
            return 0.0d;
        }
    }
}
