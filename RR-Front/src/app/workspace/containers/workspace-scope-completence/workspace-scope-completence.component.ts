import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {Select, Store} from '@ngxs/store';
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
  listOfPltsForPopUp = [];
  selectionForOverride = [];
  selectionForCancelOverride = [];
  overrideReason: string;
  overrideReasonExplained: string = '';
  attachArray: any;
  deleteArray: any;
  removeOverride: boolean = false;

  selectedDropDown: any;
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
  rowInformation: any;

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
      this.listOfPltsData = this.getSortedPlts(this.state);
      this.treatySections = _.toArray(trestySections);
      this.dataSource = this.getData(this.treatySections[0]);
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

  ngOnDestroy(): void {
    this.destroy();
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }

  /** Pin and unpin the workspace**/
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

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ]);
  }

  getData(treatySections) {
    let res = [];
    let treatySectionsClone = _.cloneDeep(treatySections);

    _.forEach(treatySectionsClone, treatySection => {
      _.forEach(treatySection.regionPerils, regionPeril => {
        let object = {
          id: regionPeril.id,
          description: regionPeril.description,
          override: false,
          selected: false,
          child: this._mergeFunction((_.find(res, item => item.id == regionPeril.id) || {child: []}).child, regionPeril.targetRaps)
        };
        const index = _.findIndex(res, row => row.id == object.id);
        if (index == -1) {
          res.push(object)
        } else {
          res[index] = object;
        }
      })
    });
    return _.cloneDeep(res);
  }

  getDataTwo(treatySections) {
    let res = [];
    let treatySectionsClone = _.cloneDeep(treatySections);

    _.forEach(treatySectionsClone, treatySection => {
      _.forEach(treatySection.targetRaps, targetRap => {
        let object = {
          id: targetRap.id,
          description: targetRap.description,
          override: false,
          selected: false,
          child: this._mergeFunctionTwo((_.find(res, item => item.id == targetRap.id) || {child: []}).child, targetRap.regionPerils)
        };
        const index = _.findIndex(res, row => row.id == object.id);
        if (index == -1) {
          res.push(object)
        } else {
          res[index] = object;
        }
      })
    });
    return _.cloneDeep(res);

  }

  /** Get The plts Sorted by the hierarchy chosen**/
  getSortedPlts(data, type = 'grain') {
    let SortConfig = {
      grain: ['regionPerilCode', 'grain'],
      regionPerilCode: ['grain', 'regionPerilCode']
    }
    let res = {};
    _.forEach(_.groupBy(data, e => e[SortConfig[type][0]]), (e, k) => res[k] = _.groupBy(e, dt => dt[SortConfig[type][1]]));
    return res;
  }

  /** get the icon depending on the RP**/
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

  /** expending the parent**/
  toggleExpanded(item: any, rowData: any) {
    this.grains[item.id + rowData.id] = !this.grains[item.id + rowData.id];
  }

  getGrainState(grain, rowData) {
    return this.grains[grain.id + rowData.id];
  }

  /** expending the child**/
  toggleParentExpand(item: any) {
    this.regionCodes[item.id] = !this.regionCodes[item.id];

  }

  /**sorting the data either by RP/TR or TR/RP**/
  changeSortBy(item) {
    this.selectedSortBy = item;
    this.pTable.expandedRowKeys = {};
    this.grains = {};
    this.cancelOverride();
    this.cancelRemoveOverride();
    this.regionCodes = {};
    if (item == 'Minimum Grain / RAP') {
      this.dataSource = this.getData(this.treatySections[0]);
      this.dataSource.forEach(res => {
        res.override = false;
      })
    }
    if (item == 'RAP / Minimum Grain') {
      this.dataSource = this.getDataTwo(this.treatySections[0]);
      this.dataSource.forEach(res => {
        res.override = false;
      })

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
                  rd = des.resonDescribed
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
                  rd = des.resonDescribed
                }
              }
            }
          )
        }

      })
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
                    holder.push('overridden')
                  }
                  if (!res.attached && !res.overridden) {
                    const check = _.get(this.listOfPltsData, `${rowData.id}.${des.id}`, null)
                    if (check != null) {
                      if (this.listOfPltsData[rowData.id][des.id]) {
                        if (this.listOfPltsData[rowData.id][des.id].length) {
                          holder.push('dispoWs');
                        }

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
                    const check = _.get(this.listOfPltsData, `${des.id}.${rowData.id}`, null);
                    if (check != null) {
                      if (this.listOfPltsData[des.id][rowData.id]) {
                        if (this.listOfPltsData[des.id][rowData.id].length) {
                          holder.push('dispoWs');
                        }

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
    console.log("this is the listOfplts:",this.listOfPltsData);

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
    if (checked == 'attached') {
      this.treatySections[0].forEach(ts => {
        if (ts.id == item.id) {
          ts.regionPerils.forEach(rg => {
            if (rg.id == rowData.id) {
              rg.attached = true;
            }
          })
          ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.attached = true;
            }
          })
        }
      })


    } else {
      this.treatySections[0].forEach(ts => {
        if (ts.id == item.id) {
          ts.regionPerils.forEach(rg => {
            if (rg.id == rowData.id) {
              rg.attached = false;
            }
          })
          ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.attached = false;
            }
          })
        }
      })
    }

    if (checked == 'overridden') {
      this.treatySections[0].forEach(ts => {
        if (ts.id == item.id) {
          ts.regionPerils.forEach(rg => {
            if (rg.id == rowData.id) {
              rg.overridden = true;
            }
          })
          ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.overridden = true;
            }
          })
        }
      })


    } else {
      this.treatySections[0].forEach(ts => {
        if (ts.id == item.id) {
          ts.regionPerils.forEach(rg => {
            if (rg.id == rowData.id) {
              rg.overridden = false;
            }
          })
          ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.overridden = false;
            }
          })
        }
      })
    }

    if (checked == 'overridden' && this.removeOverride) {
      checked = 'cancelOverride'
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
                const check = _.get(this.listOfPltsData, `${grain.id}.${rowData.id}`, null);
                if (check != null) {
                  if (this.listOfPltsData[grain.id][rowData.id]) {
                    if (this.listOfPltsData[grain.id][rowData.id].length) {

                      checked = 'dispoWs';
                    }

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

    if (rowData.override || grain.override) {
      if (checked == 'checked' || checked == 'dispoWs') {
        checked = 'override'
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
                  if (plts.pltId == plt.pltId) {
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

  /**go in the child override mode selecting all checkboxes beforeHand**/
  overrideChildMode(rowData, grain) {
    grain.override = true;
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      let holder = {}
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
            })
          }
        })
      })
    }

    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      let holder = {}
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
            })
          }
        })
      })
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
            })
          }
        })
      })

      if (count == const1) {

        rowData.child.forEach(chi => {
          item.regionPerils.forEach(reg => {
            if (reg.id == rowData.id) {
              reg.targetRaps.forEach(des => {
                if (des.id == chi.id) {
                  if (!des.attached && !des.overridden) {
                    let holder = {
                      "treatySection": item.id,
                      "parent": rowData.id,
                      "child": chi.id
                    };
                    this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);
                  }
                }
              })
            }
          })
        })


      } else {
        rowData.child.forEach(chi => {
          item.regionPerils.forEach(reg => {
            if (reg.id == rowData.id) {
              reg.targetRaps.forEach(des => {
                if (des.id == chi.id) {
                  if (!des.attached && !des.overridden) {
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
              })
            }
          })
        })
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
                if (!des.attached && !des.overridden) {
                  const1++;
                }
              }
            })
          }
        })
      })
      if (count == const1) {

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
                    this.selectionForOverride.splice(_.findIndex(this.selectionForOverride, holder), 1);
                  }
                }
              })
            }
          })
        })

      } else {

        rowData.child.forEach(chi => {
          item.targetRaps.forEach(reg => {
            if (reg.id == rowData.id) {
              reg.regionPerils.forEach(des => {
                if (des.id == chi.id) {
                  if (!des.attached && !des.overridden) {
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
              })
            }
          })
        })
      }
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
            })
          }
        })
      })

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
              })
            }
          })
        })


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
              })
            }
          })
        })
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
            })
          }
        })
      })
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
              })
            }
          })
        })

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
              })
            }
          })
        })
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
            })
          }
        })
      })

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
            })
          }
        })
      })
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
            })
          }
        })
      })

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
            })
          }
        })
      })
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
    this.dataSource.forEach(res => {
      res.override = true;
    })
  }

  RemoveOverrideAll() {
    this.removeOverride = true;
    let holder = {}
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
          })
        })
      })
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
          })
        })
      })
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
                })
              }
            })
            section.targetRaps.forEach(tr => {
              if (tr.id == item.child) {
                tr.regionPerils.forEach(rp => {
                  if (rp.id == item.parent) {
                    rp.overridden = false;
                    rp.reason = null;
                    rp.resonDescribed = null;

                  }
                })
              }

            })
          }
        })
      }
    })
    this.selectionForCancelOverride = [];
    this.selectionForOverride = []
    this.removeOverride = false;

  }

  /**Saving the overrides made (will be sending a request to the backend in the future)**/
  overrideBack() {
    this.selectionForOverride.forEach(ove => {
      this.treatySections[0].forEach(section => {

        if (ove.treatySection == section.id) {
          section.regionPerils.forEach(rp => {
            if (rp.id == ove.parent) {
              rp.targetRaps.forEach(tr => {
                if (tr.id == ove.child) {
                  tr.overridden = true;
                  tr.reason = this.overrideReason;
                  tr.resonDescribed = this.overrideReasonExplained;

                }
              })
            }
          })
          section.targetRaps.forEach(tr => {
            if (tr.id == ove.child) {
              tr.regionPerils.forEach(rp => {
                if (rp.id == ove.parent) {
                  rp.overridden = true;
                  rp.reason = this.overrideReason;
                  rp.resonDescribed = this.overrideReasonExplained;

                }
              })
            }

          })
        }
      })
    })

    this.cancelOverride();

    this.onHide();

  }

  /**cancelling the override / emptying the override container**/
  cancelOverride() {
    this.selectionForOverride = [];
    this.dataSource.forEach(res => {
      res.override = false;
      res.child.forEach(child => {
        child.override = false;
      })
    })

  }

  cancelRemoveOverride() {
    this.selectionForOverride = [];
    this.removeOverride = false;
  }

  /** showing the override checkbox for the child selected, when the conditions of override are available**/
  showOverrideButton() {
    let checked = false;
    this.dataSource.forEach(res => {
      if (res.override == true) {
        checked = true;
      }
      res.child.forEach(child => {
        if (child.override) {
          checked = true;
        }
      })
    })
    return checked;
  }

  showRemoveOverrideButton() {
    return this.removeOverride;
  }

  /** hiding the popup**/
  onHide() {
    this.showOverrideModal = false;
    this.overrideReasonExplained = '';
    this.overrideReason = null;
  }

  applyAttachChangesTwo(event) {
    this.deleteArray = event;
    _.forEach(this.deleteArray, att => {
        _.forEach(this.treatySections[0], ts => {
          if (ts.id == att.tsId) {


            _.forEach(ts.regionPerils, rg => {
              if (rg.id == att.regionPeril) {
                _.forEach(rg.targetRaps, tr => {
                  if (tr.id == att.targetRap) {
                    tr.pltsAttached.splice(_.findIndex(tr.pltsAttached, this.state[att.pltId]), 1);
                    if (tr.pltsAttached.length == 0) {
                      tr.attached = false;
                    }
                  }
                })
              }
            })
          }


          _.forEach(ts.targetRaps, rg => {
            if (rg.id == att.targetRap) {
              _.forEach(rg.regionPerils, tr => {
                if (tr.id == att.regionPeril) {
                  tr.pltsAttached.splice(_.findIndex(tr.pltsAttached, this.state[att.pltId]), 1);
                  if (tr.pltsAttached.length == 0) {
                    tr.attached = false;
                  }
                }
              })
            }
          })

        })
      }
    )

    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      this.dataSource = this.getDataTwo(this.treatySections[0]);
    }
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      this.dataSource = this.getData(this.treatySections[0]);
    }
  }

  /** applying the attach changes from the attachplt popup**/
  applyAttachChanges(event) {
    this.attachArray = [];
    this.attachArray = _.merge([], event);
    _.forEach(this.attachArray, att => {
      _.forEach(this.treatySections[0], ts => {
        if (ts.id == att.tsId) {


          _.forEach(ts.regionPerils, rg => {
            if (rg.id == att.regionPeril) {
              _.forEach(rg.targetRaps, tr => {
                if (tr.id == att.targetRap) {
                  tr.attached = true;
                  tr.pltsAttached = [...tr.pltsAttached, this.state[att.pltId]];
                }
              })
            }
          })

          _.forEach(ts.targetRaps, rg => {
            if (rg.id == att.targetRap) {
              _.forEach(rg.regionPerils, tr => {
                if (tr.id == att.regionPeril) {
                  tr.attached = true;
                  tr.pltsAttached = [...tr.pltsAttached, this.state[att.pltId]];
                }
              })
            }
          })
        }
      })

    })
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      this.dataSource = this.getDataTwo(this.treatySections[0]);
    }
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      this.dataSource = this.getData(this.treatySections[0]);
    }


  }

  /**getting the row information to send it to the attach plt popup**/
  getRowInformation(rowData) {
    if (this.selectedSortBy == 'Minimum Grain / RAP') {
      const targetRaps = [];
      rowData.child.forEach(tr => {
        targetRaps.push(tr.id)
      })
      this.rowInformation = {
        "sort": "1",
        "rowData": rowData.id,
        "child": targetRaps
      };
    }
    if (this.selectedSortBy == 'RAP / Minimum Grain') {
      const targetRaps = [];
      rowData.child.forEach(tr => {
        targetRaps.push(tr.id)
      })
      this.rowInformation = {
        "sort": "2",
        "rowData": rowData.id,
        "child": targetRaps
      };

    }
    this.addRemoveModal = true;
  }

  /**checking if the treaty section needs to show the attached icon**/
  checkTreatySectionAttached(treatySection) {
    let checked = true;
    this.treatySections[0].forEach(ts => {
      if (ts.id == treatySection.id) {
        if (this.selectedSortBy == 'Minimum Grain / RAP') {
          ts.regionPerils.forEach(rg => {
            if (!rg.attached && !rg.overridden) {
              checked = false;
            }
          })
        }
        if (this.selectedSortBy == 'RAP / Minimum Grain') {
          ts.targetRaps.forEach(tr => {
            if (!tr.attached && !tr.overridden) {
              checked = false;
            }
          })
        }
      }
    })
    return checked;
  }

  checkRowAttached(row) {
    let checked = true;
    this.treatySections[0].forEach(ts => {
      if (this.selectedSortBy == 'Minimum Grain / RAP') {
        ts.regionPerils.forEach(rp => {
            if (rp.id == row.id) {
              if (!rp.attached && !rp.overridden) {
                checked = false;
              }
            }
          }
        )
      }
      if (this.selectedSortBy == 'RAP / Minimum Grain') {
        ts.targetRaps.forEach(tr => {
            if (tr.id == row.id) {
              if (!tr.attached && !tr.overridden) {
                checked = false;
              }
            }
          }
        )
      }

    })
    return checked;
  }

  checkRowChildAttached(rowData, child) {
    let checked = true;
    this.treatySections[0].forEach(ts => {
      if (this.selectedSortBy == 'Minimum Grain / RAP') {
        ts.regionPerils.forEach(rp => {
            if (rp.id == rowData.id) {
              rp.targetRaps.forEach(tr => {
                if (tr.id == child.id) {
                  if (!tr.attached && !tr.overridden) {
                    checked = false;
                  }
                }
              })
            }
          }
        )
      }
      if (this.selectedSortBy == 'RAP / Minimum Grain') {
        ts.targetRaps.forEach(tr => {
            if (tr.id == rowData.id) {
              tr.regionPerils.forEach(rp => {
                if (rp.id == child.id) {
                  if (!rp.attached && !rp.overridden) {
                    checked = false;
                  }
                }
              })
            }
          }
        )
      }

    })
    return checked;
  }

  selectDropDown(event: boolean, rowId) {
    if (event) {
      this.selectedDropDown = rowId;
    } else {
      this.selectedDropDown = null;
    }
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
    return newData;
  }

}
