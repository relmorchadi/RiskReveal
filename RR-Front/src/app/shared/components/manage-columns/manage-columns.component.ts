import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import * as _ from 'lodash';

@Component({
  selector: 'app-manage-columns',
  templateUrl: './manage-columns.component.html',
  styleUrls: ['./manage-columns.component.scss']
})
export class ManageColumnsComponent implements OnInit {

  @Input('columns') columns: any[];
  @Input('visible') visible: boolean;
  @Input('fromDash') fromDash: boolean = false;

  @Output('onSubmit') onSubmit: EventEmitter<any> = new EventEmitter<any>();
  @Output('onCancel') onCancel: EventEmitter<any> = new EventEmitter<any>();

  listOfAvailableColumnsCache: any[];
  listOfAvailableColumns: any[];
  listOfUsedColumns: any[];

  constructor() {
    this.listOfAvailableColumnsCache = [];
  }

  ngOnInit() {
    this.listOfAvailableColumns = [];
  }

  resetColumns() {
    if (!this.visible) this.listOfUsedColumns = [...this.columns];
    this.onCancel.emit();
  }

  saveColumns() {
    this.onSubmit.emit(this.listOfUsedColumns);
  }

  onShow() {
    if (this.fromDash) {
      this.listOfAvailableColumns = _.filter(this.columns, item => !item.visible);
      this.listOfAvailableColumnsCache = [...this.listOfAvailableColumns];
      this.listOfUsedColumns = _.filter(this.columns, item => item.visible);
    } else {
      this.listOfAvailableColumnsCache = [...this.listOfAvailableColumns];
      this.listOfUsedColumns = this.columns;
    }
  }

  drop(event: CdkDragDrop<any>) {
    const {
      previousContainer,
      container
    } = event;

    if (previousContainer === container) {
      if (container.id === 'usedListOfColumns') {
        moveItemInArray(
          this.listOfUsedColumns,
          event.previousIndex,
          event.currentIndex
        );
      }
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    }
  }

  onHide() {
    console.log(this.listOfAvailableColumns, this.listOfUsedColumns, this.listOfAvailableColumnsCache);
  }

  dropAll(dir: string) {
    if (dir === 'right') {
      const t = this.listOfUsedColumns;
      this.listOfUsedColumns = this.listOfAvailableColumns;
      this.listOfAvailableColumns = t;
    }
    if (dir === 'left') {
      const t = this.listOfAvailableColumns;
      this.listOfAvailableColumns = this.listOfUsedColumns;
      this.listOfUsedColumns = t;
    }
  }
}
