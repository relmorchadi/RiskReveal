import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {Actions, ofActionDispatched, Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {combineLatest} from 'rxjs';
import * as _ from 'lodash';
import {trestySections} from './data';
import {ActivatedRoute, Router} from '@angular/router';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';
import {tap} from "rxjs/operators";
import {LoadScopeCompletenessData, PatchScopeOfCompletenessState, SetCurrentTab} from "../../store/actions";

@Component({
  selector: 'app-workspace-scope-completence',
  templateUrl: './workspace-scope-completence.component.html',
  styleUrls: ['./workspace-scope-completence.component.scss']
})
export class WorkspaceScopeCompletenceComponent extends BaseContainer implements OnInit, StateSubscriber {
  check = true;

  @ViewChild('pTable') pTable: any;

  overriddenRowAccess = false;
  overriddenCancelRowAccess = false;

  wsIdentifier;
  addRemoveModal: boolean = false;
  showOverrideModal: boolean = false;
  listOfPltsData = {};
  packageInformation = {
    "packageId": "#2335",
    "Created": "16/09/2019",
    "Last Modified": "16/09/2019",
    "Published": "N/A",
    "Status": "Pending",
    "By": "Huw Parry",
    "Note": "Information about why those modifications were applied"
  };

  filterBy: string = 'All';

  showPendingOption: boolean = false;
  accumulationStatus: string = 'Scope Only';
  removeOverride: boolean = false;
  treatySectionContainer: any;

  workspaceId: any;
  keys: any = {};
  uwy: any;
  selectedSortBy: string = 'Minimum Grain / RAP';

  treatySections: any;
  workspaceInfo: any;


  @Select(WorkspaceState.getScopeCompletenessData) state$;
  state: any;
  @Select(WorkspaceState.getWorkspaces) ws$;
  ws: any;
  @Select(WorkspaceState.getCurrentWorkspaces) wsIndept$;
  wsIndept: any;
  @Select(WorkspaceState.getScopeCompletenessPendingData) pendingData$;
  pendingData;

  @Select(WorkspaceState.getSelectedProject) selectedProject$;
  selectedProject: any;
  tabStatus: any;

  @Select(WorkspaceState.getImportStatus) importStatus$;
  importStatus: any;
  @Select(WorkspaceState.getOverrideStatus) overrideStatus$;
  overrideStatus;
  @Select(WorkspaceState.getScopeContext) scopeContext$;

  overrideALL = false;
  overrideRows = false;
  removeOverrideAll = false;
  removeOverrideRow = false;

  wsStatus: any;
  currentWsIdentifier: any;
  workspace: any;
  index: any;
  workspaceUrl: any;
  rowInformation: any;

  constructor(private route: ActivatedRoute, private actions$: Actions, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.treatySections = _.toArray(trestySections);
    this.dispatch(new LoadScopeCompletenessData());

    combineLatest(
        this.route.params
    ).pipe(this.unsubscribeOnDestroy)
        .subscribe((dt: any) => {
          const {wsId, year} = dt[0];
          this.workspaceUrl = {wsId, uwYear: year};
        });

/*    this.dispatch(new fromWs.LoadScopeCompletenessDataSuccess({
      params: {workspaceId: this.workspaceUrl.wsId, uwy: this.workspaceUrl.uwYear},
      wsIdentifier: this.workspaceUrl.wsId + '-' + this.workspaceUrl.uwYear
    }));*/

    this.select(WorkspaceState.getCurrentTab)
      .pipe(this.unsubscribeOnDestroy)
      .subscribe(curTab => {
        this.currentWsIdentifier = curTab.wsIdentifier;
        this.detectChanges();
      });

    this.importStatus$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.importStatus = value;
      this.detectChanges();
    });

    this.state$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.state = _.get(value, 'data', null);
      this.listOfPltsData = this.getSortedPlts(this.state);
      this.detectChanges();
    });

    this.overrideStatus$.pipe().subscribe(value => {
      this.overrideStatus = value;
      this.overrideALL = value.overrideAll;
      this.overrideRows = value.overrideRow;
      this.removeOverrideAll = value.overrideCancelAll;
      this.removeOverrideRow = value.overrideCancelRow;
      this.detectChanges();
    });

    this.pendingData$.pipe().subscribe(value => {
      this.pendingData =  value;
      this.detectChanges();
    });

    this.wsIndept$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.wsIndept = _.merge({}, value);
      this.wsStatus = this.wsIndept.workspaceType;
    });

    this.selectedProject$.pipe().subscribe(value => {
      this.tabStatus = _.get(value, 'projectType', null);
      this.selectedProject = value;
    });

    this.ws$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.ws = _.merge({}, value);
      this.detectChanges();
    });

    this.scopeContext$.pipe().subscribe(value => {
      this.selectedSortBy = _.get(value, 'sortBy');
      this.accumulationStatus = _.get(value, 'accumulationStatus');
      this.filterBy = _.get(value, 'filterBy')
      this.detectChanges();
    });

