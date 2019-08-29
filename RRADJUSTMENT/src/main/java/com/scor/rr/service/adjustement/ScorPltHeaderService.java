package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.BinaryPLTFileWriter;
import com.scor.rr.configuration.file.MultiExtentionReadPltFile;
import com.scor.rr.domain.dto.adjustement.AdjustmentManuelleParameterProcess;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.fileExceptionPlt.RRException;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import com.scor.rr.service.adjustement.pltAdjustment.StatisticAdjustment;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;
import static com.scor.rr.exceptions.ExceptionCodename.TYPENOTFOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ScorPltHeaderService {

    private static final String PATH = "RRADJUSTMENT\\src\\main\\resources\\file\\";

    public void persistScorPltHeaderFromPath(String path, AdjustmentManuelleParameterProcess parameterProcess) throws RRException {
        try {
            File file = new File(PATH+new Date().getTime()+".csv");
            FileUtils.touch(file);
            BinaryPLTFileWriter fileWriter = new BinaryPLTFileWriter();
            fileWriter.write(calculateAdjustment(parameterProcess,getPltLossDataFromFile(path)),file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Double CoefOfVariance(String path) throws RRException {
        return StatisticAdjustment.CoefOfVariance(getPltLossDataFromFile(path));
    }

    public Double AEPTVaRMetrics(String path) throws RRException {
        return StatisticAdjustment.AEPTVaRMetrics(CalculAdjustement.getAEPMetric(getPltLossDataFromFile(path)));
    }

    public Double OEPTVaRMetrics(String path) throws RRException {
        return StatisticAdjustment.OEPTVaRMetrics(CalculAdjustement.getOEPMetric(getPltLossDataFromFile(path)));
    }

    public Double averageAnnualLoss(String path) throws RRException {
        return StatisticAdjustment.averageAnnualLoss(getPltLossDataFromFile(path));
    }

    public Double stdDev(String path) throws RRException {
        return StatisticAdjustment.stdDev(getPltLossDataFromFile(path));
    }

    //NOTE: this service should be open and as a part of API

    private List<PLTLossData> calculateAdjustment(AdjustmentManuelleParameterProcess parameterProcess, List<PLTLossData> pltLossData) {
        if (Linear.getValue().equals(parameterProcess.getType())) {
            return CalculAdjustement.linearAdjustement(pltLossData, parameterProcess.getLmf(), parameterProcess.isCapped());
        }
        if (EEFFrequency.getValue().equals(parameterProcess.getType())) {
            return CalculAdjustement.eefFrequency(pltLossData, parameterProcess.isCapped(), parameterProcess.getRpmf());
        }
        if (NONLINEAROEP.getValue().equals(parameterProcess.getType())) {
            return CalculAdjustement.oepReturnPeriodBanding(pltLossData, parameterProcess.isCapped(), parameterProcess.getAdjustmentReturnPeriodBendings());
        }
        if (NonLinearEventDriven.getValue().equals(parameterProcess.getType())) {
            return CalculAdjustement.nonLinearEventDrivenAdjustment(pltLossData, parameterProcess.isCapped(), parameterProcess.getPeatData());
        }
        if (NONLINEARRETURNPERIOD.getValue().equals(parameterProcess.getType())) {
            return CalculAdjustement.nonLinearEventPeriodDrivenAdjustment(pltLossData, parameterProcess.isCapped(), parameterProcess.getPeatData());
        }
        if (NONLINEARRETURNEVENTPERIOD.getValue().equals(parameterProcess.getType())) {
            return CalculAdjustement.eefReturnPeriodBanding(pltLossData, parameterProcess.isCapped(), parameterProcess.getAdjustmentReturnPeriodBendings());
        }
        throwException(TYPENOTFOUND, NOT_FOUND);
        return null;
    }

    private List<PLTLossData> getPltLossDataFromFile(String path) throws RRException {
        return new MultiExtentionReadPltFile().read(new File(path));
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new com.scor.rr.exceptions.RRException(codeName, httpStatus.value());
    }
}
