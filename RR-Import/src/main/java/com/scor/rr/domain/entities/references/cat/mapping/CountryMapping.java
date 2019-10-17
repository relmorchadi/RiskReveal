package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.references.omega.Country;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the CountryMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "CountryMapping")
@Data
public class CountryMapping extends AbstractMapping<Country> {
    @Column(name = "SourceISO2A")
    private String sourceISO2A;
    @Column(name = "SourceISO3A")
    private String sourceISO3A;
    @Column(name = "SourceCountryName")
    private String sourceCountryName;
}
