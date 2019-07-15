import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {HelperService, NotificationService} from '../../../../shared';
import {Router} from '@angular/router';
import {SearchService} from '../../../service/search.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import * as _ from 'lodash';
import {Observable} from 'rxjs';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../model/workspace-main';
import {WorkspaceMainState} from '../../../store/states/workspace-main.state';
import {
  OpenNewWorkspacesAction,
  PatchWorkspace,
  PatchWorkspaceMainStateAction
} from '../../../store/actions/workspace-main.action';
import {HeaderState} from "../../../store/states/header.state";
import * as fromHeader from "../../../store/actions/header.action";

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WorkspacesMenuItemComponent implements OnInit {

  readonly componentName: string = 'workspace-pop-in';

  contractFilterFormGroup: FormGroup;
  workspaces: any = [];
  selectedWorkspace = null;
  numberofElement: number;
  lastOnes = 0;
  visible: any;
  labels: any = [];
  lastSelectedIndex = null;

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;
  favorites: any;
  pinged: any;

  constructor(private _helperService: HelperService, private router: Router, private _searchService: SearchService,
              private _fb: FormBuilder, private store: Store, private notificationService: NotificationService, private cdRef: ChangeDetectorRef) {
    this.setForm();
    this.favoriteSize = 10;
    this.pingedSize = 10;
  }

  @Select(HeaderState.getFavorite) favorites$;
  @Select(HeaderState.getPinned) pinged$;
  @Select(HeaderState.getRecent) recentWs$;
  favoriteSize: any;
  pingedSize: any;
  favoriteSearch: any;
  pingedSearch: any;

  paginationParams: [
    { id: 0, shownElement: 10, label: 'Last 10' },
    { id: 1, shownElement: 50, label: 'Last 50' },
    { id: 2, shownElement: 100, label: 'Last 100' },
    { id: 3, shownElement: 150, label: 'Last 150' }];

  recentPagination = 0;

  ngOnInit() {
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this._searchService.infodropdown.subscribe(dt => this.visible = this._searchService.getvisibleDropdown());
    this.favorites$.subscribe(fv => {
      this.favorites = _.orderBy(fv, ['lastFModified'], ['desc']);
      this.detectChanges();
    });
    this.pinged$.subscribe(pn => {
      this.pinged = _.orderBy(pn, ['lastPModified'], ['desc']);
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

  setForm() {
    this.contractFilterFormGroup = this._fb.group({
      globalKeyword: [],
      workspaceId: [],
      workspaceName: [],
      year: [],
      treaty: [],
      cedantCode: [],
      cedant: [],
      country: []
    });
  }

  searchNewWorkspace(search) {
    this.contractFilterFormGroup.patchValue({cedant: search.target.value});
  }

  toggleWorkspace(context: string, workspace, index) {
    if ((window as any).event.ctrlKey) {
      this.store.dispatch(new fromHeader.ToggleWsSelection({context, index}));
      // workspace.selected = !workspace.selected;
      this.lastSelectedIndex = index;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.lastSelectedIndex || this.lastSelectedIndex === 0) {

        // this.selectSection(Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex));
        const [from, to] = [Math.min(index, this.lastSelectedIndex), Math.max(index, this.lastSelectedIndex)];
        this.store.dispatch(new fromHeader.SelectRange({context, from, to}))

        // this.lastSelectedIndex = null;
      } else {
        this.lastSelectedIndex = index;
        this.store.dispatch(new fromHeader.ChangeWsSelection({context, index, value: true}))
        // workspace.selected = true;
      }
    } else {
      // this.state.recentWs.forEach(res => res.selected = false);
      this.lastSelectedIndex = index;
      this.store.dispatch(new fromHeader.ApplySelectionToAll({context, value: false}))
      this.store.dispatch(new fromHeader.ToggleWsSelection({context, index}));
      // workspace.selected = !workspace.selected;
    }
  }

  selectCheckboxChange(context, workspace, index) {
    event.preventDefault();
  }

  private selectSection(from, to) {
    this.state.recentWs.forEach(dt => dt.selected = false);
    if (from === to) {
      this.state.recentWs[from].selected = true;
    } else {
      for (let i = from; i <= to; i++) {
        this.state.recentWs[i].selected = true;
      }
    }
  }

  popOutWorkspaces() {
    this.visible = false;
    this.state.recentWs.filter(ws => ws.selected).forEach(ws => {
      window.open(`/workspace/${ws.workSpaceId}/${ws.uwYear}/PopOut`);
    });
  }

  openWorkspaces() {
    const selectedItems = [...this.state.recentWs.filter(ws => ws.selected)];

    console.log('Selected items', selectedItems);
    // this.store.dispatch(new fromWs.openWS())

    // let workspaces = [];
    // selectedItems.forEach(
    //   (SI) => {
    //     this.searchData(SI.workSpaceId, SI.uwYear).subscribe(
    //       (dt: any) => {
    //         const workspace = {
    //           workSpaceId: SI.workSpaceId,
    //           uwYear: SI.uwYear,
    //           selected: false,
    //           ...dt
    //         };
    //         workspaces = [workspace, ...workspaces];
    //         if (workspaces.length === selectedItems.length) {
    //           this.store.dispatch(new OpenNewWorkspacesAction(workspaces));
    //           this._helperService.updateWorkspaceItems();
    //           this._helperService.updateRecentWorkspaces();
    //           this.router.navigate([`/workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}`]);
    //           this.visible = false;
    //           this.workspaces.forEach(ws => ws.selected = false);
    //         }
    //       }
    //     );
    //   }
    // );
  }

  async openSingleWorkspaces(ws) {
    this.searchData(ws.workSpaceId, ws.uwYear).subscribe(
      (dt: any) => {
        const workspace = {
          workSpaceId: ws.workSpaceId,
          uwYear: ws.uwYear,
          selected: false,
          ...dt
        };

        this.store.dispatch(new OpenNewWorkspacesAction([workspace]));
        this._helperService.updateWorkspaceItems();
        this._helperService.updateRecentWorkspaces();
        this.visible = false;
        this.workspaces.forEach(wcs => wcs.selected = false);
        this.router.navigate([`/workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}`]);
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
    if (this.state.openedTabs.data.length > 0) {
      this.navigateToTab(this.state.openedTabs.data[0]);
      this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'openedWs', value: this.state.openedTabs.data[0]}));
    } else {
      this.notificationService.createNotification('Information',
        'There is no Opened Workspaces please try searching for some before!',
        'error', 'bottomRight', 4000);
    }
  }

  searchWorkspace(value) {
    this.togglePopup();
    const paginationOption = this.state.workspacePagination.paginationList.filter(page => page.id === value);
    this.store.dispatch(new PatchWorkspaceMainStateAction({
      key: 'appliedFilter',
      value: {shownElement: paginationOption[0].shownElement}
    }));
  }

  toggle(workspace: any, type: string, selected) {
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
  }

  togglePopup() {
    HelperService.headerBarPopinChange$.next({from: this.componentName});
  }


}
