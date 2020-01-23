package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class InuringFinalAttachePltDto {

    private long inuringFinalAttachedPltId;
    private String inuringFinalAttachedPltCurrency;
    private String inuringFinalAttachedPltName;

    public InuringFinalAttachePltDto(long inuringFinalAttachedPltId, String inuringFinalAttachedPltCurrency, String inuringFinalAttachedPltName) {
        this.inuringFinalAttachedPltId = inuringFinalAttachedPltId;
        this.inuringFinalAttachedPltCurrency = inuringFinalAttachedPltCurrency;
        this.inuringFinalAttachedPltName = inuringFinalAttachedPltName;
    }

    public InuringFinalAttachePltDto() {
    }
}
