import {Injectable} from "@angular/core";
import {CalibrationAPI} from "./api/calibration.api";
import {StateContext} from "@ngxs/store";
import {WorkspaceModel} from "../model";
import {tap} from "rxjs/operators";
import * as _ from 'lodash';
import produce from "immer";


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
      console.log("LOADING");
    }))

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
            console.log("LOADING done");
          }))
        })
      )
  }

}
