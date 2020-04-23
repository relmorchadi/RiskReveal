import {Injectable} from '@angular/core';
import {StateContext, Store} from "@ngxs/store";
import {catchError, mergeMap, switchMap, tap} from "rxjs/operators";
import * as _ from 'lodash';
import {PltApi} from './api/plt.api';
import produce from 'immer';
import {ActivatedRoute} from '@angular/router';
import {WorkspaceModel} from '../model';
import {of} from 'rxjs';
import {ScopeOfCompletenessApi} from "./api/scopeOfCompleteness.api";
import {LoadScopeCompletenessPendingData, LoadScopePLTsData} from "../store/actions";

@Injectable({
  providedIn: 'root'
})
export class ScopeCompletenessService {

  constructor(private store$: Store, private pltApi: PltApi,
              private route$: ActivatedRoute,
              private scopeApi: ScopeOfCompletenessApi) {
  }

  patchScopeState(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const scope = _.get(payload, 'scope', null);
    ctx.patchState(produce(ctx.getState(), draft => {
      if (scope === 'scopeContext') {
        draft.content[wsIdentifier].scopeOfCompleteness.scopeContext = _.merge(draft.content[wsIdentifier].scopeOfCompleteness.scopeContext, payload.mergedData)
      } else {
        draft.content[wsIdentifier].scopeOfCompleteness.override = _.merge(draft.content[wsIdentifier].scopeOfCompleteness.override, payload);
      }
    }))
  }

  publishToPricing(ctx: StateContext<WorkspaceModel>, paylaod) {
    const {
      currentTab: {wsIdentifier}
    } = ctx.getState();
    ctx.patchState(produce(ctx.getState(), draft => {
      const selectProject: any = _.filter(draft.content[wsIdentifier].projects, item => item.selected)[0];
      draft.content[wsIdentifier].projects = _.map(draft.content[wsIdentifier].projects, item => {
        if (item.carStatus === 'Completed') {
          return {...item, carStatus: 'Superseded'};
        } else {
          return item.selected ? {...item, carStatus: 'Completed', publishedDate: new Date(), publishedBy: 'Nathalie Dulac'} : {...item};
        }
      });
    }));
  }

  loadScopeCompletenessData(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const {uwYear, wsId} = state.content[wsIdentifier];
    const {projectId} =  _.find(state.content[wsIdentifier].projects, prj => prj.selected);
    return this.scopeApi.getData(uwYear, wsId, projectId)
      .pipe(
          tap((data: any[]) => {
            console.log(data);
            ctx.patchState(produce(ctx.getState(), draft => {
              const {regionPerils, targetRaps, scopeContext} = this._formatData(data);
              draft.content[wsIdentifier].scopeOfCompleteness.data = {
                regionPerils: regionPerils,
                targetRaps: targetRaps,
                scopeContext: scopeContext
              };
              if(draft.content[wsIdentifier].scopeOfCompleteness.pendingData.regionPerils.length === 0) {
                draft.content[wsIdentifier].scopeOfCompleteness.pendingData = {
                  regionPerils: regionPerils,
                  targetRaps: targetRaps
                };
              }

              draft.content[wsIdentifier].scopeOfCompleteness.projects = _.map(
                  draft.content[wsIdentifier].projects, (item, key: any) => ({...item, selected: key === 0}));
            }));
        }),
        catchError(err => {
          console.log(err);
          return of();
        })
      );
  }

  loadScopeCompletenessDataPricing(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const {uwYear, wsId} = state.content[wsIdentifier];
    const selectedPrj = _.find(state.content[wsIdentifier].projects, item => item.selected);
    return this.scopeApi.getDataPricing(uwYear, wsId, selectedPrj.projectId)
        .pipe(
            tap((data: any) => {
              ctx.patchState(produce(ctx.getState(), draft => {
                const {regionPerils, targetRaps, scopeContext} = this._formatData(data.scopeObject);
                const listOfPLTs = _.map(data.listOfImportedPLTs, item => ({...item, pltHeaderId: item.pltheaderId, scopeIndex: [item.division]}));
                let {newRegionPerils, newTargetRaps} = this._attachPLT(listOfPLTs, regionPerils, targetRaps);
                draft.content[wsIdentifier].scopeOfCompleteness.data = {
                  regionPerils: newRegionPerils,
                  targetRaps: newTargetRaps,
                  scopeContext: scopeContext
                };
              }));
            }),
            catchError(err => {
              console.log(err);
              return of();
            })
        );
  }

