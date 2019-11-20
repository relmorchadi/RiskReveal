package com.scor.rr.exceptions;

import java.io.Serializable;

public class ExceptionPayload implements Serializable {
    private ExceptionCodename code;
    private Integer status;

    ExceptionPayload(final ExceptionCodename codename, final Integer status) {
        this.code = codename;
        this.status = status;
    }

    public ExceptionCodename getCode() {
        return code;
    }

    public void setCode(ExceptionCodename code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
