package com.scor.rr.request;

import com.scor.rr.enums.InuringNodeType;

public class InuringNoteCreationRequest {

    private int inuringPackageId;

    private InuringNodeType inuringObjectType;

    private int inuringObjectId;

    private String noteContent;

    private String noteColor;

    private String noteTitle;

    public InuringNoteCreationRequest() {
    }

    public InuringNoteCreationRequest(int inuringPackageId, InuringNodeType inuringObjectType, int inuringObjectId, String noteContent, String noteColor, String noteTitle) {
        this.inuringPackageId = inuringPackageId;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.noteContent = noteContent;
        this.noteColor = noteColor;
        this.noteTitle = noteTitle;
    }

    public int getInuringPackageId() {
        return inuringPackageId;
    }

    public InuringNodeType getInuringObjectType() {
        return inuringObjectType;
    }

    public int getInuringObjectId() {
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
