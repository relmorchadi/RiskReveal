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
        draft.content[wsIdentifier].scopeOfCompleteness = _.merge(draft.content[wsIdentifier].scopeOfCompleteness, payload);
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
    return this.scopeApi.getData(uwYear, wsId)
      .pipe(
          tap((data: any[]) => {
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
                let {newRegionPerils, newTargetRaps} = this._attachPLT( data.listOfImportedPLTs, regionPerils, targetRaps);
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

  overrideSelection(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const {wsIdentifier} = state.currentTab;
    const ws = state.content[wsIdentifier];
    let target = {
      accumulationPackageId: 0,
      listOfOverrides: payload.listOfOverrides,
      overrideBasisCode: payload.overrideBasisCode,
      overrideBasisNarrative: payload.overrideBasisNarrative,
      uwYear: ws.uwYear,
      workspaceId: ws.wsId,
      workspaceName: ws.workspaceName
    };
    return this.scopeApi.overrideDone(target).pipe(
        tap((data: any) => {
          const overriddenData = data.overriddenSections;
          const scopeDataRP = _.cloneDeep(state.content[wsIdentifier].scopeOfCompleteness.data.regionPerils);
          const scopeDataTR = _.cloneDeep(state.content[wsIdentifier].scopeOfCompleteness.data.targetRaps);
          _.forEach(overriddenData, overriddenItem => {
            _.forEach(scopeDataRP, item => {
              console.log(item.id, overriddenItem.minimumGrainRegionPerilCode);
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
                      overridden: true,
                      reason: overriddenItem.overrideBasisCode,
                      reasonNarrative: overriddenItem.overrideBasisNarrative
                    };

                    scopeDataTR[overriddenTrIndex].regionPerils[overriddenTrRpIndex] = {
                      ...scopeDataTR[overriddenTrIndex].regionPerils[overriddenTrRpIndex],
                      overridden: true,
                      reason: overriddenItem.overrideBasisCode,
                      reasonNarrative: overriddenItem.overrideBasisNarrative
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

        }),
        catchError(err => {
          console.log(err);
          return of();
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
              console.log(regionIndex, targetIndex, targetConIndex);
              const regionConIndex = _.findIndex(newTargetRaps[targetConIndex].regionPerils,
                  (rp: any) => rp.id === item.id);
              console.log(targetConIndex, regionConIndex);
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
          const newTr = _.map(rp.targetRaps, trRp => ({...trRp, scopeContext: [item.id]}));
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
              _.forEach(rp.targetRaps[targetId].pltsAttached, plt => {
                const pltID = _.findIndex(regionPerils[id].targetRaps[targetId].pltsAttached, (plts: any) => plts.id === plt.id);
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
          const newRp = _.map(tr.regionPerils, trRp => ({...trRp, scopeContext: [item.id]}));
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
              _.forEach(tr.regionPerils[regionId].pltsAttached, plt => {
                const pltID = _.findIndex(targetRaps[id].regionPerils[regionId].pltsAttached, (plts: any) => plts.id === plt.id);
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

}
