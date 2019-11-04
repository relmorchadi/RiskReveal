import { Directive, ElementRef, Input, AfterViewInit, Renderer2, OnDestroy } from '@angular/core';
import * as _ from 'lodash';

@Directive({
  selector: '[scrollIndicator]'
})
export class ScrollIndicatorDirective implements AfterViewInit, OnDestroy {
  scrollBar;
  tooltip;
  max;
  scroll;

  @Input('step') rowHeight;

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  ngOnDestroy() {
    this.scroll.removeEventListener()
  }

  ngAfterViewInit() {

    this.scrollBar = new ElementRef(this.el.nativeElement.querySelector('.ui-table-scrollable-body'));

    this.scroll = new Scroll(null, this.scrollBar.nativeElement);

    this.scroll.addEventListener("start", ({srcElement: {offsetWidth,offsetHeight, scrollHeight }}) => {

      if(!this.max) {
        this.tooltip = this.createTooltip();
        this.max = scrollHeight - offsetHeight;
      }
      this.toggleTooltipVisibility(true);
    });

    this.scroll.addEventListener("scroll", ({srcElement: { offsetWidth, clientHeight, offsetHeight, scrollTop, scrollHeight } }) => {
      if(scrollTop < this.max) {
        this.renderer.appendChild(this.scrollBar.nativeElement, this.tooltip.nativeElement);
        const x = clientHeight * ( scrollTop + clientHeight / 2 ) / scrollHeight;
        this.updateTooltipText(Math.floor((scrollTop + x)/ ( this.rowHeight || 45) + 1));
        this.updateTooltipPosition({
          top: scrollTop + x + 'px'
        })
      }
    });

    this.scroll.addEventListener("ended", () => {
      this.toggleTooltipVisibility(false)
    });

  }

  createTooltip() {
    const tooltip = new ElementRef(this.renderer.createElement('div'));
    this.renderer.addClass(tooltip.nativeElement, 'scrollIndicator');

    return tooltip;
  }

  updateTooltipPosition({top}) {
    if(top) {
      this.tooltip.nativeElement.style.top = top
    }
  }

  toggleTooltipVisibility(visible) {
    if(visible) {
      this.renderer.removeStyle(this.tooltip.nativeElement, 'visibility');
    } else {
      this.renderer.setStyle(
        this.tooltip.nativeElement,
        'visibility',
        'hidden'
      )
    }
  }

  updateTooltipText(text) {
    const tooltipArrow = new ElementRef(this.renderer.createElement('div'));
    this.renderer.addClass(tooltipArrow.nativeElement, 'scrollIndicatorArrow');

    const tooltipText = this.renderer.createText(text);

    _.forEach(this.tooltip.nativeElement.children, node => {
      this.renderer.removeChild(this.tooltip.nativeElement, node);
    })

    this.tooltip.nativeElement.textContent = '';

    this.renderer.appendChild(this.tooltip.nativeElement, tooltipArrow.nativeElement);
    this.renderer.appendChild(this.tooltip.nativeElement, tooltipText);
  }

}

class Scroll {
  initialY;
  callbacks;
  timeout;
  node;

  constructor(delay, node) {
    this.initialY = scrollY;
    this.node = node;
    this.callbacks = {
      start  : null,
      scroll : null,
      ended  : null,
    };
    this.timeout = {
      delay  : delay || 150,
      handle : null,
    };
    this.node.addEventListener("scroll", this.handler);
  }

  handler = (e) => {
    if (this.initialY == 0) {
      if (this.timeout.handle == null) {
        if (this.callbacks.start) {
          this.callbacks.start.call(this, e);
        }
      }
      if (this.timeout.handle !== null) {
        clearTimeout(this.timeout.handle);
      }
      if (this.callbacks.scroll) {
        this.callbacks.scroll.call(this, e);
      }
      var self = this;
      this.timeout.handle = setTimeout(function () {
        self.timeout.handle = null;
        if (self.callbacks.ended) {
          self.callbacks.ended.call(self, e);
        }
      }, this.timeout.delay);
    } else {
      this.initialY = 0;
    }
  };

  addEventListener = (event, callback) => {
    if (Object.keys(this.callbacks).includes(event)) {
      this.callbacks[event] = callback;
    } else {
      console.warn("Invalid scroll event: " + event);
    }
  };

  removeEventListener = () => {
    this.node.removeEventListener("scroll", () => {});
  }
}
