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

  ngAfterViewInit(): void {
    this._visibleList = [...this.visibleList];
    this._availableList = [...this.availableList];
  }

  moveLeft(i: string) {
    this._availableList= _.concat({...this._visibleList[i], isVisible: false}, this._availableList);
    this.reOrderColumns(this._availableList);

    this._visibleList = _.filter(this._visibleList, (el, index) => i != index);
    this.reOrderColumns(this._visibleList);
    console.log(this._visibleList);
    console.log(this._availableList);
  }

  moveRight(i: string) {
    this._visibleList= _.concat({...this._availableList[i], isVisible: true}, this._visibleList);
    this.reOrderColumns(this._visibleList);

    this._availableList = _.filter(this._availableList, (el, index) => i != index);
    this.reOrderColumns(this._availableList);
  }

  close() {
    this.actionDispatcher.emit({
      type: "Close"
    })
  }

}
