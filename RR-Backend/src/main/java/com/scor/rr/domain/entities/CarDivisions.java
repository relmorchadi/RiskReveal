package com.scor.rr.domain.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_CarDivisions")
@Data
public class CarDivisions {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "CARequestId", length = 30)
    private String carRequestId;

    @Column(name = "DivisionNumber", length = 15)
    private String divisionNumber;

}
