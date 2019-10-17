package com.scor.rr.domain.entities.references.plt;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the SubPeril database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SubPeril")
@Data
public class SubPeril {

    @Id
    @Column(name = "Id")
    private String Id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String name;
}
