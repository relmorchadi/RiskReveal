import {ChangeDetectorRef, Component, EventEmitter, OnInit, Output, Input, ViewChild} from '@angular/core';
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
import {template} from "@angular/core/src/render3";
import {Table} from "primeng/table";
import {Message} from "../../../shared/message";
import {TreeNode} from "primeng/api";


@Component({
    selector: 'app-workspace-file-base-import',
    templateUrl: './workspace-file-base-import.component.html',
    styleUrls: ['./workspace-file-base-import.component.scss']
})
export class WorkspaceFileBaseImportComponent extends BaseContainer implements OnInit, StateSubscriber {

    isInitialized = false;
    status = 0;
    collapseImportedFiles = false;
    collapseSearchFiles = false;
    collapseImportedPLTs = false;

    managePopUp = false;
    deletePopUp = false;
    DataToDelete;
    IndiceToDelete;
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
    directoryTree: any = null;
    selectedFile: any;
    textFilesData: any;
    importedFiles: any;

    nodePath;
    nodeTemp;
    path;


    object;

    peril = [
        {color: '#E70010', content: 'EQ'},
        {color: '#008694', content: 'FL'},
        {color: '#7BBE31', content: 'WS'},
        {color: '#800000', content: 'TC'},//ajouter
    ];

    items = [
        {
            label: 'View Detail', icon: 'pi pi-eye', command: (event) => {
                this.selectedPlt = this.contextSelectedItem;
            }
        }
    ];
    contextSelectedItem;
    selectedPlt = null;

    allCheckedImportedFiles = false;
    allCheckedImportedPlts = false;

    indeterminateImportedFiles = false;
    indeterminateImportedPlts = false;

    indexImportFiles = null;
    indexImportPlts = null;


    @ViewChild('iTable') table: Table;
    filterTable = [];
    sortData = [];
    filterData = [];
    index;

    loadingFiles;
    loadingPlts;

    @Select(WorkspaceState.getFileBasedData) fileBase$;
    fileBase: any;

    fileBaseTemp;

    @Select(WorkspaceState.getFileBaseFiles) selectedData$;
    selectedData;

    selectedDataTemp;

    @Select(WorkspaceState.getFileBaseSelectedFiles) selectedFiles$;
    selectedFiles;

    selectedFilesTemp;

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

        this.dispatch(new fromWs.LoadFileBasedFoldersAction(null));

        this.fileBase$.subscribe(value => {
            if (!this.isInitialized) {
                this.fileBase = _.merge({}, value);
                this.fileBaseTemp = _.merge({}, value);
                this.directoryTree = _.merge({}, this.fileBase.folders);
                console.log('Here is directory tree', this.directoryTree);
                if (this.directoryTree.hasOwnProperty('data')) {
                    this.changeData(this.directoryTree.data);
                    console.log('directoryTree init', this.directoryTree);
                    this.isInitialized = true;
                }
                /*for (let directoryTreeKey in this.directoryTree.data) {
                    this.changeData(this.directoryTree[directoryTreeKey]);
                }*/
                this.detectChanges();
            }
        });

        this.selectedData$.subscribe(value => {
            this.selectedData = value;
            this.selectedDataTemp = value;
            this.detectChanges();
        });

        this.selectedFiles$.subscribe(value => {
            this.fileBase.selectedFiles = value;
            this.fileBaseTemp.selectedFiles = value;
            this.detectChanges();
        });

        this.textFilesData = DataTables.textFilesData;
        this.pltColumns = DataTables.PltDataTables;
        this.importedFiles = DataTables.importedFiles;
        this.loadingFiles = false;
        this.loadingPlts = false;
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

    toggleDelete(rowData, i) {
        this.deletePopUp = !this.deletePopUp;
        this.DataToDelete = rowData;
        this.IndiceToDelete = i;
    }

    patchState({wsIdentifier, data}: any): void {
        this.workspaceInfo = data;
        this.wsIdentifier = wsIdentifier;
    }

