package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StatusEntity.class)
public abstract class StatusEntity_ {

	public static volatile SingularAttribute<StatusEntity, String> longname;
	public static volatile SingularAttribute<StatusEntity, String> code;
	public static volatile SingularAttribute<StatusEntity, Boolean> isactive;
	public static volatile SingularAttribute<StatusEntity, Timestamp> lastsynchronized;
	public static volatile SingularAttribute<StatusEntity, String> id;
	public static volatile SingularAttribute<StatusEntity, String> shortname;

}

