import {ChangeDetectorRef, Component, Input, Output, OnInit, EventEmitter} from '@angular/core';
import {BaseContainer} from "../../../../shared/base";
import {Select, Store} from "@ngxs/store";
import {Router} from "@angular/router";
import {WorkspaceState} from "../../../store/states";
import * as _ from "lodash";
import {
  LoadScopeCompletenessDataSuccess,
  SelectScopeProject, LoadScopePLTsData,
  PatchScopeOfCompletenessState
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
  workspaceType: any;
  scopeContext;
  wsIdentifier;
  projectType: any;
  workspace;
  columns = [];

  @Select(WorkspaceState.getScopeProjects) projects$;
  projects;
  @Select(WorkspaceState.getScopePLTs) plts$;
  plts;
  @Select(WorkspaceState.getCurrentWS) currentWs$;
  currentWs;
  @Select(WorkspaceState.getSelectedProject) selectedProject$;

  @Select(WorkspaceState.getScopeCompletenessData) scopeData$;
  scopeData;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  patchState({wsIdentifier, data}: any): void {}

  ngOnInit() {
    this.projects$.pipe().subscribe(data => {
      this.projects = data;
      this.dispatch(new LoadScopePLTsData());
      this.detectChanges();
    });

    this.plts$.pipe().subscribe(data => {
      this.plts = data;
      console.log(this.plts, this.columns);
      this.detectChanges();
    });

    this.selectedProject$.pipe().subscribe(value => {
      this.projectType = _.get(value, 'projectType', 'FAC');
      this.detectChanges();
    });

    this.currentWs$.pipe().subscribe(value => {
      this.currentWs =  value;
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

  initColumns() {
    this.columns = [
      {field: 'selection', type: 'boolean', width: '40px', visible: true},
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
          this.columns = [...this.columns, {field: '', type: 'boolean', width: '80px', visible: true, topHeader: `Division N°${index + 1}`, columnContext: item.id}]
        });
      }
    } else {
      _.forEach(this.currentWs.treatySection, item => {
        this.columns = [...this.columns, {field: '', type: 'boolean', width: '80px', visible: true, topHeader: item}]
      });
    }
  }

  onShow() {

  }

  onHide() {
   this.closePopUp.emit();
  }

  dispatchAttachTable() {

  }

  filterByProject(projectId) {
    this.dispatch(new SelectScopeProject({projectId}));
  }

  attachable(row) {
    let attachable = false;
    _.forEach(this.scopeData.regionPerils, item => {
      if(item.id === row.perilCode) {
        _.forEach(item.targetRaps, itemTR => {
          if (itemTR.id === row.accumulationRAPCode) {
            attachable = true;
          }
        })
      }
    });
    return attachable
  }

  showApplicablePltsFunction(event) {

  }

  unableAttachment() {
    return _.includes(_.values(this.selectedForAttachment), true);
  }

  toDate(date) {
    return new Date(date);
  }
}
