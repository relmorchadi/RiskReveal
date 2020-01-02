import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';

@Component({
  selector: 'override-occurence-basis-dialog',
  templateUrl: './override-occurence-basis-dialog.component.html',
  styleUrls: ['./override-occurence-basis-dialog.component.scss']
})
export class OverrideOccurenceBasisDialogComponent implements OnInit {

  @Input('visible')
  isVisible;

  @Input('data')
  data;

  @Output('close')
  closeEmitter= new EventEmitter();

  @Output('override')
  overrideEmitter= new EventEmitter();

  changes={
    occurrenceBasis: 'PerEvent',
    overrideReason: '',
    scopeOfOverride: 'current'
  };

  constructor() { }

  ngOnInit() {
    if(this.data){
      this.changes= _.merge(this.changes, this.data);
    }
  }

  override(){
    this.overrideEmitter.emit(this.changes);
  }

  close(){
    this.closeEmitter.emit();
  }

}
