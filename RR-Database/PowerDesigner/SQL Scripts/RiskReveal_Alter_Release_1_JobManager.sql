/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2017                    */
/* Created on:     05.05.2020 09:06:37                          */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Job') and o.name = 'FKUserId_Job')
alter table dbo.Job
   drop constraint FKUserId_Job
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.JobParams') and o.name = 'FKJobId_JobParams')
alter table dbo.JobParams
   drop constraint FKJobId_JobParams
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Step') and o.name = 'FKtaskId_Step')
alter table dbo.Step
   drop constraint FKtaskId_Step
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Task') and o.name = 'FKJobId_Task')
alter table dbo.Task
   drop constraint FKJobId_Task
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.TaskParams') and o.name = 'FKTaskId_TaskParams')
alter table dbo.TaskParams
   drop constraint FKTaskId_TaskParams
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.Job')
            and   name  = 'PK__Job__056690C2A06DA0CB'
            and   indid > 0
            and   indid < 255)
alter table dbo.Job
   drop constraint PK__Job__056690C2A06DA0CB
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_Job')
            and   type = 'U')
   drop table dbo.tmp_Job
go

execute sp_rename Job, tmp_Job
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.Step')
            and   name  = 'PK__Step__4E25C21D12E6D804'
            and   indid > 0
            and   indid < 255)
alter table dbo.Step
   drop constraint PK__Step__4E25C21D12E6D804
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_Step')
            and   type = 'U')
   drop table dbo.tmp_Step
go

execute sp_rename Step, tmp_Step
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('dbo.Task')
            and   name  = 'PK__Task__7C6949B1BD153D74'
            and   indid > 0
            and   indid < 255)
alter table dbo.Task
   drop constraint PK__Task__7C6949B1BD153D74
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.tmp_Task')
            and   type = 'U')
   drop table dbo.tmp_Task
go

execute sp_rename Task, tmp_Task
go

/*==============================================================*/
/* Table: Job                                                   */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Job')
            and   type = 'U')
create table dbo.Job (
   JobId                bigint               identity(1, 1),
   SubmittedDate        datetime2(7)         null,
   Priority             int                  null,
   Status               varchar(25)          collate SQL_Latin1_General_CP1_CI_AS null,
   StartedDate          datetime2(7)         null,
   FinishedDate         datetime2(7)         null,
   JobTypeCode          varchar(30)          collate SQL_Latin1_General_CP1_CI_AS null,
   JobTypeDesc          varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   UserId               bigint               not null,
   constraint PK__Job__056690C2A06DA0CB primary key (JobId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.Job on
go

insert into dbo.Job (JobId, SubmittedDate, Priority, Status, StartedDate, FinishedDate, JobTypeCode, JobTypeDesc, UserId)
select JobId, SubmittedDate, Priority, Status, StartedDate, FinishedDate, JobTypeCode, JobTypeDesc, UserId
from dbo.tmp_Job
go

set identity_insert dbo.Job off
go

/*==============================================================*/
/* Table: Step                                                  */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Step')
            and   type = 'U')
create table dbo.Step (
   stepId               int                  identity(1, 1),
   finishedDate         datetime2(7)         null,
   startedDate          datetime2(7)         null,
   status               varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   stepName             varchar(255)         collate SQL_Latin1_General_CP1_CI_AS null,
   stepOrder            int                  null,
   taskId               bigint               not null,
   submittedDate        datetime2(7)         null,
   constraint PK__Step__4E25C21D12E6D804 primary key (stepId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.Step on
go

insert into dbo.Step (stepId, finishedDate, startedDate, status, stepName, stepOrder, taskId, submittedDate)
select stepId, finishedDate, startedDate, status, stepName, stepOrder, taskId, submittedDate
from dbo.tmp_Step
go

set identity_insert dbo.Step off
go

/*==============================================================*/
/* Table: Task                                                  */
/*==============================================================*/
if not exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Task')
            and   type = 'U')
create table dbo.Task (
   TaskId               bigint               identity(1, 1),
   JobExecutionId       bigint               null,
   JobId                bigint               not null,
   TaskType             varchar(30)          collate SQL_Latin1_General_CP1_CI_AS null,
   Status               varchar(20)          collate SQL_Latin1_General_CP1_CI_AS null,
   Priority             int                  null,
   SubmittedDate        datetime2(7)         null,
   StartedDate          datetime2(7)         null,
   FinishedDate         datetime2(7)         null,
   constraint PK__Task__7C6949B1BD153D74 primary key (TaskId)
         on "PRIMARY"
)
on "PRIMARY"
go

set identity_insert dbo.Task on
go

insert into dbo.Task (TaskId, JobExecutionId, JobId, TaskType, Status, Priority, SubmittedDate, StartedDate, FinishedDate)
select TaskId, JobExecutionId, JobId, TaskType, Status, Priority, SubmittedDate, StartedDate, FinishedDate
from dbo.tmp_Task
go

set identity_insert dbo.Task off
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Job') and o.name = 'FKUserId_Job')
alter table dbo.Job
   add constraint FKUserId_Job foreign key (UserId)
      references dbo.UserRR (UserId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.JobParams') and o.name = 'FKJobId_JobParams')
alter table dbo.JobParams
   add constraint FKJobId_JobParams foreign key (JobId)
      references dbo.Job (JobId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Step') and o.name = 'FKtaskId_Step')
alter table dbo.Step
   add constraint FKtaskId_Step foreign key (taskId)
      references dbo.Task (TaskId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Task') and o.name = 'FKJobId_Task')
alter table dbo.Task
   add constraint FKJobId_Task foreign key (JobId)
      references dbo.Job (JobId)
         on delete cascade
go

if not exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.TaskParams') and o.name = 'FKTaskId_TaskParams')
alter table dbo.TaskParams
   add constraint FKTaskId_TaskParams foreign key (TaskId)
      references dbo.Task (TaskId)
         on delete cascade
go

