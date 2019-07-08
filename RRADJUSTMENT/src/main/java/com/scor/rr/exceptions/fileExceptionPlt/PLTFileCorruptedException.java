package com.scor.rr.exceptions.fileExceptionPlt;

/**
 * Created by u004602 on 25/06/2019.
 */
public class PLTFileCorruptedException extends RRException {
    public PLTFileCorruptedException() {
        super("PLT file was corrupted or truncated");
    }
}
