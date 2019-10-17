package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "InuringNote", schema = "dbo", catalog = "RiskReveal")
public class InuringNote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "InuringNoteId", nullable = false)
    private int inuringNoteId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringObjectType", nullable = false)
    private int inuringObjectType;

    @Column(name = "InuringObjectId", nullable = false)
    private int inuringObjectId;

    @Column(name = "NoteContent")
    private String noteContent;

    @Column(name = "NoteColor")
    private String noteColor;

    @Column(name = "NoteTitle")
    private String noteTitle;
}
