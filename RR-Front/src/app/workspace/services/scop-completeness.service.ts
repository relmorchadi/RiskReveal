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
            console.log(data);
            ctx.patchState(produce(ctx.getState(), draft => {
              let regionPerils = [];
              let targetRaps = [];
              _.forEach(data, item => {
                regionPerils = [...regionPerils, ...item.regionPerils];
                targetRaps = [...targetRaps, ...item.targetRaps]
              });
              draft.content[wsIdentifier].scopeOfCompleteness.data = {
                regionPerils: regionPerils,
                targetRaps: targetRaps
              };
            }));
        }),
        catchError(err => {
          console.log(err);
          return of();
        })
      );
  }

}
