USE [RiskRevealPOC]
GO

/****** Object:  Table [dbo].[GlobalViewSummary]    Script Date: 4/8/2020 12:57:19 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[GlobalExposureViewSummary]( /***** Change table name in s/proc and code *****/
	[GlobalExposureViewSummaryId] [bigint] IDENTITY(1,1) NOT NULL, /***** change name in code *****/
	Entity int null, /***** add in code *****/
	[EdmId] [bigint] NULL,/***** Change Type in data model *****/
	[EdmName] [varchar](255) NULL,
	[InstanceId] [varchar](255) NULL,/***** Change Type in data model *****/
	[SummaryOrder] [int] NULL,
	[SummaryTitle] [varchar](255) NULL,
	[GlobalExposureViewId] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[GlobalExposureViewSummaryId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[GlobalExposureViewSummary]  WITH CHECK ADD CONSTRAINT [FKGlobalExposureViewId_GlobalExposureViewSummary] FOREIGN KEY([GlobalExposureViewId])
REFERENCES [dbo].[GlobalExposureView] ([GlobalExposureViewId])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[GlobalExposureViewSummary] CHECK CONSTRAINT [FKGlobalExposureViewId_GlobalExposureViewSummary]
GO

ALTER TABLE [dbo].[GlobalExposureViewSummary]  WITH CHECK ADD  CONSTRAINT [FKEntity_GlobalExposureViewSummary] FOREIGN KEY([Entity])
REFERENCES [dbo].[ENTITY] ([EntityId])
GO




