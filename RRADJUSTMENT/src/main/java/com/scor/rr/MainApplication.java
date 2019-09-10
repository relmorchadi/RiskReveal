package com.scor.rr;

import com.scor.rr.configuration.file.LossDataFileUtils;

public class MainApplication {
    private static final String PATH = "RRADJUSTMENT\\src\\main\\resources\\file\\";

    public static void main(String[] args) {
//            List<PLTLossData> pltLossData = csvpltFileReader.read(new File("C:\\Users\\u008208\\Desktop\\PLT Adjustment Test PLT (Pure).csv"));
//            CalculAdjustement calculAdjustement = new CalculAdjustement();
//            List<PLTLossData> pltLossDatalin = calculAdjustement.linearAdjustement(pltLossData,2,false);
//            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
//            csvpltFileWriter.write(pltLossDatalin, new File("C:\\Users\\u008208\\Desktop\\Lineaire Adjustment with lmf 2 cap.csv"));
//            List<PLTLossData> pltLossDatalincap = calculAdjustement.linearAdjustement(pltLossData,250,true);
//            csvpltFileWriter.write(pltLossDatalincap, new File("C:\\Users\\u008208\\Desktop\\Lineaire Adjustment with lmf 250  uncap.csv"));
//            List<PLTLossData> pltLossDataeef = calculAdjustement.eefFrequency(pltLossData,false,2);
//            csvpltFileWriter.write(pltLossDataeef, new File("C:\\Users\\u008208\\Desktop\\eef frequency .csv"));

        LossDataFileUtils.getPltFromLossDataFile("C:\\Users\\u008208\\Desktop\\RMS\\plt.txt");
    }
}
