package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DefaultAdjustmentEntity.class)
public abstract class DefaultAdjustmentEntity_ {

	public static volatile SingularAttribute<DefaultAdjustmentEntity, Integer> sequence;
	public static volatile SingularAttribute<DefaultAdjustmentEntity, TargetRapEntity> targetRapByTargetRapId;
	public static volatile SingularAttribute<DefaultAdjustmentEntity, RegionPerilEntity> regionPerilByRegionPerilId;
	public static volatile SingularAttribute<DefaultAdjustmentEntity, String> marketChannel;
	public static volatile SingularAttribute<DefaultAdjustmentEntity, String> engineType;
	public static volatile SingularAttribute<DefaultAdjustmentEntity, Integer> id;
	public static volatile SingularAttribute<DefaultAdjustmentEntity, String> entity;

}

