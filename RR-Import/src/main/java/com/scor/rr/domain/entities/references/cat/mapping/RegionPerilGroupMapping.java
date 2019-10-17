package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.references.RegionPerilGroup;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the RegionPerilGroupMapping database table
 * 
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 *
 */
@Entity
@Table(name = "RegionPerilGroupMapping")
public class RegionPerilGroupMapping extends AbstractRegionPerilMapping<RegionPerilGroup> {

}
