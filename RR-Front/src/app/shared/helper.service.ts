import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject,} from 'rxjs';
import {of} from "rxjs";

@Injectable({providedIn:'root'})
export class HelperService{
  items = [];

  public items$: Observable<any>;

  constructor() {}

  collapseLeftMenu$:Subject<void> = new Subject<void>();
  openWorkspaces:Subject<any> = new BehaviorSubject<any>([]);

  affectItems(item) {
    this.items = item;
  }

  getSearchedWorkspaces() {
    return of(this.items);
  }

}
