import {Component, OnDestroy, OnInit} from '@angular/core';
import {HelperService} from '../../../shared/helper.service';
import * as _ from 'lodash';
import {ActivatedRoute} from '@angular/router';
@Component({
  selector: 'app-workspace-project',
  templateUrl: './workspace-project.component.html',
  styleUrls: ['./workspace-project.component.scss']
})
export class WorkspaceProjectComponent implements OnInit {
  leftNavbarIsCollapsed = false;
  collapseWorkspaceDetail = true;
  componentSubscription: any = [];
  selectedPrStatus = '1';

  ProjectStatus: any = [
    {id: 'project1', selected: false},
    {id: 'project2', selected: false},
    {id: 'project3', selected: false},
    {id: 'project4', selected: false},
  ];

  constructor(private _helper: HelperService, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this._helper.collapseLeftMenu$.subscribe((e) => {
      this.leftNavbarIsCollapsed = !this.leftNavbarIsCollapsed;
    });
    const pathName: any = window.location.pathname || '';
  }
  ngOnDestroy(): void {
    _.forEach(this.componentSubscription, (e) => _.invoke(e, 'unsubscribe'));
  }
  selectproject(id) {
    this.ProjectStatus.array.forEach(e => {
      if (e.id === id) {
        e.selected = !e.selected;
      }
    });
  }


}
