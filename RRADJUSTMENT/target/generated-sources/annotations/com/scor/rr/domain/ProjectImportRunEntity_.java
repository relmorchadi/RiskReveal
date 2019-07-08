package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectImportRunEntity.class)
public abstract class ProjectImportRunEntity_ {

	public static volatile SingularAttribute<ProjectImportRunEntity, String> sourceConfigVendor;
	public static volatile SingularAttribute<ProjectImportRunEntity, Timestamp> endDate;
	public static volatile SingularAttribute<ProjectImportRunEntity, Integer> projectImportRunId;
	public static volatile SingularAttribute<ProjectImportRunEntity, Timestamp> lossImportEndDate;
	public static volatile SingularAttribute<ProjectImportRunEntity, Integer> runId;
	public static volatile SingularAttribute<ProjectImportRunEntity, Integer> projectId;
	public static volatile SingularAttribute<ProjectImportRunEntity, Timestamp> startDate;
	public static volatile SingularAttribute<ProjectImportRunEntity, String> status;
	public static volatile SingularAttribute<ProjectImportRunEntity, String> importedBy;

}

