import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from "lodash";

@Component({
  selector: 'app-calibration-main-table',
  templateUrl: './calibration-main-table.component.html',
  styleUrls: ['./calibration-main-table.component.scss']
})
export class CalibrationMainTableComponent implements OnInit {
  templateWidth = '130';
  pltColumns = [
    {
      sortDir: 1,
      fields: 'checkbox',
      header: '',
      width: '43',
      dragable: false,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'checkbox',
      style: 'border: none !important',
      extended: true,
      frozen: true,
    },
    {
      sortDir: 1,
      fields: 'userTags',
      header: 'User Tags',
      width: '80',
      dragable: false,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'checkbox',
      style: 'border: none !important',
      extended: true,
      frozen: true,
    },
    {
      sortDir: 1,
      fields: 'pltId',
      header: 'PLT ID',
      width: '80',
      dragable: false,
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true,
      frozen: true,
    },
    {
      sortDir: 1,
      fields: 'pltName',
      header: 'PLT Name',
      width: '150',
      dragable: false,
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true,
      frozen: true
    },
    {
      sortDir: 1,
      fields: 'peril',
      header: 'Peril',
      width: '80',
      dragable: false,
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'regionPerilCode',
      header: 'Region Peril Code',
      width: '80',
      dragable: false,
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'regionPerilName',
      header: 'Region Peril Name',
      width: '130',
      dragable: false,
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'grain',
      header: 'Grain',
      width: '160',
      dragable: false,
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '90',
      dragable: false,
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'rap',
      header: 'RAP',
      width: '70',
      dragable: false,
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      style: 'border: none !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'action',
      header: '',
      width: '25',
      dragable: false,
      sorted: false,
      filtred: false,
      icon: 'icon-focus-add',
      type: 'icon',
      style: 'border: none !important',
      extended: true,
      frozen: true
    },
    {
      sortDir: 1,
      fields: 'action',
      header: '',
      width: '25',
      dragable: false,
      sorted: false,
      filtred: false,
      icon: 'icon-note',
      type: 'icon',
      style: 'border: none !important',
      extended: true,
      frozen: true
    },
    {
      sortDir: 1,
      fields: 'overallLMF',
      header: 'Overall LMF',
      width: '60',
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true,
      frozen: true
    },
    {
      sortDir: 1,
      fields: 'base',
      header: 'Base',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'default',
      header: 'Default',
      width: '60',
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'client',
      header: 'Client',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'inuring',
      header: 'Inuring',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true,
      frozen: false
    },
    {
      sortDir: 1,
      fields: 'postInuring',
      header: 'Post-Inuring',
      width: this.templateWidth,
      dragable: true,
      sorted: false,
      filtred: false,
      icon: null,
      type: 'field',
      style: 'border: 1px solid rgba(0, 0, 0, 0.075) !important',
      extended: true,
      frozen: false
    },
  ];
  @Input() tableType;
  dataColumns;
  frozenColumns;
  frozenWidth;
  EPMColumns;
  @Input() showDeleted;
  @Input() deletedPlts;
  @Input() listOfPltsData;
  @Input() sortData;
  @Input() filterData;
  @Input() filters;
  @Input() cm;
  @Input() extended;
  @Input() addRemoveModal;
  @Input() selectedListOfPlts;
  @Input() genericWidth;
  @Input() adjsArray;
  @Input() shownDropDown;
  @Input() selectedAdjustment;
  @Input() dropdownVisible;
  @Input() selectAll;
  @Input() someItemsAreSelected;
  @Input() inProgressCheckbox;
  @Input() checkedCheckbox;
  @Input() lockedCheckbox;
  @Input() requiresRegenerationCheckbox;
  @Input() failedCheckbox;
  @Input() selectedItemForMenu;
  @Input() adjutmentApplication;
  @Input() draggedAdjs;
  @Input() dragPlaceHolderId;
  @Input() dragPlaceHolderCol;
  @Input() selectedEPM;

