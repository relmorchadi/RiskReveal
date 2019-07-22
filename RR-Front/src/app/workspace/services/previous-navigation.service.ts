import {Injectable} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {Actions, ofActionDispatched} from '@ngxs/store';
import {Navigate} from '@ngxs/router-plugin';

@Injectable()
export class PreviousNavigationService {

  private previousUrl: string = '';
  private currentUrl: string = '';

  constructor(private actions: Actions) {

    this.actions.pipe(
      ofActionDispatched(Navigate)
    ).subscribe( e => {
      this.previousUrl = this.currentUrl;
      const t = e.path[0].split('/');
      this.currentUrl = t[t.length - 1];
    });
  }

  public getPreviousUrl() {
    return this.previousUrl
  }
}
