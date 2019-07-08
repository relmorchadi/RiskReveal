package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AdjustmentProcessingAuditEntity.class)
public abstract class AdjustmentProcessingAuditEntity_ {

	public static volatile SingularAttribute<AdjustmentProcessingAuditEntity, AdjustmentNodeEntity> adjustmentNodeByAdjustmentNodeId;
	public static volatile SingularAttribute<AdjustmentProcessingAuditEntity, Timestamp> startedTime;
	public static volatile SingularAttribute<AdjustmentProcessingAuditEntity, Integer> adjustedPltId;
	public static volatile SingularAttribute<AdjustmentProcessingAuditEntity, String> adjustmentRecap;
	public static volatile SingularAttribute<AdjustmentProcessingAuditEntity, Timestamp> endedTime;
	public static volatile SingularAttribute<AdjustmentProcessingAuditEntity, Integer> inputPltId;
	public static volatile SingularAttribute<AdjustmentProcessingAuditEntity, Integer> id;

}

