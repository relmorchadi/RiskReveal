package com.scor.rr.exceptions;

public class RRException extends Exception {
    private final ExceptionPayload payload;

    public RRException(final ExceptionPayload payload) {
        this.payload = payload;
    }

    public RRException(final ExceptionCodename codename, final Integer status) {
        this.payload = new ExceptionPayload(
                codename,
                status
        );
    }

    ExceptionPayload getPayload() {
        return payload;
    }
}
