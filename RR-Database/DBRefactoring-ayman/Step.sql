USE [RiskRevealPOC]
GO

/****** Object:  Table [dbo].[Step]    Script Date: 4/16/2020 4:28:58 AM ******/
DROP TABLE [dbo].[Step]
GO

/****** Object:  Table [dbo].[Step]    Script Date: 4/16/2020 4:28:58 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Step](
	[stepId] [int] IDENTITY(1,1) NOT NULL,
	[finishedDate] [datetime2](7) NULL,
	[startedDate] [datetime2](7) NULL,
	[status] [varchar](255) NULL,
	[stepName] [varchar](255) NULL,
	[stepOrder] [int] NULL,
	[taskId] [bigint] NULL,
	[submittedDate] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[stepId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Step]  WITH CHECK ADD  CONSTRAINT [FKtaskId_Step] FOREIGN KEY([taskId])
REFERENCES [dbo].Task ([taskId])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[Step] CHECK CONSTRAINT [FKtaskId_Step]
GO
