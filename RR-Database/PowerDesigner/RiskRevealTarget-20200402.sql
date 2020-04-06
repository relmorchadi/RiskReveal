/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2014                    */
/* Created on:     02/04/2020 13:45:47                          */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AccumulationPackage') and o.name = 'FKWorkspaceId_Accum')
alter table dbo.AccumulationPackage
   drop constraint FKWorkspaceId_Accum
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AccumulationPackageAttachedPLT') and o.name = 'FKAccumulationPackageId_PLT')
alter table dbo.AccumulationPackageAttachedPLT
   drop constraint FKAccumulationPackageId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AccumulationPackageAttachedPLT') and o.name = 'FKPLTHeaderId_Accum')
alter table dbo.AccumulationPackageAttachedPLT
   drop constraint FKPLTHeaderId_Accum
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AccumulationPackageOverrideSection') and o.name = 'FKAccumulationPackageID_Override')
alter table dbo.AccumulationPackageOverrideSection
   drop constraint FKAccumulationPackageID_Override
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('AccumulationProfile') and o.name = 'FK_ACCUMULA_REFERENCE_ENTITY')
alter table AccumulationProfile
   drop constraint FK_ACCUMULA_REFERENCE_ENTITY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('AccumulationProfileDetail') and o.name = 'FK_ACCUMULA_REFERENCE_ENTITY')
alter table AccumulationProfileDetail
   drop constraint FK_ACCUMULA_REFERENCE_ENTITY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('AccumulationProfileDetail') and o.name = 'FK_ACCUMULA_REFERENCE_ACCUMULA')
alter table AccumulationProfileDetail
   drop constraint FK_ACCUMULA_REFERENCE_ACCUMULA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('AccumulationProfileDetail') and o.name = 'FK_ACCUMULA_REFERENCE_ACCUMULA')
alter table AccumulationProfileDetail
   drop constraint FK_ACCUMULA_REFERENCE_ACCUMULA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('AccumulationRAP') and o.name = 'FK_ACCUMULA_REFERENCE_ENTITY')
alter table AccumulationRAP
   drop constraint FK_ACCUMULA_REFERENCE_ENTITY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('AccumulationRAPDetail') and o.name = 'FK_ACCUMULA_REFERENCE_ENTITY')
alter table AccumulationRAPDetail
   drop constraint FK_ACCUMULA_REFERENCE_ENTITY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('AccumulationRAPDetail') and o.name = 'FK_ACCUMULA_REFERENCE_ACCUMULA')
alter table AccumulationRAPDetail
   drop constraint FK_ACCUMULA_REFERENCE_ACCUMULA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentNode') and o.name = 'FKAdjustmentProcessingRecapId_Node')
alter table dbo.AdjustmentNode
   drop constraint FKAdjustmentProcessingRecapId_Node
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentNode') and o.name = 'FKAdjustmentThreadId_Node')
alter table dbo.AdjustmentNode
   drop constraint FKAdjustmentThreadId_Node
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentNodeProcessing') and o.name = 'FKAdjustmentNodeId_Proc')
alter table dbo.AdjustmentNodeProcessing
   drop constraint FKAdjustmentNodeId_Proc
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentOrder') and o.name = 'FKAdjustmentNodeId_Order')
alter table dbo.AdjustmentOrder
   drop constraint FKAdjustmentNodeId_Order
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AdjustmentOrder') and o.name = 'FKAdjustmentThreadId_Order')
alter table dbo.AdjustmentOrder
   drop constraint FKAdjustmentThreadId_Order
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AnalysisIncludedTargetRAP') and o.name = 'FKRRAnalysisId_IncRAP')
alter table dbo.AnalysisIncludedTargetRAP
   drop constraint FKRRAnalysisId_IncRAP
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.AnalysisIncludedTargetRAP') and o.name = 'FKTargetRAPId_IncRAP')
alter table dbo.AnalysisIncludedTargetRAP
   drop constraint FKTargetRAPId_IncRAP
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.CalibrationTemplateNode') and o.name = 'FKCalibrationTemplateHeaderId_Node')
alter table dbo.CalibrationTemplateNode
   drop constraint FKCalibrationTemplateHeaderId_Node
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.CalibrationTemplateOrder') and o.name = 'FKCalibrationTemplateHeaderId_Order')
alter table dbo.CalibrationTemplateOrder
   drop constraint FKCalibrationTemplateHeaderId_Order
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.CalibrationTemplateOrder') and o.name = 'FKCalibrationTemplateNodeId_Order')
alter table dbo.CalibrationTemplateOrder
   drop constraint FKCalibrationTemplateNodeId_Order
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.CalibrationTemplateParameter') and o.name = 'FKCalibrationTemplateNodeId_Param')
alter table dbo.CalibrationTemplateParameter
   drop constraint FKCalibrationTemplateNodeId_Param
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DashboardWidget') and o.name = 'FK_DASHBOAR_REFERENCE_ENTITY')
alter table DashboardWidget
   drop constraint FK_DASHBOAR_REFERENCE_ENTITY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DashboardWidget') and o.name = 'FK_DASHBOAR_REFERENCE_REFDASHB')
alter table DashboardWidget
   drop constraint FK_DASHBOAR_REFERENCE_REFDASHB
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DashboardWidgetColumn') and o.name = 'FK_DASHBOAR_REFERENCE_DASHBOAR')
alter table DashboardWidgetColumn
   drop constraint FK_DASHBOAR_REFERENCE_DASHBOAR
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.EPCurveHeader') and o.name = 'FKPLTHeaderId_Curve')
alter table dbo.EPCurveHeader
   drop constraint FKPLTHeaderId_Curve
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.EPCurveHeader') and o.name = 'FKRRLossDataHeaderId_Curve')
alter table dbo.EPCurveHeader
   drop constraint FKRRLossDataHeaderId_Curve
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.EventBasedAdjustmentParameter') and o.name = 'FKAdjustmentNodeId_Event')
alter table dbo.EventBasedAdjustmentParameter
   drop constraint FKAdjustmentNodeId_Event
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ExposureSummaryData') and o.name = 'FKGlobalExposureViewSummary_Data')
alter table dbo.ExposureSummaryData
   drop constraint FKGlobalExposureViewSummary_Data
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.FileBasedImportConfig') and o.name = 'FKProjectImportRunId_FileBased')
alter table dbo.FileBasedImportConfig
   drop constraint FKProjectImportRunId_FileBased
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.FileImportSourceResult') and o.name = 'FKFileBasedImportConfigId_Source')
alter table dbo.FileImportSourceResult
   drop constraint FKFileBasedImportConfigId_Source
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GenericAdjustmentNode') and o.name = 'FKGenericAdjustmentSetId_Node')
alter table dbo.GenericAdjustmentNode
   drop constraint FKGenericAdjustmentSetId_Node
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GenericAdjustmentParameter') and o.name = 'FKGenericAdjustmentNodeId_Param')
alter table dbo.GenericAdjustmentParameter
   drop constraint FKGenericAdjustmentNodeId_Param
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GenericSetAdjustmentOrder') and o.name = 'FKGenericAdjustmentNodeId_Order')
alter table dbo.GenericSetAdjustmentOrder
   drop constraint FKGenericAdjustmentNodeId_Order
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GenericSetAdjustmentOrder') and o.name = 'FKGenericAdjustmentSetId_Order')
alter table dbo.GenericSetAdjustmentOrder
   drop constraint FKGenericAdjustmentSetId_Order
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GlobalExposureView') and o.name = 'FKProjectID_GlobalExposure')
alter table dbo.GlobalExposureView
   drop constraint FKProjectID_GlobalExposure
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.GlobalExposureViewSummary') and o.name = 'FKGlobalExposureViewID_Summary')
alter table dbo.GlobalExposureViewSummary
   drop constraint FKGlobalExposureViewID_Summary
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringCanvasLayout') and o.name = 'FKInuringPackageId_Canvas')
alter table dbo.InuringCanvasLayout
   drop constraint FKInuringPackageId_Canvas
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringContractLayer') and o.name = 'FKInuringContractNodeId_Layer')
alter table dbo.InuringContractLayer
   drop constraint FKInuringContractNodeId_Layer
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringContractLayerParam') and o.name = 'FKInuringContractLayerId_Param')
alter table dbo.InuringContractLayerParam
   drop constraint FKInuringContractLayerId_Param
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringContractLayerPerilLimit') and o.name = 'FKInuringContractLayerId_Peril')
alter table dbo.InuringContractLayerPerilLimit
   drop constraint FKInuringContractLayerId_Peril
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringContractLayerReinstatementDetail') and o.name = 'FKInuringContractLayerId_Reinst')
alter table dbo.InuringContractLayerReinstatementDetail
   drop constraint FKInuringContractLayerId_Reinst
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringContractNode') and o.name = 'FKInuringPackageId_Node')
alter table dbo.InuringContractNode
   drop constraint FKInuringPackageId_Node
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringEdge') and o.name = 'FKInuringPackageId_Edge')
alter table dbo.InuringEdge
   drop constraint FKInuringPackageId_Edge
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('InuringFinalAttachedPLT') and o.name = 'FKInuringFinalNodeId_PLT')
alter table InuringFinalAttachedPLT
   drop constraint FKInuringFinalNodeId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('InuringFinalAttachedPLT') and o.name = 'FKPLTHeaderId_InuringFinal')
alter table InuringFinalAttachedPLT
   drop constraint FKPLTHeaderId_InuringFinal
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringFinalNode') and o.name = 'FKInuringPackageId_Final')
alter table dbo.InuringFinalNode
   drop constraint FKInuringPackageId_Final
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringInputAttachedPLT') and o.name = 'FKInuringInputNodeId_PLT')
alter table dbo.InuringInputAttachedPLT
   drop constraint FKInuringInputNodeId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringInputAttachedPLT') and o.name = 'FKPLTHeaderId_Inuring')
alter table dbo.InuringInputAttachedPLT
   drop constraint FKPLTHeaderId_Inuring
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringInputNode') and o.name = 'FKInuringpackageId_Input')
alter table dbo.InuringInputNode
   drop constraint FKInuringpackageId_Input
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('InuringNote') and o.name = 'FKInuringPackageId_Note')
alter table InuringNote
   drop constraint FKInuringPackageId_Note
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringPackage') and o.name = 'FKWorkspaceId_Inuring')
alter table dbo.InuringPackage
   drop constraint FKWorkspaceId_Inuring
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringPackageProcessing') and o.name = 'FKInuringPackageId_Proc')
alter table dbo.InuringPackageProcessing
   drop constraint FKInuringPackageId_Proc
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.InuringPackageProcessingExchangeRate') and o.name = 'FKInuringPackageProcessingId_Rate')
alter table dbo.InuringPackageProcessingExchangeRate
   drop constraint FKInuringPackageProcessingId_Rate
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.LossDataHeader') and o.name = 'FKRRAnalysisId_RRLoss')
alter table dbo.LossDataHeader
   drop constraint FKRRAnalysisId_RRLoss
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelAnalysis') and o.name = 'FKProjectId_Analysis')
alter table dbo.ModelAnalysis
   drop constraint FKProjectId_Analysis
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelAnalysis') and o.name = 'FKProjectImportRunId_RRAnalysis')
alter table dbo.ModelAnalysis
   drop constraint FKProjectImportRunId_RRAnalysis
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ModelPortfolio') and o.name = 'FKProjectID_Portfolio')
alter table dbo.ModelPortfolio
   drop constraint FKProjectID_Portfolio
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.OmegaCountryPeril') and o.name = 'FKContractSectionId_CP')
alter table dbo.OmegaCountryPeril
   drop constraint FKContractSectionId_CP
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.OmegaReinstatement') and o.name = 'FKContractSectionId_Reins')
alter table dbo.OmegaReinstatement
   drop constraint FKContractSectionId_Reins
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.OmegaSection') and o.name = 'FKContractId_Section')
alter table dbo.OmegaSection
   drop constraint FKContractId_Section
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.OmegaTermsAndCondition') and o.name = 'FKContractSectionId_TnC')
alter table dbo.OmegaTermsAndCondition
   drop constraint FKContractSectionId_TnC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.OmegaTreaty') and o.name = 'FKClientId_Contract')
alter table dbo.OmegaTreaty
   drop constraint FKClientId_Contract
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKCurrencyCode_PLT')
alter table dbo.PLTHeader
   drop constraint FKCurrencyCode_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKRRAnalysisId_PLT')
alter table dbo.PLTHeader
   drop constraint FKRRAnalysisId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKRegionPerilId_PLT')
alter table dbo.PLTHeader
   drop constraint FKRegionPerilId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeader') and o.name = 'FKTargetRAPId_PLT')
alter table dbo.PLTHeader
   drop constraint FKTargetRAPId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeaderTag') and o.name = 'FKPLTHeaderId_Tag')
alter table dbo.PLTHeaderTag
   drop constraint FKPLTHeaderId_Tag
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTHeaderTag') and o.name = 'FKUserTagId_PLT')
alter table dbo.PLTHeaderTag
   drop constraint FKUserTagId_PLT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTPricingLossEngine') and o.name = 'FKPLTPricingId_Engine')
alter table dbo.PLTPricingLossEngine
   drop constraint FKPLTPricingId_Engine
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTPricingLossEngine') and o.name = 'FKPLTPricingSectionId_Engine')
alter table dbo.PLTPricingLossEngine
   drop constraint FKPLTPricingSectionId_Engine
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTPricingMinimumGrain') and o.name = 'FKPLTPricingId_RegionPeril')
alter table dbo.PLTPricingMinimumGrain
   drop constraint FKPLTPricingId_RegionPeril
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTPricingMinimumGrain') and o.name = 'FKPLTPricingLossEngineId_RegionPeril')
alter table dbo.PLTPricingMinimumGrain
   drop constraint FKPLTPricingLossEngineId_RegionPeril
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTPricingMinimumGrain') and o.name = 'FKPLTPricingSectionId_RegionPeril')
alter table dbo.PLTPricingMinimumGrain
   drop constraint FKPLTPricingSectionId_RegionPeril
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PLTPricingSection') and o.name = 'FKPLTPricingId_Section')
alter table dbo.PLTPricingSection
   drop constraint FKPLTPricingId_Section
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Project') and o.name = 'FKProjectImportRunId_Project')
alter table dbo.Project
   drop constraint FKProjectImportRunId_Project
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Project') and o.name = 'FKRLDataSourceId_Proj')
alter table dbo.Project
   drop constraint FKRLDataSourceId_Proj
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.Project') and o.name = 'FKWorkspaceId_Proj')
alter table dbo.Project
   drop constraint FKWorkspaceId_Proj
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationForeWriter') and o.name = 'FKProjectId_FW')
alter table dbo.ProjectConfigurationForeWriter
   drop constraint FKProjectId_FW
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationForeWriterContract') and o.name = 'FKprojectConfigurationForeWriterId_C')
alter table dbo.ProjectConfigurationForeWriterContract
   drop constraint FKprojectConfigurationForeWriterId_C
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationForeWriterDivision') and o.name = 'FKProjectConfigurationForeWriterContractId')
alter table dbo.ProjectConfigurationForeWriterDivision
   drop constraint FKProjectConfigurationForeWriterContractId
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationForeWriterFiles') and o.name = 'FKprojectConfigurationForeWriterId_F')
alter table dbo.ProjectConfigurationForeWriterFiles
   drop constraint FKprojectConfigurationForeWriterId_F
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectConfigurationMGA') and o.name = 'FKProjectId_MGA')
alter table dbo.ProjectConfigurationMGA
   drop constraint FKProjectId_MGA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ProjectForewriterExpectedScope') and o.name = 'FKProject_ExpScope')
alter table dbo.ProjectForewriterExpectedScope
   drop constraint FKProject_ExpScope
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationARC') and o.name = 'FKPLTHeaderId_ARC')
alter table dbo.PublicationARC
   drop constraint FKPLTHeaderId_ARC
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationPricingForeWriter') and o.name = 'FKPLTHeaderId_FW')
alter table dbo.PublicationPricingForeWriter
   drop constraint FKPLTHeaderId_FW
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.PublicationPricingxAct') and o.name = 'FKPLTHeaderId_xAct')
alter table dbo.PublicationPricingxAct
   drop constraint FKPLTHeaderId_xAct
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLAnalysisProfileRegion') and o.name = 'FKModelAnalysisID')
alter table dbo.RLAnalysisProfileRegion
   drop constraint FKModelAnalysisID
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLImportDivisionSelection') and o.name = 'FKRLImportSelection_Division')
alter table dbo.RLImportDivisionSelection
   drop constraint FKRLImportSelection_Division
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLImportFinancialPerspectiveSelection') and o.name = 'FKRLImportSelectionId_FinPer')
alter table dbo.RLImportFinancialPerspectiveSelection
   drop constraint FKRLImportSelectionId_FinPer
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLImportSelection') and o.name = 'FKProjectId_SourceResult')
alter table dbo.RLImportSelection
   drop constraint FKProjectId_SourceResult
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLImportSelection') and o.name = 'FKRLAnalysisId_SourceResult')
alter table dbo.RLImportSelection
   drop constraint FKRLAnalysisId_SourceResult
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLImportTargetRAPSelection') and o.name = 'FKRLImportSectionID_TargetRAP')
alter table dbo.RLImportTargetRAPSelection
   drop constraint FKRLImportSectionID_TargetRAP
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLModelAnalysis') and o.name = 'FKProjectId_RL')
alter table dbo.RLModelAnalysis
   drop constraint FKProjectId_RL
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLModelAnalysis') and o.name = 'FKRLModelDataSourceId_Analysis')
alter table dbo.RLModelAnalysis
   drop constraint FKRLModelDataSourceId_Analysis
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLPortfolio') and o.name = 'FKRLPortfolioID_Source')
alter table dbo.RLPortfolio
   drop constraint FKRLPortfolioID_Source
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLPortfolioAnalysisRegion') and o.name = 'FKRLPortfolioID_Region')
alter table dbo.RLPortfolioAnalysisRegion
   drop constraint FKRLPortfolioID_Region
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLPortfolioSelection') and o.name = 'FKRLPortfolioID_Secect')
alter table dbo.RLPortfolioSelection
   drop constraint FKRLPortfolioID_Secect
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RLSourceEPHeader') and o.name = 'FKModelAnalysisID_EP')
alter table dbo.RLSourceEPHeader
   drop constraint FKModelAnalysisID_EP
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefAnalysisRegion') and o.name = 'FKEnity_AnaReg')
alter table dbo.RefAnalysisRegion
   drop constraint FKEnity_AnaReg
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefAnalysisRegionMapping') and o.name = 'FKEntity_RegMap')
alter table dbo.RefAnalysisRegionMapping
   drop constraint FKEntity_RegMap
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefDefaultReturnPeriod') and o.name = 'FKEntity_ReturnPeriod')
alter table dbo.RefDefaultReturnPeriod
   drop constraint FKEntity_ReturnPeriod
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefExchangeRate') and o.name = 'FKEntity_ExRate')
alter table dbo.RefExchangeRate
   drop constraint FKEntity_ExRate
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefFMFContractAttribute') and o.name = 'FKEntity_Attr')
alter table dbo.RefFMFContractAttribute
   drop constraint FKEntity_Attr
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefFMFContractType') and o.name = 'FKEntity_Type')
alter table dbo.RefFMFContractType
   drop constraint FKEntity_Type
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefFMFContractTypeAttributeMap') and o.name = 'FKContractAttributeId')
alter table dbo.RefFMFContractTypeAttributeMap
   drop constraint FKContractAttributeId
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefFMFContractTypeAttributeMap') and o.name = 'FKContractTypeId')
alter table dbo.RefFMFContractTypeAttributeMap
   drop constraint FKContractTypeId
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefFMFContractTypeAttributeMap') and o.name = 'FKEntity_FMF')
alter table dbo.RefFMFContractTypeAttributeMap
   drop constraint FKEntity_FMF
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefRegionPerilMapping') and o.name = 'FKEntity_RPMap')
alter table dbo.RefRegionPerilMapping
   drop constraint FKEntity_RPMap
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.RefSourceRAPMapping') and o.name = 'FKEntity_SourceRAP')
alter table dbo.RefSourceRAPMapping
   drop constraint FKEntity_SourceRAP
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ReturnPeriodBandingAdjustmentParameter') and o.name = 'FKAdjustmentNodeId_RPBand')
alter table dbo.ReturnPeriodBandingAdjustmentParameter
   drop constraint FKAdjustmentNodeId_RPBand
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.ScalingAdjustmentParameter') and o.name = 'FKAdjustmentNodeId_Scaling')
alter table dbo.ScalingAdjustmentParameter
   drop constraint FKAdjustmentNodeId_Scaling
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.SummaryStatisticHeader') and o.name = 'FKPLTHeaderId_Stat')
alter table dbo.SummaryStatisticHeader
   drop constraint FKPLTHeaderId_Stat
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.SummaryStatisticHeader') and o.name = 'FKRRLossDataHeaderId_Stat')
alter table dbo.SummaryStatisticHeader
   drop constraint FKRRLossDataHeaderId_Stat
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.SummaryStatisticsDetail') and o.name = 'FKPLTHeaderId_SummStat')
alter table dbo.SummaryStatisticsDetail
   drop constraint FKPLTHeaderId_SummStat
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.SummaryStatisticsDetail') and o.name = 'FKRRSummaryStatisticHeaderId_Detail')
alter table dbo.SummaryStatisticsDetail
   drop constraint FKRRSummaryStatisticHeaderId_Detail
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo."User"') and o.name = 'FK_USER_FKENTITY__ENTITY')
alter table dbo."User"
   drop constraint FK_USER_FKENTITY__ENTITY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UserDashboard') and o.name = 'FK_USERDASH_REFERENCE_USER')
alter table UserDashboard
   drop constraint FK_USERDASH_REFERENCE_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UserDashboardWidget') and o.name = 'FK_USERDASH_REFERENCE_USER')
