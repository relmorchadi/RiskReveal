package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectEntity.class)
public abstract class ProjectEntity_ {

	public static volatile SingularAttribute<ProjectEntity, Boolean> masterFlag;
	public static volatile SingularAttribute<ProjectEntity, Boolean> linkFlag;
	public static volatile SingularAttribute<ProjectEntity, Boolean> clonedFlag;
	public static volatile SingularAttribute<ProjectEntity, Timestamp> receptionDate;
	public static volatile SingularAttribute<ProjectEntity, Boolean> postInuredFlag;
	public static volatile SingularAttribute<ProjectEntity, Timestamp> deletedOn;
	public static volatile SingularAttribute<ProjectEntity, Timestamp> dueDate;
	public static volatile SingularAttribute<ProjectEntity, Integer> cloneSourceProjectId;
	public static volatile SingularAttribute<ProjectEntity, String> description;
	public static volatile SingularAttribute<ProjectEntity, Integer> linkedSourceProjectId;
	public static volatile SingularAttribute<ProjectEntity, Timestamp> creationDate;
	public static volatile SingularAttribute<ProjectEntity, Boolean> publishFlag;
	public static volatile SingularAttribute<ProjectEntity, String> assignedTo;
	public static volatile SingularAttribute<ProjectEntity, String> deletedBy;
	public static volatile SingularAttribute<ProjectEntity, String> deletedDue;
	public static volatile SingularAttribute<ProjectEntity, Boolean> mgaFlag;
	public static volatile SingularAttribute<ProjectEntity, Boolean> deleted;
	public static volatile SingularAttribute<ProjectEntity, String> createdBy;
	public static volatile SingularAttribute<ProjectEntity, String> name;
	public static volatile SingularAttribute<ProjectEntity, Integer> projectId;
	public static volatile SingularAttribute<ProjectEntity, String> workspaceId;

}

