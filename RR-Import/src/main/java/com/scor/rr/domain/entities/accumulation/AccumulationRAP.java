package com.scor.rr.domain.entities.accumulation;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the AccumulationRAP database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AccumulationRAP")
@Data
public class AccumulationRAP {
    @Id
    @Column(name = "AccumulationRAPId")
    private Long accumulationRAPId;
    @Column(name = "AccumulationRAPDescription")
    private String accumulationRAPDescription;
    @Column(name = "AccumulationRAPCode")
    private String accumulationRAPCode;
    @Column(name = "PetID")
    private Long petID;
    @Column(name = "PetDescription")
    private String petDescription;
    @Column(name = "PetFileName")
    private String petFileName;
}
