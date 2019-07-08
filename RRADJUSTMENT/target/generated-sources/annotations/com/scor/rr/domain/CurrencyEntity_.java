package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CurrencyEntity.class)
public abstract class CurrencyEntity_ {

	public static volatile SingularAttribute<CurrencyEntity, Timestamp> inceptiondate;
	public static volatile SingularAttribute<CurrencyEntity, Timestamp> expirydate;
	public static volatile SingularAttribute<CurrencyEntity, String> code;
	public static volatile SingularAttribute<CurrencyEntity, String> countrycodeId;
	public static volatile SingularAttribute<CurrencyEntity, Boolean> isactive;
	public static volatile SingularAttribute<CurrencyEntity, Timestamp> lastsynchronized;
	public static volatile SingularAttribute<CurrencyEntity, Boolean> isreportingcurrency;
	public static volatile SingularAttribute<CurrencyEntity, String> id;
	public static volatile SingularAttribute<CurrencyEntity, String> label;
	public static volatile SingularAttribute<CurrencyEntity, Integer> reportingcurrencycodeId;

}

