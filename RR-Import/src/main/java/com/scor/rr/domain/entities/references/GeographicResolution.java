package com.scor.rr.domain.entities.references;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the GeographicResolution database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "GeographicResolution")
@Data
public class GeographicResolution {
    @Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String description;
}
