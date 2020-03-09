import {Router} from "@angular/router";
import {ChangeDetectorRef, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {Store} from "@ngxs/store";
import {MonoTypeOperatorFunction, Observable, Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {Navigate} from '@ngxs/router-plugin';
import {GeneralConfigState} from "../../core/store/states";


export abstract class BaseContainer implements OnInit {

  config: {
    numberOfDecimals: number;
    decimalSeparator: string;
    decimalThousandSeparator: string;
    negativeFormat: string;
  };

  protected unSubscriton$: Subject<void>;

  protected constructor(private _baseRouter: Router, private _baseCdr: ChangeDetectorRef, private _baseStore: Store) {
    this.unSubscriton$ = new Subject<void>();
  }

  get unsubscribeOnDestroy(): MonoTypeOperatorFunction<any> {
    return takeUntil(this.unSubscriton$);
  }

  ngOnInit() {
    this._baseStore
        .select(GeneralConfigState.getNumberFormatConfig)
        .pipe(this.unsubscribeOnDestroy)
        .subscribe(({ numberOfDecimals, decimalSeparator, decimalThousandSeparator, negativeFormat }) => {
              this.config = {
                numberOfDecimals,
                decimalSeparator,
                decimalThousandSeparator,
                negativeFormat
              };
              this.detectChanges();
            }
        );
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
