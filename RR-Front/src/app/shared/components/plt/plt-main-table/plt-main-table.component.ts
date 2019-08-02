import {
  ChangeDetectionStrategy,
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

@Component({
  selector: 'app-plt-main-table',
  templateUrl: './plt-main-table.component.html',
  styleUrls: ['./plt-main-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
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

  @Output() setTagModalVisibility= new EventEmitter();

  @Output() actionDispatcher: EventEmitter<Message> = new EventEmitter<Message>();

  private activeCheckboxSort: boolean;
  private lastSelectedId: number;
  private lastClick: string;
  private userTagsLength: number;

  constructor() {
    this.activeCheckboxSort = false;
    this.userTagsLength= 10000;
  }

  ngOnInit() {
  }

  checkAll($event){
    this.actionDispatcher.emit({
      type: tableStore.onCheckAll,
      payload: this.tableInputs.showDeleted
    })
  }

  checkBoxSort() {
    this.activeCheckboxSort = !this.activeCheckboxSort;

    this.actionDispatcher.emit({
      type: tableStore.checkBoxSort,
      payload: this.activeCheckboxSort ?
        _.sortBy(!this.tableInputs.showDeleted ? this.tableInputs.listOfPltsData
          :
          this.tableInputs.listOfDeletedPltsData, [(o: any) => !o.selected]) : !this.tableInputs.showDeleted ? this.tableInputs.listOfPltsCache : this.tableInputs.listOfDeletedPltsCache
    })

  }

  sortChange(field: any, sortCol: any) {
    if(!sortCol){
      this.actionDispatcher.emit({
        type: tableStore.sortChange,
        payload: _.merge({}, this.tableInputs.sortData, {[field]: 'asc'})
      })
    }else if(sortCol === 'asc'){
      this.actionDispatcher.emit({
        type: tableStore.sortChange,
        payload: _.merge({}, this.tableInputs.sortData, {[field]: 'desc'})
      })
    } else if(sortCol === 'desc') {
      this.actionDispatcher.emit({
        type: tableStore.sortChange,
        payload: _.omit(this.tableInputs.sortData, `${field}`)
      })
    }
  }

  filter(key: string, value) {
    if(value) {
      this.actionDispatcher.emit({
        type: tableStore.filterData,
        payload: _.merge({}, this.tableInputs.filterData, {[key]: value})
      })
    } else {
      this.actionDispatcher.emit({
        type: tableStore.filterData,
        payload: _.omit(this.tableInputs.filterData, [key])
      })
    }
  }

  selectedItemForMenu(pltId: any) {
    this.actionDispatcher.emit({
      type: tableStore.setSelectedMenuItem,
      payload: pltId
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
    const isSelected = _.findIndex( !this.tableInputs.showDeleted ? this.tableInputs.selectedListOfPlts : this.tableInputs.selectedListOfDeletedPlts, el => el == pltId) >= 0;
    if ($event.ctrlKey || $event.shiftKey) {
      this.lastClick = "withKey";
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
      this.lastClick= null;
    }
  }

  rowTrackBy = (index, item) => {
    return item[this.tableInputs.dataKey || this.tableInputs.pltColumns[0].field];
  }

  tmp(param: any) {
    console.log(param);
  }

  private handlePLTClickWithKey(pltId: number, i: number, isSelected: boolean, $event: MouseEvent) {
    if ($event.ctrlKey) {
      this.selectSinglePLT(pltId, isSelected);
      this.lastSelectedId = i;
      return;
    }

    if($event.shiftKey) {
      if(!this.lastSelectedId) this.lastSelectedId = 0;
      if(this.lastSelectedId || this.lastSelectedId == 0) {
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
    console.log(event)

    if( innerText == "User Tags" ) {
      console.log(_.floor(scrollWidth/18));
      this.userTagsLength= _.floor(scrollWidth/18);
    }
  }
}
