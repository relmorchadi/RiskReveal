package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(XActPublicationEntity.class)
public abstract class XActPublicationEntity_ {

	public static volatile SingularAttribute<XActPublicationEntity, Integer> xactPublicationId;
	public static volatile SingularAttribute<XActPublicationEntity, Integer> scorPltHeaderId;
	public static volatile SingularAttribute<XActPublicationEntity, Boolean> xActUsed;
	public static volatile SingularAttribute<XActPublicationEntity, Timestamp> xActPublicationDate;
	public static volatile SingularAttribute<XActPublicationEntity, Boolean> xActAvailable;

}

