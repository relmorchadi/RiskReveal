package com.scor.rr.service.adjustement.pltAdjustment;

import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import com.scor.rr.domain.dto.OEPMetric;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;

import java.util.List;

public interface ICalculAdjustment {

    List<PLTLossData> oepReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBendings);
    List<PLTLossData> eefReturnPeriodBanding(List<PLTLossData> pltLossDatas, boolean cap, List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBendings);
    List<PLTLossData> eefFrequency(List<PLTLossData> pltLossDatas, boolean cap, double rpmf);
    List<PLTLossData> nonLineaireEventDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas);
    List<PLTLossData> nonLineaireEventPeriodDrivenAdjustment(List<PLTLossData> pltLossDatas, boolean cap, List<PEATData> peatDatas);
    List<PLTLossData> lineaireAdjustement(List<PLTLossData> pltLossDatas, double lmf, boolean cap);
    List<OEPMetric> getOEPMetric(List<PLTLossData> pltLossData);

}
