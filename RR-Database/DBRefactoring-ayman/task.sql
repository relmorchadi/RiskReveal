USE [RiskRevealPOC]
GO

/****** Object:  Table [dbo].[Task]    Script Date: 4/14/2020 12:05:20 PM ******/
DROP TABLE [dbo].[Task]
GO

/****** Object:  Table [dbo].[Task]    Script Date: 4/14/2020 12:05:20 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Task](
	[TaskId] [bigint] IDENTITY(1,1) NOT NULL,
	[JobExecutionId] [bigint] NULL,
	[JobId] [bigint] NULL,
	[TaskType] [varchar](30) NULL,
	/*[TaskParams] [text] NULL,*/ /***** remove this column *****/
	[Status] [varchar](20) NULL,
	[Priority] [int] NULL,
	[SubmittedDate] [datetime2](7) NULL,
	[StartedDate] [datetime2](7) NULL,
	[FinishedDate] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[TaskId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Task]  WITH CHECK ADD  CONSTRAINT [FKJobId_Task] FOREIGN KEY([JobId])
REFERENCES [dbo].Job ([JobId])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[Task] CHECK CONSTRAINT [FKJobId_Task]
GO

