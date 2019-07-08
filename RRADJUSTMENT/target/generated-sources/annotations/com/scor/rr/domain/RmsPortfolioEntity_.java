package com.scor.rr.domain;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RmsPortfolioEntity.class)
public abstract class RmsPortfolioEntity_ {

	public static volatile SingularAttribute<RmsPortfolioEntity, String> number;
	public static volatile SingularAttribute<RmsPortfolioEntity, Integer> portfolioId;
	public static volatile SingularAttribute<RmsPortfolioEntity, String> agCurrency;
	public static volatile SingularAttribute<RmsPortfolioEntity, Timestamp> created;
	public static volatile SingularAttribute<RmsPortfolioEntity, Integer> edmId;
	public static volatile SingularAttribute<RmsPortfolioEntity, String> edmName;
	public static volatile SingularAttribute<RmsPortfolioEntity, String> agSource;
	public static volatile SingularAttribute<RmsPortfolioEntity, String> name;
	public static volatile SingularAttribute<RmsPortfolioEntity, String> agCedent;
	public static volatile SingularAttribute<RmsPortfolioEntity, String> description;
	public static volatile SingularAttribute<RmsPortfolioEntity, String> peril;
	public static volatile SingularAttribute<RmsPortfolioEntity, String> type;

}

