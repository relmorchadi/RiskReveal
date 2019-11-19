import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SearchService} from '../../../service/search.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import * as fromHD from '../../../store/actions';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import {
  HeaderState,
  OpenNewWorkspacesAction,
  PatchWorkspace,
  PatchWorkspaceMainStateAction
} from '../../../store/index';
import * as fromHeader from '../../../store/actions/header.action';
import {HelperService} from '../../../../shared/helper.service';
import * as workspaceActions from '../../../../workspace/store/actions/workspace.actions';
import {UpdateWsRouting} from '../../../../workspace/store/actions/workspace.actions';
import {NotificationService} from '../../../../shared/notification.service';
import {BaseContainer} from '../../../../shared/base';
import {WorkspaceState} from "../../../../workspace/store/states";
import {take} from "rxjs/operators";
import {Navigate} from "@ngxs/router-plugin";
import {promise} from "selenium-webdriver";
import delayed = promise.delayed;

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspacesMenuItemComponent extends BaseContainer implements OnInit {

  readonly componentName: string = 'workspace-pop-in';

  contractFilterFormGroup: FormGroup;
  workspaces: any = [];
  selectedWorkspace = null;
  numberofElement: number;
  lastOnes = 0;
  visible: any;
  labels: any = [];
  lastSelectedIndex = null;

  recent = null;
  favorites: any;
  pinged: any;

  constructor(private _helperService: HelperService,
              private router: Router, private _searchService: SearchService,
              _baseStore: Store, _baseRouter: Router,
              _baseCdr: ChangeDetectorRef,
              private _fb: FormBuilder, private store: Store,
              private notificationService: NotificationService,
              private cdRef: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  @Select(HeaderState.getAssignedWs)assignedWs$;
  @Select(HeaderState.getRecentWs)recentWs$;
  @Select(HeaderState.getFavoriteWs)favoriteWs$;
  @Select(HeaderState.getPinnedWs)pinnedWs$;
  @Select(HeaderState.getStatusCountWs)countWs$;
  @Select(WorkspaceState.getLastWorkspace) lastWorkspace$;

  countWs: any;
  recentWs: any;
  favoriteWs: any;
  pinnedWs: any;
  assignedWs: any;

  loadRecent = false;
  loadFavorite = false;
  loadAssigned = false;
  loadPinned = false;

  recentSearch: any;
  favoriteSearch: any;
  pinnedSearch: any;
  assignedSearch: any;

  recentPageable = 10;
  favoritePageable = 10;
  pinnedPageable = 10;
  assignedPageable = 10;

  paginationParams: [
    { id: 0, shownElement: 10, label: 'Last 10' },
    { id: 1, shownElement: 50, label: 'Last 50' },
    { id: 2, shownElement: 100, label: 'Last 100' },
    { id: 3, shownElement: 150, label: 'Last 150' }];

  recentPagination = 0;

  ngOnInit() {
    this.store.dispatch([new fromHD.LoadRecentWorkspace({offset: 0, size: 10, userId: 1}),
      new fromHD.LoadWsStatusCount()]);
    this.countWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => this.countWs = _.merge({}, value));
    this.recentWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {this.recentWs =  value; this.detectChanges(); });
    this.favoriteWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {this.favoriteWs =  value; this.detectChanges(); });
    this.pinnedWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {this.pinnedWs = value; this.detectChanges(); });
    this.assignedWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {this.assignedWs = value; this.detectChanges(); });

    // this.recentWs$.subscribe(value => this.recent = _.merge([], value));

    this._searchService.infodropdown.subscribe(dt => {
      this.visible = this._searchService.getvisibleDropdown();
      this.detectChanges();
    });

    HelperService.headerBarPopinChange$.subscribe(({from}) => {
      if (from != this.componentName) {
        this.visible = false;
        this.detectChanges();
      }
    });
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  loadTabData(event) {
    if (event === 0 && !this.loadRecent) {
      this.store.dispatch(new fromHD.LoadRecentWorkspace({offset: 0, size: 10, userId: 1}));
      this.loadRecent = true;
    } else if (event === 1 && !this.loadFavorite) {
      this.store.dispatch(new fromHD.LoadFavoriteWorkspace({offset: 0, size: 10, userId: 1}));
      this.loadFavorite = true;
    } else if (event === 2 && !this.loadAssigned) {
      this.store.dispatch(new fromHD.LoadAssignedWorkspace({offset: 0, size: 10, userId: 1}));
      this.loadAssigned = true;
    } else if (event === 3 && !this.loadPinned) {
      this.store.dispatch(new fromHD.LoadPinnedWorkspace({offset: 0, size: 10, userId: 1}));
      this.loadPinned = true;
    }
  }

  pageableChange($event, scope) {
    if (scope === 'recent') {
      const size =  $event - this.recentPageable;
      this.store.dispatch(new fromHD.LoadRecentWorkspace({offset: this.recentPageable,
        size, userId: 1, option: 'append'}));
    } else if (scope === 'favorite') {
      const size =  $event - this.favoritePageable;
      this.store.dispatch(new fromHD.LoadFavoriteWorkspace({offset: this.favoritePageable,
        size, userId: 1, option: 'append'}));
    } else if (scope === 'assigned') {
      const size =  $event - this.assignedPageable;
      this.store.dispatch(new fromHD.LoadAssignedWorkspace({offset: this.assignedPageable,
        size, userId: 1, option: 'append'}));
    } else if (scope === 'pinned') {
      const size =  $event - this.pinnedPageable;
      this.store.dispatch(new fromHD.LoadPinnedWorkspace({offset: this.pinnedPageable,
        size, userId: 1, option: 'append'}));
    }
  }

  searchNewWorkspace(search) {
    this.contractFilterFormGroup.patchValue({cedant: search.target.value});
  }

  singleLineSelect(scope, workspace, value = null) {
    if (scope === 'recent') {
      this.dispatch(new fromHD.ToggleRecentWsSelection({item: workspace, selection: 'single', value}));
    } else if (scope === 'favorite') {
      this.dispatch(new fromHD.ToggleFavoriteWsSelection({item: workspace, selection: 'single', value}));
    } else if (scope === 'assigned') {
      this.dispatch(new fromHD.ToggleAssignedWsSelection({item: workspace, selection: 'single', value}));
    } else if (scope === 'pinned') {
      this.dispatch(new fromHD.TogglePinnedWsSelection({item: workspace, selection: 'single', value}));
    }
  }

  toggleWorkspace(scope: string, workspace, index) {
    if ((window as any).event.ctrlKey) {
      setTimeout(() => {
        this.singleLineSelect(scope, workspace, true);
      }, 200);
    } else if ((window as any).event.shiftKey) {
      /*      if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {
              const [from, to] = [Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex)];
              this.store.dispatch(new fromHeader.SelectRange({context, from, to}));
            } else {
              this.lastSelectedIndex = index;
              this.store.dispatch(new fromHeader.ChangeWsSelection({context, index, value: true}));
              // workspace.selected = true;
            }*/
    } else {
      setTimeout(() => {
        if (scope === 'recent') {
          this.dispatch(new fromHD.ToggleRecentWsSelection({selection: 'all', value: false}));
          this.dispatch(new fromHD.ToggleRecentWsSelection({item: workspace, selection: 'single', value: true}));
        } else if (scope === 'favorite') {
          this.dispatch(new fromHD.ToggleFavoriteWsSelection({selection: 'all', value: false}));
          this.dispatch(new fromHD.ToggleFavoriteWsSelection({item: workspace, selection: 'single', value: true}));
        } else if (scope === 'assigned') {
          this.dispatch(new fromHD.ToggleAssignedWsSelection({selection: 'all', value: false}));
          this.dispatch(new fromHD.ToggleAssignedWsSelection({item: workspace, selection: 'single', value: true}));
        } else if (scope === 'pinned') {
          this.dispatch(new fromHD.TogglePinnedWsSelection({selection: 'all', value: false}));
          this.dispatch(new fromHD.TogglePinnedWsSelection({item: workspace, selection: 'single', value: true}));
        }}, 200);
    }
  }

  async openSingleWorkspace(ws) {
    console.log('redirection', ws);
    this.router.navigate([`/workspace/${ws.workspaceContextCode}/${ws.workspaceUwYear}/projects`]);
    this.visible = false;
  }

  selectCheckboxChange(context, workspace, index) {
    event.preventDefault();
  }

  popOutWorkspaces() {
    this.visible = false;
    this.recent.filter(ws => ws.selected).forEach(ws => {
      window.open(`/workspace/${ws.wsId}/${ws.uwYear}/projects`);
    });
  }

  openWorkspaces(scope) {
    let selectedItems = null;
    if (scope === 'recent') {
      selectedItems = this.recentWs.filter(ws => ws.selected);
    } else if (scope === 'favorite') {
      selectedItems = this.favoriteWs.filter(ws => ws.selected);
    } else if (scope === 'assigned') {
      selectedItems = this.assignedWs.filter(ws => ws.selected);
    } else if (scope === 'pinned') {
      selectedItems = this.pinnedWs.filter(ws => ws.selected);
    }
    let workspaces = [];
    selectedItems.forEach(
      (ws) => {
        this.searchData(ws.workspaceContextCode, ws.workspaceUwYear).subscribe(
          (dt: any) => {
            const workspace = {
              workSpaceId: ws.workspaceContextCode,
              uwYear: ws.workspaceUwYear,
              selected: false,
              ...dt
            };
            this.store.dispatch(new workspaceActions.OpenWS({wsId: ws.workspaceContextCode,
              uwYear: ws.workspaceUwYear, route: 'projects', type: 'treaty'}));
            workspaces = [workspace, ...workspaces];
            if (workspaces.length === selectedItems.length) {
              this.visible = false;
            }
            this.detectChanges();
          }
        );
      }
    );
  }

  navigateToTab(value) {
    if (value.routing == 0) {
      this.router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}`]);
    } else {
      this.router.navigate([`workspace/${value.workSpaceId}/${value.uwYear}/${value.routing}`]);
    }
  }

  redirectWorkspace() {
    this.lastWorkspace$
      .pipe(take(1))
      .subscribe(data => {
        if (data) {
          const {wsId, uwYear, route} = data;
          return this.store.dispatch(
            [new UpdateWsRouting(wsId.concat('-', uwYear), route),
              new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
          );
        } else {
          return this.notificationService.createNotification('Information',
            'There is no Opened Workspaces please try searching for some before!',
            'error', 'bottomRight', 4000);
        }
      });

  }

  searchWorkspace(value) {
    this.togglePopup();
    const paginationOption = this.recent.workspacePagination.paginationList.filter(page => page.id === value);
    this.store.dispatch(new PatchWorkspaceMainStateAction({
      key: 'appliedFilter',
      value: {shownElement: paginationOption[0].shownElement}
    }));
  }

  /*  toggle(workspace: any, type: string, selected) {
      switch (type) {
        case 'favorite':
          this.store.dispatch(new PatchWorkspace({
            key: 'selectedF',
            value: !selected,
            ws: workspace,
          }));
          break;
        case 'pinged':
          this.store.dispatch(new PatchWorkspace({
            key: 'selectedP',
            value: !selected,
            ws: workspace,
          }));
          break;
      }
    }*/

  togglePopup() {
    this.store.dispatch([new fromHD.LoadRecentWorkspace({offset: 0, size: 10, userId: 1}),
      new fromHD.LoadWsStatusCount()]);
    this.loadPinned = false; this.loadFavorite = false; this.loadAssigned = false; this.loadRecent = true;
    HelperService.headerBarPopinChange$.next({from: this.componentName});
  }

}
