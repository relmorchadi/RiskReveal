package com.scor.rr.rest.pltAdjustment;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.SummaryStatisticHeaderDetail;
import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculateAdjustmentService;
import com.scor.rr.service.adjustement.pltAdjustment.StatisticAdjustment;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;

@RestController
@RequestMapping("api/calc")
public class CalculAdjRest {

    @Autowired
    CalculateAdjustmentService calculateAdjustmentService;

    @PostMapping
    public List<PLTLossData> CalcAdjustment(@RequestBody CalculAdjustmentDto calculAdjustmentDto) throws RRException, IOException, com.scor.rr.exceptions.RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(calculAdjustmentDto.getPathToFile()));
        if (LINEAR.equals(calculAdjustmentDto.getType()) ){
            pltLossData = CalculateAdjustmentService.linearAdjustement(pltLossData, calculAdjustmentDto.getLmf(), calculAdjustmentDto.isCap());
        }
        else if (EEF_FREQUENCY.equals(calculAdjustmentDto.getType())) {
            pltLossData = calculateAdjustmentService.eefFrequency(pltLossData, calculAdjustmentDto.isCap(),calculAdjustmentDto.getRpmf());
        }
        else if (NONLINEAR_OEP_RPB.equals(calculAdjustmentDto.getType())) {
            pltLossData = calculateAdjustmentService.oepReturnBanding(pltLossData, calculAdjustmentDto.isCap(), calculAdjustmentDto.getAdjustmentReturnPeriodBandings());
        }
        else if (NONLINEAR_EVENT_DRIVEN.equals(calculAdjustmentDto.getType())) {
            pltLossData = calculateAdjustmentService.nonLinearEventDrivenAdjustment(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getPeatDatas());
        }
        else if (NONLINEAR_EVENT_PERIOD_DRIVEN.equals(calculAdjustmentDto.getType())) {
            pltLossData = calculateAdjustmentService.nonLinearEventPeriodDrivenAdjustment(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getPeatDatas());
        }
        else if (NONLINEAR_EEF_RPB.equals(calculAdjustmentDto.getType())) {
            pltLossData = calculateAdjustmentService.eefReturnPeriodBanding(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getAdjustmentReturnPeriodBandings());
        }
        File f = new File(calculAdjustmentDto.getNewFilePath());
        if (!f.exists()) {
            FileUtils.touch(f);
        }
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        csvpltFileWriter.write(pltLossData, f);
        return pltLossData.stream().sorted(Comparator.comparing(PLTLossData::getLoss)).collect(Collectors.toList());
    }

//    @PostMapping("calculateSummaryStatisticHeaderDetail")
//    private SummaryStatisticHeaderDetail calculateSummaryStatisticHeaderDetail(Long pltId, MetricType type) {
//        return calculateAdjustmentService.calculateSummaryStatisticHeaderDetail(pltId, type);
//    }
//
//    // todo
//    @PostMapping("calculateSummaryStatisticHeader")
//    private SummaryStatisticHeaderDetail calculateSummaryStatisticHeader(Long pltId, MetricType type) {
//        return calculateAdjustmentService.calculateSummaryStatisticHeader(pltId, type);
//    }

    @GetMapping("aepMetric")
    public EPMetric aepMetric(String pathToFile) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        return calculateAdjustmentService.getAEPMetric(pltLossData);
    }

    @GetMapping("oepMetric")
    public EPMetric oepMetric(String pathToFile) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        return calculateAdjustmentService.getOEPMetric(pltLossData);
    }

    @GetMapping("statistic")
    public double calculateSummaryStatisticHeader(String pathToFile, SummaryStatisticType type) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        if(SummaryStatisticType.coefOfVariance.equals(type)) {
            return StatisticAdjustment.CoefOfVariance(pltLossData);
        }
        if(SummaryStatisticType.stdDev.equals(type)) {
            return StatisticAdjustment.stdDev(pltLossData);
        }
        if(SummaryStatisticType.averageAnnualLoss.equals(type)) {
            return StatisticAdjustment.averageAnnualLoss(pltLossData);
        }
        return 0;
    }

    @GetMapping("AEPTvAR-METRIC")
    public EPMetric AEPTvAR(String pathToFile) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        return StatisticAdjustment.AEPTVaRMetrics(CalculateAdjustmentService.getAEPMetric(pltLossData).getEpMetricPoints());
    }

    @GetMapping("OEPTvAR-METRIC")
    public EPMetric OEPTvAR(String pathToFile) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        return StatisticAdjustment.OEPTVaRMetrics(CalculateAdjustmentService.getOEPMetric(pltLossData).getEpMetricPoints());
    }

    @GetMapping("convert-to-csv")
    public void convert(String pathToFile,String newfilepath) throws RRException, IOException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        File f = new File(newfilepath);
        if(!f.exists()) {
            FileUtils.touch(f);
        }
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        csvpltFileWriter.write(pltLossData,f);
    }
}
