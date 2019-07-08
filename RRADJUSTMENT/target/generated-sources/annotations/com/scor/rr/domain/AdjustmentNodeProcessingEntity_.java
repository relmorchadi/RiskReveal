package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdjustmentNodeProcessingEntity.class)
public abstract class AdjustmentNodeProcessingEntity_ {

	public static volatile SingularAttribute<AdjustmentNodeProcessingEntity, AdjustmentNodeEntity> adjustmentNodeByAdjustmentNodeId;
	public static volatile SingularAttribute<AdjustmentNodeProcessingEntity, ScorPltHeaderEntity> scorPltHeaderByAdjustedPltId;
	public static volatile SingularAttribute<AdjustmentNodeProcessingEntity, ScorPltHeaderEntity> scorPltHeaderByInputPltId;
	public static volatile SingularAttribute<AdjustmentNodeProcessingEntity, Integer> id;

}

