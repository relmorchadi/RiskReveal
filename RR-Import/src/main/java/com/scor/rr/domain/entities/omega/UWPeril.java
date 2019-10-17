package com.scor.rr.domain.entities.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the UWPeril database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "UWPeril")
@Data
public class UWPeril {
    @Id
    @Column(name = "UWPerilId")
    private Long uwPerilId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Label")
    private String label;
}
