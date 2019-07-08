package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdjustmentThreadEntity.class)
public abstract class AdjustmentThreadEntity_ {

	public static volatile SingularAttribute<AdjustmentThreadEntity, ScorPltHeaderEntity> scorPltHeaderByPurePltId;
	public static volatile SingularAttribute<AdjustmentThreadEntity, ScorPltHeaderEntity> scorPltHeaderByThreadPltId;
	public static volatile SingularAttribute<AdjustmentThreadEntity, String> threadType;
	public static volatile SingularAttribute<AdjustmentThreadEntity, Integer> adjustmentThreadId;
	public static volatile SingularAttribute<AdjustmentThreadEntity, Boolean> locked;

}

