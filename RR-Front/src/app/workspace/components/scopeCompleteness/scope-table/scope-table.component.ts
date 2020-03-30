import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';
import {Select, Store} from "@ngxs/store";
import {WorkspaceState} from "../../../store/states";
import {BaseContainer} from "../../../../shared/base";
import {Router} from "@angular/router";
import * as _ from "lodash";

@Component({
  selector: 'app-scope-table',
  templateUrl: './scope-table.component.html',
  styleUrls: ['./scope-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ScopeTableComponent extends BaseContainer implements OnInit {
  @Output('attachPLTOpen') attachPltOpen: any = new EventEmitter<any>();

  @Input()
  dataSource;

  overriddenChildRows = {};
  selectedDropDown: any;
  regionInnerCodes: any = {};
  targetInnerCodes: any = {};
  isAttachVisible = false;
  overriddenRows = {};
  workspaceType: any;
  scopeContext: any;
  regionCodes: any = {};
  targetCodes: any = {};
  projectType: any;
  columns;
  keys;

  @Select(WorkspaceState.getSelectedProject) selectedProject$;
  selectedProject: any;
  @Select(WorkspaceState.getCurrentWS) currentWs$;
  currentWs;
  @Select(WorkspaceState.getScopeCompletenessData) scopeData$;
  scopeData;
  @Select(WorkspaceState.getOverrideStatus) overrideStatus$;
  override;
  @Select(WorkspaceState.getScopeContext) scopeContext$;
  accumulationStatus;
  sortBy;
  filterBy;

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
      this.scopeContext = _.get(value, 'scopeContext', null);
      if(this.scopeContext !== null) {
        this.initTableCols();
      }
      this.detectChanges();
    });

    this.overrideStatus$.pipe().subscribe(value => {
      this.override = value;
      this.detectChanges();
    });

    this.scopeContext$.pipe().subscribe(value => {
      this.filterBy = _.get(value, 'filterBy');
      this.sortBy = _.get(value, 'sortBy');
      this.accumulationStatus = _.get(value, 'accumulationStatus');
      this.detectChanges();
    });
  }

  initTableCols() {
    this.columns = [
      {field: 'PLtHeader', type: 'multi', width: '420px', visible: true}
    ];
    if (this.workspaceType === 'fac') {
      if(this.projectType === 'FAC') {
        _.forEach(this.scopeContext, (item, index) => {
          this.columns = [...this.columns, {field: '', type: 'contractCol', width: '80px', visible: true, topHeader: `Division N°${index + 1}`, columnContext: item.id}]
        });
      }
    } else {
      _.forEach(this.currentWs.treatySection, item => {
        this.columns = [...this.columns, {field: '', type: 'contractCol', width: '80px', visible: true, topHeader: item}]
      });
    }
    this.columns = [...this.columns, {field: 'completion', header: 'Completion', type: 'commentary', width: '100px', visible: true}]
  }

  expandAllRows() {
    if(this.sortBy == 'RAP / Minimum Grain') {
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
    if (this.sortBy == 'RAP / Minimum Grain') {
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
    this.isAttachVisible = true;
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
    if (this.sortBy == 'Minimum Grain / RAP') {
      this.scopeData.regionPerils.forEach(rp => {
            if (rp.id == row.id) {
              if (!rp.attached && !rp.overridden) {
                checked = false;
              }
            }
          }
      );
    } else {
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

  checkRowChildAttached(rowData, row) {
    let checked = true;
    if (this.sortBy == 'Minimum Grain / RAP') {
      this.scopeData.regionPerils.forEach(rp => {
            if (rp.id == rowData.id) {
              rp.targetRaps.forEach(tr => {
                if (tr.id == row.id) {
                  if (!tr.attached && !tr.overridden) {
                    checked = false;
                  }
                }
              });
            }
          }
      );
    } else {
      this.scopeData.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.regionPerils.forEach(rp => {
                if (rp.id == row.id) {
                  if (!rp.attached && !rp.overridden) {
                    checked = false;
                  }
                }
              });
            }
          }
      );
    }
    return checked;
  }

  overrideRow(row, col) {
    //this.overriddenRows[row] = !this.overriddenRows[row];
    const overrideDone = _.get(this.overriddenRows, row.id + col.columnContext, false);
    if (this.sortBy == 'Minimum Grain / RAP') {
      if (overrideDone) {
        _.forEach(row.targetRaps, item => {
          if (_.toArray(item.pltsAttached).length === 0) {
            this.overriddenChildRows[row.id + item.id + col.columnContext] = false;
            this.overriddenRows[item.id + col.columnContext] = false;
            this.overriddenChildRows[item.id + row.id + col.columnContext] = false;
          }
        });
        this.overriddenRows[row.id + col.columnContext] = false;
      } else {
        _.forEach(row.targetRaps, item => {
          if (_.toArray(item.pltsAttached).length === 0) {
            this.overriddenChildRows[row.id + item.id + col.columnContext] = !this.overriddenChildRows[row.id + item.id + col.columnContext];
            this.overriddenChildRows[item.id + row.id + col.columnContext] = !this.overriddenChildRows[item.id + row.id + col.columnContext];
          }
        });
        _.forEach(this.scopeData.targetRaps, item => {
          let selectedAllRp = true;
          _.forEach(item.regionPerils, rp => {
            const overrideChild = _.get(this.overriddenChildRows, item.id + rp.id + col.columnContext, false);
            if (!overrideChild && _.toArray(rp.pltsAttached).length === 0) {
              selectedAllRp = false;
            }
          });
          this.overriddenRows[item.id + col.columnContext] = selectedAllRp;
        });
        this.overriddenRows[row.id + col.columnContext] = !this.overriddenRows[row + col.columnContext];

      }
    } else {
      if (overrideDone) {
        _.forEach(row.regionPerils, item => {
          if (_.toArray(item.pltsAttached).length === 0) {
            this.overriddenChildRows[row.id + item.id + col.columnContext] = false;
            this.overriddenRows[item.id + col.columnContext] = false;
            this.overriddenChildRows[item.id + row.id + col.columnContext] = false;
          }
        });
        this.overriddenRows[row.id + col.columnContext] = false;
      } else {
        _.forEach(row.regionPerils, item => {
          if (_.toArray(item.pltsAttached).length === 0) {
            this.overriddenChildRows[row.id + item.id + col.columnContext] = !this.overriddenChildRows[row.id + item.id + col.columnContext];
            this.overriddenChildRows[item.id + row.id + col.columnContext] = !this.overriddenChildRows[item.id + row.id + col.columnContext];
          }
        });
        _.forEach(this.scopeData.regionPerils, item => {
          let selectedAllRp = true;
          _.forEach(item.targetRaps, tr => {
            const overrideChild = _.get(this.overriddenChildRows, item.id + tr.id + col.columnContext, false);
            if (!overrideChild && _.toArray(tr.pltsAttached).length === 0) {
              selectedAllRp = false;
            }
          });
          this.overriddenRows[item.id + col.columnContext] = selectedAllRp;
        });
        this.overriddenRows[row.id + col.columnContext] = !this.overriddenRows[row + col.columnContext];
      }
    }
  }

  overrideChildMode(rowData, row, col) {
    const overrideDone = _.get(this.overriddenChildRows, rowData.id + row.id + col.columnContext, false);
    if (overrideDone) {
      this.overriddenChildRows[rowData.id + row.id + col.columnContext] = false;
      this.overriddenChildRows[row.id + rowData.id + col.columnContext] = false;
      this.overriddenRows[row.id + col.columnContext] = false;
      this.overriddenRows[rowData.id + col.columnContext] = false;
    } else {
      this.overriddenChildRows[rowData.id + row.id + col.columnContext] = !this.overriddenChildRows[rowData.id + row.id + col.columnContext];
      this.overriddenChildRows[row.id + rowData.id + col.columnContext] = !this.overriddenChildRows[row.id + rowData.id + col.columnContext];
      _.forEach(this.scopeData.regionPerils, item => {
        let selectedAllRp = true;
        _.forEach(item.targetRaps, tr => {
          const overrideChild = _.get(this.overriddenChildRows, item.id + tr.id + col.columnContext, false);
          if (!overrideChild && _.toArray(tr.pltsAttached).length === 0) {
            selectedAllRp = false;
          }
        });
        this.overriddenRows[item.id + col.columnContext] = selectedAllRp;
      });
      _.forEach(this.scopeData.targetRaps, item => {
        let selectedAllRp = true;
        _.forEach(item.regionPerils, rp => {
          const overrideChild = _.get(this.overriddenChildRows, item.id + rp.id + col.columnContext, false);
          if (!overrideChild && _.toArray(rp.pltsAttached).length === 0) {
            selectedAllRp = false;
          }
        });
        this.overriddenRows[item.id + col.columnContext] = selectedAllRp;
      });
    }
  }

  checkExpected(rowData, column) {
    let checked = 'not';
    let holder = [];

    if (_.includes(rowData.scopeContext, column.columnContext)) {
      if (this.sortBy === 'Minimum Grain / RAP') {
        rowData.targetRaps.forEach(tr => {
          if (tr.attached) {
            holder.push('attached');
          }
          if (tr.overridden) {
            holder.push('overridden');
          }
          if (!tr.attached && !tr.overridden) {
            // const check = _.get(this.listOfPltsData, `${rowData.id}.${des.id}`, null)
            // const check = this.listOfPltsData[rowData.id][des.id]
            // if (check != null) {
            /*          if (this.listOfPltsData[rowData.id] && this.listOfPltsData[rowData.id][des.id]) {
                        if (this.listOfPltsData[rowData.id][des.id].length) {
                          holder.push('dispoWs');
                        }
                      } else {*/
            holder.push('checked');
            // }
          }
        });
      }
      if (this.sortBy === 'RAP / Minimum Grain') {
        rowData.regionPerils.forEach(rg => {
          if (rg.attached) {
            holder.push('attached');
          }
          if (rg.overridden) {
            holder.push('overridden');
          }
          if (!rg.attached && !rg.overridden) {
            /*          if (this.listOfPltsData[des.id] && this.listOfPltsData[des.id][rowData.id]) {
                        if (this.listOfPltsData[des.id][rowData.id].length) {
                          holder.push('dispoWs');
                        }
                      } else {*/
            holder.push('checked');
            // }
          }
        });
      }

      if (_.includes(holder, 'dispoWs')) {
        checked = 'dispoWs';
      } else if (_.includes(holder, 'checked')) {
        checked = 'checked';
      } else if (_.includes(holder, 'attached')) {
        checked = 'attached';
      } else if (_.includes(holder, 'overridden')) {
        checked = 'overridden';
      }
      if (rowData.override) {
        if (checked == 'checked' || checked == 'dispoWs') {
          checked = 'override';
        }
      }

      /*    if (checked == 'overridden' && this.removeOverride) {
            checked = 'cancelOverride';
          }*/
      return checked;
    } else {
      return null;
    }
  }

  checkInnerExpected(row, column) {
/*    let checked = 'not';

    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      this.scopeData.regionPerils.forEach(reg => {
        if (reg.id == rowData.id) {
          reg.targetRaps.forEach(des => {
            if (des.id == grain.id) {
              if (des.attached) {
                checked = 'attached';
              }
              if (des.overridden) {
                checked = 'overridden';
              }
              if (!des.attached && !des.overridden) {
                const check = _.get(this.listOfPltsData, `${rowData.id}.${grain.id}`, null);
                if (check != null) {
                  if (this.listOfPltsData[rowData.id][grain.id]) {
                    if (this.listOfPltsData[rowData.id][grain.id].length) {
                      checked = 'dispoWs';
                    }
                  }
                } else {
                  checked = 'checked';
                }
              }
            }
          });
        }
      });
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      this.scopeData.targetRaps.forEach(reg => {
        if (reg.id == rowData.id) {
          reg.regionPerils.forEach(des => {
            if (des.id == grain.id) {
              if (des.attached) {
                checked = 'attached';
              }
              if (des.overridden) {
                checked = 'overridden';
              }
              if (!des.attached && !des.overridden) {
                const check = _.get(this.listOfPltsData, `${grain.id}.${rowData.id}`, null);
                if (check != null) {
                  if (this.listOfPltsData[grain.id][rowData.id]) {
                    if (this.listOfPltsData[grain.id][rowData.id].length) {
                      checked = 'dispoWs';
                    }
                  }
                } else {
                  checked = 'checked';
                }
              }
            }
          });
        }
      });
    }

    if (rowData.override || grain.override) {
      if (checked == 'checked' || checked == 'dispoWs') {
        checked = 'override';
      }
    }

    if (this.removeOverride && checked == 'overridden') {
      checked = 'override';
    }

    return checked;*/
  }

  getOverrideCapability(row, scope) {
    let overrideCapability = false;
    if (this.sortBy == 'Minimum Grain / RAP') {
      if (scope === 'parent') {
        _.forEach(row.targetRaps, item => {
          if (_.toArray(item.pltsAttached).length === 0) {
            overrideCapability = true
          }
        });
      } else {
        if (_.toArray(row.pltsAttached).length === 0) {
          overrideCapability = true
        }
      }
    } else {
      if (scope === 'parent') {
        _.forEach(row.regionPerils, item => {
          if (_.toArray(item.pltsAttached).length === 0) {
            overrideCapability = true
          }
        });
      } else {
        if (_.toArray(row.pltsAttached).length === 0) {
          overrideCapability = true
        }
      }
    }
    return overrideCapability;
  }

  selectedRow(row) {

  }

  closePopUp() {
    this.isAttachVisible = false;
  }

}
