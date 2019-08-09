import {Directive, ElementRef, EventEmitter, HostListener, Output} from '@angular/core';

@Directive({
  selector: '[clickOutside]'
})
export class HighlightDirective {

  @Output('clickOutside') clickOutside: EventEmitter<any> = new EventEmitter();

  constructor(private _elementRef: ElementRef) {
  }

  @HostListener('document:click', ['$event.target']) onMouseEnter(targetElement) {
    const clickedInside = this._elementRef.nativeElement.contains(targetElement);
    if (!clickedInside) {
      this.clickOutside.emit(null);
    }
  }


}
