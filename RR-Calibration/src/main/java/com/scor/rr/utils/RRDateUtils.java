package com.scor.rr.utils;

import java.sql.Date;

public class RRDateUtils {
    public static Date getDateNow() {
        return new java.sql.Date(new java.util.Date().getTime());
    }
}
