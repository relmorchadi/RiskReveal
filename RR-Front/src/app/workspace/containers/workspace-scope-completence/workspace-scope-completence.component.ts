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
  overrideReason: string;
  overrideReasonExplained: string = '';
  attachArray: any;

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
      this.listOfPltsData = this.getSortedPlts(this.state);
      this.treatySections = _.toArray(trestySections);
      this.dataSource = this.getData(this.treatySections[0]);
      console.log("onInit", this.treatySections[0]);
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

  // getData(treatySections) {
  //   let res = [];
  //   const treatySectionss = [...treatySections]
  //   console.log('11111111');
  //   _.forEach(treatySectionss, treatySection => {
  //     _.forEach(treatySection.regionPerils, regionPeril => {
  //       let object = {
  //         id: regionPeril.id,
  //         description: regionPeril.description,
  //         override: false,
  //         selected: false,
  //         child: _.merge([], [...(_.find(res, item => item.id == regionPeril.id) || { child: []}).child , ...regionPeril.targetRaps])
  //       };
  //       const index = _.findIndex(res, row => row.id == object.id);
  //       if (index == -1) {
  //         res.push( _.merge({},object))
  //       } else {
  //
  //         _.forEach(_.merge([],object.child) ,tr => {
  //           tr.pltsAttached.forEach(plt => {
  //             res[index].child.forEach(trr => {
  //               if (tr == trr) {
  //                 const index2 = _.findIndex(trr.pltsAttached, (pplt: any) => pplt.pltId == plt.pltId)
  //                 if (index2 == -1) {
  //                   trr.pltsAttached = _.merge([], [...trr.pltsAttached, plt])
  //
  //                 }
  //               }
  //             })
  //             /*res[index] = {...res[index], child: _.map(res[index].child, trr => {
  //                 if (tr == trr) {
  //                   const index2 = _.findIndex(trr.pltsAttached, (pplt: any) => pplt.pltId == plt.pltId)
  //                   if (index2 == -1) {
  //                     return {
  //                       ...trr,
  //                       pltsAttached : [...trr.pltsAttached, plt]
  //                     }
  //                   }
  //                 } else return trr;
  //             })}*/
  //           })
  //         })
  //
  //       }
  //
  //     })
  //   });
  //   console.log("this has been executed");
  //   return res;
  // };

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
          child:  this._mergeFunction((_.find(res, item => item.id == regionPeril.id) || { child: []}).child, regionPeril.targetRaps )
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

  getDataTwo(treatySections){
    let res = [];
    let treatySectionsClone = _.cloneDeep(treatySections);

    _.forEach(treatySectionsClone, treatySection => {
      _.forEach(treatySection.targetRaps, targetRap => {
        let object = {
          id: targetRap.id,
          description: targetRap.description,
          override: false,
          selected: false,
          child:  this._mergeFunctionTwo((_.find(res, item => item.id == targetRap.id) || { child: []}).child, targetRap.regionPerils )
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
    return newData;
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
  toggleExpanded(item: any) {
    this.grains[item.id] = !this.grains[item.id];
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

    return {rr,rd};
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

  /** get the plt's icon depending on the treatysections**/
  checkAttached(item, rowData, grain, plt) {
    let checked = 'not';

    console.log("this is the item",{
      item, rowData, grain, plt
    })
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
                  tr.reason = this.overrideReason ;
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
                  rp.reason = this.overrideReason ;
                  rp.resonDescribed = this.overrideReasonExplained;

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

  /**cancelling the override / emptying the override container**/
  cancelOverride() {
    this.selectionForOverride = [];
    this.dataSource.forEach(res => {
      res.override = false;
    })
  }

  /** showing the override checkbox for the child selected, when the conditions of override are available**/
  showOverrideButton() {
    let checked = false;
    this.dataSource.forEach(res => {
      if (res.override == true) {
        checked = true;
      }
    })
    return checked;
  }

  /** hiding the popup**/
  onHide() {
    this.showOverrideModal = false;
    this.overrideReasonExplained = '';
    this.overrideReason = null;
  }

  /** applying the attach changes from the attachplt popup**/
  applyAttachChanges(event) {
    this.attachArray = event;
    _.forEach(this.attachArray, att => {
      if (att.state == 'expected') {
        _.forEach(this.treatySections[0], ts => {
          if (ts.id == att.treatySectionId) {
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
          }
        })
      }
      if (att.state == 'attached') {
        _.forEach(this.treatySections[0], ts => {
          if (ts.id == att.treatySectionId) {
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
        })
      }
    })
    _.forEach(this.attachArray, att => {
      if (att.state == 'expected') {
        _.forEach(this.treatySections[0], ts => {
          if (ts.id == att.treatySectionId) {
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
      }
      if (att.state == 'attached') {
        _.forEach(this.treatySections[0], ts => {
          if (ts.id == att.treatySectionId) {
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
          }
        })
      }
    })
    if(this.selectedSortBy == 'RAP / Minimum Grain'){
      this.dataSource = this.getDataTwo(this.treatySections[0]);
    }
    if(this.selectedSortBy == 'Minimum Grain / RAP') {
      this.dataSource = this.getData(this.treatySections[0]);
    }


  }

}