alter table UserDashboardWidget
   drop constraint FK_USERDASH_REFERENCE_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UserDashboardWidget') and o.name = 'FK_USERDASH_REFERENCE_USERDASH')
alter table UserDashboardWidget
   drop constraint FK_USERDASH_REFERENCE_USERDASH
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UserDashboardWidget') and o.name = 'FK_USERDASH_REFERENCE_DASHBOAR')
alter table UserDashboardWidget
   drop constraint FK_USERDASH_REFERENCE_DASHBOAR
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UserDashboardWidgetColumns') and o.name = 'FK_USERDASH_REFERENCE_USERDASH')
alter table UserDashboardWidgetColumns
   drop constraint FK_USERDASH_REFERENCE_USERDASH
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('UserDashboardWidgetColumns') and o.name = 'FK_USERDASH_REFERENCE_USER')
alter table UserDashboardWidgetColumns
   drop constraint FK_USERDASH_REFERENCE_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.UserPreferences') and o.name = 'FK_USERPREF_FKENTITY__ENTITY')
alter table dbo.UserPreferences
   drop constraint FK_USERPREF_FKENTITY__ENTITY
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.UserPreferences') and o.name = 'FKUserId_Pref')
alter table dbo.UserPreferences
   drop constraint FKUserId_Pref
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.UserRLDataSource') and o.name = 'FKEntity_UserRLData')
alter table dbo.UserRLDataSource
   drop constraint FKEntity_UserRLData
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.UserRole') and o.name = 'FKRoleId_UserRole')
alter table dbo.UserRole
   drop constraint FKRoleId_UserRole
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.UserRole') and o.name = 'FKUserId_UserRole')
alter table dbo.UserRole
   drop constraint FKUserId_UserRole
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.UserTablePreferences') and o.name = 'FKEntity_Pref')
alter table dbo.UserTablePreferences
   drop constraint FKEntity_Pref
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.UserTablePreferences') and o.name = 'FKUserId_Pref')
alter table dbo.UserTablePreferences
   drop constraint FKUserId_Pref
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('dbo.UserTag') and o.name = 'FKTagID')
alter table dbo.UserTag
   drop constraint FKTagID
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AccumulationPackage')
            and   type = 'U')
   drop table dbo.AccumulationPackage
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AccumulationPackageAttachedPLT')
            and   type = 'U')
   drop table dbo.AccumulationPackageAttachedPLT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AccumulationPackageOverrideSection')
            and   type = 'U')
   drop table dbo.AccumulationPackageOverrideSection
go

if exists (select 1
            from  sysobjects
           where  id = object_id('AccumulationProfile')
            and   type = 'U')
   drop table AccumulationProfile
go

if exists (select 1
            from  sysobjects
           where  id = object_id('AccumulationProfileDetail')
            and   type = 'U')
   drop table AccumulationProfileDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('AccumulationRAP')
            and   type = 'U')
   drop table AccumulationRAP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('AccumulationRAPDetail')
            and   type = 'U')
   drop table AccumulationRAPDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AdjustmentNode')
            and   type = 'U')
   drop table dbo.AdjustmentNode
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AdjustmentNodeProcessing')
            and   type = 'U')
   drop table dbo.AdjustmentNodeProcessing
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AdjustmentOrder')
            and   type = 'U')
   drop table dbo.AdjustmentOrder
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AdjustmentProcessingRecap')
            and   type = 'U')
   drop table dbo.AdjustmentProcessingRecap
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AdjustmentThread')
            and   type = 'U')
   drop table dbo.AdjustmentThread
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.AnalysisIncludedTargetRAP')
            and   type = 'U')
   drop table dbo.AnalysisIncludedTargetRAP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.CalibrationTemplateHeader')
            and   type = 'U')
   drop table dbo.CalibrationTemplateHeader
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.CalibrationTemplateNode')
            and   type = 'U')
   drop table dbo.CalibrationTemplateNode
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.CalibrationTemplateOrder')
            and   type = 'U')
   drop table dbo.CalibrationTemplateOrder
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.CalibrationTemplateParameter')
            and   type = 'U')
   drop table dbo.CalibrationTemplateParameter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Client')
            and   type = 'U')
   drop table dbo.Client
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Currency')
            and   type = 'U')
   drop table dbo.Currency
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DashboardWidget')
            and   type = 'U')
   drop table DashboardWidget
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DashboardWidgetColumn')
            and   type = 'U')
   drop table DashboardWidgetColumn
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.EPCurveHeader')
            and   type = 'U')
   drop table dbo.EPCurveHeader
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Entity')
            and   type = 'U')
   drop table dbo.Entity
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.EventBasedAdjustmentParameter')
            and   type = 'U')
   drop table dbo.EventBasedAdjustmentParameter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ExposureSummaryData')
            and   type = 'U')
   drop table dbo.ExposureSummaryData
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.FileBasedImportConfig')
            and   type = 'U')
   drop table dbo.FileBasedImportConfig
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.FileImportSourceResult')
            and   type = 'U')
   drop table dbo.FileImportSourceResult
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.GenericAdjustmentNode')
            and   type = 'U')
   drop table dbo.GenericAdjustmentNode
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.GenericAdjustmentParameter')
            and   type = 'U')
   drop table dbo.GenericAdjustmentParameter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.GenericAdjustmentSet')
            and   type = 'U')
   drop table dbo.GenericAdjustmentSet
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.GenericSetAdjustmentOrder')
            and   type = 'U')
   drop table dbo.GenericSetAdjustmentOrder
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.GlobalExposureView')
            and   type = 'U')
   drop table dbo.GlobalExposureView
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.GlobalExposureViewSummary')
            and   type = 'U')
   drop table dbo.GlobalExposureViewSummary
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringCanvasLayout')
            and   type = 'U')
   drop table dbo.InuringCanvasLayout
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringContractLayer')
            and   type = 'U')
   drop table dbo.InuringContractLayer
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringContractLayerParam')
            and   type = 'U')
   drop table dbo.InuringContractLayerParam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringContractLayerPerilLimit')
            and   type = 'U')
   drop table dbo.InuringContractLayerPerilLimit
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringContractLayerReinstatementDetail')
            and   type = 'U')
   drop table dbo.InuringContractLayerReinstatementDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringContractNode')
            and   type = 'U')
   drop table dbo.InuringContractNode
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringEdge')
            and   type = 'U')
   drop table dbo.InuringEdge
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringFilterCriteria')
            and   type = 'U')
   drop table dbo.InuringFilterCriteria
go

if exists (select 1
            from  sysobjects
           where  id = object_id('InuringFinalAttachedPLT')
            and   type = 'U')
   drop table InuringFinalAttachedPLT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringFinalNode')
            and   type = 'U')
   drop table dbo.InuringFinalNode
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringInputAttachedPLT')
            and   type = 'U')
   drop table dbo.InuringInputAttachedPLT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringInputNode')
            and   type = 'U')
   drop table dbo.InuringInputNode
go

if exists (select 1
            from  sysobjects
           where  id = object_id('InuringNote')
            and   type = 'U')
   drop table InuringNote
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringPackage')
            and   type = 'U')
   drop table dbo.InuringPackage
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringPackageProcessing')
            and   type = 'U')
   drop table dbo.InuringPackageProcessing
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.InuringPackageProcessingExchangeRate')
            and   type = 'U')
   drop table dbo.InuringPackageProcessingExchangeRate
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.LossDataHeader')
            and   type = 'U')
   drop table dbo.LossDataHeader
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ModelAnalysis')
            and   type = 'U')
   drop table dbo.ModelAnalysis
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ModelPortfolio')
            and   type = 'U')
   drop table dbo.ModelPortfolio
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.OmegaCountryPeril')
            and   type = 'U')
   drop table dbo.OmegaCountryPeril
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.OmegaReinstatement')
            and   type = 'U')
   drop table dbo.OmegaReinstatement
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.OmegaSection')
            and   type = 'U')
   drop table dbo.OmegaSection
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.OmegaTermsAndCondition')
            and   type = 'U')
   drop table dbo.OmegaTermsAndCondition
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.OmegaTreaty')
            and   type = 'U')
   drop table dbo.OmegaTreaty
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PLTHeader')
            and   type = 'U')
   drop table dbo.PLTHeader
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PLTHeaderTag')
            and   type = 'U')
   drop table dbo.PLTHeaderTag
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PLTPricing')
            and   type = 'U')
   drop table dbo.PLTPricing
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PLTPricingLossEngine')
            and   type = 'U')
   drop table dbo.PLTPricingLossEngine
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PLTPricingMinimumGrain')
            and   type = 'U')
   drop table dbo.PLTPricingMinimumGrain
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PLTPricingSection')
            and   type = 'U')
   drop table dbo.PLTPricingSection
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Project')
            and   type = 'U')
   drop table dbo.Project
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ProjectConfigurationForeWriter')
            and   type = 'U')
   drop table dbo.ProjectConfigurationForeWriter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ProjectConfigurationForeWriterContract')
            and   type = 'U')
   drop table dbo.ProjectConfigurationForeWriterContract
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ProjectConfigurationForeWriterDivision')
            and   type = 'U')
   drop table dbo.ProjectConfigurationForeWriterDivision
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ProjectConfigurationForeWriterFiles')
            and   type = 'U')
   drop table dbo.ProjectConfigurationForeWriterFiles
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ProjectConfigurationMGA')
            and   type = 'U')
   drop table dbo.ProjectConfigurationMGA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ProjectForewriterExpectedScope')
            and   type = 'U')
   drop table dbo.ProjectForewriterExpectedScope
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ProjectImportRun')
            and   type = 'U')
   drop table dbo.ProjectImportRun
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PublicationARC')
            and   type = 'U')
   drop table dbo.PublicationARC
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PublicationPricingForeWriter')
            and   type = 'U')
   drop table dbo.PublicationPricingForeWriter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.PublicationPricingxAct')
            and   type = 'U')
   drop table dbo.PublicationPricingxAct
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLAnalysisProfileRegion')
            and   type = 'U')
   drop table dbo.RLAnalysisProfileRegion
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLDataSource')
            and   type = 'U')
   drop table dbo.RLDataSource
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLImportDivisionSelection')
            and   type = 'U')
   drop table dbo.RLImportDivisionSelection
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLImportFinancialPerspectiveSelection')
            and   type = 'U')
   drop table dbo.RLImportFinancialPerspectiveSelection
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLImportSelection')
            and   type = 'U')
   drop table dbo.RLImportSelection
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLImportTargetRAPSelection')
            and   type = 'U')
   drop table dbo.RLImportTargetRAPSelection
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLModelAnalysis')
            and   type = 'U')
   drop table dbo.RLModelAnalysis
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLPortfolio')
            and   type = 'U')
   drop table dbo.RLPortfolio
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLPortfolioAnalysisRegion')
            and   type = 'U')
   drop table dbo.RLPortfolioAnalysisRegion
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLPortfolioSelection')
            and   type = 'U')
   drop table dbo.RLPortfolioSelection
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RLSourceEPHeader')
            and   type = 'U')
   drop table dbo.RLSourceEPHeader
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefAnalysisRegion')
            and   type = 'U')
   drop table dbo.RefAnalysisRegion
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefAnalysisRegionMapping')
            and   type = 'U')
   drop table dbo.RefAnalysisRegionMapping
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RefDashboardWidgetType')
            and   type = 'U')
   drop table RefDashboardWidgetType
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefDefaultReturnPeriod')
            and   type = 'U')
   drop table dbo.RefDefaultReturnPeriod
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefExchangeRate')
            and   type = 'U')
   drop table dbo.RefExchangeRate
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefFMFContractAttribute')
            and   type = 'U')
   drop table dbo.RefFMFContractAttribute
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefFMFContractType')
            and   type = 'U')
   drop table dbo.RefFMFContractType
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefFMFContractTypeAttributeMap')
            and   type = 'U')
   drop table dbo.RefFMFContractTypeAttributeMap
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RefFWRegionPerilGranularity')
            and   type = 'U')
   drop table RefFWRegionPerilGranularity
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefRegionPerilMapping')
            and   type = 'U')
   drop table dbo.RefRegionPerilMapping
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RefSourceRAPMapping')
            and   type = 'U')
   drop table dbo.RefSourceRAPMapping
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.RegionPeril')
            and   type = 'U')
   drop table dbo.RegionPeril
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ReturnPeriodBandingAdjustmentParameter')
            and   type = 'U')
   drop table dbo.ReturnPeriodBandingAdjustmentParameter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Role')
            and   type = 'U')
   drop table dbo.Role
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.ScalingAdjustmentParameter')
            and   type = 'U')
   drop table dbo.ScalingAdjustmentParameter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.SourceExposureSummaryItem')
            and   type = 'U')
   drop table dbo.SourceExposureSummaryItem
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.SummaryStatisticHeader')
            and   type = 'U')
   drop table dbo.SummaryStatisticHeader
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.SummaryStatisticsDetail')
            and   type = 'U')
   drop table dbo.SummaryStatisticsDetail
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Tag')
            and   type = 'U')
   drop table dbo.Tag
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.TargetRAP')
            and   type = 'U')
   drop table dbo.TargetRAP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo."User"')
            and   type = 'U')
   drop table dbo."User"
go

if exists (select 1
            from  sysobjects
           where  id = object_id('UserDashboard')
            and   type = 'U')
   drop table UserDashboard
go

if exists (select 1
            from  sysobjects
           where  id = object_id('UserDashboardWidget')
            and   type = 'U')
   drop table UserDashboardWidget
go

if exists (select 1
            from  sysobjects
           where  id = object_id('UserDashboardWidgetColumns')
            and   type = 'U')
   drop table UserDashboardWidgetColumns
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.UserPreferences')
            and   type = 'U')
   drop table dbo.UserPreferences
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.UserRLDataSource')
            and   type = 'U')
   drop table dbo.UserRLDataSource
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.UserRole')
            and   type = 'U')
   drop table dbo.UserRole
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.UserTablePreferences')
            and   type = 'U')
   drop table dbo.UserTablePreferences
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.UserTag')
            and   type = 'U')
   drop table dbo.UserTag
go

if exists (select 1
            from  sysobjects
           where  id = object_id('dbo.Workspace')
            and   type = 'U')
   drop table dbo.Workspace
go

/*==============================================================*/
/* Table: AccumulationPackage                                   */
/*==============================================================*/
create table dbo.AccumulationPackage (
   AccumulationPackageId numeric(19,0)        not null,
   Entity               int                  null,
   WorkspaceId          numeric(19,0)        null,
   CreatedOn            datetime             null,
   CreatedBy            int                  null,
   LastModifiedOn       datetime             null,
   LastModifiedBy       int                  null,
   PublishedOn          datetime             null,
   PublishedBy          int                  null,
   AccumulationPackageVersion int                  null,
   AccumulationPackageStatus varchar(15)          null,
   AccumulationPackageNarrative varchar(255)         null,
   constraint PK_ACCUMULATIONPACKAGE primary key (AccumulationPackageId)
)
go

/*==============================================================*/
/* Table: AccumulationPackageAttachedPLT                        */
/*==============================================================*/
create table dbo.AccumulationPackageAttachedPLT (
   AccumulationPackageAttachedPLTid numeric(19,0)        not null,
   Entity               int                  null,
   AccumulationPackageId numeric(19,0)        null,
   PLTHeaderId          numeric(19,0)        null,
   ContractSectionId    varchar(25)          null,
   constraint PK_ACCUMULATIONPACKAGEATTACHED primary key (AccumulationPackageAttachedPLTid)
)
go

/*==============================================================*/
/* Table: AccumulationPackageOverrideSection                    */
/*==============================================================*/
create table dbo.AccumulationPackageOverrideSection (
   AccumulationPackageOverrideSectionId numeric(19,0)        not null,
   Entity               int                  null,
   AccumulationPackageId numeric(19,0)        null,
   ContractSectionId    varchar(25)          null,
   MinimumGrainRegionPerilCode varchar(10)          null,
   AccumulationRAPCode  varchar(50)          null,
   OverrideBasisCode    varchar(255)         null,
   OverrideBasisNarrative varchar(255)         null,
   constraint PK_ACCUMULATIONPACKAGEOVERRIDE primary key (AccumulationPackageOverrideSectionId)
)
go

/*==============================================================*/
/* Table: AccumulationProfile                                   */
/*==============================================================*/
create table AccumulationProfile (
   AccumulationProfileId numeric(19,0)        not null,
   Entity               int                  null,
   ARCImportDateTime    date                 null,
   IsDefault            tinyint              null,
   AccumulationDescription varchar(5000)        null,
   AccumulationName     varchar(255)         null,
   IsActive             tinyint              null,
   CreateDate           date                 null,
   IsMandatory          tinyint              null,
   ModifiedDate         date                 null,
   CreateUser           int                  null,
   ModeifiedUser        int                  null,
   LastUpdateARC        datetime2(0)         null,
   LastUpdateCatDomain  datetime2(0)         null,
   LastSyncRunCatDomain datetime2(0)         null,
   constraint PK_ACCUMULATIONPROFILE primary key (AccumulationProfileId)
)
go

/*==============================================================*/
/* Table: AccumulationProfileDetail                             */
/*==============================================================*/
create table AccumulationProfileDetail (
   AccumulationProfileDetailID int                  not null,
   Entity               int                  null,
   AccumulationProfileId numeric(19,0)        null,
   MinimumGrainCodeISO2 varchar(10)          null,
   MinimumGrainCodeRiskReveal varchar(10)          null,
   AccumulationRAPId    int                  null,
   BaseAdjustments      char(1)              null,
   DefaultAdjustments   char(1)              null,
   AnalystAdjustments   char(1)              null,
   ClientAdjustmentts   char(1)              null,
   EffectiveFrom        date                 null,
   EffectiveTo          date                 null,
   IsActive             tinyint              null,
   LastUpdatedARC       datetime2(0)         null,
   LastUpdatedCatDomain datetime2(0)         null,
   LastSyncRunCatDomain datetime2(0)         null,
   constraint PK_ACCUMULATIONPROFILEDETAIL primary key (AccumulationProfileDetailID)
)
go

/*==============================================================*/
/* Table: AccumulationRAP                                       */
/*==============================================================*/
create table AccumulationRAP (
   AccumulationRAPId    int                  not null,
   Entity               int                  null,
   ModellingVendor      varcchar(50)         null,
   ModellingSystem      varcchar(50)         null,
   ModellingSystemVersion varcchar(50)         null,
   AccumulationRAPCode  varcchar(50)         null,
   AccumulationRAPDesc  varcchar(50)         null,
   PETId                int                  null,
   SourceRAPCode        varcchar(50)         null,
   LastUpdateRiskReveal datetime2(0)         null,
   LastUpdateCatDomain  datetime2(0)         null,
   LastSyncRunCatDomain datetime2(0)         null,
   constraint PK_ACCUMULATIONRAP primary key (AccumulationRAPId)
)
go

/*==============================================================*/
/* Table: AccumulationRAPDetail                                 */
/*==============================================================*/
create table AccumulationRAPDetail (
   AccumulationRAPDetailId int                  not null,
   Entity               int                  null,
   AccumulationRAPId    int                  null,
   TargetRAPId          int                  null,
   lastUpdateRiskReveal datetime2(0)         null,
   LastUpdateCatDomain  datetime2(0)         null,
   LastSyncRunCatDomain datetime2(0)         null,
   constraint PK_ACCUMULATIONRAPDETAIL primary key (AccumulationRAPDetailId)
)
go

