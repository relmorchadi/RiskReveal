package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RmsEntityDataSourceEntity.class)
public abstract class RmsEntityDataSourceEntity_ {

	public static volatile SingularAttribute<RmsEntityDataSourceEntity, Integer> versionId;
	public static volatile SingularAttribute<RmsEntityDataSourceEntity, String> instanceId;
	public static volatile SingularAttribute<RmsEntityDataSourceEntity, Timestamp> dateCreated;
	public static volatile SingularAttribute<RmsEntityDataSourceEntity, String> instanceName;
	public static volatile SingularAttribute<RmsEntityDataSourceEntity, String> name;
	public static volatile SingularAttribute<RmsEntityDataSourceEntity, Integer> rmsEntityDataSourceId;
	public static volatile SingularAttribute<RmsEntityDataSourceEntity, String> rmsId;
	public static volatile SingularAttribute<RmsEntityDataSourceEntity, String> source;
	public static volatile SingularAttribute<RmsEntityDataSourceEntity, String> type;

}

