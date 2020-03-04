import {AfterViewInit, ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import * as _ from 'lodash';
import {createConnectFile} from "selenium-webdriver/safari";

@Component({
  selector: 'calib-col-manager',
  templateUrl: './column-manager.component.html',
  styleUrls: ['./column-manager.component.scss']
})
export class CalibrationColumnManagerComponent implements OnInit {

  @Input('visibleList') visibleList;
  _visibleList;

  @Input('availableList') availableList;
  _availableList;

  windowRef= window;

  @Output() actionDispatcher: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
    console.log('init col')
  }

  onManageColumns() {
    this.actionDispatcher.emit({
      type: "Manage Frozen Columns",
      payload: this._visibleList
    })
  }

  drop(event: CdkDragDrop<string[]>) {
    const {
      previousIndex,
      currentIndex,
      previousContainer,
      container
    } = event;
    console.log(event);
    const col: any = previousContainer.data[previousIndex];
    if(col.field != 'status' && col.field != 'pltId' && col.field != 'pltName') {
      if (previousContainer === container) {
       this.moveItem(container, previousIndex, currentIndex);
      } else {
        this.transferItem(
            previousContainer,
            container,
            previousIndex,
            currentIndex
        );
      }
    }

    console.log(event);
  }

  ngAfterViewInit(): void {
    this._visibleList = [...this.visibleList];
    this._availableList = [...this.availableList];
  }

  close() {
    this.actionDispatcher.emit({
      type: "Close Column Manager"
    })
  }

  moveAllRight() {
    const head = _.slice(this._visibleList,0, this._visibleList.length - 1);
    const tail = _.slice(this._visibleList, this._visibleList.length - 1);
    this._visibleList = [...head, ...this._availableList, ...tail];
    this._availableList = [];
  }

  moveAllLeft() {
    const head = _.slice(this._visibleList, 0,2);
    const tail = _.slice(this._visibleList, this._visibleList.length - 1);
    this._availableList = [..._.slice(this._visibleList,2, this._visibleList.length - 1), ...this._availableList];
    this._visibleList = [...head, ...tail];
  }

  moveItem(container, i, j) {
    if(container.id == "visible") {
      const head = _.slice(container.data, 0,2);
      const tail = _.slice(container.data, container.data.length - 1);

      const middle = _.slice(container.data, 2, container.data.length - 1);

      moveItemInArray(middle, i - 2, j - 2);
      container.data = _.assign(container.data, [ ...head, ...middle, ...tail]);

    } else {
      moveItemInArray(container.data, i, j);
    }
  }

  transferItem(prevCont, currCont, i, j) {
    if(currCont.id == 'visible') {
      const head = _.slice(currCont.data, 0,2);
      const tail = _.slice(currCont.data, currCont.data.length - 1);

      const middle = _.slice(currCont.data, 2, currCont.data.length - 1);

      let newJ = j - 2;

      if(newJ < 0) newJ = 0;

      if( newJ > middle.length) {
        newJ = middle.length - 1;
      }

      transferArrayItem(prevCont.data, middle, i, newJ);

      currCont.data = _.assign(currCont.data, [ ...head, ...middle, ...tail]);

    } else {

      transferArrayItem(prevCont.data, currCont.data, i, j);

    }
  }

}
