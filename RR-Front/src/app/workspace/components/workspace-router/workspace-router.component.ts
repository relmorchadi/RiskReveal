import {
  Component,
  ComponentFactoryResolver,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild
} from '@angular/core';
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
import {Subscription} from "rxjs";
import {ToggleWsLeftMenu, UpdateWsRouting} from "../../store/actions";
import {Navigate} from "@ngxs/router-plugin";

@Component({
  selector: 'workspace-router',
  templateUrl: './workspace-router.component.html',
  styleUrls: ['./workspace-router.component.scss']
})
export class WorkspaceRouterComponent implements OnInit, OnChanges {

  @Input('state')
  state: { wsIdentifier, data };

  @Output('action')
  actionsEmitter: EventEmitter<any> = new EventEmitter();

  @ViewChild(WsRouterDirective)
  routingTemplate: WsRouterDirective;
  readonly componentsMapper = {
    projects: {component: WorkspaceProjectComponent, selector: (state) => state.project},
    Contract: {component: WorkspaceContractComponent, selector: (state) => state},
    Activity: {component: WorkspaceActivityComponent, selector: (state) => state},
    PltBrowser: {component: WorkspacePltBrowserComponent, selector: (state) => state},
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
  private subscription: Subscription = null;
  private currentInstance: StateSubscriber;

  constructor(private componentFactoryResolver: ComponentFactoryResolver) {
  }

  ngOnInit() {
    this.initComponent();
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('Changes', changes);
    if (changes.state && this.currentInstance) {
      if (changes.state.currentValue.data.route != changes.state.previousValue.data.route)
        this.initComponent(changes.state.currentValue.data.route);
      this.currentInstance.patchState(changes.state.currentValue);
    }


  }

  handleLeftMenuNavigation({route}) {
    const {wsId, uwYear} = this.state.data;
    console.log('route', route);
    this.actionsEmitter.emit(
      [new UpdateWsRouting(this.state.wsIdentifier, route), new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
    );
  }

  handleLeftMenuToggle() {
    this.actionsEmitter.emit(new ToggleWsLeftMenu(this.state.wsIdentifier));
  }

  private initComponent(route = this.state.data.route) {
    this.subscription ? this.subscription.unsubscribe() : null;
    const correspondingComponent = this.componentsMapper[route] || this.componentsMapper.projects;
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(correspondingComponent.component);
    const containerRef = this.routingTemplate.viewContainerRef;
    containerRef.clear();
    const componentRef = containerRef.createComponent(componentFactory);
    this.currentInstance = <StateSubscriber>componentRef.instance;
    if (this.currentInstance) {
      this.currentInstance.patchState(this.state);
      this.subscription = this.currentInstance.actionsEmitter
        .subscribe(action => this.actionsEmitter.emit(action));
    }
  }


}
