import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef} from '@angular/core';
import {GeneralConfigState} from '../../../core/store/states';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import {Data} from '../../../core/model/data';
import * as moment from 'moment';
import {dashData} from '../../../shared/data/dashboard-data';
import {OpenWS} from '../../../workspace/store/actions';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {WsApi} from '../../../workspace/services/api/workspace.api';
import {WorkspaceState} from '../../../workspace/store/states';

@Component({
  selector: 'app-fac-widget',
  templateUrl: './fac-widget.component.html',
  styleUrls: ['./fac-widget.component.scss']
})
export class FacWidgetComponent implements OnInit {
  @Output('delete') delete: any = new EventEmitter<any>();
  @Output('duplicate') duplicate: any = new EventEmitter<any>();
  @Output('changeName') changeName: any = new EventEmitter<any>();
  @Output('managePopUp') openPopUp: any = new EventEmitter<any>();

  private dropdown: NzDropdownContextComponent;

  @Input()
  itemWidget: any;
  @Input()
  dashboard: any;
  @Input()
  data: any;

  identifier: number;
  itemName: any;
  widgetId: any;
  dashCols: any;
  persisted: any;
  newDashboard: any;
  editName = false;
  tabIndex = 1;
  filters = {};
  newSort = {};
  globalSort = {};

  constructor(private nzDropdownService: NzDropdownService, private store: Store,
              private cdRef: ChangeDetectorRef,
              private wsApi: WsApi) {
  }

  ngOnInit() {
    this.newDashboard = this.dashboard;
    this.identifier = this.itemWidget.id;
    this.widgetId = this.itemWidget.widgetId;
    this.itemName = this.itemWidget.name;
    this.dashCols = this.itemWidget.columns;
  }

  selectTab(index) {
    this.tabIndex = index;
  }

  openFacItem(event) {
    this.store.dispatch(new workspaceActions.OpenWS({
      wsId: event.contractName,
      uwYear: event.uwYear,
      route: 'projects',
      type: 'FAC',
      carSelected: event.projectId
    }))
  }

  filterAssign() {
    return _.filter(this.data, item => item.assignedAnalyst === 'Nathalie Dulac');
  }

  valueFavChange(event) {

  }

  openPopUpAction(scope) {
    this.openPopUp.emit(scope);
  }

  sortChange(event) {
    this.widgetId === 1 ? this.newSort = event : this.globalSort = event;
  }

  filterData($event) {
    if ($event[_.keys($event)[0]]) {
      this.filters =  _.merge({}, this.filters, $event) ;
    } else {
      this.filters =  _.omit(this.filters, _.keys($event)[0]);
    }
  }

  duplicateItem(): void {
    this.duplicate.emit(this.itemWidget.id);
  }

  deleteItem(): void {
    this.delete.emit(this.itemWidget.id);
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  validateName(keyboardMap) {
    if (keyboardMap.key === 'Enter') {
      this.changeName.emit({item: this.itemWidget, newName: keyboardMap.target.value});
      this.editName = false;
    }
  }

  activeEdit() {
    this.editName = true;
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }
}
