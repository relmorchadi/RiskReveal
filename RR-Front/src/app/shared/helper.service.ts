import {Injectable} from '@angular/core';
import {Observable, of, Subject} from 'rxjs';
import * as _ from 'lodash';
import {map} from 'rxjs/operators';
import {Select, Store} from '@ngxs/store';
import {WorkspaceMain} from "../core/model/workspace-main";


@Injectable({providedIn: 'root'})
export class HelperService {
  recentWorkspaces$: Observable<any>;
  collapseLeftMenu$ = new Subject<void>();

  public static headerBarPopinChange$:Subject<{from:string}>= new Subject();

  state: WorkspaceMain = null;

  constructor(private store: Store) {
    const obs$: any =  of(localStorage.getItem('usedWorkspaces')).pipe(map( ls => JSON.parse(ls)));
    this.recentWorkspaces$ = obs$;
    // this.store.dispatch(new LoadWorkspacesAction());
  }

  public static upperFirstWordsInSetence(sentence){
    return sentence ? _.lowerCase(sentence).split(' ').map(_.upperFirst).join(' ') : sentence;
  }
}
