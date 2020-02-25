import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import * as _ from 'lodash';

@Component({
  selector: 'd-col-manager',
  templateUrl: './global-manage-columns.component.html',
  styleUrls: ['./global-manage-columns.component.scss']
})
export class GlobalManageColumnsComponent implements OnInit, AfterViewInit {

  @Input('visibleList') visibleList;
  _visibleList;

  @Input('availableList') availableList;
  _availableList;

  windowRef= window;

  @Output() actionDispatcher: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  onManageColumns() {
    this.actionDispatcher.emit({
      type: "Manage Columns",
      payload: [...this._availableList, ...this._visibleList]
    })
  }

  drop(event: CdkDragDrop<string[]>) {
    const {
      previousIndex,
      currentIndex,
      previousContainer,
      container
    } = event;
    if (previousContainer === container) {
      moveItemInArray(container.data, previousIndex, currentIndex);
      this.reOrderColumns(container.data);
    } else {
      this.toggleVisibility(previousContainer.id, previousContainer.data, previousIndex);
      transferArrayItem(
          previousContainer.data,
          container.data,
          previousIndex,
          currentIndex
      );
      this.reOrderColumns(previousContainer.data);
      this.reOrderColumns(container.data);
    }
  }

  reOrderColumns(columns) {
    _.forEach(columns, (col, i) => {
      columns[i] = ({...col, columnOrder: _.toNumber(i)});
    })
  }

  toggleVisibility(sourceId, source, previousIndex) {
    source[previousIndex]= { ...source[previousIndex], isVisible: !(sourceId === 'visible')};
  }

  toggleAllVisibilityAndOrder(columns, visibility) {
    _.forEach(columns, (col, i) => {
      columns[i] = ({...col, columnOrder: _.toNumber(i), isVisible: visibility});
    })
  }

  ngAfterViewInit(): void {
    this._visibleList = [...this.visibleList];
    this._availableList = [...this.availableList];
  }

  close() {
    this.actionDispatcher.emit({
      type: "Close"
    })
  }

  moveAllRight() {
    this._visibleList = [...this._availableList, ...this._visibleList];
    this.toggleAllVisibilityAndOrder(this._visibleList, true);
    this._availableList = [];
  }

  moveAllLeft() {
    this._availableList = [...this._visibleList, ...this._availableList];
    this.toggleAllVisibilityAndOrder(this._availableList, false);
    this._visibleList = [];
  }

}
