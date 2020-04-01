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
import {LoadScopeCompletenessDataSuccess, PatchScopeOfCompletenessState, SetCurrentTab} from "../../store/actions";

@Component({
  selector: 'app-workspace-scope-completence',
  templateUrl: './workspace-scope-completence.component.html',
  styleUrls: ['./workspace-scope-completence.component.scss']
})
export class WorkspaceScopeCompletenceComponent extends BaseContainer implements OnInit, StateSubscriber {
  check = true;

  @ViewChild('pTable') pTable: any;

  overriddenRowAccess = false;

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
  selectionForOverride = [];
  filterBy: string = 'All';
  selectionForCancelOverride = [];

  showPendingOption: boolean = false;
  accumulationStatus: string = 'Scope Only';
  overrideReasonExplained: string = '';
  attachArray: any;
  deleteArray: any;
  removeOverride: boolean = false;
  treatySectionContainer: any;

  selectedDropDown: any;
  dataSource: any;
  workspaceId: any;
  keys: any = {};
  uwy: any;
  grains = {};
  regionCodes = {};
  selectedSortBy: string = 'Minimum Grain / RAP';

  treatySections: any;

  workspaceInfo: any;
  serviceSubscription: any;

  @Select(WorkspaceState.getScopeCompletenessData) state$;
  state: any;

  @Select(WorkspaceState.getWorkspaces) ws$;
  ws: any;

  @Select(WorkspaceState.getCurrentWorkspaces) wsIndept$;
  wsIndept: any;

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

  wsType: any;

  constructor(private route: ActivatedRoute, private actions$: Actions, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }


  ngOnInit() {
    this.treatySections = _.toArray(trestySections);
    this.dispatch(new LoadScopeCompletenessDataSuccess());

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

    this.wsIndept$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.wsIndept = _.merge({}, value);
      this.wsStatus = this.wsIndept.workspaceType;
    });

    this.selectedProject$.pipe().subscribe(value => {
      this.tabStatus = _.get(value, 'projectType', null);
      this.selectedProject = value;
      if (this.tabStatus === 'treaty' || this.tabStatus === null) {
        this.dataSource = this.getData(this.treatySections[0]);
        this.treatySectionContainer = _.cloneDeep(this.treatySections[0]);
      } else {
        if (this.checkImport(this.selectedProject)) {
          const facData = this.formatData(this.treatySections[1]);
          this.dataSource = this.getData(facData);
          this.treatySectionContainer = _.cloneDeep(facData);
        } else {
          this.dataSource = this.getData(this.treatySections[0]);
          this.treatySectionContainer = _.cloneDeep(this.treatySections[0]);
        }
      }
    });

    this.ws$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.ws = _.merge({}, value);
      this.detectChanges();
    });

    this.scopeContext$.pipe().subscribe(value => {
      this.selectedSortBy = _.get(value, 'sortBy');
      this.accumulationStatus = _.get(value, 'accumulationStatus');
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
    this.dispatch(new fromWs.PublishToPricingFacProject());
  }

  formatData(data) {
    const divisions = _.get(this.selectedProject, 'division', []);
    let scopeData = [];
    _.forEach(divisions, (item, index) => {
      if (index < data.length) {
        scopeData = [...scopeData, data[index]];
      } else {
        scopeData = [...scopeData, data[0]];
      }
    });
    return scopeData;
  }

  showPackage() {
    let check = false;
    if (this.treatySections !== undefined) {
      if (_.differenceWith(this.treatySectionContainer, this.treatySections[0], _.isEqual).length !== 0) {
        this.showPendingOption = true;
        check = !this.showCancelOverrideButton() && !this.showOverrideButton();
      } else {
        check = false;
        this.showPendingOption = false;
      }
      check ? this.accumulationStatus = 'Pending' : this.accumulationStatus = 'Scope Only';
    }
    return check;
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

  getData(treatySections) {
    const res = [];
    const treatySectionsClone = _.cloneDeep(treatySections);

    _.forEach(treatySectionsClone, treatySection => {
      _.forEach(treatySection.regionPerils, regionPeril => {
        const object = {
          id: regionPeril.id,
          description: regionPeril.description,
          override: false,
          selected: false,
          child: _.sortBy(this._mergeFunction((_.find(res, item => item.id == regionPeril.id) || {child: []}).child, regionPeril.targetRaps), item => item.id)
        };
        const index = _.findIndex(res, row => row.id == object.id);
        index === -1 ? res.push(object) : res[index] = object;
      });
    });
    return _.sortBy(_.cloneDeep(res), item => item.id);
  }

  getDataTwo(treatySections) {
    const res = [];
    const treatySectionsClone = _.cloneDeep(treatySections);

    _.forEach(treatySectionsClone, treatySection => {
      _.forEach(treatySection.targetRaps, targetRap => {
        const object = {
          id: targetRap.id,
          description: targetRap.description,
          override: false,
          selected: false,
          child: _.sortBy(this._mergeFunctionTwo((_.find(res, item => item.id == targetRap.id) || {child: []}).child, targetRap.regionPerils), item => item.id)
        };
        const index = _.findIndex(res, row => row.id === object.id);
        index === -1 ? res.push(object) : res[index] = object;
      });
    });
    return _.sortBy(_.cloneDeep(res), item => item.id);

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

  /** get the icon depending on the RP**/
  perilZone(peril) {
    if (peril === 'YY' || peril === 'EQ') {
      return {peril: 'EQ', color: '#E70010'};
    }
    if (peril === 'WS') {
      return {peril: 'WS', color: '#7BBE31'};
    }
    if (peril === 'FL') {
      return {peril: 'FL', color: '#008694'};
    }
    if (peril === 'CS') {
      return {peril: 'CS', color: '#62ec07'};
    }
  }

  /** expending the parent**/
  toggleExpanded(item: any, rowData: any) {
    this.grains[item.id + rowData.id] = !this.grains[item.id + rowData.id];
  }

  getGrainState(grain, rowData) {
    return this.grains[grain.id + rowData.id];
  }

  expandAllRows() {
    const regions = [];
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
    }
    // }this.grains[item.id + rowData.id]

  }

  closeAllRows() {
    let check = false;
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
    }
  }

  /** expending the child**/
  toggleParentExpand(item: any) {
    this.regionCodes[item.id] = !this.regionCodes[item.id];
  }

  overrideRow(rowData) {
    rowData.override = true;
    if (this.selectedSortBy === 'Minimum Grain / RAP') {
      let holder = {};
      this.treatySections[0].forEach(ts => {
        ts.regionPerils.forEach(rp => {
          if (rp.id === rowData.id) {
            rp.targetRaps.forEach(tr => {
              if (!tr.attached && !tr.overridden) {
                holder = {
                  "treatySection": ts.id,
                  "parent": rowData.id,
                  "child": tr.id
                };
                this.selectionForOverride.push(holder);
              }
            });
          }
        });
      });
    }

    if (this.selectedSortBy === 'RAP / Minimum Grain') {
      let holder = {};
      this.treatySections[0].forEach(ts => {
        ts.targetRaps.forEach(rp => {
          if (rp.id === rowData.id) {
            rp.regionPerils.forEach(tr => {
              if (!tr.attached && !tr.overridden) {
                holder = {
                  "treatySection": ts.id,
                  "parent": tr.id,
                  "child": rowData.id
                };
                this.selectionForOverride.push(holder);
              }
            });
          }
        });
      });
    }
  }

  /**sorting the data either by RP/TR or TR/RP**/
  changeSortBy(item) {
    this.selectedSortBy = item;
    this.pTable.expandedRowKeys = {};
    this.grains = {};
    this.cancelOverride();
    this.cancelRemoveOverride();
    this.regionCodes = {};
    if (item === 'Minimum Grain / RAP') {
      this.dataSource = this.getData(this.treatySections[0]);
      this.dataSource.forEach(res => {
        res.override = false;
      });
    }
    if (item === 'RAP / Minimum Grain') {
      this.dataSource = this.getDataTwo(this.treatySections[0]);
      this.dataSource.forEach(res => {
        res.override = false;
      });
    }
  }

  /** stocking the override reason for a that's gonna be shown later on hovering the override icon**/
  getReasonOverride(item, grain, rowData) {
    let rr = '';
    let rd = '';
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      item.regionPerils.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.targetRaps.forEach(des => {
              if (des.id == grain.id) {
                if (des.overridden) {
                  rr = des.reason;
                  rd = des.resonDescribed;
                }
              }
            });
          }
        }
      );
    }


    if (this.selectedSortBy === 'RAP / Minimum Grain') {
      item.targetRaps.forEach(reg => {
        if (reg.id === rowData.id) {
          reg.regionPerils.forEach(des => {
              if (des.id === grain.id) {
                if (des.overridden) {
                  rr = des.reason;
                  rd = des.resonDescribed;
                }
              }
            }
          );
        }
      });
    }

    return {rr, rd};
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

  /**go in the child override mode selecting all checkboxes beforeHand**/
  overrideChildMode(rowData, grain) {
    grain.override = true;
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      let holder = {};
      this.treatySections[0].forEach(ts => {
        ts.regionPerils.forEach(rp => {
          if (rp.id == rowData.id) {
            rp.targetRaps.forEach(tr => {
              if (!tr.attached && !tr.overridden) {
                holder = {
                  "treatySection": ts.id,
                  "parent": rowData.id,
                  "child": grain.id
                };
                this.selectionForOverride.push(holder);
              }
            });
          }
        });
      });
    }

    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      let holder = {};
      this.treatySections[0].forEach(ts => {
        ts.targetRaps.forEach(rp => {
          if (rp.id == rowData.id) {
            rp.regionPerils.forEach(tr => {
              if (!tr.attached && !tr.overridden) {
                holder = {
                  "treatySection": ts.id,
                  "parent": grain.id,
                  "child": rowData.id
                };
                this.selectionForOverride.push(holder);
              }
            });
          }
        });
      });
    }


  }

  /** adding or deleting the override selection from the override container with checking/unchecking the checkbox **/
  overrideSelectionTwo(item, rowData, grain) {

    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      let holder = {
        "treatySection": item.id,
        "parent": rowData.id,
        "child": grain.id
      };


      if (_.findIndex(this.selectionForOverride, holder) == -1) {
        this.selectionForOverride.push(holder);
      } else {

        this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);

      }
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      let holder = {
        "treatySection": item.id,
        "parent": grain.id,
        "child": rowData.id
      };


      if (_.findIndex(this.selectionForOverride, holder) == -1) {
        this.selectionForOverride.push(holder);
      } else {

        this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);

      }

    }

  }

  /** 3 state checkbox (deleting or adding the override) for the parent depending on whether all the children are selected/partl selected/none selected**/
  overrideSelection(item, rowData) {
    if (this.selectedSortBy == 'Minimum Grain / RAP') {

      const count = _.filter(this.selectionForOverride, res => res.parent == rowData.id && res.treatySection == item.id).length;

      let const1 = 0;
      rowData.child.forEach(chi => {
        item.regionPerils.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.targetRaps.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && !des.overridden) {
                  const1++;
                }
              }
            });
          }
        });
      });

      rowData.child.forEach(chi => {
        item.regionPerils.forEach(reg => {
          if (reg.id === rowData.id) {
            reg.targetRaps.forEach(des => {
              if (des.id === chi.id) {
                if (!des.attached && !des.overridden) {
                  const holder = {
                    "treatySection": item.id,
                    "parent": rowData.id,
                    "child": chi.id
                  };
                  if (count === const1) {
                    this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);
                  } else {
                    if (_.findIndex(this.selectionForOverride, holder) === -1) {
                      this.selectionForOverride.push(holder);
                    }
                  }
                }
              }
            });
          }
        });
      });
    }
    if (this.selectedSortBy === 'RAP / Minimum Grain') {
      const count = _.filter(this.selectionForOverride, res => res.child == rowData.id && res.treatySection == item.id).length;

      let const1 = 0;
      rowData.child.forEach(chi => {
        item.targetRaps.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.regionPerils.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && !des.overridden) {
                  const1++;
                }
              }
            });
          }
        });
      });

      rowData.child.forEach(chi => {
        item.targetRaps.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.regionPerils.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && !des.overridden) {
                  let holder = {
                    "treatySection": item.id,
                    "parent": chi.id,
                    "child": rowData.id
                  };
                  if (count == const1) {
                    this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);
                  } else {
                    if (_.findIndex(this.selectionForOverride, holder) == -1) {
                      this.selectionForOverride.push(holder);
                    }
                  }
                }
              }
            });
          }
        });
      });
    }
  }

  overrideSelectionForCancel(item, rowData) {
    if (this.selectedSortBy == 'Minimum Grain / RAP') {

      const count = _.filter(this.selectionForOverride, res => res.parent == rowData.id && res.treatySection == item.id).length;

      let const1 = 0;
      rowData.child.forEach(chi => {
        item.regionPerils.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.targetRaps.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && des.overridden) {
                  const1++;
                }
              }
            });
          }
        });
      });

      if (count == const1) {

        rowData.child.forEach(chi => {
          item.regionPerils.forEach(reg => {
            if (reg.id == rowData.id) {
              reg.targetRaps.forEach(des => {
                if (des.id == chi.id) {
                  if (!des.attached && des.overridden) {
                    let holder = {
                      "treatySection": item.id,
                      "parent": rowData.id,
                      "child": chi.id
                    };
                    this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);
                  }
                }
              });
            }
          });
        });


      } else {
        rowData.child.forEach(chi => {
          item.regionPerils.forEach(reg => {
            if (reg.id == rowData.id) {
              reg.targetRaps.forEach(des => {
                if (des.id == chi.id) {
                  if (!des.attached && des.overridden) {
                    let Holder = {
                      "treatySection": item.id,
                      "parent": rowData.id,
                      "child": chi.id
                    };
                    if (_.findIndex(this.selectionForOverride, Holder) == -1) {
                      this.selectionForOverride.push(Holder);
                    }
                  }
                }
              });
            }
          });
        });
      }
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      const count = _.filter(this.selectionForOverride, res => res.child == rowData.id && res.treatySection == item.id).length;

      let const1 = 0;
      rowData.child.forEach(chi => {
        item.targetRaps.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.regionPerils.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && des.overridden) {
                  const1++;
                }
              }
            });
          }
        });
      });
      if (count == const1) {

        rowData.child.forEach(chi => {
          item.targetRaps.forEach(reg => {
            if (reg.id == rowData.id) {
              reg.regionPerils.forEach(des => {
                if (des.id == chi.id) {
                  if (!des.attached && des.overridden) {
                    let holder = {
                      "treatySection": item.id,
                      "parent": chi.id,
                      "child": rowData.id
                    };
                    this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);
                  }
                }
              });
            }
          });
        });

      } else {

        rowData.child.forEach(chi => {
          item.targetRaps.forEach(reg => {
            if (reg.id == rowData.id) {
              reg.regionPerils.forEach(des => {
                if (des.id == chi.id) {
                  if (!des.attached && des.overridden) {
                    let Holder = {
                      "treatySection": item.id,
                      "parent": chi.id,
                      "child": rowData.id
                    };
                    if (_.findIndex(this.selectionForOverride, Holder) == -1) {
                      this.selectionForOverride.push(Holder);
                    }
                  }
                }
              });
            }
          });
        });
      }
    }


  }

  /** checking the parent's checkbox when all the children are selected**/
  checkParent(rowData, item) {
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      let const1 = 0;
      const count = _.filter(this.selectionForOverride, res => res.parent === rowData.id && res.treatySection === item.id).length;
      rowData.child.forEach(chi => {
        item.regionPerils.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.targetRaps.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && !des.overridden) {
                  const1++;
                }
              }
            });
          }
        });
      });

      if (count == const1) {
        return true;
      }
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      let const1 = 0;

      const count = _.filter(this.selectionForOverride, res => res.child === rowData.id && res.treatySection === item.id).length;
      rowData.child.forEach(chi => {
        item.targetRaps.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.regionPerils.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && !des.overridden) {
                  const1++;
                }
              }
            });
          }
        });
      });
      if (count == const1) {
        return true;
      }
    }
  }

  checkParentForCancel(rowData, item) {
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      let const1 = 0;
      const count = _.filter(this.selectionForOverride, res => res.parent === rowData.id && res.treatySection === item.id).length;
      rowData.child.forEach(chi => {
        item.regionPerils.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.targetRaps.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && des.overridden) {
                  const1++;
                }
              }
            });
          }
        });
      });

      if (count == const1) {
        return true;
      }
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      let const1 = 0;

      const count = _.filter(this.selectionForOverride, res => res.child === rowData.id && res.treatySection === item.id).length;
      rowData.child.forEach(chi => {
        item.targetRaps.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.regionPerils.forEach(des => {
              if (des.id == chi.id) {
                if (!des.attached && des.overridden) {
                  const1++;
                }
              }
            });
          }
        });
      });
      if (count == const1) {
        return true;
      }
    }
  }

  /** checking the override container to determine whether to check the child's checkbox or not**/
  checkChild(rowData, item, grain) {
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      let Holder = {
        "treatySection": item.id,
        "parent": rowData.id,
        "child": grain.id
      };
      return _.findIndex(this.selectionForOverride, Holder) != -1;
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      let Holder = {
        "treatySection": item.id,
        "parent": grain.id,
        "child": rowData.id
      };
      return _.findIndex(this.selectionForOverride, Holder) != -1;
    }


  }

  /**switching to the override mode for the whole table**/
  overrideAll() {
    this.dispatch(new PatchScopeOfCompletenessState({overrideAll: true}));
    this.overrideStatus = true;
    this.dataSource.forEach(res => {
      res.override = true;
    });
  }

  RemoveOverrideAll() {
    this.removeOverride = true;
    let holder = {};
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      this.treatySections[0].forEach(ts => {
        ts.regionPerils.forEach(rp => {
          rp.targetRaps.forEach(tr => {
            if (tr.overridden) {
              holder = {
                "treatySection": ts.id,
                "parent": rp.id,
                "child": tr.id
              };
              this.selectionForOverride.push(holder);
            }
          });
        });
      });
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      this.treatySections[0].forEach(ts => {
        ts.targetRaps.forEach(rp => {
          rp.regionPerils.forEach(tr => {
            if (tr.overridden) {
              holder = {
                "treatySection": ts.id,
                "parent": tr.id,
                "child": rp.id
              };
              this.selectionForOverride.push(holder);
            }
          });
        });
      });
    }

    this.selectionForCancelOverride = _.merge([], this.selectionForOverride);

  }

  checkSelectionToCancelOverride() {
    return this.selectionForCancelOverride.length > this.selectionForOverride.length;
  }

  removeOverrideBack() {
    this.selectionForCancelOverride.forEach(item => {
      if (_.findIndex(this.selectionForOverride, item) == -1) {
        this.treatySections[0].forEach(section => {

          if (item.treatySection == section.id) {
            section.regionPerils.forEach(rp => {
              if (rp.id == item.parent) {
                rp.targetRaps.forEach(tr => {
                  if (tr.id == item.child) {
                    tr.overridden = false;
                    tr.reason = null;
                    tr.resonDescribed = null;
                  }
                });
              }
            });
            section.targetRaps.forEach(tr => {
              if (tr.id == item.child) {
                tr.regionPerils.forEach(rp => {
                  if (rp.id == item.parent) {
                    rp.overridden = false;
                    rp.reason = null;
                    rp.resonDescribed = null;
                  }
                });
              }
            });
          }
        });
      }
    });
    this.selectionForCancelOverride = [];
    this.selectionForOverride = [];
    this.removeOverride = false;
    this.detectChanges();

  }

  /**cancelling the override / emptying the override container**/
  cancelOverride() {
    this.dispatch(new PatchScopeOfCompletenessState({overrideAll: false, overrideRow: false}));
  }

  cancelRemoveOverride() {
    this.dispatch(new PatchScopeOfCompletenessState({overrideCancelAll: false, overrideCancelRow: false}));
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

  private _mergeFunction(source, target) {
    let newData = [...source];

    target.forEach(tr => {
      const index = _.findIndex(newData, item => item.id == tr.id);
      if (index != -1) {
        newData[index].pltsAttached = _.uniqBy([...(tr.pltsAttached), ...(newData[index].pltsAttached)], (item: any) => item.pltId);
      } else {
        newData = [...newData, tr];
      }
    });
    newData = _.map(newData, item => {
      return {...item, override: false}
    });
    _.forEach(newData, item => {
      if (item.pltsAttached.length != 0) {
        item.pltsAttached = _.sortBy(item.pltsAttached, plt => plt.pltName)
      }
    });
    return newData;
  }

  private _mergeFunctionTwo(source, target) {
    let newData = [...source];

    target.forEach(tr => {
      const index = _.findIndex(newData, item => item.id == tr.id);
      if (index != -1) {
        newData[index].pltsAttached = _.uniqBy([...(tr.pltsAttached), ...(newData[index].pltsAttached)], (item: any) => item.pltId);
      } else {
        newData = [...newData, tr];
      }
    });
    newData = _.map(newData, item => {
      return {...item, override: false}
    });
    _.forEach(newData, item => {
      if (item.pltsAttached.length != 0) {
        item.pltsAttached = _.sortBy(item.pltsAttached, plt => plt.pltName)
      }
    });
    return newData;
  }

  showIncompleteOnly() {
    this.filterBy = 'Incomplete Only';
  }

  showAll() {
    this.filterBy = 'All';
  }


}
