USE [RiskRevealPOC]
GO

/****** Object:  Table [dbo].[ModelPortfolio]    Script Date: 4/7/2020 4:11:11 PM ******/
DROP TABLE [dbo].[ModelPortfolio]
GO

/****** Object:  Table [dbo].[ModelPortfolio]    Script Date: 4/7/2020 3:54:58 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ModelPortfolio](
	[ModelPortfolioId] [bigint] NOT NULL,

	[Entity] [int] NULL,
	[ProjectId] [bigint] NULL,
	[ProjectImportRunId] [bigint] NULL,

	[SourcePortfolioId] [bigint] NULL, /***** change name in the code*****/
	[DataSourceId] [bigint] NULL,
	[DataSourceName] [varchar](255) NULL,
	[ModelPortfolioName] [varchar](150) NULL,/***** change name in the code *****/
	[ModelPortfolioType] [varchar](10) NULL,/***** change name in the code *****/
	[ModelPortfolioDescription] [varchar](255) NULL,/***** change name in the code *****/
	[DivisionNumber] [int] NULL,/***** change name in the code and type in data model*****/
	[Currency] [varchar](3) NULL,
	[ExchangeRate] numeric(20,8) NULL,/***** change type in the code *****/
	[ImportStatus] [varchar](15) NULL,/***** change size in the data model *****/
	[ImportLocationLevel] [bit] NULL,/***** change type in the code and data model *****/
	[ExposureLevel] [bit] NULL,/***** change type in the code and data model *****/
	[ExposedLocationPerils] [varchar](50) NULL, /***** Remove column ExposedPerils from the data model*****/
	[SourceModellingVendor] [varchar](50) NULL,
	[SourceModellingSystem] [varchar](50) NULL,
	[SourceModellingSystemInstance] [varchar](50) NULL,
	[SourceModellingSystemVersion] [int] NULL, /***** change type in data model to int *****/
	[ImportedDate] [datetime2](7) NULL, /***** data model should use this date type *****/
	[CreationDate] [datetime2](7) NULL,/***** data model should use this date type *****/
	[Proportion] numeric(20,8) NULL,/***** change type in the code *****/
	[UnitMultiplier] numeric(20,8) NULL,/***** change type in the code *****/
	
	
PRIMARY KEY CLUSTERED 
(
	[ModelPortfolioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


alter table dbo.ModelPortfolio
   add constraint FKProjectID_Portfolio foreign key (ProjectId)
      references dbo.Project (ProjectId) ON DELETE CASCADE
go

alter table dbo.ModelPortfolio
   add constraint FKEntity_Portfolio foreign key (Entity)
      references dbo.Entity (EntityId)
go

alter table dbo.ModelPortfolio
   add constraint FKProjectImportRunId_Portfolio foreign key (ProjectImportRunId)
      references dbo.ProjectImportRun (ProjectImportRunId)
go

GO


