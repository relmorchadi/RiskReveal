import {Directive, EventEmitter, HostListener, Input, Output} from '@angular/core';

@Directive({
  selector: '[appScrollToParent]'
})
export class ScrollToParentDirective {

  @Input() scrollTo;
  @Input() listLength;

  @Output() emitScrollTo = new EventEmitter();
  constructor() { }

  @HostListener('keydown', ['$event'])
  handleKeyDown($event){
    if ($event.key === 'ArrowUp') {
      event.preventDefault();
      if (this.scrollTo > 0) {
        this.emitScrollTo.emit(this.scrollTo - 1);
      }
      event.stopPropagation();
    }
    if ($event.key === 'ArrowDown') {
      event.preventDefault();
      if (this.scrollTo < this.listLength - 1) {
        this.emitScrollTo.emit( this.scrollTo + 1)
      }
      event.stopPropagation();
    }
  }

}
