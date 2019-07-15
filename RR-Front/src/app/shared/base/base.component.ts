import {ChangeDetectorRef, OnDestroy} from "@angular/core";
import {MonoTypeOperatorFunction, Subject,} from "rxjs";
import {Router} from "@angular/router";
import {takeUntil} from "rxjs/operators";


export abstract class BaseComponent implements OnDestroy {

  private unSubscriton$: Subject<void>;

  constructor(protected _baseRouter: Router, protected _baseCdr: ChangeDetectorRef) {
    this.unSubscriton$ = new Subject<void>();
  }


  get unsubscribeOnDestroy(): MonoTypeOperatorFunction<any> {
    return takeUntil(this.unSubscriton$);
  }

  protected navigate(commands: any[]) {
    this._baseRouter.navigate(commands);
  }


  protected detectChanges() {
    if (!this._baseCdr['destroyed'])
      this._baseCdr.detectChanges();
  }

  protected destroy() {
    this.unSubscriton$.next();
    this.unSubscriton$.complete();
  }

  abstract ngOnDestroy(): void;

}
