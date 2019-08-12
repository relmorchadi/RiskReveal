package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdjustmentThreadEntity.class)
public abstract class AdjustmentThreadEntity_ {

	public static volatile SingularAttribute<AdjustmentThreadEntity, Timestamp> lastGeneratedOn;
	public static volatile SingularAttribute<AdjustmentThreadEntity, ScorPltHeaderEntity> scorPltHeaderByIdThreadPlt;
	public static volatile SingularAttribute<AdjustmentThreadEntity, Timestamp> generatedOn;
	public static volatile SingularAttribute<AdjustmentThreadEntity, String> createdBy;
	public static volatile SingularAttribute<AdjustmentThreadEntity, Integer> idAdjustmentThread;
	public static volatile SingularAttribute<AdjustmentThreadEntity, String> lastModifiedBy;
	public static volatile SingularAttribute<AdjustmentThreadEntity, ScorPltHeaderEntity> scorPltHeaderByIdPurePlt;
	public static volatile SingularAttribute<AdjustmentThreadEntity, String> threadType;
	public static volatile SingularAttribute<AdjustmentThreadEntity, Boolean> locked;
	public static volatile SingularAttribute<AdjustmentThreadEntity, Timestamp> createdOn;
	public static volatile SingularAttribute<AdjustmentThreadEntity, Timestamp> lastModifiedOn;

}

