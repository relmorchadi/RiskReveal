package com.scor.rr.importBatch.processing.adjustment;

import com.scor.rr.importBatch.processing.domain.PLTLoss;

/**
 * Created by U002629 on 03/03/2016.
 */
public interface Adjustment {
    PLTLoss adjustPLT(PLTLoss pltLoss);
}
