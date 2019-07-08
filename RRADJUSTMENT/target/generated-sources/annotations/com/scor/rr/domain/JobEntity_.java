package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(JobEntity.class)
public abstract class JobEntity_ {

	public static volatile SingularAttribute<JobEntity, Integer> jobId;
	public static volatile SingularAttribute<JobEntity, String> jobParams;
	public static volatile SingularAttribute<JobEntity, Integer> submittedByUser;
	public static volatile SingularAttribute<JobEntity, Timestamp> startedDate;
	public static volatile SingularAttribute<JobEntity, Timestamp> submittedDate;
	public static volatile SingularAttribute<JobEntity, String> jobType;
	public static volatile SingularAttribute<JobEntity, String> status;
	public static volatile SingularAttribute<JobEntity, Timestamp> finishedDate;

}

