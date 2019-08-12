package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdjustmentNodeEntity.class)
public abstract class AdjustmentNodeEntity_ {

	public static volatile SingularAttribute<AdjustmentNodeEntity, AdjustmentNodeEntity> adjustmentNodeById;
	public static volatile SingularAttribute<AdjustmentNodeEntity, Integer> sequence;
	public static volatile SingularAttribute<AdjustmentNodeEntity, Integer> idAdjustmentNode;
	public static volatile SingularAttribute<AdjustmentNodeEntity, AdjustmentBasisEntity> adjustmentBasis;
	public static volatile SingularAttribute<AdjustmentNodeEntity, Boolean> capped;
	public static volatile SingularAttribute<AdjustmentNodeEntity, AdjustmentTypeEntity> adjustmentType;
	public static volatile SingularAttribute<AdjustmentNodeEntity, AdjustmentThreadEntity> adjustmentThread;
	public static volatile SingularAttribute<AdjustmentNodeEntity, AdjustmentStateEntity> adjustmentState;

}

