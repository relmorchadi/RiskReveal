import {ChangeDetectorRef, OnDestroy} from "@angular/core";
import {MonoTypeOperatorFunction, Subject,} from "rxjs";
import {Router} from "@angular/router";
import {takeUntil} from "rxjs/operators";


export abstract class BaseComponent implements OnDestroy {

  private unSubscriton$ = new Subject<void>();

  constructor(private _router: Router, private _cdr: ChangeDetectorRef) {
    this.unSubscriton$ = new Subject<void>();
  }


  get unsubscribeOnDestroy(): MonoTypeOperatorFunction<any> {
    return takeUntil(this.unSubscriton$);
  }

  protected navigate(commands: any[]) {
    this._router.navigate(commands);
  }


  protected detectChanges() {
    if (!this._cdr['destroyed'])
      this._cdr.detectChanges();
  }

  protected destroy() {
    this.unSubscriton$.next();
    this.unSubscriton$.complete();
  }

  abstract ngOnDestroy(): void;

}
