package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectView.class)
public abstract class ProjectView_ {

	public static volatile SingularAttribute<ProjectView, Boolean> linkFlag;
	public static volatile SingularAttribute<ProjectView, Boolean> postInuredFlag;
	public static volatile SingularAttribute<ProjectView, Integer> pltThreadSum;
	public static volatile SingularAttribute<ProjectView, Timestamp> dueDate;
	public static volatile SingularAttribute<ProjectView, String> description;
	public static volatile SingularAttribute<ProjectView, Integer> xactSum;
	public static volatile SingularAttribute<ProjectView, Timestamp> creationDate;
	public static volatile SingularAttribute<ProjectView, Boolean> isLocking;
	public static volatile SingularAttribute<ProjectView, Boolean> publishFlag;
	public static volatile SingularAttribute<ProjectView, String> assignedTo;
	public static volatile SingularAttribute<ProjectView, Integer> regionPerilSum;
	public static volatile SingularAttribute<ProjectView, String> createdBy;
	public static volatile SingularAttribute<ProjectView, Integer> pltSum;
	public static volatile SingularAttribute<ProjectView, Integer> uwy;
	public static volatile SingularAttribute<ProjectView, String> name;
	public static volatile SingularAttribute<ProjectView, String> projectId;
	public static volatile SingularAttribute<ProjectView, String> workspaceId;

}

