package com.scor.adjustment.service.adjustement;

import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalculAdjustementNonLineaireEventPeriodDrivenTest {

    private List<PLTLossData> pltLossDataList;
    private List<PEATData> adjustmentReturnPeriodBendings;
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
        adjustmentReturnPeriodBendings = new ArrayList<PEATData>(){{
            add(new PEATData(36,8443694,1,0.7));
            add(new PEATData(36,8443695,1,0.7));
            add(new PEATData(36,8443694,1,0.7));
            add(new PEATData(36,8443694,1,0.7));

        }};
    }
    @Test
    public void nonLineaireEventPeriodDrivenAdjustment() {
        CalculAdjustement calculAdjustement = new CalculAdjustement();
        assertNull(calculAdjustement.nonLineaireEventPeriodDrivenAdjustment(pltLossDataList,cap,null));
        assertNull(calculAdjustement.nonLineaireEventPeriodDrivenAdjustment(pltLossDataList,cap,new ArrayList<>()));
        assertNull(calculAdjustement.nonLineaireEventPeriodDrivenAdjustment(null,cap,adjustmentReturnPeriodBendings));
        assertNull(calculAdjustement.nonLineaireEventPeriodDrivenAdjustment(new ArrayList<>(),cap,adjustmentReturnPeriodBendings));
        assertNotNull(calculAdjustement.nonLineaireEventPeriodDrivenAdjustment(pltLossDataList,cap,adjustmentReturnPeriodBendings));
    }
}