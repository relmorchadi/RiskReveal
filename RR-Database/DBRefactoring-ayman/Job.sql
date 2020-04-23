USE [RiskRevealPOC]
GO

/****** Object:  Table [dbo].[Job]    Script Date: 4/14/2020 12:07:48 PM ******/
DROP TABLE [dbo].[Job]
GO

/****** Object:  Table [dbo].[Job]    Script Date: 4/14/2020 12:07:48 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Job](
	[JobId] [bigint] IDENTITY(1,1) NOT NULL,
	[SubmittedDate] [datetime2](7) NULL,
	[JobCode] [varchar](255) NULL,
	[Priority] [int] NULL,
	[Status] [varchar](25) NULL,
	[StartedDate] [datetime2](7) NULL,
	[FinishedDate] [datetime2](7) NULL,
	[JobTypeCode] [varchar](30) NULL,
	[JobTypeDesc] [varchar](255) NULL,
	[UserId] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[JobId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Job]  WITH CHECK ADD  CONSTRAINT [FKUserId_Job] FOREIGN KEY([UserId])
REFERENCES [dbo].UserRR ([UserId])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[Job] CHECK CONSTRAINT [FKUserId_Job]
GO

