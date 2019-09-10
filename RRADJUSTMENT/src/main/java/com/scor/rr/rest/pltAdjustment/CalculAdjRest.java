package com.scor.rr.rest.pltAdjustment;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.dto.AEPMetric;
import com.scor.rr.domain.dto.CalculAdjustmentDto;
import com.scor.rr.domain.dto.OEPMetric;
import com.scor.rr.domain.dto.StaticType;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import com.scor.rr.service.adjustement.pltAdjustment.StatisticAdjustment;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;

@RestController
@RequestMapping("api/calc")
public class CalculAdjRest {

    @PostMapping
    public List<PLTLossData> CalcAdjustement(@RequestBody CalculAdjustmentDto calculAdjustmentDto) throws RRException, IOException {
        MultiExtentionReadPltFile readPltFile = new MultiExtentionReadPltFile();
        List<PLTLossData> pltLossData = readPltFile.read(new File(calculAdjustmentDto.getPathToFile()));
        if (Linear.equals(calculAdjustmentDto.getType()) ){
            pltLossData = CalculAdjustement.linearAdjustement(pltLossData, calculAdjustmentDto.getLmf(), calculAdjustmentDto.isCap());
        }
        else if (EEFFrequency.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.eefFrequency(pltLossData, calculAdjustmentDto.isCap(),calculAdjustmentDto.getRpmf());
        }
        else if (NONLINEAROEP.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.oepReturnPeriodBanding(pltLossData, calculAdjustmentDto.isCap(), calculAdjustmentDto.getAdjustmentReturnPeriodBendings());
        }
        else if (NonLinearEventDriven.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getPeatDatas());
        }
        else if (NONLINEARRETURNPERIOD.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getPeatDatas());
        }
        else if (NONLINEARRETURNEVENTPERIOD.equals(calculAdjustmentDto.getType())) {
            pltLossData = CalculAdjustement.eefReturnPeriodBanding(pltLossData,calculAdjustmentDto.isCap(),calculAdjustmentDto.getAdjustmentReturnPeriodBendings());
        }
        File f = new File(calculAdjustmentDto.getNewFilePath());
        if(!f.exists()) {
            FileUtils.touch(f);
        }
        CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
        csvpltFileWriter.write(pltLossData,f);
        return pltLossData;
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


}
