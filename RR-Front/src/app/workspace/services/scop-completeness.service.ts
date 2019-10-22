import {Injectable} from '@angular/core';
import {StateContext, Store} from "@ngxs/store";
import {catchError, mergeMap, switchMap} from "rxjs/operators";
import * as _ from "lodash";
import {PltApi} from "./api/plt.api";
import produce from "immer";
import {ActivatedRoute} from "@angular/router";
import {WorkspaceModel} from "../model";
import {of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ScopeCompletenessService {

  constructor(private store$: Store, private pltApi: PltApi, private route$: ActivatedRoute) {

  }

  loadScopeCompletenessData(ctx: StateContext<WorkspaceModel>, payload) {
    const {
      currentTab: {
        wsIdentifier
      }
    } = ctx.getState();
    return this.pltApi.getAllPlts(payload.params)
      .pipe(
        switchMap((data) => {
          return of(ctx.patchState(produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].scopeOfCompletence.data =
              _.merge({}, ...data.plts.map(plt => ({[plt.pltId]: {...plt, visible: true}})));
            draft.content[wsIdentifier].scopeOfCompletence.wsType = draft.content[wsIdentifier].workspaceType;
          })));
        }),
        catchError(err => {
          return of(ctx.patchState(produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].scopeOfCompletence.wsType = draft.content[wsIdentifier].workspaceType;
          })));
        })
      );
  }


}
