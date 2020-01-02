package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.dto.EPMetric;
import com.scor.rr.domain.dto.adjustement.AdjustmentManuelleParameterProcess;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.service.adjustement.pltAdjustment.CalculateAdjustmentService;
import com.scor.rr.service.adjustement.pltAdjustment.StatisticAdjustment;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;
import static com.scor.rr.exceptions.ExceptionCodename.TYPE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ScorPltHeaderService {

    @Autowired
    PltHeaderRepository pltHeaderRepository;

    @Autowired
    CalculateAdjustmentService calculateAdjustmentService;

    private static final String PATH = "RRADJUSTMENT\\src\\main\\resources\\file\\";

    public void persistScorPltHeaderFromPath(String path, AdjustmentManuelleParameterProcess parameterProcess) throws RRException {
        try {
            File file = new File(PATH + new Date().getTime()+".csv");
            FileUtils.touch(file);
            BinaryPLTFileWriter fileWriter = new BinaryPLTFileWriter();
            fileWriter.write(calculateAdjustment(parameterProcess,getPltLossDataFromFile(path)),file);
        } catch (IOException | com.scor.rr.exceptions.RRException e) {
            e.printStackTrace();
        }
    }

    public Double CoefOfVariance(String path) throws RRException {
        return StatisticAdjustment.coefOfVariance(getPltLossDataFromFile(path));
    }

    public EPMetric AEPTVaRMetrics(String path) throws RRException {
        return StatisticAdjustment.AEPTVaRMetrics(CalculateAdjustmentService.getAEPMetric(getPltLossDataFromFile(path)).getEpMetricPoints());
    }

    public EPMetric OEPTVaRMetrics(String path) throws RRException {
        return StatisticAdjustment.OEPTVaRMetrics(CalculateAdjustmentService.getOEPMetric(getPltLossDataFromFile(path)).getEpMetricPoints());
    }

    public Double averageAnnualLoss(String path) throws RRException {
        return StatisticAdjustment.averageAnnualLoss(getPltLossDataFromFile(path));
    }

    public Double stdDev(String path) throws RRException {
        return StatisticAdjustment.stdDev(getPltLossDataFromFile(path));
    }

    //NOTE: this service should be open and as a part of API

    private List<PLTLossData> calculateAdjustment(AdjustmentManuelleParameterProcess parameterProcess, List<PLTLossData> pltLossData) throws com.scor.rr.exceptions.RRException {
        if (LINEAR.getValue().equals(parameterProcess.getType())) {
            return calculateAdjustmentService.linearAdjustement(pltLossData, parameterProcess.getLmf(), parameterProcess.isCapped());
        }
        if (EEF_FREQUENCY.getValue().equals(parameterProcess.getType())) {
            return calculateAdjustmentService.eefFrequency(pltLossData, parameterProcess.isCapped(), parameterProcess.getRpmf());
        }
        if (NONLINEAR_OEP_RPB.getValue().equals(parameterProcess.getType())) {
            return calculateAdjustmentService.oepReturnPeriodBanding(pltLossData, parameterProcess.isCapped(), parameterProcess.getAdjustmentReturnPeriodBandings());
        }
        if (NONLINEAR_EVENT_DRIVEN.getValue().equals(parameterProcess.getType())) {
            return calculateAdjustmentService.nonLinearEventDrivenAdjustment(pltLossData, parameterProcess.isCapped(), parameterProcess.getPeatData());
        }
        if (NONLINEAR_EVENT_PERIOD_DRIVEN.getValue().equals(parameterProcess.getType())) {
            return calculateAdjustmentService.nonLinearEventPeriodDrivenAdjustment(pltLossData, parameterProcess.isCapped(), parameterProcess.getPeatData());
        }
        if (NONLINEAR_EEF_RPB.getValue().equals(parameterProcess.getType())) {
            return calculateAdjustmentService.eefReturnPeriodBanding(pltLossData, parameterProcess.isCapped(), parameterProcess.getAdjustmentReturnPeriodBandings());
        }
        throwException(TYPE_NOT_FOUND, NOT_FOUND);
        return null;
    }

    private List<PLTLossData> getPltLossDataFromFile(String path) throws RRException {
        return new MultiExtentionReadPltFile().read(new File(path));
    }

    public PltHeaderEntity findOne(Long scorPltHeader) {
        return pltHeaderRepository.findByPltHeaderId(scorPltHeader);
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new com.scor.rr.exceptions.RRException(codeName, httpStatus.value());
    }
}
