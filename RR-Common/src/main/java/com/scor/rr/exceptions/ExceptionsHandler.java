package com.scor.rr.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class ExceptionsHandler {
    private final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity handleUnknownException(final RuntimeException e, final WebRequest req) {
        logger.error(e.getMessage(), e);
        return respondWithExceptionPayload(
                new RRExceptionPayloadBuilder()
                        .withCode(ExceptionCodename.UNKNOWN)
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build()
        );
    }

    @ExceptionHandler({
            RRException.class
    })
    public ResponseEntity handleSmileException(final RRException e) {
        e.printStackTrace();
        logger.info("Exception thrown {}");
        return respondWithExceptionPayload(e.getPayload());
    }

    private ResponseEntity respondWithExceptionPayload(final ExceptionPayload payload) {
        return new ResponseEntity<>(
                payload,
                HttpStatus.valueOf(payload.getStatus())
        );
    }
}