  @Output('onSort') onSortEmitter: EventEmitter<any> = new EventEmitter();
  @Output('extend') extendEmitter: EventEmitter<any> = new EventEmitter();
  @Output('clickButtonPlus') clickButtonPlusEmitter: EventEmitter<any> = new EventEmitter();
  @Output('onLeaveAdjustment') onLeaveAdjustmentEmitter: EventEmitter<any> = new EventEmitter();
  @Output('selectedAdjust') selectedAdjustEmitter: EventEmitter<any> = new EventEmitter();
  @Output('dropThreadAdjustment') dropThreadAdjustmentEmitter: EventEmitter<any> = new EventEmitter();
  @Output('ModifyAdjustement') ModifyAdjustementEmitter: EventEmitter<any> = new EventEmitter();
  @Output('applyToSelected') applyToSelectedEmitter: EventEmitter<any> = new EventEmitter();
  @Output('applyToAll') applyToAllEmitter: EventEmitter<any> = new EventEmitter();
  @Output('replaceToSelectedPlt') replaceToSelectedPltEmitter: EventEmitter<any> = new EventEmitter();
  @Output('replaceToAllAdjustement') replaceToAllAdjustementEmitter: EventEmitter<any> = new EventEmitter();
  @Output('onChangeAdjValue') onChangeAdjValueEmitter: EventEmitter<any> = new EventEmitter();
  @Output('checkAll') checkAllEmitter: EventEmitter<any> = new EventEmitter();
  @Output('checkBoxsort') checkBoxsortEmitter: EventEmitter<any> = new EventEmitter();
  @Output('sortChange') sortChangeEmitter: EventEmitter<any> = new EventEmitter();
  @Output('filter') filterEmitter: EventEmitter<any> = new EventEmitter();
  @Output('onDrop') onDropEmitter: EventEmitter<any> = new EventEmitter();
  @Output('handlePLTClick') handlePLTClickEmitter: EventEmitter<any> = new EventEmitter();
  @Output('selectSinglePLT') selectSinglePLTEmitter: EventEmitter<any> = new EventEmitter();
  @Output('statusFilterActive') statusFilterActiveEmitter: EventEmitter<any> = new EventEmitter();


  constructor() {
  }

  ngOnInit() {
  }

  initDataColumns() {
    this.dataColumns = [];
    this.frozenColumns = [];
    if (this.extended) {
      if (this.tableType == 'adjustments') {
        _.forEach(this.pltColumns, (value, key) => {
          if (value.extended) {
            this.dataColumns.push(value);
          }
        });
      } else {
        _.forEach(this.EPMColumns, (value, key) => {
          if (value.extended) {
            this.dataColumns.push(value);
          }
        });
      }

    } else {
      if (this.tableType == 'adjustments') {
        _.forEach(this.pltColumns, (value, key) => {
          if (value.extended && !value.frozen) {
            this.dataColumns.push(value);
          }
          if (value.extended && value.frozen) {
            this.frozenColumns.push(value);
          }
        });
      } else {
        _.forEach(this.EPMColumns, (value, key) => {
          if (value.extended && !value.frozen) {
            this.dataColumns.push(value);
          }
          if (value.extended && value.frozen) {
            this.frozenColumns.push(value);
          }
        });
      }
    }
  }

  onSort($event: any) {

  }

  extend() {

  }

  clickButtonPlus(b: boolean) {

  }

  onLeaveAdjustment(id: any) {

  }

  selectedAdjust(id: any) {

  }

  dropThreadAdjustment() {

  }

  ModifyAdjustement(adj) {

  }

  DeleteAdjustement(adj) {

  }

  applyToSelected(adj) {

  }

  applyToAll(adj) {

  }

  replaceToSelectedPlt(adj) {

  }

  replaceToAllAdjustement(adj) {

  }

  onChangeAdjValue(adj, $event: Event) {

  }

  checkAll($event: boolean) {

  }

  checkBoxsort() {

  }

  sortChange(fields: any, sortDatum: any) {

  }

  filter(fields: any, value: any) {

  }

  onDrop(col, pltId: any) {

  }

  handlePLTClick(pltId: any, rowIndex, $event: MouseEvent) {

  }

  selectSinglePLT(pltId: any, $event: boolean) {

  }

  statusFilterActive(status: any) {
    return false;
  }

}
