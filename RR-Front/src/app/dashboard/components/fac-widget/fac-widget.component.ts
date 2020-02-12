import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, TemplateRef} from '@angular/core';
import {GeneralConfigState} from '../../../core/store/states';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {WsApi} from '../../../workspace/services/api/workspace.api';
import {DashboardApi} from "../../../core/service/api/dashboard.api";
import {forkJoin} from "rxjs";
import * as moment from "moment";

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
  @Output('widgetColsUpdate') updateColsWidth: any = new EventEmitter<any>();

  private dropdown: NzDropdownContextComponent;

  @Input()
  itemWidget: any;
  @Input()
  dashboard: any;

  carStatus = ['Archived', 'Completed', 'Canceled'];

  identifier: number;
  itemName: any;
  widgetId: any;
  dashCols: any;
  persisted: any;
  newDashboard: any;
  editName = false;
  tabIndex = 1;

  data: any = [];
  dataCounter = 0;

  filters = {};
  newSort = {};
  sortList = [];
  globalSort = {};

  constructor(private nzDropdownService: NzDropdownService, private store: Store,
              private cdRef: ChangeDetectorRef, private dashboardAPI: DashboardApi,
              private wsApi: WsApi) {
  }

  ngOnInit() {
    this.newDashboard = this.dashboard;
    this.identifier = this.itemWidget.id;
    this.widgetId = this.itemWidget.widgetId;
    this.itemName = this.itemWidget.name;
    this.dashCols = this.itemWidget.columns;

    this.loadFacData();
    this.sortFirstUpdate();
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
    this.openPopUp.emit({scope, widgetCol: this.dashCols, widgetId: this.itemWidget.id});
  }

  sortChange(event) {
    this.globalSort = event.newSort;
    this.sortList = event.newSortingList;
    if (_.isEmpty(event.sortType)) {
      this.dashboardAPI.updateSortCols(event.columnId, 0, '').subscribe();
    } else {
      _.forEach(this.sortList, (item, key) => {
        this.dashboardAPI.updateSortCols(item.columnId, key + 1, item.sortType).subscribe();
      });
    }
    //this.dashboardAPI.updateSortCols(event.columnId, sortCounter, event.sortType).subscribe();
  }

  sortFirstUpdate() {
    const sort = _.orderBy(_.filter(this.dashCols, item => item.sort !== 0), item => item.sort);
    this.sortList = _.map(sort, item => ({columnId: item.columnId, sortType: item.sortType}));
    _.forEach(sort, item => this.globalSort = _.merge({}, this.globalSort,{[item.field]: item.sortType}));
  }

  filterData($event) {
    this.dashboardAPI.updateFilterCols( $event.colId, $event.filteredValue).subscribe();
  }

  resizeTable($event) {
    const {delta, element: {cellIndex, offsetWidth}} = $event;
    const selectedColumn: any = _.filter(this.dashCols, item => item.display)[cellIndex];
    const {widthNumber, columnId} = selectedColumn;
    const previousWidth = offsetWidth - delta;
    const percentage = delta/previousWidth;
    const newWidth = _.toInteger(widthNumber + widthNumber * percentage);
    this.dashboardAPI.updateColsWidth(columnId, newWidth).subscribe();
    const dashCols = _.map(this.dashCols, item => {return item.columnId === columnId ? {...item,
      width: newWidth + 'px', widthNumber: newWidth} : item});
    this.updateColsWidth.emit({dashCols, widgetId: this.identifier});
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

  loadFacData() {
    const dataParams = {
      entity: 1,
      pageNumber: 1,
      pageSize: 50,
      selectionList: '',
      sortSelectedAction: '',
      sortSelectedFirst: true,
      userCode: "1",
      userDashboardWidgetId: this.identifier
    };

    if (this.widgetId === 3 ) {
      return forkJoin(
          this.carStatus.map(item => this.dashboardAPI.getFacDashboardResources({...dataParams, carStatus: item}))
      ).subscribe((data:any) => {
        // console.log(data);
        data.map(item => {
          this.data = [...this.data, item.content];
          this.dataCounter = this.dataCounter + item.refCount;
        })

      })
    } else {
      return this.dashboardAPI.getFacDashboardResources({...dataParams, carStatus: this.widgetId === 1 ? 'NEW' : 'In Progress'})
          .subscribe( data => {
        this.data = this._formatDate(data.content);
        this.dataCounter = data.refCount;
      })
    }

  }

  private _formatDate(data) {
    return _.map(data, item => {
      const formatCDate = moment(item.creationDate).format('DD-MM-YYYY');
      const formatLDate = moment(item.lastUpdateDate).format('DD-MM-YYYY');
      return {...item, creationDate: formatCDate, lastUpdateDate: formatLDate}
    })
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }
}
