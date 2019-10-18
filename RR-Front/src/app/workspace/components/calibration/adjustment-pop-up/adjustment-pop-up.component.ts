import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';
import * as _ from 'lodash'
import {ADJUSTMENTS_ARRAY} from "../../../containers/workspace-calibration/data";

@Component({
  selector: 'app-adjustment-pop-up',
  templateUrl: './adjustment-pop-up.component.html',
  styleUrls: ['./adjustment-pop-up.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AdjustmentPopUpComponent implements OnInit {


  @Output('handleCancel') handleCancel: EventEmitter<any> = new EventEmitter();
  @Output('addAdjustmentFromPlusIcon') addAdjustmentFromPlusIcon: EventEmitter<any> = new EventEmitter();
  @Output('saveAdjModification') saveAdjModification: EventEmitter<any> = new EventEmitter();
  @Output('selectCategory') selectCategory: EventEmitter<any> = new EventEmitter();

  @Input() modalTitle;
  allAdjsArray = _.merge([], ADJUSTMENTS_ARRAY);
  favoredBasis: any[];
  unFavoredBasis: any[];
  @Input('singleValue') singleValue;
  @Input('AdjustementType') AdjustementType;
  linear: any;
  @Input('modifyModal') modifyModal;
  @Input('columnPosition') columnPosition;
  size = 'large';
  @Input() categorySelected: any;
  inputValue: any;
  @Input() categorySelectedFromAdjustement: any;
  @Input() isVisible: boolean;
  @Input() global: boolean;

  constructor(private changeDetect: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.initFavored();

  }

  initFavored() {
    this.favoredBasis = _.filter(this.allAdjsArray, value => value.favored);
    this.unFavoredBasis = _.filter(this.allAdjsArray, value => !value.favored);
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

  emitAddAdjustmentFromPlusIcon(status, value, category, columnPosition) {
    console.log(status);
    this.addAdjustmentFromPlusIcon.emit({
      status: status,
      singleValue: value,
      category: category,
      columnPosition: columnPosition
    });
    this.hide();
  }

  emitSaveAdjModification(category, value, columnPosition) {
    this.saveAdjModification.emit({category: category, value: value, columnPosition: columnPosition});
    this.hide();
  }

  hide() {
    this.handleCancel.emit();
    this.linear = false;
    this.singleValue = null;
    this.columnPosition = null;
    this.changeDetect.detectChanges();
  }


  favorise(item: any) {
    console.log('favorise : ', item);
    _.filter(this.allAdjsArray, value => value == item)[0].favored = true;
    this.initFavored();
  }

  unfavorise(item: any) {
    console.log('unfavorise : ', item);
    _.filter(this.allAdjsArray, value => value == item)[0].favored = false;
    this.initFavored();
  }

  visibleChange($event: any) {
    if ($event) {
      console.log(this.categorySelected)
      console.log(this.categorySelectedFromAdjustement)
    }
  }
}
