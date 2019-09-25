import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  TemplateRef,
  ViewChild
} from '@angular/core';
import * as _ from 'lodash';
import * as tableStore from './store';
import {Message} from '../../../message';
import {TableSortAndFilterPipe} from "../../../pipes/table-sort-and-filter.pipe";
import {SystemTagFilterPipe} from "../../../pipes/system-tag-filter.pipe";

@Component({
  selector: 'app-plt-main-table',
  templateUrl: './plt-main-table.component.html',
  styleUrls: ['./plt-main-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [TableSortAndFilterPipe, SystemTagFilterPipe]
})
export class PltMainTableComponent implements OnInit {

  perilColors = {
    'EQ': 'red',
    'FL': '#0b99cc',
    'WS': '#62ec07',
    'CS': '#62ec07'
  }

  @ViewChild('cm') cm: TemplateRef<any>;

  @Input() tableInputs: tableStore.Input;
  @Input() containerPlts: any;

  selectedDropDown: any;
  selectedDropDownTs: any;
  initContainer: [];

  @Output() setTagModalVisibility = new EventEmitter();

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  private activeCheckboxSort: boolean;
  private activeCheckboxSortScope: boolean;
  private lastSelectedId: number;
  private lastClick: string;
  private userTagsLength: number;

  constructor(private _baseCdr: ChangeDetectorRef, private filterPipe: TableSortAndFilterPipe, private systemTagFilter: SystemTagFilterPipe) {
    this.activeCheckboxSort = false;
    this.userTagsLength = 3;

  }

  ngOnInit() {
  }

  getCurrentPlts(){
    return this.systemTagFilter.transform(this.filterPipe.transform(this.tableInputs.showDeleted ? this.tableInputs.listOfDeletedPltsData : this.tableInputs.listOfPltsData,[this.tableInputs.sortData, this.tableInputs.filterData]),[this.tableInputs.filters.systemTag])
  }

  checkAll($event) {
    this.actionDispatcher.emit({
      type: tableStore.onCheckAll,
      payload: this.tableInputs.showDeleted
    });
  }

  checkAllScope(event) {
    this.actionDispatcher.emit({
      type: tableStore.onCheckAllScope,
      payload: event
    });
  }

  checkSelectedRows() {
    let check = true;
    let count = 0;
    this.getCurrentPlts().forEach(plt => {
      let valid = false;
      plt.treatySectionsState.forEach(ts => {
        if (ts.state != 'disabled') {
          valid = true;
          count++;
        }
      });
      if (valid) {
        if (!plt.selected) {
          check = false;
        }
      }

    })
    if(count == 0) check = false;
    return check;
  }

  checkMiddleState() {
    let check = false;
    let count = 0;
    let count2 = 0;
    this.getCurrentPlts().forEach(plt => {
      if (plt.selected) {
        count++;
      }
    })
    this.getCurrentPlts().forEach(plt => {
      let valid = false;
      plt.treatySectionsState.forEach(ts => {
        if (ts.state != 'disabled') {
          valid = true;
        }
      });
      if (valid) count2++;

    });
    if (count < count2 && count != 0) {
      check = true;
    }
    return check;
  }

  isPltAttached(plt, tsId) {
    let check = '';

    if (_.findIndex(this.containerPlts, {
      pltId: plt.pltId,
      regionPeril: plt.regionPerilCode,
      targetRap: plt.grain,
      tsId: tsId
    }) != -1) {
      check = 'attached';
    } else {
      check = 'notAttached';
    }
    return check;
  }

  deselectThePlt(pltId, event) {
    if (!event){this.actionDispatcher.emit({
      type: tableStore.deselectPlt,
      payload:
      pltId
    })}
    else{
      this.actionDispatcher.emit({
        type: tableStore.selectAllPltsRow,
        payload:
        pltId
      })
    }

  }

  selectDropDown(event, pltId, tsId) {
    if (event) {
      this.selectedDropDown = pltId;
      this.selectedDropDownTs = tsId;
    } else {

      this.selectedDropDown = null;
      this.selectedDropDownTs = null;
    }
  }

  isPltDisabled(plts, tsId) {
    let check = '';
    this.tableInputs.listOfPltsData.forEach(plt => {
      if (plt.pltId == plts.pltId) {
        plt.treatySectionsState.forEach(state => {
          if (state.tsId == tsId) {
            if (state.state == 'disabled') {
              check = "disabled";
            } else {
              check = "notDisabled";
            }
          }
        })
      }
    })
    return check;
  }

  checkBoxSort() {
    this.activeCheckboxSort = !this.activeCheckboxSort;

    this.actionDispatcher.emit({
      type: tableStore.checkBoxSort,
      payload: this.activeCheckboxSort ?
        _.sortBy(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData
          :
          this.tableInputs.listOfDeletedPltsData, [(o: any) => !o.selected]) : !this.tableInputs.showDeleted ? this.tableInputs.listOfPltsCache : this.tableInputs.listOfDeletedPltsCache
    });

  }

  checkBoxSortScope() {
    this.activeCheckboxSortScope = !this.activeCheckboxSortScope;

    this.actionDispatcher.emit({
      type: tableStore.checkBoxSortScope,
      payload: this.activeCheckboxSortScope ?
        _.sortBy(this.tableInputs.listOfPltsData, plt => !plt.selected) : _.sortBy(this.tableInputs.listOfPltsData, plt => plt.selected)
    })
  };

  sortChange(field: any, sortCol: any) {
    if (!sortCol) {
      this.actionDispatcher.emit({
        type: tableStore.sortChange,
        payload: _.merge({}, this.tableInputs.sortData, {[field]: 'asc'})
      });
    } else if (sortCol === 'asc') {
      this.actionDispatcher.emit({
        type: tableStore.sortChange,
        payload: _.merge({}, this.tableInputs.sortData, {[field]: 'desc'})
      });
    } else if (sortCol === 'desc') {
      this.actionDispatcher.emit({
        type: tableStore.sortChange,
        payload: _.omit(this.tableInputs.sortData, `${field}`)
      });
    }
  }

  filter(key: string, value) {
    if (value) {
      this.actionDispatcher.emit({
        type: tableStore.filterData,
        payload: _.merge({}, this.tableInputs.filterData, {[key]: value})
      });
    } else {
      this.actionDispatcher.emit({
        type: tableStore.filterData,
        payload: _.omit(this.tableInputs.filterData, [key])
      });
    }
  }

  selectedItemForMenu(pltId: any) {
    this.actionDispatcher.emit({
      type: tableStore.setSelectedMenuItem,
      payload: pltId
    });
  }

  selectPltForAttach(plt: any, treatySectionId: any) {
    this.actionDispatcher.emit({
      type: tableStore.attachPlt,
      payload: {
        pltId: plt.pltId,
        regionPeril: plt.regionPerilCode,
        targetRap: plt.grain,
        tsId: treatySectionId
      }
    });
  }

  attachToAllTs(plt) {
    this.actionDispatcher.emit({
      type: tableStore.attachToAllTs,
      payload: {
        pltId: plt.pltId,
        regionPeril: plt.regionPerilCode,
        targetRap: plt.grain
      }
    })
  }

  unattachOtherTs(plt, tsId) {
    this.actionDispatcher.emit({
      type: tableStore.attachToJustThisTs,
      payload: {
        pltId: plt.pltId,
        regionPeril: plt.regionPerilCode,
        targetRap: plt.grain,
        tsId: tsId
      }
    })
  }

  selectSinglePLT(pltId: number, $event: boolean) {
    this.actionDispatcher.emit({
      type: tableStore.toggleSelectedPlts,
      payload: {
        [pltId]: {
          type: $event
        }
      }
    })
  }

  handlePLTClick(pltId, i: number, $event: MouseEvent) {
    const isSelected = _.findIndex(!this.tableInputs.showDeleted ? this.tableInputs.selectedListOfPlts : this.tableInputs.selectedListOfDeletedPlts, el => el == pltId) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = 'withKey';
      this.handlePLTClickWithKey(pltId, i, !isSelected, $event);
    } else {
      this.lastSelectedId = i;
      this.actionDispatcher.emit({
        type: tableStore.toggleSelectedPlts,
        payload: _.zipObject(
          _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData : this.tableInputs.listOfDeletedPltsData, plt => plt.pltId),
          _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData : this.tableInputs.listOfDeletedPltsData, plt => ({type: plt.pltId == pltId && (this.lastClick == 'withKey' || !isSelected)}))
        )
      })
      this.lastClick = null;
    }
  }

  rowTrackBy = (index, item) => {
    return item[this.tableInputs.dataKey || this.tableInputs.pltColumns[0].field];
  }

  filterByStatus(statue: string) {
    this.actionDispatcher.emit({
      type: tableStore.filterByStatus,
      payload: statue
    })
  }

  onColResize(event: any) {
    const {
      innerText,
      scrollWidth
    } = event.element;

    if (innerText == "User Tags") {
      this.userTagsLength = _.floor(scrollWidth / 18);
      this.detectChanges();
    }
  }

  protected detectChanges() {
    if (!this._baseCdr['destroyed'])
      this._baseCdr.detectChanges();
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
        this.actionDispatcher.emit({
          type: tableStore.toggleSelectedPlts,
          payload: _.zipObject(
            _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData : this.tableInputs.listOfDeletedPltsData, plt => plt.pltId),
            _.map(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData : this.tableInputs.listOfDeletedPltsData, (plt, i) => ({type: i <= max && i >= min})),
          )
        })
      } else {
        this.lastSelectedId = i;
      }
      return;
    }
  }

  getElHeight() {
    const els = document.getElementsByClassName('ui-table-scrollable-header');
    return els.length ?  document.getElementsByClassName('ui-table-scrollable-header')[0].clientHeight + 'px' : 0;
  }
}
