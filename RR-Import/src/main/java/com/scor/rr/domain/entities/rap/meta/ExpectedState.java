package com.scor.rr.domain.entities.rap.meta;

/**
 * @author Hamiani Mohammed
 * creation date  17/09/2019 at 11:13
 **/
public enum ExpectedState {
    ENABLED(1), DISABLED(0);

    private int index;

    private ExpectedState(int index) {
        this.index = index;
    }

    public static ExpectedState get(Boolean isExpected) {
        if (isExpected == false) {
            return DISABLED;
        }
        if (isExpected == true) {
            return ENABLED;
        }
        return null;
    }

}
