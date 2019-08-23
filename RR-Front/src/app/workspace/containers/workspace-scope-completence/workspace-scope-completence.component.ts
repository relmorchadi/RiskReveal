import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {combineLatest} from 'rxjs';
import * as _ from 'lodash';
import {dataTable, dataTable2, trestySections} from './data';
import {ActivatedRoute, Router} from '@angular/router';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-workspace-scope-completence',
  templateUrl: './workspace-scope-completence.component.html',
  styleUrls: ['./workspace-scope-completence.component.scss']
})
export class WorkspaceScopeCompletenceComponent extends BaseContainer implements OnInit, StateSubscriber {
  check = true;

  @ViewChild('pTable') pTable: any;

  wsIdentifier;
  addRemoveModal: boolean = false;
  showOverrideModal: boolean = false;
  listOfPltsData = {};
  selectionForOverride = [];
  overrideReason: string;
  overrideReasonExplained: string = '';

  dataSource: any;
  workspaceId: any;
  uwy: any;
  grains = {}
  regionCodes = {};
  selectedSortBy: string = 'Minimum Grain / RAP';

  treatySections: any;

  workspaceInfo: any;
  serviceSubscription: any;
  @Select(WorkspaceState.getScopeCompletenessData) state$;
  state: any;

  workspace: any;
  index: any;
  workspaceUrl: any;

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }


  ngOnInit() {
    this.observeRouteParams().pipe(
      this.unsubscribeOnDestroy
    ).subscribe(() => {
      this.dispatch(new fromWs.LoadScopeCompletenessDataSuccess({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));
    });

    this.state$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.state = value;
        this.listOfPltsData = this.getSortedPlts(this.state)


      // this.dataSource = this.dataSource.map((dt, k) => dt = {...dt, id: k});
      this.dataSource = dataTable;
      this.treatySections = _.toArray(trestySections);
      this.detectChanges();
    });

    combineLatest(
      this.route.params
    ).pipe(this.unsubscribeOnDestroy)
      .subscribe((dt: any) => {
        const {wsId, year} = dt[0];
        this.workspaceUrl = {wsId, uwYear: year};
      });
  }

  observeRouteParams() {
    return this.route.params.pipe(tap(({wsId, year}) => {
      this.workspaceId = wsId;
      this.uwy = year;
    }))
  }


  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }


  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);
  }

  getSortedPlts(data, type = 'grain') {
    let SortConfig = {
      grain: ['regionPerilCode', 'grain'],
      regionPerilCode: ['grain', 'regionPerilCode']
    }
    let res = {};
    _.forEach(_.groupBy(data, e => e[SortConfig[type][0]]), (e, k) => res[k] = _.groupBy(e, dt => dt[SortConfig[type][1]]));
    return res;
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ]);
  }

  sortByRegionPeril() {
  }

  perilZone(peril) {
    if (peril === 'YY') {
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

  ngOnDestroy(): void {
    this.destroy();
  }


  toggleExpanded(item: any) {
    this.grains[item.id] = !this.grains[item.id];
  }

  toggleParentExpand(item: any) {
    this.regionCodes[item.id] = !this.regionCodes[item.id];

  }


  changeSortBy(item) {
    this.selectedSortBy = item;
    this.pTable.expandedRowKeys = {};
    this.grains = {};
    this.regionCodes = {};
    if (item == 'Minimum Grain / RAP') {
      this.dataSource = dataTable
      this.dataSource.forEach(res => {
        res.override = false;
      })
    }
    if (item == 'RAP / Minimum Grain') {
      this.dataSource = dataTable2;
      this.dataSource.forEach(res => {
        res.override = false;
      })

    }


  }

  getReasonOverride(item, grain, rowData) {
    let rr = '';
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      item.regionPerils.forEach(reg => {
          if (reg.id == rowData.id) {
            reg.targetRaps.forEach(des => {
              if (des.id == grain.id) {
                if (des.overridden) {
                  rr = des.reason;
                }

              }
            })
          }
        }
      )
    }


    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      item.targetRaps.forEach(reg => {
        if (reg.id == rowData.id) {
          reg.regionPerils.forEach(des => {
              if (des.id == grain.id) {
                if (des.overridden) {
                  rr = des.reason;
                }
              }
            }
          )
        }

      })
    }

    return rr;
  }

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
                    holder.push('overridden')
                  }
                  if (!res.attached && !res.overridden) {
                    if (this.listOfPltsData[rowData.id][des.id]) {
                      if (this.listOfPltsData[rowData.id][des.id].length) {
                        holder.push('dispoWs');
                      }

                    } else {
                      holder.push('checked');
                    }
                  }
                }
              })

            })

          }
        }
      )
      if (_.includes(holder, 'dispoWs')) {
        checked = 'dispoWs';
      } else if (_.includes(holder, 'checked')) {
        checked = 'checked';
      } else if (_.includes(holder, 'attached')) {
        checked = 'attached';
      } else if (_.includes(holder, 'overridden')) {
        checked = 'overridden';
      }
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      item.targetRaps.forEach(reg => {
          if (reg.id == rowData.id) {

            reg.regionPerils.forEach(res => {
              rowData.child.forEach(des => {
                if (des.id == res.id) {
                  if (res.attached) {
                    holder.push('attached');
                  }
                  if (res.overridden) {
                    holder.push('overridden')
                  }
                  if (!res.attached && !res.overridden) {
                    if (this.listOfPltsData[des.id][rowData.id]) {
                      if (this.listOfPltsData[des.id][rowData.id].length) {
                        holder.push('dispoWs');
                      }

                    } else {
                      holder.push('checked');
                    }
                  }
                }
              })

            })

          }
        }
      )
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
        checked = 'override'
      }
    }
    return checked;
  }

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
                if (this.listOfPltsData[rowData.id][grain.id]) {
                  if (this.listOfPltsData[rowData.id][grain.id].length) {
                    checked = 'dispoWs';
                  }

                } else {
                  checked = 'checked'
                }
              }
            }
          })

        }
      })
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
                if (this.listOfPltsData[grain.id][rowData.id]) {
                  if (this.listOfPltsData[grain.id][rowData.id].length) {

                    checked = 'dispoWs';
                  }

                } else {
                  checked = 'checked'
                }
              }
            }
          })
        }
      })
    }

    if (rowData.override) {
      if (checked == 'checked' || checked == 'dispoWs') {
        checked = 'override'
      }
    }
    return checked;
  }

  checkAttached(item, rowData, grain, plt) {
    let checked = 'not';

    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      item.regionPerils.forEach(reg => {
        if (reg.id == rowData.id) {
          reg.targetRaps.forEach(des => {
            if (des.id == grain.id) {
              if (des.attached) {
                des.pltsAttached.forEach(plts => {
                  if (plts.id == plt.id) {
                    checked = "attached";
                  }
                })

              }
            }
          })

        }
      })
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      item.targetRaps.forEach(reg => {
        if (reg.id == rowData.id) {
          reg.regionPerils.forEach(des => {
            if (des.id == grain.id) {
              if (des.attached) {
                des.pltsAttached.forEach(plts => {
                  if (plts.id == plt.id) {
                    checked = "attached";
                  }
                })

              }

            }
          })
        }
      })
    }
    return checked;
  }

  overrideSelectionTwo(item, rowData, grain) {

    if(this.selectedSortBy == 'Minimum Grain / RAP'){
    let holder = {
      "treatySection": item.id,
      "parent": rowData.id,
      "child": grain.id
    };


    if (_.findIndex(this.selectionForOverride, holder) == -1) {
      this.selectionForOverride.push(holder);
    } else {

      this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);

    }}
  if(this.selectedSortBy == 'RAP / Minimum Grain'){
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

  overrideSelection(item, rowData) {
    const count = _.filter(this.selectionForOverride, res => res.parent == rowData.id && res.treatySection == item.id).length;
    if (count == rowData.child.length) {
      // this.selectionForOverride = _.pullAllBy([...this.selectionForOverride], [{
      //   "treatySection": item.id,
      //   "parent": rowData.id
      // }],  'treatySection' && 'parent' );
      rowData.child.forEach(res => {
        let holder = {
          "treatySection": item.id,
          "parent": rowData.id,
          "child": res.id
        };
        this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);

      })

    } else {
      rowData.child.forEach(res => {
        let Holder = {
          "treatySection": item.id,
          "parent": rowData.id,
          "child": res.id
        };
        if (_.findIndex(this.selectionForOverride, Holder) == -1) {
          this.selectionForOverride.push(Holder);
        }
      })
    }
  }

  checkParent(rowData, item) {
    const count = _.filter(this.selectionForOverride, res => res.parent === rowData.id && res.treatySection === item.id).length;
    return count === rowData.child.length;
  }

  checkChild(rowData, item, grain) {
    let Holder = {
      "treatySection": item.id,
      "parent": rowData.id,
      "child": grain.id
    };
    return _.findIndex(this.selectionForOverride, Holder) != -1;

  }

  overrideAll() {
    this.dataSource.forEach(res => {
      res.override = true;
    })
  }

  overrideBack() {
    this.selectionForOverride.forEach(ove => {
      this.treatySections[0].forEach(section => {

        if (ove.treatySection == section.id) {
          section.regionPerils.forEach(rp => {
            if (rp.id == ove.parent) {
              rp.targetRaps.forEach(tr => {
                if (tr.id == ove.child) {
                  tr.overridden = true;
                  tr.reason = this.overrideReason + ' ' + this.overrideReasonExplained;

                }
              })
            }
          })
          section.targetRaps.forEach(tr => {
            if (tr.id == ove.child) {
              tr.regionPerils.forEach(rp => {
                if (rp.id == ove.parent) {
                  rp.overridden = true;
                  rp.reason = this.overrideReason + ' ' + this.overrideReasonExplained;

                }
              })
            }

          })
        }
      })
    })

    this.dataSource.forEach(res => {
      res.override = false;
    })

    this.onHide();
    this.selectionForOverride = [];

  }

  cancelOverride() {
    this.selectionForOverride = [];
    this.dataSource.forEach(res => {
      res.override = false;
    })
  }

  showOverrideButton() {
    let checked = false;
    this.dataSource.forEach(res => {
      if (res.override == true) {
        checked = true;
      }
    })
    return checked;
  }

  onHide() {
    this.showOverrideModal = false;
    this.overrideReasonExplained = '';
    this.overrideReason = null;
  }
}
