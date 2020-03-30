import {Injectable} from "@angular/core";
import {CalibrationAPI} from "./api/calibration.api";
import {StateContext} from "@ngxs/store";
import {WorkspaceModel} from "../model";
import {catchError, mergeMap, switchMap, tap} from "rxjs/operators";
import * as _ from 'lodash';
import produce from "immer";
import {concat, EMPTY, forkJoin, of} from "rxjs";
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

  loadEpMetrics(ctx: StateContext<WorkspaceModel>, {wsId, uwYear, curveType, resetMetrics}: any) {

    const {
      currentTab: {
        wsIdentifier
      }
    } = ctx.getState();

    return this.calibrationAPI.loadEpMetrics(wsId, uwYear, this.formatCurveType(curveType), 'Calibration').pipe(
        tap(epMetrics => {

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
        this.calibrationAPI.loadAllAdjustmentTypes(),
        this.calibrationAPI.loadAllAdjustmentStates(),
        this.calibrationAPI.loadDefaultRPs()
    ).pipe(
      tap(([basis, adjustmentTypes, status]: any) => {
        ctx.patchState(produce(ctx.getState(), draft => {

          draft.constants = {
            basis,
            adjustmentTypes,
            status
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
      draft.content[wsIdentifier].calibrationNew.plts = [...plts];
    }));
  }

  saveRPs(ctx: StateContext<WorkspaceModel>, {rps, wsId, uwYear, curveType}: any) {
    return this.calibrationAPI.saveListOfRPsByUserId(rps, 'Calibration')
      .pipe(
        mergeMap(() => ctx.dispatch(new LoadEpMetrics({wsId, uwYear, curveType, resetMetrics: true})))
      )
  }

  saveOrDeleteRPs(ctx: StateContext<WorkspaceModel>, {deletedRPs, newlyAddedRPs, wsId, uwYear, curveType}) {
    return forkJoin(
        this.calibrationAPI.saveListOfRPsByUserId(newlyAddedRPs, 'Calibration'),
        this.calibrationAPI.deleteListOfRPsByUserId(deletedRPs, 'Calibration')
    ).pipe(
        mergeMap(() => ctx.dispatch(new LoadEpMetrics({wsId, uwYear, curveType, resetMetrics: true})))
    )
  }

}
