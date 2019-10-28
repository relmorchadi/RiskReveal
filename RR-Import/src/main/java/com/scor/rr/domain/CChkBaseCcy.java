package com.scor.rr.domain;

import lombok.Data;

@Data
public class CChkBaseCcy {
    private int result;
    private String errorMessage;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
