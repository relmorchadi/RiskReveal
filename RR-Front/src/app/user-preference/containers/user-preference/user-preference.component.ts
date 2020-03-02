import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Actions, ofActionSuccessful, Select, Store} from '@ngxs/store';
import {Observable, of} from 'rxjs';
import * as _ from 'lodash';
import {GeneralConfigState, SearchNavBarState} from '../../../core/store/states';
import {
  LoadConfiguration,
  PatchNumberFormatAction,
  PatchSearchStateAction, PatchTimeZoneAction,
  PostNewConfigAction,
  PostNewConfigFailAction,
  PostNewConfigSuccessAction
} from '../../../core/store/actions';
import {GeneralConfig} from '../../../core/model';
import {BaseContainer} from "../../../shared/base";
import {Router} from "@angular/router";
import {Data} from "../../../core/model/data";
import * as fromWorkspaceStore from "../../../workspace/store";
import {NotificationService} from "../../../shared/services";
import {mergeMap} from "rxjs/operators";
import {RiskApi} from "../../../workspace/services/api/risk.api";
import {GenericBrowserDomAdapter} from "@angular/platform-browser/src/browser/generic_browser_adapter";

@Component({
  selector: 'app-user-preference',
  templateUrl: './user-preference.component.html',
  styleUrls: ['./user-preference.component.scss']
})
export class UserPreferenceComponent extends BaseContainer implements OnInit {

  searchTarget: any;
  testNumber: 1231024;

  search = ['Country',
    'Cedent Name',
    'Cedant Code',
    'Uw Year',
    'Workspace Name',
    'Workspace Context'
  ];

  shortDate: any;
  shortTime: any;
  longDate: any;
  longTime: any;
  timeZone: any;

  numberOfDecimals: any;
  decimalSeparator: any;
  decimalThousandSeparator: any;
  negativeFormat: any;

  importPage: any;
  financialPerspectiveELT: any;
  selectedFinancialPerspectiveELT = [];
  financialPerspectiveEPM: any;
  selectedFinancialPerspectiveEPM: any;
  targetCurrency: any;
  selectedTargetCurrency: any;
  targetAnalysisCurrency: any;
  selectedTargetAnalysisCurrency: any;
  rmsInstance: any;
  selectedRMSInstance: any;

  countries: any;
  selectedCountries = [];
  uwUnits: any;
  selectedUwUnits = [];

  epCurves: any = [];

  numberCollapse = false;

  @Select(GeneralConfigState)
  state$: Observable<GeneralConfig>;
  @Select(GeneralConfigState.getDateConfig)
  dateConfig$;
  @Select(GeneralConfigState.getNumberFormatConfig)
  numberConfig$;
  @Select(GeneralConfigState.getImportConfig)
  importConfig$;

  state: GeneralConfig = null;

  constructor(public location: Location,
              private cdRef: ChangeDetectorRef,
              private actions$: Actions,
              private riskApi: RiskApi,
              private notification: NotificationService,
              private router$: Router, public store$: Store) {
    super(router$, cdRef, store$);
    this.countries = Data.countries;
    this.uwUnits = Data.uwUnit;
  }

