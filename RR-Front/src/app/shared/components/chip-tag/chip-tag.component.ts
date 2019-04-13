import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';


// @ts-ignore
@Component({
  selector: 'app-chip-tag',
  templateUrl: './chip-tag.component.html',
  styleUrls: ['./chip-tag.component.scss']
})
export class ChipTagComponent implements OnInit {
  @Input()
  key: string;
  @Input()
  value: string;
  @Input()
  closeStatus: boolean;
  @Output()
  onclick: EventEmitter<boolean> =  new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  emitclicked(status) {
    this.onclick.emit(status);
  }

}
