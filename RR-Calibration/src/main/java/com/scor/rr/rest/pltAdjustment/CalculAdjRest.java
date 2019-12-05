package com.scor.rr.rest.pltAdjustment;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.dto.AEPMetric;
import com.scor.rr.domain.dto.CalculAdjustmentDto;
import com.scor.rr.domain.dto.OEPMetric;
import com.scor.rr.domain.dto.StaticType;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import com.scor.rr.service.adjustement.pltAdjustment.StatisticAdjustment;
import org.apache.commons.io.FileUtils;
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

    @PostMapping
    public List<PLTLossData> CalcAdjustment(@RequestBody CalculAdjustmentDto calculAdjustmentDto) throws RRException, IOException, com.scor.rr.exceptions.RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(calculAdjustmentDto.getPathToFile()));
        if (LINEAR.equals(calculAdjustmentDto.getType()) ){
            pltLossData = CalculAdjustement.linearAdjustement(pltLossData, calculAdjustmentDto.getLmf(), calculAdjustmentDto.isCap());
        }
        else if (EEF_FREQUENCY.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.eefFrequency(pltLossData, calculAdjustmentDto.isCap(),calculAdjustmentDto.getRpmf());
        }
        else if (NONLINEAR_OEP_RPB.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.oepReturnBanding(pltLossData, calculAdjustmentDto.isCap(), calculAdjustmentDto.getAdjustmentReturnPeriodBandings());
        }
        else if (NONLINEAR_EVENT_DRIVEN.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getPeatDatas());
        }
        else if (NONLINEAR_EVENT_PERIOD_DRIVEN.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getPeatDatas());
        }
        else if (NONLINEAR_EEF_RPB.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.eefReturnPeriodBanding(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getAdjustmentReturnPeriodBandings());
        }
        File f = new File(calculAdjustmentDto.getNewFilePath());
        if(!f.exists()) {
            FileUtils.touch(f);
        }
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        csvpltFileWriter.write(pltLossData, f);
        return pltLossData.stream().sorted(Comparator.comparing(PLTLossData::getLoss)).collect(Collectors.toList());
    }


    @GetMapping("aepMetric")
    public List<AEPMetric> aepMetric(String pathToFile) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        return CalculAdjustement.getAEPMetric(pltLossData);
    }
    @GetMapping("oepMetric")
    public List<OEPMetric> oepMetric(String pathToFile) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        return CalculAdjustement.getOEPMetric(pltLossData);
    }

    @GetMapping("statistic")
    public double CoefOfVariance(String pathToFile,StaticType type) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        if(StaticType.CoefOfVariance.equals(type)) {
            return StatisticAdjustment.CoefOfVariance(pltLossData);
        }
        if(StaticType.stdDev.equals(type)) {
            return StatisticAdjustment.stdDev(pltLossData);
        }
        if(StaticType.averageAnnualLoss.equals(type)) {
            return StatisticAdjustment.averageAnnualLoss(pltLossData);
        }
        return 0;
    }
    @GetMapping("AEPTvAR-METRIC")
    public List<AEPMetric> AEPTvAR(String pathToFile) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        return StatisticAdjustment.AEPTVaRMetrics(CalculAdjustement.getAEPMetric(pltLossData));
    }
    @GetMapping("OEPTvAR-METRIC")
    public List<OEPMetric> OEPTvAR(String pathToFile) throws RRException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(pathToFile));
        return StatisticAdjustment.OEPTVaRMetrics(CalculAdjustement.getOEPMetric(pltLossData));
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
