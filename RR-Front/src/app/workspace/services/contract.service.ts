import { Injectable } from '@angular/core';
import {StateContext, Store} from '@ngxs/store';
import {WorkspaceModel} from '../model';
import produce from 'immer';
import * as _ from 'lodash';
import * as fromWS from '../store/actions';

@Injectable({
  providedIn: 'root'
})
export class ContractService {

  constructor() { }

  loadContractData(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = state.currentTab.wsIdentifier;
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].contract.typeWs = draft.content[wsIdentifier].workspaceType;
      draft.content[wsIdentifier].contract.fac =  draft.content[wsIdentifier].projects;
      }
    ));
  }
}
