package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PltPricingEntity.class)
public abstract class PltPricingEntity_ {

	public static volatile SingularAttribute<PltPricingEntity, Integer> companyCode;
	public static volatile SingularAttribute<PltPricingEntity, Integer> uwYear;
	public static volatile SingularAttribute<PltPricingEntity, Timestamp> lastSynchronized;
	public static volatile SingularAttribute<PltPricingEntity, String> pltPricingId;
	public static volatile SingularAttribute<PltPricingEntity, String> countryIsoCode;
	public static volatile SingularAttribute<PltPricingEntity, String> name;
	public static volatile SingularAttribute<PltPricingEntity, Timestamp> lastUpdateRiskReveal;
	public static volatile SingularAttribute<PltPricingEntity, String> pricingStructure;
	public static volatile SingularAttribute<PltPricingEntity, String> importedOmegaId;
	public static volatile SingularAttribute<PltPricingEntity, String> status;

}

