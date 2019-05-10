import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import {SearchService} from '../../../core/service/search.service';
import * as _ from 'lodash';
import {forkJoin, of} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {mergeMap} from 'rxjs/internal/operators/mergeMap';
import {delay} from 'rxjs/operators';
import {OpenWorkspaceMainAction} from '../../../core/store/actions';
import {Select, Store} from '@ngxs/store';
import {Location} from "@angular/common";

@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss']
})
export class WorkspaceMainComponent implements OnInit {
  leftNavbarIsCollapsed = false;
  tabs: any = [];
  collapseWorkspaceDetail = true;
  wsId: any;
  year: any;
  loading = false;
  sliceValidator = true;

  ws$: Observable<any>;
  ws = [];
  fromSingleWorkspace = false;

  constructor(private _helper: HelperService, private route: ActivatedRoute, private _searchService: SearchService, private store: Store) {

  }

  ngOnInit() {
    this._helper.collapseLeftMenu$.subscribe((e) => {
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });
    const pathName: any = window.location.pathname || '';
    this._helper.changeSelectedWorkspace$.subscribe(() => {
      this.loading = false;
      this.fromSingleWorkspace = false;
      this.getSearchedWorkspaces();
    });
    this.route.children[0] && this.route.children[0].params.subscribe(
      ({wsId, year}: any) => {
        console.log({wsId, year});
        this.wsId = wsId;
        this.year = year;
        this.wsId != null ? this.fromSingleWorkspace = true : this.fromSingleWorkspace = false;
        this.getSearchedWorkspaces();
      });

  }

  getSearchedWorkspaces() {
    if (this.wsId != undefined && this.fromSingleWorkspace == true) {
      this.loading = true;
      this.searchData(this.wsId, this.year).pipe(
        mergeMap((content: any) => {
          const item = {
            workSpaceId: this.wsId,
            uwYear: this.year,
            ...content
          };
          this.store.dispatch(new OpenWorkspaceMainAction(item));
          this.ws = [item];
          this._helper.affectItems([item], true);
          this.ws$ = of(this.ws);
          // this.loading = false;
          // return of();
          return forkJoin(...content.years.map((year) => this.searchData(this.wsId, year)));
        })
      ).subscribe((content) => {
        this.wsId = undefined;
        this.year = undefined;
        this.fromSingleWorkspace = true;
      });
    } else {
      this.fromSingleWorkspace = false;
      this.ws$ = this._helper.getSearchedWorkspaces();
    }
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  close(title, year) {
    console.log(this.fromSingleWorkspace);
    if (this.fromSingleWorkspace === false) {
      this._helper.itemsRemove(title, year);
    } else {
      this.ws = _.filter(this.ws, ws => {
        if (ws.workSpaceId === title && ws.uwYear == year) { return null; } else {return ws; }});
      this.ws$ = of(this.ws);
    }
  }

  addWs(title, year) {
    if (this.fromSingleWorkspace === false) {
      this.searchData(title, year).subscribe(
        (dt: any) => {
          const workspace = {
            workSpaceId: title,
            uwYear: year,
            ...dt
          };
          this._helper.itemsAppend(workspace);
          this.store.dispatch(new OpenWorkspaceMainAction(workspace));
        }
      );
    } else {
      this.searchData(title, year).subscribe(
        (dt: any) => {
          const workspace = {
            workSpaceId: title,
            uwYear: year,
            ...dt
          };
          this.store.dispatch(new OpenWorkspaceMainAction(workspace));
          this.ws = _.filter(this.ws, ws => {
            if (ws.workSpaceId == title && ws.uwYear == year) { return null; } else {return ws; }});
          this.ws = [...this.ws, workspace];
          this.ws$ = of(this.ws);
        }
      );
    }
  }

  generateYear(year, years, title = '') {
    let itemImported = JSON.parse(localStorage.getItem('workspaces')) || [];
    let generatedYears = years.filter(y => y != year ) || [];
    if (title !== '') {
      itemImported = itemImported.filter( dt => dt.workSpaceId === title);
      if (itemImported.length > 0) {
        itemImported.forEach( item => {
          generatedYears = generatedYears.filter(y =>  y != item.uwYear);
        });
      }
    }
    return generatedYears;
  }

  sliceContent(content: any, valid: boolean) {
    if (valid && content) {
      return content.slice(0, 3);
    } else {
      return content;
    }
  }

}
