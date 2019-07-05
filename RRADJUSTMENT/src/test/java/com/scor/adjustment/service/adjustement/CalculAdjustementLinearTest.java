package com.scor.adjustment.service.adjustement;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalculAdjustementLinearTest {
    private List<PLTLossData> pltLossDataList;
    private double lmf;
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
        lmf = 0.7;

    }

    @Test
    public void lineaireAdjustement() {
        CalculAdjustement calculAdjustement = new CalculAdjustement();
        //when lmf <= 0
        assertNull(calculAdjustement.lineaireAdjustement(pltLossDataList, -1, cap));
        //when PLT is empty or null
        assertNull(calculAdjustement.lineaireAdjustement(null,lmf,cap));
        assertNull(calculAdjustement.lineaireAdjustement(new ArrayList<>(),lmf,cap));
    }
}