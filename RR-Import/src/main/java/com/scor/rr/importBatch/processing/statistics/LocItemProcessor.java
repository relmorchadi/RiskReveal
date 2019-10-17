package com.scor.rr.importBatch.processing.statistics;

import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.batch.ParameterBean;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBean;
import com.scor.rr.importBatch.processing.domain.rms.RMSLocRow;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by U002629 on 12/05/2015.
 */
public class LocItemProcessor extends BaseBatchBeanImpl implements ItemProcessor<RMSLocRow,RMSLocRow> {
        private boolean forceCarId;
        private ParameterBean rmsParameters;

        public void setForceCarId(boolean forceCarId) {
                this.forceCarId = forceCarId;
        }
        public void setRmsParameters(ParameterBean rmsParameters) {
                this.rmsParameters = rmsParameters;
        }

        @Override
        public RMSLocRow process(RMSLocRow item) throws Exception {
                if(forceCarId)
                        item.setCarID(catReqId);

                item.setDivision(division);
                item.setAccuracyLevel(mappingHandler.getGeoResForCode(Integer.toString(item.getGeoResultionCode())));
                item.setCurrencyCode((String)rmsParameters.getQueryParameters().get(BaseRMSBean.LIABILITY_CCY));

                return item;
        }
}
