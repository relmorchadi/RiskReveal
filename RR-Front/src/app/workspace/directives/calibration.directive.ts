import {Directive, ElementRef, HostListener, Input, OnInit} from '@angular/core';

@Directive({
  selector: '[appCalibration]'
})
export class CalibrationDirective implements OnInit {


  @Input() appCalibration: any;
  @Input() typePlte: string;
  @Input() test: string;

  //@Output('visibleIcon') visible: EventEmitter<any> = new EventEmitter();

  constructor(private elementRef: ElementRef) {
  }

  ngOnInit() {
    if (this.appCalibration == "Default" || this.typePlte == "icon-lock-alt iconRed") {
      this.elementRef.nativeElement.children[0].disabled = true;
      this.elementRef.nativeElement.children[0].style.backgroundColor = "#efefef";
    } else {
      this.elementRef.nativeElement.style.display = "inline-block";
      // this.elementRef.nativeElement.style.width="50px";
      this.elementRef.nativeElement.style.height = "20px";
      this.elementRef.nativeElement.children[0].style.visibility = "hidden";
      this.elementRef.nativeElement.children[1].style.visibility = "hidden";
    }
  }

  @HostListener('document:click', ['$event'])
  onClick(value) {
    if (this.elementRef.nativeElement.contains(event.target)) {
      this.elementRef.nativeElement.style.display = "inline-block";
      //this.elementRef.nativeElement.style.width="50px";
      this.elementRef.nativeElement.style.height = "20px";
      this.elementRef.nativeElement.children[0].style.visibility = "visible";
      this.elementRef.nativeElement.children[1].style.visibility = "visible";
      this.elementRef.nativeElement.children[0].value = ""
    } else {
      if (!this.elementRef.nativeElement.children[0].disabled && this.elementRef.nativeElement.children[0].value == "" && this.elementRef.nativeElement.children[0].style.visibility == "visible") {
        this.elementRef.nativeElement.children[0].style.visibility = "hidden";
        this.elementRef.nativeElement.children[1].style.visibility = "hidden";
      }
    }

  }

  @HostListener('blur', ['$event.target'])
  onBlur(value) {
    this.elementRef.nativeElement.style.display = "inline-block";
    this.elementRef.nativeElement.style.height = "20px";
    console.log(this.elementRef.nativeElement.children[0].value);
    this.elementRef.nativeElement.children[0].style.visibility = "visible";
    this.elementRef.nativeElement.children[1].style.visibility = "visible";
  }


}
