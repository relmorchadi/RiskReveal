package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdjustmentParameterEntity.class)
public abstract class AdjustmentParameterEntity_ {

	public static volatile SingularAttribute<AdjustmentParameterEntity, String> paramField;
	public static volatile SingularAttribute<AdjustmentParameterEntity, String> paramType;
	public static volatile SingularAttribute<AdjustmentParameterEntity, AdjustmentNodeEntity> adjustmentNodeByAdjustmentNodeId;
	public static volatile SingularAttribute<AdjustmentParameterEntity, Integer> adjustmentNodeId;
	public static volatile SingularAttribute<AdjustmentParameterEntity, String> paramValue;

}

