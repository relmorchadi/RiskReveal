package com.scor.adjustment.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileReader;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.EventDateFormatException;
import com.scor.rr.exceptions.pltfile.PLTDataNullException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculateAdjustmentService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class CalculateAdjustmentServiceLinearTest {

    private static final Logger log = LoggerFactory.getLogger(CalculateAdjustmentServiceEEFReturnPeriodBandingTest.class);

    private List<PLTLossData> pltLossDataList;
    private double lmf;
    private boolean cap;

    @Autowired
    CalculateAdjustmentService calculateAdjustmentService;

    @Before
    public void setUp() {
        log.info("Launch test for Linear Adjustment");
        pltLossDataList = new ArrayList<PLTLossData>(){{
            add(new PLTLossData(36,8443694,41334,1,(double)3957801220L,(double)14240566));
            add(new PLTLossData(68,8441785,41542,1,(double)476993400L, 3455929.25));
            add(new PLTLossData(74,8440073,41512,1,(double)75025550L, 211684.75));
            add(new PLTLossData(94,8440368,41414,1,(double)292462560L, 112700.48));
            add(new PLTLossData(117,8309116,41292,1,(double)116508000000L, 12625397800L));
        }};
        cap = true;
        lmf = 0.7;
    }
    //when lmf <= 0
    @Test
    public void linearAdjustementLmfNegative() throws com.scor.rr.exceptions.RRException {

        log.info("Launch test for Linear Adjustment with negative lmf");
        assertNull(calculateAdjustmentService.linearAdjustement(pltLossDataList, -1, cap));

    }
    //when PLT is empty or null
    @Test
    public void linearAdjustementNullPlt() throws com.scor.rr.exceptions.RRException {
        log.info("Launch test for Linear Adjustment with PLT NULL");
        assertNull(calculateAdjustmentService.linearAdjustement(null,lmf,cap));
    }

    @Test
    public void linearAdjustementEmptyPlt() throws com.scor.rr.exceptions.RRException {
        log.info("Launch test for Linear Adjustment with an EMPTY PLT");
        assertNull(calculateAdjustmentService.linearAdjustement(new ArrayList<>(),lmf,cap));
    }

    @Test
    public void testLinearFileEmpty() throws com.scor.rr.exceptions.RRException {
        BinaryPLTFileReader binarypltFileReader = new BinaryPLTFileReader();
        log.info("Launch test for Linear Adjustment with an empty plt file ");
        try {
            pltLossDataList = binarypltFileReader.read(new File("src/main/resources/file/empty.bin"));
            assertThat(pltLossDataList.isEmpty(), is(true));
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = calculateAdjustmentService.linearAdjustement(pltLossDataList,lmf,cap);
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
    public void testLinearFileFormatDateWrong() throws com.scor.rr.exceptions.RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Linear Adjustment for a plt file ");
        try {
            pltLossDataList = csvpltFileReader.read(new File("src/main/resources/file/event_date_format_wrong.csv"));
        } catch (EventDateFormatException ex) {
            assertEquals("Event Date format wrong. Correct format: dd/MM/yyyy HH:mm:ss", ex.getMessage());
        } catch (Exception anotherException) {
            fail();
        }
        pltLossDataList = calculateAdjustmentService.linearAdjustement(pltLossDataList,lmf,cap);
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
    public void testLinearFile() throws RRException, com.scor.rr.exceptions.RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Linear Adjustment for a File ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = calculateAdjustmentService.linearAdjustement(pltLossData,lmf,cap);
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        csvpltFileWriter.write(pltLossData,new File("src/main/resources/file/pltLinear.csv"));
        log.info("End test for Linear Adjustment for a File ");
    }

    @Test
    public void testLinearFileLmf2Cap() throws RRException, com.scor.rr.exceptions.RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Linear Adjustment for a File ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = calculateAdjustmentService.linearAdjustement(pltLossData,2,true);
        List<PLTLossData> pltLossDataResult = csvpltFileReader.read(new File("src/main/resources/file/Lineaire Adjustment with lmf 2 cap.csv"));
        assert pltLossData != null;
        for(int i = 0; i<pltLossData.size(); i++) {
            assertEquals(pltLossData.get(i),pltLossDataResult.get(i));
        }
    }

    @Test
    public void testLinearFileLmf250Uncap() throws RRException, com.scor.rr.exceptions.RRException {
        CSVPLTFileReader csvpltFileReader = new CSVPLTFileReader();
        log.info("Launch test for Linear Adjustment for a File ");
        List<PLTLossData> pltLossData = csvpltFileReader.read(new File("src/main/resources/file/PLT Adjustment Test PLT (Pure) 1.csv"));
        pltLossData = calculateAdjustmentService.linearAdjustement(pltLossData,250,false);
        List<PLTLossData> pltLossDataResult = csvpltFileReader.read(new File("src/main/resources/file/Lineaire Adjustment with lmf 250  uncap.csv"));
        assert pltLossData != null;
        for(int i = 0; i<pltLossData.size(); i++) {
            assertEquals(pltLossData.get(i),pltLossDataResult.get(i));
        }
    }
}