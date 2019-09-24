import {Injectable} from '@angular/core';
import {StateContext} from "@ngxs/store";
import * as fromInuring from "../store/actions/inuring.actions";
import {WorkspaceModel} from "../model";
import * as _ from 'lodash';
import produce from "immer";

export const defaultInuringState = {
  packages: [
    {
      selected: false,
      id: 'IP000000666',
      name: 'Inuring 1',
      description: '',
      statsExchangeRate: '',
      status: 'Done',
      creationDate: Date.now(),
      lastModified: Date.now(),
      by: 'Risk Reveal'
    },
    {
      selected: false,
      id: 'IP000000768',
      name: 'Inuring 2',
      description: '',
      statsExchangeRate: '',
      status: 'Done',
      creationDate: Date.now(),
      lastModified: Date.now(),
      by: 'Risk Reveal'
    },
    {
      selected: false,
      id: 'IP000003333',
      name: 'Inuring 3',
      description: '',
      statsExchangeRate: '',
      status: 'Done',
      creationDate: Date.now(),
      lastModified: Date.now(),
      by: 'Risk Reveal'
    }
  ],
  content: {},
  currentTab: {index: 0, packageId: null}
};

@Injectable({
  providedIn: 'root'
})
export class InuringService {

  constructor() {
  }

  openInuringPackage(ctx: StateContext<WorkspaceModel>, {payload}: fromInuring.OpenInuringPackage): any {
    const {wsIdentifier, data} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].inuring.content[data.id] = data;
      draft.content[wsIdentifier].inuring.currentTab = {
        index: _.findIndex(_.keys(draft.content[wsIdentifier].inuring.content), (key, val) => key == data.id)+1,
        packageId: data.id
      };
    }));
  }

  closeInuringPackage(ctx: StateContext<WorkspaceModel>, {payload}: fromInuring.CloseInuringPackage): any {
    const {wsIdentifier, id} = payload;
    ctx.patchState(produce(ctx.getState(), draft => {
      const inuring = draft.content[wsIdentifier].inuring;
      draft.content[wsIdentifier].inuring.content = _.omit(inuring.content, id);
      if (inuring.currentTab.packageId == id) {
        draft.content[wsIdentifier].inuring.currentTab = {
          index: 0,
          packageId: _.get(_.first(_.values(inuring.content)), 'id', null)
        };
      }
    }));
  }


}
