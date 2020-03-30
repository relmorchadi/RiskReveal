import {ChangeDetectionStrategy, ChangeDetectorRef, Component, HostListener, OnInit} from '@angular/core';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import * as fromWs from '../../store/actions';
import {ToggleWsDetails, ToggleWsLeftMenu, UpdateWsRouting} from '../../store/actions/workspace.actions';
import {BaseContainer} from '../../../shared/base';
import {WorkspaceState} from '../../store/states';
import {Navigate} from '@ngxs/router-plugin';
import * as fromHeader from 'src/app/core/store/actions/header.action';
import {HelperService} from '../../../shared/services';


@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspaceMainComponent extends BaseContainer implements OnInit {

  state: WorkspaceMain = null;
  selectedTabIndex: number;
  loading: boolean;
  data: { [p: string]: any } = null;
  tabs: any[];
  currentWsIdentifier: string;
  wsStatus: string;
  selectedPrj: any = {};
  wsId: any;
  uwYear: any;
  route: any;
  ws: any;

  currentTab: any;

  @Select(WorkspaceState.getWorkspaceStatus) status$;
  @Select(WorkspaceState.getSelectedProject) selectedPrj$;
  @Select(WorkspaceState.getCurrentTab) currentTab$;
  @Select(WorkspaceState.getWorkspaces) ws$;

  constructor(
    private _helper: HelperService,
    _cdRef: ChangeDetectorRef,
    private _route: ActivatedRoute,
    _store: Store,
    _router: Router,
  ) {
    super(_router, _cdRef, _store);
    this.loading = false;
  }

  ngOnInit() {
    this.ws$.pipe().subscribe(value => {
     this.ws = value;
    });

    this._route.params
      .pipe(this.unsubscribeOnDestroy)
      .subscribe(({wsId, year, route}: any) => {
        const openedWs = _.get(this.ws, `${wsId}-${year}`, null);
        if (openedWs === null) {
          this.dispatch(new fromWs.OpenWS({wsId, uwYear: year, route}))
        }
      });

    this.currentTab$.pipe(this.unsubscribeOnDestroy)
      .subscribe(curTab => {
        this.selectedTabIndex = curTab.index;
        this.currentWsIdentifier = curTab.wsIdentifier;
        this.currentTab = curTab;
        this.detectChanges();
      });
    this.select(WorkspaceState.getLoading)
      .pipe(this.unsubscribeOnDestroy)
      .subscribe(loading => {
        this.loading = loading;
        this.detectChanges();
      });
    this.select(WorkspaceState.getWorkspaces)
      .pipe(this.unsubscribeOnDestroy)
      .subscribe(content => {
        this.data = content;
        this.detectChanges();
      });

    this.status$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.wsStatus = value;
      this.detectChanges();
    });

    this.selectedPrj$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.selectedPrj = value;
      this.detectChanges();
    })
  }

  @HostListener('window:keyup', ['$event'])
  keyEvent(e: KeyboardEvent) {
    if (e.ctrlKey && _.size(this.data)) {
      if (e.key == 'ArrowRight') {
        if (_.size(this.data) > this.selectedTabIndex) {
          const wsIdentifier = _.keys(this.data)[this.selectedTabIndex + 1];
          this.selectWorkspace(wsIdentifier, this.selectedTabIndex + 1)
        } else {
          this.selectWorkspace(_.keys(this.data)[0], 0)
        }
      }
      if (e.key == 'ArrowLeft') {
        if (this.selectedTabIndex > 0) {
          const wsIdentifier = _.keys(this.data)[this.selectedTabIndex - 1];
          this.selectWorkspace(wsIdentifier, this.selectedTabIndex - 1);
        } else {
          const i = _.size(this.data) - 1;
          const wsIdentifier = _.keys(this.data)[i];
          this.selectWorkspace(wsIdentifier, i)
        }
      }
    }
  }

  close(wsId, uwYear) {
    event.stopPropagation();
    this.dispatch(new fromWs.CloseWS({
      wsId,uwYear
    }));
  }

  addWs(wsId, uwYear) {
    this.dispatch(new fromWs.OpenWS({
      wsId,
      uwYear,
      route: 'projects'}));
  }

  generateYear(year, years, title = '') {
    return (years || []).filter(y => y != year) || [];
  }

  sliceContent(content: any, valid: boolean) {
    if (valid && content) {
      return content.slice(0, 3);
    } else {
      return content;
    }
  }
/*
  patchSliceValue(value) {
    this.dispatch(new PatchWorkspaceMainStateAction({key: 'sliceValidator', value: value}));
  }
*/

  patchWorkspaceDetail(wsId: string) {
    this.dispatch(new ToggleWsDetails(wsId));
  }

  selectWorkspace(wsIdentifier, index) {
    this.dispatch(new fromWs.SetCurrentTab({
      wsIdentifier,
      index
    }));
  }

  toggleFavorite(wsIdentifier: string, {wsId, uwYear, workspaceName, programName, cedantName}) {
    this.dispatch([new fromHeader.ToggleFavoriteWsState({
      userId: 1,
      workspaceContextCode: wsId,
      workspaceUwYear: uwYear
    })]);
  }

  filterSelected() {
    return _.find(_.get(this.data[this.currentWsIdentifier], 'projects' , []), item => item.selected);
  }

  filterSelectedDivision() {
    return _.map(_.get(this.filterSelected(), 'division', []), item => item.divisionNo);
  }

  filterListProject() {
    return _.filter(_.get(this.data[this.currentWsIdentifier], 'projects' , []), item => !item.selected);
  }

  selectProject(selectionEvent) {
    const projectIndex = _.findIndex(this.data[this.currentWsIdentifier].projects,
      (item: any) => item.projectId === selectionEvent);
    this.dispatch(new fromWs.ToggleProjectSelection({projectIndex, wsIdentifier: this.currentWsIdentifier}));
  }

  leftMenuNavigation({route}, {wsId, uwYear}) {
    const actions = [new UpdateWsRouting(`${wsId}-${uwYear}`, route), new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}`])];
    this.dispatch(actions);
  }

  toggleLeftMenu(wsId: string) {
    this.dispatch(new ToggleWsLeftMenu(wsId));
  }


  ngOnDestroy(): void {
    this.destroy();
  }
}

