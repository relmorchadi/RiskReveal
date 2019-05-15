import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import {SearchService} from '../../../core/service/search.service';
import * as _ from 'lodash';
import {forkJoin, of} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {mergeMap} from 'rxjs/internal/operators/mergeMap';
import {delay} from 'rxjs/operators';
import {
  CloseWorkspaceMainAction,
  LoadWorkspacesAction, OpenNewWorkspacesAction,
  AppendNewWorkspaceMainAction,
  PatchWorkspaceMainStateAction
} from '../../../core/store/actions';
import {Select, Store} from '@ngxs/store';
import {Location} from '@angular/common';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {WorkspaceMainState} from '../../../core/store/states';

@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss']
})
export class WorkspaceMainComponent implements OnInit {
  leftNavbarIsCollapsed = false;
  wsId: any;
  year: any;
  tabIndex = 0;

  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;

  constructor(private _helper: HelperService, private route: ActivatedRoute, private _searchService: SearchService, private store: Store, private _router: Router) {

  }

  ngOnInit() {
    this.store.dispatch(new LoadWorkspacesAction());
    this.state$.subscribe(value => this.state = _.merge({}, value));
    this._helper.collapseLeftMenu$.subscribe((e) => {
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });
    const pathName: any = window.location.pathname || '';
    this._helper.changeSelectedWorkspace$.subscribe(() => {
      this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'loading', value: false}));
      this.getSearchedWorkspaces();
    });
    /*    this.route.children[0] && this.route.children[0].params.subscribe(
          ({wsId, year}: any) => {
            this.wsId = wsId;
            this.year = year;
            this.getSearchedWorkspaces();
          });*/

  }

  getSearchedWorkspaces() {
    if (this.wsId != undefined) {
      this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'loading', value: true}));
      this.searchData(this.wsId, this.year).pipe(
        mergeMap((content: any) => {
          const item = {
            workSpaceId: this.wsId,
            uwYear: this.year,
            selected: false,
            ...content
          };
          this.store.dispatch(new OpenNewWorkspacesAction([item]));
          this._helper.updateWorkspaceItems();
          return forkJoin(...content.years.map((year) => this.searchData(this.wsId, year)));
        })
      ).subscribe((content) => {
        this.wsId = undefined;
        this.year = undefined;
        this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'loading', value: false}));
      });
    }
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  close(title, year) {
    const state = this.state.openedWs;
    if (state.workSpaceId === title && state.uwYear == year) {
      this.store.dispatch(new CloseWorkspaceMainAction({workSpaceId: title, uwYear: year, same: true}));
      this.navigateToTab();
    } else {
      this.store.dispatch(new CloseWorkspaceMainAction({workSpaceId: title, uwYear: year, same: false}));
    }
    this._helper.updateWorkspaceItems();
  }

  navigateToTab() {
    if (this.state.openedWs.routing) {
      this._router.navigate([`workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}/${this.state.openedWs.routing}`]);
    } else {
      this._router.navigate([`workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}`]);
    }
  }

  addWs(title, year) {
    const alreadyOpened = this.state.openedTabs.filter(ws => ws.workSpaceId === title && ws.uwYear == year);
    const index = _.findIndex(this.state.openedTabs, ws => ws.workSpaceId === title && ws.uwYear == year);
    if (alreadyOpened.length > 0) {
      this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'openedWs', value: alreadyOpened[0]}));
      this.tabIndex = index;
    } else {
      this.searchData(title, year).subscribe(
        (dt: any) => {
          const workspace = {
            workSpaceId: title,
            uwYear: year,
            selected: false,
            ...dt
          };
          this.store.dispatch(new AppendNewWorkspaceMainAction(workspace));
          this._helper.updateWorkspaceItems();
          this._helper.updateRecentWorkspaces();
          this.tabIndex = this.state.openedTabs.length;
        }
      );
    }
    this.navigateToTab();
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
    return years.filter(y => y != year) || [];
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

  selectWorkspace(workspace) {
    this.store.dispatch(new PatchWorkspaceMainStateAction({key: 'openedWs', value: workspace}));
    this.navigateToTab();
  }

}
