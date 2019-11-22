import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class InputComponent implements OnInit {

  @Input('visible') visible;
  @Input('value') value;

  @Output('onInput') onInput: EventEmitter<string>= new EventEmitter<string>();

  focused: boolean;

  constructor() { }

  ngOnInit() {
    this.focused= false;
  }

  filter(value: any) {
    this.onInput.emit(value);
  }

  setFocus(focus: boolean) {
    this.focused = focus;
  }
}
