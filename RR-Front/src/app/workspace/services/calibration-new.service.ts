import {Injectable} from "@angular/core";
import {CalibrationAPI} from "./api/calibration.api";
import {StateContext} from "@ngxs/store";
import {WorkspaceModel} from "../model";
import {catchError, mergeMap, tap} from "rxjs/operators";
import * as _ from 'lodash';
import produce from "immer";
import * as fromWS from '../store'
import {EMPTY} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class CalibrationNewService {

  constructor(private calibrationAPI: CalibrationAPI) {}

  getCalibState = (state, wsIdentifier) => state.content[wsIdentifier].calibrationNew;

  loadGroupedPltsByPure(ctx: StateContext<WorkspaceModel>, { wsId, uwYear }: any) {

    ctx.patchState(produce(ctx.getState(), draft => {
      const {
        currentTab: {
          wsIdentifier
        }
      } = draft;

      this.getCalibState(draft, wsIdentifier).loading = true;
    }));

    return this.calibrationAPI.loaGroupedPltsByPure(wsId, uwYear)
      .pipe(
        tap( data => {
          ctx.patchState(produce(ctx.getState(), (draft: WorkspaceModel) => {

            const {
              currentTab: {
                wsIdentifier
              }
            } = draft;

            const innerDraft = this.getCalibState(draft, wsIdentifier);

            innerDraft.plts = data;
            innerDraft.loading = false;
          }))
        }),
        catchError( e => {
          console.log(e);
          return EMPTY;
        })
      )
  }

  loadDefaultAdjustmentsInScope(ctx: StateContext<WorkspaceModel>, { wsId, uwYear }: any) {
    ctx.patchState(produce(ctx.getState(), draft => {
      const {
        currentTab: {
          wsIdentifier
        }
      } = draft;

      this.getCalibState(draft, wsIdentifier).loading = true;
    }));

    return this.calibrationAPI.loadDefaultAdjustments(wsId, uwYear)
      .pipe(
        tap( defaultAdjustments => {
          ctx.patchState(produce(ctx.getState(), (draft: WorkspaceModel) => {

            const {
              currentTab: {
                wsIdentifier
              }
            } = draft;

            const innerDraft = this.getCalibState(draft, wsIdentifier);

            _.forEach(defaultAdjustments, (adjustment: any, i) => {

              const {
                pltId,
                categoryCode
              } = adjustment;

              if(!innerDraft.adjustments[categoryCode]) innerDraft.adjustments[categoryCode]= {};
              innerDraft.adjustments[categoryCode][pltId] = adjustment;
            })
          }))
        }),
        catchError( e => {
          console.log(e);
          return EMPTY;
        })
      )
  }

  loadEpMetrics(ctx: StateContext<WorkspaceModel>, { wsId, uwYear, userId, curveType }: any) {
    return this.calibrationAPI.loadEpMetrics(wsId, uwYear, userId, curveType)
      .pipe(
        tap( epMetrics => {

          ctx.patchState(produce(ctx.getState(), draft => {

            const {
              currentTab: {
                wsIdentifier
              }
            } = draft;

            const innerDraft = this.getCalibState(draft, wsIdentifier);

            _.forEach(epMetrics, (metric: any, i) => {

              const {
                pltId,
                curveType
              } = metric;
              if(!innerDraft.epMetrics[curveType]) innerDraft.epMetrics[curveType]= {};
              innerDraft.epMetrics[curveType][pltId] = metric;
              if( i == '0' ) {
                innerDraft.epMetrics.cols = _.keys(_.omit(metric, ['pltId', 'curveType']));
              }
            })


          }))

        }),
        catchError( e => {
          console.log(e);
          return EMPTY;
        })
      )
  }

  selectPlts(ctx: StateContext<WorkspaceModel>, payload){
    console.log(payload)
    const {
      plts,
      wsIdentifier
    } = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].calibrationNew.plts =  plts;
    }));
  }
}
