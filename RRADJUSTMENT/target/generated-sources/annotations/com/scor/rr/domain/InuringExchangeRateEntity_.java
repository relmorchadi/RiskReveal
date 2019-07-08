package com.scor.rr.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InuringExchangeRateEntity.class)
public abstract class InuringExchangeRateEntity_ {

	public static volatile SingularAttribute<InuringExchangeRateEntity, Integer> inuringExchangeRateId;
	public static volatile SingularAttribute<InuringExchangeRateEntity, String> exchangeRateId;
	public static volatile SingularAttribute<InuringExchangeRateEntity, String> sourceCurrencyId;
	public static volatile SingularAttribute<InuringExchangeRateEntity, Integer> inuringPackageOperationId;
	public static volatile SingularAttribute<InuringExchangeRateEntity, String> targetCurrencyId;

}

