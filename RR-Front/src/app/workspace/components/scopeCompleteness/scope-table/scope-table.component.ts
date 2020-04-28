import {ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Select, Store} from "@ngxs/store";
import {WorkspaceState} from "../../../store/states";
import {BaseContainer} from "../../../../shared/base";
import {Router} from "@angular/router";
import * as _ from "lodash";
import {
  LoadScopeCompletenessAccumulationInfo,
  LoadScopeCompletenessData, LoadScopeCompletenessPendingData,
  LoadScopeCompletenessPricingData, OverrideActiveAction, OverrideDeleteAction,
  PatchScopeOfCompletenessState
} from "../../../store/actions";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-scope-table',
  templateUrl: './scope-table.component.html',
  styleUrls: ['./scope-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ScopeTableComponent extends BaseContainer implements OnInit {
  @Output('overrideUnable') overrideUnable: any = new EventEmitter<any>();
  @Output('overrideRemoveUnable') overrideRemoveUnable: any = new EventEmitter<any>();
  @Output('showOverrideButton') showOverrideButton: any = new EventEmitter<any>();
  @Output('closeModal') closeModal: any = new EventEmitter<any>();

  @Input()
  dataSource;
  @Input()
  showOverrideModal = false;

  overriddenChildRemovedRows = {};
  rowOverrideRemoveInit = {};
  overriddenRemovedRows = {};
  overriddenChildRows = {};
  overrideNarrative: string;
  selectedDropDown: any;
  regionInnerCodes: any = {};
  targetInnerCodes: any = {};
  isAttachVisible = false;
  rowOverrideInit = {};
  overrideReason: string;
  overriddenRows = {};
  workspaceType: any;
  scopeContext: any;
  regionCodes: any = {};
  targetCodes: any = {};
  projectType: any;
  columns;
  keys;

  overrideAll = false;
  overrideRows = false;
  removeOverrideAll = false;
  removeOverrideRow = false;

  perilZone = {
    'EQ': '#E70010',
    'WS': '#CCCCCC',
    'FL': '#008694'
  };

  @Select(WorkspaceState.getSelectedProject) selectedProject$;
  selectedProject: any;
  @Select(WorkspaceState.getCurrentWS) currentWs$;
  currentWs;
  @Select(WorkspaceState.getScopeCompletenessData) scopeData$;
  scopeData;
  @Select(WorkspaceState.getScopeCompletenessPendingData) pendingData$;
  pendingData;
  @Select(WorkspaceState.getOverrideStatus) overrideStatus$;
  overrideStatus;
  @Select(WorkspaceState.getScopeContext) scopeContext$;
  accumulationStatus;
  sortBy;
  filterBy;

  constructor( _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.scopeContext$.pipe().subscribe(value => {
      this.filterBy = _.get(value, 'filterBy');
      this.sortBy = _.get(value, 'sortBy');
      this.accumulationStatus = _.get(value, 'accumulationStatus');
      if (this.accumulationStatus === 'Pricing' ) {
        this.dispatch([new LoadScopeCompletenessPricingData(), new PatchScopeOfCompletenessState({overrideAll: false})]);
      } else if (this.accumulationStatus === 'Scope Only') {
        this.dispatch([new LoadScopeCompletenessData(), new PatchScopeOfCompletenessState({overrideAll: false})]);
      } else if (this.accumulationStatus === 'Pending') {
        this.dispatch(new LoadScopeCompletenessAccumulationInfo());
      }
      this.detectChanges();
    });

    this.selectedProject$.pipe().subscribe(value => {
      this.selectedProject = value;
      this.projectType = _.get(value, 'projectType', 'FAC');
      if (this.accumulationStatus === 'Pricing' ) {
        this.dispatch(new LoadScopeCompletenessPricingData());
        this.overrideStop();
      } else if (this.accumulationStatus === 'Pending') {
        this.dispatch(new LoadScopeCompletenessAccumulationInfo());
      }
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

    this.pendingData$.pipe().subscribe(value => {
      this.pendingData =  value;
      this.detectChanges();
    });

    this.overrideStatus$.pipe().subscribe(value => {
      this.overrideStatus = value;
      this.overrideAll = value.overrideAll;
      this.overrideRows = value.overrideRow;
      this.removeOverrideAll = value.overrideCancelAll;
      this.removeOverrideRow = value.overrideCancelRow;
      value.overrideCancelStart ? this.removeOverrideStart() : null;
      if(!this.overrideAll && !this.overrideRows) {this.overrideStop() }
      if(!this.removeOverrideAll && !this.removeOverrideRow) {this.removeOverrideStop() }
      this.detectChanges();
    });
  }

  dataShown() {
    return this.accumulationStatus === 'Pending' ? this.pendingData : this.scopeData;
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
    const colIndex = _.findIndex(this.scopeContext, (item: any) => item.id === col.columnContext) + 1;
    if (this.sortBy == 'Minimum Grain / RAP') {
      if (overrideDone) {
        _.forEach(row.targetRaps, item => {
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (listPltAttached === 0) {
            this.overriddenChildRows[row.id + item.id + col.columnContext] = false;
            this.overriddenRows[item.id + col.columnContext] = false;
            this.overriddenChildRows[item.id + row.id + col.columnContext] = false;
          }
        });
        this.overriddenRows[row.id + col.columnContext] = false;
      } else {
        _.forEach(row.targetRaps, item => {
          const alreadyOverridden = _.get(item, `override.${colIndex}.overridden`, false);
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (listPltAttached === 0 && !alreadyOverridden) {
            this.overriddenChildRows[row.id + item.id + col.columnContext] = !this.overriddenChildRows[row.id + item.id + col.columnContext];
            this.overriddenChildRows[item.id + row.id + col.columnContext] = !this.overriddenChildRows[item.id + row.id + col.columnContext];
          }
        });
        _.forEach(this.pendingData.targetRaps, item => {
          let selectedAllRp = true;
          _.forEach(item.regionPerils, rp => {
            const overrideChild = _.get(this.overriddenChildRows, item.id + rp.id + col.columnContext, false);
            const alreadyOverridden = _.get(rp, `override.${colIndex}.overridden`, false);
            const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
            if (!overrideChild && listPltAttached === 0 && !alreadyOverridden) {
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
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (listPltAttached === 0) {
            this.overriddenChildRows[row.id + item.id + col.columnContext] = false;
            this.overriddenRows[item.id + col.columnContext] = false;
            this.overriddenChildRows[item.id + row.id + col.columnContext] = false;
          }
        });
        this.overriddenRows[row.id + col.columnContext] = false;
      } else {
        _.forEach(row.regionPerils, item => {
          const alreadyOverridden = _.get(item, `override.${colIndex}.overridden`, false);
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (listPltAttached === 0 && !alreadyOverridden) {
            this.overriddenChildRows[row.id + item.id + col.columnContext] = !this.overriddenChildRows[row.id + item.id + col.columnContext];
            this.overriddenChildRows[item.id + row.id + col.columnContext] = !this.overriddenChildRows[item.id + row.id + col.columnContext];
          }
        });
        _.forEach(this.pendingData.regionPerils, item => {
          let selectedAllRp = true;
          _.forEach(item.targetRaps, tr => {
            const overrideChild = _.get(this.overriddenChildRows, item.id + tr.id + col.columnContext, false);
            const alreadyOverridden = _.get(tr, `override.${colIndex}.overridden`, false);
            const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
            if (!overrideChild && listPltAttached === 0 && !alreadyOverridden) {
              selectedAllRp = false;
            }
          });
          this.overriddenRows[item.id + col.columnContext] = selectedAllRp;
        });
        this.overriddenRows[row.id + col.columnContext] = !this.overriddenRows[row + col.columnContext];
      }
    }

    const childValues = _.values(this.overriddenChildRows);
    this.overrideUnable.emit(_.includes(childValues, true));
  }

  overrideChildMode(rowData, row, col) {
    const overrideDone = _.get(this.overriddenChildRows, rowData.id + row.id + col.columnContext, false);
    const colIndex = _.findIndex(this.scopeContext, (item: any) => item.id === col.columnContext) + 1;
    if (overrideDone) {
      this.overriddenChildRows[rowData.id + row.id + col.columnContext] = false;
      this.overriddenChildRows[row.id + rowData.id + col.columnContext] = false;
      this.overriddenRows[row.id + col.columnContext] = false;
      this.overriddenRows[rowData.id + col.columnContext] = false;
    } else {
      this.overriddenChildRows[rowData.id + row.id + col.columnContext] = !this.overriddenChildRows[rowData.id + row.id + col.columnContext];
      this.overriddenChildRows[row.id + rowData.id + col.columnContext] = !this.overriddenChildRows[row.id + rowData.id + col.columnContext];
      _.forEach(this.pendingData.regionPerils, item => {
        let selectedAllRp = true;
        _.forEach(item.targetRaps, tr => {
          const overrideChild = _.get(this.overriddenChildRows, item.id + tr.id + col.columnContext, false);
          const alreadyOverridden = _.get(tr, `override.${colIndex}.overridden`, false);
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (!overrideChild && listPltAttached === 0 && !alreadyOverridden) {
            selectedAllRp = false;
          }
        });
        this.overriddenRows[item.id + col.columnContext] = selectedAllRp;
      });
      _.forEach(this.pendingData.targetRaps, item => {
        let selectedAllRp = true;
        _.forEach(item.regionPerils, rp => {
          const overrideChild = _.get(this.overriddenChildRows, item.id + rp.id + col.columnContext, false);
          const alreadyOverridden = _.get(rp, `override.${colIndex}.overridden`, false);
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (!overrideChild && listPltAttached === 0 && !alreadyOverridden) {
            selectedAllRp = false;
          }
        });
        this.overriddenRows[item.id + col.columnContext] = selectedAllRp;
      });

      const childValues = _.values(this.overriddenChildRows);
      this.overrideUnable.emit(_.includes(childValues, true));
    }
  }

  overrideRemoveRow(row, col) {
    //this.overriddenRows[row] = !this.overriddenRows[row];
    const overrideDone = _.get(this.overriddenRemovedRows, row.id + col.columnContext, false);
    if (this.sortBy == 'Minimum Grain / RAP') {
      if (overrideDone) {
        _.forEach(row.targetRaps, item => {
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (listPltAttached === 0) {
            this.overriddenChildRemovedRows[row.id + item.id + col.columnContext] = false;
            this.overriddenRemovedRows[item.id + col.columnContext] = false;
            this.overriddenChildRemovedRows[item.id + row.id + col.columnContext] = false;
          }
        });
        this.overriddenRemovedRows[row.id + col.columnContext] = false;
      } else {
        _.forEach(row.targetRaps, item => {
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (listPltAttached === 0) {
            this.overriddenChildRemovedRows[row.id + item.id + col.columnContext] = !this.overriddenChildRemovedRows[row.id + item.id + col.columnContext];
            this.overriddenChildRemovedRows[item.id + row.id + col.columnContext] = !this.overriddenChildRemovedRows[item.id + row.id + col.columnContext];
          }
        });
        _.forEach(this.scopeData.targetRaps, item => {
          let selectedAllRp = true;
          _.forEach(item.regionPerils, rp => {
            const overrideChild = _.get(this.overriddenChildRemovedRows, item.id + rp.id + col.columnContext, false);
            const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
            if (!overrideChild && listPltAttached=== 0) {
              selectedAllRp = false;
            }
          });
          this.overriddenRemovedRows[item.id + col.columnContext] = selectedAllRp;
        });
        this.overriddenRemovedRows[row.id + col.columnContext] = !this.overriddenRemovedRows[row + col.columnContext];

      }
    } else {
      if (overrideDone) {
        _.forEach(row.regionPerils, item => {
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (listPltAttached === 0) {
            this.overriddenChildRemovedRows[row.id + item.id + col.columnContext] = false;
            this.overriddenRemovedRows[item.id + col.columnContext] = false;
            this.overriddenChildRemovedRows[item.id + row.id + col.columnContext] = false;
          }
        });
        this.overriddenRemovedRows[row.id + col.columnContext] = false;
      } else {
        _.forEach(row.regionPerils, item => {
          const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (listPltAttached === 0) {
            this.overriddenChildRemovedRows[row.id + item.id + col.columnContext] = !this.overriddenChildRemovedRows[row.id + item.id + col.columnContext];
            this.overriddenChildRemovedRows[item.id + row.id + col.columnContext] = !this.overriddenChildRemovedRows[item.id + row.id + col.columnContext];
          }
        });
        _.forEach(this.scopeData.regionPerils, item => {
          let selectedAllRp = true;
          _.forEach(item.targetRaps, tr => {
            const overrideChild = _.get(this.overriddenChildRemovedRows, item.id + tr.id + col.columnContext, false);
            const listPltAttached = _.filter(_.toArray(item.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
            if (!overrideChild && listPltAttached === 0) {
              selectedAllRp = false;
            }
          });
          this.overriddenRemovedRows[item.id + col.columnContext] = selectedAllRp;
        });
        this.overriddenRemovedRows[row.id + col.columnContext] = !this.overriddenRemovedRows[row + col.columnContext];
      }
    }
    console.log(this.overriddenChildRemovedRows);
    const childValues = _.values(this.overriddenChildRemovedRows);
    this.overrideRemoveUnable.emit(_.includes(childValues, true));
  }

  overrideChildRemoveMode(rowData, row, col) {
    const overrideDone = _.get(this.overriddenChildRemovedRows, rowData.id + row.id + col.columnContext, false);
    if (overrideDone) {
      this.overriddenChildRemovedRows[rowData.id + row.id + col.columnContext] = false;
      this.overriddenChildRemovedRows[row.id + rowData.id + col.columnContext] = false;
      this.overriddenRemovedRows[row.id + col.columnContext] = false;
      this.overriddenRemovedRows[rowData.id + col.columnContext] = false;
    } else {
      this.overriddenChildRemovedRows[rowData.id + row.id + col.columnContext] = !this.overriddenChildRows[rowData.id + row.id + col.columnContext];
      this.overriddenChildRemovedRows[row.id + rowData.id + col.columnContext] = !this.overriddenChildRows[row.id + rowData.id + col.columnContext];
      _.forEach(this.scopeData.regionPerils, item => {
        let selectedAllRp = true;
        _.forEach(item.targetRaps, tr => {
          const overrideChild = _.get(this.overriddenChildRemovedRows, item.id + tr.id + col.columnContext, false);
          const listPltAttached = _.filter(_.toArray(tr.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (!overrideChild && listPltAttached === 0) {
            selectedAllRp = false;
          }
        });
        this.overriddenRemovedRows[item.id + col.columnContext] = selectedAllRp;
      });
      _.forEach(this.scopeData.targetRaps, item => {
        let selectedAllRp = true;
        _.forEach(item.regionPerils, rp => {
          const overrideChild = _.get(this.overriddenChildRemovedRows, item.id + rp.id + col.columnContext, false);
          const listPltAttached = _.filter(_.toArray(rp.pltsAttached), plt => _.includes(plt.scope, col.columnContext)).length;
          if (!overrideChild && listPltAttached === 0) {
            selectedAllRp = false;
          }
        });
        this.overriddenRemovedRows[item.id + col.columnContext] = selectedAllRp;
      });
    }

    console.log(this.overriddenChildRemovedRows);
    const childValues = _.values(this.overriddenChildRemovedRows);
    this.overrideRemoveUnable.emit(_.includes(childValues, true));
  }

  checkExpected(rowData, column, colHeader) {
    let checked = 'not';
    let holder = [];

    if (_.includes(rowData.scopeContext, column.columnContext)) {
      if (this.sortBy === 'Minimum Grain / RAP') {
        rowData.targetRaps.forEach(tr => {
          const override = _.get(tr, `override.${colHeader}.overridden`, false);
          const attachedPLTs = _.toArray(tr.pltsAttached);
          const attached = _.filter(attachedPLTs, item => _.includes(item.scopeIndex, colHeader));
          if (override) {
            holder.push('overridden');
          } else if (attached.length > 0) {
            holder.push('attached');
          } else {
            holder.push('checked');
          }
        });
      }
      if (this.sortBy === 'RAP / Minimum Grain') {
        rowData.regionPerils.forEach(rg => {
          const override = _.get(rg, `override.${colHeader}.overridden`, false);
          const attachedPLTs = _.toArray(rg.pltsAttached);
          const attached = _.filter(attachedPLTs, item => _.includes(item.scopeIndex, colHeader));
          if (override) {
            holder.push('overridden');
          } else if (attached.length > 0) {
            holder.push('attached');
          } else {
            holder.push('checked');
          }
        });
      }

      if (_.includes(holder, 'overridden') && _.includes(holder, 'attached')) {
        checked = 'dispoWs';
      } else if (_.includes(holder, 'checked')) {
        checked = 'checked';
      } else if (_.includes(holder, 'attached')) {
        checked = 'attached';
      } else if (_.includes(holder, 'overridden')) {
        checked = 'overridden';
      }

      return checked;
    } else {
      return null;
    }
  }

  checkChildExpected(rowData, column, colHeader) {
    const override = _.get(rowData, `override.${colHeader}.overridden`, false);
    const attached = _.toArray(rowData.pltsAttached);
    let attachedPLT = false;
    _.forEach(attached, item => {
      if (_.includes(item.scopeIndex, colHeader)) {
        attachedPLT = true;
      }
    });

    if (override) {
      return "overridden";
    } else if (attachedPLT) {
      return "attached";
    }
  }

  overrideRowStatusChange(rowData, row, value) {
    if (row === null) {
      this.rowOverrideInit[rowData.id] = value;
      if (this.sortBy === 'Minimum Grain / RAP') {
        _.forEach(rowData.targetRaps, item => {
          this.rowOverrideInit[rowData.id + item.id] = value;
        })
      } else {
        _.forEach(rowData.regionPerils, item => {
          this.rowOverrideInit[rowData.id + item.id] = value;
        })
      }
    } else {
      this.rowOverrideInit[rowData.id + row.id] = value;
    }

    const overrideRowUnable = _.values(this.rowOverrideInit);
    this.dispatch(new PatchScopeOfCompletenessState({overrideRow: _.includes(overrideRowUnable, true)}));
  }

  overrideRowRemoveStatusChange(rowData, row, value) {
    if (row === null) {
      this.rowOverrideRemoveInit[rowData.id] = value;
      if (this.sortBy === 'Minimum Grain / RAP') {
        _.forEach(rowData.targetRaps, item => {
          this.rowOverrideRemoveInit[rowData.id + item.id] = value;
        })
      } else {
        _.forEach(rowData.regionPerils, item => {
          this.rowOverrideRemoveInit[rowData.id + item.id] = value;
        })
      }
    } else {
      this.rowOverrideRemoveInit[rowData.id + row.id] = value;
    }
    const overrideRowUnable = _.values(this.rowOverrideRemoveInit);
    this.dispatch(new PatchScopeOfCompletenessState({overrideCancelRow: _.includes(overrideRowUnable, true)}));
  }

  overrideStart() {
    let selectedForOverride = [];
    _.forEach(this.scopeData.regionPerils, item => {
      _.forEach(item.targetRaps, tr => {
        _.forEach(this.scopeContext, (scope, key) => {
          const targetRegion = item.id + tr.id + scope.id;
          if (this.overriddenChildRows[targetRegion]) {
            selectedForOverride = [...selectedForOverride, {
              accumulationRAPCode: tr.id,
              contractSectionId: key + 1,
              minimumGrainRegionPerilCode: item.id
            }];
          }
        })
      })
    });
    this.dispatch([new OverrideActiveAction({listOfOverrides: selectedForOverride, overrideBasisCode: this.overrideReason, overrideBasisNarrative: this.overrideNarrative}),
      new PatchScopeOfCompletenessState({overrideAll: false})]);
    this.onHide(); this.overrideStop();
  }

  removeOverrideStart() {
    let selectedForOverride = [];
    _.forEach(this.pendingData.regionPerils, item => {
      _.forEach(item.targetRaps, tr => {
        _.forEach(this.scopeContext, (scope, key) => {
          const targetRegion = item.id + tr.id + scope.id;
          const overrideRemove = _.get(this.overriddenChildRemovedRows, `${targetRegion}`, false)
          if (overrideRemove) {
            selectedForOverride = [...selectedForOverride, {
              accumulationRAPCode: tr.id,
              contractSectionId: key + 1,
              minimumGrainRegionPerilCode: item.id
            }];
          }
        })
      })
    });
    this.dispatch([new OverrideDeleteAction({listOfRemovedItems: selectedForOverride}),
      new PatchScopeOfCompletenessState({overrideCancelAll: false, overrideCancelRow: false, removeOverrideUnable: false, overrideCancelStart: false})]);
  }

  overrideRowUnable(rowData, scope) {
    let overrideUnable = false;
    _.forEach(this.scopeContext, (scope, index) => {
      const colIndex = index + 1;
      const overrideCapable = this.getOverrideCapability(rowData, scope, colIndex);
      if (overrideCapable) {
        overrideUnable = true;
      }
    });
    return overrideUnable;
  }

  removeOverrideUnable(rowData, scope) {
    if (scope === 'child') {
      const override = _.toArray(_.get(rowData, 'override', {}));
      return override.length > 0;
    } else {
      if (this.sortBy == 'Minimum Grain / RAP') {
        let unabler = true;
        _.forEach(rowData.targetRaps, item => {
          const override = _.toArray(_.get(item, 'override', {}));
          if (override.length === 0) {
            unabler = false;
          }
        });
        return unabler;
      } else {
        let unabler = true;
        _.forEach(rowData.regionPerils, item => {
          const override = _.toArray(_.get(item, 'override', {}));
          if (override.length === 0) {
            unabler = false;
          }
        });
        return unabler
      }
    }
  }

  overrideStop() {
    this.overriddenRows = {};
    this.overriddenChildRows = {};
    this.rowOverrideInit = {};
    this.overrideUnable.emit(false);
  }

  removeOverrideStop() {
    this.rowOverrideRemoveInit = {};
  }

  getOverrideCapability(row, scope, colIndex) {
    const scopeContext = this.scopeContext[colIndex - 1].id;
    const rowScopeContext = _.includes(row.scopeContext, scopeContext);
    if(rowScopeContext) {
      let overrideCapability = true;
      let overridden = true;
      if (scope === 'parent') {
        if (this.sortBy == 'Minimum Grain / RAP') {
          _.forEach(row.targetRaps, item => {
            const override = _.get(item, `override.${colIndex}.overridden`, false);
            _.forEach(_.toArray(item.pltsAttached), plt => {
              if (_.includes(plt.scopeIndex, colIndex)) {
                overrideCapability = false;
              }
            });
            if (!override) {
              overridden = false;
            }
          });
        } else {
          _.forEach(row.regionPerils, item => {
            const override = _.get(item, `override.${colIndex}.overridden`, false);
            _.forEach(_.toArray(item.pltsAttached), plt => {
              if (_.includes(plt.scopeIndex, colIndex)) {
                overrideCapability = false;
              }
            });
            if (!override) {
              overridden = false;
            }
          });
        }
      } else {
        overridden = _.get(row, `override.${colIndex}.overridden`, false);
        _.forEach(row.pltsAttached, plt => {
          if (_.includes(plt.scopeIndex, colIndex)) {
            overrideCapability = false;
          }
        });
      }
      return overrideCapability && !overridden;
    } else {
      return false;
    }


  }

  removeOverrideCapability(row, col, scope) {
    if (scope === 'parent') {
      let removeOverride = false;
      if (this.sortBy == 'Minimum Grain / RAP') {
        _.forEach(row.targetRaps, item => {
          if (_.get(item, `override.${col}.overridden`, false)) {
            removeOverride = true;
          }
        })
      } else {
        _.forEach(row.regionPerils, item => {
          if (_.get(item, `override.${col}.overridden`, false)) {
            removeOverride = true;
          }
        })
      }
      return removeOverride;
    } else {
      return _.get(row, `override.${col}.overridden`, false);
    }
  }

  removeOverrideAccess(rowData, row) {
    if (row === null) {
      return this.removeOverrideAll || (this.removeOverrideRow && this.rowOverrideRemoveInit[rowData.id]);
    } else {
      return this.removeOverrideAll || (this.removeOverrideRow && this.rowOverrideRemoveInit[rowData.id + row.id]);
    }
  }

  attachedPLTToDivision(plt, col) {
    return _.includes(plt.scopeIndex, col);
  }

  onHide() {
    this.overrideNarrative = '';
    this.overrideReason = null;
    this.closeModal.emit(false);
  }

  closePopUp() {
    this.isAttachVisible = false;
  }

}
