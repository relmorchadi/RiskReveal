import {Router} from "@angular/router";
import {ChangeDetectorRef} from "@angular/core";
import {Store} from "@ngxs/store";
import {MonoTypeOperatorFunction, Observable, Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {Navigate} from '@ngxs/router-plugin';


export abstract class BaseContainer {

  protected unSubscriton$: Subject<void>;

  protected constructor(private _baseRouter: Router, private _baseCdr: ChangeDetectorRef, private _baseStore: Store) {
    this.unSubscriton$ = new Subject<void>();
  }

  get unsubscribeOnDestroy(): MonoTypeOperatorFunction<any> {
    return takeUntil(this.unSubscriton$);
  }

  protected navigate(commands: any[]) {
    this.dispatch(new Navigate(commands));
  }

  protected detectChanges() {
    if (!this._baseCdr['destroyed'])
      this._baseCdr.detectChanges();
  }

  protected markForCheck() {
    if (!this._baseCdr['destroyed']) this._baseCdr.markForCheck();
  }

  protected destroy() {
    this.unSubscriton$.next();
    this.unSubscriton$.complete();
  }

  protected dispatch(action: any | any[]): Observable<any> {
    return this._baseStore.dispatch(action);
  }

  protected select(selector): Observable<any> {
    return this._baseStore.select(selector);
  }

}
