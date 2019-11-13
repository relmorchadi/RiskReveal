import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Select, Store} from '@ngxs/store';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';
import {RemoveFileFromImportAction, UpdateWsRouting} from '../../store/actions';
import {Navigate} from '@ngxs/router-plugin';
import {DataTables} from './data';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import * as _ from 'lodash';
import {WorkspaceState} from '../../store/states';

@Component({
  selector: 'app-workspace-file-base-import',
  templateUrl: './workspace-file-base-import.component.html',
  styleUrls: ['./workspace-file-base-import.component.scss']
})
export class WorkspaceFileBaseImportComponent extends BaseContainer implements OnInit, StateSubscriber {
  collapseImportedFiles = false;
  collapseSearchFiles = false;
  collapseImportedPLTs = false;

  managePopUp = false;
  columnsForConfig;
  targetConfig;

  wsIdentifier;
  workspaceInfo: any;

  hyperLinks: string[] = ['RiskLink', 'File-Based'];
  hyperLinksRoutes: any = {
    'RiskLink': '/RiskLink',
    'File-Based': '/FileBasedImport'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };

  pltColumns: any;
  directoryTree: any;
  selectedFile: any;
  textFilesData: any;
  importedFiles: any;

  nodePath;
  peril = [
    {color: '#E70010', content: 'EQ'},
    {color: '#008694', content: 'FL'},
    {color: '#7BBE31', content: 'WS'}
  ];

  items = [
    {
      label: 'View Detail', icon: 'pi pi-eye', command: (event) => {this.selectedPlt = this.contextSelectedItem; }
    }
  ];
  contextSelectedItem;
  selectedPlt = null;

  allCheckedImportedFiles =  false;
  allCheckedImportedPlts = false;

  indeterminateImportedFiles = false;
  indeterminateImportedPlts = false;

  indexImportFiles = null;
  indexImportPlts = null;

  @Select(WorkspaceState.getFileBasedData) fileBase$;
  fileBase: any;

  @Select(WorkspaceState.getFileBaseSelectedFiles) selectedData$;
  selectedData;

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
      this.hyperLinksConfig = {
        wsId,
        uwYear: year
      };
    });

    this.dispatch(new fromWs.LoadFileBasedFoldersAction());
    this.fileBase$.subscribe(value => {
      this.fileBase = _.merge({}, value);
      this.detectChanges();
    });
    this.selectedData$.subscribe(value => {
      this.selectedData =  value;
      this.detectChanges();
    });

    this.textFilesData = DataTables.textFilesData;
    this.pltColumns = DataTables.PltDataTables;
    this.directoryTree = _.merge({}, DataTables.directoryTree, this.fileBase.folders);
    this.importedFiles = DataTables.importedFiles;
  }

  saveColumns() {
    this.toggleColumnsManager(this.targetConfig);
    if (this.targetConfig === 'pltToImport') {
      this.pltColumns = [...this.columnsForConfig];
    } else if (this.targetConfig === 'pltImported') {
      this.importedFiles = [...this.columnsForConfig];
    }
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.columnsForConfig, event.previousIndex, event.currentIndex);
  }

  toggleCol(i: number) {
    this.columnsForConfig = _.merge(
      [],
      this.columnsForConfig,
      {[i]: {...this.columnsForConfig[i], visible: !this.columnsForConfig[i].visible}}
    );
  }

  toggleColumnsManager(target) {
    this.managePopUp = !this.managePopUp;
    if (this.managePopUp) {
      if (target === 'pltToImport') {
        this.columnsForConfig = [...this.pltColumns];
      } else if (target === 'pltImported') {
        this.columnsForConfig = [...this.importedFiles];
      }
      this.targetConfig = target;
    }
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }

  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspaceInfo;
