package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DefaultAdjustmentNodeEntity.class)
public abstract class DefaultAdjustmentNodeEntity_ {

	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, Integer> sequence;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, Integer> idDefaultAdjustmentNode;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, DefaultAdjustmentThreadEntity> defaultAdjustmentThread;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, AdjustmentBasisEntity> adjustmentBasis;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, AdjustmentTypeEntity> adjustmentType;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, Boolean> cappedMaxExposure;

}

