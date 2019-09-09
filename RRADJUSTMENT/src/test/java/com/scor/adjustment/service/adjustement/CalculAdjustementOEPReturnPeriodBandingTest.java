package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.fileExceptionPlt.EventDateFormatException;
import com.scor.rr.exceptions.fileExceptionPlt.PLTDataNullException;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
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

public class CalculAdjustementOEPReturnPeriodBandingTest {
    private static final Logger log = LoggerFactory.getLogger(CalcAdjustmentsNonLinearEventDrivenAdjustmentTest.class);
    private List<PLTLossData> pltLossDataList;
    private List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings;
    private double periodConstante;
    private boolean cap;
    @Before
    public void setUp() {
        log.info("Launch test for OEP Return Period adjustment");
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
            add(new AdjustmentReturnPeriodBending(500d,0.87));
            add(new AdjustmentReturnPeriodBending(750d,0.9));
            add(new AdjustmentReturnPeriodBending(10000d,0.93));
            add(new AdjustmentReturnPeriodBending(20000d,0.97));

        }};

    }
    @Test
    public void oepReturnPeriodBandingNullParameterAdjustmentReturnPeriodBending() {
        log.info("Launch test for OEP Return Period adjustment with parameter Adjustment return Period Bending [return period,lmf] null");
        assertNull(CalculAdjustement.oepReturnPeriodBanding(pltLossDataList,cap,null));
    }
    @Test
    public void oepReturnPeriodBandingEmptyParameterAdjustmentReturnPeriodBending() {
        log.info("Launch test for OEP Return Period adjustment with parameter Adjustment return Period Bending [return period,lmf] empty");
        assertNull(CalculAdjustement.oepReturnPeriodBanding(pltLossDataList,cap,new ArrayList<>()));
    }

    @Test
    public void oepReturnPeriodBandingNullPlt() {
        log.info("Launch test for OEP Return Period adjustment with PLT null");
        assertNull(CalculAdjustement.oepReturnPeriodBanding(null,cap,adjustmentReturnPeriodBendings));
    }

    @Test
    public void oepReturnPeriodBandingEmptyPlt() {
        log.info("Launch test for OEP Return Period adjustment with PLT empty");
        assertNull(CalculAdjustement.oepReturnPeriodBanding(new ArrayList<>(),cap,adjustmentReturnPeriodBendings));
    }

    @Test
    public void testOepReturnPeriodBandingAdjustmentFileEmpty() {
        BinaryPLTFileReader binarypltFileReader = new BinaryPLTFileReader();
        log.info("Launch test for Linear Adjustment with an empty plt file ");
        try {
            pltLossDataList = binarypltFileReader.read(new File("src/main/resources/file/empty.bin"));
            assertThat(pltLossDataList.isEmpty(), is(true));
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.oepReturnPeriodBanding(pltLossDataList,cap,adjustmentReturnPeriodBendings);
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
    public void testOepReturnPeriodBandingAdjustmentFileFormatDateWrong() {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Linear Adjustment for a plt file ");
        try {
            pltLossDataList = csvpltFileReader.read(new File("src/main/resources/file/event_date_format_wrong.csv"));
        } catch (EventDateFormatException ex) {
            assertEquals("Event Date format wrong. Correct format: dd/MM/yyyy HH:mm:ss", ex.getMessage());
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.oepReturnPeriodBanding(pltLossDataList,cap,adjustmentReturnPeriodBendings);
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
    public void testoepReturnPeriodBandingAdjustmentFile() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for oep return period banding with a file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = CalculAdjustement.oepReturnPeriodBanding(pltLossData,cap,adjustmentReturnPeriodBendings);
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossDataList = readPltFile.read(new File("src/main/resources/file/pltoepReturnPeriodBanding.csv"));
        assertEquals(pltLossDataList,pltLossData);
        log.info("End test for oep return period banding with a file");
    }

    @Test
    public void testoepReturnPeriodBandingAdjustmentUncappedFile() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for oep return period banding with a file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = CalculAdjustement.oepReturnPeriodBanding(pltLossData,false,adjustmentReturnPeriodBendings);
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossDataList = readPltFile.read(new File("src/main/resources/file/pltoepReturnPeriodBandingUnCapped.csv"));
        assertEquals(pltLossDataList,pltLossData);
        log.info("End test for oep return period banding with a file");
    }

    //TODO: test for Uncapped
}