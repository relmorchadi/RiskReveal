import { Directive, ElementRef, HostListener, AfterViewInit, QueryList, ContentChildren } from '@angular/core';
import {ScrollItemDirective} from "./scroll-item.directive";

@Directive({
  selector: '[scrollManager]',
})
export class ScrollManagerDirective implements AfterViewInit {

  currentIndex: number;

  @ContentChildren(
    ScrollItemDirective,
    {descendants: false}
  ) childrens: QueryList<ScrollItemDirective>;

  constructor(private el: ElementRef) {
    this.currentIndex = -1;
  }

  ngAfterViewInit() {
    this.childrens.forEach( (el, i) => {
      el.setIndex(i);
    });
  }

  @HostListener('document:keydown', ['$event']) onKeydownHandler(event: KeyboardEvent) {
    if(event.code === "ArrowDown") this.down();
    if(event.code === "ArrowUp") this.up();
    if( event.code === "Space") this.space();

    this.childrens.forEach( (el, i) => {
      if(i === this.currentIndex) {
        el.addClass('highlight-scroll-item');
      } else {
        el.removeClass('highlight-scroll-item');
      }
    })

  }

  up() {
    if(this.currentIndex > 0) {
      this.currentIndex = this.currentIndex - 1;
    } else {
      this.currentIndex = this.childrens.length
    }
  }

  down() {
    if(this.currentIndex < this.childrens.length - 1) {
      this.currentIndex = this.currentIndex + 1;
    } else {
      this.currentIndex = 0;
    }
  }

  space() {
    if( this.currentIndex != -1 ) {
      this.childrens.forEach( (el, i) => {
        if(i === this.currentIndex) {
          el.emit();
        }
      })
    }
  }

}
