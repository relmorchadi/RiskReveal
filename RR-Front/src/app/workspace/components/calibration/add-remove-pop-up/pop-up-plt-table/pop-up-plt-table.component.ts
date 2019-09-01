import {Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild} from '@angular/core';
import * as _ from "lodash";

@Component({
  selector: 'app-pop-up-plt-table',
  templateUrl: './pop-up-plt-table.component.html',
  styleUrls: ['./pop-up-plt-table.component.scss']
})
export class PopUpPltTableComponent implements OnInit {

  @ViewChild('cm') cm: TemplateRef<any>;

  @Input() tableInputs: {
    filterInput: string,
    contextMenuItems: any,
    pltColumns: any[];
    listOfPltsData: [];
    listOfDeletedPltsData: [];
    listOfPltsCache: [];
    listOfDeletedPltsCache: [];
    listOfPlts: [];
    listOfDeletedPlts: [];
    selectedListOfPlts: any;
    selectedListOfDeletedPlts: any;
    selectAll: boolean;
    someItemsAreSelected: boolean;
    showDeleted: boolean;
    filterData: any;
    filters: {
      systemTag: [],
      userTag: []
    };
    sortData: any;
  };
  contextMenuItems = [
    {
      label: 'View Detail', command: (event) => {
        this.openPltInDrawer.emit(this.contextMenuSelect.pltId);
      }
    },
    {
      label: 'Delete', command: (event) =>
        this.deletePlt.emit()
    },
    {
      label: 'Edit Tags', command: (event) =>
        this.editTags.emit()
    },
    {
      label: 'Restore',
      command: () => this.restore.emit()
    }
  ];
  contextMenuSelect: any;
  contextMenuItemsCache = this.contextMenuItems;
  @Output() editTags = new EventEmitter();
  @Output() deletePlt = new EventEmitter();
  @Output() restore = new EventEmitter();
  @Output() openPltInDrawer = new EventEmitter();
  @Output() onCheckAll = new EventEmitter();
  @Output() onCheckBoxSort = new EventEmitter();
  @Output() onSortChange = new EventEmitter();
  @Output() onFilter = new EventEmitter();
  @Output() onItemSelectForMenu = new EventEmitter();
  @Output() onSelectSinglePlt = new EventEmitter();
  @Output() onPltClick = new EventEmitter();
  @Output() setTagModalVisibility = new EventEmitter();
  @Input('listOfPltsThread') listOfPltsThread: any;

  private activeCheckboxSort: boolean;
  private lastSelectedId: number;
  private lastClick: string;
  private selectAll: any;
  private someItemsAreSelected: any;

  constructor() {
  }

  ngOnInit() {
  }

  toggleSelectPlts(plts: any) {
    this.onPltClick.emit(plts);
  }
  checkAll($event) {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(this.listOfPltsThread, plt => plt.pltId),
        _.range(this.listOfPltsThread.length).map(plt => ({calibrate: $event}))
      )
    );
  }

  checkBoxSort() {
    this.activeCheckboxSort = !this.activeCheckboxSort;
    this.onCheckBoxSort.emit(this.activeCheckboxSort ?
      _.sortBy(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData
        :
        this.tableInputs.listOfDeletedPltsData, [(o: any) => !o.selected]) : !this.tableInputs.showDeleted ? this.tableInputs.listOfPltsCache : this.tableInputs.listOfDeletedPltsCache)
  }

  sortChange(field: any, sortCol: any) {
    if (!sortCol) {
      this.onSortChange.emit(_.merge({}, this.tableInputs.sortData, {[field]: 'asc'}))
    } else if (sortCol === 'asc') {
      this.onSortChange.emit(_.merge({}, this.tableInputs.sortData, {[field]: 'desc'}))
    } else if (sortCol === 'desc') {
      this.onSortChange.emit(_.omit(this.tableInputs.sortData, `${field}`))
    }
  }

  filter(key: string, value) {
    if (value) {
      this.onFilter.emit(_.merge({}, this.tableInputs.filterData, {[key]: value}))
    } else {
      this.onFilter.emit(_.omit(this.tableInputs.filterData, [key]))
    }
  }

  selectedItemForMenu(pltId: any) {
    this.onItemSelectForMenu.emit(pltId);
  }

  selectSinglePLT(pltId: number, $event: boolean) {
    this.toggleSelectPlts({
      [pltId]: {
        calibrate: $event
      }
    });
  }

  generateContextMenu(toRestore) {
    this.contextMenuItems = _.filter(this.contextMenuItemsCache, e => e.label != (!toRestore ? 'Restore' : 'Delete'))
  }

  handlePLTClick(pltId, i: number, $event: MouseEvent) {
    i = _.findIndex(this.listOfPltsThread, (row: any) => row.pltId == pltId);
    let index = -1;
    let isSelected;
    _.forEach(this.listOfPltsThread, (plt, i) => {
      if (plt.pltId == pltId) {
        isSelected = plt.selected
      }
    });

    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = "withKey";
      this.handlePLTClickWithKey(pltId, i, !isSelected, $event);
    } else {
      this.lastSelectedId = i;
      this.toggleSelectPlts(
        _.zipObject(
          _.map(this.listOfPltsThread, plt => plt.pltId),
          _.map(this.listOfPltsThread, plt => ({calibrate: plt.pltId == pltId && (this.lastClick == 'withKey' || !isSelected)}))
        )
      );
      this.lastClick = null;
    }
  }

  private handlePLTClickWithKey(pltId: number, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      this.selectSinglePLT(pltId, isSelected);
      this.lastSelectedId = i;
      return;
    }

    if ($event.shiftKey) {
      if (!this.lastSelectedId) this.lastSelectedId = 0;
      if (this.lastSelectedId || this.lastSelectedId == 0) {
        const max = _.max([i, this.lastSelectedId]);
        const min = _.min([i, this.lastSelectedId]);
        console.log('min-max ==> ', min, max);
        this.toggleSelectPlts(
          _.zipObject(
            _.map(this.listOfPltsThread, plt => plt.pltId),
            _.map(this.listOfPltsThread, (plt, i: number) => ({calibrate: i <= max && i >= min})),
          )
        );
      } else {
        this.lastSelectedId = i;
      }
      console.log(this.lastSelectedId);
      return;
    }
  }

  trackBy(row, index) {
    return row.pltId;
  }
}
