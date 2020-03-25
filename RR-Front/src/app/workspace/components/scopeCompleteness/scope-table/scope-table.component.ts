import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
  @Output('attachPLTOpen') attachPltOpen: any = new EventEmitter<any>();

  @Input()
  dataSource;
  @Input()
  override;
  @Input()
  selectedSortBy;
  @Input()
  sortBy;

  selectedDropDown: any;
  regionInnerCodes: any = {};
  targetInnerCodes: any = {};
  workspaceType: any;
  regionCodes: any = {};
  targetCodes: any = {};
  projectType: any;
  columns;
  keys;

  @Select(WorkspaceState.getSelectedProject) selectedProject$;
  selectedProject: any;
  @Select(WorkspaceState.getCurrentWS) currentWs$;
  currentWs;
  @Select(WorkspaceState.getScopeCompletenessData)scopeData$;
  scopeData;

  constructor( _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.selectedProject$.pipe().subscribe(value => {
      this.selectedProject = value;
      this.projectType = _.get(value, 'projectType', 'FAC');
      this.detectChanges();
    });
    this.currentWs$.pipe().subscribe(value => {
      this.currentWs =  value;
      this.workspaceType = _.get(value, 'workspaceType', 'fac');
      this.detectChanges();
    });
    this.scopeData$.pipe().subscribe(value => {
      this.scopeData = value;
      console.log(this.scopeData);
      this.detectChanges();
    });
    this.initTableCols();
  }

  initTableCols() {
    this.columns = [
      {field: 'PLtHeader', type: 'multi', width: '420px'}
    ];
    if (this.workspaceType === 'fac') {
      if(this.projectType === 'FAC') {
        _.forEach(this.selectedProject.divisions, item => {
          this.columns = [...this.columns, {field: '', type: 'contractCol', width: '80px', topHeader: `Division N°${item}`}]
        });
      }
    } else {
      _.forEach(this.currentWs.treatySection, item => {
        this.columns = [...this.columns, {field: '', type: 'contractCol', width: '80px', topHeader: item}]
      });
    }
    this.columns = [...this.columns, {field: 'completion', header: 'Completion', type: 'commentary', width: '100px'}]
  }

  expandAllRows() {
    if(this.selectedSortBy == 'RAP / Minimum Grain') {
      if (_.toArray(this.targetCodes).length < this.scopeData.targetRaps.length) {
        _.forEach(this.scopeData.targetRaps, item => {
          this.targetCodes[item.id] = true;
        })
      } else {
        _.forEach(this.scopeData.targetRaps, item => {
          _.forEach(item.regionPerils, rp => {
            this.targetInnerCodes[rp.id + item.id] = true;
          })
        })
      }
    } else {
      if (_.toArray(this.regionCodes).length < this.scopeData.regionPerils.length) {
        _.forEach(this.scopeData.regionPerils, item => {
          this.regionCodes[item.id] = true;
        })
      } else {
        _.forEach(this.scopeData.regionPerils, item => {
          _.forEach(item.targetRaps, tr => {
            this.regionInnerCodes[tr.id + item.id] = true;
          })
        })
      }
    }
  }

  closeAllRows() {
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      _.toArray(this.targetInnerCodes).length > 0 ? this.targetInnerCodes = {} : this.targetCodes = {};
    } else {
      _.toArray(this.regionInnerCodes).length > 0 ? this.regionInnerCodes = {} : this.regionCodes = {};
    }
  }

  expandInnerRP(innerRowId, rowId) {
    this.regionInnerCodes[innerRowId + rowId] = !this.regionInnerCodes[innerRowId + rowId];
  }

  expandInnerTR(innerRowId, rowId) {
    this.targetInnerCodes[innerRowId + rowId] = !this.targetInnerCodes[innerRowId + rowId];
  }

  openAttachPLT() {
    this.attachPltOpen.emit();
  }

  selectDropDown(event: boolean, rowId) {
    if (event) {
      this.selectedDropDown = rowId;
    } else {
      this.selectedDropDown = null;
    }
  }

  checkRowAttached(row) {
    let checked = true;
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      this.scopeData.regionPerils.forEach(rp => {
            if (rp.id == row.id) {
              if (!rp.attached && !rp.overridden) {
                checked = false;
              }
            }
          }
      );
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      this.scopeData.targetRaps.forEach(tr => {
            if (tr.id == row.id) {
              if (!tr.attached && !tr.overridden) {
                checked = false;
              }
            }
          }
      );
    }
    return checked;
  }

  overrideRow(row) {

  }

  selectedRow(row) {

  }

}
