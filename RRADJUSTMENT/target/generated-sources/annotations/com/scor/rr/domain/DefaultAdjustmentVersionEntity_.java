package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DefaultAdjustmentVersionEntity.class)
public abstract class DefaultAdjustmentVersionEntity_ {

	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Timestamp> effectiveTo;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, String> notes;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, DefaultAdjustmentEntity> defaultAdjustment;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Integer> idDefaultAdjustmentVersion;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Boolean> isactive;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Timestamp> deletedDt;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Integer> versionSequence;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, Timestamp> effectiveFrom;
	public static volatile SingularAttribute<DefaultAdjustmentVersionEntity, String> deletedBy;

}

