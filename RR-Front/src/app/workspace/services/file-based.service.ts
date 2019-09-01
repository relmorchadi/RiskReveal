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
    let newData = [];
    files.forEach(item => {
      newData = [...newData, {
        File_Version: 'YLT_1.1',
        LossTableHeaderFormat: 'Manual Import',
        LossTableHeaderProducer: 'MAT-R',
        LossTableType: 'YLT',
        LossTableBasis: 'AM',
        CreateDate: '2019-01-11 15:58:53',
        Model: 'Elements',
        ModelProvider: 'IF',
        ModelSystem: 'Elements',
        ModelSystemVersion: '12.0.0.0',
        ModelSystemInstance: 'PRD',
        ResultsDatabaseName: 'IF_RES_2019_THFL',
        User: 'U006220',
        Simulation_Set_Id: '7511',
        Peril: 'FL',
        Region: 'Thailand',
        Model_Version: '12.0.0.0',
        ModelModule: 'DLM',
        Geo_Code: 'THA',
        ModelName: 'Thailand - Flood',
        SourceResultsReference: '52',
        ResultsName: 'SAGI_XOL_2019 - SAGI_XOL_2019',
        ResultsDescription: 'NA',
        Run_Date: '2018-11-21',
        RegionPeril: 'ZAEQ',
        Currency: 'THB',
        TargetCurrency: 'THB',
        FinPerspective: 'GR',
        FinPerspectiveDesc: 'Gross',
        UnitMultiplier: 1,
        Proportion: 100,
        Years: '100000',
        SimulationPeriodBasis: '1',
        occurrenceBasis: 'PerEvent',
        OEP2: '0',
        OEP5: '202169773',
        OEP10: '644137073',
        OEP25: '2284819230',
        OEP50: '2659970392',
        OEP100: '2888477988',
        OEP200: '3053388300',
        OEP250: '3154820328',
        OEP500: '3297144605',
        OEP1000: '3517068402',
        OEP1250: '3564348833',
        OEP2500: '3854736696',
        OEP5000: '3952938598',
        OEP10000: '3970661158',
        AEP2: '0',
        AEP5: '212568642',
        AEP10: '676625138',
        AEP25: '2333180412',
        AEP50: '2765833769',
        AEP100: '3053280912',
        AEP200: '3364788457',
        AEP250: '3517137188',
        AEP500: '3952938598',
        AEP1000: '4833154038',
        AEP1250: '5082054013',
        AEP2500: '5560342418',
        AEP5000: '5863617237',
        AEP10000: '6282498696',
        AAL: '246796891.07553',
        STD: '650809745.215264',
        COV: '2.63702570311589',
        FileType: 'PLT',
        FileName: item.label,
        selected: false
      }];
    });
    ctx.patchState(produce(ctx.getState(), draft => {
      draft.content[wsIdentifier].fileBaseImport.selectedFiles = newData;
    }));
    /*return forkJoin(
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
      ));*/
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
