import {Directive, ElementRef, HostListener, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {filter} from 'rxjs/operators';
import {HelperService} from "../services/helper.service";

@Directive({
  selector: '[scroller]'
})
export class ScrollerDirective implements OnInit, OnDestroy {

  @Input('topic')
  topic: string;

  activeElement: boolean = null;

  froozeDispatch: boolean = false;

  subscription: Subscription;

  constructor(private elementRef: ElementRef, private _helper: HelperService) {
  }

  ngOnInit() {
    this.subscription = this._helper.scrollSubject.pipe(filter(this._canSubscribe))
      .subscribe((value: any) => {
        this.froozeDispatch = true;
        this.elementRef.nativeElement.scrollTo(0, value.scroll);
      });
  }

  @HostListener("scroll", ["$event"])
  onScroll(event: UIEvent) {
    if (this.froozeDispatch) {
      this.froozeDispatch = false;
      return;
    }
    const scroll = this._calculateScroll(event);
    this._helper.scrollSubject.next({topic: this.topic, scroll})
  }

  @HostListener("mouseenter")
  mouseEnter() {
    this.activeElement = true;
  }

  @HostListener("mouseleave")
  mouseLeave() {
    this.activeElement = false;
  }

  ngOnDestroy() {
    if (this.subscription)
      this.subscription.unsubscribe();
  }

  private _calculateScroll(event: UIEvent) {
    return 2 * (Math.round((event.srcElement.scrollTop / (event.srcElement.scrollHeight - event.srcElement.clientHeight)) * 100));
  }

  private _canSubscribe = ({topic}) => {
    return !this.activeElement && topic == this.topic
  }

}
