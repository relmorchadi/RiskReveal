import {Directive, ElementRef, EventEmitter, HostListener, Input, OnChanges, Output, Renderer2, SimpleChanges} from '@angular/core';

@Directive({
  selector: '[appKeyDownScroll]'
})
export class KeyDownScrollDirective implements OnChanges{

  @Input() appKeyDownScroll: boolean;
  @Input() i: number;
  @Input() k: number;
  @Input() data: any;
  @Input() scrollTo: number;
  @Output() setPos = new EventEmitter();

  calculatePos(i: number, j: number) {
    let r = 0;
    for(let index=0; index < i;index++) {
      r+= this.data[index].length;
    }
    return j + r
  }



  ngOnChanges(changes : SimpleChanges){
    if(this.calculatePos(this.i,this.k) === this.scrollTo) {
      this.elRef.nativeElement.scrollIntoView();

      this.setPos.emit({
        i: this.i-1,
        j: this.k
      });
      this.renderer.addClass(this.elRef.nativeElement,'highlight-scroll-item');
    }else{
      this.renderer.removeClass(this.elRef.nativeElement,'highlight-scroll-item');
    }
  }

  constructor(private renderer: Renderer2,private elRef: ElementRef) {

  }

}
