import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../shared/helper.service';
import {SearchService} from '../core/service/search.service'
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
import {Observable} from "rxjs";
let staticTabs: any = [{title: 'TP07939', year: '2018'},
  {title: '02PY376', year: '2018'},
  {title: '02PY376', year: '2019'},
  {title: '06YE736', year: '2018'},
  {title: '19IT762', year: '2018'}
];
@Component({
  selector: 'app-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.scss']
})
export class WorkspaceComponent implements OnInit {
  leftNavbarIsCollapsed: boolean = false;
  tabs: any = [];
  collapseWorkspaceDetail: boolean = true;
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
  getSearchedWorkspaces(){
    return this._helper.getSearchedWorkspaces();
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  close(item) {
    this.tabs = _.filter(this.tabs, (i) => i !== item);
  }
  ngOnDestroy(): void {
    _.forEach(this.componentSubscription, (e) => _.invoke(e, 'unsubscribe'));
  }

  selectedWorkspaceset(items){
    this.selectedWorkspaces = [];
    items.forEach(
      ws => this.searchData(ws.title, ws.year).subscribe(
        data => {
          this.selectedWorkspaces = [...this.selectedWorkspaces, data];
          console.log(this.selectedWorkspaces);
        }
      )
    )

  }

  addWs(title, year) {
    this.tabs = [...this.tabs, {title, year}];
  }

  selectproject(id) {
    this.ProjectStatus.array.forEach(e => {
      if (e.id === id) {
        e.selected = !e.selected;
      }
    });
  }

}
