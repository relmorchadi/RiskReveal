package com.scor.rr.exceptions.pltfile;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

/**
 * Created by u004602 on 27/06/2019.
 */
public class EventDateFormatException extends RRException {
    public EventDateFormatException() {
        super(ExceptionCodename.EVENT_DATE_FORMAT_WRONG, 1, "Event Date format wrong. Correct format: dd/MM/yyyy HH:mm:ss");
    }
}
