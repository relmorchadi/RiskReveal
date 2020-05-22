import {LeftMenuComponent} from "./left-menu/left-menu.component";
import {ProjectsListComponent} from "./project/projects-list/projects-list.component";
import {WorkspaceRouterComponent} from './workspace-router/workspace-router.component';
import {ScopeTableComponent} from "./scopeCompleteness/scope-table/scope-table.component";
import {InuringDatatableComponent} from "./inuring";
import {InuringPackageDetailsComponent} from "./inuring/inuring-package-details/inuring-package-details.component";
import {NodeCreationPopupComponent} from "./inuring/node-creation-popup/node-creation-popup.component";
import {InuringGraphComponent} from "./inuring/inuring-graph/inuring-graph.component";
import {INURING_NODES} from "./inuring/nodes";
import {AnalysisResultComponent, PortfolioResultComponent} from "./import";
import {
    FinancialPerspSelectionDialogComponent,
    OverrideRegionPerilDialogComponent,
    OverridePeqtDialogComponent,
    OverrideOccurenceBasisDialogComponent
} from "./import/analysis-result";
import {CalibrationNewTableComponent} from "./calibration-new/calibration-new-table/calibration-new-table.component";
import {AdjustmentPopUpNewComponent} from "./calibration-new/adjustment-pop-up-new/adjustment-pop-up-new.component";
import {ReturnPeriodPopUpComponent} from "./calibration-new/return-period-pop-up/return-period-pop-up.component";
import {NonLinearAdjustmentTableComponent} from "./calibration-new/non-linear-adjustment-table/non-linear-adjustment-table.component";
import {ExposuresMainTableComponent} from "./exposures/exposures-main-table/exposures-main-table.component";
import {ExposuresHeaderComponent} from "./exposures/exposures-header/exposures-header.component";
import {ExposuresLeftMenuComponent} from "./exposures/exposures-left-menu/exposures-left-menu.component";
import {ExposuresRightMenuComponent} from "./exposures/exposures-right-menu/exposures-right-menu.component";
import {DatasourceListComponent} from "./import/datasource-list/datasource-list.component";
import {CalibrationColumnManagerComponent} from "./calibration-new/column-manager/column-manager.component";
import {TagsComponent} from "./calibration/tags/tags.component";
import {LastAdjustmentMatrixComponent} from "./calibration/last-adjustment-matrix/last-adjustment-matrix.component";
import {AdjustmentPopUpComponent} from "./calibration/adjustment-pop-up/adjustment-pop-up.component";
import {AddRemovePopUpComponent} from "./calibration/add-remove-pop-up/add-remove-pop-up.component";
import {PopUpPltTableComponent} from "./calibration/add-remove-pop-up/pop-up-plt-table/pop-up-plt-table.component";
import {CalibrationMainTableComponent} from "./calibration/calibration-main-table/calibration-main-table.component";
import {InuringCanvasTabComponent} from "./inuring/inuring-canvas-tab/inuring-canvas-tab.component";
import {EditContractPopUpComponent} from "./inuring/edit-contract-pop-up/edit-contract-pop-up.component";
import {EditEdgePopUpComponent} from "./inuring/edit-edge-pop-up/edit-edge-pop-up.component";
import {AddRemovePopUpNewComponent} from "./calibration-new/add-remove-pop-up-new/add-remove-pop-up-new.component";
import {PopUpPltTableNewComponent} from "./calibration-new/pop-up-plt-table-new/pop-up-plt-table-new.component";
import {AttachPltsPopUpComponent} from "./scopeCompleteness/attach-plts-pop-up/attach-plts-pop-up.component";

export const COMPONENTS = [
    TagsComponent,
    LastAdjustmentMatrixComponent,
    AdjustmentPopUpComponent,
    AddRemovePopUpComponent,
    PopUpPltTableComponent,
    CalibrationMainTableComponent,
    InuringCanvasTabComponent,
    EditContractPopUpComponent,
    EditEdgePopUpComponent,
    ReturnPeriodPopUpComponent,
    AddRemovePopUpNewComponent,
    PopUpPltTableNewComponent,
    AttachPltsPopUpComponent,
    LeftMenuComponent,
    ProjectsListComponent,
    ScopeTableComponent,
    WorkspaceRouterComponent,
    InuringDatatableComponent,
    InuringPackageDetailsComponent,
    NodeCreationPopupComponent,
    InuringGraphComponent,
    ...INURING_NODES,
    AnalysisResultComponent,
    PortfolioResultComponent,
    CalibrationNewTableComponent,
    AdjustmentPopUpNewComponent,
    ReturnPeriodPopUpComponent,
    PortfolioResultComponent,
    OverrideRegionPerilDialogComponent,
    FinancialPerspSelectionDialogComponent,
    OverridePeqtDialogComponent,
    OverrideOccurenceBasisDialogComponent,
    NonLinearAdjustmentTableComponent,
    ExposuresMainTableComponent,
    ExposuresHeaderComponent,
    ExposuresLeftMenuComponent,
    ExposuresRightMenuComponent,
    DatasourceListComponent,
    CalibrationColumnManagerComponent
];

export * from './workspace-router/workspace-router.component';
export * from './left-menu/left-menu.component';
export * from './project/projects-list/projects-list.component';
export * from './inuring';
export * from './import';
