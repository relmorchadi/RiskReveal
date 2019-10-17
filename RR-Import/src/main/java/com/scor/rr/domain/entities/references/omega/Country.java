package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the Country database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Country")
@Data
public class Country {
    @Id
    @Column(name = "CountryId")
    private String countryId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String name;
    @Column(name = "ShortName")
    private String shortName;
}
