package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "AdjustmentThread", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdjustmentThreadId")
    private Integer adjustmentThreadId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ThreadIndex")
    private Integer threadIndex;

    @Column(name = "InitialPlt")
    private Integer initialPlt;

    @Column(name = "FinalPltId")
    private Integer finalPltId;

    @Column(name = "Locked")
    private Boolean locked;

    @Column(name = "ThreadStatus", length = 25)
    private String threadStatus;
}
