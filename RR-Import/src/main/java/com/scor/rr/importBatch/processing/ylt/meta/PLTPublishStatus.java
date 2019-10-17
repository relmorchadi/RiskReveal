package com.scor.rr.importBatch.processing.ylt.meta;

/**
 * Created by u004119 on 05/08/2016.
 */
public enum PLTPublishStatus {
    PURE("P"), POST_GROUP("G"), POST_INURED("I");

    final String value;

    PLTPublishStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
