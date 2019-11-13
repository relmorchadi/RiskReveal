package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmAnalysisEpCurves {

    private Long id;
    private String finPerspCode;
    private String treatyLabelId;
    private String treatyLabel;
    private int ebpTypeCode;
    private int loss;
    private int exceedanceProbabilty;
    private BigDecimal returnId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFinPerspCode() {
        return finPerspCode;
    }

    public void setFinPerspCode(String finPerspCode) {
        this.finPerspCode = finPerspCode;
    }

    public String getTreatyLabelId() {
        return treatyLabelId;
    }

    public void setTreatyLabelId(String treatyLabelId) {
        this.treatyLabelId = treatyLabelId;
    }

    public String getTreatyLabel() {
        return treatyLabel;
    }

    public void setTreatyLabel(String treatyLabel) {
        this.treatyLabel = treatyLabel;
    }

    public int getEbpTypeCode() {
        return ebpTypeCode;
    }

    public void setEbpTypeCode(int ebpTypeCode) {
        this.ebpTypeCode = ebpTypeCode;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getExceedanceProbabilty() {
        return exceedanceProbabilty;
    }

    public void setExceedanceProbabilty(int exceedanceProbabilty) {
        this.exceedanceProbabilty = exceedanceProbabilty;
    }

    public BigDecimal getReturnId() {
        return returnId;
    }

    public void setReturnId(BigDecimal returnId) {
        this.returnId = returnId;
    }
}
