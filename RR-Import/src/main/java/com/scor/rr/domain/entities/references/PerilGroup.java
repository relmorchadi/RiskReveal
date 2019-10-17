package com.scor.rr.domain.entities.references;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the PerilGroup database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "PerilGroup")
@Data
public class PerilGroup {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String name;
}
