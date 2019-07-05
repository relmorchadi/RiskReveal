package com.scor.rr.exceptions.fileExceptionPlt;

/**
 * Created by u004602 on 28/06/2019.
 */
public class PLTFileWriteException extends RRException {
    public PLTFileWriteException(String message) {
        super("Error while writing to file: " + message);
    }
}
