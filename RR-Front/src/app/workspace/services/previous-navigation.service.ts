import {Injectable} from '@angular/core';
import {Actions, ofActionDispatched} from '@ngxs/store';
import {Navigate, RouterNavigation} from '@ngxs/router-plugin';

@Injectable()
export class PreviousNavigationService {

  private previousUrl: string = '';
  private currentUrl: string = '';

  constructor(private actions: Actions) {

    this.actions.pipe(
      ofActionDispatched([Navigate, RouterNavigation])
    ).subscribe(e => {
      if(e.routerState) {
        this.previousUrl = this.currentUrl;
        this.currentUrl = e.params.route;
      }
      if(e.path){
        this.previousUrl = this.currentUrl;
        const t = e.path[0].split('/');
        this.currentUrl = t[t.length - 1];
      }
    });
  }

  public getPreviousUrl() {
    return this.previousUrl
  }
}
