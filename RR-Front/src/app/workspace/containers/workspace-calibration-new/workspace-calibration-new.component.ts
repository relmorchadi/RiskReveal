import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {BaseContainer} from "../../../shared/base";
import {StateSubscriber} from "../../model/state-subscriber";
import {Store} from "@ngxs/store";
import {Router} from "@angular/router";
import * as fromWorkspaceStore from '../../store';
import * as _ from "lodash";

@Component({
  selector: 'app-workspace-calibration-new',
  templateUrl: './workspace-calibration-new.component.html',
  styleUrls: ['./workspace-calibration-new.component.scss']
})
export class WorkspaceCalibrationNewComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  wsIdentifier: string;
  wsId: string;
  uwYear: string;

  data: any[];
  epMetrics: any;
  adjustments: any;
  loading: boolean;

  //Table Config
  columns: any [];
  mode: 'default' | 'grouped';
  isGrouped: boolean
  rowKeys: any;

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);

    this.data = [];
    this.mode = "default";
    this.isGrouped= false;
    this.columns = [
      {type: "arrow", width: "7"},
      {field: 'pltId', header: 'PLT Id', width: "15"},
      {field: 'pltName', header: 'PLT Name', width: "40"},
      {header: 'Peril',field: 'peril', icon:'', width: "40px", filter: true, sort: true},
      {field: 'regionPerilCode', header: 'Region Peril', width: "40"},
      {header: 'Region Peril Name',field: 'regionPerilDesc', width: "40px", icon:'', filter: true, sort: true},
      {field: 'grain', header: 'Grain', width: "45"},
      {header: 'Vendor System',field: 'vendorSystem', width: "40px", icon:'', filter: true, sort: true},
      {field: 'rap', header: 'RAP', width: "70"},
      {header: '',field: 'status', width: "40px", icon:'', filter: false, sort: false},
      {header: 'Overall LMF',field: 'overallLmf', width: "40px", icon:'', filter: false, sort: false},
      {header: 'Base',field: 'base', width: "40px", icon:'', filter: false, sort: false},
      {header: 'Default',field: 'default', width: "40px", icon:'', filter: false, sort: false},
      {header: 'Client',field: 'client', width: "40px", icon:'', filter: false, sort: false},
      {header: 'Inuring',field: 'inuring', width: "40px", icon:'', filter: false, sort: false},
      {header: 'Post-Inuring',field: 'postInuring', width: "40px", icon:'', filter: false, sort: false},
    ];
    this.rowKeys= {};
  }

  ngOnInit() {

  }

  ngOnDestroy(): void {
    this.destroy();
  }

  selectState = ({wsIdentifier, data : {wsId, uwYear, calibrationNew} }) => ({wsIdentifier, wsId, uwYear, calibrationNew});

  patchState(state): void {
    const {
      wsIdentifier,
      wsId, uwYear,
      calibrationNew
    } = this.selectState(state);

    console.log(state);

    this.iniRouting(wsIdentifier, wsId, uwYear);
    this.initComponent(calibrationNew);


    this.detectChanges();
  }

  iniRouting(wsIdentifier, wsId, uwYear) {

    if(!this.wsId && wsId && !this.uwYear && uwYear ) {
      this.loadCalibrationPlts(wsId, uwYear);
    }

    this.wsIdentifier = wsIdentifier;
    this.wsId = wsId;
    this.uwYear = uwYear;
  }

  loadCalibrationPlts(wsId: string, uwYear: number) {
    this.dispatch(new fromWorkspaceStore.LoadGroupedPltsByPure({wsId ,uwYear}));
  }

  initComponent(state) {

    const {
      plts,
      epMetrics,
      adjustments,
      loading
    } = state;

    this.data = plts;
    this.epMetrics = epMetrics;
    this.adjustments = adjustments;
    this.loading = loading;

    _.forEach(this.data, row => {
      this.rowKeys[row.pltId] = false;
    });

  }

}
