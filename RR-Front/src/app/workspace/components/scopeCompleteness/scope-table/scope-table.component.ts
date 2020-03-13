import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {Select, Store} from "@ngxs/store";
import {WorkspaceState} from "../../../store/states";
import {BaseContainer} from "../../../../shared/base";
import {Router} from "@angular/router";
import * as _ from "lodash";

@Component({
  selector: 'app-scope-table',
  templateUrl: './scope-table.component.html',
  styleUrls: ['./scope-table.component.scss']
})
export class ScopeTableComponent extends BaseContainer implements OnInit {

  @Input()
  dataSource;

  columns;
  keys;

  @Select(WorkspaceState.getSelectedProject) selectedProject$;
  selectedProject: any;
  @Select(WorkspaceState.getCurrentWS) currentWs$;
  currentWs;

  constructor( _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.selectedProject$.pipe().subscribe(value => {
      this.selectedProject = value;
      this.detectChanges();
    });
    this.currentWs$.pipe().subscribe(value => {
      this.currentWs =  value;
      this.detectChanges();
    })
  }

  initTableCols() {
    this.columns = [
      {field: 'PLtHeader', type: 'multi', width: '420px'}
    ];
  }

  expandAllRows() {
/*    const regions = [];
    _.forEach(this.dataSource, data => {
      this.keys[data.id] = true;
      regions[data.id] = true;
    });
    if (_.keys(this.pTable.expandedRowKeys).length < _.keys(this.keys).length) {
      this.pTable.expandedRowKeys = {...this.keys};
      this.regionCodes = regions;
    } else {
      _.forEach(this.dataSource, row => {
        row.child.forEach(item => {
          this.grains[item.id + row.id] = true;
        });
      });
    }*/
    // }this.grains[item.id + rowData.id]

  }

  closeAllRows() {
/*    let check = false;
    _.forEach(this.grains, item => {
      if (item) {
        check = true;
      }
    });
    if (check) {
      _.forEach(this.dataSource, row => {
        row.child.forEach(item => {
          this.grains[item.id + row.id] = false;
        });
      });
    } else {
      this.pTable.expandedRowKeys = {};
      this.regionCodes = [];
    }*/
  }

  selectedRow(row) {

  }

}
