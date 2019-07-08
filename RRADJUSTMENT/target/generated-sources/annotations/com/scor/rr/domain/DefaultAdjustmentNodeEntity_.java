package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DefaultAdjustmentNodeEntity.class)
public abstract class DefaultAdjustmentNodeEntity_ {

	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, Integer> sequence;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, AdjustmentStateEntity> adjustmentStateByIdState;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, Integer> adjustmentNodeId;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, AdjustmentCategoryEntity> adjustmentCategoryByIdCategory;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, AdjustmentTypeEntity> adjustmentTypeByAdjustmentType;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, String> adjustmentParamsSource;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, String> lossNetFlag;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, Boolean> hasNewParamsFile;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, DefaultAdjustmentThreadEntity> defaultAdjustmentThreadByIdAdjustmentThread;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, Boolean> inputChanged;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, AdjustmentBasisEntity> adjustmentBasisByFkAdjustmentBasis;
	public static volatile SingularAttribute<DefaultAdjustmentNodeEntity, String> layer;

}

