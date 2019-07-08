package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CountryEntity.class)
public abstract class CountryEntity_ {

	public static volatile SingularAttribute<CountryEntity, Timestamp> inceptiondate;
	public static volatile SingularAttribute<CountryEntity, Timestamp> expirydate;
	public static volatile SingularAttribute<CountryEntity, String> replaceby;
	public static volatile SingularAttribute<CountryEntity, Boolean> isactive;
	public static volatile SingularAttribute<CountryEntity, String> countrycode;
	public static volatile SingularAttribute<CountryEntity, String> countryofficialname;
	public static volatile SingularAttribute<CountryEntity, Timestamp> lastsynchronized;
	public static volatile SingularAttribute<CountryEntity, String> telephonecode;
	public static volatile SingularAttribute<CountryEntity, String> id;
	public static volatile SingularAttribute<CountryEntity, String> countryshortname;
	public static volatile SingularAttribute<CountryEntity, String> currencycode;
	public static volatile SingularAttribute<CountryEntity, Boolean> embargostatus;

}

