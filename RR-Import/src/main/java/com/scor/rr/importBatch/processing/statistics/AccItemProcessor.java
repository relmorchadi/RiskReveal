package com.scor.rr.importBatch.processing.statistics;

import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.domain.rms.RMSAccRow;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by U002629 on 12/05/2015.
 */
public class AccItemProcessor extends BaseBatchBeanImpl implements ItemProcessor<RMSAccRow,RMSAccRow> {

    private boolean forceCarId;

    public void setForceCarId(boolean forceCarId) {
        this.forceCarId = forceCarId;
    }

    @Override
    public RMSAccRow process(RMSAccRow item) throws Exception {
        if(!isConstruction()){
            item.setInceptionDate(null);
            item.setPracticalCompletionDate(null);
        }
        if(forceCarId)
            item.setCarID(catReqId);
        return item;
    }
}
