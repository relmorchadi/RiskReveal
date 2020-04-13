USE [RiskRevealPOC]
GO

/****** Object:  Table [dbo].[ExposureSummaryData]    Script Date: 4/8/2020 1:39:32 PM ******/
DROP TABLE [dbo].[ExposureSummaryData]
GO

/****** Object:  Table [dbo].[ExposureSummaryData]    Script Date: 4/8/2020 1:39:32 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ExposureSummaryData](
	[ExposureSummaryDataId] [bigint] IDENTITY(1,1) NOT NULL,
	[Admin1Code] [varchar](10) NULL,
	[AnalysisRegionCode] [varchar](10) NULL,
	[ConformedCurrency] [varchar](3) NULL,
	[conformedCurrencyUSDRate] numeric(20,8) NULL, /***** change type in code *****/
	[CountryCode] [varchar](10) NULL,/***** missing from data model *****/
	[Dimension1] [varchar](50) NULL,
	[Dimension2] [varchar](50) NULL,
	[Dimension3] [varchar](50) NULL,
	[Dimension4] [varchar](50) NULL,
	[DimensionSort1] [int] NULL,
	[DimensionSort2] [int] NULL,
	[DimensionSort3] [int] NULL,
	[DimensionSort4] [int] NULL,
	[ExposureCurrency] [varchar](3) NULL,
	[ExposureCurrencyUSDRate] numeric(20,8) NULL, /***** change type in code *****/
	[FinancialPerspective] [varchar](5) NULL,
	[LocationCount] numeric(19,0) NULL, /***** change type in code *****/
	[Metric] [varchar](10) NULL,
	[PerilCode] [varchar](4) NULL,
	[SourcePortfolioId] numeric(19,0) NULL, /***** change type in code *****/
	[SourcePortfolioType] [varchar](10) NULL, /***** change name in code *****/
	[RateDate] [datetime2](7) NULL,
	[RegionPerilCode] [varchar](15) NULL,
	[RegionPerilGroupCode] [varchar](15) NULL,
	[TIV] numeric(27,2) NULL, /***** change type in code *****/
	[AvgTIV] numeric(27,2) NULL, /***** change type in code *****/
	[GlobalExposureViewSummaryId] [bigint] NULL, /***** change name in code and s/proc *****/
	[ModelPortfolioId] [bigint] NULL,
	[Entity] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ExposureSummaryDataId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ExposureSummaryData]  WITH CHECK ADD CONSTRAINT [FKGlobalExposureViewSummaryId_ExposureSummaryData] FOREIGN KEY([GlobalExposureViewSummaryId])
REFERENCES [dbo].[GlobalExposureViewSummary] (GlobalExposureViewSummaryId)
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[ExposureSummaryData] CHECK CONSTRAINT [FKGlobalExposureViewSummaryId_ExposureSummaryData]
GO




ALTER TABLE [dbo].ModelPortfolio  WITH CHECK ADD CONSTRAINT [FKModelPortfolioId_ExposureSummaryData] FOREIGN KEY([ModelPortfolioId])
REFERENCES [dbo].[ModelPortfolio] ([ModelPortfolioId])
GO

ALTER TABLE [dbo].[ExposureSummaryData] CHECK CONSTRAINT [FKModelPortfolioId_ExposureSummaryData]
GO




ALTER TABLE [dbo].[ExposureSummaryData]  WITH CHECK ADD  CONSTRAINT [FKEntity_ExposureSummaryData] FOREIGN KEY([Entity])
REFERENCES [dbo].[ENTITY] ([EntityId])
GO

