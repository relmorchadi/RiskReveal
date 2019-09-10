import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-manage-columns',
  templateUrl: './manage-columns.component.html',
  styleUrls: ['./manage-columns.component.scss']
})
export class ManageColumnsComponent implements OnInit {

  @Input('columns') columns: any[];
  @Input('visible') visible: boolean;

  @Output('onSubmit') onSubmit: EventEmitter<any>= new EventEmitter<any>();
  @Output('onCancel') onCancel: EventEmitter<any>= new EventEmitter<any>();

  listOfAvailbleColumnsCache: any[];
  listOfAvailbleColumns: any[];
  listOfUsedColumns: any[];

  constructor() {
    this.listOfAvailbleColumnsCache= [];
  }

  ngOnInit() {
    this.listOfAvailbleColumns= [];
  }

  resetColumns() {
    if(!this.visible) this.listOfUsedColumns = [...this.columns];
    this.onCancel.emit();
  }

  saveColumns() {
    this.onSubmit.emit(this.listOfUsedColumns);
  }

  onShow() {
    this.listOfAvailbleColumnsCache= [...this.listOfAvailbleColumns];
    this.listOfUsedColumns= this.columns;
  }

  drop(event: CdkDragDrop<any>) {
    const {
      previousContainer,
      container
    } = event;

    if (previousContainer === container) {
      if(container.id == "usedListOfColumns") {
        moveItemInArray(
          this.listOfUsedColumns,
          event.previousIndex + 1,
          event.currentIndex + 1
        );
      }
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousContainer.id == "usedListOfColumns" ? event.previousIndex + 1 : event.previousIndex,
        event.container.id == "availableListOfColumns" ? event.currentIndex : event.currentIndex + 1
      );
    }
  }

  onHide() {
    console.log(this.listOfAvailbleColumns, this.listOfUsedColumns, this.listOfAvailbleColumnsCache);
  }
}
