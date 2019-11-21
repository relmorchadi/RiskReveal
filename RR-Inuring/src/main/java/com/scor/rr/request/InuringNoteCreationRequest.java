package com.scor.rr.request;

import com.scor.rr.enums.InuringNodeType;

public class InuringNoteCreationRequest {

    private long inuringPackageId;

    private InuringNodeType inuringObjectType;

    private long inuringObjectId;

    private String noteContent;

    private String noteColor;

    private String noteTitle;

    public InuringNoteCreationRequest() {
    }

    public InuringNoteCreationRequest(long inuringPackageId, InuringNodeType inuringObjectType, long inuringObjectId, String noteContent, String noteColor, String noteTitle) {
        this.inuringPackageId = inuringPackageId;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.noteContent = noteContent;
        this.noteColor = noteColor;
        this.noteTitle = noteTitle;
    }

    public long getInuringPackageId() {
        return inuringPackageId;
    }

    public InuringNodeType getInuringObjectType() {
        return inuringObjectType;
    }

    public long getInuringObjectId() {
        return inuringObjectId;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getNoteColor() {
        return noteColor;
    }

    public String getNoteTitle() {
        return noteTitle;
    }
}
