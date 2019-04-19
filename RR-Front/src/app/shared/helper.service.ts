import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import * as _ from 'lodash';
import {of} from 'rxjs';

@Injectable({providedIn: 'root'})
export class HelperService {
  items = JSON.parse(localStorage.getItem('workspaces')) || [];
  public items$: Observable<any>;

  constructor() {}

  collapseLeftMenu$ = new Subject<void>();
  openWorkspaces = new BehaviorSubject<any>([]);

  affectItems(item) {
    localStorage.setItem('workspaces', JSON.stringify(item));
    this.items = JSON.parse(localStorage.getItem('workspaces'));
  }

  getSearchedWorkspaces() {
    return of(this.items);
  }

  itemsAppend(item) {
    const alreadyImported = _.filter(this.items, ws => {
      if (ws.workSpaceId === item.workSpaceId && ws.uwYear == item.uwYear) {return ws; }
      }
    );
    if (alreadyImported.length === 0 ) {
      this.items.push(item);
      const savedWorkspaces: any = localStorage.getItem('workspaces');
      savedWorkspaces.push(item);
      console.log(savedWorkspaces);
      localStorage.setItem('workspaces', JSON.stringify(savedWorkspaces));
    }
  }

  itemsRemove( wsId, year) {
    this.items = _.filter(this.items, ws => {
        if (ws.workSpaceId === wsId && ws.uwYear == year) { return null; } else {return ws; }
      }
    );
  }

}
