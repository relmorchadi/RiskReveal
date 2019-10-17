package com.scor.rr.importBatch.processing.domain;

/**
 * Created by U002629 on 06/03/2015.
 */
interface LossCalculator {
    double getLoss(double quantile);
}
