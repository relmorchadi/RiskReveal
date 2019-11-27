package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "InuringFinalAttachedPLT")
public class InuringFinalAttachedPLT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inuringFinalAttachedPLT", nullable = false)
    private long inuringFinalAttachedPLT;

    @Column(name = "RREntity")
    private int Entity;

    @Column(name = "inuringFinalNodeId")
    private long inuringFinalNodeId;

    @Column(name = "PLTCcy")
    private String PLTCcy;

    @Column(name = "PLTHeaderId")
    private long PLTHeaderId;
}
