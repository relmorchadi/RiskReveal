package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PltPricingLossEngineEntity.class)
public abstract class PltPricingLossEngineEntity_ {

	public static volatile SingularAttribute<PltPricingLossEngineEntity, Integer> capLossesAmount;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, Timestamp> lastSynchronized;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, String> franchiseDeductibleType;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, Double> pltsFxRate;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, Timestamp> lastUpdateRiskReveal;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, String> pltsRiskRevealId;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, String> type;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, String> pltPricingId;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, String> name;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, Long> nonNatCatLoss;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, String> pltPricingSectionId;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, String> id;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, Integer> franchiseDeductibleAmount;
	public static volatile SingularAttribute<PltPricingLossEngineEntity, Integer> engineId;

}

