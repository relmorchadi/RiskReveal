/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2017                    */
/* Created on:     05.05.2020 08:39:22                          */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.sysdiagrams')
            and   type = 'U')
   drop table dbo.sysdiagrams
go

