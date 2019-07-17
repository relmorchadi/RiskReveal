import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-adjustment-pop-up',
  templateUrl: './adjustment-pop-up.component.html',
  styleUrls: ['./adjustment-pop-up.component.scss']
})
export class AdjustmentPopUpComponent implements OnInit {


  @Output('handleCancel') handleCancel: EventEmitter<any> = new EventEmitter();
  @Output('addAdjustmentFromPlusIcon') addAdjustmentFromPlusIcon: EventEmitter<any> = new EventEmitter();
  @Output('saveAdjModification') saveAdjModification: EventEmitter<any> = new EventEmitter();
  @Output('selectCategory') selectCategory: EventEmitter<any> = new EventEmitter();

  @Input() modalTitle;
  @Input() allAdjsArray;
  @Input() singleValue;
  @Input() AdjustementType;
  @Input() linear;
  @Input() modifyModal;
  @Input() columnPosition;
  size = 'large';
  categorySelected: any;
  inputValue: any;
  @Input() categorySelectedFromAdjustement: any;
  @Input() isVisible: boolean;

  constructor() {
  }

  ngOnInit() {
  }

  emitSelectCategory(p) {
    this.selectCategory.emit(p)
  }

  selectBasis(adjustment) {
    if (adjustment.id == 1) {
      this.linear = true;
    } else {
      this.linear = false;
    }
  }

  emitAddAdjustmentFromPlusIcon(status, value, category) {
    this.addAdjustmentFromPlusIcon.emit({status: status, singleValue: value, category: category})
  }

  emitSaveAdjModification(category) {
    this.saveAdjModification.emit(category)
    this.hide();
  }

  hide() {
    this.handleCancel.emit()
  }

}
