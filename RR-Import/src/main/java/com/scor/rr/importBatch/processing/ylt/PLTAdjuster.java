package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.domain.PLTData;
import com.scor.rr.importBatch.processing.domain.PLTPeriod;
import com.scor.rr.importBatch.processing.domain.PLTResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by U002629 on 19/02/2015.
 */
public class PLTAdjuster extends BaseBatchBeanImpl implements PLTHandler {
    private static final Logger log= LoggerFactory.getLogger(PLTAdjuster.class);
    private PLTData pltData;

    public PLTData getPltData() {
        return pltData;
    }

    public void setPltData(PLTData pltData) {
        this.pltData = pltData;
    }

    @Override
    public Boolean handle(){
        log.debug("Starting PLTAdjuster");
        for (String regionPeril : pltData.getRegionPerils()) {
            log.info("begin adjusting PLT for "+regionPeril);
            for (PLTPeriod result : pltData.getLossDataForRP(regionPeril).getPeriods()) {
                for (PLTResult pltResult : result.getResults()) {
                    continue;
                }
            }
            log.info("finished adjusting PLT for "+regionPeril);
            addMessage("PLT ASJ", "PLT adjustments complete for "+regionPeril);
        }
        log.debug("PLTAdjuster completed");
        return true;
    }
}
