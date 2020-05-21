import {ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Actions, ofActionCompleted, ofActionDispatched, ofActionSuccessful, Select, Store} from "@ngxs/store";
import {WorkspaceState} from "../../../store/states";
import {BaseContainer} from "../../../../shared/base";
import {Router} from "@angular/router";
import * as _ from "lodash";
import {
  LoadScopeCompletenessAccumulationInfo,
  LoadScopeCompletenessData, LoadScopeCompletenessPendingData,
  LoadScopeCompletenessPricingData, LoadScopePLTsData, OverrideActiveAction, OverrideDeleteAction,
  PatchScopeOfCompletenessState, SelectScopeProject
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
  loading = false;
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
  @Select(WorkspaceState.getScopePLTs) plts$;
  plts;
  @Select(WorkspaceState.getScopeCompletenessData) scopeData$;
  scopeData;
  @Select(WorkspaceState.getScopeCompletenessPendingData) pendingData$;
  pendingData;
  @Select(WorkspaceState.getOverrideStatus) overrideStatus$;
  overrideStatus;
  @Select(WorkspaceState.getScopeContext) scopeContext$;
  accumulationStatus = 'Scope Only';
  sortBy;
  filterBy;

  constructor( _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
               private actions: Actions) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.selectedProject$.pipe().subscribe(value => {
      this.selectedProject = value;
      this.projectType = _.get(value, 'projectType', 'FAC');
      this.dispatch(new SelectScopeProject({projectId: this.selectedProject.projectId}));
      this.loading = true;
      if (this.accumulationStatus === 'Pricing' ) {
        this.dispatch([new LoadScopeCompletenessPricingData()]).subscribe(() => {}, () => {}, () => {
          this.loading = false;
          this.detectChanges();
        });
        this.overrideStop();
      } else if (this.accumulationStatus === 'Scope Only') {
        this.dispatch([new LoadScopeCompletenessData(), new LoadScopePLTsData()]).subscribe(() => {}, () => {}, () => {
          this.loading = false;
          this.detectChanges();
        });
        this.overrideStop();
      } else if (this.accumulationStatus === 'Pending') {
        this.dispatch([new LoadScopeCompletenessAccumulationInfo()]).subscribe(() => {}, () => {}, () => {
          this.loading = false;
          this.detectChanges();
        });
      }
      this.detectChanges();
    });

    this.scopeContext$.pipe().subscribe(value => {
      this.filterBy = _.get(value, 'filterBy');
      this.sortBy = _.get(value, 'sortBy');
      this.accumulationStatus = _.get(value, 'accumulationStatus', this.accumulationStatus);
      this.dispatch(new SelectScopeProject({projectId: this.selectedProject.projectId}));
      this.loading = true;
      if (this.accumulationStatus === 'Pricing' ) {
        this.dispatch([new LoadScopeCompletenessPricingData(), new PatchScopeOfCompletenessState({overrideAll: false})]).subscribe(() => {}, () => {}, () => {
          this.loading = false;
          this.detectChanges();
        });
      } else if (this.accumulationStatus === 'Scope Only') {
        this.dispatch([new LoadScopeCompletenessData(), new PatchScopeOfCompletenessState({overrideAll: false})]).subscribe(() => {}, () => {}, () => {
          this.loading = false;
          this.detectChanges();
        });
      } else if (this.accumulationStatus === 'Pending') {
        this.dispatch([new LoadScopeCompletenessAccumulationInfo()]).subscribe(() => {}, () => {}, () => {
          this.loading = false;
          this.detectChanges();
        });
      }
      this.detectChanges();
    });

    this.plts$.pipe().subscribe(data => {
      this.plts = data;
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
    const dataScope = this.accumulationStatus === 'Pending' ? this.pendingData : this.scopeData;
    console.log(dataScope)
    if(this.sortBy == 'RAP / Minimum Grain') {
      if (_.toArray(this.targetCodes).length < this.scopeData.targetRaps.length) {
        _.forEach(dataScope.targetRaps, item => {
          this.targetCodes[item.id] = true;
        });
      } else {
        _.forEach(dataScope.targetRaps, item => {
          _.forEach(item.regionPerils, rp => {
            this.targetInnerCodes[rp.id + item.id] = true;
          })
        });
      }
    } else {
      if (_.toArray(this.regionCodes).length < this.scopeData.regionPerils.length) {
        _.forEach(dataScope.regionPerils, item => {
          this.regionCodes[item.id] = true;
        });
      } else {
        _.forEach(dataScope.regionPerils, item => {
          _.forEach(item.targetRaps, tr => {
            this.regionInnerCodes[tr.id + item.id] = true;
          })
        });
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

  checkRowAttached(rowData, row) {
    let checked = true;
    let holder = [];
    _.forEach(this.scopeContext, (item, index) => {
      const column = _.find(this.columns, col => col.columnContext === item.id);
      if (_.includes(row.scopeContext, column.columnContext)) {
        holder.push(this.checkChildExpected(row, rowData, column, index + 1));
      }
    });
    _.forEach(holder, item => { if (item !== 'attached' && item !== 'overridden') {
      checked = false;
    }});
    return checked;
  }

  checkColumnAttached(column, index) {
    const dataSource = this.accumulationStatus === 'Pending' ? this.pendingData : this.scopeData;
    let checked = true;
    let overriddenOnly = true;
    let holder = [];
    if (this.sortBy === 'Minimum Grain / RAP') {
      _.forEach(dataSource.regionPerils, rp => {
        holder.push(this.checkExpected(rp, column, index));
      })
    } else {
      _.forEach(dataSource.targetRaps, tr => {
        holder.push(this.checkExpected(tr, column, index));
      })
    }
    holder = _.compact(holder);
    _.forEach(holder, item => { if (item !== 'overridden') {
      overriddenOnly = false;
    }});
    _.forEach(holder, item => { if (item !== 'attached' && item !== 'overridden') {
      checked = false;
    }});
    return checked && !overriddenOnly;
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
          holder.push(this.checkChildExpected(tr, rowData, column, colHeader));
        });
      }
      if (this.sortBy === 'RAP / Minimum Grain') {
        rowData.regionPerils.forEach(rg => {
          holder.push(this.checkChildExpected(rg, rowData, column, colHeader));
        });
      }
      if (_.includes(holder, 'checked')) {
        checked = 'checked';
      } else if (_.includes(holder, 'dispoWs')) {
        checked = 'dispoWs';
      }  else if (_.includes(holder, 'attached')) {
        checked = 'attached';
      } else if (_.includes(holder, 'overridden')) {
        checked = 'overridden';
      }

      return checked;
    } else {
      return null;
    }
  }

  checkChildExpected(rowData, row, column, colHeader) {
    const override = _.get(rowData, `override.${colHeader}.overridden`, false);
    const attached = _.toArray(rowData.pltsAttached);
    const possibleAttachPLT = this.checkAttachingPLT(rowData, row, column);
    let attachedPLT = false;
    _.forEach(attached, item => {
      if (_.includes(item.scopeIndex, colHeader)) {
        attachedPLT = true;
      }
    });
    if (_.includes(rowData.scopeContext, column.columnContext)) {
      if (override) {
        return 'overridden';
      } else if (attachedPLT) {
        return 'attached';
      } else if (possibleAttachPLT) {
        return 'dispoWs';
      } else {
        return 'checked';
      }
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
      new PatchScopeOfCompletenessState({overrideAll: false, overrideRow: false})]);
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

  checkAttachingPLT(item, scopeData, col) {
    let attachable = false;
    const scope = scopeData.id;
    if (_.includes(item.scopeContext, col.columnContext)) {
      _.forEach(this.plts, plt =>  {
        if (this.sortBy === 'Minimum Grain / RAP') {
          if (item.id === plt.accumulationRapCode && scope === plt.regionPerilCode) {
            attachable = true;
          }
        } else {
          if (item.id === plt.regionPerilCode && scope === plt.accumulationRapCode) {
            attachable = true;
          }
        }
      });
      return attachable;
    } else {
      return false
    }
  }

  visibleIcons(rowData, row, scope, i) {
    const overrideActive = this.overrideAll || (this.rowOverrideInit[rowData.id] && this.overrideRows);
    const removeOverrideActive = this.removeOverrideAccess(rowData, row);
    return !overrideActive && !removeOverrideActive;
  }

  filterData() {
    const scopeOfData = this.accumulationStatus === 'Pending' ? this.pendingData : this.scopeData;
    const draftData = this.sortBy == 'RAP / Minimum Grain' ? _.get(scopeOfData, 'targetRaps', []) : _.get(scopeOfData , 'regionPerils', []);
    if (this.filterBy === 'All') {
      return draftData;
    } else if (this.filterBy === 'Incomplete Only') {
      return _.compact(_.map(draftData, item => {
        let filteredData = [];
        if (this.sortBy === 'Minimum Grain / RAP') {
          _.forEach(item.targetRaps, tr => {
            if (!this.checkRowAttached(item, tr)) {
              filteredData = [...filteredData, tr];
            }
          });
        } else {
          _.forEach(item.regionPerils, rp => {
            if (!this.checkRowAttached(item, rp)) {
              filteredData = [...filteredData, rp];
            }
          });
        }
        if (filteredData.length === 0) {
          return null;
        } else {
          return this.sortBy === 'Minimum Grain / RAP' ? {...item, targetRaps: filteredData} : {...item, regionPerils: filteredData};
        }
      }));
    } else if (this.filterBy === 'Overridden Only') {
      return _.compact(_.map(draftData, item => {
        let filteredData = [];
        if (this.sortBy === 'Minimum Grain / RAP') {
          _.forEach(item.targetRaps, tr => {
            if (_.toArray(tr.override).length > 0) {
              filteredData = [...filteredData, {...tr, pltsAttached: []}];
            }
          });
        } else {
          _.forEach(item.regionPerils, tr => {
            if (_.toArray(tr.override).length > 0) {
              filteredData = [...filteredData, {...tr, pltsAttached: []}];
            }
          });
        }
        if (filteredData.length === 0) {
          return null;
        } else {
          return this.sortBy === 'Minimum Grain / RAP' ? {...item, targetRaps: filteredData} : {...item, regionPerils: filteredData}
        }
      }));
    } else if (this.filterBy === 'Synthetic PLTs Only') {
      return draftData;
    } else if (this.filterBy === 'Expected Only') {
      return _.filter(draftData, item => {
        let filtered = true;
        if (this.sortBy === 'Minimum Grain / RAP') {
          _.forEach(item.targetRaps, tr => {
            if (_.includes(_.values(tr.expected), false)) {
              filtered = false;
            }
          });
        } else {
          _.forEach(item.regionPerils, tr => {
            if (_.includes(_.values(tr.expected), false)) {
              filtered = false;
            }
          });
        }
        return filtered;
      });
    } else if (this.filterBy === 'Unexpected Only') {
      return _.filter(draftData, item => {
        let filtered = false;
        if (this.sortBy === 'Minimum Grain / RAP') {
          _.forEach(item.targetRaps, tr => {
            if (_.includes(_.values(tr.expected), false)) {
              filtered = true;
            }
          });
        } else {
          _.forEach(item.regionPerils, tr => {
            if (_.includes(_.values(tr.expected), false)) {
              filtered = true;
            }
          });
        }
        return filtered;
      });
    }
  }

  filterColumn(column, index) {
    if (column.type === 'contractCol') {
      if (this.filterBy === 'Incomplete Only') {
        return !this.checkColumnAttached(column, index);
      } else if(this.filterBy === 'Overridden Only') {
        let removeCol = false;
        if (this.sortBy === 'Minimum Grain / RAP') {
          _.forEach(this.pendingData.regionPerils, rp => {
            _.forEach(rp.targetRaps, tr => {
              if (_.get(tr, `override.${index}.overridden`, false)) {
                removeCol = true;
              }
            });
          })
        } else {
          _.forEach(this.pendingData.targetRaps, tr => {
            _.forEach(tr.regionPerils, rp => {
              if (_.get(rp, `override.${index}.overridden`, false)) {
                removeCol = true;
              }
            });
          })
        }
        return removeCol;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  checkUnexpected(rowData, col) {
    let unexpected = false;
    if (this.sortBy === 'Minimum Grain / RAP') {
      _.forEach(rowData.targetRaps, item => {
        if (_.includes(item.scopeContext, col.columnContext)) {
          if (!item.expected[col.columnContext]) {
            unexpected = true;
          }
        }
      })
    } else {
      _.forEach(rowData.regionPerils, item => {
        if (_.includes(item.scopeContext, col.columnContext)) {
          if (!item.expected[col.columnContext]) {
            unexpected = true;
          }
        }
      })
    }
    return unexpected;
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
