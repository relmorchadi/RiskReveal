package com.scor.rr.domain;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DefaultAdjustmentVersionEntity.class)
public abstract class DefaultAdjustmentVersionEntity_ {

	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Date> effectiveTo;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, DefaultAdjustmentEntity> defaultAdjustmentByFkDefaultAdjustment;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Integer> versionSequence;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Integer> id;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Date> effectiveFrom;

}

