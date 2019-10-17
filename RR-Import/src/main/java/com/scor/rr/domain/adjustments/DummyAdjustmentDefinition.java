package com.scor.rr.domain.adjustments;

/**
 * Created by U002629 on 07/03/2016.
 */
public class DummyAdjustmentDefinition extends AdjustmentDefinition {
    public static final String type = "DUMMY";
    public DummyAdjustmentDefinition() {
        super(type);
    }

    public String label(){return new StringBuilder(type).toString();}

    @Override
    public String toString() {
        return new StringBuilder(type).toString();
    }
}
