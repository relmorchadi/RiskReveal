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
export class SyncScrollDirective implements AfterViewInit,AfterViewChecked, OnDestroy, OnChanges {

  @Input() classIdentifier;

  listener: any[];

  constructor(private el: ElementRef) {
    this.listener= [];
  }

  ngAfterViewChecked(): void {

    /*this.frozen = this.el.nativeElement.querySelector('.ui-table-frozen-view .ui-table-scrollable-body');
    this.unfrozen = this.el.nativeElement.querySelector('.ui-table-unfrozen-view .ui-table-scrollable-body');

    this.syncScroll(this.frozen, this.unfrozen);*/

    const nodes = this.el.nativeElement.querySelectorAll( `${this.classIdentifier ? '.'+this.classIdentifier : ''} .ui-table-scrollable-body`);
    console.log(nodes, this.classIdentifier);

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

  ngAfterViewInit(): void {
  }

  syncScroll(source, target) {
    source.addEventListener('scroll', ({srcElement: { scrollTop }}) => {
      const t = new ElementRef(target);
      t.nativeElement.scrollTop = scrollTop;
    });
  }

  ngOnDestroy(): void {
    _.forEach(this.listener, node => {
      node.removeEventListener('scroll', () => {});
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
  }

}
