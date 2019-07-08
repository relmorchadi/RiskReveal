package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RegionPerilEntity.class)
public abstract class RegionPerilEntity_ {

	public static volatile SingularAttribute<RegionPerilEntity, String> perilCode;
	public static volatile SingularAttribute<RegionPerilEntity, Integer> regionPerilId;
	public static volatile SingularAttribute<RegionPerilEntity, String> hierachyParentCode;
	public static volatile SingularAttribute<RegionPerilEntity, Boolean> active;
	public static volatile SingularAttribute<RegionPerilEntity, Integer> hierachyLevel;
	public static volatile SingularAttribute<RegionPerilEntity, String> parentMinimumGrainRegionPeril;
	public static volatile SingularAttribute<RegionPerilEntity, Boolean> entityled;
	public static volatile SingularAttribute<RegionPerilEntity, String> regionDesc;
	public static volatile SingularAttribute<RegionPerilEntity, String> regionPerilCode;
	public static volatile SingularAttribute<RegionPerilEntity, Boolean> modelled;
	public static volatile SingularAttribute<RegionPerilEntity, Integer> regionHierachy;
	public static volatile SingularAttribute<RegionPerilEntity, String> regionPerilGroupCode;
	public static volatile SingularAttribute<RegionPerilEntity, String> regionPerilDesc;
	public static volatile SingularAttribute<RegionPerilEntity, Boolean> minimumGrainRegionPeril;
	public static volatile SingularAttribute<RegionPerilEntity, String> regionPerilGroupDesc;

}

