import {Directive, ElementRef, Output, AfterViewInit, EventEmitter, Renderer2} from '@angular/core';

@Directive({
  selector: '[scrollItem]',
})
export class ScrollItemDirective implements AfterViewInit {
  index: number;

  @Output("onTrigger") onTrigger= new EventEmitter<any>();

  constructor(private renderer: Renderer2,private el: ElementRef) {}

  ngAfterViewInit() {

  }

  public addClass(cls) {
    this.el.nativeElement.scrollIntoView();
    this.renderer.addClass(this.el.nativeElement,cls);
  }

  public removeClass(cls) {
    this.renderer.removeClass(this.el.nativeElement,cls);
  }

  public getIndex() {
    return this.index;
  }



  public setIndex(index: number) {
    this.index= index;
  }

  public emit() {
    this.onTrigger.emit();
  }
}
