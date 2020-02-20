import {AfterViewInit, Directive, ElementRef, EventEmitter, HostListener, Input, Output} from '@angular/core';

@Directive({
  selector: '[tRows]'
})
export class TableRowsDirective {

  @Input() tableClass: string;
  @Output() heightChange: EventEmitter<number> = new EventEmitter();

  constructor(private el: ElementRef) { }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    console.log("Change");
    this.onHeightChanges();
  }

  onHeightChanges() {
    const node = document.querySelector('.' + this.tableClass + ' .ui-table-scrollable-body');
    if(node) {
      const element: any = new ElementRef(node);
      const rows = Math.ceil((element.nativeElement.offsetHeight - 8) / 45.556);
      console.log(rows, element.nativeElement.offsetHeight);
      this.heightChange.emit(rows);
    }
  }

}
