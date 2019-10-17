package com.scor.rr.importBatch.processing.adjustment;

import com.scor.rr.domain.adjustments.LinearAdjustmentDefinition;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.domain.PLTPeriod;
import com.scor.rr.importBatch.processing.domain.PLTResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by U002629 on 03/03/2016.
 */
public class LinearAdjustment implements Adjustment {
    private static final Logger log= LoggerFactory.getLogger(Adjustment.class);
    LinearAdjustmentDefinition definition;

    public LinearAdjustment(LinearAdjustmentDefinition definition) {
        this.definition = definition;
    }

    @Override
    public PLTLoss adjustPLT(PLTLoss pltLoss) {
        log.info("adjusting "+ pltLoss.getRegion()+"_"+pltLoss.getPeril());
        double adjValue = definition.getAmount();

        final PLTLoss adjLoss = new PLTLoss(pltLoss.getRegion(),pltLoss.getPeril(),pltLoss.getCurrency(),pltLoss.getFinancialPerspective());
        for (PLTPeriod period : pltLoss.getPeriods()) {
            PLTPeriod adjPeriod = new PLTPeriod(period.getPeriod());
            adjLoss.addPeriod(adjPeriod);
            for (PLTResult pltResult : period.getResults()) {
                adjPeriod.addResult(new PLTResult(pltResult.getEventId(), pltResult.getSeq(), pltResult.getLoss()*adjValue, pltResult.getEventDate()));
            }
        }

        return adjLoss;
    }

}
