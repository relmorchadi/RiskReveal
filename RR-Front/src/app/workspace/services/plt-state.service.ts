import { Injectable } from '@angular/core';
import {StateContext} from '@ngxs/store';
import {loadAllPlts} from '../store/actions';
import {catchError, mergeMap} from 'rxjs/operators';
import * as _ from 'lodash';
import * as fromPlt from '../store/actions';
import {PltApi} from './plt.api';
import {pltMainModel} from '../model';

@Injectable({
  providedIn: 'root'
})
export class PltStateService {

  ctx: StateContext<pltMainModel>;

  constructor(private pltApi: PltApi) { }

  init(ctx: StateContext<pltMainModel>) {
    this.ctx= ctx;
  }

  LoadAllPlts( {payload}: loadAllPlts){
    const {
      params
    } = payload;

    this.ctx.patchState({
      loading: true
    });

    console.log('ls', JSON.parse(localStorage.getItem('deletedPlts')))

    const ls = JSON.parse(localStorage.getItem('deletedPlts')) || {};

    return this.pltApi.getAllPlts(params)
      .pipe(
        mergeMap((data) => {
          this.ctx.patchState({
            data: Object.assign({},
              {
                ...this.ctx.getState().data,
                [params.workspaceId + '-' + params.uwy]: _.merge({},
                  ...data.plts.map(plt => ({
                    [plt.pltId]: {
                      ...plt,
                      selected: false,
                      visible: true,
                      tagFilterActive: false,
                      opened: false,
                      deleted: ls[plt.pltId] ? ls[plt.pltId].deleted : undefined,
                      deletedBy: ls[plt.pltId] ? ls[plt.pltId].deletedBy : undefined,
                      deletedAt: ls[plt.pltId] ? ls[plt.pltId].deletedAt : undefined,
                      status: this.status[this.getRandomInt()],
                      newPlt: Math.random() >= 0.5,
                      EPM: ['1,080,913', '151,893', '14.05%']
                    }
                  }))
                )
              }
            ),
            filters: {
              systemTag: [],
              userTag: []
            }
          });
          return this.ctx.dispatch(new fromPlt.loadAllPltsSuccess({userTags: data.userTags}));
        }),
        catchError(err => this.ctx.dispatch(new fromPlt.loadAllPltsFail()))
      );
  }

  getRandomInt(min = 0, max = 4) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  status = ['in progress', 'valid', 'locked', 'requires regeneration', 'failed'];


}
