import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {Select, Store} from "@ngxs/store";
import {WorkspaceState} from "../../../store/states";
import {BaseContainer} from "../../../../shared/base";
import {Router} from "@angular/router";

@Component({
  selector: 'app-scope-table',
  templateUrl: './scope-table.component.html',
  styleUrls: ['./scope-table.component.scss']
})
export class ScopeTableComponent extends BaseContainer implements OnInit {

  @Input()
  dataSource;
  @Input()
  Columns;

  @Select(WorkspaceState.getSelectedProject) selectedProject$;
  selectedProject: any;

  constructor( _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {

  }

  selectedRow(row) {

  }

}
