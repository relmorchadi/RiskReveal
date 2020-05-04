import { Directive, ElementRef, HostListener, Input, Output, EventEmitter } from '@angular/core';

@Directive({
  selector: '[clickOutside]'
})
export class HighlightDirective {

  constructor(private _elementRef: ElementRef) { }

  @Input('step') step: number;
  counter: number = 0;

  @Output('clickOutsideHandler') clickOutside: EventEmitter<any> = new EventEmitter();

  @HostListener('document:click', ['$event.target']) onMouseEnter(targetElement) {
    this.clickOutsideHandler(targetElement);
  }

  clickOutsideHandler(target) {
    if(this.step) this.withStep(target);
    else this.withoutStep(target)
  }

  withStep(target) {
    const clickedInside = this._elementRef.nativeElement.contains(target);
    if (!clickedInside && this.step == this.counter) {
      this.clickOutside.emit(true);
    }
    if(!clickedInside) this.counter++;
  }

  withoutStep(target) {
    const clickedInside = this._elementRef.nativeElement.contains(target);
    if (!clickedInside) {
      this.clickOutside.emit(true);
    }
  }


}