  ngOnInit() {
    this.dispatch(new LoadConfiguration());

    this.riskApi.loadImportRefData().subscribe(
      (refData: any) => {
        this.rmsInstance = refData.rmsInstances;
        this.targetAnalysisCurrency = refData.currencies;
        this.targetCurrency = refData.currencies;
        this.financialPerspectiveEPM = _.map(refData.financialPerspectives, item => item.desc);
        this.financialPerspectiveELT = _.map(refData.financialPerspectives, item => { return {label: item.desc, value: item.code}});
        this.detectChanges();
      }
    );

    this.dateConfig$.pipe().subscribe(value => {
      this.shortDate = value.shortDate;
      this.longDate = value.longDate;
      this.shortTime = value.shortTime;
      this.longTime = value.longTime;
      this.timeZone = value.timeZone;
      this.detectChanges();
    });

    this.numberConfig$.pipe().subscribe(value => {
      this.numberOfDecimals = value.numberOfDecimals;
      this.decimalSeparator = value.decimalSeparator;
      this.decimalThousandSeparator = value.decimalThousandSeparator;
      this.negativeFormat = value.negativeFormat;
      this.detectChanges();
    });

    this.importConfig$.pipe().subscribe(value => {
      this.selectedTargetCurrency = value.targetCurrency;
      this.selectedTargetAnalysisCurrency = value.targetAnalysisCurrency;
      this.importPage = value.importPage;
      this.selectedFinancialPerspectiveEPM = value.financialPerspectiveEPM;
      this.selectedFinancialPerspectiveELT = value.financialPerspectiveELT;
      this.selectedRMSInstance = value.rmsInstance;
      this.detectChanges();
    });

    this.store$.select(SearchNavBarState.getSearchTarget).subscribe(
      value => this.searchTarget = value
    );

    this.actions$.pipe(ofActionSuccessful(PostNewConfigSuccessAction)).subscribe( data => {
        this.dispatch(new LoadConfiguration());
        this.notification.createNotification('Information',
        'the Current Configuration has been saved Successfully.',
        'info', 'bottomRight', 4000);
        this.detectChanges();
    });

    this.actions$.pipe(ofActionSuccessful(PostNewConfigFailAction)).subscribe( data => {
      this.notification.createNotification('Warning',
        'A failure in saving The Current Current Configuration has occurred please check your internet Connexion before Saving.',
        'info', 'bottomRight', 4000);
      this.detectChanges();
    });

    this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  changeTimeZone(event) {
    this.dispatch(new PatchTimeZoneAction({value: event}))
  }

  navigateBack() {
    this.location.back();
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.search, event.previousIndex, event.currentIndex);
  }

  numberFormat() {
    return `1.${this.state.general.numberFormat.numberOfDecimals}-${this.state.general.numberFormat.numberOfDecimals}`;
  }

  number_format(num, decimals, decPoint, thousandsSep) {
    const newNum = !isFinite(+num) ? 0 : +num,
      prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
      sep = (typeof thousandsSep === 'undefined') ? ',' : thousandsSep,
      dec = (typeof decPoint === 'undefined') ? '.' : decPoint,
      toFixedFix = (n, v) => {
        // Fix for IE parseFloat(0.55).toFixed(0) = 0;
        const k = Math.pow(10, v);
        return Math.round(n * k) / k;
      },
      s = (prec ? toFixedFix(newNum, prec) : Math.round(newNum)).toString().split('.');
    if (s[0].length > 3) {
      s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
    }
    if ((s[1] || '').length < prec) {
      s[1] = s[1] || '';
      s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
  }

  changeNumberFormat(event, target) {
    this.dispatch(new PatchNumberFormatAction({target, value: event}));
  }

  changeLog($event) {
    console.log($event);
  }

  saveChanges() {
    let financialPerspective = '';
    let country = '';
    _.forEach(this.selectedFinancialPerspectiveELT, item => financialPerspective = financialPerspective + item + ' ');
    _.forEach(this.selectedCountries, item => country = country + item.countryCode + ' ');
    //_.forEach(state.contractOfInterest.uwUnit, item => uwUnit = uwUnit + item.id + ' ');

    console.log(this.shortDate, this.shortDate);
    const configData = {
      decimalSeparator: this.decimalSeparator,
      decimalThousandSeparator: this.decimalThousandSeparator,
      defaultRmsInstance: this.selectedRMSInstance,
      display: null,
      financialPerspectiveELT: financialPerspective,
      financialPerspectiveEPM: this.selectedFinancialPerspectiveEPM,
      importPage: this.importPage,
      longDate: this.longDate,
      longTime: this.longTime,
      negativeFormat: this.negativeFormat,
      numberHistory: 5,
      numberOfDecimals: this.numberOfDecimals,
      returnPeriod: 0,
      shortDate: this.shortDate,
      shortTime: this.shortTime,
      targetAnalysisCurrency: this.selectedTargetAnalysisCurrency,
      targetCurrency: this.selectedTargetCurrency,
      timeZone: this.timeZone,
      countryCode: country,
    };


    this.dispatch(new PostNewConfigAction(configData));
  }

  changeSearch(event) {
    this.store$.dispatch(new PatchSearchStateAction({key: 'searchTarget', value: event}));
  }

}
