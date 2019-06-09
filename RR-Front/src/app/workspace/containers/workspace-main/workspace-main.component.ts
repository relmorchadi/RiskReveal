import {ChangeDetectionStrategy, ChangeDetectorRef, Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import {SearchService} from '../../../core/service/search.service';
import * as _ from 'lodash';
import {combineLatest, forkJoin, from, fromEvent, Observable, of, Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {mergeMap} from 'rxjs/internal/operators/mergeMap';

import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {WorkspaceMainState} from '../../../core/store/states/workspace-main.state';
import {
  AppendNewWorkspaceMainAction,
  CloseWorkspaceMainAction,
  LoadWorkspacesAction,
  OpenNewWorkspacesAction, PatchWorkspace,
  PatchWorkspaceMainStateAction, setTabsIndex
} from '../../../core/store/actions/workspace-main.action';
import * as moment from 'moment'
import * as fromWS from '../../store/'


@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspaceMainComponent implements OnInit,OnDestroy {
  componentSubscriptions: Subscription[];
  tabIndex = 0;
  liked = false;

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;
  private keyEvents: Observable<any>;
  private keyDowns: any;
  private keyUps: any;
  private selectedTabIndex: number;
  private loading: boolean;
  private data: { [p: string]: any };

  constructor(
    private _helper: HelperService,
    private cdRef: ChangeDetectorRef,
    private route: ActivatedRoute,
    private _searchService: SearchService,
    private store: Store,
    private _router: Router,
  ) {
    this.componentSubscriptions = [];
    this.selectedTabIndex=0;
    this.loading= false;
  }

  ngOnInit() {
    /*this.store.dispatch(new LoadWorkspacesAction());*/
    this.route.children[0].params.subscribe(
      ({wsId, year}: any) => {
        this.getSearchedWorkspaces(wsId, year);
        this.detectChanges();
      });
    //New
    this.componentSubscriptions.push(
      this.store.select(fromWS.WorkspaceState.getCurrentTab).subscribe( curTab => {
        this.selectedTabIndex= curTab.index;
        console.log(this.selectedTabIndex);
        this.detectChanges();
      }),
      this.store.select(fromWS.WorkspaceState.getLoading).subscribe( loading => {
        this.loading = loading;
        this.detectChanges();
      }),
      this.store.select(fromWS.WorkspaceState.getWorkspaces).subscribe( content => {
        this.data = content;
        console.log(content);
        this.detectChanges();
      })
    )
  }

  @HostListener('window:keyup', ['$event'])
  keyEvent(e: KeyboardEvent) {

    if(e.ctrlKey && _.size(this.data)) {
      if(e.key == 'ArrowRight') {
        if( _.size(this.data) > this.selectedTabIndex){
          const wsIdentifier = _.keys(this.data)[this.selectedTabIndex + 1];
          this.selectWorkspace(wsIdentifier, this.selectedTabIndex + 1)
        }else{
          this.selectWorkspace(_.keys(this.data)[0],0)
        }
      }
      if(e.key == 'ArrowLeft'){
        if(this.selectedTabIndex > 0){
          const wsIdentifier = _.keys(this.data)[this.selectedTabIndex - 1];
          this.selectWorkspace(wsIdentifier, this.selectedTabIndex - 1);
        }else{
          const i = _.size(this.data) - 1;
          const wsIdentifier = _.keys(this.data)[i];
          this.selectWorkspace(wsIdentifier,i)
        }
      }
    }
  }

  getSearchedWorkspaces(wsId = null, year = null) {
    const popConfirm = this._router.url === `/workspace/${wsId}/${year}/PopOut`;
    console.log(popConfirm)
    if (popConfirm) {
      this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'loading', value: true}));
      this.searchData(wsId, year).pipe(
        mergeMap((content: any) => {
          const item = {
            workSpaceId: wsId,
            uwYear: year,
            selected: false,
            ...content
          };
          this.store.dispatch(new OpenNewWorkspacesAction([item]));
          return forkJoin(...content.years.map((years) => this.searchData(wsId, years)));
        })
      ).subscribe((content) => {
        this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'loading', value: false}));
        this.detectChanges();
      });
    }
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  close(wsId, uwYear) {
    this.store.dispatch(new fromWS.closeWS({
      wsIdentifier: wsId+'-'+uwYear
    }))
    //AFTER
    this._helper.updateWorkspaceItems();
  }

  addWs(wsId, uwYear) {
    /*const alreadyOpened = this.state.openedTabs.data.filter(ws => ws.workSpaceId === title && ws.uwYear == year);
    const index = _.findIndex(this.state.openedTabs.data, ws => ws.workSpaceId === title && ws.uwYear == year);
    if (alreadyOpened.length > 0) {
      this.store.dispatch(new PatchWorkspaceMainStateAction([{key: 'openedWs', value: alreadyOpened[0]},
        {key: 'openedTabs', value: {data: this.state.openedTabs.data, tabsIndex: index}}]));
    } else {
      this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'loading', value: true}));
      this.searchData(title, year).subscribe(
        (dt: any) => {
          let workspace = {
            workSpaceId: title,
            uwYear: year,
            selected: false,
            ...dt
          };
          workspace.projects = workspace.projects.map(prj => prj = {...prj, selected: false});
          this.store.dispatch(new AppendNewWorkspaceMainAction(workspace));
          this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'loading', value: false}));
          this._helper.updateWorkspaceItems();
          this._helper.updateRecentWorkspaces();
          this.tabIndex = this.state.openedTabs.data.length;
          this.detectChanges();
        }
      );
    }*/
    this.store.dispatch(new fromWS.openWS({
      wsId,
      uwYear
    }))
  }

  generateYear(year, years, title = '') {
    /*    let generatedYears = years.filter(y => y != year) || [];
        let itemImported = this.state.openedTabs || [];
        if (title !== '') {
          itemImported = itemImported.filter(dt => dt.workSpaceId === title);
          if (itemImported.length > 0) {
            itemImported.forEach(item => {
              generatedYears = generatedYears.filter(y => y != item.uwYear);
            });
          }
        }*/
    return (years || []).filter(y => y != year) || [];
  }

  sliceContent(content: any, valid: boolean) {
    if (valid && content) {
      return content.slice(0, 3);
    } else {
      return content;
    }
  }

  patchSliceValue(value) {
    this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'sliceValidator', value: value}));
  }

  patchWorkspaceDetail() {
    this.store.dispatch(new PatchWorkspaceMainStateAction({
      key: 'collapseWorkspaceDetail',
      value: !this.state.collapseWorkspaceDetail
    }));
  }

  selectWorkspace(wsIdentifier, index) {
    this.store.dispatch(new fromWS.setCurrentTab({
      wsIdentifier,
      index
    }))
  }

  detectChanges() {
    if (!this.cdRef['destroyed']) {
      this.cdRef.detectChanges();
    }
  }

  addToFavorite(tab: any,k,liked) {
    this.liked = liked;
    this.store.dispatch(new PatchWorkspace({
      key: ['favorite','lastFModified'],
      value: [liked,moment().format('x')],
      k,
      ws: tab
    }))

    let workspaceMenuItem = JSON.parse(localStorage.getItem('workSpaceMenuItem')) || {};

    if(liked){
      workspaceMenuItem[tab.workSpaceId + '-'+ tab.uwYear] = {...tab,favorite: true, lastFModified: moment().format('x')};
    }else{
      workspaceMenuItem = {...workspaceMenuItem, [tab.workSpaceId + '-'+ tab.uwYear]: _.omit(workspaceMenuItem[tab.workSpaceId + '-'+ tab.uwYear], ['favorite','lastFModified'])};
    }
    localStorage.setItem('workSpaceMenuItem',JSON.stringify(workspaceMenuItem));
  }

  ngOnDestroy(): void {
    _.forEach(this.componentSubscriptions, sub => sub.unsubscribe())
  }
}
