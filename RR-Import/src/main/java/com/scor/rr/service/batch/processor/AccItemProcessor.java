package com.scor.rr.service.batch.processor;

import com.scor.rr.service.batch.processor.rows.RLAccRow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

public class AccItemProcessor implements ItemProcessor<RLAccRow, RLAccRow> {

    // TODO : Review
    private boolean forceCarId = true;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{jobParameters['carId']}")
    private String carId;

    @Value("#{jobParameters['lob']}")
    private String lob;

    public void setForceCarId(boolean forceCarId) {
        this.forceCarId = forceCarId;
    }

    @Override
    public RLAccRow process(RLAccRow item) throws Exception {
        if (marketChannel.equalsIgnoreCase("Fac")) {

            if (!this.isConstruction()) {
                item.setInceptionDate(null);
                item.setPracticalCompletionDate(null);
            }
            if (forceCarId)
                item.setCarID(carId);
            return item;
        }
        return null;
    }

    private boolean isConstruction() {
        return "02".equals(lob);
    }
}