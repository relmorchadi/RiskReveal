import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Store} from '@ngxs/store';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';
import {UpdateWsRouting} from '../../store/actions';
import {Navigate} from '@ngxs/router-plugin';
import {DataTables} from './data';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import * as _ from 'lodash';

@Component({
  selector: 'app-workspace-file-base-import',
  templateUrl: './workspace-file-base-import.component.html',
  styleUrls: ['./workspace-file-base-import.component.scss']
})
export class WorkspaceFileBaseImportComponent extends BaseContainer implements OnInit, StateSubscriber {
  collapseImportedFiles = false;
  collapseImportedPLTs = false;

  managePopUp = false;
  columnsForConfig;
  targetConfig;

  wsIdentifier;
  workspaceInfo: any;

  hyperLinks: string[] = ['Risk link', 'File-based'];
  hyperLinksRoutes: any = {
    'Risk link': '/RiskLink',
    'File-based': '/FileBasedImport'
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

  emptyData = false;

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

    this.textFilesData = DataTables.textFilesData;
    this.pltColumns = DataTables.PltDataTables;
    this.directoryTree = DataTables.directoryTree;
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
    this.dispatch([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ]);
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

  checkRow(event, row, scope) {
  }

  selectRows(row, index) {
  }

  deleteRow(id) {
  }

  nodeSelect(event) {
  }

  nodeUnselect(event) {
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