/*    this.dispatch([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);*/
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspaceInfo;
/*    this.dispatch([
    ]);*/
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  navigateFromHyperLink({route}) {
    const {wsId, uwYear} = this.workspaceInfo;
    this.dispatch(
      [new UpdateWsRouting(this.wsIdentifier, route),
        new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
    );
  }

  checkRow(event, index, scope) {
    if (scope === 'files') {
      this.dispatch(new fromWs.ToggleFilesAction({index, selection: event, scope: 'single'}));
      this.checkIndeterminateFile();
    } else if (scope === 'plt') {
      this.dispatch(new fromWs.TogglePltsAction({index, selection: event, scope: 'single'}));
      this.checkIndeterminatePlt();
    }
  }

  checkIndeterminateFile() {
    const filtredFiles =  _.filter(this.fileBase.files, item => item.selected).length;
    console.log(filtredFiles === this.fileBase.files.length);
    if (filtredFiles === this.fileBase.files.length) {
      this.indeterminateImportedFiles = false;
      this.allCheckedImportedFiles = true;
    } else {
      if (filtredFiles > 0) {
        this.indeterminateImportedFiles = true;
        this.allCheckedImportedFiles = false;
      } else {
        this.indeterminateImportedFiles = false;
        this.allCheckedImportedFiles = false;
      }
    }
    console.log(this.allCheckedImportedFiles, this.indeterminateImportedFiles);
  }

   checkIndeterminatePlt() {
     const filtred =  _.filter(this.fileBase.selectedFiles, item => item.selected).length;
     if (filtred === this.fileBase.selectedFiles.length) {
       this.indeterminateImportedPlts = false;
       this.allCheckedImportedPlts = true;
     } else {
       if (filtred > 0) {
         this.indeterminateImportedPlts = true;
         this.allCheckedImportedPlts = false;
       } else {
         this.indeterminateImportedPlts = false;
         this.allCheckedImportedPlts = false;
       }
     }
     if (this.fileBase.selectedFiles.length === 0) {
       this.indeterminateImportedPlts = false;
       this.allCheckedImportedPlts = false;
     }
   }

  selectToImport(value, index) {
    if ((window as any).event.ctrlKey) {
      this.dispatch(new fromWs.ToggleFilesAction({index, selection: true, scope: 'single'}));
      this.indexImportFiles = index;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.indexImportFiles || this.indexImportFiles === 0) {
        this._selectSection(Math.min(index, this.indexImportFiles), Math.max(index, this.indexImportFiles), 'files');
      } else {
        this.dispatch(new fromWs.ToggleFilesAction({index, selection: true, scope: 'single'}));
      }
    } else {
      this.dispatch(new fromWs.ToggleFilesAction({selection: false, scope: 'all'}));
      this.dispatch(new fromWs.ToggleFilesAction({index, selection: true, scope: 'single'}));
      this.indexImportFiles = index;
    }
    this.checkIndeterminateFile();
  }

  selectRows(index) {
    if ((window as any).event.ctrlKey) {
      this.dispatch(new fromWs.TogglePltsAction({index, selection: true, scope: 'single'}));
      this.indexImportPlts = index;
    } else if ((window as any).event.shiftKey) {
      event.preventDefault();
      if (this.indexImportPlts || this.indexImportPlts === 0) {
        this._selectSection(Math.min(index, this.indexImportPlts), Math.max(index, this.indexImportPlts), 'plt');
      } else {
        this.dispatch(new fromWs.TogglePltsAction({index, selection: true, scope: 'single'}));
      }
    } else {
      this.dispatch(new fromWs.TogglePltsAction({selection: false, scope: 'all'}));
      this.dispatch(new fromWs.TogglePltsAction({index, selection: true, scope: 'single'}));
      this.indexImportPlts = index;
    }
    this.checkIndeterminatePlt();
  }

  private _selectSection(from, to, scope) {
    if (scope === 'plt') {
      this.dispatch(new fromWs.TogglePltsAction({selection: false, scope: 'all'}));
      if (from === to) {
        this.dispatch(new fromWs.TogglePltsAction({index: from, selection: true, scope: 'single'}));
      } else {
        this.dispatch(new fromWs.TogglePltsAction({from, to, selection: true, scope: 'chunk'}));
      }
    } else if (scope === 'files') {
      this.dispatch(new fromWs.ToggleFilesAction({selection: false, scope: 'all'}));
      if (from === to) {
        this.dispatch(new fromWs.ToggleFilesAction({index: from, selection: true, scope: 'single'}));
      } else {
        this.dispatch(new fromWs.ToggleFilesAction({from, to, selection: true, scope: 'chunk'}));
      }
    }
  }

  deleteRow(row, index) {
    if (row.FileName === this.selectedPlt.FileName) {
      if (index === this.fileBase.selectedFiles.length - 1) {
        this.selectedPlt = this.fileBase.selectedFiles[index - 1];
      } else {
        this.selectedPlt = this.fileBase.selectedFiles[index + 1];
      }
    }
    const selectedPltData = _.filter(this.fileBase.selectedFiles, item => item.selected);
    this.dispatch(new fromWs.RemoveFileFromImportAction(row));
    this.checkIndeterminatePlt();
  }

  updateAllChecked(scope) {
    if (scope === 'ImportedFiles') {
      const selected = _.filter(this.fileBase.files, item => item.selected).length;
      if (selected === 0) {
        this.dispatch(new fromWs.ToggleFilesAction({selection: true, scope: 'all'}));
      } else {
        this.allCheckedImportedFiles = true;
        this.dispatch(new fromWs.ToggleFilesAction({selection: false, scope: 'all'}));
      }
      this.checkIndeterminateFile();
    } else if (scope === 'ImportedPlts') {
      const selected = _.filter(this.fileBase.selectedFiles, item => item.selected).length;
      if (selected === 0) {
        this.dispatch(new fromWs.TogglePltsAction({selection: true, scope: 'all'}));
      } else {
        this.allCheckedImportedPlts = true;
        this.dispatch(new fromWs.TogglePltsAction({selection: false, scope: 'all'}));
      }
      this.checkIndeterminatePlt();
    }
  }

  nodeSelect(event) {
    this.nodePath = '/' + event.node.label;
    this.dispatch(new fromWs.LoadFileBasedFilesAction(this.nodePath));
    this.allCheckedImportedFiles = false;
    this.indeterminateImportedFiles = false;
  }

  nodeUnselect(event) {
    this.nodePath = null;
  }

  addForImport() {
    this.dispatch(new fromWs.AddFileForImportAction(this.nodePath));
    this.selectedPlt = this.fileBase.selectedFiles[0];
  }

  getColor(RP) {
    return  _.filter(this.peril, item => item.content === RP)[0].color;
  }

  getLength() {
    const path = _.get(this.fileBase, 'selectedFiles', null);
    if (path === null) {
      return true;
    } else {
      return this.fileBase.selectedFiles.length === 0;
    }
  }

  getHeight() {
    return this.collapseSearchFiles ? '350px' : '143px';
  }

  getFileLength() {
    const path = _.get(this.fileBase, 'files', null);
    if (path === null) {
      return true;
    } else {
      return this.fileBase.files.length === 0;
    }
  }

  getKeys(object) {
    return _.keys(object);
  }

  rowTrackBy = (index, item) => {
    return item.FileName;
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
