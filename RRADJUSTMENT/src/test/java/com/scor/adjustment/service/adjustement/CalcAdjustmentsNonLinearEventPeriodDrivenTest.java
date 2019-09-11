package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.EventDateFormatException;
import com.scor.rr.exceptions.pltfile.PLTDataNullException;
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

public class CalcAdjustmentsNonLinearEventPeriodDrivenTest {
    private static final Logger log = LoggerFactory.getLogger(CalcAdjustmentsNonLinearEventPeriodDrivenTest.class);

    private List<PLTLossData> pltLossDataList;
    private List<PEATData> adjustmentReturnPeriodBendings;
    private boolean cap;
    @Before
    public void setUp() {
        log.info("Launch test for non linear event period driven");
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
    public void nonLinearEventPeriodDrivenAdjustmentNullPeatData() {
        log.info("Launch test for non linear event period driven with reference parameter PEAT DATA null");
        assertNull(CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossDataList,cap,null));
    }
    @Test
    public void nonLinearEventPeriodDrivenAdjustmentEmptyPeatData() {
        log.info("Launch test for non linear event period driven with reference parameter PEAT DATA Empty");
        assertNull(CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossDataList,cap,new ArrayList<>()));
    }
    @Test
    public void nonLinearEventPeriodDrivenAdjustmentNullPct() {
        log.info("Launch test for non linear event period driven with reference parameter PLT NULL");
        assertNull(CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(null,cap,adjustmentReturnPeriodBendings));
    }
    @Test
    public void nonLinearEventPeriodDrivenEmptyPct() {
        log.info("Launch test for non linear event period driven with reference parameter PLT EMPTY");
        assertNull(CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(new ArrayList<>(),cap,adjustmentReturnPeriodBendings));
    }

    @Test
    public void testNonLinearEventPeriodDrivenFileEmpty() {
        BinaryPLTFileReader binarypltFileReader = new BinaryPLTFileReader();
        log.info("Launch test for Linear Adjustment with an empty plt file ");
        try {
            pltLossDataList = binarypltFileReader.read(new File("src/main/resources/file/empty.bin"));
            assertThat(pltLossDataList.isEmpty(), is(true));
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossDataList,cap,adjustmentReturnPeriodBendings);
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
    public void testNonLinearEventPeriodDrivenFileFormatDateWrong() {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Linear Adjustment for a plt file ");
        try {
            pltLossDataList = csvpltFileReader.read(new File("src/main/resources/file/event_date_format_wrong.csv"));
        } catch (EventDateFormatException ex) {
            assertEquals("Event Date format wrong. Correct format: dd/MM/yyyy HH:mm:ss", ex.getMessage());
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossDataList,cap,adjustmentReturnPeriodBendings);
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
    public void testNonLinearEventPeriodDrivenAdjustmentCapped() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for non linear event period driven  Adjustment with a file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossData,cap,adjustmentReturnPeriodBendings);
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossDataList = readPltFile.read(new File("src/main/resources/file/nonLinearEventPeriodDrivenCapped.csv"));
        assertEquals(pltLossDataList,pltLossData);
        log.info("END test for non linear event period driven  Adjustment with a file ");
    }

    @Test
    public void testNonLinearEventPeriodDrivenAdjustmentUnCapped() throws RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for non linear event period driven  Adjustment with a file ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossData,false,adjustmentReturnPeriodBendings);
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossDataList = readPltFile.read(new File("src/main/resources/file/nonLinearEventPeriodDrivenUnCapped.csv"));
        assertEquals(pltLossDataList,pltLossData);
        log.info("END test for non linear event period driven  Adjustment with a file ");
    }

    //TODO: test for uncapped

}