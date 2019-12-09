package com.scor.rr.service.adjustement.pltAdjustment;

import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameter;
import com.scor.rr.domain.dto.EPMetricPoint;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;

import java.util.List;

public interface ICalculAdjustment {

    static List<PLTLossData> oepReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBendings) {
        return null;
    }

    static List<PLTLossData> eefReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<ReturnPeriodBandingAdjustmentParameter> adjustmentReturnPeriodBendings) {
        return null;
    }

    static List<PLTLossData> eefFrequency(List<PLTLossData> pltLossDatas, boolean cap, double rpmf) {
        return null;
    }

    static List<PLTLossData> nonLineaireEventDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas) {
        return null;
    }

    static List<PLTLossData> nonLineaireEventPeriodDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas) {
        return null;
    }

    static List<PLTLossData> linearAdjustement(List<PLTLossData> pltLossDatas, double lmf, boolean cap) {
        return null;
    }

    static List<EPMetricPoint> getOEPMetric(List<PLTLossData> pltLossData) {
        return null;
    }

}