/*    this.actions$
      .pipe(
        ofActionDispatched(SetCurrentTab)
      ).subscribe(({payload}) => {
      if (payload.wsIdentifier != this.wsIdentifier) this.destroy();
      this.detectChanges();
    });*/
  }

  checkImport(project) {
    return project.carStatus === 'Completed' || project.carStatus === 'Superseded';
  }

  checkNonImport(project) {
    return project.carStatus !== 'Completed' && project.carStatus !== 'Superseded' && project.carStatus !== 'Canceled';
  }

  publishFacToPricing() {
    //this.dispatch(new fromWs.PublishToPricingFacProject());
  }

  observeRouteParams() {
    return this.route.params.pipe(tap(({wsId, year}) => {
      this.workspaceId = wsId;
      this.uwy = year;
    }));
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }

  /** Pin and unpin the workspace **/
  pinWorkspace() {
    this.dispatch([new fromHeader.TogglePinnedWsState({
      "userId": 1,
      "workspaceContextCode": this.workspace.wsId,
      "workspaceUwYear": this.workspace.uwYear
    })]);
  }

  updateScopeStateAccumulation(data) {
    this.accumulationStatus = data;
    this.dispatch(new fromWs.PatchScopeOfCompletenessState({mergedData: {accumulationStatus: data}, scope: 'scopeContext'}))
  }

  updateScopeStateSort(data) {
/*    this.changeSortBy(data);*/
    this.dispatch(new fromWs.PatchScopeOfCompletenessState({mergedData: {sortBy: data}, scope: 'scopeContext'}))
  }

  updateScopeStateFilter(data) {
    this.dispatch(new fromWs.PatchScopeOfCompletenessState({mergedData: {filterBy: data}, scope: 'scopeContext'}))
  }

  unableOverride(event) {
    this.overriddenRowAccess = event;
  }

  unableCancelOverride(event) {
    this.overriddenCancelRowAccess = event;
  }

  /** Get The plts Sorted by the hierarchy chosen**/
  getSortedPlts(data, type = 'grain') {
    let SortConfig = {
      grain: ['regionPerilCode', 'grain'],
      regionPerilCode: ['grain', 'regionPerilCode']
    };
    let res = {};
    _.forEach(_.groupBy(data, e => e[SortConfig[type][0]]), (e, k) => res[k] = _.groupBy(e, dt => dt[SortConfig[type][1]]));
    return res;
  }

  /** get which icon to be shown in the parent depending on the children's icons **/
  checkExpected(item, rowData) {
    let checked = 'not';
    let holder = [];
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      item.regionPerils.forEach(reg => {
        if (reg.id == rowData.id) {

          reg.targetRaps.forEach(res => {
            rowData.child.forEach(des => {
              if (des.id == res.id) {
                if (res.attached) {
                  holder.push('attached');
                }
                if (res.overridden) {
                  holder.push('overridden');
                }
                if (!res.attached && !res.overridden) {
                  // const check = _.get(this.listOfPltsData, `${rowData.id}.${des.id}`, null)
                  // const check = this.listOfPltsData[rowData.id][des.id]
                  // if (check != null) {
                  if (this.listOfPltsData[rowData.id] && this.listOfPltsData[rowData.id][des.id]) {
                    if (this.listOfPltsData[rowData.id][des.id].length) {
                      holder.push('dispoWs');
                    }
                  } else {
                    holder.push('checked');
                  }
                }
              }
            });
          });
        }
      });
    }
    if (this.selectedSortBy === 'RAP / Minimum Grain') {
      item.targetRaps.forEach(reg => {
        if (reg.id === rowData.id) {

          reg.regionPerils.forEach(res => {
            rowData.child.forEach(des => {
              if (des.id === res.id) {
                if (res.attached) {
                  holder.push('attached');
                }
                if (res.overridden) {
                  holder.push('overridden');
                }
                if (!res.attached && !res.overridden) {
                  if (this.listOfPltsData[des.id] && this.listOfPltsData[des.id][rowData.id]) {
                    if (this.listOfPltsData[des.id][rowData.id].length) {
                      holder.push('dispoWs');
                    }
                  } else {
                    holder.push('checked');
                  }
                }
              }
            });
          });
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
    if (checked == 'attached') {
      this.treatySections[0].forEach(ts => {
        if (ts.id == item.id) {
          ts.regionPerils.forEach(rg => {
            if (rg.id == rowData.id) {
              rg.attached = true;
            }
          });
          ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.attached = true;
            }
          });
        }
      });
    } else {
      this.treatySections[0].forEach(ts => {
        if (ts.id == item.id) {
          ts.regionPerils.forEach(rg => {
            if (rg.id == rowData.id) {
              rg.attached = false;
            }
          });
          ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.attached = false;
            }
          });
        }
      });
    }

    if (checked == 'overridden') {
      this.treatySections[0].forEach(ts => {
        if (ts.id == item.id) {
          ts.regionPerils.forEach(rg => {
            if (rg.id == rowData.id) {
              rg.overridden = true;
            }
          });
          ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.overridden = true;
            }
          });
        }
      });
    } else {
      this.treatySections[0].forEach(ts => {
        if (ts.id == item.id) {
          ts.regionPerils.forEach(rg => {
            if (rg.id == rowData.id) {
              rg.overridden = false;
            }
          });
          ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.overridden = false;
            }
          });
        }
      });
    }

    if (checked == 'overridden' && this.removeOverride) {
      checked = 'cancelOverride';
    }
    return checked;
  }

  /** get the child's icon independently of others**/
  checkExpectedTwo(item, grain, rowData) {
    let checked = 'not';

    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      item.regionPerils.forEach(reg => {
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
      item.targetRaps.forEach(reg => {
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

    return checked;
  }

  /** get the plt's icon depending on the treatysections**/
  checkAttached(item, rowData, grain, plt) {
    let checked = 'not';
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      item.regionPerils.forEach(reg => {
        if (reg.id == rowData.id) {
          reg.targetRaps.forEach(des => {
            if (des.id == grain.id) {
              if (des.attached) {
                des.pltsAttached.forEach(plts => {
                  if (plts.pltId == plt.pltId) {
                    checked = "attached";
                  }
                });
              }
            }
          });
        }
      });
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      item.targetRaps.forEach(reg => {
        if (reg.id == rowData.id) {
          reg.regionPerils.forEach(des => {
            if (des.id == grain.id) {
              if (des.attached) {
                des.pltsAttached.forEach(plts => {
                  if (plts.pltId == plt.pltId) {
                    checked = 'attached';
                  }
                });
              }
            }
          });
        }
      });
    }
    return checked;
  }

  /**switching to the override mode for the whole table**/
  overrideAll() {
    this.dispatch(new PatchScopeOfCompletenessState({overrideAll: true}));
    this.overrideStatus = true;
  }

  RemoveOverrideAll() {
      this.dispatch(new PatchScopeOfCompletenessState({overrideCancelAll: true}));
  }

  closeModal(event) {
    this.showOverrideModal = event;
  }

  /**cancelling the override / emptying the override container**/
  cancelOverride() {
    this.dispatch(new PatchScopeOfCompletenessState({overrideAll: false, overrideRow: false}));
  }

  cancelRemoveOverride() {
    this.dispatch(new PatchScopeOfCompletenessState({overrideCancelAll: false, overrideCancelRow: false}));
  }

  removeOverrideStart() {
    this.dispatch(new PatchScopeOfCompletenessState({overrideCancelStart: true}));
  }

  /** showing the override checkbox for the child selected, when the conditions of override are available**/
  rowOverrideShowButton($event) {
    this.overrideRows = $event;
  }

  showOverrideButton() {
    return this.overrideRows || this.overrideALL;
  }

  showCancelOverrideButton() {
    return this.removeOverrideAll || this.removeOverrideRow;
  }
}
