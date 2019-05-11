import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[appScrolltable]'
})
export class ScrolltableDirective {
  private scroll: number;
  constructor(private el: ElementRef) { }

  track($event: Event) {
    this.scroll = this.el.nativeElement.scrollTop
    console.debug("Scroll Event", this.scroll);
  }
  @HostListener('scroll', ['$event'])
  scrollIt() { this.scroll = event.srcElement.scrollTop }
}
