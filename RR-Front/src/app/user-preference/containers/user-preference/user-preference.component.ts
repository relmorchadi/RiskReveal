import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Actions, ofActionSuccessful, Select, Store} from '@ngxs/store';
import {Observable} from 'rxjs';
import * as _ from 'lodash';
import {GeneralConfigState, SearchNavBarState} from '../../../core/store/states';
import {
  PatchDateFormatAction,
  PatchImportDataAction,
  PatchNumberFormatAction,
  PatchSearchStateAction,
  PatchWidgetDataAction,
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

  financialPerspectiveELT = [{label: 'Net Loss Pre Cat (RL)', value: 'Net Loss Pre Cat (RL)'},
    {label: 'Gross Loss (GR)', value: 'Gross Loss (GR)'},
    {label: 'Net Cat (NC)', value: 'Net Cat (NC)'}];
  financialPerspectiveEPM = ['Facultative Reinsurance Loss', 'Ground UP Loss (GU)', 'Variante Reinsurance Loss'];
  targetCurrency = ['Main Liability Currency (MLC)', 'Underlying Loss Currency', 'User Defined Currency'];
  targetAnalysisCurrency = ['Main Liability Currency (MLC)', 'Underlying Loss Currency', 'User Defined Currency'];
  rmsInstance = ['AZU-P-RL17-SQL14', 'AZU-P-RL17-SQL15'];

  countries: any;
  uwUnits: any;

  numberCollapse = false;
  defaultImport;

  @Select(GeneralConfigState)
  state$: Observable<GeneralConfig>;
  state: GeneralConfig = null;

  constructor(public location: Location,
              private cdRef: ChangeDetectorRef,
              private actions$: Actions,
              private notification: NotificationService,
              private router$: Router, public store$: Store) {
    super(router$, cdRef, store$);
    this.countries = Data.countries;
    this.uwUnits = Data.uwUnit;
  }

  ngOnInit() {
    this.defaultImport = localStorage.getItem('importConfig');

    this.store$.select(SearchNavBarState.getSearchTarget).subscribe(
      value => this.searchTarget = value
    );

    this.actions$.pipe(ofActionSuccessful(PostNewConfigSuccessAction)).subscribe( data => {
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

  changePerspective(event, target) {
    this.dispatch(new PatchDateFormatAction({target, value: event}))
  }

  changeImportData(event, target) {
    this.dispatch(new PatchImportDataAction({target, value: event}))
  }

  changeNumberFormat(event, target) {
    this.dispatch(new PatchNumberFormatAction({target, value: event}));
  }

  widgetConfigChange(event, target) {
    this.dispatch(new PatchWidgetDataAction({target, value: event}));
  }

  saveChanges() {
    this.dispatch(new PostNewConfigAction(this.state));
  }

  changeSearch(event) {
    this.store$.dispatch(new PatchSearchStateAction({key: 'searchTarget', value: event}));
  }

}
