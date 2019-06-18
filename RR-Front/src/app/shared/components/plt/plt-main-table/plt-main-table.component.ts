import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';
import * as fromWorkspaceStore from "../../../../workspace/store";

@Component({
  selector: 'app-plt-main-table',
  templateUrl: './plt-main-table.component.html',
  styleUrls: ['./plt-main-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PltMainTableComponent implements OnInit {

  @Input() tableInputs: {
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

  contextMenuSelect: any;

  contextMenuItems = [
    { label: 'View Detail', command: (event) => {
        console.log(this.contextMenuSelect);
        this.openPltInDrawer.emit(this.contextMenuSelect.pltId);
      }},
    { label: 'Delete', command: (event) =>
        this.deletePlt.emit()
    },
     { label: 'Edit Tags', command: (event) =>
         this.editTags.emit()
    },
    {
      label: 'Restore',
      command: () => this.restore.emit()
    }
  ];

  contextMenuItemsCache = this.contextMenuItems;

  @Output() editTags = new EventEmitter();
  @Output() deletePlt = new EventEmitter();
  @Output() restore = new EventEmitter();
  @Output() openPltInDrawer = new EventEmitter();

  @Output() onCheckAll= new EventEmitter();
  @Output() onCheckBoxSort= new EventEmitter();
  @Output() onSortChange= new EventEmitter();
  @Output() onFilter= new EventEmitter();
  @Output() onItemSelectForMenu= new EventEmitter();
  @Output() onSelectSinglePlt= new EventEmitter();
  @Output() onPltClick= new EventEmitter();
  @Output() setTagModalVisibility= new EventEmitter();

  private activeCheckboxSort: boolean;
  private lastSelectedId: number;
  private lastClick: string;

  constructor() {}

  ngOnInit() {
  }

  checkAll($event){
    this.onCheckAll.emit(this.tableInputs.showDeleted);
  }

  checkBoxSort() {
    this.activeCheckboxSort = !this.activeCheckboxSort;
    this.onCheckBoxSort.emit( this.activeCheckboxSort ?
      _.sortBy( !this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData
        :
        this.tableInputs.listOfDeletedPltsData, [(o: any) => !o.selected]) : !this.tableInputs.showDeleted ? this.tableInputs.listOfPltsCache : this.tableInputs.listOfDeletedPltsCache)
  }

  sortChange(field: any, sortCol: any) {
    if(!sortCol){
      this.onSortChange.emit(_.merge({}, this.tableInputs.sortData, { [field]: 'asc'}))
    }else if(sortCol === 'asc'){
      this.onSortChange.emit(_.merge({}, this.tableInputs.sortData, { [field]: 'desc'}))
    } else if(sortCol === 'desc') {
      this.onSortChange.emit(_.omit(this.tableInputs.sortData, `${field}`))
    }
  }

  filter(key: string, value) {
    if(value) {
      this.onFilter.emit(_.merge({},this.tableInputs.filterData, {[key]: value}))
    } else {
      this.onFilter.emit(_.omit(this.tableInputs.filterData, [key]))
    }
  }

  selectedItemForMenu(pltId: any) {
    console.log()
    this.onItemSelectForMenu.emit(pltId);
  }

  selectSinglePLT(pltId: number, $event: boolean) {
    this.onSelectSinglePlt.emit({
      [pltId]: {
        type:  $event
      }
    })
  }

  generateContextMenu(toRestore) {
    this.contextMenuItems = _.filter(this.contextMenuItemsCache, e => e.label != (!toRestore ? 'Restore' : 'Delete'))
  }

  handlePLTClick(pltId, i: number, $event: MouseEvent) {
    const isSelected = _.findIndex( !this.tableInputs.showDeleted ? this.tableInputs.selectedListOfPlts : this.tableInputs.selectedListOfDeletedPlts, el => el == pltId) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = "withKey";
      this.handlePLTClickWithKey(pltId, i, !isSelected, $event);
    } else {
      this.lastSelectedId = i;
      this.onPltClick.emit(
        _.zipObject(
          _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPlts : this.tableInputs.listOfDeletedPlts, plt => plt),
          _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPlts : this.tableInputs.listOfDeletedPlts, plt =>   ({type: plt == pltId && (this.lastClick == 'withKey' || !isSelected) }))
        )
      );
      this.lastClick= null;
    }
  }

  private handlePLTClickWithKey(pltId: number, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      this.selectSinglePLT(pltId, isSelected);
      this.lastSelectedId = i;
      return;
    }

    if($event.shiftKey) {
      console.log(i, this.lastSelectedId);
      if(!this.lastSelectedId) this.lastSelectedId = 0;
      if(this.lastSelectedId || this.lastSelectedId == 0) {
        const max = _.max([i, this.lastSelectedId]);
        const min = _.min([i, this.lastSelectedId]);
        this.onPltClick.emit(
          _.zipObject(
            _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPlts : this.tableInputs.listOfDeletedPlts, plt => plt),
            _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPlts : this.tableInputs.listOfDeletedPlts,(plt,i) =>  ({type:  i <= max  && i >= min})),
          )
        )
      } else {
        this.lastSelectedId = i;
      }
      return;
    }
  }


}
