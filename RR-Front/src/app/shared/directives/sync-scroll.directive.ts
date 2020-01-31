import {
  AfterViewInit,
  Directive,
  ElementRef,
  HostListener,
  Input,
  OnChanges,
  OnDestroy,
  SimpleChanges
} from '@angular/core';

@Directive({
  selector: '[syncScroll]'
})
export class SyncScrollDirective implements AfterViewInit, OnDestroy, OnChanges {

  frozen: ElementRef;
  unfrozen: ElementRef;

  @Input() frozenColumns;

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
    //this.frozen.nativeElement.removeEventListener('scroll');
    //this.unfrozen.nativeElement.removeEventListener('scroll');
    console.log("DESTROY");
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

  @HostListener('window:scroll', ['$event']) onScrollEvent($event){
    // console.log($event['Window']);
    console.log($event);

  }

}
