package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
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

public class CalcAdjustmentsNonLinearEventDrivenAdjustmentTest {
    private static final Logger log = LoggerFactory.getLogger(CalcAdjustmentsNonLinearEventDrivenAdjustmentTest.class);

    private List<PLTLossData> pltLossDataList;
    private List<PEATData> adjustmentReturnPeriodBendings;
    private boolean cap;

    @Before
    public void setUp() {
        log.info("Launch test for non linear event  driven adjustment");
        pltLossDataList = new ArrayList<PLTLossData>(){{
            add(new PLTLossData(36,8443694,(double)41334,1,(double)3957801220L,(double)14240566));
            add(new PLTLossData(68,8441785,(double)41542,1,(double)476993400L, 3455929.25));
            add(new PLTLossData(74,8440073,(double)41512,1,(double)75025550L, 211684.75));
            add(new PLTLossData(94,8440368,(double)41414,1,(double)292462560L, 112700.48));
            add(new PLTLossData(117,8309116,(double)41292,1,(double)116508000000L, 12625397800L));
        }};
        cap = true;
        adjustmentReturnPeriodBendings = new ArrayList<PEATData>(){{
            add(new PEATData(36,8443694,1,1.4));
            add(new PEATData(68,8441785,1,2.2));
            add(new PEATData(74,8440073,1,0.7));
            add(new PEATData(94,8443621,1,1.5));

        }};
    }

    @Test
    public void nonLineaireEventDrivenAdjustmentNullPeatData() {
        log.info("Launch test for non linear event  driven adjustment with parameter PEAT DATA NULL");
        assertNull(CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossDataList,cap,null));
    }
    @Test
    public void nonLineaireEventDrivenAdjustmentEmptyPeatData() {
        log.info("Launch test for non linear event  driven adjustment with parameter PEAT DATA EMPTY");
        assertNull(CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossDataList,cap,new ArrayList<>()));
    }

    @Test
    public void nonLineaireEventDrivenAdjustmentNullPlt() {
        log.info("Launch test for non linear event  driven adjustment with PLT NULL");
        assertNull(CalculAdjustement.nonLinearEventDrivenAdjustment(null,cap,adjustmentReturnPeriodBendings));
    }

    @Test
    public void nonLineaireEventDrivenAdjustmentEmptyPlt() {
        log.info("Launch test for non linear event  driven adjustment with PLT Empty");
        assertNull(CalculAdjustement.nonLinearEventDrivenAdjustment(new ArrayList<>(),cap,adjustmentReturnPeriodBendings));
    }

    @Test
    public void testNonLineaireEventDrivenAdjustmentFileEmpty() {
        BinaryPLTFileReader binarypltFileReader = new BinaryPLTFileReader();
        log.info("Launch test for Linear Adjustment with an empty plt file ");
        try {
            pltLossDataList = binarypltFileReader.read(new File("src/main/resources/file/empty.bin"));
            assertThat(pltLossDataList.isEmpty(), is(true));
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossDataList,cap,adjustmentReturnPeriodBendings);
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
    public void testNonLineaireEventDrivenAdjustmentFileFormatDateWrong() {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Linear Adjustment for a plt file ");
        try {
            pltLossDataList = csvpltFileReader.read(new File("src/main/resources/file/event_date_format_wrong.csv"));
        } catch (EventDateFormatException ex) {
            assertEquals("Event Date format wrong. Correct format: dd/MM/yyyy HH:mm:ss", ex.getMessage());
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossDataList,cap,adjustmentReturnPeriodBendings);
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
    public void testNonLineaireEventDrivenAdjustment() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for EEF Frequency Adjustment for File ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv"));
        pltLossData = CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossData,cap,adjustmentReturnPeriodBendings);
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossDataList = readPltFile.read(new File("src/main/resources/file/nonLineaireEventPeriodDrivenAdjustment.csv"));
        //assertEquals(pltLossDataList,pltLossData);
        log.info("End test for non linear event capped  driven adjustment with a File ");
    }

    @Test
    public void testNonLineaireEventDrivenAdjustmentUncapped() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for EEF Frequency Adjustment for File ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure).csv"));
        pltLossData = CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossData,false,adjustmentReturnPeriodBendings);
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossDataList = readPltFile.read(new File("src/main/resources/file/nonLineaireEventPeriodDrivenAdjustmentUncapped.csv"));
        //assertEquals(pltLossDataList,pltLossData);
        log.info("End test for non linear event uncapped driven adjustment with a File ");
    }
}