    pinWorkspace() {
        this.dispatch([new fromHeader.TogglePinnedWsState({
            "userId": 1,
            "workspaceContextCode": this.workspaceInfo.wsId,
            "workspaceUwYear": this.workspaceInfo.uwYear
        })]);
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
            //this.dispatch(new fromWs.ToggleFilesAction({index, selection: event, scope: 'single'}));
            this.selectedDataTemp[index].selected = true;
            this.checkIndeterminateFile();
        } else if (scope === 'plt') {
            this.dispatch(new fromWs.TogglePltsAction({index, selection: event, scope: 'single'}));
            //this.fileBase.selectedFiles[index].selected=true;
            this.checkIndeterminatePlt();
            this.status = 0;
        }
    }

    checkIndeterminateFile() {
        const filtredFiles = _.filter(this.fileBase.files, item => item.selected).length;
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
        const filtred = _.filter(this.fileBase.selectedFiles, item => item.selected).length;
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
            //this.dispatch(new fromWs.ToggleFilesAction({index, selection: true, scope: 'single'}));
            this.selectedDataTemp[index].selected = true;
            this.indexImportFiles = index;
        } else if ((window as any).event.shiftKey) {
            event.preventDefault();
            if (this.indexImportFiles || this.indexImportFiles === 0) {
                this._selectSection(Math.min(index, this.indexImportFiles), Math.max(index, this.indexImportFiles), 'files');
            } else {
                //this.dispatch(new fromWs.ToggleFilesAction({index, selection: true, scope: 'single'}));
                this.selectedDataTemp[index].selected = true;
            }
        } else {
            //this.dispatch(new fromWs.ToggleFilesAction({selection: false, scope: 'all'}));
            //this.dispatch(new fromWs.ToggleFilesAction({index, selection: true, scope: 'single'}));
            for (let key in this.selectedDataTemp) {
                this.selectedDataTemp[key].selected = false;
            }
            this.selectedDataTemp[index].selected = true;
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
        this.toggleDelete(null, null);
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
                //this.dispatch(new fromWs.ToggleFilesAction({selection: true, scope: 'all'}));
                for (let key in this.selectedDataTemp) {
                    this.selectedDataTemp[key].selected = true;
                }
            } else {
                this.allCheckedImportedFiles = true;
                //this.dispatch(new fromWs.ToggleFilesAction({selection: false, scope: 'all'}));
                for (let key in this.selectedDataTemp) {
                    this.selectedDataTemp[key].selected = false;
                }
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
        console.log('Node select', event);
        this.nodePath = '/' + event.node.label;
        this.nodeTemp = event.node;
        while (this.nodeTemp.parent) {
            this.nodePath = '/' + this.nodeTemp.parent.label + this.nodePath;
            this.nodeTemp = this.nodeTemp.parent;
        }
        this.dispatch(new fromWs.LoadFileBasedFilesAction(this.nodePath));
        console.log('directoryTree Apres', this.directoryTree);
        this.selectedData$.subscribe(value => {
            this.selectedData = value;
            this.selectedDataTemp = value;
            for (let key in this.selectedData) {
                const {label}=this.selectedData[key];
                console.log('Here is label', label);
                if(label.match('\\\\'))
                    this.selectedData[key].label = _.last(label.split('\\\\'));
                else if(label.match('/'))
                    this.selectedData[key].label= _.last(label.split('/'));
            }
            this.detectChanges();
        });
        this.fileBase$.subscribe(value => {
            this.fileBase.files = this.selectedData;
            this.detectChanges();
        });
        this.allCheckedImportedFiles = false;
        this.indeterminateImportedFiles = false;
        this.loadingFiles = false;
        console.log('LFiles', this.loadingFiles);
        console.log('directory tree apres selection', this.directoryTree);
    }

    nodeUnselect(event) {
        this.nodePath = null;
    }

    addForImport() {
        this.loadingPlts = true;
        this.dispatch(new fromWs.AddFileForImportAction(this.nodePath));
        this.loadingPlts = false;
        this.status = 0;
        //this.selectedPlt = this.fileBase.selectedFiles[0];
    }

    runImport() {
        this.fileBaseTemp.selectedFiles.forEach(item => {
            if (item.selected) {
                this.status = 0;
            }
        });
        this.dispatch(new fromWs.LaunchFileBasedImportAction());
        this.fileBaseTemp.selectedFiles.forEach(item => {
            if (item.selected) {
                this.status = 100;
            }
        });
    }

    getColor(RP) {
        return _.filter(this.peril, item => item.content === RP)[0].color;
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


    onSort($event: any) {
        const {} = $event;
        console.log('test');
    }

    sortChange(field: any, sortCol: any) {
        if (!sortCol) {
            this.sortData = ({...this.sortData, [field]: 'asc'});
        } else if (sortCol === 'asc') {
            this.sortData = ({...this.sortData, [field]: 'desc'});
        } else if (sortCol === 'desc') {
            this.sortData = ({...this.sortData, [field]: null});
        }
    }

    resetSort() {
        this.sortData = [];
        //this.table.reset();
    }

    resetFilter() {
        this.filterTable = [];
        this.table.reset();
    }

    resetSortFilter() {
        this.sortData = [];
        this.filterTable = [];
        this.table.reset();
    }

    unselectAll() {
        for (this.index = 0; this.index < this.fileBase.files.length; this.index++) {
            this.fileBase.files[this.index].selected = false;
        }
    }


    changeData(object: any) {

        object.forEach(o => {
            if (o.hasOwnProperty('label')) {
                o.children = o.label.children;
                o.leaf = o.label.leaf;
                o.partialSelected = o.label.partialSelected;
                o.root = o.label.root;
                o.selected = o.label.selected;
                if(o.label.data.file.match('\\\\'))
                    o.label = _.last(o.label.data.file.split('\\'));
                else if(o.label.data.file.match('/')){
                    o.label = _.last(o.label.data.file.split('/'));
                }
            } else {
                //o.label = o.data.file.split('\\')[o.data.file.split('\\').length - 1];
                if(o.data.file.match('\\\\'))
                    o.label = _.last(o.data.file.split('\\'));
                else if(o.data.file.match('/')){
                    o.label = _.last(o.data.file.split('/'));
                }
                o.collapsedIcon = 'fa fa-folder';
                o.expandedIcon = 'fa fa-folder-open';
                o.data = 'folder';
            }
            this.changeData(o.children)
        });

    }

    /*changeData(object: any) {

        object.forEach(o => {
            if(o.hasOwnProperty('label')) {
                o.children = o.label.children;
                o.leaf = o.label.leaf;
                o.partialSelected = o.label.partialSelected;
                o.root = o.label.root;
                o.selected = o.label.selected;
                o.label = o.label.data.file.split('\\')[o.label.data.file.split('\\').length-1];
            } else {
                o.label = o.data.file.split('\\')[o.data.file.split('\\').length-1];
                o.collapsedIcon = 'fa fa-folder';
                o.expandedIcon = 'fa fa-folder-open';
                o.data = 'folder';
            }
            this.changeData(o.children)
        });

    }*/

    onFilterFile(value, field) {
        this.selectedDataTemp = this.selectedData.filter(o => o[field].toUpperCase().search(value.toUpperCase()) != -1);
    }

    onFilterPLT(value, field) {
        this.fileBaseTemp.selectedFiles = this.fileBase.selectedFiles.filter(o => o[field].toUpperCase().search(value.toUpperCase()) != -1);
    }

}