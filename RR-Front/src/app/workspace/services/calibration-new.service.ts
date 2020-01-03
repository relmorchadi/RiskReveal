import {Injectable} from "@angular/core";
import {CalibrationAPI} from "./api/calibration.api";
import {StateContext} from "@ngxs/store";
import {WorkspaceModel} from "../model";
import {catchError, mergeMap, tap} from "rxjs/operators";
import * as _ from 'lodash';
import produce from "immer";
import {EMPTY, forkJoin, of} from "rxjs";
import {LoadEpMetrics} from "../store/actions";


@Injectable({
  providedIn: 'root'
})
export class CalibrationNewService {

  constructor(private calibrationAPI: CalibrationAPI) {
  }

  getCalibState = (state, wsIdentifier) => state.content[wsIdentifier].calibrationNew;

  loadGroupedPltsByPure(ctx: StateContext<WorkspaceModel>, {wsId, uwYear}: any) {

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
        tap(data => {
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
        catchError(e => {
          console.log(e);
          return EMPTY;
        })
      )
  }

  loadDefaultAdjustmentsInScope(ctx: StateContext<WorkspaceModel>, {wsId, uwYear}: any) {
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
        tap(defaultAdjustments => {
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

              if (!innerDraft.adjustments[categoryCode]) innerDraft.adjustments[categoryCode] = {};
              innerDraft.adjustments[categoryCode][pltId] = adjustment;
            })
          }))
        }),
        catchError(e => {
          console.log(e);
          return EMPTY;
        })
      )
  }

  formatCurveType(curveType) {
    return _.replace(curveType, /(-)/g, '');
  }

  loadEpMetrics(ctx: StateContext<WorkspaceModel>, {wsId, uwYear, userId, curveType, resetMetrics}: any) {

    const {
      currentTab: {
        wsIdentifier
      }
    } = ctx.getState();

    return resetMetrics ?
      this.calibrationAPI.loadEpMetrics(wsId, uwYear, userId, this.formatCurveType(curveType)) : (
        !this.getCalibState(ctx.getState(), wsIdentifier).epMetrics[curveType] ? this.calibrationAPI.loadEpMetrics(wsId, uwYear, userId, this.formatCurveType(curveType)) : EMPTY
      ).pipe(
        tap(epMetrics => {

          ctx.patchState(produce(ctx.getState(), draft => {

            const {
              currentTab: {
                wsIdentifier
              }
            } = draft;

            const innerDraft = this.getCalibState(draft, wsIdentifier);

            if(resetMetrics) {
              innerDraft.epMetrics = _.pick(innerDraft.epMetrics, ['rps', 'cols']);
            };


            _.forEach(epMetrics, (metric: any, i) => {

              const {
                pltId,
                curveType
              } = metric;

              if (i == '0') {
                const rps = _.keys(_.omit(metric, ['pltId', 'curveType', 'AAL']));
                innerDraft.epMetrics.rps = rps;
                innerDraft.epMetrics.cols = ['AAL', ...rps];
              }

              if(!innerDraft.epMetrics[curveType]) innerDraft.epMetrics[curveType] = {};
              innerDraft.epMetrics[curveType][pltId] = metric;
            })


          }))

        }),
        catchError(e => {
          console.log(e);
          return EMPTY;
        })
      )
  }

  loadCalibrationConstants(ctx: StateContext<WorkspaceModel>) {
    return forkJoin(
      this.calibrationAPI.loadAllBasis(),
      this.calibrationAPI.loadAllAdjustmentTypes()
    ).pipe(
      tap(([basis, adjustmentTypes]) => {
        ctx.patchState(produce(ctx.getState(), draft => {
          const {
            currentTab: {
              wsIdentifier
            }
          } = draft;

          const innerDraft = this.getCalibState(draft, wsIdentifier);

          innerDraft.constants = {
            basis,
            adjustmentTypes
          }
        }))
      })
    )
  }

  selectPlts(ctx: StateContext<WorkspaceModel>, payload) {
    const {
      plts,
      wsIdentifier
    } = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].calibrationNew.plts = plts;
    }));
  }

  saveRPs(ctx: StateContext<WorkspaceModel>, {userId, rps, wsId, uwYear, curveType}: any) {
    return this.calibrationAPI.saveListOfRPsByUserId(rps, userId)
      .pipe(
        mergeMap(() => ctx.dispatch(new LoadEpMetrics({wsId, uwYear, userId, curveType, resetMetrics: true})))
      )
  }
}
