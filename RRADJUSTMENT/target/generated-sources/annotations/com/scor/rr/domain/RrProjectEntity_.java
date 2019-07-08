package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RrProjectEntity.class)
public abstract class RrProjectEntity_ {

	public static volatile SingularAttribute<RrProjectEntity, String> masterFlag;
	public static volatile SingularAttribute<RrProjectEntity, String> sourceId;
	public static volatile SingularAttribute<RrProjectEntity, String> linkFlag;
	public static volatile SingularAttribute<RrProjectEntity, String> clonedFlag;
	public static volatile SingularAttribute<RrProjectEntity, String> receptionDate;
	public static volatile SingularAttribute<RrProjectEntity, Double> pltThreadSum;
	public static volatile SingularAttribute<RrProjectEntity, String> postInuredFlag;
	public static volatile SingularAttribute<RrProjectEntity, Timestamp> dueDate;
	public static volatile SingularAttribute<RrProjectEntity, String> description;
	public static volatile SingularAttribute<RrProjectEntity, Double> xactSum;
	public static volatile SingularAttribute<RrProjectEntity, String> cloneSourceProject;
	public static volatile SingularAttribute<RrProjectEntity, Timestamp> creationDate;
	public static volatile SingularAttribute<RrProjectEntity, String> publishFlag;
	public static volatile SingularAttribute<RrProjectEntity, String> assignedTo;
	public static volatile SingularAttribute<RrProjectEntity, Double> regionPerilSum;
	public static volatile SingularAttribute<RrProjectEntity, String> createdBy;
	public static volatile SingularAttribute<RrProjectEntity, String> pltSum;
	public static volatile SingularAttribute<RrProjectEntity, String> name;
	public static volatile SingularAttribute<RrProjectEntity, String> id;

}

