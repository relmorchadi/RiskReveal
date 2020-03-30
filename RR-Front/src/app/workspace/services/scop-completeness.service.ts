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
              draft.content[wsIdentifier].scopeOfCompleteness.data = {
                regionPerils: regionPerils,
                targetRaps: targetRaps,
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
      listOfOverrides: [
/*      {
        "accumulationRAPCode": "string",
        "contractSectionId": 0,
        "minimumGrainRegionPerilCode": "string"
      }*/
      ],
      overrideBasisCode: "string",
      overrideBasisNarrative: "string",
      uwYear: ws.uwYear,
      workspaceId: ws.workspaceId,
      workspaceName: ws.workspaceName
    }
  }

  deleteOverride(ctx: StateContext<WorkspaceModel>, payload) {

  }

}
