package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "InuringFinalAttachedPLT")
public class    InuringFinalAttachedPLT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inuringFinalAttachedPLT", nullable = false)
    private long inuringFinalAttachedPLT;

    @Column(name = "Entity")
    private int Entity;

    @Column(name = "InuringFinalNodeId")
    private long inuringFinalNodeId;

    @Column(name = "PLTCcy")
    private String PLTCcy;

    @Column(name = "PLTHeaderId")
    private long PLTHeaderId;

    @Column(name = "IsGrouped")
    private boolean isGrouped;

    @Column(name = "OriginalPLTId")
    private long originalPLTId;

    @Column(name = "TargetRapId")
    private long targetRapId;

    @Column(name = "MinimumGrainRegionPerilCode")
    private String minimumGrainRegionPerilCode;

    @Column(name = "Peril")
    private String peril;

    @Column(name = "PLTName")
    private String pltName;

    @Column(name = "OccurrenceBasisCode")
    private String occurrenceBasisCode;
}
