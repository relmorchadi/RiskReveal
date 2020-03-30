import {ChangeDetectorRef, Component, Input, Output, OnInit, EventEmitter} from '@angular/core';
import {BaseContainer} from "../../../../shared/base";
import {Select, Store} from "@ngxs/store";
import {Router} from "@angular/router";
import {WorkspaceState} from "../../../store/states";

@Component({
  selector: 'app-attach-plts-pop-up',
  templateUrl: './attach-plts-pop-up.component.html',
  styleUrls: ['./attach-plts-pop-up.component.scss']
})
export class AttachPltsPopUpComponent extends BaseContainer implements OnInit {
  @Output('closePopUp') closePopUp: any = new EventEmitter<any>();

  @Input()
  isVisible;

  wsIdentifier;
  workspace;
  projects;

  @Select(WorkspaceState.getProjects) projects$;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  patchState({wsIdentifier, data}: any): void {
    this.projects = data.projects;
    this.workspace = data;
    this.wsIdentifier = wsIdentifier;
  }

  ngOnInit() {
    this.projects$.pipe().subscribe(data => {
      this.projects = data;
      this.detectChanges();
    })
  }

  onShow() {

  }

  onHide() {
   this.closePopUp.emit();
  }

  dispatchAttachTable() {

  }

  filterByProject(projectId) {

  }

  toDate(date) {
    return new Date(date);
  }
}
