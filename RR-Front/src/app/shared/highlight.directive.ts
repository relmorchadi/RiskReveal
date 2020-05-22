import { Directive, ElementRef, HostListener, Input, Output, EventEmitter } from '@angular/core';

@Directive({
  selector: '[clickOutside]'
})
export class HighlightDirective {

  constructor(private _elementRef: ElementRef) { }

  @Input('init') init: boolean;
  initialized: boolean;
  @Input('exclude') exclude: string;

  @Output('clickOutsideHandler') clickOutside: EventEmitter<any> = new EventEmitter();

  @HostListener('document:click', ['$event.target']) onClick(targetElement) {
    this.clickOutsideHandler(targetElement);
  }

  clickOutsideHandler(target) {
    if(this.init) this.withInit(target);
    else this.withoutInit(target)
  }

  withInit(target) {
    const clickedInside = this._elementRef.nativeElement.contains(target);
    if (!clickedInside && this.initialized) {
      if(!this.excludeCheck(target)) this.clickOutside.emit(true);
    }
    if(!clickedInside && !this.initialized) this.initialized = true;
  }

  withoutInit(target) {
    const clickedInside = this._elementRef.nativeElement.contains(target);
    if (!clickedInside) {
      if(!this.excludeCheck(target)) this.clickOutside.emit(true);
    }
  }

  excludeCheck(target) {
    const nodes = Array.from(document.querySelectorAll(this.exclude)) as Array<HTMLElement>;
    console.log(nodes);
    for (let excludedNode of nodes) {
      if (excludedNode.contains(target)) {
        return true;
      }
    }
    return false;
  }


}
