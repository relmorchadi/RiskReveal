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
import {$e} from "codelyzer/angular/styles/chars";
import * as fromWorkspaceStore from "../../../../workspace/store";
import {BaseContainer} from "../../../base";
import {Store} from "@ngxs/store";
import {Router} from "@angular/router";

@Component({
  selector: 'app-plt-main-table',
  templateUrl: './plt-main-table.component.html',
  styleUrls: ['./plt-main-table.component.scss'],
  // changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [TableSortAndFilterPipe, SystemTagFilterPipe]
})
export class PltMainTableComponent extends BaseContainer implements OnInit {

  perilColors = {
    'EQ': 'red',
    'FL': '#0b99cc',
    'WS': '#62ec07',
    'CS': '#62ec07'
  };

  @ViewChild('cm') cm: TemplateRef<any>;

  @Input() tableInputs: tableStore.Input;
  @Input() containerPlts: any;
  @Input() selectedProject: number;

  selectedDropDown: any;
  selectedDropDownTs: any;
  initContainer: any[];

  payloadSelectedPLts: any = {};

  @Output() setTagModalVisibility = new EventEmitter();

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  private activeCheckboxSort: boolean;
  private activeCheckboxSortScope: boolean;
  private lastSelectedId: number;
  private lastClick: string;
  private userTagsLength: number;

  constructor(private filterPipe: TableSortAndFilterPipe, private systemTagFilter: SystemTagFilterPipe,
              _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.activeCheckboxSort = false;
    this.userTagsLength = 3;
  }

  ngOnInit() {
    console.log(this.tableInputs);
    this.detectChanges();
  }

  getCurrentPlts(){
    return this.systemTagFilter.transform(this.filterPipe.transform(this.tableInputs.showDeleted ? this.tableInputs.listOfDeletedPltsData : this.tableInputs.listOfPltsData,[this.tableInputs.sortData, this.tableInputs.filterData]),[this.tableInputs.filters.systemTag])
  }

  checkAll(event) {
    let newPayload = {};
    this.tableInputs.listOfPltsData = _.map(
        this.tableInputs.listOfPltsData, ws =>  {
          newPayload = _.merge(newPayload, {[ws.pltId]: {type: event}});
          return {...ws, selected: event}
        }
    );
    this.payloadSelectedPLts = newPayload;

/*    this.actionDispatcher.emit({
      type: tableStore.onCheckAll,
      payload: this.tableInputs.showDeleted
    });*/
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

  selectSinglePLT(rowData, event: boolean) {
    console.log('data', event);
    const {pltId, projectId} = rowData;
    const payload = {[pltId]: {
        type: event,
        projectId
      }
    };
    this.payloadSelectedPLts = _.merge(this.payloadSelectedPLts, payload);
    this.tableInputs.listOfPltsData = _.map(this.tableInputs.listOfPltsData, plt => { return plt.pltId === pltId ? {...plt, selected: event} : {...plt}});
    this.sendData();
  }

  handlePLTClick(rowData, i: number, $event: MouseEvent) {
    const {pltId, projectId} = rowData;
    const isSelected = _.findIndex(!this.tableInputs.showDeleted ? this.tableInputs.selectedListOfPlts : this.tableInputs.selectedListOfDeletedPlts, el => el == pltId) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = 'withKey';
      this.handlePLTClickWithKey(rowData, i, !isSelected, $event);
    } else {
      let newPayload = {};
      this.tableInputs.listOfPltsData = _.map(this.tableInputs.listOfPltsData,
          ws => {
        newPayload = _.merge(newPayload, {[ws.pltId]: {type: ws.pltId === pltId}});
        return {...ws, selected: ws.pltId === pltId}}
      );
      this.lastSelectedId = i;
      this.payloadSelectedPLts = newPayload;
      this.lastClick = null;
      this.sendData();
    }
  }

  private handlePLTClickWithKey(rowData, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      this.selectSinglePLT(rowData, isSelected);
      this.lastSelectedId = i;
    } else if ($event.shiftKey) {
      if (!this.lastSelectedId) this.lastSelectedId = 0;
      if (this.lastSelectedId || this.lastSelectedId == 0) {
        let newPayload = {};
        const max = _.max([i, this.lastSelectedId]);
        const min = _.min([i, this.lastSelectedId]);
        this.tableInputs.listOfPltsData = _.map(this.tableInputs.listOfPltsData,
            (ws, index) => {
              newPayload = _.merge(newPayload, {[ws.pltId]: {type: index <= max && index >= min}});
              return {...ws, selected: index <= max && index >= min}
            }
        );
        this.payloadSelectedPLts = newPayload;
        this.sendData();
      } else {
        this.lastSelectedId = i;
      }
    }
  }

  rowTrackBy = (index, item) => {
    return item[this.tableInputs.dataKey || this.tableInputs.pltColumns.visible[0].field];
  };

  filterByFalsely(bool: string) {
    this.actionDispatcher.emit({
      type: tableStore.filterByFalesely,
      payload: bool
    })
  }

  sendData() {
     setTimeout(() => {
      this.actionDispatcher.emit({
        type: tableStore.toggleSelectedPlts,
        payload: this.payloadSelectedPLts
      })
    }, 100)
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

  getElHeight() {
    const els = document.getElementsByClassName('ui-table-scrollable-header');
    return els.length ?  document.getElementsByClassName('ui-table-scrollable-header')[0].clientHeight + 'px' : 0;
  }

  ok($ev) {

  }

}
