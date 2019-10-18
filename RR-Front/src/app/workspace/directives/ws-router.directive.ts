import {Directive, ViewContainerRef} from '@angular/core';

@Directive({
  selector: '[WsRouter]'
})
export class WsRouterDirective {

  constructor(public viewContainerRef: ViewContainerRef) {
  }

}
