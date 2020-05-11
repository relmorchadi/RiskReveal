/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2017                    */
/* Created on:     07.05.2020 13:26:45                          */
/*==============================================================*/


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
   where r.fkeyid = object_id('dbo.GlobalExposureViewSummary') and o.name = 'FKGlobalExposureViewId_GlobalExposureViewSummary')
alter table dbo.GlobalExposureViewSummary
   drop constraint FKGlobalExposureViewId_GlobalExposureViewSummary
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
   where r.fkeyid = object_id('dbo.SourceExposureSummaryItem') and o.name = 'FKGlobalExposureViewSummaryId_SourceExposureSummaryItem')
alter table dbo.SourceExposureSummaryItem
   drop constraint FKGlobalExposureViewSummaryId_SourceExposureSummaryItem
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
   where r.fkeyid = object_id('dbo.GlobalExposureViewSummary') and o.name = 'FKGlobalExposureViewId_GlobalExposureViewSummary')
alter table dbo.GlobalExposureViewSummary
   add constraint FKGlobalExposureViewId_GlobalExposureViewSummary foreign key (GlobalExposureViewId)
      references dbo.GlobalExposureView (GlobalExposureViewId)
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
   where r.fkeyid = object_id('dbo.SourceExposureSummaryItem') and o.name = 'FKGlobalExposureViewSummaryId_SourceExposureSummaryItem')
alter table dbo.SourceExposureSummaryItem
   add constraint FKGlobalExposureViewSummaryId_SourceExposureSummaryItem foreign key (GlobalExposureViewSummaryId)
      references dbo.GlobalExposureViewSummary (GlobalExposureViewSummaryId)
         on delete cascade
go

