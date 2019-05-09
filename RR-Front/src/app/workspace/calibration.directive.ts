import {Directive, ElementRef, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';

@Directive({
  selector: '[appCalibration]'
})
export class CalibrationDirective implements OnInit{


   @Input()appCalibration:any;
   @Input() typePlte:string;
   @Input() test:string;
  @Output('visibleIcon') visible: EventEmitter<any> = new EventEmitter();

  constructor(private elementRef: ElementRef) {}

  ngOnInit() {


    if(this.appCalibration=="Default" || this.typePlte =="icon-lock-alt iconRed"){
      this.elementRef.nativeElement.children[0].disabled=true;
      this.elementRef.nativeElement.children[0].style.backgroundColor="#efefef";

    }
    else{
      this.elementRef.nativeElement.style.display="inline-block";
      this.elementRef.nativeElement.style.width="50px";
      this.elementRef.nativeElement.style.height="20px";
      this.elementRef.nativeElement.children[0].style.visibility="hidden";
      this.elementRef.nativeElement.children[1].style.visibility="hidden";
    }

  }
 @HostListener('click', ['$event.target'])
 onClick(value){
    this.elementRef.nativeElement.children[0].style.visibility="visible";
   this.elementRef.nativeElement.children[1].style.visibility="visible";
   this.elementRef.nativeElement.style.display=null;
    this.visible.emit(this.elementRef.nativeElement);
  }



}
