package com.scor.rr.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RrSummaryStatisticHeaderEntity.class)
public abstract class RrSummaryStatisticHeaderEntity_ {

	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> aep500;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> aep250;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> cov;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> aep100;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, Integer> rrSummaryStatisticHeaderId;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, String> lossDataType;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> purePremium;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, String> epsFilePath;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, String> epsFileName;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> oep500;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> oep100;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, String> financialPerspective;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> standardDeviation;
	public static volatile SingularAttribute<RrSummaryStatisticHeaderEntity, BigDecimal> oep250;

}

