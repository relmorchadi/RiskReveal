package com.scor.rr.importBatch.processing.adjustment;

import com.scor.rr.domain.adjustments.DummyAdjustmentDefinition;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.domain.PLTPeriod;
import com.scor.rr.importBatch.processing.domain.PLTResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by U002629 on 07/03/2016.
 */
public class DummyAdjustment implements Adjustment {
    private static final Logger log= LoggerFactory.getLogger(Adjustment.class);
    DummyAdjustmentDefinition definition;

    public DummyAdjustment(DummyAdjustmentDefinition definition) {
        this.definition = definition;
    }

    @Override
    public PLTLoss adjustPLT(PLTLoss pltLoss) {
        log.info("adjusting "+ pltLoss.getRegion()+"_"+pltLoss.getPeril());

        final PLTLoss adjLoss = new PLTLoss(pltLoss.getRegion(),pltLoss.getPeril(),pltLoss.getCurrency(),pltLoss.getFinancialPerspective());
        for (PLTPeriod period : pltLoss.getPeriods()) {
            PLTPeriod adjPeriod = new PLTPeriod(period.getPeriod());
            adjLoss.addPeriod(adjPeriod);
            for (PLTResult pltResult : period.getResults()) {
                adjPeriod.addResult(new PLTResult(pltResult.getEventId(), pltResult.getSeq(), pltResult.getLoss(), pltResult.getEventDate()));
            }
        }

        return adjLoss;
    }

}