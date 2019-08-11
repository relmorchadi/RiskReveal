import {Injectable} from '@angular/core';
import {FileBaseApi} from './fileBase.api';
import * as _ from 'lodash';
import produce from 'immer';
import {mergeMap} from 'rxjs/internal/operators/mergeMap';
import {StateContext} from '@ngxs/store';
import {WorkspaceModel} from '../model';
import {forkJoin, of} from 'rxjs';
import {switchMap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class FileBasedService {

  constructor(private fileBaseApi: FileBaseApi) {
  }

  loadFolderList(ctx: StateContext<WorkspaceModel>) {
    const state = ctx.getState();
    const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
    return this.fileBaseApi.searchFoldersList().pipe(
      mergeMap(
        (ds: any) => {
          const data = {data: []};
          _.forEach(ds, item => data.data = [...data.data,
            {
              label: item, data: 'folder',
              expandedIcon: 'fa fa-folder-open',
              collapsedIcon: 'fa fa-folder'
            }]);
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
              draft.content[wsIdentifier].fileBaseImport.files = ds.map(item => {
                return {label: item, selected: false};
              });
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
    const files = _.filter(state.content[wsIdentifier].fileBaseImport.files, item => item.selected);
    return forkJoin(
      files.map((dt: any) => this.fileBaseApi.searchReadFiles(payload + '/' + dt.label))
    ).pipe(
      switchMap(out => {
          out.forEach(item => {
            const text = item.split('\n');
            const tab = Array(62).fill('').map((v, i) => i);
            let parsedData = '';
            tab.forEach(index => {
              if (index > 0) {
                console.log(text[index]);
                parsedData = parsedData + '"' + text[index].replace(/[^\S]+\s/g, '": "') + '",' + '\n';
              }
            });
            parsedData = '{\n' + parsedData + '\n}';
            console.log({data: parsedData});
          });
          const dataTables = {};
          console.log(out);
          return of(
            ctx.patchState(produce(ctx.getState(), draft => {
            })));
        }
      ));
  }

}
