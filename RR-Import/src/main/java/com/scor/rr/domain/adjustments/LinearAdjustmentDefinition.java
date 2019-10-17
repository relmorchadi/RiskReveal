package com.scor.rr.domain.adjustments;

/**
 * Created by U002629 on 03/03/2016.
 */
public class LinearAdjustmentDefinition extends AdjustmentDefinition {
    public static final String type = "LINEAR";
    double amount;
    public LinearAdjustmentDefinition(double amount) {
        super(type);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String label(){return new StringBuilder(type).append("_").append(amount).toString();}

    @Override
    public String toString() {
        return new StringBuilder(type).append("_").append(amount).toString();
    }
}
