package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "AdjustmentThread")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdjustmentThreadId")
    private Long adjustmentThreadId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ThreadIndex")
    private Integer threadIndex;

    @Column(name = "InitialPlt")
    private Long initialPlt;

    @Column(name = "FinalPltId")
    private Long finalPltId;

    @Column(name = "Locked")
    private Boolean locked;

    @Column(name = "ThreadStatus", length = 25)
    private String threadStatus;
}
