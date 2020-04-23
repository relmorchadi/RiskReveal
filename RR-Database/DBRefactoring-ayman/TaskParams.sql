
/****** Object:  Table [dbo].[GlobalExposureView]    Script Date: 4/10/2020 1:30:49 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[TaskParams](
	[TaskParamsId] [bigint] IDENTITY(1,1) NOT NULL,
	[Entity] [int] NULL,
	[TaskId] [bigint] NOT NULL,
	[ParameterName] [varchar](150) NULL,
	[ParameterValue] [varchar](150) NULL,
PRIMARY KEY CLUSTERED 
(
	[TaskParamsId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[TaskParams]  WITH CHECK ADD  CONSTRAINT [FKTaskId_TaskParams] FOREIGN KEY([TaskId])
REFERENCES [dbo].Task ([TaskId])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[TaskParams] CHECK CONSTRAINT [FKTaskId_TaskParams]
GO


