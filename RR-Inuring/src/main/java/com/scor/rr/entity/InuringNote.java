package com.scor.rr.entity;

import com.scor.rr.enums.InuringNodeType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "InuringNote", schema = "dbo", catalog = "RiskReveal")
public class InuringNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringNoteId", nullable = false)
    private int inuringNoteId;

    @Column(name = "Entity")
    private int entity;

    @Column(name="InuringPackageId", nullable = false)
    private int inuringPackageId;

    @Column(name = "InuringObjectType")
    private InuringNodeType inuringObjectType;

    @Column(name = "InuringObjectId")
    private int inuringObjectId;

    @Column(name = "NoteContent")
    private String noteContent;

    @Column(name = "NoteColor")
    private String noteColor;

    @Column(name = "NoteTitle")
    private String noteTitle;

    public InuringNote() {
    }

    public InuringNote(int inuringPackageId, InuringNodeType inuringObjectType, int inuringObjectId, String noteContent, String noteColor, String noteTitle) {
        this.entity=1;
        this.inuringPackageId = inuringPackageId;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.noteContent = noteContent;
        this.noteColor = noteColor;
        this.noteTitle = noteTitle;
    }

    public InuringNote(int inuringPackageId, String noteContent, String noteColor, String noteTitle) {
        this.entity=1;
        this.inuringPackageId = inuringPackageId;
        this.noteContent = noteContent;
        this.noteColor = noteColor;
        this.noteTitle = noteTitle;
    }
}
