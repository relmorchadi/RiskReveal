/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2017                    */
/* Created on:     07.05.2020 14:57:23                          */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AccumulationPackageAttachedPLT') and o.name = 'FKAccumulationAttached_PLTHeader')
alter table dbo.AccumulationPackageAttachedPLT
   drop constraint FKAccumulationAttached_PLTHeader
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentNodeProcessing') and o.name = 'FKAdjustNodeProcAdjustedPLT_PLTHeaderId')
alter table dbo.AdjustmentNodeProcessing
   drop constraint FKAdjustNodeProcAdjustedPLT_PLTHeaderId
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentNodeProcessing') and o.name = 'FKAdjustNodeProcInputPLT_PLTHeaderId')
alter table dbo.AdjustmentNodeProcessing
   drop constraint FKAdjustNodeProcInputPLT_PLTHeaderId
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentThread') and o.name = 'FKAdjustmentThread_finalPLT')
alter table dbo.AdjustmentThread
   drop constraint FKAdjustmentThread_finalPLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentThread') and o.name = 'FKAdjustmentThread_initialPLT')
alter table dbo.AdjustmentThread
   drop constraint FKAdjustmentThread_initialPLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ExposureSummaryData') and o.name = 'FKGlobalExposureViewSummaryId_ExposureSummaryData')
alter table dbo.ExposureSummaryData
   drop constraint FKGlobalExposureViewSummaryId_ExposureSummaryData
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ExposureSummaryData') and o.name = 'FKModelPortfolioId_ExposureSummaryData')
alter table dbo.ExposureSummaryData
   drop constraint FKModelPortfolioId_ExposureSummaryData
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GlobalExposureView') and o.name = 'FKProjectId_GlobalExposureView')
alter table dbo.GlobalExposureView
   drop constraint FKProjectId_GlobalExposureView
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GlobalExposureViewSummary') and o.name = 'FKGlobalExposureViewId_GlobalExposureViewSummary')
alter table dbo.GlobalExposureViewSummary
   drop constraint FKGlobalExposureViewId_GlobalExposureViewSummary
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringFinalAttachedPLT') and o.name = 'FKPLTHeaderId_InuringFinal')
alter table dbo.InuringFinalAttachedPLT
   drop constraint FKPLTHeaderId_InuringFinal
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringInputAttachedPLT') and o.name = 'FKPLTHeaderId_Inuring')
alter table dbo.InuringInputAttachedPLT
   drop constraint FKPLTHeaderId_Inuring
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelAnalysis') and o.name = 'FKProjectId_Analysis')
alter table dbo.ModelAnalysis
   drop constraint FKProjectId_Analysis
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelPortfolio') and o.name = 'FKProjectID_Portfolio')
alter table dbo.ModelPortfolio
   drop constraint FKProjectID_Portfolio
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelPortfolio') and o.name = 'FKProjectImportRunId_Portfolio')
alter table dbo.ModelPortfolio
   drop constraint FKProjectImportRunId_Portfolio
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKCurrencyCode_PLT')
alter table dbo.PLTHeader
   drop constraint FKCurrencyCode_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKPLTHeader_SummaryStatHeaderId')
alter table dbo.PLTHeader
   drop constraint FKPLTHeader_SummaryStatHeaderId
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKRRAnalysisId_PltHeaderId')
alter table dbo.PLTHeader
   drop constraint FKRRAnalysisId_PltHeaderId
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKRegionPerilId_PLT')
alter table dbo.PLTHeader
   drop constraint FKRegionPerilId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKTargetRAPId_PLT')
alter table dbo.PLTHeader
   drop constraint FKTargetRAPId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeaderTag') and o.name = 'FKPLTHeaderId_Tag')
