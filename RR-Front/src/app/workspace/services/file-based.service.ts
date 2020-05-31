import {Injectable} from '@angular/core';
import {FileBaseApi} from './api/fileBase.api';
import * as fromWs from '../store/actions';
import * as _ from 'lodash';
import produce from 'immer';
import {mergeMap, MergeMapOperator} from 'rxjs/internal/operators/mergeMap';
import {Select, StateContext} from '@ngxs/store';
import {WorkspaceModel} from '../model';
import {forkJoin, of} from 'rxjs';
import {catchError, first, switchMap} from 'rxjs/operators';
import {forEach} from "@angular/router/src/utils/collection";
import {WorkspaceState} from "../store/states";

@Injectable({
    providedIn: 'root'
})
export class FileBasedService {


    constructor(private fileBaseApi: FileBaseApi) {
    }

    loadFolderList(ctx: StateContext<WorkspaceModel>, payload) {
        const state = ctx.getState();
        const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
        const data = {data: []};
        //let isInitialized=false;
        return this.fileBaseApi.searchFoldersList(payload).pipe(
            mergeMap(
                (ds: any) => {
                    _.forEach(ds.children, item => data.data = [...data.data,
                        {
                            label: item, data: 'folder',
                            expandedIcon: 'fa fa-folder-open',
                            collapsedIcon: 'fa fa-folder'
                        }]);
                    /*if(!isInitialized){
                        if (data.hasOwnProperty('data')) {
                            changeData(data.data);
                            console.log('directoryTree init',data);
                            isInitialized= true;
                        }
                    }*/
                    return of(ctx.patchState(
                        produce(
                            ctx.getState(), draft => {
                                draft.content[wsIdentifier].fileBaseImport.folders = data;
                                if (draft.content[wsIdentifier].fileBaseImport.importedPLTs == null) {
                                    draft.content[wsIdentifier].fileBaseImport.files = [];
                                }
                                console.log('folders service', data);
                                //draft.content[wsIdentifier].fileBaseImport.files = [];
                                //draft.content[wsIdentifier].fileBaseImport.selectedFiles = [];
                            }
                        )
                    ));
                }
            ),
            catchError(err => {
                return of(ctx.patchState(
                    produce(
                        ctx.getState(), draft => {
                            draft.content[wsIdentifier].fileBaseImport.folders = data;
                            //draft.content[wsIdentifier].fileBaseImport.files = [];
                            //draft.content[wsIdentifier].fileBaseImport.selectedFiles = [];
                        }
                    )
                ));
            })
        );
    }

    /*loadFilesList(ctx: StateContext<WorkspaceModel>, payload) {
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
    }*/

    loadFilesList(ctx: StateContext<WorkspaceModel>, payload) {
        const state = ctx.getState();
        const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
        this.fileBaseApi.searchFilesList(payload).subscribe(data => {
            ctx.patchState(produce(ctx.getState(), draft => {
                draft.content[wsIdentifier].fileBaseImport.files = data;
            }));
        })

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
        if (payload.scope === 'single') {
            ctx.patchState(produce(ctx.getState(), draft => {
                draft.content[wsIdentifier].fileBaseImport.files[payload.index].selected = payload.selection;
            }));
        } else if (payload.scope === 'all') {
            ctx.patchState(produce(ctx.getState(), draft => {
                draft.content[wsIdentifier].fileBaseImport.files = draft.content[wsIdentifier].fileBaseImport.files.map(item => {
                    return {...item, selected: payload.selection};
                });
            }));
        } else if (payload.scope === 'chunk') {
            ctx.patchState(produce(ctx.getState(), draft => {
                draft.content[wsIdentifier].fileBaseImport.files =
                    draft.content[wsIdentifier].fileBaseImport.files.map((item, i) => {
                        if (i >= payload.from && i <= payload.to) {
                            return {...item, selected: true};
                        } else {
                            return {...item, selected: false};
                        }
                    });
            }));
        }

    }

