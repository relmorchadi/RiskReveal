package com.scor.rr.importBatch.processing.ylt.meta;

// ORIGINAL for data from rms, target for internally generated data
public enum XLTOT {
    ORIGINAL("O"), TARGET("T");

    final String value;

    XLTOT(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
