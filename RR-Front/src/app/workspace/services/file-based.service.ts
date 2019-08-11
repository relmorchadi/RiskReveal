import { Injectable } from '@angular/core';
import {FileBaseApi} from './fileBase.api';
import * as fromWs from '../store/actions';
import * as _ from 'lodash';
import produce from 'immer';
import {mergeMap} from 'rxjs/internal/operators/mergeMap';
import {StateContext} from '@ngxs/store';
import {WorkspaceModel} from '../model';
import {forkJoin, of} from 'rxjs';
import {switchMap} from 'rxjs/operators';

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
        (ds: any) => {
          const data = {data: []};
          _.forEach(ds, item => data.data = [...data.data,
            {label: item, data: 'folder',
            expandedIcon: 'fa fa-folder-open',
            collapsedIcon: 'fa fa-folder'}]);
          return of(ctx.patchState(
            produce(
              ctx.getState(), draft => {
                draft.content[wsIdentifier].fileBaseImport.folders = data;
                draft.content[wsIdentifier].fileBaseImport.files = [];
              }
            )
          ));
        }
      )
    );
  }

  loadFilesList(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return this.fileBaseApi.searchFilesList(payload).pipe(
      mergeMap(
        (ds: any) => of(ctx.patchState(
          produce(
            ctx.getState(), draft => {
              draft.content[wsIdentifier].fileBaseImport.files = ds.map(item => { return {label: item, selected: false}; });
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

  toggleFile(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].fileBaseImport.files[payload.index].selected = payload.selection;
    }));
  }

  addToImport(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    const files: any = _.filter(state.content[wsIdentifier].fileBaseImport.files, item => item.selected);
    return forkJoin(
      files.map((dt: any) => this.fileBaseApi.searchReadFiles(payload + '/' + dt.label))
    ).pipe(
      switchMap(out => {
        let dataTables = [];
        out.forEach((item: string, key) => {
          let parsedData = '';
          const text = item.split('\n');
          const tab = Array(62).fill('').map((v, i) => i);
          tab.forEach(index => {
            if (index > 0) {
              const keyValue = text[index].replace(/\s+/, '\x01').split('\x01');
              parsedData = parsedData + '"' + keyValue[0] + '": "' + keyValue[1] + '",' + '\n';
            }
          });
          parsedData = '{\n' + parsedData +
            ` "FileType": "PLT",\n "FileName": "${files[key].label}",\n "selected": false \n} `;
          dataTables = [...dataTables, JSON.parse(parsedData)];
        });
        console.log(dataTables);
        return of(
           ctx.patchState(produce(ctx.getState(), draft => {
             draft.content[wsIdentifier].fileBaseImport.selectedFiles = dataTables;
           })));
        }
      ));
  }

  removeFileFromImport(ctx: StateContext<WorkspaceModel>, payload) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    ctx.patchState(produce(
      ctx.getState(), draft => {
        draft.content[wsIdentifier].fileBaseImport.selectedFiles =
          _.filter(draft.content[wsIdentifier].fileBaseImport.selectedFiles
            , item => item.FileName !== payload.FileName);
      }
    ));
  }

}
