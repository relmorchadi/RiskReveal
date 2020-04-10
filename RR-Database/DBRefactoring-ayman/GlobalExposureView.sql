USE [RiskRevealPOC]
GO

/****** Object:  Table [dbo].[GlobalExposureView]    Script Date: 4/7/2020 5:43:28 PM ******/
DROP TABLE [dbo].[GlobalExposureView]
GO

/****** Object:  Table [dbo].[GlobalExposureView]    Script Date: 4/7/2020 5:43:28 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[GlobalExposureView](
	[GlobalExposureViewId] [bigint] IDENTITY(1,1) NOT NULL,
	Entity int null, /***** Add this column in code *****/
	[ProjectId] [bigint] NOT NULL,
	[PeriodBasisId] [int] NULL,
	/***** Delete this column from data model and code
	[DivisionNumber] [int] NULL,
	*****/
	[GlobalExposureViewName] [varchar](255) NULL,/***** change column name in the code ****/
	[Version] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[GlobalExposureViewId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[GlobalExposureView]  WITH CHECK ADD  CONSTRAINT [FKProjectId_GlobalExposureView] FOREIGN KEY([ProjectId])
REFERENCES [dbo].[Project] ([ProjectId])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[GlobalExposureView] CHECK CONSTRAINT [FKProjectId_GlobalExposureView]
GO

ALTER TABLE [dbo].[GlobalExposureView]  WITH CHECK ADD  CONSTRAINT [FKEntity_GlobalExposureView] FOREIGN KEY([Entity])
REFERENCES [dbo].[ENTITY] ([EntityId])
GO

ALTER TABLE [dbo].[GlobalExposureView] CHECK CONSTRAINT [FKEntity_GlobalExposureView]
GO


