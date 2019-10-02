import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-edit-edge-pop-up',
  templateUrl: './edit-edge-pop-up.component.html',
  styleUrls: ['./edit-edge-pop-up.component.scss']
})
export class EditEdgePopUpComponent implements OnInit {

  @Input() showEditEdgePopup: any;
  @Output('closePop') closePopEmitter: EventEmitter<any> = new EventEmitter();
  outPutPerspectives: any = ['Net'];
  treatmentOnGrouping: any = 'positive';
  perilFilters;

  perilFiltersTypes=[];
  filters=[];
  constructor() {
  }

  ngOnInit() {
  }

  closePopup() {
    this.closePopEmitter.emit();
  }

  saveFilter = (p) => {};
  removeFilter = (p) => {};
  addPeriFilter = () => {};
}
