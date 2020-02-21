import {AfterViewInit, Directive, ElementRef, EventEmitter, HostListener, Input, Output} from '@angular/core';

@Directive({
  selector: '[tRows]'
})
export class TableRowsDirective {

  @Input() tableClass: string;
  @Input() virtualRowHeight: number;
  @Output() heightChange: EventEmitter<number> = new EventEmitter();

  constructor(private el: ElementRef) { }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.onHeightChanges();
  }

  onHeightChanges() {
    const node = document.querySelector('.' + this.tableClass + ' .ui-table-scrollable-body');
    if(node) {
      const element: any = new ElementRef(node);
      const rows = Math.ceil((element.nativeElement.offsetHeight - 8) / this.virtualRowHeight);
      this.heightChange.emit(rows);
    }
  }

}
