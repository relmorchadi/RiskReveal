package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.references.EPMetric;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the EPMetricMapping database table
 * 
 * @author HADDINI Zakariyae
 *
 */
@Entity
@Table(name = "EPMetricMapping")
public class EPMetricMapping extends SimpleMapping<EPMetric> {

}
