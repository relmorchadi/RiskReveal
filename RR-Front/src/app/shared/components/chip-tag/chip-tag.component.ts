import {ChangeDetectorRef, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';


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
  @Output()
  onChangeValue: EventEmitter<any> = new EventEmitter();
  @ViewChild('inputElem')
  textEl: ElementRef;
  inputMinWidth: number = 40;
  inputWidth: number = this.inputMinWidth;
  charWith: number = 6;
  inputDisabled: boolean = true;

  constructor(private crf: ChangeDetectorRef) {
  }

  ngOnInit() {
  }

  emitclicked(status) {
    this.onclick.emit(status);
  }

  resize() {
    this.inputWidth = this.value.length * this.charWith > this.inputMinWidth ? this.value.length * this.charWith : this.inputMinWidth;

    this.crf.detectChanges();
  }

  toggleInput(event: boolean) {
    console.log(event);
    this.inputDisabled = event;
    if (event)
      this.onChangeValue.emit({key: this.key, value: this.value});
  }
}
