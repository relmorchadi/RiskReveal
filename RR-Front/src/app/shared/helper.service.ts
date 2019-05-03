import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import * as _ from 'lodash';
import {of} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class HelperService {
  items = JSON.parse(localStorage.getItem('workspaces')) || [];
  recentWorkspaces$: Observable<any>;
  test$: BehaviorSubject<any>;
  changeSelectedWorkspace$: Subject<void> = new Subject();


  constructor() {
    const obs$: any =  of(localStorage.getItem('usedWorkspaces')).pipe(map( ls => JSON.parse(ls)));
    this.recentWorkspaces$ = obs$;
    this.test$ = new BehaviorSubject(JSON.parse(localStorage.getItem('usedWorkspaces')) || []);
  }

  collapseLeftMenu$ = new Subject<void>();
  openWorkspaces = new BehaviorSubject<any>([]);

  affectItems(item , newWorkspaces = false) {
    if (newWorkspaces) {
      localStorage.setItem('workspaces', JSON.stringify(_.uniqWith(item, _.isEqual )));
    } else {
      if (this.items.length === 0) {
        localStorage.setItem('workspaces', JSON.stringify(item));
      } else {
        this.items = this.items.filter(dt => dt);
        const listItems = [...this.items, ...item];
        localStorage.setItem('workspaces', JSON.stringify(_.uniqWith(listItems, _.isEqual )));
      }
    }
    this.items = JSON.parse(localStorage.getItem('workspaces')) || [];
    this.changeSelectedWorkspace$.next();
  }

  getSearchedWorkspaces() {
    console.log(this.items);
    return of(this.items);
  }

  itemsAppend(item) {
    const alreadyImported = _.filter(this.items, ws => {
        if (ws.workSpaceId === item.workSpaceId && ws.uwYear == item.uwYear) {return ws; }
      }
    );
    if (alreadyImported.length === 0 ) {
      this.items.push(item);
      const savedWorkspaces: any = JSON.parse(localStorage.getItem('workspaces')) || [];
      savedWorkspaces.push(item);
      localStorage.setItem('workspaces', JSON.stringify(savedWorkspaces));
    }
  }

  itemsRemove( wsId, year) {
    this.items = _.filter(this.items, ws => {
        if (ws.workSpaceId === wsId && ws.uwYear == year) { return null; } else {return ws; }
      }
    );
    this.changeSelectedWorkspace$.next();
    localStorage.setItem('workspaces', JSON.stringify(this.items));
  }

  updateRecentWorkspaces(data) {
    localStorage.setItem('usedWorkspaces', JSON.stringify(data));
    this.test$.next(data);
  }
}
