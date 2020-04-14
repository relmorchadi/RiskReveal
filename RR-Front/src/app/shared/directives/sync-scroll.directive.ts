import {
  AfterViewChecked,
  AfterViewInit,
  Directive,
  ElementRef,
  HostListener,
  Input,
  OnChanges,
  OnDestroy,
  SimpleChanges
} from '@angular/core';
import * as _ from 'lodash';
@Directive({
  selector: '[syncScroll]'
})
export class SyncScrollDirective implements AfterViewInit, AfterViewChecked, OnDestroy, OnChanges {

  @Input() classIdentifier;
  @Input() expandCount;

  listener: any[];

  constructor(private el: ElementRef) {
    this.listener= [];
  }

  ngAfterViewChecked(): void {
    this.sync()
  }

  ngAfterViewInit(): void {
  }

  syncScroll(source, target) {
    source.addEventListener('scroll', ({srcElement: { scrollTop }}) => {
      const t = new ElementRef(target);
      t.nativeElement.scrollTop = scrollTop;
    });
  }

  sync() {
    const nodes = this.el.nativeElement.querySelectorAll( `${this.classIdentifier ? '.'+this.classIdentifier : ''} .ui-table-scrollable-body`);

    if( nodes.length == 2 ) {

      _.forEach(this.listener, node => {
        node.removeEventListener('scroll', () => {});
      });

      const node1 = nodes[0];
      const node2 = nodes[1];

      this.listener.push(node1);
      this.listener.push(node2);

      this.syncScroll(node1, node2);
      this.syncScroll(node2, node1);
    }
  }

  ngOnDestroy(): void {
    console.log("destr")
    _.forEach(this.listener, node => {
      node.removeEventListener('scroll', () => {});
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes);
    this.sync();
  }

}
