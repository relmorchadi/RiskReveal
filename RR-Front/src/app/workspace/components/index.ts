import {LeftMenuComponent} from "./left-menu/left-menu.component";
import {ProjectsListComponent} from "./project/projects-list/projects-list.component";
import {PinComponent} from './pin/pin.component';
import {WorkspaceRouterComponent} from './workspace-router/workspace-router.component';
import {InuringDatatableComponent} from "./inuring";
import {InuringPackageDetailsComponent} from "./inuring/inuring-package-details/inuring-package-details.component";
import {NodeCreationPopupComponent} from "./inuring/node-creation-popup/node-creation-popup.component";
import {InuringGraphComponent} from "./inuring/inuring-graph/inuring-graph.component";
import {INURING_NODES} from "./inuring/nodes";
import {AttachPltPopUpComponent} from "./scopeCompleteness/attachPlt-pop-up/attach-plt-pop-up/attach-plt-pop-up.component";
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
import {DatasourceListComponent} from "./import/datasource-list/datasource-list.component";

export const COMPONENTS = [
    LeftMenuComponent,
    ProjectsListComponent,
    PinComponent,
    WorkspaceRouterComponent,
    InuringDatatableComponent,
    InuringPackageDetailsComponent,
    NodeCreationPopupComponent,
    InuringGraphComponent,
    ...INURING_NODES,
    AttachPltPopUpComponent,
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
    DatasourceListComponent
];

export * from './workspace-router/workspace-router.component';
export * from './left-menu/left-menu.component';
export * from './project/projects-list/projects-list.component';
export * from './inuring';
export * from './import';
