import {Directive, ElementRef, EventEmitter, HostListener, Output} from '@angular/core';

@Directive({
  selector: '[clickOutsidee]'
})
export class HighlightDirective {

  @Output('clickOutside') clickOutside: EventEmitter<any> = new EventEmitter();

  constructor(private _elementRef: ElementRef) {
  }

  @HostListener('document:click', ['$event']) onMouseEnter(event) {
    const clickedInside = this._elementRef.nativeElement.contains(event.target);
    if (!clickedInside) {
      this.clickOutside.emit(true);
    }
  }


}
