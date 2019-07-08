package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TargetRapEntity.class)
public abstract class TargetRapEntity_ {

	public static volatile SingularAttribute<TargetRapEntity, Integer> petId;
	public static volatile SingularAttribute<TargetRapEntity, Boolean> scorGenerated;
	public static volatile SingularAttribute<TargetRapEntity, String> modellingSystem;
	public static volatile SingularAttribute<TargetRapEntity, String> modellingSystemVersion;
	public static volatile SingularAttribute<TargetRapEntity, Boolean> active;
	public static volatile SingularAttribute<TargetRapEntity, String> sourceRapCode;
	public static volatile SingularAttribute<TargetRapEntity, Integer> targetRapId;
	public static volatile SingularAttribute<TargetRapEntity, String> targetRapDesc;
	public static volatile SingularAttribute<TargetRapEntity, String> modellingVendor;
	public static volatile SingularAttribute<TargetRapEntity, Boolean> scorDefault;
	public static volatile SingularAttribute<TargetRapEntity, String> entitylingSystem;
	public static volatile SingularAttribute<TargetRapEntity, String> entitylingSystemVersion;
	public static volatile SingularAttribute<TargetRapEntity, String> targetRapCode;
	public static volatile SingularAttribute<TargetRapEntity, String> entitylingVendor;
	public static volatile SingularAttribute<TargetRapEntity, Boolean> scorCurrent;

}