alter table dbo.PLTHeaderTag
   drop constraint FKPLTHeaderId_Tag
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Project') and o.name = 'FKProjectImportRunId_Project')
alter table dbo.Project
   drop constraint FKProjectImportRunId_Project
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Project') and o.name = 'FKRLModelDataSourceId_Proj')
alter table dbo.Project
   drop constraint FKRLModelDataSourceId_Proj
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationForeWriter') and o.name = 'FKProjectId_FW')
alter table dbo.ProjectConfigurationForeWriter
   drop constraint FKProjectId_FW
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationMGA') and o.name = 'FKProjectId_MGA')
alter table dbo.ProjectConfigurationMGA
   drop constraint FKProjectId_MGA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectForewriterExpectedScope') and o.name = 'FKProject_ExpScope')
alter table dbo.ProjectForewriterExpectedScope
   drop constraint FKProject_ExpScope
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationARC') and o.name = 'FKPLTHeaderId_ARC')
alter table dbo.PublicationARC
   drop constraint FKPLTHeaderId_ARC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationPricingForeWriter') and o.name = 'FKPLTHeaderId_FW')
alter table dbo.PublicationPricingForeWriter
   drop constraint FKPLTHeaderId_FW
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationPricingxAct') and o.name = 'FKPLTHeaderId_xAct')
alter table dbo.PublicationPricingxAct
   drop constraint FKPLTHeaderId_xAct
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.SourceExposureSummaryItem') and o.name = 'FKGlobalExposureViewSummaryId_SourceExposureSummaryItem')
alter table dbo.SourceExposureSummaryItem
   drop constraint FKGlobalExposureViewSummaryId_SourceExposureSummaryItem
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.SummaryStatisticsDetail') and o.name = 'FKPLTHeaderId_SummStat')
alter table dbo.SummaryStatisticsDetail
   drop constraint FKPLTHeaderId_SummStat
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.ExposureSummaryData')
            and   name  = 'PK__Exposure__D0B4B24BAF3137DD'
            and   indid > 0
            and   indid < 255)
alter table dbo.ExposureSummaryData
   drop constraint PK__Exposure__D0B4B24BAF3137DD
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_ExposureSummaryData')
            and   type = 'U')
   drop table dbo.tmp_ExposureSummaryData
go

execute sp_rename ExposureSummaryData, tmp_ExposureSummaryData
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.GlobalExposureViewSummary')
            and   name  = 'PK__GlobalEx__7DA84073BEB1F185'
            and   indid > 0
            and   indid < 255)
alter table dbo.GlobalExposureViewSummary
   drop constraint PK__GlobalEx__7DA84073BEB1F185
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_GlobalExposureViewSummary')
            and   type = 'U')
   drop table dbo.tmp_GlobalExposureViewSummary
go

execute sp_rename GlobalExposureViewSummary, tmp_GlobalExposureViewSummary
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.ModelPortfolio')
            and   name  = 'PK__ModelPor__72D43252724036C7'
            and   indid > 0
            and   indid < 255)
alter table dbo.ModelPortfolio
   drop constraint PK__ModelPor__72D43252724036C7
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_ModelPortfolio')
            and   type = 'U')
   drop table dbo.tmp_ModelPortfolio
go

execute sp_rename ModelPortfolio, tmp_ModelPortfolio
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.PLTHeader')
            and   name  = 'PK__PLTHEADERID'
            and   indid > 0
            and   indid < 255)
alter table dbo.PLTHeader
   drop constraint PK__PLTHEADERID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_PLTHeader')
            and   type = 'U')
   drop table dbo.tmp_PLTHeader
go

execute sp_rename PLTHeader, tmp_PLTHeader
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.Project')
            and   name  = 'PK_PROJECTID'
            and   indid > 0
            and   indid < 255)
alter table dbo.Project
   drop constraint PK_PROJECTID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_Project')
            and   type = 'U')
   drop table dbo.tmp_Project
go

execute sp_rename Project, tmp_Project
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.SourceExposureSummaryItem')
            and   name  = 'PK__SourceEx__614605A2427EF3B8'
            and   indid > 0
            and   indid < 255)
alter table dbo.SourceExposureSummaryItem
   drop constraint PK__SourceEx__614605A2427EF3B8
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_SourceExposureSummaryItem')
            and   type = 'U')
   drop table dbo.tmp_SourceExposureSummaryItem
go

