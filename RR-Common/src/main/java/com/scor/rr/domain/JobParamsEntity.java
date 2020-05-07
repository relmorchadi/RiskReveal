package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "JobParams")
@AllArgsConstructor
@NoArgsConstructor
public class JobParamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JobParamsId")
    private Long jobParamsId;
    @Column(name = "Entity")
    private Long entity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JobId")
    private JobEntity jobId;
    @Column(name = "ParameterName")
    private String parameterName;
    @Column(name = "ParameterValue")
    private String parameterValue;
}
