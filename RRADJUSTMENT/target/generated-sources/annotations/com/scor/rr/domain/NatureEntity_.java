package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NatureEntity.class)
public abstract class NatureEntity_ {

	public static volatile SingularAttribute<NatureEntity, String> naturecode;
	public static volatile SingularAttribute<NatureEntity, String> longname;
	public static volatile SingularAttribute<NatureEntity, String> familycode;
	public static volatile SingularAttribute<NatureEntity, String> generalnature;
	public static volatile SingularAttribute<NatureEntity, Boolean> isactive;
	public static volatile SingularAttribute<NatureEntity, Timestamp> lastsynchronized;
	public static volatile SingularAttribute<NatureEntity, String> mnemonic;
	public static volatile SingularAttribute<NatureEntity, String> id;
	public static volatile SingularAttribute<NatureEntity, String> familylabel;
	public static volatile SingularAttribute<NatureEntity, String> shortname;

}

