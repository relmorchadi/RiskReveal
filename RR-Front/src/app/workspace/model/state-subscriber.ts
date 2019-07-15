import {EventEmitter} from "@angular/core";

export interface StateSubscriber {
  actionsEmitter:EventEmitter<any>;
  patchState(state:any):void;
}
