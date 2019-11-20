import {Component, ComponentFactoryResolver, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {
  WorkspaceAccumulationComponent,
  WorkspaceActivityComponent,
  WorkspaceCalibrationComponent,
  WorkspaceCloneDataComponent,
  WorkspaceContractComponent,
  WorkspaceExposuresComponent,
  WorkspaceFileBaseImportComponent,
  WorkspaceInuringComponent,
  WorkspacePltBrowserComponent,
  WorkspaceProjectComponent,
  WorkspaceResultsComponent,
  WorkspaceRiskLinkComponent,
  WorkspaceScopeCompletenceComponent
} from "../../containers";
import {WsRouterDirective} from "../../directives/ws-router.directive";
import {StateSubscriber} from "../../model/state-subscriber";
import {ToggleWsLeftMenu, UpdateWsRouting} from "../../store/actions";
import {Navigate} from "@ngxs/router-plugin";
import {Store} from "@ngxs/store";
import * as _ from 'lodash';

/**
 * @Component Workspace Router
 * @Desc A container that hold and manage Dynamic instantiation on Workspace components
 */
@Component({
  selector: 'workspace-router',
  templateUrl: './workspace-router.component.html',
  styleUrls: ['./workspace-router.component.scss']
})
export class WorkspaceRouterComponent implements OnInit, OnChanges {

  @Input('state')
  state: { wsIdentifier, data };

  /**
   * The template Reference where we instantiate our target component
   */
  @ViewChild(WsRouterDirective)
  routingTemplate: WsRouterDirective;

  /**
   * A map of Components referred by a component name
   * We use to check the route attribute from the workspace resolve the corresponding component then instantiate it
   */
  readonly componentsMapper = {
    projects: {component: WorkspaceProjectComponent, selector: (state) => state.project},
    Contract: {component: WorkspaceContractComponent, selector: (state) => state},
    Activity: {component: WorkspaceActivityComponent, selector: (state) => state},
    PltBrowser: {component: WorkspacePltBrowserComponent, selector: (state) => state.pltManager},
    RiskLink: {component: WorkspaceRiskLinkComponent, selector: (state) => state},
    FileBasedImport: {component: WorkspaceFileBaseImportComponent, selector: (state) => state},
    CloneData: {component: WorkspaceCloneDataComponent, selector: (state) => state},
    Exposures: {component: WorkspaceExposuresComponent, selector: (state) => state},
    Results: {component: WorkspaceResultsComponent, selector: (state) => state},
    Calibration: {component: WorkspaceCalibrationComponent, selector: (state) => state},
    Inuring: {component: WorkspaceInuringComponent, selector: (state) => state},
    ScopeCompleteness: {component: WorkspaceScopeCompletenceComponent, selector: (state) => state},
    Accumulation: {component: WorkspaceAccumulationComponent, selector: (state) => state},
  };

  private currentInstance: StateSubscriber;

  constructor(private componentFactoryResolver: ComponentFactoryResolver, private store: Store) {
  }

  ngOnInit() {
    this.initComponent();
  }

  /**
   *
   * @param changes
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (changes.state && this.currentInstance) {
      if (changes.state.currentValue.data.route != changes.state.previousValue.data.route)
        this.initComponent(changes.state.currentValue.data.route);
      if (this.currentInstance && _.isFunction(this.currentInstance.patchState))
        this.currentInstance.patchState(changes.state.currentValue);
    }
  }

  /**
   * @desc Handle left menu navigations
   * @param route
   */
  handleLeftMenuNavigation({route}) {
    const {wsId, uwYear} = this.state.data;
    this.store.dispatch(
      [new UpdateWsRouting(this.state.wsIdentifier, route), new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
    );
  }

  /**
   * @desc Handle left menu Toggle
   */
  handleLeftMenuToggle() {
    this.store.dispatch(new ToggleWsLeftMenu(this.state.wsIdentifier));
  }

  /**
   * @desc Instantiate the workspace router component instance
   * @param route
   */
  private initComponent(route = this.state.data.route) {
    const correspondingComponent = this.componentsMapper[route] || this.componentsMapper.PltBrowser;
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(correspondingComponent.component);
    const containerRef = this.routingTemplate.viewContainerRef;
    containerRef.clear();
    const componentRef = containerRef.createComponent(componentFactory);
    this.currentInstance = <StateSubscriber>componentRef.instance;
    if (this.currentInstance && _.isFunction(this.currentInstance.patchState)) {
      this.currentInstance.patchState(this.state);
    }
  }


}
