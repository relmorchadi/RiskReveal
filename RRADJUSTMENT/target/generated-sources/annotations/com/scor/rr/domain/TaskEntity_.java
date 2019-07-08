package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskEntity.class)
public abstract class TaskEntity_ {

	public static volatile SingularAttribute<TaskEntity, Integer> jobId;
	public static volatile SingularAttribute<TaskEntity, String> taskType;
	public static volatile SingularAttribute<TaskEntity, Timestamp> startedDate;
	public static volatile SingularAttribute<TaskEntity, String> taskParams;
	public static volatile SingularAttribute<TaskEntity, Integer> taskId;
	public static volatile SingularAttribute<TaskEntity, String> status;
	public static volatile SingularAttribute<TaskEntity, Timestamp> finishedDate;

}

