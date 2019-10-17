package com.scor.rr.importBatch.processing.ylt;

import java.io.File;
import java.util.HashMap;

/**
 * Created by U002629 on 19/02/2015.
 */
public class MockedPEQTSelector implements PEQTSelector {

    private final HashMap<String, String> map;

    private final String peqtPath;

    public String getPeqtPath() {
        return peqtPath;
    }

    /*
 RMS_SimSet_11_IDEQ_tst.bin
 RMS_SimSet_15_PHEQ_tst.bin
 RMS_SimSet_18_GUEQ_tst.bin
 RMS_SimSet_19_GUWS_tst.bin
 RMS_SimSet_25_CBEQ_tst.bin
 RMS_SimSet_26_ILEQ_tst.bin
 RMS_SimSet_28_CCEQ_tst.bin
 RMS_SimSet_30_INEQ_tst.bin
 RMS_SimSet_31_NZEQ_tst.bin
 RMS_SimSet_37_EUCS_tst.bin
 RMS_SimSet_45_EUFL_tst.bin
 RMS_SimSet_51_JPWS_tst.bin
 RMS_SimSet_52_AUWS_tst.bin
 RMS_SimSet_54_NAEQ_tst.bin
 RMS_SimSet_56_ACEQ_tst.bin
 RMS_SimSet_57_NACS_tst.bin
 RMS_SimSet_58_NAWT_tst.bin
 RMS_SimSet_59_AUCS_tst.bin
 RMS_SimSet_60_AHEQ_tst.bin
 RMS_SimSet_61_EUEQ_tst.bin
 RMS_SimSet_65_EUWS_tst.bin
 RMS_SimSet_67_CCWS_tst.bin
 RMS_SimSet_68_JPEQ_tst.bin
 RMS_SimSet_72_NAWS_tst.bin
 RMS_SimSet_7_AUEQ_tst.bin
     */


    public MockedPEQTSelector(String peqtPath) {
        map = new HashMap<>();
        map.put("IDEQ","RMS_SimSet_11_IDEQ.bin");
        map.put("CLEQ","EventSetId_12_CLEQ.bin");
        map.put("TREQ","EventSetId_13_TREQ.bin");
        map.put("COEQ","EventSetId_14_COEQ.bin");
        map.put("PHEQ","RMS_SimSet_15_PHEQ.bin");
        map.put("GUEQ","RMS_SimSet_18_GUEQ.bin");
        map.put("GUWS","RMS_SimSet_19_GUWS.bin");
        map.put("HKWS","EventSetId_20_HKWS.bin");
        map.put("MXEQ","EventSetId_23_MXEQ.bin");
        map.put("PTEQ","EventSetId_24_PTEQ.bin");
        map.put("CBEQ","RMS_SimSet_25_CBEQ.bin");
        map.put("ILEQ","RMS_SimSet_26_ILEQ.bin");
        map.put("CCEQ","RMS_SimSet_28_CCEQ.bin");
        map.put("INEQ","RMS_SimSet_30_INEQ.bin");
        map.put("NZEQ","RMS_SimSet_31_NZEQ.bin");
        map.put("NATO","EventSetId_34_NATO.bin");
        map.put("EUCS","RMS_SimSet_37_EUCS.bin");
        map.put("EUFL","RMS_SimSet_45_EUFL.bin");
        map.put("USWS","EventSetId_48_USWS.bin");
        map.put("CBWS","EventSetId_49_CBWS.bin");
        map.put("JPWS","RMS_SimSet_51_JPWS.bin");
        map.put("AUWS","RMS_SimSet_52_AUWS.bin");
        map.put("NAEQ","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("ACEQ","RMS_SimSet_56_ACEQ.bin");
        map.put("NACS","RMS_SimSet_57_NACS.bin");
        map.put("NAWT","RMS_SimSet_58_NAWT.bin");
        map.put("AUCS","RMS_SimSet_59_AUCS.bin");
        map.put("AHEQ","RMS_SimSet_60_AHEQ.bin");
        map.put("EUEQ","RMS_SimSet_61_EUEQ.bin");
        map.put("EUWS","RMS_SimSet_65_EUWS.bin");
        map.put("CCWS","RMS_SimSet_67_CCWS.bin");
        map.put("JPEQ","RMS_SimSet_68_JPEQ.bin");
        map.put("NAWS","RMS_SimSet_72_NAWS_tst.bin");
        map.put("AUEQ","RMS_SimSet_7_AUEQ.bin");
        map.put("GREQ","EventSetId_8_GREQ.bin");

        map.put("USEQ\\MW","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("CAEQ\\WE","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("CAEQ\\EA","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("NAHU\\USM","RMS_SimSet_72_NAWS_tst.bin");


        map.put("SBS EQ USAMW","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("SBS EQ CANWE","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("SBS EQ CANEA","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("SBS WS USA","RMS_SimSet_72_NAWS_tst.bin");
        map.put("SBS_EQ_USAMW","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("SBS_EQ_CANWE","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("SBS_EQ_CANEA","RMS_SimSet_54_NAEQ_tst.bin");
        map.put("SBS_WS_USA","RMS_SimSet_72_NAWS_tst.bin");

        this.peqtPath = peqtPath;
    }

    @Override
    public String getPEQTFile(String region, String peril){
        return new File(peqtPath,map.get(region+peril)).getAbsolutePath();
    }

    @Override
    public String getPEQTFile(String regionPeril){
        return new File(peqtPath,map.get(regionPeril)).getAbsolutePath();
    }

    @Override
    public String getPEQTFunction(String dlmProfile) {
        return "BETA";
    }

    @Override
    public String getPETType(String dlmProfile) {
        return "Quantile";
    }

    @Override
    public PET getPETInfo(String dlmProfile) {
        return null;
    }

}
