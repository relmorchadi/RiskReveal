package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "InuringFinalAttachedPLT", schema = "dbo", catalog = "RiskReveal")
public class InuringFinalAttachedPLT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inuringFinalAttachedPLT", nullable = false)
    private int inuringFinalAttachedPLT;

    @Column(name = "Entity")
    private int Entity;

    @Column(name = "inuringFinalNodeId")
    private int inuringFinalNodeId;

    @Column(name = "PLTCcy")
    private String PLTCcy;

    @Column(name = "PLTHeaderId")
    private int PLTHeaderId;
}
