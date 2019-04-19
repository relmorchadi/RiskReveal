import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import {SearchService} from '../../../core/service/search.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';

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

  selectedWorkspaces: any = [];
  WorkspaceData: any;

  ProjectStatus: any = [
    {id: 'project1', selected: false},
    {id: 'project2', selected: false},
    {id: 'project3', selected: false},
    {id: 'project4', selected: false},
  ];


  constructor(private _helper: HelperService, private route: ActivatedRoute, private _searchService: SearchService) {

  }

  ngOnInit() {
    this._helper.collapseLeftMenu$.subscribe((e) => {
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });

    const pathName: any = window.location.pathname || '';
    if (pathName.includes('workspace')) {
      const workspaceId: any = pathName[pathName.length - 1];
      /*      if (workspaceId != null ) {
              this.tabs = [staticTabs[0]];
            }*/
    }
  }
  getSearchedWorkspaces() {
    return this._helper.getSearchedWorkspaces();
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  ngOnDestroy(): void {
    _.forEach(this.componentSubscription, (e) => _.invoke(e, 'unsubscribe'));
  }

  close(title, year) {
    this._helper.itemsRemove(title, year);
  }

  addWs(title, year) {
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
  }

  generateYear(year, years) {
    const generatedYears = years.filter(y => y < year);
    return generatedYears;
  }

  selectproject(id) {
    this.ProjectStatus.array.forEach(e => {
      if (e.id === id) {
        e.selected = !e.selected;
      }
    });
  }

}
