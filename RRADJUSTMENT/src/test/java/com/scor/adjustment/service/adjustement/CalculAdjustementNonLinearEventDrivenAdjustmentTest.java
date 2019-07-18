package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
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

public class CalculAdjustementNonLinearEventDrivenAdjustmentTest {
    private static final Logger log = LoggerFactory.getLogger(CalculAdjustementNonLinearEventDrivenAdjustmentTest.class);

    private List<PLTLossData> pltLossDataList;
    private List<PEATData> adjustmentReturnPeriodBendings;
    private boolean cap;
    CalculAdjustement calculAdjustement;
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
            add(new PEATData(36,8443694,1,0.7));
            add(new PEATData(36,8443695,1,0.7));
            add(new PEATData(36,8443694,1,0.7));
            add(new PEATData(36,8443694,1,0.7));

        }};

        calculAdjustement = new CalculAdjustement();
    }

    @Test
    public void nonLineaireEventDrivenAdjustmentNullPeatData() {
        log.info("Launch test for non linear event  driven adjustment with parameter PEAT DATA NULL");
        assertNull(calculAdjustement.nonLineaireEventDrivenAdjustment(pltLossDataList,cap,null));
    }
    @Test
    public void nonLineaireEventDrivenAdjustmentEmptyPeatData() {
        log.info("Launch test for non linear event  driven adjustment with parameter PEAT DATA EMPTY");
        assertNull(calculAdjustement.nonLineaireEventDrivenAdjustment(pltLossDataList,cap,new ArrayList<>()));
    }

    @Test
    public void nonLineaireEventDrivenAdjustmentNullPlt() {
        log.info("Launch test for non linear event  driven adjustment with PLT NULL");
        assertNull(calculAdjustement.nonLineaireEventDrivenAdjustment(null,cap,adjustmentReturnPeriodBendings));
    }

    @Test
    public void nonLineaireEventDrivenAdjustmentEmptyPlt() {
        log.info("Launch test for non linear event  driven adjustment with PLT Empty");
        assertNull(calculAdjustement.nonLineaireEventDrivenAdjustment(new ArrayList<>(),cap,adjustmentReturnPeriodBendings));
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
        pltLossDataList = calculAdjustement.nonLineaireEventDrivenAdjustment(pltLossDataList,cap,adjustmentReturnPeriodBendings);
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
        pltLossDataList = calculAdjustement.nonLineaireEventDrivenAdjustment(pltLossDataList,cap,adjustmentReturnPeriodBendings);
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
        pltLossData = calculAdjustement.nonLineaireEventDrivenAdjustment(pltLossData,cap,adjustmentReturnPeriodBendings);
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        csvpltFileWriter.write(pltLossData,new File("src/main/resources/file/nonLineaireEventPeriodDrivenAdjustment.csv"));
        log.info("End test for non linear event  driven adjustment with a File ");
    }

}