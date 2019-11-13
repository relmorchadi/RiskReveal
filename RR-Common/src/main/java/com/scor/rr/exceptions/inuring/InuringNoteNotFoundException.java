package com.scor.rr.exceptions.inuring;

import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;

public class InuringNoteNotFoundException extends RRException {
    public InuringNoteNotFoundException(int inuringNoteId) {
        super(ExceptionCodename.INURING_NOTE_NOT_FOUND, "Inuring Note with id: " + inuringNoteId+ " not found");
    }
}
