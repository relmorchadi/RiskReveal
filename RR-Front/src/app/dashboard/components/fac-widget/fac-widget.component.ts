import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {DashboardState, GeneralConfigState} from '../../../core/store/states';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import * as workspaceActions from '../../../workspace/store/actions/workspace.actions';
import {WsApi} from '../../../workspace/services/api/workspace.api';
import {DashboardApi} from "../../../core/service/api/dashboard.api";
import {forkJoin} from "rxjs";
import * as moment from "moment";
import {BaseContainer} from "../../../shared/base";
import {Router} from "@angular/router";
import * as fromHD from "../../../core/store/actions";
import {debounce} from "rxjs/operators";

@Component({
  selector: 'app-fac-widget',
  templateUrl: './fac-widget.component.html',
  styleUrls: ['./fac-widget.component.scss']
})
export class FacWidgetComponent extends BaseContainer implements OnInit {
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

  @Select(DashboardState.getFacData)facData$;
  @Select(DashboardState.getDataCounter)dataCounter$;
  @Select(DashboardState.getVirtualScroll)virtualScroll$;

  inputName: ElementRef;
  @ViewChild('inputName') set assetInput(elRef: ElementRef) {
    this.inputName = elRef;
  };

  virtualScroll: any;

  carStatus = {"1": 'NEW', "2": 'In Progress', "3": 'Archived'};

  identifier: number;
  itemName: any;
  widgetId: any;
  dashCols: any;
  ColsTotalWidth: any = 0;
  newDashboard: any;
  editName = false;
  tabIndex = 1;

  data: any = [];
  dataCounter = 10;
  rows: any;
  loading = false;
  secondaryLoad = false;

  filters = {};
  newSort = {};
  sortList = [];
  globalSort = {};

  constructor(private nzDropdownService: NzDropdownService, private store: Store,
              private cdRef: ChangeDetectorRef, private dashboardAPI: DashboardApi,
              private wsApi: WsApi, _baseRouter: Router, _baseCdr: ChangeDetectorRef, _baseStore: Store) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.newDashboard = this.dashboard;
    this.identifier = this.itemWidget.id;
    this.widgetId = this.itemWidget.widgetId;
    this.itemName = this.itemWidget.name;
    this.dashCols = this.itemWidget.columns;
    _.forEach(this.dashCols, item => {
      this.ColsTotalWidth = this.ColsTotalWidth + item.widthNumber + 5;
    });
    this.loadFacData(1);
    this.sortFirstUpdate();

    this.facData$.pipe().subscribe(value => {
      this.data = _.get(value, `${this.identifier}`, []);
      this.detectChanges();
    });

    this.dataCounter$.pipe().subscribe(value => {
      this.dataCounter = _.get(value, `${this.identifier}`, 10);
      this.detectChanges();
    });

    this.virtualScroll$.pipe().subscribe(value => {
      this.virtualScroll =  _.get(value, `${this.identifier}`, false);
      this.detectChanges();
    });
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
    const carStatus = this.carStatus[this.widgetId];
    this.secondaryLoad = true;
    if (_.isEmpty(event.sortType)) {
      this.dashboardAPI.updateSortCols(event.columnId, 0, '').subscribe(
          () => {}, () => {}, () => {
            this.dispatch(new fromHD.LoadDashboardFacDataAction({identifier: this.identifier, pageNumber: 1, carStatus})).subscribe(
                () => {}, () => {}, () => {
                  this.secondaryLoad = false;
                  this.detectChanges();
                }
            );
          });
    } else {
      _.forEach(this.sortList, (item, key) => {
        this.dashboardAPI.updateSortCols(item.columnId, key + 1, item.sortType)
            .subscribe(() => {}, () => {}, () => {
              this.dispatch(new fromHD.LoadDashboardFacDataAction({identifier: this.identifier, pageNumber: 1, carStatus})).subscribe(
                  () => {}, () => {}, () => {
                    this.secondaryLoad = false;
                    this.detectChanges();
                  }
              );
            });
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
    const carStatus = this.carStatus[this.widgetId];
    this.secondaryLoad = true;
    this.dashboardAPI.updateFilterCols( $event.colId, $event.filteredValue)
        .subscribe(() => {}, () => {}, () => {
          this.dispatch(new fromHD.LoadDashboardFacDataAction({identifier: this.identifier, pageNumber: 1, carStatus})).subscribe(
              () => {}, () => {}, () => {
                this.secondaryLoad = false;
                this.detectChanges();
              }
          );
        });
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
    setTimeout(() => {
      this.inputName.nativeElement.select();
    }, 100);
  }

  loadFacData(pageNumber) {
    this.loading = true;
    const carStatus = this.carStatus[this.widgetId];
    this.dispatch(new fromHD.LoadDashboardFacDataAction({identifier: this.identifier, pageNumber, carStatus}))
        .subscribe(() => {}, ()=> {}, () => {
          this.loading = false;
          this.detectChanges();
        })
  }

  loadMore(event) {
    const carStatus = this.carStatus[this.widgetId];
    const pageNumber =  (event.first / (event.rows / 2)) + 1;
    this.loading = true;
    if (event.rows === 50) {
      this.dispatch(new fromHD.LoadDashboardFacDataAction({identifier: this.identifier, pageNumber, carStatus}))
          .subscribe(() => {}, ()=> {}, () => {
            this.loading = false;
            this.detectChanges();
          });
    }
  }

  changeHeight($event) {
    //this.rows = $event;
    //console.log('rows', this.rows);
  }

  getNumberOfRows() {
    console.log(Math.floor(this.dataCounter / 2));
    return 25 > this.dataCounter ? Math.floor(this.dataCounter / 2) : 25;
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
