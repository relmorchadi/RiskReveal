import {LeftMenuComponent} from "./left-menu/left-menu.component";
import {ProjectsListComponent} from "./project/projects-list/projects-list.component";
import {PinComponent} from './pin/pin.component';
import {WorkspaceRouterComponent} from './workspace-router/workspace-router.component';
import {InuringDatatableComponent} from "./inuring";
import {InuringPackageDetailsComponent} from "./inuring/inuring-package-details/inuring-package-details.component";
import {NodeCreationPopupComponent} from "./inuring/node-creation-popup/node-creation-popup.component";
import {InuringGraphComponent} from "./inuring/inuring-graph/inuring-graph.component";
import {SimpleNodeComponent} from "./inuring/simple-node/simple-node.component";
import {INURING_NODES} from "./inuring/nodes";
import {AttachPltPopUpComponent} from "./scopeCompleteness/attachPlt-pop-up/attach-plt-pop-up/attach-plt-pop-up.component";



export const COMPONENTS = [
  LeftMenuComponent,
  ProjectsListComponent,
  PinComponent,
  WorkspaceRouterComponent,
  InuringDatatableComponent,
  InuringPackageDetailsComponent,
  NodeCreationPopupComponent,
  InuringGraphComponent,
  SimpleNodeComponent,
  ...INURING_NODES,
  AttachPltPopUpComponent
];

export * from './workspace-router/workspace-router.component';
export * from './left-menu/left-menu.component';
export * from './project/projects-list/projects-list.component';
export * from './inuring';