  loadScopeCompletenessDataPending(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const {uwYear, wsId} = state.content[wsIdentifier];
    const {accumulationPackageId} = state.content[wsIdentifier].scopeOfCompleteness.pendingData;
    const {scopeContext} = state.content[wsIdentifier].scopeOfCompleteness.data;
    const {projectId} =  _.find(state.content[wsIdentifier].projects, prj => prj.selected);
    return this.scopeApi.getDataPending(accumulationPackageId, uwYear, wsId, projectId).pipe(
        tap((data: any) => {
          const overriddenData = data.overriddenSections;
          const {regionPerils, targetRaps} = this._formatData(data.scopeObject);
          const {scopeDataRP, scopeDataTR} = this._overrideData(overriddenData, regionPerils, targetRaps);
          let mergedPltList = [];

          const attachedPLTs = _.map(data.attachedPLTs, item => {
            const scopeIndex =  _.toNumber(item.contractSectionId);
            return {...item.attachedPLT, scopeIndex: scopeIndex, scope: scopeContext[scopeIndex - 1].id, pltHeaderId: item.attachedPLT.pltheaderId}
          });

          _.forEach(attachedPLTs, plt => {
            if (_.includes(_.map(mergedPltList, item => item.pltHeaderId), plt.pltHeaderId)) {
              const pltIndex = _.findIndex(mergedPltList, item => item.pltHeaderId === plt.pltHeaderId);
              _.merge(mergedPltList, {[pltIndex]: {...mergedPltList[pltIndex], scope: [...mergedPltList[pltIndex].scope, plt.scope],
                  scopeIndex: [...mergedPltList[pltIndex].scopeIndex, plt.scopeIndex]
                }});
            } else {
              mergedPltList = [...mergedPltList, {...plt, scope: [plt.scope], scopeIndex: [plt.scopeIndex]}];
            }
          });

          const {newRegionPerils, newTargetRaps} = this._attachPLT(mergedPltList, scopeDataRP, scopeDataTR);
          ctx.patchState(produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].scopeOfCompleteness.pendingData = {
              ...draft.content[wsIdentifier].scopeOfCompleteness.pendingData,
              targetRaps: newTargetRaps,
              regionPerils: newRegionPerils,
              overriddenSections: data.overriddenSections
            }
          }))
        })
    )
  }

  loadAccumulationPackageInfo(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const selectedProject = _.find(state.content[wsIdentifier].projects, item => item.selected);
    return this.scopeApi.getDataAccumulation(selectedProject.projectId).pipe(
        tap((data: any)  => {
          const packageData = data.length === 0 ? {accumulationPackageId: 0,
            accumulationPackageStatus: "string"} : data[0];
          ctx.patchState(produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].scopeOfCompleteness.pendingData = {
              ...draft.content[wsIdentifier].scopeOfCompleteness.pendingData,
              ...packageData,
              packages: data,
            }
          }));
          ctx.dispatch(new LoadScopeCompletenessPendingData());
        }),
        catchError(err => {
          return of();
        })
    );


  }

  listOfPLTsToAttach(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const projects = state.content[wsIdentifier].scopeOfCompleteness.projects;
    if (projects.length > 0) {
      const selectedProject = _.find(state.content[wsIdentifier].scopeOfCompleteness.projects, item => item.selected);
      return  this.scopeApi.getListOfPLTs(0, selectedProject.projectId).pipe(
          tap(data => {
            ctx.patchState(produce(ctx.getState(), draft => {
              draft.content[wsIdentifier].scopeOfCompleteness.plts = _.map(data, item => ({...item, selected: false}));
            }))
          })
      )
    }
  }

  overrideSelection(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const ws = state.content[wsIdentifier];
    let target = {
      accumulationPackageId: ws.scopeOfCompleteness.pendingData.accumulationPackageId,
      listOfOverrides: payload.listOfOverrides,
      overrideBasisCode: payload.overrideBasisCode,
      overrideBasisNarrative: payload.overrideBasisNarrative,
      uwYear: ws.uwYear,
      workspaceId: ws.wsId,
      projectId: _.find(ws.projects, item => item.selected).projectId,
      workspaceName: ws.wsId
    };
    return this.scopeApi.overrideDone(target).pipe(
        tap((data: any) => {
          const overriddenData = data.overriddenSections;
          const regionPeril = _.cloneDeep(state.content[wsIdentifier].scopeOfCompleteness.pendingData.regionPerils);
          const targetRap = _.cloneDeep(state.content[wsIdentifier].scopeOfCompleteness.pendingData.targetRaps);

          const {scopeDataRP, scopeDataTR} = this._overrideData(overriddenData, regionPeril, targetRap);

          ctx.patchState(produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].scopeOfCompleteness.pendingData = {
              regionPerils: scopeDataRP,
              targetRaps: scopeDataTR,
              overriddenSections: data.overriddenSections
            }
          }))

        }),
        catchError(err => {
          console.log(err);
          return of();
        })
    )
  }

  removeOverrideSelection(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const ws = state.content[wsIdentifier];
    const scopeDataRP = _.cloneDeep(state.content[wsIdentifier].scopeOfCompleteness.pendingData.regionPerils);
    const scopeDataTR = _.cloneDeep(state.content[wsIdentifier].scopeOfCompleteness.pendingData.targetRaps);
    const {overriddenSections} = state.content[wsIdentifier].scopeOfCompleteness.pendingData;

    let removedOverride = [];

    _.forEach(payload.listOfRemovedItems, item => {
      removedOverride = [...removedOverride, _.find(overriddenSections, overriddenItem => overriddenItem.minimumGrainRegionPerilCode === item.minimumGrainRegionPerilCode &&
      overriddenItem.accumulationRAPCode === item.accumulationRAPCode && overriddenItem.contractSectionId == item.contractSectionId)];
    });

    return this.scopeApi.overrideDelete(removedOverride).pipe(tap(data => {
      _.forEach(payload.listOfRemovedItems, overriddenItem => {
        _.forEach(scopeDataRP, item => {
          if (item.id === overriddenItem.minimumGrainRegionPerilCode) {
            const overriddenRpIndex = _.findIndex(scopeDataRP, (Otr: any) => Otr.id === item.id);
            _.forEach(item.targetRaps, tr => {
              if (tr.id === overriddenItem.accumulationRAPCode) {
                const overriddenRpTrIndex = _.findIndex(item.targetRaps, (Otr: any) => Otr.id === tr.id);
                const overriddenTrIndex = _.findIndex(scopeDataTR, (Otr: any) => Otr.id === tr.id);
                const overriddenTrRpIndex = _.findIndex(scopeDataTR[overriddenTrIndex].regionPerils,
                    (Otr: any) => Otr.id === item.id);
                console.log(_.omit(scopeDataRP[overriddenRpIndex].targetRaps[overriddenRpTrIndex].override, `${overriddenItem.contractSectionId}`));
                scopeDataRP[overriddenRpIndex].targetRaps[overriddenRpTrIndex] = {
                  ...scopeDataRP[overriddenRpIndex].targetRaps[overriddenRpTrIndex],
                  override: _.omit(scopeDataRP[overriddenRpIndex].targetRaps[overriddenRpTrIndex].override, `${overriddenItem.contractSectionId}`)
                };

                scopeDataTR[overriddenTrIndex].regionPerils[overriddenTrRpIndex] = {
                  ...scopeDataTR[overriddenTrIndex].regionPerils[overriddenTrRpIndex],
                  override: _.omit(scopeDataTR[overriddenTrIndex].regionPerils[overriddenTrRpIndex], `${overriddenItem.contractSectionId}`)
                }

              }
            })
          }
        });
      });

      ctx.patchState(produce(ctx.getState(), draft => {
        draft.content[wsIdentifier].scopeOfCompleteness.pendingData = {
          regionPerils: scopeDataRP,
          targetRaps: scopeDataTR
        }
      }))
    }));


  }

  selectProject(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].scopeOfCompleteness.projects = _.map(draft.content[wsIdentifier].scopeOfCompleteness.projects, item => ({...item, selected: item.projectId === payload.projectId}))
    }));
    ctx.dispatch(new LoadScopePLTsData());
  }

  attachPLT(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const plts = _.uniqWith(payload.plts, _.isEqual);
    const ws = state.content[wsIdentifier];

    const sendData = {
      accumulationPackageId: ws.scopeOfCompleteness.pendingData.accumulationPackageId,
      pltList: _.map(plts, item => ({contractSectionId: item.scopeIndex, pltHeaderId: item.pltHeaderId})),
      projectId: _.find(ws.projects, item => item.selected).projectId,
      workspaceName: ws.wsId,
      uwYear: ws.uwYear,
    };

    let listOFImportedPLTs = [];

    _.forEach(plts, plt => {
      const alreadyAdded = _.includes(_.map(listOFImportedPLTs, item => item.pltHeaderId), plt.pltHeaderId);
      if (alreadyAdded) {
        const pltIndex = _.findIndex(listOFImportedPLTs, item => item.pltHeaderId === plt.pltHeaderId);
        listOFImportedPLTs = _.merge(listOFImportedPLTs, {[pltIndex]: {...listOFImportedPLTs[pltIndex], scope: [...listOFImportedPLTs[pltIndex].scope, plt.scope],
          scopeIndex: [...listOFImportedPLTs[pltIndex].scopeIndex, plt.scopeIndex]}})
      } else {
        listOFImportedPLTs = [...listOFImportedPLTs, {...plt, scope: [plt.scope], scopeIndex: [plt.scopeIndex]}];
      }
    });

    return this.scopeApi.attachePLTCreate(sendData).pipe(
        tap(data => {
          ctx.patchState(produce(ctx.getState(), draft => {
            let regionPeril = [...state.content[wsIdentifier].scopeOfCompleteness.pendingData.regionPerils];
            let targetRaps = [...state.content[wsIdentifier].scopeOfCompleteness.pendingData.targetRaps];
            regionPeril = _.map(regionPeril, item => ({
              ...item, targetRaps: _.map(item.targetRaps, itemPR => ({...itemPR, pltsAttached: []}))
            }));

            targetRaps = _.map(targetRaps, item => ({
              ...item, regionPerils: _.map(item.regionPerils, itemPR => ({...itemPR, pltsAttached: []}))
            }));

            _.forEach(listOFImportedPLTs, plt => {
              _.forEach(regionPeril, item => {
                if (item.id === plt.regionPerilCode) {
                  const regionIndex = _.findIndex(regionPeril, rp => rp.id === item.id);
                  _.forEach(item.targetRaps, itemTR => {

                    if(itemTR.id === plt.accumulationRapCode) {
                      const regionTargetIndex = _.findIndex(item.targetRaps, (tr: any) => tr.id === itemTR.id);
                      const targetIndex = _.findIndex(targetRaps, tr => tr.id === itemTR.id);
                      const targetRegionIndex = _.findIndex(targetRaps[targetIndex].regionPerils, (rp: any) => rp.id === item.id);

                      regionPeril[regionIndex].targetRaps[regionTargetIndex].pltsAttached = [...regionPeril[regionIndex].targetRaps[regionTargetIndex].pltsAttached, plt];
                      targetRaps[targetIndex].regionPerils[targetRegionIndex].pltsAttached = [...targetRaps[targetIndex].regionPerils[targetRegionIndex].pltsAttached, plt];

                    }
                  })
                }
              })
            });

            draft.content[wsIdentifier].scopeOfCompleteness.pendingData = {
              regionPerils: regionPeril,
              targetRaps: targetRaps
            }

          }));
        })
    )
  }

  deleteOverride(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const ws = state.content[wsIdentifier];
    let target = {
      listOfOverrides: payload.listOfOverrides,
      overrideBasisCode: payload.overrideBasisCode,
      overrideBasisNarrative: payload.overrideBasisNarrative,
      uwYear: ws.uwYear,
      workspaceId: ws.wsId,
      workspaceName: ws.workspaceName
    };
    return this.scopeApi.overrideDelete(target).pipe(
        tap(data => {})
    )
  }

  private _attachPLT(plts, regionPerils, targetRaps) {
    const newRegionPerils = [...regionPerils];
    const newTargetRaps = [...targetRaps];
    _.forEach(newRegionPerils, item => {
      _.forEach(plts, plt => {
        if(_.includes(item.id, plt.regionPerilCode)) {
          const regionIndex = _.findIndex(newRegionPerils, (rp: any) => rp.id === item.id);
          _.forEach(item.targetRaps, child => {
            if(_.isEqual(child.id, plt.accumulationRapCode)) {
              const targetIndex = _.findIndex(item.targetRaps, (tr: any) => tr.id === child.id);
              const targetConIndex = _.findIndex(newTargetRaps, (rp: any) => rp.id === child.id);
              const regionConIndex = _.findIndex(newTargetRaps[targetConIndex].regionPerils,
                  (rp: any) => rp.id === item.id);

              newRegionPerils[regionIndex].targetRaps[targetIndex].pltsAttached =
                  [..._.toArray(newRegionPerils[regionIndex].targetRaps[targetIndex].pltsAttached), plt];
              newTargetRaps[targetConIndex].regionPerils[regionConIndex].pltsAttached =
                  [..._.toArray(newTargetRaps[targetConIndex].regionPerils[regionConIndex].pltsAttached), plt];
            }
          })
        }
      })
    });

    return {newRegionPerils , newTargetRaps}
  }

  private _formatData(data) {
    let regionPerils = [];
    let targetRaps = [];
    let scopeContext = [];
    _.forEach(data, item => {
      scopeContext = [...scopeContext, _.omit(item, ['regionPerils', 'targetRaps'])];
      _.forEach(item.regionPerils, rp => {
        const id = _.findIndex(regionPerils, dt => dt.id === rp.id);
        if (id === -1) {
          const newTr = _.map(_.uniqWith(rp.targetRaps, _.isEqual), trRp => ({...trRp, scopeContext: [item.id]}));
          regionPerils = [...regionPerils, {...rp, scopeContext: [item.id], targetRaps: newTr}];
        } else {
          regionPerils = _.merge(regionPerils, {[id]: {scopeContext: [...regionPerils[id].scopeContext, item.id]}});
          _.forEach(rp.targetRaps, tr => {
            const targetId = _.findIndex(regionPerils[id].targetRaps, (dt: any) => dt.id === tr.id);
            if (targetId === -1) {
              regionPerils = _.merge(regionPerils, {[id]: {targetRaps: [...regionPerils[id].targetRaps, {...tr, scopeContext: [item.id]}]}})
            } else {
              regionPerils = _.merge(regionPerils, {[id]: {targetRaps: _.merge(regionPerils[id].targetRaps, {[targetId]: {
                      scopeContext: [...regionPerils[id].targetRaps[targetId].scopeContext, item.id]
                    }})}});
              _.forEach(tr.pltsAttached, plt => {
                const pltID = _.findIndex(regionPerils[id].targetRaps[targetId].pltsAttached, (plts: any) => plts.pltHeaderId === plt.pltHeaderId);
                if (pltID === -1) {
                  regionPerils = _.merge(regionPerils, {[id]: {targetRaps: _.merge(regionPerils[id].targetRaps, {[targetId]: {
                          pltsAttached: [...regionPerils[id].targetRaps[targetId].pltsAttached, plt]
                        }})}})
                }
              })
            }
          });
        }
      });

      _.forEach(item.targetRaps, tr => {
        const id = _.findIndex(targetRaps, dt => dt.id === tr.id);
        if (id === -1) {
          const newRp = _.map(_.uniqWith(tr.regionPerils, _.isEqual), trRp => ({...trRp, scopeContext: [item.id]}));
          targetRaps = [...targetRaps, {...tr, scopeContext: [item.id], regionPerils: newRp}];
        } else {
          targetRaps = _.merge(targetRaps, {[id]: {scopeContext: [...targetRaps[id].scopeContext, item.id]}});
          _.forEach(tr.regionPerils, rp => {
            const regionId = _.findIndex(targetRaps[id].regionPerils, (dt: any) => dt.id === rp.id);
            if (regionId === -1) {
              targetRaps = _.merge(targetRaps, {[id]: {regionPerils: [...targetRaps[id].regionPerils, {...rp, scopeContext: [item.id]}]}})
            } else {
              targetRaps = _.merge(targetRaps, {[id]: {regionPerils: _.merge(targetRaps[id].regionPerils, {[regionId]: {
                      scopeContext: [...targetRaps[id].regionPerils[regionId].scopeContext, item.id]
                    }})}});
              _.forEach(rp.pltsAttached, plt => {
                const pltID = _.findIndex(targetRaps[id].regionPerils[regionId].pltsAttached, (plts: any) => plts.pltHeaderId === plt.pltHeaderId);
                if (pltID === -1) {
                  targetRaps = _.merge(targetRaps, {[id]: {regionPerils: _.merge(targetRaps[id].regionPerils, {[regionId]: {
                          pltsAttached: [...targetRaps[id].regionPerils[regionId].pltsAttached, plt]
                        }})}})
                }
              })
            }
          });
        }
      });
    });
    return {regionPerils, targetRaps, scopeContext}
  }

  private _overrideData(overriddenData,  regionPeril, targetRap) {
    const scopeDataRP = [...regionPeril];
    const scopeDataTR = [...targetRap];

    _.forEach(overriddenData, overriddenItem => {
      _.forEach(scopeDataRP, item => {
        if (item.id === overriddenItem.minimumGrainRegionPerilCode) {
          const overriddenRpIndex = _.findIndex(scopeDataRP, (Otr: any) => Otr.id === item.id);
          _.forEach(item.targetRaps, tr => {
            if (tr.id === overriddenItem.accumulationRAPCode) {
              const overriddenRpTrIndex = _.findIndex(item.targetRaps, (Otr: any) => Otr.id === tr.id);
              const overriddenTrIndex = _.findIndex(scopeDataTR, (Otr: any) => Otr.id === tr.id);
              const overriddenTrRpIndex = _.findIndex(scopeDataTR[overriddenTrIndex].regionPerils,
                  (Otr: any) => Otr.id === item.id);

              scopeDataRP[overriddenRpIndex].targetRaps[overriddenRpTrIndex] = {
                ...scopeDataRP[overriddenRpIndex].targetRaps[overriddenRpTrIndex],
                override: {
                  ..._.get(scopeDataRP[overriddenRpIndex].targetRaps[overriddenRpTrIndex], 'override', {}),
                  [overriddenItem.contractSectionId]: {
                    overridden: true,
                    reason: overriddenItem.overrideBasisCode,
                    reasonNarrative: overriddenItem.overrideBasisNarrative
                  }},
              };

              scopeDataTR[overriddenTrIndex].regionPerils[overriddenTrRpIndex] = {
                ...scopeDataTR[overriddenTrIndex].regionPerils[overriddenTrRpIndex],
                override: {
                  ..._.get(scopeDataTR[overriddenTrIndex].regionPerils[overriddenTrRpIndex], 'override', {}),
                  [overriddenItem.contractSectionId]: {
                    overridden: true,
                    reason: overriddenItem.overrideBasisCode,
                    reasonNarrative: overriddenItem.overrideBasisNarrative
                  }},
              }

            }
          })
        }
      });
    });

    return {scopeDataRP, scopeDataTR};
  }
}
