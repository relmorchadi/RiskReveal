package com.scor.rr.domain.entities.references.cat;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the Insured database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Insured")
@Data
public class Insured {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String name;
    @Column(name = "SecondName")
    private String secondName;

}
