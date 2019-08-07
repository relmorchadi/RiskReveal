import { Injectable } from '@angular/core';
import {FileBaseApi} from './fileBase.api';
import * as fromWs from '../store/actions';
import * as _ from 'lodash';
import produce from 'immer';
import {mergeMap} from 'rxjs/internal/operators/mergeMap';
import {StateContext} from '@ngxs/store';
import {WorkspaceModel} from '../model';
import {of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileBasedService {

  constructor(private fileBaseApi: FileBaseApi) { }

  loadFolderList(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return this.fileBaseApi.searchFoldersList().pipe(
      mergeMap(
        (ds: any) => of(ctx.patchState(
          produce(
            ctx.getState(), draft => {
              draft.content[wsIdentifier].fileBaseImport.folders = ds.content;
            }
          )
        ))
      )
    );
  }

  loadFilesList(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return this.fileBaseApi.searchFilesList().pipe(
      mergeMap(
        (ds: any) => of(ctx.patchState(
          produce(
            ctx.getState(), draft => {
              draft.content[wsIdentifier].fileBaseImport.files = ds.content;
            }
          )
        ))
      )
    );
  }

  readFileContent(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return this.fileBaseApi.searchReadFiles(payload).pipe(
      mergeMap(
        (ds: any) => of(ctx.patchState(
          produce(
            ctx.getState(), draft => {
              draft.content[wsIdentifier].fileBaseImport.selectedFiles = ds.content;
            }
          )
        ))
      )
    );
  }
}
