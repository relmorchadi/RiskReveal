package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RrLossDataHeaderEntity.class)
public abstract class RrLossDataHeaderEntity_ {

	public static volatile SingularAttribute<RrLossDataHeaderEntity, Integer> rrLossDataHeaderId;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, String> originalTarget;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, Timestamp> createdDate;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, String> lossDataFileName;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, Integer> cloningSourceId;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, String> lossTableType;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, String> lossDataFilePath;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, String> currency;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, String> fileType;
	public static volatile SingularAttribute<RrLossDataHeaderEntity, String> fileDataFormat;

}

