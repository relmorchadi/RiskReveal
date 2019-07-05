package com.scor.rr.exceptions.fileExceptionPlt;

/**
 * Created by u004602 on 27/06/2019.
 */
public class NumberFormatException extends RRException {
    public NumberFormatException(String message) {
        super("Number Format wrong: " + message);
    }
}
