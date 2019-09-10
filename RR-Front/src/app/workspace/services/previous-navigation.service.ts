import {Actions, ofActionDispatched} from '@ngxs/store';
import {Navigate, RouterNavigation} from '@ngxs/router-plugin';
import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class PreviousNavigationService {

  private previousUrl: string = '';
  private currentUrl: string = '';

  constructor(private actions: Actions) {

    this.actions.pipe(
      ofActionDispatched(RouterNavigation)
    ).subscribe(e => {
        this.previousUrl = this.currentUrl;
      this.currentUrl = e.routerState.params.route;
    });

    this.actions.pipe(
      ofActionDispatched(Navigate)
    ).subscribe(e => {
        this.previousUrl = this.currentUrl;
        const t = e.path[0].split('/');
        this.currentUrl = t[t.length - 1];
    });
  }

  public getPreviousUrl() {
    return this.previousUrl
  }
}
