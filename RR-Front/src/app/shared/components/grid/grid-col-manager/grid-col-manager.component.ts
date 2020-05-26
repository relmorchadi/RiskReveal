import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from "lodash";
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

@Component({
  selector: 'grid-col-manager',
  templateUrl: './grid-col-manager.component.html',
  styleUrls: ['./grid-col-manager.component.scss']
})
export class GridColManager implements OnInit {

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
      payload: {
        availableList: this._availableList,
        visibleList: this._visibleList
      }
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
    } else {
      this.toggleVisibility(previousContainer.id, previousContainer.data, previousIndex);
      transferArrayItem(
          previousContainer.data,
          container.data,
          previousIndex,
          currentIndex
      );
    }
  }

  toggleVisibility(sourceId, source, previousIndex) {
    source[previousIndex]= { ...source[previousIndex], hide: sourceId === 'visible'};
  }

  toggleAllVisibilityAndOrder(columns, visibility) {
    _.forEach(columns, (col, i) => {
      columns[i] = ({...col, columnOrder: _.toNumber(i), visible: visibility});
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
