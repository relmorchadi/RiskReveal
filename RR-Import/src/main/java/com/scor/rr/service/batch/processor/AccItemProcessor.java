package com.scor.rr.service.batch.processor;

import com.scor.rr.service.batch.processor.rows.RLAccRow;
import com.scor.rr.service.state.FacParameters;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AccItemProcessor implements ItemProcessor<RLAccRow, RLAccRow> {

    // TODO : Review
    private boolean forceCarId = true;

    @Autowired
    private FacParameters facParameters;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    public void setForceCarId(boolean forceCarId) {
        this.forceCarId = forceCarId;
    }

    @Override
    public RLAccRow process(RLAccRow item) throws Exception {
        if (marketChannel.equalsIgnoreCase("Fac")) {

            if (!facParameters.isConstruction()) {
                item.setInceptionDate(null);
                item.setPracticalCompletionDate(null);
            }
            if (forceCarId)
                item.setCarID(facParameters.getCatReqId());
            return item;
        }
        return null;
    }
}