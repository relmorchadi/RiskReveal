USE [RiskRevealPOC]
GO

/****** Object:  Table [dbo].[SourceExposureSummaryItem]    Script Date: 4/8/2020 2:25:29 PM ******/
DROP TABLE [dbo].[SourceExposureSummaryItem]
GO

/****** Object:  Table [dbo].[SourceExposureSummaryItem]    Script Date: 4/8/2020 2:25:29 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SourceExposureSummaryItem](/***** remove ZZ *****/
	[SourceExposureSummaryItemId] [bigint] IDENTITY(1,1) NOT NULL,
	[Entity] int NULL,
	/***** delete this column
	[ExposureSummaryId] [bigint] NULL,
	*****/
	[ExposureSummaryName] [varchar](150) NULL,
	[Admin1Code] [varchar](15) NULL,
	[AnalysisRegionCode] [varchar](15) NULL,
	[ConformedCurrency] [varchar](3) NULL,
	[ConformedCurrencyUSDRate] numeric(20,8) NULL,/***** change type in code *****/
	[CountryCode] [varchar](10) NULL,
	[Dimension1] [varchar](50) NULL,
	[Dimension2] [varchar](50) NULL,
	[Dimension3] [varchar](50) NULL,
	[Dimension4] [varchar](50) NULL,
	[DimensionSort1] [int] NULL,
	[DimensionSort2] [int] NULL,
	[DimensionSort3] [int] NULL,
	[DimensionSort4] [int] NULL,
	[EDMId] [bigint] NULL,
	[ExposureCurrency] [varchar](3) NULL,
	[ExposureCurrencyUSDRate] numeric(20,8) NULL,/***** change type in code *****/
	[FinancialPerspective] [varchar](5) NULL,
	[LocationCount] numeric(20,8) NULL,/***** change type in code *****/
	[Peril] [varchar](4) NULL,
	[PortfolioId] [bigint] NULL,
	[PortfolioType] [varchar](10) NULL,
	[FxRateVintageDate] [datetime2](7) NULL,
	[RegionPerilCode] [varchar](15) NULL,
	[RegionPerilGroupCode] [varchar](15) NULL,
	[TotalTiv] numeric(27,2) NULL,/***** change type in code *****/
	[GlobalExposureViewSummaryId] [bigint] NULL, /***** name change in code *****/
PRIMARY KEY CLUSTERED 
(
	[SourceExposureSummaryItemId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SourceExposureSummaryItem]  WITH CHECK ADD  CONSTRAINT [FKEntity_SourceExposureSummaryItem] FOREIGN KEY([Entity])
REFERENCES [dbo].[ENTITY] ([EntityId])
GO

ALTER TABLE [dbo].[SourceExposureSummaryItem]  WITH CHECK ADD  CONSTRAINT [FKGlobalExposureViewSummaryId_SourceExposureSummaryItem] FOREIGN KEY([GlobalExposureViewSummaryId])
REFERENCES [dbo].[GlobalExposureViewSummary] ([GlobalExposureViewSummaryId])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[SourceExposureSummaryItem] CHECK CONSTRAINT [FKGlobalExposureViewSummaryId_SourceExposureSummaryItem]
GO



