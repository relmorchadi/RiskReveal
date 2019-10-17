package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.references.GeographicResolution;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the GeographicResolutionMapping database table
 * 
 * @author HADDINI Zakariyae
 *
 */
@Entity
@Table(name = "GeographicResolutionMapping")
public class GeographicResolutionMapping extends SimpleMapping<GeographicResolution> {

}