    togglePlts(ctx: StateContext<WorkspaceModel>, payload) {
        const state = ctx.getState();
        const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
        if (payload.scope === 'single') {
            ctx.patchState(produce(ctx.getState(), draft => {
                draft.content[wsIdentifier].fileBaseImport.selectedFiles[payload.index].selected = payload.selection;
            }));
        } else if (payload.scope === 'all') {
            ctx.patchState(produce(ctx.getState(), draft => {
                draft.content[wsIdentifier].fileBaseImport.selectedFiles =
                    draft.content[wsIdentifier].fileBaseImport.selectedFiles.map(item => {
                        return {...item, selected: payload.selection};
                    });
            }));
        } else if (payload.scope === 'chunk') {
            ctx.patchState(produce(ctx.getState(), draft => {
                draft.content[wsIdentifier].fileBaseImport.selectedFiles =
                    draft.content[wsIdentifier].fileBaseImport.selectedFiles.map((item, i) => {
                        if (i >= payload.from && i <= payload.to) {
                            return {...item, selected: true};
                        } else {
                            return {...item, selected: false};
                        }
                    });
            }));
        }
    }

    addToImport(ctx: StateContext<WorkspaceModel>, payload) {
        const state = ctx.getState();
        console.log('state', state);
        const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
        const files: any = _.filter(state.content[wsIdentifier].fileBaseImport.files, item => item.selected);
        let projects = state.content[wsIdentifier].projects;
        let projectId;
        let newData = state.content[wsIdentifier].fileBaseImport.selectedFiles;
        let importedPLTs = state.content[wsIdentifier].fileBaseImport.importedPLTs;
        if (newData == null) {
            newData = [];
        }
        if (importedPLTs == null) {
            importedPLTs = [];
        }
        projects.forEach(item => {
            if (item.selected)
                projectId = item.projectId.toString();
        });
        if (!projectId) {
            alert('No Selected Project !');
            return;
        }
        if (payload == null) {
            if (state.content[wsIdentifier].fileBaseImport.importedPLTs != null) {
                payload = '';
                let tab = state.content[wsIdentifier].fileBaseImport.importedPLTs[state.content[wsIdentifier].fileBaseImport.importedPLTs.length - 1].filePath.split('/');
                console.log('tab', tab);
                for (let i = 0; i < tab.length - 1; i++) {
                    payload = payload + '\\' + tab[i];
                }
            }
        }
        let filePaths = [];
        files.forEach(item => {
            this.fileBaseApi.searchReadFiles(payload + "\\" + item.label).subscribe(data => {
                //newData = [...newData, data];
                let payloadTemp = payload.replace(/\\/gi, "/");
                filePaths.push(payloadTemp + "/" + item.label);
                newData = [...newData, {
                    File_Version: data.File_Version,
                    LossTableHeaderFormat: data.LossTableHeaderFormat,
                    LossTableHeaderProducer: data.LossTableHeaderProducer,
                    LossTableType: data.LossTableType,
                    LossTableBasis: data.LossTableBasis,
                    CreateDate: data.CreateDate.split(" ")[0],
                    File_Last_Update_Date: data.File_Last_Update_Date.split(" ")[0],
                    Model: data.Model,
                    ModelProvider: data.ModelProvider,
                    ModelSystem: data.ModelSystem,
                    ModelSystemVersion: data.ModelSystemVersion,
                    ModelSystemInstance: data.ModelSystemInstance,
                    ResultsDatabaseName: data.ResultsDatabaseName,
                    User: data.User,
                    Simulation_Set_Id: data.Simulation_Set_Id,
                    Peril: data.Peril,
                    Region: data.Region,
                    Model_Version: data.Model_Version,
                    ModelModule: data.ModelModule,
                    Geo_Code: data.Geo_Code,
                    ModelName: data.ModelName,
                    SourceResultsReference: data.SourceResultsReference,
                    ResultsName: data.ResultsName,
                    ResultsDescription: data.ResultsDescription,
                    Run_Date: data.Run_Date.split(" ")[0],
                    RegionPeril: data.RegionPeril,
                    Currency: data.Currency,
                    TargetCurrency: data.TargetCurrency,
                    FinPerspective: data.FinPerspective,
                    FinPerspectiveDesc: data.FinPerspectiveDesc,
                    UnitMultiplier: data.UnitMultiplier,
                    Proportion: data.Proportion,
                    Years: data.Years,
                    SimulationPeriodBasis: data.SimulationPeriodBasis,
                    occurrenceBasis: data.occurrenceBasis,
                    OEP2: data.OEP2,
                    OEP5: data.OEP5,
                    OEP10: data.OEP10,
                    OEP25: data.OEP25,
                    OEP50: data.OEP50,
                    OEP100: data.OEP100,
                    OEP200: data.OEP200,
                    OEP250: data.OEP250,
                    OEP500: data.OEP500,
                    OEP1000: data.OEP1000,
                    OEP1250: data.OEP1250,
                    OEP2500: data.OEP2500,
                    OEP5000: data.OEP5000,
                    OEP10000: data.OEP10000,
                    AEP2: data.AEP2,
                    AEP5: data.AEP5,
                    AEP10: data.AEP10,
                    AEP25: data.AEP25,
                    AEP50: data.AEP50,
                    AEP100: data.AEP100,
                    AEP200: data.AEP200,
                    AEP250: data.AEP250,
                    AEP500: data.AEP500,
                    AEP1000: data.AEP1000,
                    AEP1250: data.AEP1250,
                    AEP2500: data.AEP2500,
                    AEP5000: data.AEP5000,
                    AEP10000: data.AEP10000,
                    AAL: data.AAL,
                    STD: data.STD,
                    COV: data.COV,
                    FileType: data.FileType,
                    FileName: data.File_Name,
                    //status: 0,
                    File_Path: payload + "\\" + data.File_Name,
                    selected: false
                }
                ];
                if (files[files.length - 1] == item) {
                    this.fileBaseApi.persisteFileBasedImportConfig(true, projectId, filePaths, payloadTemp).subscribe(data => {
                        data.forEach(item => {
                            importedPLTs = [...importedPLTs, item];
                        });
                        //importedPLTs = [...importedPLTs, data];
                        //importedPLTs.concat(data);
                        ctx.patchState(produce(ctx.getState(), draft => {
                            draft.content[wsIdentifier].fileBaseImport.importedPLTs = importedPLTs;
                        }));
                    });
                }
                ctx.patchState(produce(ctx.getState(), draft => {
                    draft.content[wsIdentifier].fileBaseImport.selectedFiles = newData;
                }));
            });
        });

        /*ctx.patchState(produce(ctx.getState(), draft => {
            draft.content[wsIdentifier].fileBaseImport.fleBasedImportConfig = data;//prb
        }));*/


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

    launchFileBasedImport(ctx: StateContext<WorkspaceModel>) {
        const state = ctx.getState();
        const wsIdentifier = _.get(state, 'currentTab.wsIdentifier');
        let projects = state.content[wsIdentifier].projects;
        let projectId = '';
        projects.forEach(item => {
            if (item.selected)
                projectId = item.projectId.toString();
        });
        if(!projectId){
            alert('No Selected Project !');
        }

        let fileBasedImportConfig = state.content[wsIdentifier].fileBaseImport.importedPLTs;

        _.forEach(fileBasedImportConfig, item =>{
           if(!item.targetRAPCode){
               alert('No available targetRAP for file '+item.fileName);
               return;
           }
        });
        const sourceResultsIds: any = _.map(fileBasedImportConfig, item => item.fileBasedImportConfigId).join(';');
        const {fileBasedImportConfigId} = _.first(fileBasedImportConfig);
        this.fileBaseApi.runFileBasedImport('',fileBasedImportConfigId , fileBasedImportConfigId, 'U011191', projectId, sourceResultsIds)
            .pipe(catchError(err =>{
                alert('Error while Submitting the Job !');
                return of(err);
            } ))
            .subscribe(data => {
            //console.log('Batch', data);
            alert('Job Submitted Successfully !')
        });


    }

}

function changeData(object: any) {

    object.forEach(o => {
        if (o.hasOwnProperty('label')) {
            o.children = o.label.children;
            o.leaf = o.label.leaf;
            o.partialSelected = o.label.partialSelected;
            o.root = o.label.root;
            o.selected = o.label.selected;
            o.label = o.label.data.file.split('\\')[o.label.data.file.split('\\').length - 1];
        } else {
            o.label = o.data.file.split('\\')[o.data.file.split('\\').length - 1];
            o.collapsedIcon = 'fa fa-folder';
            o.expandedIcon = 'fa fa-folder-open';
            o.data = 'folder';
        }
        this.changeData(o.children)
    });

}