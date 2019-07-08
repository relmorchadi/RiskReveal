package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StepEntity.class)
public abstract class StepEntity_ {

	public static volatile SingularAttribute<StepEntity, String> stepParams;
	public static volatile SingularAttribute<StepEntity, String> stepName;
	public static volatile SingularAttribute<StepEntity, Integer> stepId;
	public static volatile SingularAttribute<StepEntity, Integer> stepOrder;
	public static volatile SingularAttribute<StepEntity, Timestamp> startedDate;
	public static volatile SingularAttribute<StepEntity, Integer> taskId;
	public static volatile SingularAttribute<StepEntity, String> status;
	public static volatile SingularAttribute<StepEntity, Timestamp> finishedDate;

}

