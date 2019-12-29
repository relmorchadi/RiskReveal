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
import {FinancialPerspSelectionDialogComponent, OverrideRegionPerilDialogComponent} from "./import/analysis-result";
import {CalibrationNewTableComponent} from "./calibration-new/calibration-new-table/calibration-new-table.component";



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
  PortfolioResultComponent,
  OverrideRegionPerilDialogComponent,
  FinancialPerspSelectionDialogComponent
];

export * from './workspace-router/workspace-router.component';
export * from './left-menu/left-menu.component';
export * from './project/projects-list/projects-list.component';
export * from './inuring';
export * from './import';
