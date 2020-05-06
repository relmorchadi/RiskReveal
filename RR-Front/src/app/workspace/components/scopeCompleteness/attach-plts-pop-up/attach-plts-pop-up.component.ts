import {ChangeDetectorRef, Component, Input, Output, OnInit, EventEmitter} from '@angular/core';
import {BaseContainer} from "../../../../shared/base";
import {Select, Store} from "@ngxs/store";
import {Router} from "@angular/router";
import {WorkspaceState} from "../../../store/states";
import * as _ from "lodash";
import {
  LoadScopeCompletenessData,
  SelectScopeProject, LoadScopePLTsData,
  AttachPLTsForScope
} from "../../../store/actions";

@Component({
  selector: 'app-attach-plts-pop-up',
  templateUrl: './attach-plts-pop-up.component.html',
  styleUrls: ['./attach-plts-pop-up.component.scss']
})
export class AttachPltsPopUpComponent extends BaseContainer implements OnInit {
  @Output('closePopUp') closePopUp: any = new EventEmitter<any>();

  @Input()
  isVisible;

  selectedForAttachment: any = {};
  showApplicablePlts = false;
  selectedProject: any;
  workspaceType: any;
  selectedPLTs: any = [];
  scopeContext;
  wsIdentifier;
  filteredData = {};
  projectType: any;
  workspace;
  sortData = {};
  columns = [];

  @Select(WorkspaceState.getScopeProjects) projects$;
  projects;
  @Select(WorkspaceState.getScopePLTs) plts$;
  plts;
  @Select(WorkspaceState.getCurrentWS) currentWs$;
  currentWs;
  @Select(WorkspaceState.getSelectedProject) selectedProject$;

  @Select(WorkspaceState.getScopeCompletenessPendingData) pendingData$;
  pendingData;
  @Select(WorkspaceState.getScopeCompletenessData) scopeData$;
  scopeData;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  patchState({wsIdentifier, data}: any): void {}

  ngOnInit() {
    this.projects$.pipe().subscribe(data => {
      if(this.projects !== data) {
        this.projects = data;
      }
      this.detectChanges();
    });

    this.plts$.pipe().subscribe(data => {
      this.plts = data;
      this.detectChanges();
    });

    this.selectedProject$.pipe().subscribe(value => {
      this.projectType = _.get(value, 'projectType', 'FAC');
      this.detectChanges();
    });

    this.pendingData$.pipe().subscribe(value => {
      this.pendingData =  value;
      this.detectChanges();
    });

    this.currentWs$.pipe().subscribe((value: any) => {
      this.currentWs =  value;
      this.wsIdentifier = value.wsId + '-' + value.uwYear;
      this.selectedProject = _.find(value.projects, item => item.selected);
      this.workspaceType = _.get(value, 'workspaceType', 'fac');
      this.detectChanges();
    });

    this.scopeData$.pipe().subscribe(value => {
      this.scopeData = value;
      this.scopeContext = _.get(value, 'scopeContext', null);
      if(this.scopeContext !== null) {
        this.initColumns();
      }
      this.detectChanges();
    });
  }

  initAttachedPLTs() {
    if (this.selectedProject !== undefined) {
      this.dispatch(new SelectScopeProject({projectId: this.selectedProject.projectId}));
    }
    this.selectedPLTs = [];
    this.selectedForAttachment = {};
    _.forEach(this.pendingData.regionPerils, item => {
      _.forEach(item.targetRaps, itemTR => {
        _.forEach(_.toArray(itemTR.pltsAttached), plt => {
          _.forEach(plt.scope, pltScope => {
            const pltScopeIndex = _.findIndex(this.scopeContext, (scope: any) => scope.id === pltScope) + 1;
            this.selectedForAttachment[plt.pltHeaderId + pltScope] = true;
            this.selectedPLTs = [...this.selectedPLTs, {...plt, scopeIndex: pltScopeIndex, scope: pltScope}];
            console.log(this.selectedPLTs);
          });
        })
      })
    });
  }

  initColumns() {
    this.columns = [
      {field: 'selection', type: 'boolean', width: '40px', visible: true, selection: 'line'},
      {field: 'pltHeaderId', header: 'PLT ID', type: 'text', width: '80px', visible: true},
      {field: 'contractSectionId', header: 'Contract Section ID', type: 'text', width: '60px', visible: true},
      {field: 'defaultPltName', header: 'PLT Name', type: 'text', width: '100px', visible: true},
      {field: 'perilCode', header: 'Peril Code', type: 'text', width: '60px', visible: true},
      {field: 'accumulationRapCode', header: 'Accumulation Rap Code', type: 'text', width: '100px', visible: true},
      {field: 'regionPerilCode', header: 'Region Peril Code', type: 'text', width: '60px', visible: true},
      {field: 'regionPerilDesc', header: 'Region Peril Description', type: 'text', width: '80px', visible: true},
    ];
    if (this.workspaceType === 'fac') {
      if(this.projectType === 'FAC') {
        _.forEach(this.scopeContext, (item, index) => {
          this.columns = [...this.columns, {field: '', type: 'boolean', width: '80px', visible: true, topHeader: `Division N°${index + 1}`, id: item.id, selection: 'single', index: index + 1}]
        });
      }
    } else {
      _.forEach(this.currentWs.treatySection, item => {
        this.columns = [...this.columns, {field: '', type: 'boolean', width: '80px', visible: true, topHeader: item, selection: 'single'}]
      });
    }
  }

