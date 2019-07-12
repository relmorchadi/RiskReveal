package com.scor.rr;

import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainApplication {

    public static void main(String[] args) throws IOException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        try {
            List<PLTLossData> pltLossData = csvpltFileReader.read(new File("C:\\Users\\u008208\\Desktop\\PLT Adjustment Test PLT (Pure).csv"));
            CalculAdjustement calculAdjustement = new CalculAdjustement();
            pltLossData = calculAdjustement.lineaireAdjustement(pltLossData,2,false);
            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
            csvpltFileWriter.write(pltLossData, new File("C:\\Users\\u008208\\Desktop\\Lineaire Adjustment with lmf 2 cap.csv"));
            pltLossData = calculAdjustement.lineaireAdjustement(pltLossData,250,true);
            csvpltFileWriter.write(pltLossData, new File("C:\\Users\\u008208\\Desktop\\Lineaire Adjustment with lmf 250  uncap.csv"));
            pltLossData = calculAdjustement.eefFrequency(pltLossData,false,2,100000);
            csvpltFileWriter.write(pltLossData, new File("C:\\Users\\u008208\\Desktop\\eef frequency .csv"));
        } catch (RRException e) {
            e.printStackTrace();
        }

    }
}
