import {Injectable} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {filter} from 'rxjs/operators';

@Injectable()
export class PreviousNavigationService {

  private previousUrl: string = '';
  private currentUrl: string = '';

  constructor(private router: Router) {
    this.currentUrl = this.router.url;
    router.events.subscribe((event: any) => {
      if(event instanceof NavigationEnd) {
        this.previousUrl = this.currentUrl;
        this.currentUrl = event.url;
        console.log(this.previousUrl, this.currentUrl);
      }
    });
  }

  public getPreviousUrl() {
    const t = this.previousUrl.split('/');
    return t[t.length - 1];
  }
}