/*==============================================================*/
/* Table: AdjustmentNode                                        */
/*==============================================================*/
create table dbo.AdjustmentNode (
   AdjustmentNodeId     numeric(19,0)        not null,
   Entity               int                  null,
   AdjustmentThreadId   numeric(19,0)        null,
   AdjustmentProcessingRecapId numeric(19,0)        null,
   AdjustmentNodeState  int                  null,
   AdjustmentBasisCode  varchar(255)         collate Latin1_General_CI_AS null,
   AdjustmentCategoryCode varchar(255)         collate Latin1_General_CI_AS null,
   AdjustmentTypeCode   varchar(255)         collate Latin1_General_CI_AS null,
   Capped               bit                  null,
   UserNarrative        varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__Adjustme__A9EC460E93ECA960 primary key (AdjustmentNodeId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: AdjustmentNodeProcessing                              */
/*==============================================================*/
create table dbo.AdjustmentNodeProcessing (
   AdjustmentNodeProcessingId numeric(19,0)        not null,
   Entity               int                  null,
   AdjustmentNodeId     numeric(19,0)        null,
   InputPLTId           numeric(19,0)        null,
   AdjustedPLTId        numeric(19,0)        null,
   constraint PK__Adjustme__1FA48CCFCA3E5F35 primary key (AdjustmentNodeProcessingId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: AdjustmentOrder                                       */
/*==============================================================*/
create table dbo.AdjustmentOrder (
   AdjustmentNodeOrderId numeric(19,0)        not null,
   Entity               int                  null,
   AdjustmentThreadId   numeric(19,0)        null,
   AdjustmentNodeId     numeric(19,0)        null,
   AdjustmentOrder      int                  null,
   constraint PK__Adjustme__E657A67E2524DAE2 primary key (AdjustmentNodeOrderId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: AdjustmentProcessingRecap                             */
/*==============================================================*/
create table dbo.AdjustmentProcessingRecap (
   AdjustmentProcessingRecapId numeric(19,0)        not null,
   Entity               int                  null,
   AdjustmentNodeId     numeric(19,0)        null,
   AdjustmentTypeCode   varchar(255)         collate Latin1_General_CI_AS null,
   Capped               bit                  null,
   AdjustmentParamRecap varchar(255)         collate Latin1_General_CI_AS null,
   InputFile            varchar(255)         collate Latin1_General_CI_AS null,
   OutputFile           varchar(255)         collate Latin1_General_CI_AS null,
   SummittedDate        datetime             null,
   SummittedBy          varchar(255)         collate Latin1_General_CI_AS null,
   ExecTime             time                 null,
   ExecStatus           varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__Adjustme__55C1A9ED21CFF66B primary key (AdjustmentProcessingRecapId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: AdjustmentThread                                      */
/*==============================================================*/
create table dbo.AdjustmentThread (
   AdjustmentThreadId   numeric(19,0)        not null,
   Entity               int                  null,
   ThreadIndex          numeric(19,0)        null,
   InitialPLT           numeric(19,0)        null,
   FinalPLTId           numeric(19,0)        null,
   Locked               bit                  null,
   ThreadStatus         varchar(25)          null,
   constraint PK__Adjustme__66D173B012021F53 primary key (AdjustmentThreadId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: AnalysisIncludedTargetRAP                             */
/*==============================================================*/
create table dbo.AnalysisIncludedTargetRAP (
   AnalysisIncludedTargetRAPid numeric(19,0)        not null,
   Entity               int                  null,
   ModelAnalysisId      numeric(19,0)        null,
   TargetRAPId          int                  null,
   constraint PK__RRAnalys__00A6CD73BDD3FFE2 primary key (AnalysisIncludedTargetRAPid)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: CalibrationTemplateHeader                             */
/*==============================================================*/
create table dbo.CalibrationTemplateHeader (
   CalibrationTemplateHeaderId int                  not null,
   Entity               int                  null,
   TemaplateName        varchar(125)         null,
   TemplateOwner        int                  null,
   CreatedBy            int                  null,
   CreatedDate          datetime             null,
   UpdatedBy            int                  null,
   UpdatedDate          datetime             null,
   Active               bit                  null,
   constraint PK_CALIBRATIONTEMPLATEHEADER primary key (CalibrationTemplateHeaderId)
)
go

/*==============================================================*/
/* Table: CalibrationTemplateNode                               */
/*==============================================================*/
create table dbo.CalibrationTemplateNode (
   CalibrationTemplateNodeId int                  not null,
   Entity               int                  null,
   CalibrationTemplateHeaderId int                  null,
   AdjustmentBasisCode  varchar(255)         null,
   AdjustmentCategoryCode varchar(255)         null,
   AdjustmentTypeCode   varchar(255)         null,
   Capped               bit                  null,
   UserNarrative        varchar(255)         null,
   constraint PK_CALIBRATIONTEMPLATENODE primary key (CalibrationTemplateNodeId)
)
go

/*==============================================================*/
/* Table: CalibrationTemplateOrder                              */
/*==============================================================*/
create table dbo.CalibrationTemplateOrder (
   CalibrationTemplateOrderId int                  not null,
   Entity               bit                  null,
   CalibrationTemplateHeaderId int                  null,
   CalibrationTemplateNodeId int                  null,
   CalibrationTemplateOrder int                  null,
   constraint PK_CALIBRATIONTEMPLATEORDER primary key (CalibrationTemplateOrderId)
)
go

/*==============================================================*/
/* Table: CalibrationTemplateParameter                          */
/*==============================================================*/
create table dbo.CalibrationTemplateParameter (
   CalibrationTemplateParameterId int                  not null,
   Entity               int                  null,
   CalibrationTemplateNodeId int                  null,
   ParamName            varchar(255)         null,
   ParamType            varchar(255)         null,
   ParamValue           varchar(255)         null,
   constraint PK_CALIBRATIONTEMPLATEPARAMETE primary key (CalibrationTemplateParameterId)
)
go

/*==============================================================*/
/* Table: Client                                                */
/*==============================================================*/
create table dbo.Client (
   OmegaClientId        numeric(19,0)        not null,
   IsActive             tinyint              null,
   ClientAcceptation    tinyint              null,
   ClientActivityCode   varchar(4)           null,
   ClientCedent         tinyint              null,
   ClientHierachicalLevel int                  null,
   ClientInactiveDate   datetime             null,
   ClientInactiveNatureCode varchar(5)           null,
   ClientLegalStatus    varchar(100)         null,
   ClientManagementType int                  null,
   ClientNumber         int                  null,
   ClientPayer          tinyint              null,
   ClientRetrocession   tinyint              null,
   ClientShortName      varchar(50)          null,
   ClientStatus         int                  null,
   ClientSubsidiaryCode int                  null,
   ClientSuppression    tinyint              null,
   ClientTypeCode       int                  null,
   CreationDate         date                 null,
   CreationUserCode     varchar(10)          null,
   Embargo              int                  null,
   GroupSegment         int                  null,
   HeadOfficeCity       varchar(5)           null,
   HeadOfficeCountryCode varchar(5)           null,
   HeadOfficePostalCode varchar(75)          null,
   HeadOfficeStateCode  varchar(10)          null,
   HeadOfficeStreet1    varchar(200)         null,
   HeadOfficeStreet2    varchar(200)         null,
   HoldingName          varchar(100)         null,
   HoldingNumber        int                  null,
   LastUpdateDate       date                 null,
   LastUpdateUser       varchar(10)          null,
   ReplacingClientCode  int                  null,
   ResponsibleSubsidiaryCode int                  null,
   ResponsibleUnitCode  int                  null,
   constraint PK_CLIENT primary key (OmegaClientId)
)
go

/*==============================================================*/
/* Table: Currency                                              */
/*==============================================================*/
create table dbo.Currency (
   Currencyid           int                  collate Latin1_General_CI_AS not null,
   IsActive             bit                  null,
   CurrencyCode         varchar(4)           collate Latin1_General_CI_AS null,
   CurrencyLabel        varchar(255)         collate Latin1_General_CI_AS null,
   CountryCodeId        varchar(255)         collate Latin1_General_CI_AS null,
   InceptionDate        datetime             null,
   ExpiryDate           datetime             null,
   constraint PK__Currency__147E6FE8CEDABF6F primary key (Currencyid)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: DashboardWidget                                       */
/*==============================================================*/
create table DashboardWidget (
   DashboardWidgetId    bigint               not null,
   Entity               int                  null,
   WidgetName           varchar(255)         null,
   RefDashboardWidgetTypeId bigint               null,
   WisdgetDefaultDisplayName varchar(255)         null,
   DataSourceType       varchar(25)          null,
   DataSource           varchar(255)         null,
   RowSpan              int                  null,
   ColSpan              int                  null,
   MinItemCols          int                  null,
   WidgetMode           varchar(10)          null,
   constraint PK_DASHBOARDWIDGET primary key (DashboardWidgetId)
)
go

/*==============================================================*/
/* Table: DashboardWidgetColumn                                 */
/*==============================================================*/
create table DashboardWidgetColumn (
   DashboardWidgetColumnId bigint               not null,
   DashboardWidgetId    bigint               null,
   ColumnName           varchar(255)         null,
   "Column"             int                  null,
   IsVisible            bit                  null,
   DataType             varchar(25)          null,
   MinWidth             int                  null,
   MaxWidth             int                  null,
   DefaultWidth         int                  null,
   Sorting              int                  null,
   SortType             varchar(25)          null,
   ColumnHeader         varchar(255)         null,
   DataColumnType       varchar(25)          null,
   constraint PK_DASHBOARDWIDGETCOLUMN primary key (DashboardWidgetColumnId)
)
go

/*==============================================================*/
/* Table: EPCurveHeader                                         */
/*==============================================================*/
create table dbo.EPCurveHeader (
   EPCurveHeaderId      numeric(19,0)        not null,
   Entity               int                  null,
   LossDataType         varchar(255)         collate Latin1_General_CI_AS null,
   LossDataId           numeric(19,0)        null,
   FinancialPerspective varchar(255)         collate Latin1_General_CI_AS null,
   StatisticMetric      varchar(255)         collate Latin1_General_CI_AS null,
   EPCurves             varchar(255)         collate Latin1_General_CI_AS null,
   EPCFilePath          varchar(255)         collate Latin1_General_CI_AS null,
   EPCFileName          varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__RREPCurv__61B1B8D524E819B9 primary key (EPCurveHeaderId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: Entity                                                */
/*==============================================================*/
create table dbo.Entity (
   Entity               int                  not null,
   EntityDescription    varchar(75)          null,
   constraint PK_ENTITY primary key (Entity)
)
go

/*==============================================================*/
/* Table: EventBasedAdjustmentParameter                         */
/*==============================================================*/
create table dbo.EventBasedAdjustmentParameter (
   AdjustmentParameterId numeric(19,0)        not null,
   Entity               int                  null,
   AdjustmentNodeId     numeric(19,0)        null,
   InputFilePath        varchar(255)         collate Latin1_General_CI_AS null,
   InputFileName        varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__EventBas__C4949B3286580AE8 primary key (AdjustmentParameterId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: ExposureSummaryData                                   */
/*==============================================================*/
create table dbo.ExposureSummaryData (
   ExposureSummaryDataId Numeric(19,0)        not null,
   GlobalExposureViewSummaryId Numeric(19,0)        null,
   Entity               int                  null,
   GlobalExposureSummaryViewId Numeric(19,0)        null,
   ModelPortfolioId     Numeric(19,0)        null,
   SourcePortfolioId    Numeric(19,0)        null,
   SourcePortfolioType  varchar(5)           null,
   RegionPerilGroupCode varchar(15)          null,
   RegionPerilCode      varchar(15)          null,
   PerilCode            varchar(4)           null,
   Admin1Code           varchar(10)          null,
   AnalysisRegionCode   varchar(10)          null,
   FinancialPerspective varchar(5)           null,
   LocationCount        Numeric(19,0)        null,
   ExposureCurrency     varchar(3)           null,
   ExposureCurrencyUSDRate Numeric(20,8)        null,
   RateDate             date                 null,
   Dimension1           varchar(50)          null,
   Dimension2           varchar(50)          null,
   Dimension3           varchar(50)          null,
   Dimension4           varchar(50)          null,
   DimensionSort1       varchar(5)           null,
   DimensionSort2       varchar(5)           null,
   DimensionSort3       varchar(5)           null,
   DimensionSort4       varchar(5)           null,
   ConformedCurrency    varchar(3)           null,
   ConformedCurrencyUSDRate Numeric(20,8)        null,
   Metric               varchar(10)          null,
   TIV                  Numeric(27,2)        null,
   AvgTIV               Numeric(27,2)        null,
   constraint PK_EXPOSURESUMMARYDATA primary key (ExposureSummaryDataId)
)
go

/*==============================================================*/
/* Table: FileBasedImportConfig                                 */
/*==============================================================*/
create table dbo.FileBasedImportConfig (
   FileBasedImportConfig numeric(19,0)        not null,
   ProjectImportRunId   numeric(19,0)        null,
   ProjectId            numeric(19,0)        null,
   IsImportLocked       bit                  null,
   ProjectImportSourceConfigId numeric(19,0)        null,
   SelectedSourceFileImportId int                  null,
   SelectedSourceFileImportidForRowSelect int                  null,
   SelectedFolderSourcePath varchar(500)         null,
   FolderSequence       int                  null,
   SelectedFileSourcePath varchar(500)         null,
   FileSequence         int                  null,
   ImportedAtLeastOnce  bit                  null,
   Importing            bit                  null,
   LastProjectImportRunId int                  null,
   LastUnlockDateForImport date                 null,
   LastUnlockBy         int                  null,
   constraint PK_FILEBASEDIMPORTCONFIG primary key (FileBasedImportConfig)
)
go

/*==============================================================*/
/* Table: FileImportSourceResult                                */
/*==============================================================*/
create table dbo.FileImportSourceResult (
   FileImportSourceResultId numeric(19,0)        not null,
   FileBasedImportConfig numeric(19,0)        null,
   ResultName           varchar(255)         null,
   TargetRAPCode        varchar(20)          null,
   SourceFileImportId   int                  null,
   ProjectId            int                  null,
   AvailableRegionPerils varchar(255)         null,
   TargetCurrency       varchar(3)           null,
   FinancialPerspective varchar(125)         null,
   UnitMultiplier       numeric(20,8)        null,
   Proportion           numeric(20,8)        null,
   ModelVersion         varchar(10)          null,
   SelectedRegionPerilCode varchar(15)          null,
   SourceCurrency       varchar(3)           null,
   TargetCurrencyBasis  varchar(15)          null,
   DataSource           varchar(255)         null,
   FileName             varchar(255)         null,
   ImportUser           int                  null,
   PLTSimulationPeriods int                  null,
   Grain                varchar(100)         null,
   OverrideReasonText   varchar(255)         null,
   ImportedAtLeastOnce  bit                  null,
   ImportStatus         varchar(15)          null,
   constraint PK_FILEIMPORTSOURCERESULT primary key (FileImportSourceResultId)
)
go

/*==============================================================*/
/* Table: GenericAdjustmentNode                                 */
/*==============================================================*/
create table dbo.GenericAdjustmentNode (
   GenericAdjustmentNodeId int                  not null,
   Entity               int                  null,
   GenericAdjustmentSetId int                  null,
   AdjustmentBasisCode  varchar(255)         collate Latin1_General_CI_AS null,
   AdjustmentCategoryCode varchar(255)         collate Latin1_General_CI_AS null,
   AdjustmentTypeCode   varchar(255)         collate Latin1_General_CI_AS null,
   Capped               bit                  null,
   UserNarrative        varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__GenericA__EF308433B82ED73B primary key (GenericAdjustmentNodeId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: GenericAdjustmentParameter                            */
/*==============================================================*/
create table dbo.GenericAdjustmentParameter (
   GenericAdjustmentParameterId int                  not null,
   Entity               int                  null,
   GenericAdjustmentNodeId int                  null,
   ParamName            varchar(255)         collate Latin1_General_CI_AS null,
   ParamType            varchar(255)         collate Latin1_General_CI_AS null,
   ParamValue           varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__GenericA__E505B972598A78DF primary key (GenericAdjustmentParameterId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: GenericAdjustmentSet                                  */
/*==============================================================*/
create table dbo.GenericAdjustmentSet (
   GenericAdjustmentSetId int                  not null,
   Entity               int                  null,
   UserId               int                  null,
   WorkspaceId          int                  null,
   constraint PK__GenericA__C23220ED82BBEA2F primary key (GenericAdjustmentSetId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: GenericSetAdjustmentOrder                             */
/*==============================================================*/
create table dbo.GenericSetAdjustmentOrder (
   GenericSetAdjustmentOrderId int                  not null,
   Entity               int                  null,
   GenericAdjustmentSetId int                  null,
   GenericAdjustmentNodeId int                  null,
   GenericAdjustmentOrder int                  null,
   constraint PK__Adjustme__E657A67E2524DAE2 primary key (GenericSetAdjustmentOrderId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: GlobalExposureView                                    */
/*==============================================================*/
create table dbo.GlobalExposureView (
   GlobalExposureViewId Numeric(19,0)        not null,
   Enitity              int                  null,
   ProjectId            Numeric(19,0)        null,
   DivisionNumber       varchar(15)          null,
   GlobalExposureViewName varchar(255)         null,
   Version              int                  null,
   PeriodBasisId        int                  null,
   constraint PK_GLOBALEXPOSUREVIEW primary key (GlobalExposureViewId)
)
go

/*==============================================================*/
/* Table: GlobalExposureViewSummary                             */
/*==============================================================*/
create table dbo.GlobalExposureViewSummary (
   GlobalExposureViewSummaryId Numeric(19,0)        not null,
   Entity               int                  null,
   GlobalExposureViewId Numeric(19,0)        null,
   EDMid                varchar(20)          null,
   EDMName              varchar(255          null,
   InstanceId           Numeric(19,0)        null,
   SummaryTitle         varchar(255)         null,
   SummaryOrder         int                  null,
   constraint PK_GLOBALEXPOSUREVIEWSUMMARY primary key (GlobalExposureViewSummaryId)
)
go

/*==============================================================*/
/* Table: InuringCanvasLayout                                   */
/*==============================================================*/
create table dbo.InuringCanvasLayout (
   InuringCanvasLayoutId numeric(19,0)        not null,
   Entity               int                  null,
   InuringPackageId     numeric(19,0)        null,
   NodeId               numeric(19,0)        null,
   NodeType             varchar(12)          null,
   NodeName             varchar(25)          null,
   NodeDisplayName      varchar(75)          null,
   NodeTop              Numeric(10,5)        null,
   NodeLeft             Numeric(10,5)        null,
   CreatedBy            int                  null,
   Created              datetime             null,
   UpdatedBy            int                  null,
   Updated              datetime             null,
   constraint PK_INURINGCANVASLAYOUT primary key (InuringCanvasLayoutId)
)
go

/*==============================================================*/
/* Table: InuringContractLayer                                  */
/*==============================================================*/
create table dbo.InuringContractLayer (
   InuringContractLayerId numeric(19,0)        not null,
   Entity               int                  null,
   InuringContractNodeId numeric(19,0)        null,
   LayerNumber          int                  null,
   LayerSequence        int                  null,
   LayerCode            varchar(255)         collate Latin1_General_CI_AS null,
   LayerCurrency        varchar(255)         collate Latin1_General_CI_AS null,
   LayerDescription     varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__InuringC__BD9364EE96BCBC08 primary key (InuringContractLayerId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringContractLayerParam                             */
/*==============================================================*/
create table dbo.InuringContractLayerParam (
   InuringContractParamId numeric(19,0)        not null,
   Entity               int                  null,
   InuringContractLayerId numeric(19,0)        null,
   ParamName            varchar(255)         collate Latin1_General_CI_AS null,
   ParamType            varchar(255)         collate Latin1_General_CI_AS null,
   ParamValue           varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__InuringC__1748E9A243DA916D primary key (InuringContractParamId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringContractLayerPerilLimit                        */
/*==============================================================*/
create table dbo.InuringContractLayerPerilLimit (
   InuringContractLayerPerilLimitId numeric(19,0)        not null,
   Entity               int                  null,
   InuringContractLayerId numeric(19,0)        null,
   Peril                varchar(255)         collate Latin1_General_CI_AS null,
   Limit                numeric(27,2)        null,
   constraint PK__InuringC__F5B2C38F077D0878 primary key (InuringContractLayerPerilLimitId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringContractLayerReinstatementDetail               */
/*==============================================================*/
create table dbo.InuringContractLayerReinstatementDetail (
   InuringContractLayerReinstatementDetailId numeric(19,0)        not null,
   Entity               int                  null,
   InuringContractLayerId numeric(19,0)        null,
   ReinstatementRank    int                  null,
   ReinstatementNumber  int                  null,
   ReinstatementCharge  numeric(18,8)        null,
   ProRataTemporis      bit                  null,
   constraint PK__InuringC__F78CCBA1C12180AE primary key (InuringContractLayerReinstatementDetailId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringContractNode                                   */
/*==============================================================*/
create table dbo.InuringContractNode (
   InuringContractNodeId numeric(19,0)        not null,
   Entity               int                  null,
   InuringPackageId     numeric(19,0)        null,
   ContractNodeStatus   int                  null,
   ContractTypeCode     varchar(255)         collate Latin1_General_CI_AS null,
   ContractName         varchar(255)         collate Latin1_General_CI_AS null,
   OccurenceBasis       varchar(255)         collate Latin1_General_CI_AS null,
   ClaimsBasis          varchar(255)         collate Latin1_General_CI_AS null,
   ContractCurrency     varchar(255)         collate Latin1_General_CI_AS null,
   SubjectPremium       numeric(27,2)        null,
   constraint PK__InuringC__F3C2A042B865F7CF primary key (InuringContractNodeId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringEdge                                           */
/*==============================================================*/
create table dbo.InuringEdge (
   InuringEdgeId        numeric(19,0)        not null,
   Entity               int                  null,
   InuringPackageId     numeric(19,0)        null,
   SourceNodeId         numeric(19,0)        null,
   SourceNodeType       int                  null,
   TargetNodeId         numeric(19,0)        null,
   TargetNodeType       int                  null,
   OutputPerspective    int                  null,
   OutputGroupingSign   int                  null,
   OutputAtLayerLevel   bit                  null,
   constraint PK__InuringE__7D9ED69844F492FB primary key (InuringEdgeId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringFilterCriteria                                 */
/*==============================================================*/
create table dbo.InuringFilterCriteria (
   InuringFilterCriteriaId numeric(19,0)        not null,
   Entity               int                  null,
   InuringObjectType    int                  null,
   InuringObjectId      int                  null,
   FilterKey            varchar(255)         collate Latin1_General_CI_AS null,
   FilterValue          varchar(255)         collate Latin1_General_CI_AS null,
   Including            bit                  null,
   constraint PK__InuringF__0FB26F773C3810BA primary key (InuringFilterCriteriaId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringFinalAttachedPLT                               */
/*==============================================================*/
create table InuringFinalAttachedPLT (
   InuringFinalAttachedPLT numeric(19,0)        not null,
   Entity               int                  null,
   InuringFinalNodeId   numeric(19,0)        null,
   PLTCcy               varchar(5)           null,
   PLTHeaderId          numeric(19,0)        null,
   IsGrouped            bit                  null,
   OriginalPLTId        long                 null,
   TargetRapId          long                 null,
   MinimumGrainRegionPerilCode varchar(10)          null,
   Peril                varchar(2)           null,
   PLTName              varchar(255)         null,
   OccurrenceBasisCode  varchar(20)          null,
   constraint PK_INURINGFINALATTACHEDPLT primary key (InuringFinalAttachedPLT)
)
go

/*==============================================================*/
/* Table: InuringFinalNode                                      */
/*==============================================================*/
create table dbo.InuringFinalNode (
   InuringFinalNodeId   numeric(19,0)        not null,
   Entity               int                  null,
   InuringPackageId     numeric(19,0)        null,
   FinalNodeStatus      int                  null,
   InuringOutputGrain   int                  null,
   constraint PK__InuringF__7FDBFEAC94113C5A primary key (InuringFinalNodeId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringInputAttachedPLT                               */
/*==============================================================*/
create table dbo.InuringInputAttachedPLT (
   InuringInputAttachedPLTId numeric(19,0)        not null,
   Entity               int                  null,
   InuringInputNodeId   numeric(19,0)        null,
   PLTHeaderId          numeric(19,0)        null,
   constraint PK__InuringI__F6B4A0E638695D91 primary key (InuringInputAttachedPLTId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringInputNode                                      */
/*==============================================================*/
create table dbo.InuringInputNode (
   InuringInputNodeId   numeric(19,0)        not null,
   Entity               int                  null,
   InuringPackageId     numeric(19,0)        null,
   InputNodeStatus      int                  null,
   InputNodeName        varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__InuringI__CD4B7E5CB9E6F905 primary key (InuringInputNodeId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringNote                                           */
/*==============================================================*/
create table InuringNote (
   InuringNoteId        bigint               not null,
   Entity               int                  null,
   InuringObjectId      bigint               null,
   InuringObjectType    int                  null,
   InuringPackageId     numeric(19,0)        null,
   NoteColor            varchar(255)         null,
   NoteContent          varchar(255)         null,
   NoteTitle            varchar(255)         null,
   constraint PK_INURINGNOTE primary key (InuringNoteId)
)
go

/*==============================================================*/
/* Table: InuringPackage                                        */
/*==============================================================*/
create table dbo.InuringPackage (
   InuringPackageId     numeric(19,0)        not null,
   Entity               int                  null,
   PackageName          varchar(255)         collate Latin1_General_CI_AS null,
   PackageDescription   varchar(255)         collate Latin1_General_CI_AS null,
   WorkspaceId          numeric(19,0)        null,
   CreatedBy            int                  null,
   CreatedOn            datetime             null,
   LastModifiedBy       int                  null,
   LastModifiedOn       datetime             null,
   IsLocked             bit                  null,
   PackageStatus        int                  null,
   constraint PK__InuringP__4915C7623BB62196 primary key (InuringPackageId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringPackageProcessing                              */
/*==============================================================*/
create table dbo.InuringPackageProcessing (
   InuringPackageProcessingId numeric(19,0)        not null,
   Entity               int                  null,
   InuringPackageId     numeric(19,0)        null,
   SubmittedDate        datetime             null,
   SubmittedBy          int                  null,
   StartedDate          datetime             null,
   EndedDate            datetime             null,
   InuringProcessingStatus int                  null,
   InputFileName        varchar(255)         collate Latin1_General_CI_AS null,
   InputFilePath        varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__InuringP__6109DD36C1DF96D7 primary key (InuringPackageProcessingId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: InuringPackageProcessingExchangeRate                  */
/*==============================================================*/
create table dbo.InuringPackageProcessingExchangeRate (
   InuringPackageProcessingExchangeRateId numeric(19,0)        not null,
   Entity               int                  null,
   InuringPackageProcessingId numeric(19,0)        null,
   SourceCcy            varchar(255)         collate Latin1_General_CI_AS null,
   TargetCcy            varchar(255)         collate Latin1_General_CI_AS null,
   ExchangeRate         numeric(38,20)       null,
   StatisticalRateDate  date                 null,
   StatisticalRateType  varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__InuringP__04B36987B21FE8B3 primary key (InuringPackageProcessingExchangeRateId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: LossDataHeader                                        */
/*==============================================================*/
create table dbo.LossDataHeader (
   LossDataHeaderId     numeric(19,0)        not null,
   Entity               Int                  null,
   ModelAnalysisId      numeric(19,0)        null,
   LossTableType        varchar(255)         collate Latin1_General_CI_AS null,
   OriginalTarget       varchar(255)         collate Latin1_General_CI_AS null,
   Currency             varchar(255)         collate Latin1_General_CI_AS null,
   CreatedDate          datetime             null,
   LossDataFilePath     varchar(255)         collate Latin1_General_CI_AS null,
   LossDataFileName     varchar(255)         collate Latin1_General_CI_AS null,
   FileDataFormat       varchar(255)         collate Latin1_General_CI_AS null,
   FileType             varchar(255)         collate Latin1_General_CI_AS null,
   CloningSourceId      int                  null,
   constraint PK__RRLossDa__EC9BAF7C4310D174 primary key (LossDataHeaderId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: ModelAnalysis                                         */
/*==============================================================*/
create table dbo.ModelAnalysis (
   ModelAnalysisId      numeric(19,0)        not null,
   Entity               int                  not null,
   ProjectId            numeric(19,0)        null,
   ImportedDate         datetime             null,
   CreationDate         datetime             null,
   RunDate              datetime             null,
   ImportStatus         varchar(255)         collate Latin1_General_CI_AS null,
   ProjectImportRunId   numeric(19,0)        null,
   SourceModellingSystemInstance varchar(255)         collate Latin1_General_CI_AS null,
   SourceModellingVendor varchar(255)         collate Latin1_General_CI_AS null,
   SourceModellingSystem varchar(255)         collate Latin1_General_CI_AS null,
   SourceModellingSystemVersion varchar(255)         collate Latin1_General_CI_AS null,
   DataSourceId         bigint               null,
   DataSourceName       varchar(255)         collate Latin1_General_CI_AS null,
   FileName             varchar(255)         collate Latin1_General_CI_AS null,
   SourceAnalysisId     int                  null,
   SourceAnalysisName   varchar(255)         collate Latin1_General_CI_AS null,
   DivisionNumber       varchar(15)          null,
   Grain                varchar(255)         collate Latin1_General_CI_AS null,
   FinancialPerspective varchar(255)         collate Latin1_General_CI_AS null,
   FinancialPerspectiveTreatyLabel varchar(255)         collate Latin1_General_CI_AS null,
   FinancialPerspectiveTreatyTag varchar(255)         collate Latin1_General_CI_AS null,
   Peril                varchar(255)         collate Latin1_General_CI_AS null,
   GeoCode              varchar(255)         collate Latin1_General_CI_AS null,
   RegionPeril          varchar(255)         collate Latin1_General_CI_AS null,
   SourceCurrency       varchar(255)         collate Latin1_General_CI_AS null,
   TargetCurrency       varchar(255)         collate Latin1_General_CI_AS null,
   TargetCurrencyBasis  varchar(255)         collate Latin1_General_CI_AS null,
   ExchangeRate         numeric(38,20)       null,
   DefaultOccurrenceBasis varchar(255)         collate Latin1_General_CI_AS null,
   UserOccurrenceBasis  varchar(255)         collate Latin1_General_CI_AS null,
   Proportion           numeric(38,20)       null,
   ProxyScalingBasis    varchar(255)         collate Latin1_General_CI_AS null,
   ProxyScalingNarrative varchar(255)         collate Latin1_General_CI_AS null,
   UnitMultiplier       numeric(38,20)       null,
   MultiplierBasis      varchar(255)         collate Latin1_General_CI_AS null,
   MultiplierNarrative  varchar(255)         collate Latin1_General_CI_AS null,
   ProfileKey           varchar(255)         collate Latin1_General_CI_AS null,
   Description          varchar(255)         collate Latin1_General_CI_AS null,
   SourceAnalysisLevel  varchar(255)         collate Latin1_General_CI_AS null,
   LossAmplification    varchar(255)         collate Latin1_General_CI_AS null,
   Model                varchar(255)         collate Latin1_General_CI_AS null,
   Tags                 varchar(255)         collate Latin1_General_CI_AS null,
   UserNotes            varchar(255)         collate Latin1_General_CI_AS null,
   OverrideReasonText   varchar(255)         collate Latin1_General_CI_AS null,
   ResultName           varchar(255)         collate Latin1_General_CI_AS null,
   SourceLossModellingBasis varchar(255)         collate Latin1_General_CI_AS null,
   SourceLossTableType  varchar(255)         collate Latin1_General_CI_AS null,
   EventSet             varchar(255)         collate Latin1_General_CI_AS null,
   ModelModule          varchar(255)         collate Latin1_General_CI_AS null,
   SourceResultsReference varchar(255)         collate Latin1_General_CI_AS null,
   SubPeril             varchar(255)         collate Latin1_General_CI_AS null,
   Region               varchar(255)         collate Latin1_General_CI_AS null,
   ProfileName          varchar(255)         collate Latin1_General_CI_AS null,
   OccurrenceBasisOverrideReason varchar(255)         collate Latin1_General_CI_AS null,
   OccurenceBasisOverridenBy varchar(255)         collate Latin1_General_CI_AS null,
   Metadata             varchar(Max)         collate Latin1_General_CI_AS null,
   constraint PK__RRAnalys__782CE2284D28EA7E primary key (ModelAnalysisId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: ModelPortfolio                                        */
/*==============================================================*/
create table dbo.ModelPortfolio (
   ModelPortfolioId     numeric(19,0)        not null,
   Entity               int                  null,
   ProjectId            numeric(19,0)        null,
   ProjectImportRunId   Numeric(19,0)        null,
   SourcePortfolioId    numeric(19,0)        null,
   ModelPortfolioName   varchar(150)         null,
   ModelPortfolioType   varchar(10)          null,
   DataSourceId         numeric(19,0)        null,
   DataSourceName       varchar(255)         null,
   ModelPortfolioDescription varchar(150)         null,
   DivisionNumber       varchar(15)          null,
   Currency             varchar(3)           null,
   ExchangeRate         numeric(20,8)        null,
   ImportStatus         varchar(5)           null,
   ImportLocationLevel  varchar(50)          null,
   ExposureLevel        varchar(50)          null,
   ExposedPerils        varchar(50)          null,
   ExposedLocationPerils varchar(50)          null,
   SourceModellingVendor varchar(50)          null,
   SourceModellingSystem varchar(50)          null,
   SourceModellingSystemInstance varchar(50)          null,
   SourceModellingSystemVersion varchar(50)          null,
   ImportedDate         datetime             null,
   Proportion           numeric(20,8)        null,
   UnitMultiplier       numeric(20,8)        null,
   CreationDate         datetime             null,
   constraint PK_MODELPORTFOLIO primary key (ModelPortfolioId)
)
go

/*==============================================================*/
/* Table: OmegaCountryPeril                                     */
/*==============================================================*/
create table dbo.OmegaCountryPeril (
   OmegaCountryPerilId  int                  not null,
   Entity               int                  null,
   OmegaSectionId       varchar(25)          null,
   TreatyIdentifier     varchar(100)         null,
   UWYear               int                  null,
   UWOrder              int                  null,
   EndorsementNumber    int                  null,
   SectionId            int                  null,
   InclusionId          varchar(15)          null,
   CountryCode          varchar(4)           null,
   PerilCode            varchar(4)           null,
   LastUpdateOmega      datetime             null,
   LastExtractOmega     datetime             null,
   RRImportDateTime     datetime             null,
   constraint PK_OMEGACOUNTRYPERIL primary key (OmegaCountryPerilId)
)
go

/*==============================================================*/
/* Table: OmegaReinstatement                                    */
/*==============================================================*/
create table dbo.OmegaReinstatement (
   OmegaReinstatementId int                  not null,
   Entity               int                  null,
   OmegaSectionId       varchar(25)          null,
   TreatyIdentifier     varchar(100)         null,
   UWYear               int                  null,
   UWOrder              int                  null,
   EndorsementNumber    int                  null,
   SectionId            int                  null,
   Rank                 int                  null,
   ReinstatementLabel   varchar(100)         null,
   Premium              numeric(27,5)        null,
   ProportionCededRate  numeric(27,5)        null,
   ProRataTemporis      bit                  null,
   LastUpdateOmega      datetime             null,
   LastExtractOmega     datetime             null,
   RRImportDateTime     datetime             null,
   constraint PK_OMEGAREINSTATEMENT primary key (OmegaReinstatementId)
)
go

/*==============================================================*/
/* Table: OmegaSection                                          */
/*==============================================================*/
create table dbo.OmegaSection (
   OmegaSectionId       varchar(25)          not null,
   Entity               int                  null,
   OmegaTreatyId        numeric(19,0)        null,
   TreatyIdentifier     varchar(100)         null,
   UWYear               int                  null,
   UWOrder              int                  null,
   EndorsementNumber    int                  null,
   SectionId            int                  null,
   SectionLabel         nvarchar(255)        null,
   SectionStatus        int                  null,
   LineOfBusiness       int                  null,
   ScopeOfBusiness      int                  null,
   TypeOfPolicy         int                  null,
   NatureCode           int                  null,
   CededShare           numeric(38,20)       null,
   WrittenShare         numeric(38,20)       null,
   SignedShare          numeric(38,20)       null,
   ExpectedShare        numeric(38,20)       null,
   EQExposed            bit                  null,
   WSExposed            bit                  null,
   FLExposed            bit                  null,
   PremiumCcyCode       varchar(3)           null,
   EGPI                 Numeric(20,2)        null,
   LimitCurrency        varchar(3)           null,
   CancellationDate     date                 null,
   ClaimRunOff          tinyint              null,
   PremiumRunOff        tinyint              null,
   ClaimCutOff          tinyint              null,
   PremiumCutOff        tinyint              null,
   RunOffDurationYears  int                  null,
   LiabilityByRisk      bit                  null,
   LiabilityByEvent     bit                  null,
   SectionType          int                  null,
   AccountingType       int                  null,
   WorkingCAT           varchar(10)          null,
   LastUpdateOmega      datetime             null,
   LastExtractOmega     datetime             null,
   RRImportDateTime     datetime             null,
   constraint PK_OMEGASECTION primary key (OmegaSectionId)
)
go

/*==============================================================*/
/* Table: OmegaTermsAndCondition                                */
/*==============================================================*/
create table dbo.OmegaTermsAndCondition (
   OmegaTermsConditionId int                  not null,
   Entity               int                  null,
   OmegaSectionId       varchar(25)          null,
   TreatyIdentifier     varchar(100)         null,
   UWYear               int                  null,
   UWOrder              int                  null,
   EndorsementNumber    int                  null,
   SectionId            int                  null,
   OccurrenceBasis      varchar(3)           null,
   SubjectPremiumCcyCode varchar(3)           null,
   SubjectPremium       numeric(27,2)        null,
   SubjectPremiumBasis  int                  null,
   IsUnlimited          int                  null,
   CededOr100PctShare   varchar(10)          null,
   EventLimit           numeric(27,2)        null,
   AnnualLimit          numeric(27,2)        null,
   AnnualDeductible     numeric(27,2)        null,
   EQLimit              numeric(27,2)        null,
   FLLimit              numeric(27,2)        null,
   WSLimit              numeric(27,2)        null,
   Capacity             numeric(27,2)        null,
   AttachmentPoint      numeric(27,2)        null,
   AggregateDeductible  numeric(27,2)        null,
   UnlimitedReinstatement int                  null,
   LastUpdateOmega      datetime             null,
   LastExtractOmega     datetime             null,
   RRImportDateTime     datetime             null,
   isActive             bit                  null,
   constraint PK_OMEGATERMSANDCONDITION primary key (OmegaTermsConditionId)
)
go

/*==============================================================*/
/* Table: OmegaTreaty                                           */
/*==============================================================*/
create table dbo.OmegaTreaty (
   OmegaTreatyId        numeric(19,0)        not null,
   ClientId             numeric(19,0)        null,
   Entity               int                  null,
   TreatyIdentifier     varchar(100)         null,
   UWYear               int                  null,
   UWOrder              nvarchar(255)        null,
   EndorsementNumber    int                  null,
   TreatyLabel          datetime             null,
   TreatyStatus         datetime             null,
   SubsidiaryCode       int                  null,
   UWUnitID             varchar(15)          null,
   SubsidiaryLedgerCode varchar(10)          null,
   InceptionDate        date                 null,
   ExpiryDate           date                 null,
   ProgramId            varchar(20)          null,
   ProgramName          varchar(125)         null,
   BouquetID            varchar(20)          null,
   BouquetName          varchar(125)         null,
   CedentId             varchar(20)          null,
   UnderwriterId        varchar(20)          null,
   UnderwriterFirstName varchar(60)          null,
   UnderwriterLastName  varchar(60)          null,
   LiabilityCcyCode     varchar(5)           null,
   AnnualLimit          numeric(27,2)        null,
   EventLimit           numeric(27,2)        null,
   LastUpdateOmega      datetime             null,
   Leader               int                  null,
   UWUnitCode2          varchar(20)          null,
   UWUnitName           varchar(100)         null,
   UltimateGroupCode    int                  null,
   UltimateGroupName    varchar(100)         null,
   GroupSegmentName     varchar(100)         null,
   GroupSegmentCode     int                  null,
   IntermediaryCode     int                  null,
   LastExtractOmega     datetime             null,
   RRImportDateTime     datetime             null,
   constraint PK_OMEGATREATY primary key (OmegaTreatyId)
)
go

/*==============================================================*/
/* Table: PLTHeader                                             */
/*==============================================================*/
create table dbo.PLTHeader (
   PLTHeaderId          numeric(19,0)        not null,
   Entity               int                  not null,
   PLTType              varchar(10)          collate Latin1_General_CI_AS null,
   ModelAnalysisId      numeric(19,0)        null,
   TargetRAPId          int                  null,
   RegionPerilId        int                  null,
   ProjectId            numeric(19,0)        null,
   IsLocked             bit                  null,
   SimulationPeriods    int                  null,
   GeneratedFromDefaultAdjustment bit                  null,
   CloningSourceId      int                  null,
   LossDataFilePath     varchar(255)         collate Latin1_General_CI_AS null,
   LossDataFileName     varchar(255)         collate Latin1_General_CI_AS null,
   CurrencyCode         varchar(4)           collate Latin1_General_CI_AS null,
   CreatedDate          datetime             null,
   PerilCode            varchar(255)         collate Latin1_General_CI_AS null,
   GeoCode              varchar(255)         collate Latin1_General_CI_AS null,
   GeoDescription       varchar(255)         collate Latin1_General_CI_AS null,
   RMSSimulationSet     int                  null,
   ImportSequence       int                  null,
   ThreadName           varchar(255)         collate Latin1_General_CI_AS null,
   UDName               varchar(255)         collate Latin1_General_CI_AS null,
   UserOccurrenceBasis  varchar(255)         collate Latin1_General_CI_AS null,
   DefaultPltName       varchar(255)         collate Latin1_General_CI_AS null,
   TruncationThreshold  varchar(255)         collate Latin1_General_CI_AS null,
   TruncationExchangeRate varchar(255)         collate Latin1_General_CI_AS null,
   TruncationCurrency   varchar(255)         collate Latin1_General_CI_AS null,
   InuringPackageId     int                  null,
   SourceLossModelingBasis varchar(255)         collate Latin1_General_CI_AS null,
   GroupedPLT           bit                  null,
   DeletedOn            datetime             null,
   DeletedDue           varchar(255)         collate Latin1_General_CI_AS null,
   DeletedBy            varchar(255)         collate Latin1_General_CI_AS null,
   Archived             bit                  null,
   ArchivedDate         datetime             null,
   CreatedBy            int                  null,
   constraint PK__SCORPLTH__C0B579F96DFF1D55 primary key (PLTHeaderId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: PLTHeaderTag                                          */
/*==============================================================*/
create table dbo.PLTHeaderTag (
   PLTHeaderTagId       numeric(19,0)        not null,
   Entity               int                  null,
   PLTHeaderId          numeric(19,0)        null,
   WorkspaceId          numeric(19,0)        null,
   TagId                numeric(19,0)        null,
   CreatedBy            int                  collate Latin1_General_CI_AS null,
   CreatedDate          datetime             null,
   constraint PK__SCORPLTH__38C3B86A9CAE35A7 primary key (PLTHeaderTagId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: PLTPricing                                            */
/*==============================================================*/
create table dbo.PLTPricing (
   PLTPricingId         numeric(19,0)        not null,
   Entity               int                  null,
   ClientId             numeric(19,0)        null,
   CountryCode          varchar(5)           null,
   ImportedOmegaId      varchar(50)          null,
   PricingName          varchar(255)         null,
   PricingStructure     varchar(255)         null,
   PricingStatus        varchar(50)          null,
   UWYear               int                  null,
   FileName             varchar(255)         null,
   LastSynchronised     datetime             null,
   LastUpdateRR         datetime             null,
   constraint PK_PLTPRICING primary key (PLTPricingId)
)
go

/*==============================================================*/
/* Table: PLTPricingLossEngine                                  */
/*==============================================================*/
create table dbo.PLTPricingLossEngine (
   PLTPricingLossEngineId numeric(19,0)        not null,
   Entity               int                  null,
   PLTPricingId         numeric(19,0)        null,
   PLTPricingSectionId  numeric(19,0)        null,
   PLTPricingLossEngineName varchar(5)           null,
   NonNatCatLoss        numeric(27,2)        null,
   PLTPricingLossEngineType varchar(5)           null,
   PLTFxRate            numeric(20,8)        null,
   PLTHeaderId          varchar(5)           null,
   FranchiseDeductibleType varchar(5)           null,
   FranchiseDeductibleAmount numeric(27,2)        null,
   CapLossesAmount      numeric(27,2)        null,
   FileName             varchar(5)           null,
   LastSynchronised     datetime             null,
   LastUpdateRR         datetime             null,
   constraint PK_PLTPRICINGLOSSENGINE primary key (PLTPricingLossEngineId)
)
go

/*==============================================================*/
/* Table: PLTPricingMinimumGrain                                */
/*==============================================================*/
create table dbo.PLTPricingMinimumGrain (
   PLTPricingMinimumGrainId numeric(19,0)        not null,
   Entity               int                  null,
   PLTPricingId         numeric(19,0)        null,
   PLTPricingLossEngineId numeric(19,0)        null,
   PLTPricingSectionId  numeric(19,0)        null,
   RegionPerilCode      varchar(8)           null,
   ExpectedLoss         numeric(27,2)        null,
   FileName             varchar(255)         null,
   LastSynchronised     datetime             null,
   LastUpdateRR         datetime             null,
   constraint PK_PLTPRICINGMINIMUMGRAIN primary key (PLTPricingMinimumGrainId)
)
go

/*==============================================================*/
/* Table: PLTPricingSection                                     */
/*==============================================================*/
create table dbo.PLTPricingSection (
   PLTPricingSectionId  numeric(19,0)        not null,
   Entity               int                  null,
   PLTPricingId         numeric(19,0)        null,
   Currency             varchar(5)           null,
   PLTPricingSectionName varchar(255)         null,
   OmegaTreatyCode      varchar(25)          null,
   OmegaSectionCode     int                  null,
   FileName             varchar(255)         null,
   LastSynchronised     datetime             null,
   LastUpdateRR         datetime             null,
   constraint PK_PLTPRICINGSECTION primary key (PLTPricingSectionId)
)
go

/*==============================================================*/
/* Table: Project                                               */
/*==============================================================*/
create table dbo.Project (
   ProjectId            numeric(19,0)        not null,
   Entity               int                  null,
   WorkspaceId          numeric(19,0)        null,
   ProjectImportRunId   numeric(19,0)        null,
   RLDataSourceId       numeric(19,0)        null,
   ProjectName          varchar(255)         collate Latin1_General_CI_AS null,
   ProjectDescription   varchar(255)         collate Latin1_General_CI_AS null,
   IsMaster             bit                  null,
   IsLinked             bit                  null,
   IsPublished          bit                  null,
   IsCloned             bit                  null,
   IsPostInured         bit                  null,
   IsMGA                bit                  null,
   AssignedTo           varchar(255)         collate Latin1_General_CI_AS null,
   CreationDate         datetime             null,
   ReceptionDate        datetime             null,
   DueDate              datetime             null,
   CreatedBy            varchar(255)         collate Latin1_General_CI_AS null,
   LinkedSourceProjectId int                  null,
   CloneSourceProjectId int                  null,
   Deleted              bit                  null,
   DeletedOn            datetime             null,
   DeletedDue           varchar(255)         collate Latin1_General_CI_AS null,
   DeletedBy            varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__Project__761ABEF02EBB054E primary key (ProjectId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: ProjectConfigurationForeWriter                        */
/*==============================================================*/
create table dbo.ProjectConfigurationForeWriter (
   ProjectConfigurationForeWriterId numeric(19,0)        not null,
   Entity               int                  null,
   ProjectId            numeric(19,0)        null,
   CARequestId          varchar(30)          null,
   CARType              varchar(15)          null,
   CARStatus            varchar(10)          null,
   CARName              varchar(255)         null,
   AssignedTo           int                  null,
   LastUpdateBy         int                  null,
   LastUpdateDate       datetime             null,
   RequestCreationDate  datetime             null,
   RequestedBy          int                  null,
   Code                 varchar(50)          null,
   Narrative            varchar(500)         null,
   constraint PK_PROJECTCONFIGURATIONFOREWRI primary key (ProjectConfigurationForeWriterId)
)
go

/*==============================================================*/
/* Table: ProjectConfigurationForeWriterContract                */
/*==============================================================*/
create table dbo.ProjectConfigurationForeWriterContract (
   ProjectConfigurationForeWriterContract__ numeric(19,0)        not null,
   Entity               int                  null,
   ProjectConfigurationForewriterId numeric(19,0)        null,
   ContractID           varchar(25)          null,
   FacNumber            varchar(25)          null,
   UWYear               int                  null,
   UWOrder              int                  null,
   EndorsementNumber    int                  null,
   ContractName         varchar(50)          null,
   BusinessType         varchar(25)          null,
   ClientId             int                  null,
   Client               varchar(125)         null,
   Subsidiary           varchar(25)          null,
   LineOfBusiness       varchar(25)          null,
   Sector               varchar(25)          null,
   UWAnalysis           varchar(15)          null,
   constraint PK_PROJECTCONFIGURATIONFOREWRI primary key (ProjectConfigurationForeWriterContract__)
)
go

/*==============================================================*/
/* Table: ProjectConfigurationForeWriterDivision                */
/*==============================================================*/
create table dbo.ProjectConfigurationForeWriterDivision (
   ProjectConfigurationFWDivisionsId numeric(19,0)        not null,
   Entity               int                  null,
   ProjectConfigurationFWDivisionsContractId numeric(19,0)        null,
   DivisionNumber       varchar(15)          null,
   PrincipalDivision    varchar(20)          null,
   LineOfBusiness       varchar(25)          null,
   CoverageType         varchar(5)           null,
   Currency             varchar(4)           null,
   constraint PK_PROJECTCONFIGURATIONFOREWRI primary key (ProjectConfigurationFWDivisionsId)
)
go

/*==============================================================*/
/* Table: ProjectConfigurationForeWriterFiles                   */
/*==============================================================*/
create table dbo.ProjectConfigurationForeWriterFiles (
   ProjectConfigurationForeWriterFilesId numeric(19,0)        not null,
   Entity               int                  null,
   ProjectConfigurationForeWriterId numeric(19,0)        null,
   AccFileName          varchar(255)         null,
   AccFileId            int                  null,
   LocFileName          varchar(255)         null,
   LocFileId            int                  null,
   vlsFileName          varchar(255)         null,
   vlsFileId            int                  null,
   constraint PK_PROJECTCONFIGURATIONFOREWRI primary key (ProjectConfigurationForeWriterFilesId)
)
go

/*==============================================================*/
/* Table: ProjectConfigurationMGA                               */
/*==============================================================*/
create table dbo.ProjectConfigurationMGA (
   ProjectMGAConfigurationid int                  not null,
   Entity               int                  null,
   ProjectId            numeric(19,0)        null,
   ExpectedFrequency    varchar(255)         collate Latin1_General_CI_AS null,
   SubmissionPeriod     varchar(255)         collate Latin1_General_CI_AS null,
   FinancialBasis       varchar(255)         collate Latin1_General_CI_AS null,
   TreatyId             varchar(255)         collate Latin1_General_CI_AS null,
   SectionId            varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__ProjectM__326992439FA6B99E primary key (ProjectMGAConfigurationid)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: ProjectForewriterExpectedScope                        */
/*==============================================================*/
create table dbo.ProjectForewriterExpectedScope (
   ProjectForewriterExpectedScopeId numeric(19,0)        not null,
   Entity               int                  null,
   ProjectId            numeric(19,0)        null,
   FACNumber            varchar(25)          null,
   UWYear               int                  null,
   EndorsementNumber    int                  null,
   UWOrder              int                  null,
   SourceAnalysisName   varchar(255)         null,
   DivisionNumber       int                  null,
   Country              varchar(100)         null,
   State                varchar(100)         null,
   TIV                  numeric(27,2)        null,
   TIVCurrency          varchar(5)           null,
   constraint PK_PROJECTFOREWRITEREXPECTEDSC primary key (ProjectForewriterExpectedScopeId)
)
go

/*==============================================================*/
/* Table: ProjectImportRun                                      */
/*==============================================================*/
create table dbo.ProjectImportRun (
   ProjectImportRunId   numeric(19,0)        not null,
   Entity               int                  null,
   RunId                int                  null,
   ProjectId            numeric(19,0)        null,
   Status               varchar(255)         collate Latin1_General_CI_AS null,
   StartDate            datetime             null,
   EndDate              datetime             null,
   LossImportEndDate    datetime             null,
   ImportedBy           varchar(255)         collate Latin1_General_CI_AS null,
   SourceConfigVendor   varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__ProjectI__F59440E40110D1F1 primary key (ProjectImportRunId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: PublicationARC                                        */
/*==============================================================*/
create table dbo.PublicationARC (
   PublicationARCId     numeric(19,0)        not null,
   Entity               int                  null,
   PLTHeaderId          numeric(19,0)        null,
   PLTLossDataFilePathAccumulation varchar(255)         collate Latin1_General_CI_AS null,
   PLTLossDataFileNameAccumulation varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__ARCPubli__5AF24B454D719C35 primary key (PublicationARCId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: PublicationPricingForeWriter                          */
/*==============================================================*/
create table dbo.PublicationPricingForeWriter (
   PublicationPricingForeWriterId numeric(19,0)        not null,
   Entity               int                  null,
   PLTHeaderId          numeric(19,0)        null,
   constraint PK__FWPublic__37158534ACC0E94C primary key (PublicationPricingForeWriterId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: PublicationPricingxAct                                */
/*==============================================================*/
create table dbo.PublicationPricingxAct (
   PublicationPricingxActId numeric(19,0)        not null,
   Entity               int                  null,
   PLTHeaderId          numeric(19,0)        null,
   xActAvailable        bit                  null,
   xActUsed             bit                  null,
   xActPublicationDate  datetime             null,
   constraint PK__xActPubl__04D98776107A7A7A primary key (PublicationPricingxActId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: RLAnalysisProfileRegion                               */
/*==============================================================*/
create table dbo.RLAnalysisProfileRegion (
   RLAnalysisProfileRegionId Numeric(19,0)        not null,
   Entity               int                  null,
   RLModelAnalysisId    Numeric(19,0)        null,
   AnalysisRegion       varchar(50)          null,
   AnalysisRegionName   varchar(150)         null,
   ProfileRegion        varchar(50)          null,
   ProfileRegionName    varchar(150)         null,
   Peril                varchar(5)           null,
   AAL                  Numeric(28,2)        null,
   constraint PK_RLANALYSISPROFILEREGION primary key (RLAnalysisProfileRegionId)
)
go

/*==============================================================*/
/* Table: RLDataSource                                          */
/*==============================================================*/
create table dbo.RLDataSource (
   RLDataSourceId       numeric(19,0)        not null,
   Entity               int                  null,
   ProjectId            numeric(19,0)        null,
   RLInstanceId         int                  collate Latin1_General_CI_AS null,
   RLDataSourceName     varchar(255)         collate Latin1_General_CI_AS null,
   RLDatabaseId         int                  collate Latin1_General_CI_AS null,
   RLDateCreated        datetime             null,
   DataType             varchar(255)         collate Latin1_General_CI_AS null,
   RLVersionId          varchar(255)         null,
   ModelCount           int                  null,
   BasicScanStatus      int                  null,
   LastBasicScanStart   datetime             null,
   LastBasicScanEnd     datetime             null,
   constraint PK__RMSModel__425D327AACCD369D primary key (RLDataSourceId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: RLImportDivisionSelection                             */
/*==============================================================*/
create table dbo.RLImportDivisionSelection (
   RLImportDivisionSelectionId numeric(19,0)        not null,
   Entity               int                  null,
   RLImportSelectionId  numeric(19,0)        null,
   DivisionNumber       varchar(15)          collate Latin1_General_CI_AS null,
   constraint PK__RMSSourc__949FB657792CA9C4 primary key (RLImportDivisionSelectionId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: RLImportFinancialPerspectiveSelection                 */
/*==============================================================*/
create table dbo.RLImportFinancialPerspectiveSelection (
   RLImportFinancialPerspectiveSelectionId numeric(19,0)        not null,
   Entity               int                  null,
   RLImportSelectionId  numeric(19,0)        null,
   FinancialPerspective varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__RMSSourc__949FB657792CA9C4 primary key (RLImportFinancialPerspectiveSelectionId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: RLImportSelection                                     */
/*==============================================================*/
create table dbo.RLImportSelection (
   RLImportSelectionId  numeric(19,0)        not null,
   Entity               int                  null,
   ProjectId            numeric(19,0)        null,
   RLModelAnalysisId    numeric(19,0)        null,
   TargetCurrency       varchar(255)         collate Latin1_General_CI_AS null,
   TargetRegionPeril    varchar(255)         collate Latin1_General_CI_AS null,
   OverrideRegionPerilBasis varchar(255)         collate Latin1_General_CI_AS null,
   OccurrenceBasis      varchar(255)         collate Latin1_General_CI_AS null,
   UnitMultiplier       numeric(38,20)       null,
   Proportion           numeric(38,20)       null,
   constraint PK__RMSSourc__949FB657792CA9C4 primary key (RLImportSelectionId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: RLImportTargetRAPSelection                            */
/*==============================================================*/
create table dbo.RLImportTargetRAPSelection (
   RLImportTargetRAPSelectionId numeric(19,0)        not null,
   Entity               int                  null,
   RLImportSelectionId  numeric(19,0)        null,
   TargetRAPCode        varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__RMSSourc__949FB657792CA9C4 primary key (RLImportTargetRAPSelectionId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: RLModelAnalysis                                       */
/*==============================================================*/
create table dbo.RLModelAnalysis (
   RLModelAnalysisId    numeric(19,0)        not null,
   Entity               int                  null,
   RLDataSourceId       numeric(19,0)        null,
   InstanceId           int                  null,
   ProjectId            numeric(19,0)        null,
   RDMId                bigint               null,
   RDMName              varchar(255)         collate Latin1_General_CI_AS null,
   Model                varchar(100)         null,
   AnalysisId           bigint               null,
   AnalysisName         varchar(255)         collate Latin1_General_CI_AS null,
   AnalysisDescription  varchar(255)         collate Latin1_General_CI_AS null,
   DivisionNumber       varchar(15)          null,
   DefaultGrain         varchar(255)         collate Latin1_General_CI_AS null,
   ExposureType         varchar(255)         collate Latin1_General_CI_AS null,
   ExposureTypeCode     int                  null,
   EDMNameSourceLink    varchar(255)         collate Latin1_General_CI_AS null,
   ExposureId           bigint               null,
   AnalysisCurrency     varchar(255)         collate Latin1_General_CI_AS null,
   RLExchangeRate       numeric(38,20)       null,
   TypeCode             int                  null,
   AnalysisType         varchar(255)         collate Latin1_General_CI_AS null,
   RunDate              datetime             null,
   Region               varchar(255)         collate Latin1_General_CI_AS null,
   RegionName           varchar(30)          null,
   Peril                varchar(255)         collate Latin1_General_CI_AS null,
   RegionPerilCode      varchar(255)         collate Latin1_General_CI_AS null,
   IsGroup              bit                  null,
   GroupType            varchar(20)          null,
   IsMultiRegionPeril   bit                  null,
   SubPeril             varchar(255)         collate Latin1_General_CI_AS null,
   LossAmplification    varchar(255)         collate Latin1_General_CI_AS null,
   RLAnalysisStatus     bigint               null,
   AnalysisMode         int                  null,
   EngineTypeCode       int                  null,
   EngineType           varchar(255)         collate Latin1_General_CI_AS null,
   EngineVersion        varchar(255)         collate Latin1_General_CI_AS null,
   EngineVersionMajor   varchar(255)         collate Latin1_General_CI_AS null,
   ProfileKey           varchar(255)         collate Latin1_General_CI_AS null,
   ProfileName          varchar(255)         collate Latin1_General_CI_AS null,
   PurePremium          numeric(27,2)        null,
   ExposureTIV          numeric(27,2)        null,
   Cedant               varchar(150)         null,
   LOB                  varchar(30)          null,
   User1                varchar(55)          null,
   User2                varchar(55)          null,
   User3                varchar(55)          null,
   User4                varchar(55)          null,
   GeoCode              varchar(10)          null,
   GeoDescription       varchar(255)         null,
   LastScanDateBasic    datetime             null,
   LastScanDateDetail   datetime             null,
   DetailScanStatus     int                  null,
   DetailScanStatusDescription varchar(255)         null,
   ScanLevel            int                  null,
   ScanStatus           int                  null,
   LastScan             Datetime2            null,
   constraint PK__RMSAnaly__EB2C36E34B4ADD05 primary key (RLModelAnalysisId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: RLPortfolio                                           */
/*==============================================================*/
create table dbo.RLPortfolio (
   RLPortfolioId        numeric(19,0)        not null,
   RLDataSourceId       numeric(19,0)        null,
   Entity               int                  null,
   ProjectId            numeric(19,0)        null,
   RLPortfolioName      varchar(20)          null,
   RLPortfolioNumber    varchar(20)          null,
   DescriptionType      varchar(10)          null,
   Description          varchar(150)         null,
   EDMId                varchar(20)          null,
   EDMName              varchar(255)         null,
   AgSource             varchar(20)          null,
   AgCedent             varchar(150)         null,
   AgCurrency           varchar(4)           null,
   Peril                varchar(4)           null,
   PortfolioId          varchar(20)          null,
   Type                 varchar(10)          null,
   TIV                  numeric(28,2)        null,
   Created              datetime2(0)         null,
   ScanLevel            int                  null,
   ScanStatus           int                  null,
   LastScan             datetime2(0)         null,
   constraint PK_RLPORTFOLIO primary key (RLPortfolioId)
)
go

/*==============================================================*/
/* Table: RLPortfolioAnalysisRegion                             */
/*==============================================================*/
create table dbo.RLPortfolioAnalysisRegion (
   RLPortfolioAnalysisRegionId numeric(19,0)        not null,
   Entity               int                  null,
   RLPortfolioId        numeric(19,0)        null,
   AnalysisRegion       varchar(15)          null,
   AnalysisRegionName   varcchar(150)        null,
   Peril                varchar(5)           null,
   LocationCount        Numeric(9,0)         null,
   ExposureCurrency     varchar(4)           null,
   RateToCurrency       numeric(38,20)       null,
   TotalTIVinUSD        numeric(28,2)        null,
   constraint PK_RLPORTFOLIOANALYSISREGION primary key (RLPortfolioAnalysisRegionId)
)
go

/*==============================================================*/
/* Table: RLPortfolioSelection                                  */
/*==============================================================*/
create table dbo.RLPortfolioSelection (
   RLPortfolioSelectionId numeric(19,0)        not null,
   Entity               int                  null,
   RLPortfolioId        numeric(19,0)        null,
   ProjectId            numeric(19,0)        null,
   DivisionNumber       varchar(15)          null,
   AnalysisRegion       varchar(25)          null,
   ImportLocationLevel  char(1)              null,
   TargetCurrency       varchar(4)           null,
   Proportion           Numeric(10,4)        null,
   UnitMultiplier       Numeric(10,4)        null,
   constraint PK_RLPORTFOLIOSELECTION primary key (RLPortfolioSelectionId)
)
go

/*==============================================================*/
/* Table: RLSourceEPHeader                                      */
/*==============================================================*/
create table dbo.RLSourceEPHeader (
   RLSourceEPHeaderId   numeric(19,0)        not null,
   Entity               int                  null,
   RLModelAnalysisId    numeric(19,0)        null,
   FinancialPerpective  varchar(10)          null,
   OEP10                numeric(27,2)        null,
   OEP50                numeric(27,2)        null,
   OEP100               numeric(27,2)        null,
   OEP250               numeric(27,2)        null,
   OEP500               numeric(27,2)        null,
   OEP1000              numeric(27,2)        null,
   AEP10                numeric(27,2)        null,
   AEP50                numeric(27,2)        null,
   AEP100               numeric(27,2)        null,
   AEP250               numeric(27,2)        null,
   AEP500               numeric(27,2)        null,
   AEP1000              numeric(27,2)        null,
   PurePremium          numeric(27,2)        null,
   StdDev               numeric(27,2)        null,
   CoV                  numeric(27,2)        null,
   constraint PK_RLSOURCEEPHEADER primary key (RLSourceEPHeaderId)
)
go

/*==============================================================*/
/* Table: RefAnalysisRegion                                     */
/*==============================================================*/
create table dbo.RefAnalysisRegion (
   RefAnalysisRegionId  bigint               not null,
   Entity               int                  null,
   AnalysisRegionCode   varchar(20)          null,
   AnalysisRegionDesc   Varchar(250)         null,
   constraint PK_REFANALYSISREGION primary key (RefAnalysisRegionId)
)
go

/*==============================================================*/
/* Table: RefAnalysisRegionMapping                              */
/*==============================================================*/
create table dbo.RefAnalysisRegionMapping (
   RefAnalysisRegionMappingId bigint               not null,
   Entity               int                  not null,
   AnalysisRegionCode   varchar(20)          null,
   ModellingSystem      varchar(5)           null,
   ModellingSystemVersion varchar(10)          null,
   CountryCode          varchar(20)          null,
   Admin1Code           varchar(10)          null,
   PerilCode            varchar(5)           null,
   constraint PK_REFANALYSISREGIONMAPPING primary key (RefAnalysisRegionMappingId)
)
go

/*==============================================================*/
/* Table: RefDashboardWidgetType                                */
/*==============================================================*/
create table RefDashboardWidgetType (
   RefDashboardWidgetTypeId bigint               not null,
   DashboardWidgetType  varchar(255)         null,
   constraint PK_REFDASHBOARDWIDGETTYPE primary key (RefDashboardWidgetTypeId)
)
go

/*==============================================================*/
/* Table: RefDefaultReturnPeriod                                */
/*==============================================================*/
create table dbo.RefDefaultReturnPeriod (
   RefDefaultReturnPeriodId bigint               not null,
   Entity               int                  null,
   ExceedenceProbability float                null,
   IsCurveEP            bit                  null,
   IsTableRP            bit                  null,
   ReturnPeriod         int                  null,
   Rank                 int                  null,
   constraint PK_REFDEFAULTRETURNPERIOD primary key (RefDefaultReturnPeriodId)
)
go

/*==============================================================*/
/* Table: RefExchangeRate                                       */
/*==============================================================*/
create table dbo.RefExchangeRate (
   RefAnalysisRegionId  bigint               not null,
   Entity               int                  null,
   CurrencyCode         varchar(5)           null,
   Type                 varchar(15)          null,
   CADRate              decimal(20,8)        null,
   EURRate              decimal(20,8)        null,
   GBPRate              decimal(20,8)        null,
   SGDRate              decimal(20,8)        null,
   USDRate              decimal(20,8)        null,
   EffectiveDate        date                 null,
   IsActive             bit                  null,
   LastSynchronised     datetime             null,
   constraint PK_REFEXCHANGERATE primary key (RefAnalysisRegionId)
)
go

/*==============================================================*/
/* Table: RefFMFContractAttribute                               */
/*==============================================================*/
create table dbo.RefFMFContractAttribute (
   ContractAttributeId  int                  not null,
   Entity               int                  null,
   UIAttributeName      varchar(150)         null,
   AttributeDescription varchar(250)         null,
   TargetRelease        int                  null,
   UISectionName        varchar(60)          null,
   UISection            int                  null,
   UISectionSequence    int                  null,
   UIVisible            char(1)              null,
   AttributeLabel       varchar(200)         null,
   UserInput            char(1)              null,
   AttributeCode        varchar(100)         null,
   DataType             varchar(25)          null,
   UIElement            varchar(25)          null,
   UIAlignment          varchar(10)          null,
   DefaultValue         varchar(50)          null,
   ValueValidation      varchar(150)         null,
   DefaultValueNotes    varchar(500)         null,
   constraint PK_REFFMFCONTRACTATTRIBUTE primary key (ContractAttributeId)
)
go

/*==============================================================*/
/* Table: RefFMFContractType                                    */
/*==============================================================*/
create table dbo.RefFMFContractType (
   ContractTypeId       int                  not null,
   Entity               int                  null,
   ContractTypeCode     varchar(10)          null,
   ContractTypeName     varchar(120)         null,
   UsedInRR             bit                  null,
   RRUISequence         int                  null,
   MainDistribution     varchar(50)          null,
   ContractClass        varchar(25)          null,
   RRUILongName         varchar(160)         null,
   MultiLayer           bit                  null,
   IsActive             bit                  null,
   constraint PK_REFFMFCONTRACTTYPE primary key (ContractTypeId)
)
go

/*==============================================================*/
/* Table: RefFMFContractTypeAttributeMap                        */
/*==============================================================*/
create table dbo.RefFMFContractTypeAttributeMap (
   RefFMFContractTypeAttributeMap int                  not null,
   Entity               int                  null,
   ContractTypeId       int                  null,
   ContractAttributeID  int                  null,
   Flag                 int                  null,
   constraint PK_REFFMFCONTRACTTYPEATTRIBUTE primary key (RefFMFContractTypeAttributeMap)
)
go

/*==============================================================*/
/* Table: RefFWRegionPerilGranularity                           */
/*==============================================================*/
create table RefFWRegionPerilGranularity (
   RefFWRegionPerilGranularityId bigint               not null,
   Entity               int                  null,
   RootRegionPeril      varchar(20)          null,
   MinimumGrainRegionPeril varchar(20)          null,
   HierarchyLevel       int                  null,
   constraint PK_REFFWREGIONPERILGRANULARITY primary key (RefFWRegionPerilGranularityId)
)
go

/*==============================================================*/
/* Table: RefRegionPerilMapping                                 */
/*==============================================================*/
create table dbo.RefRegionPerilMapping (
   RefRegionPerilMappingId bigint               not null,
   Entity               int                  null,
   RegionPerilId        bigint               null,
   Admin1Code           varchar(10)          null,
   Admin1Desc           varchar(100)         null,
   CountryCode          varchar(20)          null,
   CountryDesc          varchar(100)         null,
   PerilCode            varchar(5)           null,
   ModellingSystem      varchar(5)           null,
   ModellingSystemVersion varchar(10)          null,
   ModellingVendor      varchar(10)          null,
   constraint PK_REFREGIONPERILMAPPING primary key (RefRegionPerilMappingId)
)
go

/*==============================================================*/
/* Table: RefSourceRAPMapping                                   */
/*==============================================================*/
create table dbo.RefSourceRAPMapping (
   RefSourceRAPMappingId bigint               not null,
   Entity               int                  null,
   ModellingVendor      varchar(10)          null,
   ModellingSystem      varchar(5)           null,
   ModellingSystemVersion varchar(10)          null,
   ProfileKey           varchar(255)         null,
   SourceRAPCode        varchar(255)         null,
   constraint PK_REFSOURCERAPMAPPING primary key (RefSourceRAPMappingId)
)
go

/*==============================================================*/
/* Table: RegionPeril                                           */
/*==============================================================*/
create table dbo.RegionPeril (
   RegionPerilId        int                  not null,
   RegionPerilCode      varchar(255)         collate Latin1_General_CI_AS null,
   RegionPerilDesc      varchar(255)         collate Latin1_General_CI_AS null,
   PerilCode            varchar(255)         collate Latin1_General_CI_AS null,
   RegionPerilGroupCode varchar(255)         collate Latin1_General_CI_AS null,
   RegionPerilGroupDesc varchar(255)         collate Latin1_General_CI_AS null,
   RegionHierachy       int                  null,
   RegionDesc           varchar(255)         collate Latin1_General_CI_AS null,
   IsModelled           bit                  null,
   HierachyParentCode   varchar(255)         collate Latin1_General_CI_AS null,
   HierachyLevel        int                  null,
   IsMinimumGrainRegionPeril bit                  null,
   ParentMinimumGrainRegionPeril varchar(255)         collate Latin1_General_CI_AS null,
   RootRegionPeril      varchar(255)         null,
   IsActive             bit                  null,
   constraint PK__RegionPe__C8E8DBCAFBAE8F4F primary key (RegionPerilId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: ReturnPeriodBandingAdjustmentParameter                */
/*==============================================================*/
create table dbo.ReturnPeriodBandingAdjustmentParameter (
   AdjustmentParameterId numeric(19,0)        not null,
   Entity               int                  null,
   AdjustmentNodeId     numeric(19,0)        null,
   ReturnPeriod         numeric(15,5)        null,
   AdjustmentFactor     numeric(38,20)       null,
   constraint PK__ReturnPe__C4949B32BFFB015D primary key (AdjustmentParameterId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: Role                                                  */
/*==============================================================*/
create table dbo.Role (
   RoleId               int                  not null,
   RoleCode             varchar(5)           null,
   RoleName             varchar(20)          null,
   RoleDescription      varchar(100)         null,
   constraint PK_ROLE primary key (RoleId)
)
go

/*==============================================================*/
/* Table: ScalingAdjustmentParameter                            */
/*==============================================================*/
create table dbo.ScalingAdjustmentParameter (
   AdjustmentParameterId numeric(19,0)        not null,
   Entity               int                  null,
   AdjustmentNodeId     numeric(19,0)        null,
   AdjustmentFactor     numeric(38,20)       null,
   constraint PK__ScalingA__C4949B32231A8125 primary key (AdjustmentParameterId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: SourceExposureSummaryItem                             */
/*==============================================================*/
create table dbo.SourceExposureSummaryItem (
   SourceExposureSummaryItemId Numeric(19,0)        not null,
   Entity               int                  null,
   ExposureSummaryId    Numeric(19,0)        null,
   ExposureSummaryName  varchar(150)         null,
   SequenceNo           int                  null,
   EDMId                int                  null,
   PortfolioId          int                  null,
   PortfolioType        varchar(5)           null,
   Dimension1           varchar(50)          null,
   Dimension2           varchar(50)          null,
   Dimension3           varchar(50)          null,
   Dimension4           varchar(50)          null,
   FinancialPerspective varchar(5)           null,
   RegionPerilCode      varchar(15)          null,
   RegionPerilGroupCode varchar(15)          null,
   Peril                varchar(4)           null,
   CountryCode          varchar(4)           null,
   Admin1Code           varchar(10)          null,
   AnalysisRegionCode   varchar(50)          null,
   ExposureCurrency     varchar(3)           null,
   ConformedCurrency    varchar(3)           null,
   LocationCount        int                  null,
   TotalTIV             numeric(27,2)        null,
   ExposureCurrencyUSDRate numeric(20,8)        null,
   ConformedCurrencyUSDRate numeric(20,8)        null,
   FxRateVintageDate    date                 null,
   DimensionSort1       varchar(5)           null,
   DimensionSort2       varchar(5)           null,
   DimensionSort3       varchar(5)           null,
   DimensionSort4       varchar(5)           null,
   constraint PK_SOURCEEXPOSURESUMMARYITEM primary key (SourceExposureSummaryItemId)
)
go

/*==============================================================*/
/* Table: SummaryStatisticHeader                                */
/*==============================================================*/
create table dbo.SummaryStatisticHeader (
   SummaryStatisticHeaderId numeric(19,0)        not null,
   Entity               int                  null,
   LossDataType         varchar(255)         collate Latin1_General_CI_AS null,
   LossDataId           numeric(19,0)        null,
   FinancialPerspective varchar(255)         collate Latin1_General_CI_AS null,
   PurePremium          numeric(27,2)        null,
   Currency             varchar(3)           null,
   StandardDeviation    numeric(38,20)       null,
   Cov                  numeric(38,20)       null,
   Skewness             numeric(27,2)        null,
   Kurtosis             numeric(27,2)        null,
   EPSFilePath          varchar(255)         collate Latin1_General_CI_AS null,
   EPSFileName          varchar(255)         collate Latin1_General_CI_AS null,
   OEP10                numeric(27,2)        null,
   OEP50                numeric(27,2)        null,
   OEP100               numeric(27,2)        null,
   OEP250               numeric(27,2)        null,
   OEP500               numeric(27,2)        null,
   OEP1000              numeric(27,2)        null,
   AEP10                numeric(27,2)        null,
   AEP50                numeric(27,2)        null,
   AEP100               numeric(27,2)        null,
   AEP250               numeric(27,2)        null,
   AEP500               numeric(27,2)        null,
   AEP1000              numeric(27,2)        null,
   constraint PK__RRSummar__7DA99401E9615551 primary key (SummaryStatisticHeaderId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: SummaryStatisticsDetail                               */
/*==============================================================*/
create table dbo.SummaryStatisticsDetail (
   SummaryStatisticsDetailId numeric(19,0)        not null,
   Entity               int                  null,
   SummaryStatisticHeaderId numeric(19,0)        null,
   PLTHeaderId          numeric(19,0)        null,
   LossType             varchar(50)          null,
   CurveType            varchar(50)          null,
   RP100000             numeric(19,2)        null,
   RP50000              numeric(19,2)        null,
   RP33333              numeric(19,2)        null,
   RP25000              numeric(19,2)        null,
   RP20000              numeric(19,2)        null,
   RP16667              numeric(19,2)        null,
   RP14286              numeric(19,2)        null,
   RP12500              numeric(19,2)        null,
   RP11111              numeric(19,2)        null,
   RP10000              numeric(19,2)        null,
   RP9091               numeric(19,2)        null,
   RP8333               numeric(19,2)        null,
   RP7692               numeric(19,2)        null,
   RP7143               numeric(19,2)        null,
   RP6667               numeric(19,2)        null,
   RP6250               numeric(19,2)        null,
   RP5882               numeric(19,2)        null,
   RP5556               numeric(19,2)        null,
   RP5263               numeric(19,2)        null,
   RP5000               numeric(19,2)        null,
   RP4762               numeric(19,2)        null,
   RP4545               numeric(19,2)        null,
   RP4348               numeric(19,2)        null,
   RP4167               numeric(19,2)        null,
   RP4000               numeric(19,2)        null,
   RP3846               numeric(19,2)        null,
   RP3704               numeric(19,2)        null,
   RP3571               numeric(19,2)        null,
   RP3448               numeric(19,2)        null,
   RP3333               numeric(19,2)        null,
   RP3226               numeric(19,2)        null,
   RP3125               numeric(19,2)        null,
   RP3030               numeric(19,2)        null,
   RP2941               numeric(19,2)        null,
   RP2857               numeric(19,2)        null,
   RP2778               numeric(19,2)        null,
   RP2703               numeric(19,2)        null,
   RP2632               numeric(19,2)        null,
   RP2564               numeric(19,2)        null,
   RP2500               numeric(19,2)        null,
   RP2439               numeric(19,2)        null,
   RP2381               numeric(19,2)        null,
   RP2326               numeric(19,2)        null,
   RP2273               numeric(19,2)        null,
   RP2222               numeric(19,2)        null,
   RP2174               numeric(19,2)        null,
   RP2128               numeric(19,2)        null,
   RP2083               numeric(19,2)        null,
   RP2041               numeric(19,2)        null,
   RP2000               numeric(19,2)        null,
   RP1961               numeric(19,2)        null,
   RP1923               numeric(19,2)        null,
   RP1887               numeric(19,2)        null,
   RP1852               numeric(19,2)        null,
   RP1818               numeric(19,2)        null,
   RP1786               numeric(19,2)        null,
   RP1754               numeric(19,2)        null,
   RP1724               numeric(19,2)        null,
   RP1695               numeric(19,2)        null,
   RP1667               numeric(19,2)        null,
   RP1639               numeric(19,2)        null,
   RP1613               numeric(19,2)        null,
   RP1587               numeric(19,2)        null,
   RP1563               numeric(19,2)        null,
   RP1538               numeric(19,2)        null,
   RP1515               numeric(19,2)        null,
   RP1493               numeric(19,2)        null,
   RP1471               numeric(19,2)        null,
   RP1449               numeric(19,2)        null,
   RP1429               numeric(19,2)        null,
   RP1408               numeric(19,2)        null,
   RP1389               numeric(19,2)        null,
   RP1370               numeric(19,2)        null,
   RP1351               numeric(19,2)        null,
   RP1333               numeric(19,2)        null,
   RP1316               numeric(19,2)        null,
   RP1299               numeric(19,2)        null,
   RP1282               numeric(19,2)        null,
   RP1266               numeric(19,2)        null,
   RP1250               numeric(19,2)        null,
   RP1235               numeric(19,2)        null,
   RP1220               numeric(19,2)        null,
   RP1205               numeric(19,2)        null,
   RP1190               numeric(19,2)        null,
   RP1176               numeric(19,2)        null,
   RP1163               numeric(19,2)        null,
   RP1149               numeric(19,2)        null,
   RP1136               numeric(19,2)        null,
   RP1124               numeric(19,2)        null,
   RP1111               numeric(19,2)        null,
   RP1099               numeric(19,2)        null,
   RP1087               numeric(19,2)        null,
   RP1075               numeric(19,2)        null,
   RP1064               numeric(19,2)        null,
   RP1053               numeric(19,2)        null,
   RP1042               numeric(19,2)        null,
   RP1031               numeric(19,2)        null,
   RP1020               numeric(19,2)        null,
   RP1010               numeric(19,2)        null,
   RP1000               numeric(19,2)        null,
   RP990                numeric(19,2)        null,
   RP980                numeric(19,2)        null,
   RP971                numeric(19,2)        null,
   RP962                numeric(19,2)        null,
   RP952                numeric(19,2)        null,
   RP943                numeric(19,2)        null,
   RP935                numeric(19,2)        null,
   RP926                numeric(19,2)        null,
   RP917                numeric(19,2)        null,
   RP909                numeric(19,2)        null,
   RP901                numeric(19,2)        null,
   RP893                numeric(19,2)        null,
   RP885                numeric(19,2)        null,
   RP877                numeric(19,2)        null,
   RP870                numeric(19,2)        null,
   RP862                numeric(19,2)        null,
   RP855                numeric(19,2)        null,
   RP847                numeric(19,2)        null,
   RP840                numeric(19,2)        null,
   RP833                numeric(19,2)        null,
   RP826                numeric(19,2)        null,
   RP820                numeric(19,2)        null,
   RP813                numeric(19,2)        null,
   RP806                numeric(19,2)        null,
   RP800                numeric(19,2)        null,
   RP794                numeric(19,2)        null,
   RP787                numeric(19,2)        null,
   RP781                numeric(19,2)        null,
   RP775                numeric(19,2)        null,
   RP769                numeric(19,2)        null,
   RP763                numeric(19,2)        null,
   RP758                numeric(19,2)        null,
   RP752                numeric(19,2)        null,
   RP746                numeric(19,2)        null,
   RP741                numeric(19,2)        null,
   RP735                numeric(19,2)        null,
   RP730                numeric(19,2)        null,
   RP725                numeric(19,2)        null,
   RP719                numeric(19,2)        null,
   RP714                numeric(19,2)        null,
   RP709                numeric(19,2)        null,
   RP704                numeric(19,2)        null,
   RP699                numeric(19,2)        null,
   RP694                numeric(19,2)        null,
   RP690                numeric(19,2)        null,
   RP685                numeric(19,2)        null,
   RP680                numeric(19,2)        null,
   RP676                numeric(19,2)        null,
   RP671                numeric(19,2)        null,
   RP667                numeric(19,2)        null,
   RP662                numeric(19,2)        null,
   RP658                numeric(19,2)        null,
   RP654                numeric(19,2)        null,
   RP649                numeric(19,2)        null,
   RP645                numeric(19,2)        null,
   RP641                numeric(19,2)        null,
   RP637                numeric(19,2)        null,
   RP633                numeric(19,2)        null,
   RP629                numeric(19,2)        null,
   RP625                numeric(19,2)        null,
   RP621                numeric(19,2)        null,
   RP617                numeric(19,2)        null,
   RP613                numeric(19,2)        null,
   RP610                numeric(19,2)        null,
   RP606                numeric(19,2)        null,
   RP602                numeric(19,2)        null,
   RP599                numeric(19,2)        null,
   RP595                numeric(19,2)        null,
   RP592                numeric(19,2)        null,
   RP588                numeric(19,2)        null,
   RP585                numeric(19,2)        null,
   RP581                numeric(19,2)        null,
   RP578                numeric(19,2)        null,
   RP575                numeric(19,2)        null,
   RP571                numeric(19,2)        null,
   RP568                numeric(19,2)        null,
   RP565                numeric(19,2)        null,
   RP562                numeric(19,2)        null,
   RP559                numeric(19,2)        null,
   RP556                numeric(19,2)        null,
   RP552                numeric(19,2)        null,
   RP549                numeric(19,2)        null,
   RP546                numeric(19,2)        null,
   RP543                numeric(19,2)        null,
   RP541                numeric(19,2)        null,
   RP538                numeric(19,2)        null,
   RP535                numeric(19,2)        null,
   RP532                numeric(19,2)        null,
   RP529                numeric(19,2)        null,
   RP526                numeric(19,2)        null,
   RP524                numeric(19,2)        null,
   RP521                numeric(19,2)        null,
   RP518                numeric(19,2)        null,
   RP515                numeric(19,2)        null,
   RP513                numeric(19,2)        null,
   RP510                numeric(19,2)        null,
   RP508                numeric(19,2)        null,
   RP505                numeric(19,2)        null,
   RP503                numeric(19,2)        null,
   RP500                numeric(19,2)        null,
   RP498                numeric(19,2)        null,
   RP495                numeric(19,2)        null,
   RP493                numeric(19,2)        null,
   RP490                numeric(19,2)        null,
   RP488                numeric(19,2)        null,
   RP485                numeric(19,2)        null,
   RP483                numeric(19,2)        null,
   RP481                numeric(19,2)        null,
   RP478                numeric(19,2)        null,
   RP476                numeric(19,2)        null,
   RP474                numeric(19,2)        null,
   RP472                numeric(19,2)        null,
   RP469                numeric(19,2)        null,
   RP467                numeric(19,2)        null,
   RP465                numeric(19,2)        null,
   RP463                numeric(19,2)        null,
   RP461                numeric(19,2)        null,
   RP459                numeric(19,2)        null,
   RP457                numeric(19,2)        null,
   RP455                numeric(19,2)        null,
   RP452                numeric(19,2)        null,
   RP450                numeric(19,2)        null,
   RP448                numeric(19,2)        null,
   RP446                numeric(19,2)        null,
   RP444                numeric(19,2)        null,
   RP442                numeric(19,2)        null,
   RP441                numeric(19,2)        null,
   RP439                numeric(19,2)        null,
   RP437                numeric(19,2)        null,
   RP435                numeric(19,2)        null,
   RP433                numeric(19,2)        null,
   RP431                numeric(19,2)        null,
   RP429                numeric(19,2)        null,
   RP427                numeric(19,2)        null,
   RP426                numeric(19,2)        null,
   RP424                numeric(19,2)        null,
   RP422                numeric(19,2)        null,
   RP420                numeric(19,2)        null,
   RP418                numeric(19,2)        null,
   RP417                numeric(19,2)        null,
   RP415                numeric(19,2)        null,
   RP413                numeric(19,2)        null,
   RP412                numeric(19,2)        null,
   RP410                numeric(19,2)        null,
   RP408                numeric(19,2)        null,
   RP407                numeric(19,2)        null,
   RP405                numeric(19,2)        null,
   RP403                numeric(19,2)        null,
   RP402                numeric(19,2)        null,
   RP400                numeric(19,2)        null,
   RP398                numeric(19,2)        null,
   RP397                numeric(19,2)        null,
   RP395                numeric(19,2)        null,
   RP394                numeric(19,2)        null,
   RP392                numeric(19,2)        null,
   RP391                numeric(19,2)        null,
   RP390                numeric(19,2)        null,
   RP389                numeric(19,2)        null,
   RP388                numeric(19,2)        null,
   RP386                numeric(19,2)        null,
   RP385                numeric(19,2)        null,
   RP383                numeric(19,2)        null,
   RP382                numeric(19,2)        null,
   RP380                numeric(19,2)        null,
   RP379                numeric(19,2)        null,
   RP377                numeric(19,2)        null,
   RP376                numeric(19,2)        null,
   RP375                numeric(19,2)        null,
   RP373                numeric(19,2)        null,
   RP372                numeric(19,2)        null,
   RP370                numeric(19,2)        null,
   RP369                numeric(19,2)        null,
   RP368                numeric(19,2)        null,
   RP366                numeric(19,2)        null,
   RP365                numeric(19,2)        null,
   RP364                numeric(19,2)        null,
   RP362                numeric(19,2)        null,
   RP361                numeric(19,2)        null,
   RP360                numeric(19,2)        null,
   RP358                numeric(19,2)        null,
   RP357                numeric(19,2)        null,
   RP356                numeric(19,2)        null,
   RP355                numeric(19,2)        null,
   RP353                numeric(19,2)        null,
   RP352                numeric(19,2)        null,
   RP351                numeric(19,2)        null,
   RP350                numeric(19,2)        null,
   RP348                numeric(19,2)        null,
   RP347                numeric(19,2)        null,
   RP346                numeric(19,2)        null,
   RP345                numeric(19,2)        null,
   RP344                numeric(19,2)        null,
   RP342                numeric(19,2)        null,
   RP341                numeric(19,2)        null,
   RP340                numeric(19,2)        null,
   RP339                numeric(19,2)        null,
   RP338                numeric(19,2)        null,
   RP337                numeric(19,2)        null,
   RP336                numeric(19,2)        null,
   RP334                numeric(19,2)        null,
   RP333                numeric(19,2)        null,
   RP332                numeric(19,2)        null,
   RP331                numeric(19,2)        null,
   RP330                numeric(19,2)        null,
   RP329                numeric(19,2)        null,
   RP328                numeric(19,2)        null,
   RP327                numeric(19,2)        null,
   RP326                numeric(19,2)        null,
   RP325                numeric(19,2)        null,
   RP324                numeric(19,2)        null,
   RP323                numeric(19,2)        null,
   RP322                numeric(19,2)        null,
   RP321                numeric(19,2)        null,
   RP319                numeric(19,2)        null,
   RP318                numeric(19,2)        null,
   RP317                numeric(19,2)        null,
   RP316                numeric(19,2)        null,
   RP315                numeric(19,2)        null,
   RP314                numeric(19,2)        null,
   RP313                numeric(19,2)        null,
   RP312                numeric(19,2)        null,
   RP311                numeric(19,2)        null,
   RP310                numeric(19,2)        null,
   RP309                numeric(19,2)        null,
   RP308                numeric(19,2)        null,
   RP307                numeric(19,2)        null,
   RP306                numeric(19,2)        null,
   RP305                numeric(19,2)        null,
   RP304                numeric(19,2)        null,
   RP303                numeric(19,2)        null,
   RP302                numeric(19,2)        null,
   RP301                numeric(19,2)        null,
   RP300                numeric(19,2)        null,
   RP299                numeric(19,2)        null,
   RP298                numeric(19,2)        null,
   RP297                numeric(19,2)        null,
   RP296                numeric(19,2)        null,
   RP295                numeric(19,2)        null,
   RP294                numeric(19,2)        null,
   RP293                numeric(19,2)        null,
   RP292                numeric(19,2)        null,
   RP291                numeric(19,2)        null,
   RP290                numeric(19,2)        null,
   RP289                numeric(19,2)        null,
   RP288                numeric(19,2)        null,
   RP287                numeric(19,2)        null,
   RP286                numeric(19,2)        null,
   RP285                numeric(19,2)        null,
   RP284                numeric(19,2)        null,
   RP283                numeric(19,2)        null,
   RP282                numeric(19,2)        null,
   RP281                numeric(19,2)        null,
   RP280                numeric(19,2)        null,
   RP279                numeric(19,2)        null,
   RP278                numeric(19,2)        null,
   RP277                numeric(19,2)        null,
   RP276                numeric(19,2)        null,
   RP275                numeric(19,2)        null,
   RP274                numeric(19,2)        null,
   RP273                numeric(19,2)        null,
   RP272                numeric(19,2)        null,
   RP271                numeric(19,2)        null,
   RP270                numeric(19,2)        null,
   RP269                numeric(19,2)        null,
   RP268                numeric(19,2)        null,
   RP267                numeric(19,2)        null,
   RP266                numeric(19,2)        null,
   RP265                numeric(19,2)        null,
   RP264                numeric(19,2)        null,
   RP263                numeric(19,2)        null,
   RP262                numeric(19,2)        null,
   RP261                numeric(19,2)        null,
   RP260                numeric(19,2)        null,
   RP259                numeric(19,2)        null,
   RP258                numeric(19,2)        null,
   RP257                numeric(19,2)        null,
   RP256                numeric(19,2)        null,
   RP255                numeric(19,2)        null,
   RP254                numeric(19,2)        null,
   RP253                numeric(19,2)        null,
   RP252                numeric(19,2)        null,
   RP251                numeric(19,2)        null,
   RP250                numeric(19,2)        null,
   RP249                numeric(19,2)        null,
   RP248                numeric(19,2)        null,
   RP247                numeric(19,2)        null,
   RP246                numeric(19,2)        null,
   RP245                numeric(19,2)        null,
   RP244                numeric(19,2)        null,
   RP243                numeric(19,2)        null,
   RP242                numeric(19,2)        null,
   RP241                numeric(19,2)        null,
   RP240                numeric(19,2)        null,
   RP239                numeric(19,2)        null,
   RP238                numeric(19,2)        null,
   RP237                numeric(19,2)        null,
   RP236                numeric(19,2)        null,
   RP235                numeric(19,2)        null,
   RP234                numeric(19,2)        null,
   RP233                numeric(19,2)        null,
   RP232                numeric(19,2)        null,
   RP231                numeric(19,2)        null,
   RP230                numeric(19,2)        null,
   RP229                numeric(19,2)        null,
   RP228                numeric(19,2)        null,
   RP227                numeric(19,2)        null,
   RP226                numeric(19,2)        null,
   RP225                numeric(19,2)        null,
   RP224                numeric(19,2)        null,
   RP223                numeric(19,2)        null,
   RP222                numeric(19,2)        null,
   RP221                numeric(19,2)        null,
   RP220                numeric(19,2)        null,
   RP219                numeric(19,2)        null,
   RP218                numeric(19,2)        null,
   RP217                numeric(19,2)        null,
   RP216                numeric(19,2)        null,
   RP215                numeric(19,2)        null,
   RP214                numeric(19,2)        null,
   RP213                numeric(19,2)        null,
   RP212                numeric(19,2)        null,
   RP211                numeric(19,2)        null,
   RP210                numeric(19,2)        null,
   RP209                numeric(19,2)        null,
   RP208                numeric(19,2)        null,
   RP207                numeric(19,2)        null,
   RP206                numeric(19,2)        null,
   RP205                numeric(19,2)        null,
   RP204                numeric(19,2)        null,
   RP203                numeric(19,2)        null,
   RP202                numeric(19,2)        null,
   RP201                numeric(19,2)        null,
   RP200                numeric(19,2)        null,
   RP199                numeric(19,2)        null,
   RP198                numeric(19,2)        null,
   RP197                numeric(19,2)        null,
   RP196                numeric(19,2)        null,
   RP195                numeric(19,2)        null,
   RP194                numeric(19,2)        null,
   RP193                numeric(19,2)        null,
   RP192                numeric(19,2)        null,
   RP191                numeric(19,2)        null,
   RP190                numeric(19,2)        null,
   RP189                numeric(19,2)        null,
   RP188                numeric(19,2)        null,
   RP187                numeric(19,2)        null,
   RP186                numeric(19,2)        null,
   RP185                numeric(19,2)        null,
   RP184                numeric(19,2)        null,
   RP183                numeric(19,2)        null,
   RP182                numeric(19,2)        null,
   RP181                numeric(19,2)        null,
   RP180                numeric(19,2)        null,
   RP179                numeric(19,2)        null,
   RP178                numeric(19,2)        null,
   RP177                numeric(19,2)        null,
   RP176                numeric(19,2)        null,
   RP175                numeric(19,2)        null,
   RP174                numeric(19,2)        null,
   RP173                numeric(19,2)        null,
   RP172                numeric(19,2)        null,
   RP171                numeric(19,2)        null,
   RP170                numeric(19,2)        null,
   RP169                numeric(19,2)        null,
   RP168                numeric(19,2)        null,
   RP167                numeric(19,2)        null,
   RP166                numeric(19,2)        null,
   RP165                numeric(19,2)        null,
   RP164                numeric(19,2)        null,
   RP163                numeric(19,2)        null,
   RP162                numeric(19,2)        null,
   RP161                numeric(19,2)        null,
   RP160                numeric(19,2)        null,
   RP159                numeric(19,2)        null,
   RP158                numeric(19,2)        null,
   RP157                numeric(19,2)        null,
   RP156                numeric(19,2)        null,
   RP155                numeric(19,2)        null,
   RP154                numeric(19,2)        null,
   RP153                numeric(19,2)        null,
   RP152                numeric(19,2)        null,
   RP151                numeric(19,2)        null,
   RP150                numeric(19,2)        null,
   RP149                numeric(19,2)        null,
   RP148                numeric(19,2)        null,
   RP147                numeric(19,2)        null,
   RP146                numeric(19,2)        null,
   RP145                numeric(19,2)        null,
   RP144                numeric(19,2)        null,
   RP143                numeric(19,2)        null,
   RP142                numeric(19,2)        null,
   RP141                numeric(19,2)        null,
   RP140                numeric(19,2)        null,
   RP139                numeric(19,2)        null,
   RP138                numeric(19,2)        null,
   RP137                numeric(19,2)        null,
   RP136                numeric(19,2)        null,
   RP135                numeric(19,2)        null,
   RP134                numeric(19,2)        null,
   RP133                numeric(19,2)        null,
   RP132                numeric(19,2)        null,
   RP131                numeric(19,2)        null,
   RP130                numeric(19,2)        null,
   RP129                numeric(19,2)        null,
   RP128                numeric(19,2)        null,
   RP127                numeric(19,2)        null,
   RP126                numeric(19,2)        null,
   RP125                numeric(19,2)        null,
   RP124                numeric(19,2)        null,
   RP123                numeric(19,2)        null,
   RP122                numeric(19,2)        null,
   RP121                numeric(19,2)        null,
   RP120                numeric(19,2)        null,
   RP119                numeric(19,2)        null,
   RP118                numeric(19,2)        null,
   RP117                numeric(19,2)        null,
   RP116                numeric(19,2)        null,
   RP115                numeric(19,2)        null,
   RP114                numeric(19,2)        null,
   RP113                numeric(19,2)        null,
   RP112                numeric(19,2)        null,
   RP111                numeric(19,2)        null,
   RP110                numeric(19,2)        null,
   RP109                numeric(19,2)        null,
   RP108                numeric(19,2)        null,
   RP107                numeric(19,2)        null,
   RP106                numeric(19,2)        null,
   RP105                numeric(19,2)        null,
   RP104                numeric(19,2)        null,
   RP103                numeric(19,2)        null,
   RP102                numeric(19,2)        null,
   RP101                numeric(19,2)        null,
   RP100                numeric(19,2)        null,
   RP99                 numeric(19,2)        null,
   RP98                 numeric(19,2)        null,
   RP97                 numeric(19,2)        null,
   RP96                 numeric(19,2)        null,
   RP95                 numeric(19,2)        null,
   RP94                 numeric(19,2)        null,
   RP93                 numeric(19,2)        null,
   RP92                 numeric(19,2)        null,
   RP91                 numeric(19,2)        null,
   RP90                 numeric(19,2)        null,
   RP89                 numeric(19,2)        null,
   RP88                 numeric(19,2)        null,
   RP87                 numeric(19,2)        null,
   RP86                 numeric(19,2)        null,
   RP85                 numeric(19,2)        null,
   RP84                 numeric(19,2)        null,
   RP83                 numeric(19,2)        null,
   RP82                 numeric(19,2)        null,
   RP81                 numeric(19,2)        null,
   RP80                 numeric(19,2)        null,
   RP79                 numeric(19,2)        null,
   RP78                 numeric(19,2)        null,
   RP77                 numeric(19,2)        null,
   RP76                 numeric(19,2)        null,
   RP75                 numeric(19,2)        null,
   RP74                 numeric(19,2)        null,
   RP73                 numeric(19,2)        null,
   RP72                 numeric(19,2)        null,
   RP71                 numeric(19,2)        null,
   RP70                 numeric(19,2)        null,
   RP69                 numeric(19,2)        null,
   RP68                 numeric(19,2)        null,
   RP67                 numeric(19,2)        null,
   RP66                 numeric(19,2)        null,
   RP65                 numeric(19,2)        null,
   RP64                 numeric(19,2)        null,
   RP63                 numeric(19,2)        null,
   RP62                 numeric(19,2)        null,
   RP61                 numeric(19,2)        null,
   RP60                 numeric(19,2)        null,
   RP59                 numeric(19,2)        null,
   RP58                 numeric(19,2)        null,
   RP57                 numeric(19,2)        null,
   RP56                 numeric(19,2)        null,
   RP55                 numeric(19,2)        null,
   RP54                 numeric(19,2)        null,
   RP53                 numeric(19,2)        null,
   RP52                 numeric(19,2)        null,
   RP51                 numeric(19,2)        null,
   RP50                 numeric(19,2)        null,
   RP49                 numeric(19,2)        null,
   RP48                 numeric(19,2)        null,
   RP47                 numeric(19,2)        null,
   RP46                 numeric(19,2)        null,
   RP45                 numeric(19,2)        null,
   RP44                 numeric(19,2)        null,
   RP43                 numeric(19,2)        null,
   RP42                 numeric(19,2)        null,
   RP41                 numeric(19,2)        null,
   RP40                 numeric(19,2)        null,
   RP39                 numeric(19,2)        null,
   RP38                 numeric(19,2)        null,
   RP37                 numeric(19,2)        null,
   RP36                 numeric(19,2)        null,
   RP35                 numeric(19,2)        null,
   RP34                 numeric(19,2)        null,
   RP33                 numeric(19,2)        null,
   RP32                 numeric(19,2)        null,
   RP31                 numeric(19,2)        null,
   RP30                 numeric(19,2)        null,
   RP29                 numeric(19,2)        null,
   RP28                 numeric(19,2)        null,
   RP27                 numeric(19,2)        null,
   RP26                 numeric(19,2)        null,
   RP25                 numeric(19,2)        null,
   RP24                 numeric(19,2)        null,
   RP23                 numeric(19,2)        null,
   RP22                 numeric(19,2)        null,
   RP21                 numeric(19,2)        null,
   RP20                 numeric(19,2)        null,
   RP19                 numeric(19,2)        null,
   RP18                 numeric(19,2)        null,
   RP17                 numeric(19,2)        null,
   RP16                 numeric(19,2)        null,
   RP15                 numeric(19,2)        null,
   RP14                 numeric(19,2)        null,
   RP13                 numeric(19,2)        null,
   RP12                 numeric(19,2)        null,
   RP11                 numeric(19,2)        null,
   RP10                 numeric(19,2)        null,
   RP9                  numeric(19,2)        null,
   RP8                  numeric(19,2)        null,
   RP7                  numeric(19,2)        null,
   RP6                  numeric(19,2)        null,
   RP5                  numeric(19,2)        null,
   RP4                  numeric(19,2)        null,
   RP3                  numeric(19,2)        null,
   RP2                  numeric(19,2)        null,
   constraint PK_SUMMARYSTATISTICSDETAIL primary key (SummaryStatisticsDetailId)
)
go

/*==============================================================*/
/* Table: Tag                                                   */
/*==============================================================*/
create table dbo.Tag (
   TagId                numeric(19,0)        not null,
   Entity               int                  null,
   TagName              varchar(150)         null,
   CreatedBy            int                  null,
   CreatedDate          datetime             null,
   DefaultColour        varchar(8)           null,
   constraint PK_TAG primary key (TagId)
)
go

/*==============================================================*/
/* Table: TargetRAP                                             */
/*==============================================================*/
create table dbo.TargetRAP (
   TargetRAPId          int                  not null,
   ModellingVendor      varchar(255)         collate Latin1_General_CI_AS null,
   ModellingSystem      varchar(255)         collate Latin1_General_CI_AS null,
   ModellingSystemVersion varchar(255)         collate Latin1_General_CI_AS null,
   TargetRAPCode        varchar(255)         collate Latin1_General_CI_AS null,
   TargetRAPDesc        varchar(255)         collate Latin1_General_CI_AS null,
   PETId                int                  null,
   SourceRAPCode        varchar(255)         collate Latin1_General_CI_AS null,
   IsSCORGenerated      bit                  null,
   IsSCORCurrent        bit                  null,
   IsSCORDefault        bit                  null,
   IsActive             bit                  null,
   constraint PK__TargetRa__339B9E01AC396F39 primary key (TargetRAPId)
         on "PRIMARY"
)
on "PRIMARY"
go

/*==============================================================*/
/* Table: "User"                                                */
/*==============================================================*/
create table dbo."User" (
   UserId               int                  not null,
   Entity               int                  null,
   UserCode             varchar(15)          null,
   FirstName            varchar(15)          null,
   LastName             varchar(15)          null,
   OmegaUser            varchar(15)          null,
   WindowsUser          varchar(15)          null,
   constraint PK_USER primary key (UserId)
)
go

/*==============================================================*/
/* Table: UserDashboard                                         */
/*==============================================================*/
create table UserDashboard (
   UserDashboardId      bigint               not null,
   UserId               int                  null,
   DashboardName        varchar(255)         null,
   SeacrhMode           varchar(10)          null,
   IsVisible            bit                  null,
   DashboardSequence    int                  null,
   constraint PK_USERDASHBOARD primary key (UserDashboardId)
)
go

/*==============================================================*/
/* Table: UserDashboardWidget                                   */
/*==============================================================*/
create table UserDashboardWidget (
   UserDashboardWidgetId bigint               not null,
   UserDashboardId      bigint               null,
   DashboardWidgetId    bigint               null,
   UserAssignedName     vachar(255)          null,
   RowPosition          int                  null,
   ColPosition          int                  null,
   RowSpan              int                  null,
   ColSpan              int                  null,
   MinItemRows          int                  null,
   MinItemCols          int                  null,
   UserId               int                  null,
   constraint PK_USERDASHBOARDWIDGET primary key (UserDashboardWidgetId)
)
go

/*==============================================================*/
/* Table: UserDashboardWidgetColumns                            */
/*==============================================================*/
create table UserDashboardWidgetColumns (
   UserDashboardWidgetColumnsId bigint               not null,
   UserDashboardWidgetId bigint               null,
   DashboardColumnName  varchar(255)         null,
   DashboardColumnWidth int                  null,
   DashboardColumnOrder int                  null,
   IsVisible            bit                  null,
   FilterCriteria       varchar(255)         null,
   Sort                 int                  null,
   SortType             varchar(25)          null,
   UserId               int                  null,
   ColumnHeader         varchar(255)         null,
   DataType             varchar(50)          null,
   DataColumnType       varchar(50)          null,
   constraint PK_USERDASHBOARDWIDGETCOLUMNS primary key (UserDashboardWidgetColumnsId)
)
go

/*==============================================================*/
/* Table: UserPreferences                                       */
/*==============================================================*/
create table dbo.UserPreferences (
   UserPreferencesId    int                  not null,
   Entity               int                  null,
   UserId               int                  null,
   Category             varchar(125)         null,
   Value                VarChar(125)         null,
   CreateDate           datetime             null,
   UpdateDate           datetime             null,
   constraint PK_USERPREFERENCES primary key (UserPreferencesId)
)
go

/*==============================================================*/
/* Table: UserRLDataSource                                      */
/*==============================================================*/
create table dbo.UserRLDataSource (
   UserRLDataSourceId   bigint               not null,
   Entity               int                  null,
   UserId               bigint               null,
   InstanceId           varchar(50)          null,
   InstanceName         varchar(255)         null,
   DataSourceType       varchar(5)           null,
   DataSourceId         bigint               null,
   DataSourceName       varchar(255)         null,
   ProjectId            bigint               null,
   constraint PK_USERRLDATASOURCE primary key (UserRLDataSourceId)
)
go

/*==============================================================*/
/* Table: UserRole                                              */
/*==============================================================*/
create table dbo.UserRole (
   UserRoleId           int                  not null,
   UserId               int                  null,
   RoleId               int                  null,
   Active               int                  null,
   Updated              datetime             null,
   UpdatedBy            int                  null,
   constraint PK_USERROLE primary key (UserRoleId)
)
go

/*==============================================================*/
/* Table: UserTablePreferences                                  */
/*==============================================================*/
create table dbo.UserTablePreferences (
   UserTablePreferencesId int                  not null,
   Entity               int                  null,
   UserId               int                  null,
   UIPage               varchar(125)         null,
   TableName            varchar(125)         null,
   UserColumns          varchar(1000)        null,
   CreatedDate          datetime             null,
   ModeifiedDate        datetime             null,
   constraint PK_USERTABLEPREFERENCES primary key (UserTablePreferencesId)
)
go

/*==============================================================*/
/* Table: UserTag                                               */
/*==============================================================*/
create table dbo.UserTag (
   UserTagId            numeric(19,0)        not null,
   TagId                numeric(19,0)        null,
   "User"               int                  null,
   UserOverrideColour   varchar(8)           null,
   CreatedDate          datetime             null,
   ModifiedDate         datetime             null,
   constraint PK_USERTAG primary key (UserTagId)
)
go

/*==============================================================*/
/* Table: Workspace                                             */
/*==============================================================*/
create table dbo.Workspace (
   WorkspaceId          numeric(19,0)        not null,
   Entity               int                  null,
   WorkspaceContext     varchar(40)          null,
   WorkspaceContextCode varchar(55)          collate Latin1_General_CI_AS null,
   WorkspaceUwYear      int                  null,
   WorkspaceName        varchar(255)         collate Latin1_General_CI_AS null,
   WorkspaceMarketChannel varchar(5)           null,
   ClentName            varchar(255)         collate Latin1_General_CI_AS null,
   constraint PK__Workspac__C84765D15FE14184 primary key (WorkspaceId)
         on "PRIMARY"
)
on "PRIMARY"
go

alter table dbo.AccumulationPackage
   add constraint FKWorkspaceId_Accum foreign key (WorkspaceId)
      references dbo.Workspace (WorkspaceId)
go

alter table dbo.AccumulationPackageAttachedPLT
   add constraint FKAccumulationPackageId_PLT foreign key (AccumulationPackageId)
      references dbo.AccumulationPackage (AccumulationPackageId)
go

alter table dbo.AccumulationPackageAttachedPLT
   add constraint FKPLTHeaderId_Accum foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.AccumulationPackageOverrideSection
   add constraint FKAccumulationPackageID_Override foreign key (AccumulationPackageId)
      references dbo.AccumulationPackage (AccumulationPackageId)
go

alter table AccumulationProfile
   add constraint FK_ACCUMULA_REFERENCE_ENTITY foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table AccumulationProfileDetail
   add constraint FK_ACCUMULA_REFERENCE_ENTITY foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table AccumulationProfileDetail
   add constraint FK_ACCUMULA_REFERENCE_ACCUMULA foreign key (AccumulationProfileId)
      references AccumulationProfile (AccumulationProfileId)
go

alter table AccumulationProfileDetail
   add constraint FK_ACCUMULA_REFERENCE_ACCUMULA foreign key (AccumulationRAPId)
      references AccumulationRAP (AccumulationRAPId)
go

alter table AccumulationRAP
   add constraint FK_ACCUMULA_REFERENCE_ENTITY foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table AccumulationRAPDetail
   add constraint FK_ACCUMULA_REFERENCE_ENTITY foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table AccumulationRAPDetail
   add constraint FK_ACCUMULA_REFERENCE_ACCUMULA foreign key (AccumulationRAPId)
      references AccumulationRAP (AccumulationRAPId)
go

alter table dbo.AdjustmentNode
   add constraint FKAdjustmentProcessingRecapId_Node foreign key (AdjustmentProcessingRecapId)
      references dbo.AdjustmentProcessingRecap (AdjustmentProcessingRecapId)
go

alter table dbo.AdjustmentNode
   add constraint FKAdjustmentThreadId_Node foreign key (AdjustmentThreadId)
      references dbo.AdjustmentThread (AdjustmentThreadId)
go

alter table dbo.AdjustmentNodeProcessing
   add constraint FKAdjustmentNodeId_Proc foreign key (AdjustmentNodeId)
      references dbo.AdjustmentNode (AdjustmentNodeId)
go

alter table dbo.AdjustmentOrder
   add constraint FKAdjustmentNodeId_Order foreign key (AdjustmentNodeId)
      references dbo.AdjustmentNode (AdjustmentNodeId)
go

alter table dbo.AdjustmentOrder
   add constraint FKAdjustmentThreadId_Order foreign key (AdjustmentThreadId)
      references dbo.AdjustmentThread (AdjustmentThreadId)
go

alter table dbo.AnalysisIncludedTargetRAP
   add constraint FKRRAnalysisId_IncRAP foreign key (ModelAnalysisId)
      references dbo.ModelAnalysis (ModelAnalysisId)
go

alter table dbo.AnalysisIncludedTargetRAP
   add constraint FKTargetRAPId_IncRAP foreign key (TargetRAPId)
      references dbo.TargetRAP (TargetRAPId)
go

alter table dbo.CalibrationTemplateNode
   add constraint FKCalibrationTemplateHeaderId_Node foreign key (CalibrationTemplateHeaderId)
      references dbo.CalibrationTemplateHeader (CalibrationTemplateHeaderId)
go

alter table dbo.CalibrationTemplateOrder
   add constraint FKCalibrationTemplateHeaderId_Order foreign key (CalibrationTemplateHeaderId)
      references dbo.CalibrationTemplateHeader (CalibrationTemplateHeaderId)
go

alter table dbo.CalibrationTemplateOrder
   add constraint FKCalibrationTemplateNodeId_Order foreign key (CalibrationTemplateNodeId)
      references dbo.CalibrationTemplateNode (CalibrationTemplateNodeId)
go

alter table dbo.CalibrationTemplateParameter
   add constraint FKCalibrationTemplateNodeId_Param foreign key (CalibrationTemplateNodeId)
      references dbo.CalibrationTemplateNode (CalibrationTemplateNodeId)
go

alter table DashboardWidget
   add constraint FK_DASHBOAR_REFERENCE_ENTITY foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table DashboardWidget
   add constraint FK_DASHBOAR_REFERENCE_REFDASHB foreign key (RefDashboardWidgetTypeId)
      references RefDashboardWidgetType (RefDashboardWidgetTypeId)
go

alter table DashboardWidgetColumn
   add constraint FK_DASHBOAR_REFERENCE_DASHBOAR foreign key (DashboardWidgetId)
      references DashboardWidget (DashboardWidgetId)
go

alter table dbo.EPCurveHeader
   add constraint FKPLTHeaderId_Curve foreign key (LossDataId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.EPCurveHeader
   add constraint FKRRLossDataHeaderId_Curve foreign key (LossDataId)
      references dbo.LossDataHeader (LossDataHeaderId)
go

alter table dbo.EventBasedAdjustmentParameter
   add constraint FKAdjustmentNodeId_Event foreign key (AdjustmentNodeId)
      references dbo.AdjustmentNode (AdjustmentNodeId)
go

alter table dbo.ExposureSummaryData
   add constraint FKGlobalExposureViewSummary_Data foreign key (GlobalExposureViewSummaryId)
      references dbo.GlobalExposureViewSummary (GlobalExposureViewSummaryId)
go

alter table dbo.FileBasedImportConfig
   add constraint FKProjectImportRunId_FileBased foreign key (ProjectImportRunId)
      references dbo.ProjectImportRun (ProjectImportRunId)
go

alter table dbo.FileImportSourceResult
   add constraint FKFileBasedImportConfigId_Source foreign key (FileBasedImportConfig)
      references dbo.FileBasedImportConfig (FileBasedImportConfig)
go

alter table dbo.GenericAdjustmentNode
   add constraint FKGenericAdjustmentSetId_Node foreign key (GenericAdjustmentSetId)
      references dbo.GenericAdjustmentSet (GenericAdjustmentSetId)
go

alter table dbo.GenericAdjustmentParameter
   add constraint FKGenericAdjustmentNodeId_Param foreign key (GenericAdjustmentNodeId)
      references dbo.GenericAdjustmentNode (GenericAdjustmentNodeId)
go

alter table dbo.GenericSetAdjustmentOrder
   add constraint FKGenericAdjustmentNodeId_Order foreign key (GenericAdjustmentNodeId)
      references dbo.GenericAdjustmentNode (GenericAdjustmentNodeId)
go

alter table dbo.GenericSetAdjustmentOrder
   add constraint FKGenericAdjustmentSetId_Order foreign key (GenericAdjustmentSetId)
      references dbo.GenericAdjustmentSet (GenericAdjustmentSetId)
go

alter table dbo.GlobalExposureView
   add constraint FKProjectID_GlobalExposure foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

alter table dbo.GlobalExposureViewSummary
   add constraint FKGlobalExposureViewID_Summary foreign key (GlobalExposureViewId)
      references dbo.GlobalExposureView (GlobalExposureViewId)
go

alter table dbo.InuringCanvasLayout
   add constraint FKInuringPackageId_Canvas foreign key (InuringPackageId)
      references dbo.InuringPackage (InuringPackageId)
go

alter table dbo.InuringContractLayer
   add constraint FKInuringContractNodeId_Layer foreign key (InuringContractNodeId)
      references dbo.InuringContractNode (InuringContractNodeId)
go

alter table dbo.InuringContractLayerParam
   add constraint FKInuringContractLayerId_Param foreign key (InuringContractLayerId)
      references dbo.InuringContractLayer (InuringContractLayerId)
go

alter table dbo.InuringContractLayerPerilLimit
   add constraint FKInuringContractLayerId_Peril foreign key (InuringContractLayerId)
      references dbo.InuringContractLayer (InuringContractLayerId)
go

alter table dbo.InuringContractLayerReinstatementDetail
   add constraint FKInuringContractLayerId_Reinst foreign key (InuringContractLayerId)
      references dbo.InuringContractLayer (InuringContractLayerId)
go

alter table dbo.InuringContractNode
   add constraint FKInuringPackageId_Node foreign key (InuringPackageId)
      references dbo.InuringPackage (InuringPackageId)
go

alter table dbo.InuringEdge
   add constraint FKInuringPackageId_Edge foreign key (InuringPackageId)
      references dbo.InuringPackage (InuringPackageId)
go

alter table InuringFinalAttachedPLT
   add constraint FKInuringFinalNodeId_PLT foreign key (InuringFinalNodeId)
      references dbo.InuringFinalNode (InuringFinalNodeId)
go

alter table InuringFinalAttachedPLT
   add constraint FKPLTHeaderId_InuringFinal foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.InuringFinalNode
   add constraint FKInuringPackageId_Final foreign key (InuringPackageId)
      references dbo.InuringPackage (InuringPackageId)
go

alter table dbo.InuringInputAttachedPLT
   add constraint FKInuringInputNodeId_PLT foreign key (InuringInputNodeId)
      references dbo.InuringInputNode (InuringInputNodeId)
go

alter table dbo.InuringInputAttachedPLT
   add constraint FKPLTHeaderId_Inuring foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.InuringInputNode
   add constraint FKInuringpackageId_Input foreign key (InuringPackageId)
      references dbo.InuringPackage (InuringPackageId)
go

alter table InuringNote
   add constraint FKInuringPackageId_Note foreign key (InuringPackageId)
      references dbo.InuringPackage (InuringPackageId)
go

alter table dbo.InuringPackage
   add constraint FKWorkspaceId_Inuring foreign key (WorkspaceId)
      references dbo.Workspace (WorkspaceId)
go

alter table dbo.InuringPackageProcessing
   add constraint FKInuringPackageId_Proc foreign key (InuringPackageId)
      references dbo.InuringPackage (InuringPackageId)
go

alter table dbo.InuringPackageProcessingExchangeRate
   add constraint FKInuringPackageProcessingId_Rate foreign key (InuringPackageProcessingId)
      references dbo.InuringPackageProcessing (InuringPackageProcessingId)
go

alter table dbo.LossDataHeader
   add constraint FKRRAnalysisId_RRLoss foreign key (ModelAnalysisId)
      references dbo.ModelAnalysis (ModelAnalysisId)
go

alter table dbo.ModelAnalysis
   add constraint FKProjectId_Analysis foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

alter table dbo.ModelAnalysis
   add constraint FKProjectImportRunId_RRAnalysis foreign key (ProjectImportRunId)
      references dbo.ProjectImportRun (ProjectImportRunId)
go

alter table dbo.ModelPortfolio
   add constraint FKProjectID_Portfolio foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

alter table dbo.OmegaCountryPeril
   add constraint FKContractSectionId_CP foreign key (OmegaSectionId)
      references dbo.OmegaSection (OmegaSectionId)
go

alter table dbo.OmegaReinstatement
   add constraint FKContractSectionId_Reins foreign key (OmegaSectionId)
      references dbo.OmegaSection (OmegaSectionId)
go

alter table dbo.OmegaSection
   add constraint FKContractId_Section foreign key (OmegaTreatyId)
      references dbo.OmegaTreaty (OmegaTreatyId)
go

alter table dbo.OmegaTermsAndCondition
   add constraint FKContractSectionId_TnC foreign key (OmegaSectionId)
      references dbo.OmegaSection (OmegaSectionId)
go

alter table dbo.OmegaTreaty
   add constraint FKClientId_Contract foreign key (ClientId)
      references dbo.Client (OmegaClientId)
go

alter table dbo.PLTHeader
   add constraint FKCurrencyCode_PLT foreign key (CurrencyCode)
      references dbo.Currency (CurrencyCode)
go

alter table dbo.PLTHeader
   add constraint FKRRAnalysisId_PLT foreign key (ModelAnalysisId)
      references dbo.ModelAnalysis (ModelAnalysisId)
go

alter table dbo.PLTHeader
   add constraint FKRegionPerilId_PLT foreign key (RegionPerilId)
      references dbo.RegionPeril (RegionPerilId)
go

alter table dbo.PLTHeader
   add constraint FKTargetRAPId_PLT foreign key (TargetRAPId)
      references dbo.TargetRAP (TargetRAPId)
go

alter table dbo.PLTHeaderTag
   add constraint FKPLTHeaderId_Tag foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.PLTHeaderTag
   add constraint FKUserTagId_PLT foreign key (TagId)
      references dbo.Tag (TagId)
go

alter table dbo.PLTPricingLossEngine
   add constraint FKPLTPricingId_Engine foreign key (PLTPricingId)
      references dbo.PLTPricing (PLTPricingId)
go

alter table dbo.PLTPricingLossEngine
   add constraint FKPLTPricingSectionId_Engine foreign key (PLTPricingSectionId)
      references dbo.PLTPricingSection (PLTPricingSectionId)
go

alter table dbo.PLTPricingMinimumGrain
   add constraint FKPLTPricingId_RegionPeril foreign key (PLTPricingId)
      references dbo.PLTPricing (PLTPricingId)
go

alter table dbo.PLTPricingMinimumGrain
   add constraint FKPLTPricingLossEngineId_RegionPeril foreign key (PLTPricingLossEngineId)
      references dbo.PLTPricingLossEngine (PLTPricingLossEngineId)
go

alter table dbo.PLTPricingMinimumGrain
   add constraint FKPLTPricingSectionId_RegionPeril foreign key (PLTPricingSectionId)
      references dbo.PLTPricingSection (PLTPricingSectionId)
go

alter table dbo.PLTPricingSection
   add constraint FKPLTPricingId_Section foreign key (PLTPricingId)
      references dbo.PLTPricing (PLTPricingId)
go

alter table dbo.Project
   add constraint FKProjectImportRunId_Project foreign key (ProjectImportRunId)
      references dbo.ProjectImportRun (ProjectImportRunId)
go

alter table dbo.Project
   add constraint FKRLDataSourceId_Proj foreign key (RLDataSourceId)
      references dbo.RLDataSource (RLDataSourceId)
go

alter table dbo.Project
   add constraint FKWorkspaceId_Proj foreign key (WorkspaceId)
      references dbo.Workspace (WorkspaceId)
go

alter table dbo.ProjectConfigurationForeWriter
   add constraint FKProjectId_FW foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

alter table dbo.ProjectConfigurationForeWriterContract
   add constraint FKprojectConfigurationForeWriterId_C foreign key (ProjectConfigurationForewriterId)
      references dbo.ProjectConfigurationForeWriter (ProjectConfigurationForeWriterId)
go

alter table dbo.ProjectConfigurationForeWriterDivision
   add constraint FKProjectConfigurationForeWriterContractId foreign key (ProjectConfigurationFWDivisionsContractId)
      references dbo.ProjectConfigurationForeWriterContract (ProjectConfigurationForeWriterContract__)
go

alter table dbo.ProjectConfigurationForeWriterFiles
   add constraint FKprojectConfigurationForeWriterId_F foreign key (ProjectConfigurationForeWriterId)
      references dbo.ProjectConfigurationForeWriter (ProjectConfigurationForeWriterId)
go

alter table dbo.ProjectConfigurationMGA
   add constraint FKProjectId_MGA foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

alter table dbo.ProjectForewriterExpectedScope
   add constraint FKProject_ExpScope foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

alter table dbo.PublicationARC
   add constraint FKPLTHeaderId_ARC foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.PublicationPricingForeWriter
   add constraint FKPLTHeaderId_FW foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.PublicationPricingxAct
   add constraint FKPLTHeaderId_xAct foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.RLAnalysisProfileRegion
   add constraint FKModelAnalysisID foreign key (RLModelAnalysisId)
      references dbo.RLModelAnalysis (RLModelAnalysisId)
go

alter table dbo.RLImportDivisionSelection
   add constraint FKRLImportSelection_Division foreign key (RLImportSelectionId)
      references dbo.RLImportSelection (RLImportSelectionId)
go

alter table dbo.RLImportFinancialPerspectiveSelection
   add constraint FKRLImportSelectionId_FinPer foreign key (RLImportSelectionId)
      references dbo.RLImportSelection (RLImportSelectionId)
go

alter table dbo.RLImportSelection
   add constraint FKProjectId_SourceResult foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

alter table dbo.RLImportSelection
   add constraint FKRLAnalysisId_SourceResult foreign key (RLModelAnalysisId)
      references dbo.RLModelAnalysis (RLModelAnalysisId)
go

alter table dbo.RLImportTargetRAPSelection
   add constraint FKRLImportSectionID_TargetRAP foreign key (RLImportSelectionId)
      references dbo.RLImportSelection (RLImportSelectionId)
go

alter table dbo.RLModelAnalysis
   add constraint FKProjectId_RL foreign key (ProjectId)
      references dbo.Project (ProjectId)
go

alter table dbo.RLModelAnalysis
   add constraint FKRLModelDataSourceId_Analysis foreign key (RLDataSourceId)
      references dbo.RLDataSource (RLDataSourceId)
go

alter table dbo.RLPortfolio
   add constraint FKRLPortfolioID_Source foreign key (RLDataSourceId)
      references dbo.RLDataSource (RLDataSourceId)
go

alter table dbo.RLPortfolioAnalysisRegion
   add constraint FKRLPortfolioID_Region foreign key (RLPortfolioId)
      references dbo.RLPortfolio (RLPortfolioId)
go

alter table dbo.RLPortfolioSelection
   add constraint FKRLPortfolioID_Secect foreign key (RLPortfolioId)
      references dbo.RLPortfolio (RLPortfolioId)
go

alter table dbo.RLSourceEPHeader
   add constraint FKModelAnalysisID_EP foreign key (RLModelAnalysisId)
      references dbo.RLModelAnalysis (RLModelAnalysisId)
go

alter table dbo.RefAnalysisRegion
   add constraint FKEnity_AnaReg foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.RefAnalysisRegionMapping
   add constraint FKEntity_RegMap foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.RefDefaultReturnPeriod
   add constraint FKEntity_ReturnPeriod foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.RefExchangeRate
   add constraint FKEntity_ExRate foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.RefFMFContractAttribute
   add constraint FKEntity_Attr foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.RefFMFContractType
   add constraint FKEntity_Type foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.RefFMFContractTypeAttributeMap
   add constraint FKContractAttributeId foreign key (ContractAttributeID)
      references dbo.RefFMFContractAttribute (ContractAttributeId)
go

alter table dbo.RefFMFContractTypeAttributeMap
   add constraint FKContractTypeId foreign key (ContractTypeId)
      references dbo.RefFMFContractType (ContractTypeId)
go

alter table dbo.RefFMFContractTypeAttributeMap
   add constraint FKEntity_FMF foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.RefRegionPerilMapping
   add constraint FKEntity_RPMap foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.RefSourceRAPMapping
   add constraint FKEntity_SourceRAP foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.ReturnPeriodBandingAdjustmentParameter
   add constraint FKAdjustmentNodeId_RPBand foreign key (AdjustmentNodeId)
      references dbo.AdjustmentNode (AdjustmentNodeId)
go

alter table dbo.ScalingAdjustmentParameter
   add constraint FKAdjustmentNodeId_Scaling foreign key (AdjustmentNodeId)
      references dbo.AdjustmentNode (AdjustmentNodeId)
go

alter table dbo.SummaryStatisticHeader
   add constraint FKPLTHeaderId_Stat foreign key (LossDataId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.SummaryStatisticHeader
   add constraint FKRRLossDataHeaderId_Stat foreign key (LossDataId)
      references dbo.LossDataHeader (LossDataHeaderId)
go

alter table dbo.SummaryStatisticsDetail
   add constraint FKPLTHeaderId_SummStat foreign key (PLTHeaderId)
      references dbo.PLTHeader (PLTHeaderId)
go

alter table dbo.SummaryStatisticsDetail
   add constraint FKRRSummaryStatisticHeaderId_Detail foreign key (SummaryStatisticHeaderId)
      references dbo.SummaryStatisticHeader (SummaryStatisticHeaderId)
go

alter table dbo."User"
   add constraint FK_USER_FKENTITY__ENTITY foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table UserDashboard
   add constraint FK_USERDASH_REFERENCE_USER foreign key (UserId)
      references dbo."User" (UserId)
go

alter table UserDashboardWidget
   add constraint FK_USERDASH_REFERENCE_USER foreign key (UserId)
      references dbo."User" (UserId)
go

alter table UserDashboardWidget
   add constraint FK_USERDASH_REFERENCE_USERDASH foreign key (UserDashboardId)
      references UserDashboard (UserDashboardId)
go

alter table UserDashboardWidget
   add constraint FK_USERDASH_REFERENCE_DASHBOAR foreign key (DashboardWidgetId)
      references DashboardWidget (DashboardWidgetId)
go

alter table UserDashboardWidgetColumns
   add constraint FK_USERDASH_REFERENCE_USERDASH foreign key (UserDashboardWidgetId)
      references UserDashboardWidget (UserDashboardWidgetId)
go

alter table UserDashboardWidgetColumns
   add constraint FK_USERDASH_REFERENCE_USER foreign key (UserId)
      references dbo."User" (UserId)
go

alter table dbo.UserPreferences
   add constraint FK_USERPREF_FKENTITY__ENTITY foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.UserPreferences
   add constraint FKUserId_Pref foreign key (UserId)
      references dbo."User" (UserId)
go

alter table dbo.UserRLDataSource
   add constraint FKEntity_UserRLData foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.UserRole
   add constraint FKRoleId_UserRole foreign key (RoleId)
      references dbo.Role (RoleId)
go

alter table dbo.UserRole
   add constraint FKUserId_UserRole foreign key (UserId)
      references dbo."User" (UserId)
go

alter table dbo.UserTablePreferences
   add constraint FKEntity_Pref foreign key (Entity)
      references dbo.Entity (Entity)
go

alter table dbo.UserTablePreferences
   add constraint FKUserId_Pref foreign key (UserId)
      references dbo."User" (UserId)
go

alter table dbo.UserTag
   add constraint FKTagID foreign key (TagId)
      references dbo.Tag (TagId)
go

