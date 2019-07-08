package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SubsidiaryEntity.class)
public abstract class SubsidiaryEntity_ {

	public static volatile SingularAttribute<SubsidiaryEntity, String> code;
	public static volatile SingularAttribute<SubsidiaryEntity, Boolean> isactive;
	public static volatile SingularAttribute<SubsidiaryEntity, Timestamp> lastsynchronized;
	public static volatile SingularAttribute<SubsidiaryEntity, String> id;
	public static volatile SingularAttribute<SubsidiaryEntity, String> label;

}

