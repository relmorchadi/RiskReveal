package com.scor.rr.configuration.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by u004602 on 27/06/2019.
 */
public class Constant {
    private static final SimpleDateFormat EVENT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static SimpleDateFormat getEventDateFormat() {
        EVENT_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        return EVENT_DATE_FORMAT;
    }
}