execute sp_rename SourceExposureSummaryItem, tmp_SourceExposureSummaryItem
go

/*==============================================================*/
/* Table: ExposureSummaryData                                   */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ExposureSummaryData')
            and   type = 'U')
create table dbo.ExposureSummaryData (
   ExposureSummaryDataId bigint               identity(1, 1),
   Admin1Code           varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   AnalysisRegionCode   varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   ConformedCurrency    varchar(3)           collate SQL_Latin1_General_CP1_CI_AS null,
   conformedCurrencyUSDRate numeric(20,8)        null,
   CountryCode          varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   Dimension1           varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   Dimension2           varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   Dimension3           varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   Dimension4           varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   DimensionSort1       int                  null,
   DimensionSort2       int                  null,
   DimensionSort3       int                  null,
   DimensionSort4       int                  null,
   ExposureCurrency     varchar(3)           collate SQL_Latin1_General_CP1_CI_AS null,
   ExposureCurrencyUSDRate numeric(20,8)        null,
   FinancialPerspective varchar(5)           collate SQL_Latin1_General_CP1_CI_AS null,
   LocationCount        numeric(19)          null,
   Metric               varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   PerilCode            varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   SourcePortfolioId    numeric(19)          null,
   SourcePortfolioType  varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   RateDate             datetime2(7)         null,
   RegionPerilCode      varchar(15)          collate SQL_Latin1_General_CP1_CI_AS null,
   RegionPerilGroupCode varchar(15)          collate SQL_Latin1_General_CP1_CI_AS null,
   TIV                  numeric(27,2)        null,
   AvgTIV               numeric(27,2)        null,
   GlobalExposureViewSummaryId bigint               not null,
   ModelPortfolioId     bigint               not null,
   Entity               int                  null,
   constraint PK__Exposure__D0B4B24BAF3137DD primary key (ExposureSummaryDataId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.ExposureSummaryData on
go

insert into dbo.ExposureSummaryData (ExposureSummaryDataId, Admin1Code, AnalysisRegionCode, ConformedCurrency, conformedCurrencyUSDRate, CountryCode, Dimension1, Dimension2, Dimension3, Dimension4, DimensionSort1, DimensionSort2, DimensionSort3, DimensionSort4, ExposureCurrency, ExposureCurrencyUSDRate, FinancialPerspective, LocationCount, Metric, PerilCode, SourcePortfolioId, SourcePortfolioType, RateDate, RegionPerilCode, RegionPerilGroupCode, TIV, AvgTIV, GlobalExposureViewSummaryId, ModelPortfolioId, Entity)
select ExposureSummaryDataId, Admin1Code, AnalysisRegionCode, ConformedCurrency, conformedCurrencyUSDRate, CountryCode, Dimension1, Dimension2, Dimension3, Dimension4, DimensionSort1, DimensionSort2, DimensionSort3, DimensionSort4, ExposureCurrency, ExposureCurrencyUSDRate, FinancialPerspective, LocationCount, Metric, PerilCode, SourcePortfolioId, SourcePortfolioType, RateDate, RegionPerilCode, RegionPerilGroupCode, TIV, AvgTIV, GlobalExposureViewSummaryId, ModelPortfolioId, Entity
from dbo.tmp_ExposureSummaryData
go

set identity_insert dbo.ExposureSummaryData off
go

/*==============================================================*/
/* Table: GlobalExposureViewSummary                             */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.GlobalExposureViewSummary')
            and   type = 'U')
create table dbo.GlobalExposureViewSummary (
   GlobalExposureViewSummaryId bigint               identity(1, 1),
   Entity               int                  null,
   EdmId                bigint               null,
   EdmName              varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   InstanceId           varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   SummaryOrder         int                  null,
   SummaryTitle         varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   GlobalExposureViewId bigint               not null,
   constraint PK__GlobalEx__7DA84073BEB1F185 primary key (GlobalExposureViewSummaryId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.GlobalExposureViewSummary on
go

insert into dbo.GlobalExposureViewSummary (GlobalExposureViewSummaryId, Entity, EdmId, EdmName, InstanceId, SummaryOrder, SummaryTitle, GlobalExposureViewId)
select GlobalExposureViewSummaryId, Entity, EdmId, EdmName, InstanceId, SummaryOrder, SummaryTitle, GlobalExposureViewId
from dbo.tmp_GlobalExposureViewSummary
go

set identity_insert dbo.GlobalExposureViewSummary off
go

/*==============================================================*/
/* Table: ModelPortfolio                                        */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ModelPortfolio')
            and   type = 'U')
create table dbo.ModelPortfolio (
   ModelPortfolioId     bigint               identity(1, 1),
   Entity               int                  null,
   ProjectId            bigint               not null,
   ProjectImportRunId   bigint               not null,
   SourcePortfolioId    bigint               null,
   DataSourceId         bigint               null,
   DataSourceName       varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   ModelPortfolioName   varchar(150)         collate SQL_Latin1_General_CP1_CI_AS null,
   ModelPortfolioType   varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   ModelPortfolioDescription varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   DivisionNumber       int                  null,
   Currency             varchar(3)           collate SQL_Latin1_General_CP1_CI_AS null,
   ExchangeRate         numeric(20,8)        null,
   ImportStatus         varchar(15)          collate SQL_Latin1_General_CP1_CI_AS null,
   ImportLocationLevel  bit                  null,
   ExposureLevel        bit                  null,
   ExposedLocationPerils varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   SourceModellingVendor varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   SourceModellingSystem varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   SourceModellingSystemInstance varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   SourceModellingSystemVersion int                  null,
   ImportedDate         datetime2(7)         null,
   CreationDate         datetime2(7)         null,
   Proportion           numeric(20,8)        null,
   UnitMultiplier       numeric(20,8)        null,
   constraint PK__ModelPor__72D43252724036C7 primary key (ModelPortfolioId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.ModelPortfolio on
go

insert into dbo.ModelPortfolio (ModelPortfolioId, Entity, ProjectId, ProjectImportRunId, SourcePortfolioId, DataSourceId, DataSourceName, ModelPortfolioName, ModelPortfolioType, ModelPortfolioDescription, DivisionNumber, Currency, ExchangeRate, ImportStatus, ImportLocationLevel, ExposureLevel, ExposedLocationPerils, SourceModellingVendor, SourceModellingSystem, SourceModellingSystemInstance, SourceModellingSystemVersion, ImportedDate, CreationDate, Proportion, UnitMultiplier)
select ModelPortfolioId, Entity, ProjectId, ProjectImportRunId, SourcePortfolioId, DataSourceId, DataSourceName, ModelPortfolioName, ModelPortfolioType, ModelPortfolioDescription, DivisionNumber, Currency, ExchangeRate, ImportStatus, ImportLocationLevel, ExposureLevel, ExposedLocationPerils, SourceModellingVendor, SourceModellingSystem, SourceModellingSystemInstance, SourceModellingSystemVersion, ImportedDate, CreationDate, Proportion, UnitMultiplier
from dbo.tmp_ModelPortfolio
go

set identity_insert dbo.ModelPortfolio off
go

/*==============================================================*/
/* Table: PLTHeader                                             */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PLTHeader')
            and   type = 'U')
create table dbo.PLTHeader (
   PLTHeaderId          bigint               identity(1, 1),
   Archived             bit                  null,
   ArchivedDate         datetime2(7)         null,
   CloningSourceId      bigint               null,
   CreatedBy            bigint               null,
   CreatedDate          datetime2(7)         null,
   CurrencyCode         varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   Currencyid           varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   DefaultPltName       varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   DeletedBy            varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   DeletedDue           varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   DeletedOn            datetime2(7)         null,
   Entity               int                  null,
   GeneratedFromDefaultAdjustment bit                  null,
   GeoCode              varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   GeoDescription       varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   GroupedPLT           bit                  null,
   ImportSequence       int                  null,
   InuringPackageId     bigint               null,
   IsLocked             bit                  null,
   LossDataFileName     varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   LossDataFilePath     varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   ModelAnalysisId      bigint               not null,
   PerilCode            varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   SimulationPeriods    int                  null,
   PLTType              varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   ProjectId            bigint               not null,
   RegionPerilId        bigint               null,
   RmsSimulationSet     int                  null,
   SourceLossModelingBasis varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   TargetRAPId          bigint               not null,
   ThreadName           varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   TruncationCurrency   varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   TruncationExchangeRate varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   TruncationThreshold  float                null,
   UDName               varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   UserOccurrenceBasis  varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   Locked               bit                  null,
   PLTSimulationPeriods int                  null,
   SummaryStatisticHeaderId bigint               null,
   constraint PK__PLTHEADERID primary key (PLTHeaderId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.PLTHeader on
go

insert into dbo.PLTHeader (PLTHeaderId, Archived, ArchivedDate, CloningSourceId, CreatedBy, CreatedDate, CurrencyCode, Currencyid, DefaultPltName, DeletedBy, DeletedDue, DeletedOn, Entity, GeneratedFromDefaultAdjustment, GeoCode, GeoDescription, GroupedPLT, ImportSequence, InuringPackageId, IsLocked, LossDataFileName, LossDataFilePath, ModelAnalysisId, PerilCode, SimulationPeriods, PLTType, ProjectId, RegionPerilId, RmsSimulationSet, SourceLossModelingBasis, TargetRAPId, ThreadName, TruncationCurrency, TruncationExchangeRate, TruncationThreshold, UDName, UserOccurrenceBasis, Locked, PLTSimulationPeriods, SummaryStatisticHeaderId)
select PLTHeaderId, Archived, ArchivedDate, CloningSourceId, CreatedBy, CreatedDate, CurrencyCode, Currencyid, DefaultPltName, DeletedBy, DeletedDue, DeletedOn, Entity, GeneratedFromDefaultAdjustment, GeoCode, GeoDescription, GroupedPLT, ImportSequence, InuringPackageId, IsLocked, LossDataFileName, LossDataFilePath, ModelAnalysisId, PerilCode, SimulationPeriods, PLTType, ProjectId, RegionPerilId, RmsSimulationSet, SourceLossModelingBasis, TargetRAPId, ThreadName, TruncationCurrency, TruncationExchangeRate, TruncationThreshold, UDName, UserOccurrenceBasis, Locked, PLTSimulationPeriods, SummaryStatisticHeaderId
from dbo.tmp_PLTHeader
go

set identity_insert dbo.PLTHeader off
go

/*==============================================================*/
/* Index: PLTHeader_SummaryStatisticHeaderId_unique             */
/*==============================================================*/




create unique nonclustered index PLTHeader_SummaryStatisticHeaderId_unique on dbo.PLTHeader (SummaryStatisticHeaderId ASC)
   where ([SummaryStatisticHeaderId] IS NOT NULL)
   on "PRIMARY"
go

/*==============================================================*/
/* Table: Project                                               */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Project')
            and   type = 'U')
create table dbo.Project (
   ProjectId            bigint               identity(1, 1),
   Entity               int                  null,
   WorkspaceId          bigint               not null,
   ProjectImportRunId   bigint               null,
   RLDataSourceId       bigint               null,
   ProjectName          varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   ProjectDescription   varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   IsMaster             bit                  null,
   IsLinked             bit                  null,
   IsPublished          bit                  null,
   IsCloned             bit                  null,
   IsPostInured         bit                  null,
   IsMGA                bit                  null,
   AssignedTo           varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   CreationDate         datetime2(7)         null,
   ReceptionDate        datetime2(7)         null,
   DueDate              datetime2(7)         null,
   CreatedBy            varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   LinkedSourceProjectId bigint               null,
   CloneSourceProjectId bigint               null,
   Deleted              bit                  null,
   DeletedOn            datetime2(7)         null,
   DeletedDue           varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   DeletedBy            varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   lastUpdatedOn        datetime2(7)         null,
   lastUpdatedBy        varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   constraint PK_PROJECTID primary key (ProjectId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.Project on
go

insert into dbo.Project (ProjectId, Entity, WorkspaceId, ProjectImportRunId, RLDataSourceId, ProjectName, ProjectDescription, IsMaster, IsLinked, IsPublished, IsCloned, IsPostInured, IsMGA, AssignedTo, CreationDate, ReceptionDate, DueDate, CreatedBy, LinkedSourceProjectId, CloneSourceProjectId, Deleted, DeletedOn, DeletedDue, DeletedBy, lastUpdatedOn, lastUpdatedBy)
select ProjectId, Entity, WorkspaceId, ProjectImportRunId, RLDataSourceId, ProjectName, ProjectDescription, IsMaster, IsLinked, IsPublished, IsCloned, IsPostInured, IsMGA, AssignedTo, CreationDate, ReceptionDate, DueDate, CreatedBy, LinkedSourceProjectId, CloneSourceProjectId, Deleted, DeletedOn, DeletedDue, DeletedBy, lastUpdatedOn, lastUpdatedBy
from dbo.tmp_Project
go

set identity_insert dbo.Project off
go

/*==============================================================*/
/* Table: SourceExposureSummaryItem                             */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.SourceExposureSummaryItem')
            and   type = 'U')
create table dbo.SourceExposureSummaryItem (
   SourceExposureSummaryItemId bigint               identity(1, 1),
   Entity               int                  null,
   ExposureSummaryName  varchar(150)         collate SQL_Latin1_General_CP1_CI_AS null,
   Admin1Code           varchar(15)          collate SQL_Latin1_General_CP1_CI_AS null,
   AnalysisRegionCode   varchar(15)          collate SQL_Latin1_General_CP1_CI_AS null,
   ConformedCurrency    varchar(3)           collate SQL_Latin1_General_CP1_CI_AS null,
   ConformedCurrencyUSDRate numeric(20,8)        null,
   CountryCode          varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   Dimension1           varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   Dimension2           varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   Dimension3           varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   Dimension4           varchar(50)          collate SQL_Latin1_General_CP1_CI_AS null,
   DimensionSort1       int                  null,
   DimensionSort2       int                  null,
   DimensionSort3       int                  null,
   DimensionSort4       int                  null,
   EDMId                bigint               null,
   ExposureCurrency     varchar(3)           collate SQL_Latin1_General_CP1_CI_AS null,
   ExposureCurrencyUSDRate numeric(20,8)        null,
   FinancialPerspective varchar(5)           collate SQL_Latin1_General_CP1_CI_AS null,
   LocationCount        numeric(20,8)        null,
   Peril                varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   PortfolioId          bigint               null,
   PortfolioType        varchar(10)          collate SQL_Latin1_General_CP1_CI_AS null,
   FxRateVintageDate    datetime2(7)         null,
   RegionPerilCode      varchar(15)          collate SQL_Latin1_General_CP1_CI_AS null,
   RegionPerilGroupCode varchar(15)          collate SQL_Latin1_General_CP1_CI_AS null,
   TotalTiv             numeric(27,2)        null,
   GlobalExposureViewSummaryId bigint               not null,
   constraint PK__SourceEx__614605A2427EF3B8 primary key (SourceExposureSummaryItemId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.SourceExposureSummaryItem on
go

insert into dbo.SourceExposureSummaryItem (SourceExposureSummaryItemId, Entity, ExposureSummaryName, Admin1Code, AnalysisRegionCode, ConformedCurrency, ConformedCurrencyUSDRate, CountryCode, Dimension1, Dimension2, Dimension3, Dimension4, DimensionSort1, DimensionSort2, DimensionSort3, DimensionSort4, EDMId, ExposureCurrency, ExposureCurrencyUSDRate, FinancialPerspective, LocationCount, Peril, PortfolioId, PortfolioType, FxRateVintageDate, RegionPerilCode, RegionPerilGroupCode, TotalTiv, GlobalExposureViewSummaryId)
select SourceExposureSummaryItemId, Entity, ExposureSummaryName, Admin1Code, AnalysisRegionCode, ConformedCurrency, ConformedCurrencyUSDRate, CountryCode, Dimension1, Dimension2, Dimension3, Dimension4, DimensionSort1, DimensionSort2, DimensionSort3, DimensionSort4, EDMId, ExposureCurrency, ExposureCurrencyUSDRate, FinancialPerspective, LocationCount, Peril, PortfolioId, PortfolioType, FxRateVintageDate, RegionPerilCode, RegionPerilGroupCode, TotalTiv, GlobalExposureViewSummaryId
from dbo.tmp_SourceExposureSummaryItem
go

set identity_insert dbo.SourceExposureSummaryItem off
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AccumulationPackageAttachedPLT') and o.name = 'FKAccumulationAttached_PLTHeader')
alter table dbo.AccumulationPackageAttachedPLT
   add constraint FKAccumulationAttached_PLTHeader foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentNodeProcessing') and o.name = 'FKAdjustNodeProcAdjustedPLT_PLTHeaderId')
alter table dbo.AdjustmentNodeProcessing
   add constraint FKAdjustNodeProcAdjustedPLT_PLTHeaderId foreign key (AdjustedPLTId)
      references dbo.PLTHeader (PLTHeaderId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentNodeProcessing') and o.name = 'FKAdjustNodeProcInputPLT_PLTHeaderId')
alter table dbo.AdjustmentNodeProcessing
   add constraint FKAdjustNodeProcInputPLT_PLTHeaderId foreign key (InputPLTId)
      references dbo.PLTHeader (PLTHeaderId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentThread') and o.name = 'FKAdjustmentThread_finalPLT')
alter table dbo.AdjustmentThread
   add constraint FKAdjustmentThread_finalPLT foreign key (FinalPLTId)
      references dbo.PLTHeader (PLTHeaderId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentThread') and o.name = 'FKAdjustmentThread_initialPLT')
alter table dbo.AdjustmentThread
   add constraint FKAdjustmentThread_initialPLT foreign key (InitialPLT)
      references dbo.PLTHeader (PLTHeaderId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ExposureSummaryData') and o.name = 'FKGlobalExposureViewSummaryId_ExposureSummaryData')
alter table dbo.ExposureSummaryData
   add constraint FKGlobalExposureViewSummaryId_ExposureSummaryData foreign key (GlobalExposureViewSummaryId)
      references dbo.GlobalExposureViewSummary (GlobalExposureViewSummaryId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ExposureSummaryData') and o.name = 'FKModelPortfolioId_ExposureSummaryData')
alter table dbo.ExposureSummaryData
   add constraint FKModelPortfolioId_ExposureSummaryData foreign key (ModelPortfolioId)
      references dbo.ModelPortfolio (ModelPortfolioId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GlobalExposureView') and o.name = 'FKProjectId_GlobalExposureView')
alter table dbo.GlobalExposureView
   add constraint FKProjectId_GlobalExposureView foreign key (ProjectId)
      references dbo.Project (ProjectId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GlobalExposureViewSummary') and o.name = 'FKGlobalExposureViewId_GlobalExposureViewSummary')
alter table dbo.GlobalExposureViewSummary
   add constraint FKGlobalExposureViewId_GlobalExposureViewSummary foreign key (GlobalExposureViewId)
      references dbo.GlobalExposureView (GlobalExposureViewId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringFinalAttachedPLT') and o.name = 'FKPLTHeaderId_InuringFinal')
alter table dbo.InuringFinalAttachedPLT
   add constraint FKPLTHeaderId_InuringFinal foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringInputAttachedPLT') and o.name = 'FKPLTHeaderId_Inuring')
alter table dbo.InuringInputAttachedPLT
   add constraint FKPLTHeaderId_Inuring foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelAnalysis') and o.name = 'FKProjectId_Analysis')
alter table dbo.ModelAnalysis
   add constraint FKProjectId_Analysis foreign key (ProjectId)
      references dbo.Project (ProjectId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelPortfolio') and o.name = 'FKProjectID_Portfolio')
alter table dbo.ModelPortfolio
   add constraint FKProjectID_Portfolio foreign key (ProjectId)
      references dbo.Project (ProjectId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelPortfolio') and o.name = 'FKProjectImportRunId_Portfolio')
alter table dbo.ModelPortfolio
   add constraint FKProjectImportRunId_Portfolio foreign key (ProjectImportRunId)
      references dbo.ProjectImportRun (ProjectImportRunId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKCurrencyCode_PLT')
alter table dbo.PLTHeader
   add constraint FKCurrencyCode_PLT foreign key (Currencyid)
      references dbo.Currency (CurrencyId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKPLTHeader_SummaryStatHeaderId')
alter table dbo.PLTHeader
   add constraint FKPLTHeader_SummaryStatHeaderId foreign key (SummaryStatisticHeaderId)
      references dbo.SummaryStatisticHeader (SummaryStatisticHeaderId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKRRAnalysisId_PltHeaderId')
alter table dbo.PLTHeader
   add constraint FKRRAnalysisId_PltHeaderId foreign key (ModelAnalysisId)
      references dbo.ModelAnalysis (ModelAnalysisId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKRegionPerilId_PLT')
alter table dbo.PLTHeader
   add constraint FKRegionPerilId_PLT foreign key (RegionPerilId)
      references dbo.RegionPeril (RegionPerilId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKTargetRAPId_PLT')
alter table dbo.PLTHeader
   add constraint FKTargetRAPId_PLT foreign key (TargetRAPId)
      references dbo.TargetRAP (TargetRAPId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FK_PLTHEADE_REFERENCE_PROJECT')
alter table dbo.PLTHeader
   add constraint FK_PLTHEADE_REFERENCE_PROJECT foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeaderTag') and o.name = 'FKPLTHeaderId_Tag')
alter table dbo.PLTHeaderTag
   add constraint FKPLTHeaderId_Tag foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Project') and o.name = 'FKProjectImportRunId_Project')
alter table dbo.Project
   add constraint FKProjectImportRunId_Project foreign key (ProjectImportRunId)
      references dbo.ProjectImportRun (ProjectImportRunId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Project') and o.name = 'FKRLModelDataSourceId_Proj')
alter table dbo.Project
   add constraint FKRLModelDataSourceId_Proj foreign key (RLDataSourceId)
      references dbo.RLModelDataSource (RlDataSourceId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Project') and o.name = 'FK_PROJECT_REFERENCE_WORKSPAC')
alter table dbo.Project
   add constraint FK_PROJECT_REFERENCE_WORKSPAC foreign key (WorkspaceId)
      references dbo.Workspace (WorkspaceId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationForeWriter') and o.name = 'FKProjectId_FW')
alter table dbo.ProjectConfigurationForeWriter
   add constraint FKProjectId_FW foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationMGA') and o.name = 'FKProjectId_MGA')
alter table dbo.ProjectConfigurationMGA
   add constraint FKProjectId_MGA foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectForewriterExpectedScope') and o.name = 'FKProject_ExpScope')
alter table dbo.ProjectForewriterExpectedScope
   add constraint FKProject_ExpScope foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationARC') and o.name = 'FKPLTHeaderId_ARC')
alter table dbo.PublicationARC
   add constraint FKPLTHeaderId_ARC foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationPricingForeWriter') and o.name = 'FKPLTHeaderId_FW')
alter table dbo.PublicationPricingForeWriter
   add constraint FKPLTHeaderId_FW foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationPricingxAct') and o.name = 'FKPLTHeaderId_xAct')
alter table dbo.PublicationPricingxAct
   add constraint FKPLTHeaderId_xAct foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.SourceExposureSummaryItem') and o.name = 'FKGlobalExposureViewSummaryId_SourceExposureSummaryItem')
alter table dbo.SourceExposureSummaryItem
   add constraint FKGlobalExposureViewSummaryId_SourceExposureSummaryItem foreign key (GlobalExposureViewSummaryId)
      references dbo.GlobalExposureViewSummary (GlobalExposureViewSummaryId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.SummaryStatisticsDetail') and o.name = 'FKPLTHeaderId_SummStat')
alter table dbo.SummaryStatisticsDetail
   add constraint FKPLTHeaderId_SummStat foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

