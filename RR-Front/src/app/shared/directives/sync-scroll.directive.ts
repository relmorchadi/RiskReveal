import {AfterViewInit, Directive, ElementRef, OnDestroy} from '@angular/core';

@Directive({
  selector: '[syncScroll]'
})
export class SyncScrollDirective implements AfterViewInit, OnDestroy {

  frozen: ElementRef;
  unfrozen: ElementRef;

  constructor(private el: ElementRef) {}

  ngAfterViewInit(): void {
    this.frozen = this.el.nativeElement.querySelector('.ui-table-frozen-view .ui-table-scrollable-body');
    this.unfrozen = this.el.nativeElement.querySelector('.ui-table-unfrozen-view .ui-table-scrollable-body');

    this.syncScroll(this.frozen, this.unfrozen);
  }

  syncScroll(source, target) {
    source.addEventListener('scroll', ({srcElement: { scrollTop }}) => {
      const t = new ElementRef(target);
      t.nativeElement.scrollTop = scrollTop;
    });
  }

  ngOnDestroy(): void {
    this.frozen.nativeElement.removeEventListener('scroll');
    this.unfrozen.nativeElement.removeEventListener('scroll');
  }

}
