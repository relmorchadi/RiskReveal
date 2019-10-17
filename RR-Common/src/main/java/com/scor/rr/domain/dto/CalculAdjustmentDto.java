package com.scor.rr.domain.dto;

import com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;

import java.util.List;

public class CalculAdjustmentDto {
    
    String pathToFile;
    AdjustmentTypeEnum type;
    double lmf;
    double rpmf;
    boolean cap;
    List<PEATData> peatDatas;
    List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings;
    String newFilePath;

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public AdjustmentTypeEnum getType() {
        return type;
    }

    public void setType(AdjustmentTypeEnum type) {
        this.type = type;
    }

    public double getLmf() {
        return lmf;
    }

    public void setLmf(double lmf) {
        this.lmf = lmf;
    }

    public double getRpmf() {
        return rpmf;
    }

    public void setRpmf(double rpmf) {
        this.rpmf = rpmf;
    }

    public boolean isCap() {
        return cap;
    }

    public void setCap(boolean cap) {
        this.cap = cap;
    }

    public List<PEATData> getPeatDatas() {
        return peatDatas;
    }

    public void setPeatDatas(List<PEATData> peatDatas) {
        this.peatDatas = peatDatas;
    }

    public List<AdjustmentReturnPeriodBending> getAdjustmentReturnPeriodBendings() {
        return adjustmentReturnPeriodBendings;
    }

    public void setAdjustmentReturnPeriodBendings(List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings) {
        this.adjustmentReturnPeriodBendings = adjustmentReturnPeriodBendings;
    }

    public String getNewFilePath() {
        return newFilePath;
    }

    public void setNewFilePath(String newFilePath) {
        this.newFilePath = newFilePath;
    }
}
