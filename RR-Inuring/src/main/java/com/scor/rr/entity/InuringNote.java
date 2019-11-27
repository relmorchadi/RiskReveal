package com.scor.rr.entity;

import com.scor.rr.enums.InuringNodeType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "InuringNote")
public class InuringNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringNoteId", nullable = false)
    private long inuringNoteId;

    @Column(name = "Entity")
    private int entity;

    @Column(name="InuringPackageId", nullable = false)
    private long inuringPackageId;

    @Column(name = "InuringObjectType")
    private InuringNodeType inuringObjectType;

    @Column(name = "InuringObjectId")
    private long inuringObjectId;

    @Column(name = "NoteContent")
    private String noteContent;

    @Column(name = "NoteColor")
    private String noteColor;

    @Column(name = "NoteTitle")
    private String noteTitle;

    public InuringNote() {
    }

    public InuringNote(long inuringPackageId, InuringNodeType inuringObjectType, long inuringObjectId, String noteContent, String noteColor, String noteTitle) {
        this.entity=1;
        this.inuringPackageId = inuringPackageId;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.noteContent = noteContent;
        this.noteColor = noteColor;
        this.noteTitle = noteTitle;
    }

    public InuringNote(long inuringPackageId, String noteContent, String noteColor, String noteTitle) {
        this.entity=1;
        this.inuringPackageId = inuringPackageId;
        this.noteContent = noteContent;
        this.noteColor = noteColor;
        this.noteTitle = noteTitle;
    }
}
