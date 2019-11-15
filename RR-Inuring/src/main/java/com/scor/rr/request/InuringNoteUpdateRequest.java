package com.scor.rr.request;

public class InuringNoteUpdateRequest {

    private long inuringNoteId;
    private String inuringNoteDescription;
    private String inuringNoteColor;
    private String title;

    public InuringNoteUpdateRequest(long inuringNoteId, String inuringNoteDescription, String inuringNoteColor,String title) {
        this.inuringNoteId = inuringNoteId;
        this.inuringNoteDescription = inuringNoteDescription;
        this.inuringNoteColor = inuringNoteColor;
        this.title = title;
    }

    public long getInuringNoteId() {
        return inuringNoteId;
    }

    public String getInuringNoteDescription() {
        return inuringNoteDescription;
    }

    public String getInuringNoteColor() {
        return inuringNoteColor;
    }

    public String getTitle() {
        return title;
    }

    public InuringNoteUpdateRequest() {
    }
}
