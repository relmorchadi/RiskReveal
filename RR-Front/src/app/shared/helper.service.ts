import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import * as _ from 'lodash';
import {of} from 'rxjs';
import {map} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class HelperService {
  items = JSON.parse(localStorage.getItem('workspaces')) || [];
  recentWorkspaces$: Observable<any>;
  recentWorkspaces: [];
  subjectRecent = new Subject<any>();
  public items$: Observable<any>;

  constructor() {
    this.recentWorkspaces$ = of(localStorage.getItem('usedWorkspaces')).pipe(map( ls => JSON.parse(ls)));
  }

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
      localStorage.setItem('workspaces', JSON.stringify(savedWorkspaces));
    }
  }

  itemsRemove( wsId, year) {
    this.items = _.filter(this.items, ws => {
        if (ws.workSpaceId === wsId && ws.uwYear == year) { return null; } else {return ws; }
      }
    );
  }

  getrecentWorkspaces() {
    return this.recentWorkspaces;
  }

  updateRecentWorkspaces(data) {
    this.subjectRecent.next();
    this.recentWorkspaces = data;
    localStorage.setItem('usedWorkspaces', JSON.stringify(data));
  }

  /* searchWorkspace(size: string = '10') {
    this.workspaces = [];
    if (localStorage.getItem('usedWorkspaces') === null) {
      this._searchService.searchContracts(this.contractFilterFormGroup.value, size)
        .subscribe((data: any) => {
          data.content.forEach(
            ws => {
              this.workspaces = [...this.workspaces, {...ws, selected: false}];
              this.workspaces = [...this.workspaces, {...ws, selected: false}];
            }
          );
        });
    } else {
      const items = JSON.parse(localStorage.getItem('usedWorkspaces'));
      items.forEach(
        ws => {
          this.workspaces = [...this.workspaces, {...ws, selected: false, timeStamp: Date.now()}];
        }
      );
    }
    if (this.selectedItems.length === 0) {
      this.workspaces[0].selected = true;
      this.selectedItems = [...this.selectedItems, this.workspaces[0]];
    } else {
      this.workspaces.forEach((ws) => {
          this.selectedItems.forEach((si) => ws.workSpaceId === si.workSpaceId ? ws.selected = true : null);
        }
      );
    }
  }*/
}
