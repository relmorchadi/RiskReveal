import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import {SearchService} from '../../../core/service/search.service';
import * as _ from 'lodash';
import {forkJoin, of} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {mergeMap} from "rxjs/internal/operators/mergeMap";
import {delay} from 'rxjs/operators';

@Component({
  selector: 'app-workspace-main',
  templateUrl: './workspace-main.component.html',
  styleUrls: ['./workspace-main.component.scss']
})
export class WorkspaceMainComponent implements OnInit {
  leftNavbarIsCollapsed = false;
  tabs: any = [];
  collapseWorkspaceDetail = true;
  componentSubscription: any = [];
  selectedPrStatus = '1';
  wsId: any;
  year: any;
  loading = false;
  sliceValidator = true;
  selectedWorkspaces: any = [];
  WorkspaceData: any;

  ProjectStatus: any = [
    {id: 'project1', selected: false},
    {id: 'project2', selected: false},
    {id: 'project3', selected: false},
    {id: 'project4', selected: false},
  ];

  ws$: Observable<any>;
  ws = [];
  fromSingleWorkspace = false;

  constructor(private _helper: HelperService, private route: ActivatedRoute, private _searchService: SearchService) {

  }

  ngOnInit() {
    this._helper.collapseLeftMenu$.subscribe((e) => {
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });
    const pathName: any = window.location.pathname || '';
    this.route.children[0] && this.route.children[0].params.subscribe(
      ({wsId, year}: any) => {
        console.log({wsId, year});
        this.wsId = wsId;
        this.year = year;
        this.wsId != null ? this.fromSingleWorkspace = true : this.fromSingleWorkspace = false;
        this.getSearchedWorkspaces();
      });
    this._helper.changeSelectedWorkspace$.subscribe((e: any) => {
      this.loading = false;
      this.fromSingleWorkspace = false;
      this.getSearchedWorkspaces();
    });
  }

  getSearchedWorkspaces() {
    if (this.wsId != undefined && this.fromSingleWorkspace == true) {
      this.loading = true;
      this.searchData(this.wsId, this.year).pipe(
        mergeMap((content: any) => {
          const item = {
            uwYear: this.year,
            workSpaceId: this.wsId,
            cedantCode: content.cedantCode,
            cedantName: content.cedantName,
            ledgerName: content.ledgerName,
            ledgerId: content.subsidiaryLedgerId,
            subsidiaryName: content.subsidiaryName,
            subsidiaryId: content.subsidiaryId,
            expiryDate: content.expiryDate,
            inceptionDate: content.inceptionDate,
            treatySections: content.treatySections,
            workspaceName: content.worspaceName,
            years: content.years
          };
          this.ws = [item];
          this._helper.affectItems([item], true);
          this.ws$ = of(this.ws);
          // this.loading = false;
          return of();
/*          return forkJoin(...content.years.map((year) => this.searchData(this.wsId, year)));*/
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
            uwYear: year,
            workSpaceId: title,
            cedantCode: dt.cedantCode,
            cedantName: dt.cedantName,
            ledgerName: dt.ledgerName,
            ledgerId: dt.subsidiaryLedgerId,
            subsidiaryName: dt.subsidiaryName,
            subsidiaryId: dt.subsidiaryId,
            expiryDate: dt.expiryDate,
            inceptionDate: dt.inceptionDate,
            treatySections: dt.treatySections,
            workspaceName: dt.worspaceName,
            years: dt.years
          };
          this._helper.itemsAppend(workspace);
        }
      );
    } else {
      this.searchData(title, year).subscribe(
        (dt: any) => {
          const workspace = {
            uwYear: year,
            workSpaceId: title,
            cedantCode: dt.cedantCode,
            cedantName: dt.cedantName,
            ledgerName: dt.ledgerName,
            ledgerId: dt.subsidiaryLedgerId,
            subsidiaryName: dt.subsidiaryName,
            subsidiaryId: dt.subsidiaryId,
            expiryDate: dt.expiryDate,
            inceptionDate: dt.inceptionDate,
            treatySections: dt.treatySections,
            workspaceName: dt.worspaceName,
            years: dt.years
          };
          this.ws = _.filter(this.ws, ws => {
            if (ws.workSpaceId == title && ws.uwYear == year) { return null; } else {return ws; }});
          this.ws = [...this.ws, workspace];
          this.ws$ = of(this.ws);
        }
      );
    }
  }

  generateYear(year, years) {
    const generatedYears = years.filter(y => y < year);
    return generatedYears;
  }

  sliceContent(content: any, valid: boolean) {
    if (valid && content) {
      return content.slice(0, 3);
    } else {
      return content;
    }
  }

  selectproject(id) {
    this.ProjectStatus.array.forEach(e => {
      if (e.id === id) {
        e.selected = !e.selected;
      }
    });
  }

}
