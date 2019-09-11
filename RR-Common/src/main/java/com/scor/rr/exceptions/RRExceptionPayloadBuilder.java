package com.scor.rr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RRExceptionPayloadBuilder {
    private ExceptionCodename code;
    private Integer status;

    public RRExceptionPayloadBuilder withCode(final ExceptionCodename code) {
        this.code = code;
        return this;
    }

    public RRExceptionPayloadBuilder withCode(final String code) {
        this.code = ExceptionCodename.valueOf(code);
        return this;
    }

    public RRExceptionPayloadBuilder withStatus(final Integer status) {
        this.status = status;
        return this;
    }

    public RRExceptionPayloadBuilder withStatus(final HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public ExceptionPayload build() {
        return new ExceptionPayload(
                code,
                status
        );
    }
}
