package com.scor.rr.importBatch.processing.workflow.consistency.rms;

import com.scor.rr.importBatch.processing.batch.BaseBatchBeanImpl;
import com.scor.rr.importBatch.processing.domain.ELTData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by U002629 on 03/12/2015.
 */
public class PortfolioCatCapacityCurrencyCheck extends BaseBatchBeanImpl /*implements ConsistencyCheck*/ {
    static final Logger log = LoggerFactory.getLogger(PortfolioCatCapacityCurrencyCheck.class);

    final String checkName;
    private ELTData eltData;

    public PortfolioCatCapacityCurrencyCheck(String checkName) {
        this.checkName = checkName;
    }

//    @Override
//    public ConsistencyStatus performCheck() {
//
//        final CATObjectGroup catObjectGroup = getCatObjectGroup();
//        final Map<String, ModellingResult> modellingResultsByRegionPeril = catObjectGroup.getModellingResultsByRegionPeril();
//        List<String> errors = new LinkedList<String>();
//        ConsistencyStatus.Status status = ConsistencyStatus.Status.OK;
//        String message = "All Analysis Currencies match Capacity Currencies.";
//
//        for (String regionPeril : eltData.getRegionPerils()) {
//           log.info("check capacity currency for "+regionPeril);
//            ELTLoss data = eltData.getLossDataForRp(regionPeril);
//            String modelledCurrencyCode="";
//            String capacityCurrencyCode="";
//            try {
//                modelledCurrencyCode = data.getCurrency();
//                if (modelledCurrencyCode == null || modelledCurrencyCode.trim().equals(""))
//                    throw new RuntimeException("modelledCurrencyCode not available");
//            } catch (Exception e) {
//                final String msg = "modelledCurrencyCode not available for region peril " + regionPeril + " in portfolio " + portfolio;
//                log.error(msg);
//                errors.add(msg);
//            }
//            try {
//                capacityCurrencyCode = modellingResultsByRegionPeril.get(regionPeril).getModelRAPSource().getRegionPeril().getGroup().getCapacityCCY().getCode();
//                if (capacityCurrencyCode == null || modelledCurrencyCode.trim().equals(""))
//                    throw new RuntimeException("capacityCurrencyCode not available");
//            } catch (Exception e) {
//                final String msg = "capacityCurrencyCode not available for region peril " + regionPeril + " in portfolio " + portfolio;
//                log.error(msg);
//                errors.add(msg);
//            }
//
//
//            if (!modelledCurrencyCode.equals(capacityCurrencyCode))
//                errors.add("Analysis Currency does not match Capacity Currency for "+regionPeril);
//
//        }
//
//        if (errors.size()!=0) {
//            status = ConsistencyStatus.Status.KO;
//            StringBuilder sb = new StringBuilder();
//            for (String error : errors) {
//                sb.append(error).append("\\n");
//            }
//
//            message = sb.toString();
//        }
//
//
//        return new ConsistencyStatus(status, message, checkName);
//    }

    /*@Override
    public String getCheckName() {
        return checkName;
    }

    public void setEltData(ELTData eltData) {
        this.eltData = eltData;
    }*/
}
