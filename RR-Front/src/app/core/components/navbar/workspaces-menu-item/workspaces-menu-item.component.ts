import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {SearchService} from '../../../service/search.service';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import * as fromHD from '../../../store/actions';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import {
  HeaderState,
} from '../../../store/index';
import {HelperService} from '../../../../shared/helper.service';
import * as workspaceActions from '../../../../workspace/store/actions/workspace.actions';
import {UpdateWsRouting} from '../../../../workspace/store/actions/workspace.actions';
import {NotificationService} from '../../../../shared/notification.service';
import {BaseContainer} from '../../../../shared/base';
import {WorkspaceState} from "../../../../workspace/store/states";
import {debounceTime, take, takeUntil} from "rxjs/operators";
import {Navigate} from "@ngxs/router-plugin";
import {Subject, Subscription} from "rxjs";

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
  visible: any;
  labels: any = [];

  recent = null;
  pinged: any;

  @Select(HeaderState.getAssignedWs) assignedWs$;
  @Select(HeaderState.getRecentWs) recentWs$;
  @Select(HeaderState.getFavoriteWs) favoriteWs$;
  @Select(HeaderState.getPinnedWs) pinnedWs$;
  @Select(HeaderState.getStatusCountWs) countWs$;
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

  LoadedRecent = 10;
  LoadedFavorite = 10;
  LoadedAssigned = 10;
  LoadedPinned = 10;

  paginationParams: [
    { id: 0, shownElement: 10, label: 'Last 10' },
    { id: 1, shownElement: 50, label: 'Last 50' },
    { id: 2, shownElement: 100, label: 'Last 100' },
    { id: 3, shownElement: 150, label: 'Last 150' }];

  subscriptionsRecent: Subscription;
  subscriptionsFavorite: Subscription;
  subscriptionsAssigned: Subscription;
  subscriptionsPinned: Subscription;
  private unSubscribeRec$: Subject<void>;
  private unSubscribeFav$: Subject<void>;
  private unSubscribeAssigned$: Subject<void>;
  private unSubscribePin$: Subject<void>;

  selectedRowsRecent: any;
  selectedRowsFavorites: any;
  selectedRowsAssigned: any;
  selectedRowsPinned: any;

  workspaceCols = [
    {width: '15px', type: 'select'},
    {width: '160px', type: 'multi'},
    {width: '35px', type: 'text'}
  ];

  constructor(private _helperService: HelperService,
              private router: Router, private _searchService: SearchService,
              _baseStore: Store, _baseRouter: Router,
              _baseCdr: ChangeDetectorRef,
              private _fb: FormBuilder, private store: Store,
              private notificationService: NotificationService,
              private cdRef: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.unSubscribeRec$ = new Subject<void>();
    this.unSubscribeFav$ = new Subject<void>();
    this.unSubscribeAssigned$ = new Subject<void>();
    this.unSubscribePin$ = new Subject<void>();
  }

  ngOnInit() {
    this.countWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.countWs = _.merge({}, value);
      this.detectChanges();
    });
    this.recentWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.recentWs = value;
      this.detectChanges();
    });
    this.favoriteWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.favoriteWs = value;
      this.detectChanges();
    });
    this.pinnedWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.pinnedWs = value;
      this.detectChanges();
    });
    this.assignedWs$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.assignedWs = value;
      this.detectChanges();
    });

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

    this.initForm();
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  private _subscribeRecentSearchChanges() {
    this._unsubscribeToFormChanges();
    this.subscriptionsRecent = this.contractFilterFormGroup.get('recentSearch')
      .valueChanges
      .pipe(takeUntil(this.unSubscribeRec$), debounceTime(500))
      .subscribe((value) => {
        this.dispatch(new fromHD.LoadRecentWorkspace({offset: 0, size: this.recentPageable, userId: 1, search: value}))
      });

    this.subscriptionsFavorite = this.contractFilterFormGroup.get('favoriteSearch')
      .valueChanges
      .pipe(takeUntil(this.unSubscribeFav$), debounceTime(500))
      .subscribe((value) => {
        this.dispatch(new fromHD.LoadFavoriteWorkspace({
          offset: 0,
          size: this.favoritePageable,
          userId: 1,
          search: value
        }))
      });

    this.subscriptionsAssigned = this.contractFilterFormGroup.get('assignedSearch')
      .valueChanges
      .pipe(takeUntil(this.unSubscribeAssigned$), debounceTime(500))
      .subscribe((value) => {
        this.dispatch(new fromHD.LoadAssignedWorkspace({
          offset: 0,
          size: this.assignedPageable,
          userId: 1,
          search: value
        }))
      });

    this.subscriptionsPinned = this.contractFilterFormGroup.get('pinnedSearch')
      .valueChanges
      .pipe(takeUntil(this.unSubscribePin$), debounceTime(500))
      .subscribe((value) => {
        this.dispatch(new fromHD.LoadPinnedWorkspace({offset: 0, size: this.pinnedPageable, userId: 1, search: value}))
      });
  }

  private _unsubscribeToFormChanges() {
    this.subscriptionsRecent ? this.subscriptionsRecent.unsubscribe() : null;
    this.subscriptionsFavorite ? this.subscriptionsFavorite.unsubscribe() : null;
    this.subscriptionsAssigned ? this.subscriptionsAssigned.unsubscribe() : null;
    this.subscriptionsPinned ? this.subscriptionsPinned.unsubscribe() : null;
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
      const size = $event - this.LoadedRecent;
      if (size > 0) {
        this.store.dispatch(new fromHD.LoadRecentWorkspace({
          offset: this.LoadedRecent, search: this.recentSearch,
          size, userId: 1, option: 'append'
        }));
        this.LoadedRecent = $event;
      }
    } else if (scope === 'favorite') {
      const size = $event - this.LoadedFavorite;
      if (size > 0) {
        this.store.dispatch(new fromHD.LoadFavoriteWorkspace({
          offset: this.LoadedFavorite, search: this.favoriteSearch,
          size, userId: 1, option: 'append'
        }));
        this.LoadedFavorite = $event;
      }
    } else if (scope === 'assigned') {
      const size = $event - this.LoadedAssigned;
      if (size > 0) {
        this.store.dispatch(new fromHD.LoadAssignedWorkspace({
          offset: this.LoadedAssigned, search: this.assignedSearch,
          size, userId: 1, option: 'append'
        }));
      }
      this.assignedPageable = $event;
    } else if (scope === 'pinned') {
      const size = $event - this.LoadedPinned;
      if (size > 0) {
        this.store.dispatch(new fromHD.LoadPinnedWorkspace({
          offset: this.LoadedPinned, search: this.pinnedSearch,
          size, userId: 1, option: 'append'
        }));
        this.LoadedPinned = $event;
      }
    }
  }

  searchNewWorkspace(search, scope) {
    switch (scope) {
      case 'recent' :
        this.contractFilterFormGroup.patchValue({recentSearch: search.target.value});
        break;
      case 'favorite' :
        this.contractFilterFormGroup.patchValue({favoriteSearch: search.target.value});
        break;
      case 'assigned' :
        this.contractFilterFormGroup.patchValue({assignedSearch: search.target.value});
        break;
      case 'pinned' :
        this.contractFilterFormGroup.patchValue({pinnedSearch: search.target.value});
        break;
    }
  }

  getSelection($event, scope) {
    if ($event.length > 1) {
      if (scope === 'recent') {
        this.dispatch(new fromHD.ToggleRecentWsSelection({item: $event, selection: 'chunk'}));
      } else if (scope === 'favorite') {
        this.dispatch(new fromHD.ToggleFavoriteWsSelection({item: $event, selection: 'chunk'}));
      } else if (scope === 'assigned') {
        this.dispatch(new fromHD.ToggleAssignedWsSelection({item: $event, selection: 'chunk'}));
      } else if (scope === 'pinned') {
        this.dispatch(new fromHD.TogglePinnedWsSelection({item: $event, selection: 'chunk'}));

      }
    }
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
        }
      }, 200);
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

  popOutWorkspaces(scope) {
    this.visible = false;
    if (scope === 'recent') {
      this.recentWs.filter(ws => ws.selected).forEach(ws => {
        window.open(`/workspace/${ws.workspaceContextCode}/${ws.workspaceUwYear}/projects`);
      });
    } else if (scope === 'favorite') {
      this.favoriteWs.filter(ws => ws.selected).forEach(ws => {
        window.open(`/workspace/${ws.workspaceContextCode}/${ws.workspaceUwYear}/projects`);
      });
    } else if (scope === 'assigned') {
      this.assignedWs.filter(ws => ws.selected).forEach(ws => {
        window.open(`/workspace/${ws.workspaceContextCode}/${ws.workspaceUwYear}/projects`);
      });
    } else if (scope === 'pinned') {
      this.pinnedWs.filter(ws => ws.selected).forEach(ws => {
        window.open(`/workspace/${ws.workspaceContextCode}/${ws.workspaceUwYear}/projects`);
      });
    }
  }

  openWorkspaces(scope) {
    let selectedItems = [];
    let workspaces = [];
    if (scope === 'recent') {
      selectedItems = this.recentWs.filter(ws => ws.selected);
    } else if (scope === 'favorite') {
      selectedItems = this.favoriteWs.filter(ws => ws.selected);
    } else if (scope === 'assigned') {
      selectedItems = this.assignedWs.filter(ws => ws.selected);
    } else if (scope === 'pinned') {
      selectedItems = this.pinnedWs.filter(ws => ws.selected);
    }
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
            this.store.dispatch(new workspaceActions.OpenWS({
              wsId: ws.workspaceContextCode,
              uwYear: ws.workspaceUwYear, route: 'projects', type: 'treaty'
            }));
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
  }

  initForm() {
    this.contractFilterFormGroup = new FormGroup({
      recentSearch: new FormControl(''),
      favoriteSearch: new FormControl(''),
      assignedSearch: new FormControl(''),
      pinnedSearch: new FormControl(''),
    });
  }

  togglePopup() {
    this.store.dispatch([new fromHD.LoadRecentWorkspace({offset: 0, size: 10, userId: 1}),
      new fromHD.LoadWsStatusCount()]);
    this.loadPinned = false;
    this.loadFavorite = false;
    this.loadAssigned = false;
    this.loadRecent = true;
    this._subscribeRecentSearchChanges();
    HelperService.headerBarPopinChange$.next({from: this.componentName});
  }

  rowTrackBy = (index, item) => {
    return item.workspaceId;
  }

}
