import {
  AfterViewChecked,
  AfterViewInit,
  Directive,
  ElementRef,
  EventEmitter,
  HostListener,
  Input,
  OnChanges,
  OnInit,
  Output, SimpleChanges
} from '@angular/core';

@Directive({
  selector: '[tRows]'
})
export class TableRowsDirective implements AfterViewChecked{

  @Input() tableClass: string;
  @Input() virtualRowHeight: number;
  @Input() loading: number;
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
      const rows = Math.floor((element.nativeElement.offsetHeight - 8) / this.virtualRowHeight);
      this.heightChange.emit(rows);
    }
  }

  ngAfterViewInit(): void {
    this.onHeightChanges();
  }

  ngAfterViewChecked(): void {
    this.onHeightChanges();
  }

}
