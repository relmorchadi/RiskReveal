package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TaskParams")
@AllArgsConstructor
@NoArgsConstructor
public class TaskParamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskParamsId")
    private Long taskParamsId;
    @Column(name = "Entity")
    private Long entity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TaskId")
    private TaskEntity taskId;
    @Column(name = "ParameterName")
    private String parameterName;
    @Column(name = "ParameterValue")
    private String parameterValue;
}
