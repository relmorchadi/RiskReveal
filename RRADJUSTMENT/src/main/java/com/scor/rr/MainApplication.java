package com.scor.rr;

import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
            try {
                File file = new File(PATH+new Date().getTime()+".dat");

                // Touch the file, when the file is not exist a new file will be
                // created. If the file exist change the file timestamp.
                FileUtils.touch(file);
                System.out.println("Temp file : " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
