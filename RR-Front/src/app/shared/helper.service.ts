import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject,} from 'rxjs';
import * as _ from 'lodash';
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

  itemsAppend(item) {
    let alreadyImported = _.filter(this.items, ws => {
      if(ws.workSpaceId === item.workSpaceId && ws.uwYear == item.uwYear)
        return ws;
      }
    );
    console.log(alreadyImported);
    if(alreadyImported.length === 0 )
      this.items.push(item)
  }

  itemsRemove( wsId, year) {
    this.items = _.filter(this.items, ws => {
        if(ws.workSpaceId === wsId && ws.uwYear == year)
          return null;
        else
          return ws;
      }
    )
  }

}
