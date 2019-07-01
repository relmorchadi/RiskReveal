package com.scor.rr.service.adjustement;

import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalculAdjustementOEPReturnPeriodBandingTest {
    private List<PLTLossData> pltLossDataList;
    private List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings;
    private double periodConstante;
    private boolean cap;
    @Before
    public void setUp() {
        pltLossDataList = new ArrayList<PLTLossData>(){{
            add(new PLTLossData(36,8443694,(double)41334,1,(double)3957801220L,(double)14240566));
            add(new PLTLossData(68,8441785,(double)41542,1,(double)476993400L, 3455929.25));
            add(new PLTLossData(74,8440073,(double)41512,1,(double)75025550L, 211684.75));
            add(new PLTLossData(94,8440368,(double)41414,1,(double)292462560L, 112700.48));
            add(new PLTLossData(117,8309116,(double)41292,1,(double)116508000000L, 12625397800L));
        }};
        cap = true;
        periodConstante = 100000;
        adjustmentReturnPeriodBendings = new ArrayList<AdjustmentReturnPeriodBending>(){{
            add(new AdjustmentReturnPeriodBending(500,0.87));
            add(new AdjustmentReturnPeriodBending(750,0.9));
            add(new AdjustmentReturnPeriodBending(10000,0.93));
            add(new AdjustmentReturnPeriodBending(20000,0.97));

        }};

    }
    @Test
    public void oepReturnPeriodBanding() {
        CalculAdjustement calculAdjustement = new CalculAdjustement();
        assertNull(calculAdjustement.oepReturnPeriodBanding(pltLossDataList,cap,null,periodConstante));
        assertNull(calculAdjustement.oepReturnPeriodBanding(pltLossDataList,cap,new ArrayList<>(),periodConstante));
        assertNull(calculAdjustement.oepReturnPeriodBanding(null,cap,adjustmentReturnPeriodBendings,periodConstante));
        assertNull(calculAdjustement.oepReturnPeriodBanding(new ArrayList<>(),cap,adjustmentReturnPeriodBendings,periodConstante));
        assertNotNull(calculAdjustement.oepReturnPeriodBanding(pltLossDataList,cap,adjustmentReturnPeriodBendings,periodConstante));
    }
}