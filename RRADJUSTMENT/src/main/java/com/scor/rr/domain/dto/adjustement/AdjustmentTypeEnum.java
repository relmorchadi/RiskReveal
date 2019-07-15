package com.scor.rr.domain.dto.adjustement;

public enum  AdjustmentTypeEnum {
    Linear("Linear"),
    NONLINEAIRERETURNPERIOD("Non-Linear EEF Return Period Banding"),
    NONLINEARRETURNEVENTPERIOD("Non-Linear Event & Period Driven"),
    NONLINEAROEP("Non-Linear OEP Return Period Banding"),
    EEFFrequency("EEF Frequency"),
    NonLinearEventDriven("Non-Linear Event Driven");

    String value;

    AdjustmentTypeEnum(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
