import {Directive, EventEmitter, HostListener, Output} from '@angular/core';

const ENTER= 'Enter';
const SPACE= ' ';
const ARROW_UP= 'ArrowUp';
const ARROW_DOWN= 'ArrowDown';
const DELETE='Delete';
const BACKSPACE='Backspace';
const DOUBLE_POINTS =':';

@Directive({
  selector: '[searchInput]'
})
export class SearchInputDirective {

  @Output('onDoublePoints')
  onDoublePoints:EventEmitter<KeyboardEvent>=new EventEmitter();

  @Output('onEnter')
  onEnter:EventEmitter<KeyboardEvent>=new EventEmitter();

  @Output('onSpace')
  onSpace:EventEmitter<KeyboardEvent>= new EventEmitter();

  @Output('onArrowUp')
  onArrowUp:EventEmitter<KeyboardEvent>=new EventEmitter();

  @Output('onArrowDown')
  onArrowDown:EventEmitter<KeyboardEvent>=new EventEmitter();

  @Output('onDelete')
  onDelete:EventEmitter<KeyboardEvent>=new EventEmitter();

  @Output('onBackspace')
  onBackspace:EventEmitter<KeyboardEvent>=new EventEmitter();

  readonly keyToEmitterMapper= {
    [DOUBLE_POINTS]: this.onDoublePoints,
    [ENTER]: this.onEnter,
    [SPACE]: this.onSpace,
    [ARROW_UP]: this.onArrowUp,
    [ARROW_DOWN]: this.onArrowDown,
    [DELETE]: this.onDelete,
    [BACKSPACE]: {emit: (evt) => this._backspaceHandler(evt)}
  };
  lastEvent=null;

  constructor() { }

  @HostListener('keydown', ['$event'])
  onKeydown(keyboardEvent:KeyboardEvent) {
    let { key }= keyboardEvent;
    let dispatcher= this.keyToEmitterMapper[key];
    dispatcher ? dispatcher.emit(keyboardEvent) : null;
    this.lastEvent=key;
  }

  private _backspaceHandler(evt){
    if(this.lastEvent==BACKSPACE)
      this.onBackspace.emit(evt);
  }

}