  onShow() {
   this.initAttachedPLTs();
  }

  onHide() {
   this.closePopUp.emit();
  }

  dispatchAttachTable() {
    this.dispatch(new AttachPLTsForScope({plts: this.selectedPLTs}));
    this.onHide();
  }

  filterByProject(projectId) {
    this.dispatch(new SelectScopeProject({projectId}));
  }

  attachable(row, col) {
    let attachable = false;
    _.forEach(this.pendingData.regionPerils, item => {
      if(item.id === row.regionPerilCode) {
        _.forEach(item.targetRaps, itemTR => {
          const override = _.get(itemTR, `override.${col.index}.overridden`, false);
          if (itemTR.id === row.accumulationRapCode && !override) {
            if (_.includes(itemTR.scopeContext, col.id)) {
              attachable = true;
            }
          }
        })
      }
    });
    return attachable;
  }

  attachableMulti(rowData) {
    return _.includes(_.map(this.scopeContext, (scope, index) => this.attachable(rowData, {...scope, index: index + 1})), true)
  }

  filterData() {
    return this.showApplicablePlts ? _.filter(this.plts, item => {
      return _.includes(_.map(this.scopeContext, (scope, index) => this.attachable(item, {...scope, index: index + 1})), true);
    }) : this.plts;
  }

  selectPLTByDiv(rowData, col, event) {
    this.selectedForAttachment[rowData.pltHeaderId + col.id] = event;
    const scopeIndex = _.findIndex(this.scopeContext, (item: any) => item.id === col.id) + 1;
    if (event) {
      this.selectedPLTs = [...this.selectedPLTs, {...rowData, scope: col.id, scopeIndex: scopeIndex}];
    } else {
      this.selectedPLTs = _.filter(this.selectedPLTs, item => item.pltHeaderId !== rowData.pltHeaderId || item.scope !== col.id);
    }
  }

  selectPLTByRow(rowData, event) {
    _.forEach(this.scopeContext, (scope, index) => {
      if (this.attachable(rowData, {...scope, index: index + 1})) {
        this.selectedForAttachment[rowData.pltHeaderId + scope.id] = event;
        if (event) {
          this.selectedPLTs = [...this.selectedPLTs, {...rowData, scope: scope.id, scopeIndex: index + 1}];
        } else {
          this.selectedPLTs = _.filter(this.selectedPLTs, item => item.pltHeaderId !== rowData.pltHeaderId || item.scope !== scope.id);
        }
      }
    });
  }

  selectPLTByRowNC(rowData, event) {
    _.forEach(this.scopeContext, (scope, index) => {
      this.selectedForAttachment[rowData.pltHeaderId + scope.id] = event;
      if (event) {
        this.selectedPLTs = [...this.selectedPLTs, {...rowData, scope: scope.id, scopeIndex: index + 1}];
      } else {
        this.selectedPLTs = _.filter(this.selectedPLTs, item => item.pltHeaderId !== rowData.pltHeaderId || item.scope !== scope.id);
      }
    });
  }

  checkedRow(rowData) {
    let checked = true;
    _.forEach(this.scopeContext, (scope, index) => {
      const possibleToAttach = this.attachable(rowData, {...scope, index: index + 1});
      if (possibleToAttach) {
        const attachable = _.get(this.selectedForAttachment, `${rowData.pltHeaderId}${scope.id}`, false);
        if (!attachable) {
          checked = false;
        }
      }
    });
    return checked;
  }

  checkedRowNC(rowData) {
    let checked = true;
    _.forEach(this.scopeContext, (scope, index) => {
      const attachable = _.get(this.selectedForAttachment, `${rowData.pltHeaderId}${scope.id}`, false);
      if (!attachable) {
        checked = false;
      }
    });
    return checked;
  }

  unableAttachment() {
    return _.includes(_.values(this.selectedForAttachment), true);
  }

  sortChange(field: any, sortCol: any) {
    if (!sortCol) {
      this.sortData = _.merge({}, this.sortData, {[field]: 'asc'});
    } else if (sortCol === 'asc') {
      this.sortData =_.merge({}, this.sortData, {[field]: 'desc'});
    } else if (sortCol === 'desc') {
      this.sortData = _.omit(this.sortData, `${field}`);
    }
  }

  filterChange(field: any, filterValue: any) {
    if (_.isEmpty(_.trim(filterValue))) {
      this.filteredData =_.omit( this.filteredData, `${field}`);
    } else {
      this.filteredData = _.merge({}, this.filteredData, {[field]: filterValue});
      console.log(this.filteredData);
    }
  }

  toDate(date) {
    return new Date(date);
  }
}
