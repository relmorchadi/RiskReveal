package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InsuredEntity.class)
public abstract class InsuredEntity_ {

	public static volatile SingularAttribute<InsuredEntity, String> secondname;
	public static volatile SingularAttribute<InsuredEntity, String> code;
	public static volatile SingularAttribute<InsuredEntity, Boolean> isactive;
	public static volatile SingularAttribute<InsuredEntity, Timestamp> lastsynchronized;
	public static volatile SingularAttribute<InsuredEntity, String> name;
	public static volatile SingularAttribute<InsuredEntity, String> id;
	public static volatile SingularAttribute<InsuredEntity, String> clientcodeId;

}

