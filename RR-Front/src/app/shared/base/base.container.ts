import {BaseComponent} from "./base.component";
import {Router} from "@angular/router";
import {ChangeDetectorRef} from "@angular/core";
import {Store} from "@ngxs/store";
import {Observable} from "rxjs";


export abstract class BaseContainer extends BaseComponent{

  protected constructor(router: Router, cdr: ChangeDetectorRef, private _store:Store) {
    super(router, cdr);
  }

  protected dispatch(action: any|any[]):Observable<any>{
    return this._store.dispatch(action);
  }

}
