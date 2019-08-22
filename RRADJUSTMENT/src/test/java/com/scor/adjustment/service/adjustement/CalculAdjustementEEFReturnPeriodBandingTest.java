package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.AdjustmentReturnPeriodBandingParameterEntity;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
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

public class CalculAdjustementEEFReturnPeriodBandingTest {

    private static final Logger log = LoggerFactory.getLogger(CalculAdjustementEEFReturnPeriodBandingTest.class);

    private List<PLTLossData> pltLossDataList;
    private List<AdjustmentReturnPeriodBandingParameterEntity> adjustmentReturnPeriodBandings;
    private double periodConstante;
    private boolean cap;
    @Before
    public void setUp() {
        log.info("Launch test for EEF Return Period Banding Adjustment");
        pltLossDataList = new ArrayList<PLTLossData>(){{
            add(new PLTLossData(36,8443694,(double)41334,1,(double)3957801220L,(double)14240566));
            add(new PLTLossData(68,8441785,(double)41542,1,(double)476993400L, 3455929.25));
            add(new PLTLossData(74,8440073,(double)41512,1,(double)75025550L, 211684.75));
            add(new PLTLossData(94,8440368,(double)41414,1,(double)292462560L, 112700.48));
            add(new PLTLossData(117,8309116,(double)41292,1,(double)116508000000L, 12625397800L));
        }};
        cap = true;
        periodConstante = 100000;
        adjustmentReturnPeriodBandings = new ArrayList<AdjustmentReturnPeriodBandingParameterEntity>(){{
            add(new AdjustmentReturnPeriodBandingParameterEntity(500,0.87));
            add(new AdjustmentReturnPeriodBandingParameterEntity(750,0.9));
            add(new AdjustmentReturnPeriodBandingParameterEntity(10000,0.93));
            add(new AdjustmentReturnPeriodBandingParameterEntity(20000,0.97));

        }};
    }
    @After
    public void tearDown() {
        log.info("END test for EEF Return Period Banding Adjustment");
    }

    @Test
    public void testEEFReturnPeriodBandingNullAdjustmentReturnPeriodBending() {
        log.info("Launch test for EEF Return Period Banding Adjustment with parameter table [return period,lmf] null");
        assertNull(CalculAdjustement.eefReturnPeriodBanding(pltLossDataList,cap,null));



    }
    @Test
    public void testEEFReturnPeriodBandingEmptyAdjustmentReturnPeriodBending() {
        log.info("Launch test for EEF Return Period Banding Adjustment with parameter table [return period,lmf] empty");
        assertNull(CalculAdjustement.eefReturnPeriodBanding(pltLossDataList,cap,new ArrayList<>()));
    }

    @Test
    public void testEEFReturnPeriodBandingNullPlt() {
        log.info("Launch test for EEF Return Period Banding Adjustment with PLT NULL");
        assertNull(CalculAdjustement.eefReturnPeriodBanding(null,cap,adjustmentReturnPeriodBandings));
    }

    @Test
    public void testEEFReturnPeriodBandingEmptyPlt() {
        log.info("Launch test for EEF Return Period Banding Adjustment with PLT empty");
        assertNull(CalculAdjustement.eefReturnPeriodBanding(new ArrayList<>(),cap,adjustmentReturnPeriodBandings));
    }

    @Test
    public void testEEFReturnPeriodBandingFileEmpty() {
        BinaryPLTFileReader binarypltFileReader = new BinaryPLTFileReader();
        log.info("Launch test for EEF Return Period Banding Adjustment with an empty plt file ");
        try {
            pltLossDataList = binarypltFileReader.read(new File("src/main/resources/file/empty.bin"));
            assertThat(pltLossDataList.isEmpty(), is(true));
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.eefReturnPeriodBanding(pltLossDataList,cap,adjustmentReturnPeriodBandings);
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
    public void testEEFReturnPeriodBandingFileFormatDateWrong() {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test For EEF Return Period Banding Adjustment with a wrong plt file ");
        try {
            pltLossDataList = csvpltFileReader.read(new File("src/main/resources/file/event_date_format_wrong.csv"));
        } catch (EventDateFormatException ex) {
            assertEquals("Event Date format wrong. Correct format: dd/MM/yyyy HH:mm:ss", ex.getMessage());
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.eefReturnPeriodBanding(pltLossDataList,cap,adjustmentReturnPeriodBandings);
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
    public void testEEFReturnPeriodBandingFile() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for EEF Return Period Banding Adjustment for a file");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv"));
        pltLossData = CalculAdjustement.eefReturnPeriodBanding(pltLossData,cap,adjustmentReturnPeriodBandings);
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        csvpltFileWriter.write(pltLossData,new File("src/main/resources/file/pltEEFReturnPeriodBanding.csv"));
        log.info("Launch test for EEF Return Period Banding Adjustment for a file");
    }
}