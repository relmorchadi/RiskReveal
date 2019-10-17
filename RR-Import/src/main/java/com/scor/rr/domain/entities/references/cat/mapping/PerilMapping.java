package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.references.Peril;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the PerilMapping database table
 * 
 * @author HADDINI Zakariyae
 *
 */
@Entity
@Table(name = "PerilMapping")
public class PerilMapping extends SimpleMapping<Peril> {

}
