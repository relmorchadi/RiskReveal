package com.scor.rr.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RmsSourceResultEntity.class)
public abstract class RmsSourceResultEntity_ {

	public static volatile SingularAttribute<RmsSourceResultEntity, String> targetCurrency;
	public static volatile SingularAttribute<RmsSourceResultEntity, BigDecimal> unitMultiplier;
	public static volatile SingularAttribute<RmsSourceResultEntity, BigDecimal> proportion;
	public static volatile SingularAttribute<RmsSourceResultEntity, Integer> rmsSourceResultId;
	public static volatile SingularAttribute<RmsSourceResultEntity, String> targetRegionPeril;
	public static volatile SingularAttribute<RmsSourceResultEntity, String> targetRapCode;
	public static volatile SingularAttribute<RmsSourceResultEntity, String> financialPerspective;
	public static volatile SingularAttribute<RmsSourceResultEntity, Integer> projectId;
	public static volatile SingularAttribute<RmsSourceResultEntity, String> overrideRegionPerilBasis;
	public static volatile SingularAttribute<RmsSourceResultEntity, String> occurrenceBasis;

}

