package com.scor.rr.domain;

import java.sql.Date;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScorPltHeaderEntity.class)
public abstract class ScorPltHeaderEntity_ {

	public static volatile SingularAttribute<ScorPltHeaderEntity, Boolean> publishToPricing;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Boolean> narrativeChanged;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> perilCode;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> truncationThreshold;
	public static volatile SingularAttribute<ScorPltHeaderEntity, RrAnalysisNewEntity> rrAnalysis;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Timestamp> deletedOn;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> pltLossDataFileName;
	public static volatile SingularAttribute<ScorPltHeaderEntity, MarketChannelEntity> marketChannel;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> engineType;
	public static volatile SingularAttribute<ScorPltHeaderEntity, ProjectEntity> project;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Timestamp> createdOn;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> deletedBy;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Integer> currentNarrative;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> userOccurrenceBasis;
	public static volatile SingularAttribute<ScorPltHeaderEntity, AdjustmentBasisEntity> adjustmentBasisPrevious;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Integer> previousNarrative;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Integer> inuringPackageId;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Boolean> generatedFromDefaultAdjustement;
	public static volatile SingularAttribute<ScorPltHeaderEntity, BinFileEntity> binFile;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> pltType;
	public static volatile SingularAttribute<ScorPltHeaderEntity, AdjustmentBasisEntity> adjustmentBasisCurrent;
	public static volatile SingularAttribute<ScorPltHeaderEntity, ScorPltHeaderEntity> scorPltHeaderByCloningSourceId;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Integer> importSequence;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> defaultPltName;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> ccyCode;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> lastModifiedBy;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Integer> rmsSimulationSet;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> geoCode;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> pltLossDataFilePath;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> threadName;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> sourceLossModelingBasis;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> deletedDue;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> lastModifiedOn;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Integer> pltSimulationPeriods;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Date> createdDate;
	public static volatile SingularAttribute<ScorPltHeaderEntity, TargetRapEntity> targetRap;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> truncationCurrency;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> sourceLossEntityingBasis;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> createdBy;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Boolean> basisChanged;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> truncationExchangeRate;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> udName;
	public static volatile SingularAttribute<ScorPltHeaderEntity, String> geoDescription;
	public static volatile SingularAttribute<ScorPltHeaderEntity, RegionPerilEntity> regionPeril;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Integer> scorPltHeaderId;
	public static volatile SingularAttribute<ScorPltHeaderEntity, EntityEntity> entity;
	public static volatile SingularAttribute<ScorPltHeaderEntity, Timestamp> lastGenerated;

}

