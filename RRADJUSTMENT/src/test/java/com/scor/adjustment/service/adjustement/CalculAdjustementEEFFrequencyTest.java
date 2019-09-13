package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.fileExceptionPlt.EventDateFormatException;
import com.scor.rr.exceptions.fileExceptionPlt.PLTDataNullException;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class CalculAdjustementEEFFrequencyTest {
    private static final Logger log = LoggerFactory.getLogger(CalculAdjustementEEFFrequencyTest.class);
    private List<PLTLossData> pltLossDataList;
    private double rpmf;
    private boolean cap;

    @Before
    public void setUp() throws Exception {
        log.info("Launch test for EEF Frequency Adjustment");
        pltLossDataList = new ArrayList<PLTLossData>(){{
            add(new PLTLossData(36,8443694,(double)41334,1,(double)3957801220L,(double)14240566));
            add(new PLTLossData(68,8441785,(double)41542,1,(double)476993400L, 3455929.25));
            add(new PLTLossData(74,8440073,(double)41512,1,(double)75025550L, 211684.75));
            add(new PLTLossData(94,8440368,(double)41414,1,(double)292462560L, 112700.48));
            add(new PLTLossData(117,8309116,(double)41292,1,(double)116508000000L, 12625397800L));
        }};
        cap = true;
        rpmf =0.4;
    }

    @After
    public void tearDown() {
        log.info("END test for EEF Frequency Adjustment");
    }
    @Test
    public void eefFrequencyNegativeRpmf() {
        log.info("Launch test for EEF Frequency Adjustment for negative rpmf = {}",rpmf);
        assertNull(CalculAdjustement.eefFrequency(pltLossDataList,cap,-2));
    }
    @Test
    public void eefFrequencyNullPlt() {
        log.info("Launch test for EEF Frequency Adjustment for null plt ");
        assertNull(CalculAdjustement.eefFrequency(null,cap,rpmf));
    }
    @Test
    public void eefFrequencyEmptyPlt() {
        log.info("Launch test for EEF Frequency Adjustment for an empty plt ");
        assertNull(CalculAdjustement.eefFrequency(new ArrayList<>(),cap,rpmf));
    }

    @Test
    public void eefFrequencyFileEmpty() {
        BinaryPLTFileReader binarypltFileReader = new BinaryPLTFileReader();
        log.info("Launch test for EEF Frequency Adjustment for a plt file ");
        try {
            pltLossDataList = binarypltFileReader.read(new File("src/main/resources/file/empty.bin"));
            assertThat(pltLossDataList.isEmpty(), is(true));
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.eefFrequency(pltLossDataList,cap,rpmf);
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        try {
            csvpltFileWriter.write(pltLossDataList,new File("src/main/resources/file/pltEEFFrequency.csv"));
        } catch (PLTDataNullException e) {
            assertEquals("PLT Data is null", e.getMessage());
        } catch (Exception anotherException) {
            fail();
        }
    }

    @Test
    public void eefFrequencyFileFormatDateWrong() {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for EEF Frequency Adjustment for a plt file ");
        try {
            pltLossDataList = csvpltFileReader.read(new File("src/main/resources/file/event_date_format_wrong.csv"));
        } catch (EventDateFormatException ex) {
            assertEquals("Event Date format wrong. Correct format: dd/MM/yyyy HH:mm:ss", ex.getMessage());
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.eefFrequency(pltLossDataList,cap,rpmf);
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        try {
            csvpltFileWriter.write(pltLossDataList,new File("src/main/resources/file/pltEEFFrequency.csv"));
        } catch (PLTDataNullException e) {
            assertEquals("PLT Data is null", e.getMessage());
        } catch (Exception anotherException) {
            fail();
        }
    }


    @Test
    public void eefFrequencyFile() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for EEF Frequency Adjustment for a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = CalculAdjustement.eefFrequency(pltLossData,cap,rpmf);
        List<PLTLossData> pltLossDataResult = csvpltFileReader.read(new File("src/main/resources/file/eef frequency.csv"));
        for(int i=0;i<pltLossData.size();i++) {
            assertEquals(pltLossData.get(i),pltLossDataResult.get(i));
        }
    }

    @Test
    public void eefFrequencyRPMF2Uncapped() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for EEF Frequency Adjustment for a plt file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = CalculAdjustement.eefFrequency(pltLossData,false,2);
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        csvpltFileWriter.write(pltLossDataList,new File("src/main/resources/file/eefFrequencyRPMF2Uncapped.csv"));
        List<PLTLossData> pltLossDataResult = csvpltFileReader.read(new File("src/main/resources/file/eefFrequencyRPMF2Uncapped.csv"));
//        for(int i=0;i<pltLossData.size();i++) {
//            assertEquals(pltLossData.get(i),pltLossDataResult.get(i));
//        }
    }
}