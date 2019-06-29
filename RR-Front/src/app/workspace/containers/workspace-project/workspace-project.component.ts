import {Component, EventEmitter, Input, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {HelperService} from '../../../shared';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from '../../../core/model/workspace-main';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import {PatchWorkspace, SelectProjectAction} from '../../../core/store/actions/workspace-main.action';
import * as moment from 'moment'
import {takeUntil} from "rxjs/operators";
import {StateSubscriber} from "../../model/state-subscriber";
import * as fromHeader from "../../../core/store/actions/header.action";
import * as fromWs from "../../store/actions";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'workspace-project',
  templateUrl: './workspace-project.component.html',
  styleUrls: ['./workspace-project.component.scss']
})
export class WorkspaceProjectComponent implements OnInit, OnDestroy, StateSubscriber {
  actionsEmitter: EventEmitter<any>;
  // unSubscribe$: Subject<void>;
  collapseWorkspaceDetail = true;
  selectedPrStatus = '1';
  private dropdown: NzDropdownContextComponent;
  state: WorkspaceMain = null;
  workspaceUrl: any;
  workspace: any;
  index: any;
  isVisible = false;
  wsIdentifier;
  //@Select(WorkspaceState.getData)
  // data$= of();
  //@Select(Workspacetate.getProjects)
  // projects$= of();


  newProject = false;
  existingProject = false;
  mgaProject = false;
  searchWorkspace = false;

  selectedWs: any;

  receptionDate: any;
  dueDate: any;
  contextMenuProject: any = null;
  description: any;
  newProjectForm: FormGroup;


  constructor(private _helper: HelperService, private route: ActivatedRoute,
              private nzDropdownService: NzDropdownService) {
    console.log('init project');
    this.actionsEmitter = new EventEmitter();
    this.newProjectForm = new FormGroup({
      name: new FormControl(null, Validators.required),
      description: new FormControl(null),
      createdBy: new FormControl(null, Validators.required),
      receptionDate: new FormControl(null, Validators.required),
      dueDate: new FormControl(null, Validators.required),
    });
  }

  patchState({wsIdentifier, data}: any): void {
    this.workspace = data;
    this.wsIdentifier = wsIdentifier;
  }


  ngOnInit() {
    // combineLatest(
    // this.data$,
    // this.route.params
    // )

    // .pipe(takeUntil(this.unSubscribe$))
    // .subscribe(([data, {wsId, year}]: any) => {

    // this.workspaceUrl = {
    //   wsId,
    //   uwYear: year
    // };

    //   this.workspace = _.find(data, dt => dt.workSpaceId == wsId && dt.uwYear == year);
    //   this.index = _.findIndex(data, (dt: any) => dt.workSpaceId == wsId && dt.uwYear == year);
    // });
  }

  handleOk(): void {
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  selectProject(project) {
    this.actionsEmitter.emit(new SelectProjectAction(project));
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspace;
    this.actionsEmitter.emit([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspace;
    this.actionsEmitter.emit([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ])
  }


  delete(project) {
    // this.store.dispatch(new DeleteProject({
    //   workspaceId: this.workspace.workSpaceId, uwYear: this.workspace.uwYear, project,
    // }));
    this.dropdown.close();
  }

  ngOnDestroy(): void {
    // this.unSubscribe$.next();
    // this.unSubscribe$.complete();
  }


}
