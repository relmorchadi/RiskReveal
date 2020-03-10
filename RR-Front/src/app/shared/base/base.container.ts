import {Router} from "@angular/router";
import {ChangeDetectorRef, OnChanges, OnInit, SimpleChanges} from "@angular/core";
import {Store} from "@ngxs/store";
import {MonoTypeOperatorFunction, Observable, Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {Navigate} from '@ngxs/router-plugin';
import {GeneralConfigState} from "../../core/store/states";


export abstract class BaseContainer implements OnInit {

  numberConfig: {
    numberOfDecimals: number;
    decimalSeparator: string;
    decimalThousandSeparator: string;
    negativeFormat: string;
  };

  dateConfig: {
    shortDate: '',
    shortTime: '',
    longDate: '',
    longTime: ''
  };

  protected unSubscription$: Subject<void>;

  protected constructor(private _baseRouter: Router, private _baseCdr: ChangeDetectorRef, private _baseStore: Store) {
    this.unSubscription$ = new Subject<void>();
  }

  get unsubscribeOnDestroy(): MonoTypeOperatorFunction<any> {
    return takeUntil(this.unSubscription$);
  }

  ngOnInit() {
    this._baseStore
        .select(GeneralConfigState.getNumberFormatConfig)
        .pipe(this.unsubscribeOnDestroy)
        .subscribe(({ numberOfDecimals, decimalSeparator, decimalThousandSeparator, negativeFormat }) => {
              this.numberConfig = {
                numberOfDecimals,
                decimalSeparator,
                decimalThousandSeparator,
                negativeFormat
              };
              this.detectChanges();
            }
        );

    this._baseStore
        .select(GeneralConfigState.getDateConfig)
        .pipe(this.unsubscribeOnDestroy)
        .subscribe((config) => {
              this.dateConfig = config;
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
    this.unSubscription$.next();
    this.unSubscription$.complete();
  }

  protected dispatch(action: any | any[]): Observable<any> {
    return this._baseStore.dispatch(action);
  }

  protected select(selector): Observable<any> {
    return this._baseStore.select(selector);
  }

}
