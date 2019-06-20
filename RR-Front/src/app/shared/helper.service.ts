import {Injectable} from '@angular/core';
import {Observable, of, Subject} from 'rxjs';
import * as _ from 'lodash';
import {map} from 'rxjs/operators';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMainState} from "../core/store/states/workspace-main.state";
import {WorkspaceMain} from "../core/model/workspace-main";
import {LoadWorkspacesAction} from "../core/store/actions/workspace-main.action";


@Injectable({providedIn: 'root'})
export class HelperService {
  recentWorkspaces$: Observable<any>;
  collapseLeftMenu$ = new Subject<void>();


  @Select(WorkspaceMainState)
  state$: Observable<WorkspaceMain>;
  state: WorkspaceMain = null;

  constructor(private store: Store) {
    const obs$: any =  of(localStorage.getItem('usedWorkspaces')).pipe(map( ls => JSON.parse(ls)));
    this.recentWorkspaces$ = obs$;
    this.store.dispatch(new LoadWorkspacesAction());
    this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  updateWorkspaceItems() {
    localStorage.setItem('workspaces', JSON.stringify(this.state.openedTabs));
  }

  updateRecentWorkspaces() {
    localStorage.setItem('usedWorkspaces', JSON.stringify(this.state.recentWs));
  }

  public static upperFirstWordsInSetence(sentence){
    return sentence ? _.lowerCase(sentence).split(' ').map(_.upperFirst).join(' ') : sentence;
  }
}
