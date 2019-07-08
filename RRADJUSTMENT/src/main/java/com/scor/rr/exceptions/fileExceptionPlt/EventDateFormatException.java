package com.scor.rr.exceptions.fileExceptionPlt;

/**
 * Created by u004602 on 27/06/2019.
 */
public class EventDateFormatException extends RRException {
    public EventDateFormatException() {
        super("Event Date format wrong. Correct format: dd/MM/yyyy HH:mm:ss");
    }
}
