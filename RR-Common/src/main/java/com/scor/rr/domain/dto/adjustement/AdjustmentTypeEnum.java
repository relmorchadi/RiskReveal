package com.scor.rr.domain.dto.adjustement;

public enum  AdjustmentTypeEnum {
    LINEAR("Linear"),
    NONLINEAR_EEF_RPB("Non-Linear EEF Return Period Banding"),
    NONLINEAR_EVENT_PERIOD_DRIVEN("Non-Linear Event & Period Driven"),
    NONLINEAR_OEP_RPB("Non-Linear OEP Return Period Banding"),
    EEF_FREQUENCY("EEF Frequency"),
    NONLINEAR_EVENT_DRIVEN("Non-Linear Event Driven");

    String value;

    AdjustmentTypeEnum(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
