import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges
} from '@angular/core';
import {Message} from '../../../message';
import * as rightMenuStore from './store';
import * as pltDetailsPopUpItemStore from '../plt-details-pop-up-item/store';
import * as _ from 'lodash';
import {BaseContainer} from "../../../base";
import {Store} from "@ngxs/store";
import {Router} from "@angular/router";
import {CalibrationAPI} from "../../../../workspace/services/api/calibration.api";
import {PltApi} from "../../../../workspace/services/api/plt.api";
import {filter} from "rxjs/operators";

@Component({
  selector: 'app-plt-right-menu',
  templateUrl: './plt-right-menu.component.html',
  styleUrls: ['./plt-right-menu.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PltRightMenuComponent extends BaseContainer implements OnInit, OnDestroy, OnChanges  {

  @Input('Inputs') inputs: rightMenuStore.Input;

  pltPopUpItemConfig: pltDetailsPopUpItemStore.Input[];

  _input(key): any {
    return this.inputs[key];
  }

  @Output() actionDispatcher: EventEmitter<Message>= new EventEmitter<Message>();

  currencies = [
    {id: '1', name: 'Euro', label: 'EUR'},
    {id: '2', name: 'Us Dollar', label: 'USD'},
    {id: '3', name: 'Britsh Pound', label: 'GBP'},
    {id: '4', name: 'Canadian Dollar', label: 'CAD'},
    {id: '5', name: 'Moroccan Dirham', label: 'MAD'},
    {id: '5', name: 'Swiss Franc', label: 'CHF'},
    {id: '5', name: 'Saudi Riyal', label: 'SAR'},
    {id: '6', name: 'Bitcoin', label: 'XBT'},
    {id: '7', name: 'Hungarian forint', label: 'HUF'},
    {id: '8', name: 'Singapore Dollars', label: 'SGD'}
  ];

  units = [
    {id: '3', label: 'Billion'},
    {id: '1', label: 'Thousands'},
    {id: '2', label: 'Million'},
    {id: '4', label: 'Unit'}
  ];

  metrics = [
    {
      metricID: '1',
      retrunPeriod: '10000',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '2',
      retrunPeriod: '5,000',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '4',
      retrunPeriod: '1,000',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '5',
      retrunPeriod: '500',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '6',
      retrunPeriod: '250',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '7',
      retrunPeriod: '100',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '8',
      retrunPeriod: '50',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '9',
      retrunPeriod: '25',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '10',
      retrunPeriod: '10',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '11',
      retrunPeriod: '5',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    },
    {
      metricID: '12',
      retrunPeriod: '2',
      OEP: '291,621.790',
      AEP: '291,621.790',
      TVaR_OEP: '3214,654.789',
      TVaR_AEP: '458,711.620'
    }
  ];

  dependencies = [
    {id: 1, title: 'ETL', content: 'RDM: CC_IT1607_XYZ_Surplus_R', chip: 'Analysis ID: 149'},
    {id: 2, title: 'PTL', content: 'ID 9867', chip: 'Pure PLT'},
    {id: 2, title: 'PTL', content: 'ID 9888', chip: 'Thead PLT'},
    {id: 2, title: 'PTL', content: 'ID 9901', chip: 'Cloned PLT'}
  ];
  fullViewActive: boolean;
  currentFullView: any;

  //Summary

  selectedPLT: any;
  pltCache: any;

  //Summary EPMetrics

  constants: {
    financialUnits: string[],
    curveTypes: any[],
    currencies: string[]
  };

  summaryEpMetricsConfig: {
    selectedCurrency: string;
    selectedFinancialUnit: string;
    selectedCurveType: any[];
  };

  epMetricsTableConfig: {
    columns: any[];
  };

  epMetrics;

  epMetricsSubscriptions: any;

  rps;

  epMetricsLosses;

  epMetricsLossesSubscriptions: any;

  constructor(
      _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
      private calibrationAPI: CalibrationAPI,
      private pltAPI: PltApi
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    console.log("init");
    this.selectedPLT= {};
    this.pltCache= {};
    this.pltPopUpItemConfig= [
      {
        title: "PLT Thread",
        cols: {
          summary: [{header: "Target RAP Code", field: "targetRAPCode"}, {header: "Target RAP Desc", field: "targetRAPDesc"}, {header: "Root Region Peril Code", field: "minimumGrainRPCode"}, {header: "Root Region Peril Desc", field: ""}, {header: "Region Peril Code", field: "regionPerilCode"}, {header: "Region Peril Desc", field: "regionPerilDesc"}, {header: "Grain", field: "grain"}, {header: "Grouped PLT", field: "groupedPlt", type: "indicator"},{header: "Cloned PLT", field: "clonedPlt", type: "indicator"}, {header: "OccurenceBasis", field: "occurenceBasis"}, {header: "Base Adjusted", field: "baseAdjustment", type: "indicator"}, {header: "Default Adjusted", field: "defaultAdjustment", type: "indicator"}, {header: "Client Adjusted", field: "clientAdjustment", type: "indicator"}, {header: "PLT Ccy", field: "pltCcy"}, {header: "Create Date", field: "createDate"}, {header: "Created By", field: "createdBy"}, {header: "Archived", field: "archived", type: "indicator"}, {header: "Archived Date", field: "archivedDate"}],
          sections: [{ title: "Thread Details", rows: [{label: "Target RAP Code", field: "targetRAPCode"}, {label: "Target RAP Desc", field: "targetRAPDesc"}, {label: "Root Region Peril Code", field: "minimumGrainRPCode"}, {label: "Root Region Peril Desc", field: ""}, {label: "Minimum Grain Grain RP Code", field: "minimumGrainRPCode"}, {label: "Minimum Grain RP Desc", field: ""}, {label: "Region Peril Code", field: "regionPerilCode"}, {label: "Region Peril Desc", field: "regionPerilDesc"}, {label: "Peril Group Code", field: "perilGroupCode"}, {label: "Peril Group Desc", field: "perilGroupDesc"}, {label: "Grain", field: "grain"}, {label: "Grouped PLT", field: "groupedPlt", type: "indicator"},{label: "Cloned PLT", field: "clonedPlt", type: "indicator"}, {label: "Occurence Basis Default", field: "defaultOccurenceBasis"}, {label: "OccurenceBasis", field: "occurenceBasis"}, {label: "Base Adjusted", field: "baseAdjustment", type: "indicator"}, {label: "Default Adjusted", field: "defaultAdjustment", type: "indicator"}, {label: "Client Adjusted", field: "clientAdjustment", type: "indicator"}, {label: "PLT Ccy", field: "pltCcy"}, {label: "Create Date", field: "createDate"}, {label: "Created By", field: "createdBy"}, {label: "Archived", field: "archived"}, {label: "Archived Date", field: "archivedDate"}]}]
        }
      },
      {
        title: "EP & Statistics",
        cols: {
          summary: [
            {header: "AAL", field: "aal"},
            {header: "CoV", field: "cov"},
            {header: "Std Dev", field: "stdDev"},
          ]
        },
        table: {
            cols: ['Return Period', 'AEP', 'OEP'],
            data: [
              {
                'Return Period': 10,
                'AEP': 'aep10',
                'OEP': 'oep10'
              },
              {
                'Return Period': 50,
                'AEP': 'aep50',
                'OEP': 'oep50'
              },
              {
                'Return Period': 100,
                'AEP': 'aep100',
                'OEP': 'oep100'
              },
              {
                'Return Period': 250,
                'AEP': 'aep250',
                'OEP': 'oep250'
              },
              {
                'Return Period': 500,
                'AEP': 'aep500',
                'OEP': 'oep500'
              },
              {
                'Return Period': 1000,
                'AEP': 'aep1000',
                'OEP': 'oep1000'
              },
            ]
        },
        isTable: true
      },
      {
        title: "Cloning Source",
        cloningSource: true,
        cols: {
          summary: [
            {header: "Clone Source PLT", field: "clonedSourcePlt"}, {header: "Clone Source Project", field: "clonedSourceProject"}, {header: "Clone Source Workspace", field: "clonedSourceWorkspace"}
          ]
        }
      },
      {
        title: "Project",
        cols: {
          summary: [
            {header: "Project ID", field: "projectId"}, {header: "Project Name", field: "projectName"}, {header: "Project Description", field: "projectDescription"}, {header: "Project Type", field: "projectType"}, {header: "Assigned Analyst", field: "assignedTo"}, {header: "Created Date", field: "projectCreatedDate", type: "date"}, {header: "Created By", field: "createdBy"}, {header: "Car ID", field: "carId"}, {header: "CAR Source System", field: "carSourceSystem"}, {header: "CAR Source System Ref", field: "carSourceSystemRef"}, {header: "CAR Raised Ref", field: "carRaisedRef"}, {header: "Master Project ID", field: "masterProjectId"}, {header: "Master Project Name", field: "masterProjectName"}, {header: "Master Project Description", field: "masterProjectDescription"}, {header: "Master Project Workspace", field: "masterProjectWorkspace"}, {header: "Master Project Client", field: "masterProjectClient"}, {header: "MGA Details", field: ""}
          ],
          sections: [
            {
              title: "Project", rows: [{label: "Project ID", field: "projectId"}, {label: "Project Name", field: "projectName"}, {label: "Project Description", field: "projectDescription"}, {label: "Project Type", field: "projectType"}, {label: "Assigned Analyst", field: "assignedTo"}, {label: "Created Date", field: "projectCreatedDate", type: "date"}, {label: "Created By", field: "createdBy"}
            ],
            },
            {
              title: "CAR Details", rows: [{label: "Car ID", field: "carId"}, {label: "CAR Source System", field: "carSourceSystem"}, {label: "CAR Source System Ref", field: "carSourceSystemRef"}, {label: "CAR Raised Ref", field: "carRaisedRef"}]
            },
            {
              title: "Master Project", rows: [{label: "Master Project ID", field: "masterProjectId"}, {label: "Master Project Name", field: "masterProjectName"}, {label: "Master Project Description", field: "masterProjectDescription"}, {label: "Master Project Workspace", field: "masterProjectWorkspace"}, {label: "Master Project Client", field: "masterProjectClient"}]
            }
            ]
        }
      },
      {
        title: "Publishing Status",
        cols: {
          summary: [
            {header: "(X) Published to xAct", field: "xActPublication"}, {header: "Published to Pricing Date", field: "xActPublicationDate", type: "date"}, {header: "Published to Pricing By", field: "publishedBy"}, {header: "(P) Priced PLT", field: "xActPriced", type: "indicator"}, {header: "(A) Published to ARC", field: "arcPublication", type: "indicator"}, {header: "Published to ARC Date", field: ""}, {header: "Published to ARC By", field: ""}
          ],
          sections: [
            { title: "Pricing", rows: [{label: "(X) Published to xAct", field: "xActPublication"}, {label: "Published to Pricing Date", field: "xActPublicationDate", type: "date"}, {label: "Published to Pricing By", field: "publishedBy"}, {label: "(P) Priced PLT", field: "xActPriced"}]},
            { title: "Accumulation", rows: [{label: "(A) Published to ARC", field: "arcPublication"}, {label: "Published to ARC Date", field: ""}, {label: "Published to ARC By", field: ""}]}
          ]
        }
      },
      {
        title: "Pure PLT",
        cols: {
          summary: [
            {header: "Pure PLT ID", field: "pureId"}, {header: "Pure PLT Name", field: "pureName"},
          ],
          sections: [
            { title: "Pure PLT", rows: [{label: "Pure PLT ID", field: "pureId"}, {label: "Pure PLT Name", field: "pureName"}] }
          ]
        }
      },
      {
        title: "Group PLT",
        cols: {
          summary: [
            {header: "Group PLT ID", field: ""}, {header: "Group PLT Name", field: ""}, {header: "Created Date", field: ""}, {header: "Created By", field: ""}, {header: "Source PLT Count", field: ""}, {header: "PLT Ccy", field: ""}, {header: "Source PLT ID", field: ""}, {header: "Source PLT Ccy", field: ""}, {header: "Source PLT Name", field: ""},
          ],
          sections: [
            {
              title: "Group PLT", rows: [{label: "Group PLT ID", field: ""}, {label: "Group PLT Name", field: ""}, {label: "Created Date", field: ""}, {label: "Created By", field: ""}, {label: "Source PLT Count", field: ""}, {label: "PLT Ccy", field: ""}]
            },
            {
              title: "Source PLT List", rows: [{label: "Source PLT ID", field: ""}, {label: "Source PLT Ccy", field: ""}, {label: "Source PLT Name", field: ""}]
            }
          ]
        }
      },
      {
        title: "Source Loss Table",
        cols: {
          summary: [
            {header: "Loss Table Type", field: "lossTableType"}, {header: "Loss Table Id", field: "lossTypeId"}, {header: "Vendor System", field: "vendorSystem"}, {header: "Modelling Data Source", field: "modellingDataSource"}, {header: "Source Analysis Id", field: "sourceAnalysisId"}, {header: "Source Analysis Name", field: "sourceAnalysisName"}, {header: "Source Analysis Description", field: "sourceAnalysisDescription"}, {header: "Source Financial Perspective", field: "sourceFinancialPerspective"}
          ],
          sections: [
            {
              title: "Source Loss Table", rows : [{label: "Loss Table Type", field: "lossTableType"}, {label: "Loss Table Id", field: "lossTypeId"}, {label: "Vendor System", field: "vendorSystem"}, {label: "Modelling Data Source", field: "modellingDataSource"}, {label: "Source Analaysis Id", field: "sourceAnalysisId"}, {label: "Source Analysis Name", field: "sourceAnalysisName"}, {label: "Source Analysis Description", field: "sourceAnalysisDescription"}, {label: "Source Financial Perspective", field: "sourceFinancialPerspective"}]
            }
          ]
        }
      },
      {
        title: "Inuring Package",
        cols: {
          summary: [
            {header: "ID", field: "inuringPackageId"}, {header: "Name", field: "inuringPackageName"}, {header: "Description", field: "inuringPackageDescription"}, {header: "Package Status", field: "inuringPackageStatus"}, {header: "Locked Indicator", field: "inuringPackageLocked"}, {header: "Created On", field: "inuringCreatedOn"}, {header: "Last Updated", field: "inuringLastModifiedOn", type: "date"}, {header: "Last Updated By", field: "inuringLastModifiedBy"},
          ],
          sections: [
            {
              title: "Inuring Pkg", rows: [{label: "ID", field: "inuringPackageId"}, {label: "Name", field: "inuringPackageName"}, {label: "Description", field: "inuringPackageDescription"}, {label: "Package Status", field: "inuringPackageStatus"}, {label: "Locked Indicator", field: "inuringPackageLocked"}, {label: "Created On", field: "inuringCreatedOn"}, {label: "Last Updated", field: "inuringLastModifiedOn"}, {label: "Last Updated By", field: "inuringLastModifiedBy"},]
            }
          ]
        }
      }
    ];
    this.fullViewActive= false;
    this.currentFullView= {};

    //Summary EpMetrics

    this.constants = {
      curveTypes: [{ label: 'OEP', value: 'OEP'}, { label: 'OEP-TVAR', value: 'OEP-TVAR' }, { label: 'AEP', value: 'AEP'} , { label: 'AEP-TVAR', value: 'AEP-TVAR'} ],
      financialUnits: [ 'Unit', 'Thousands', 'Million', 'Billion'],
      currencies: ['USD', 'EUR', 'MAD']
    };

    this.summaryEpMetricsConfig = {
      selectedCurrency: 'USD',
      selectedCurveType: ['OEP'],
      selectedFinancialUnit: 'Unit'
    };

    this.epMetricsTableConfig = {
      columns: []
    };

    this.epMetricsLosses= {};
    this.epMetricsLossesSubscriptions= {};
    this.epMetrics= {};
    this.epMetricsSubscriptions= {};
    this.rps= {};
  }

  ngOnInit() {
  }

  ngOnDestroy() {
    this.destroy();
  }

  ngOnChanges(changes: SimpleChanges) {
    const {
      inputs: {
        currentValue,
        previousValue
      }
    } = changes;

    if(!previousValue) {
      this.loadSummary(currentValue.pltHeaderId);
      this.loadTab(currentValue.selectedTab.index);
    } else {
      if(previousValue.pltHeaderId != currentValue.pltHeaderId) {
        console.log(this.epMetricsTableConfig.columns);
        this.appendColumn('OEP');
        console.log(this.epMetricsTableConfig.columns);
        this.loadSummary(currentValue.pltHeaderId);
        this.loadTab(currentValue.selectedTab.index);
      }
    }

  }

  closeDrawer() {
    this.actionDispatcher.emit({
      type: this.inputs['visible'] ? rightMenuStore.closeDrawer : rightMenuStore.openDrawer
    })
  }

  popupActionHandler(action: Message) {
    switch (action.type) {

      case pltDetailsPopUpItemStore.openSection:
        this.openSection(action.payload);
        break;

      case pltDetailsPopUpItemStore.closeSection:
        this.closeSection();
        break;
    }
  }

  openSection(title) {
    this.currentFullView = _.find(this.pltPopUpItemConfig, ['title', title]);
    if(this.currentFullView.cols.sections && this.currentFullView.cols.sections.length) this.fullViewActive= true;
  }

  closeSection() {
    this.fullViewActive= false;
    this.currentFullView= {};
  }

  selectTab(index, title) {
    this.actionDispatcher.emit({
      type: rightMenuStore.setSelectedTabByIndex,
      payload: {index, title}
    })
  }

  loadTab(index: number) {
    this.handleLazyTabs(index);
  }

  handleLazyTabs(tabIndex) {
    switch (tabIndex) {
      case 0:
        this.loadSummary(this.inputs.pltHeaderId);
        break;
      case 1:
        this.handleEpMetricsTabLoading(this.inputs.pltHeaderId, 'OEP');
        break;
      default:
        console.log(tabIndex);
        break;
    }
  }

  loadSummary(pltHeaderId) {
    if(!this.pltCache[pltHeaderId]) {
      this.pltAPI.getSummary(pltHeaderId)
          .pipe(this.unsubscribeOnDestroy)
          .subscribe(summary => {
            this.pltCache[summary.pltId] = summary;
            this.selectedPLT= summary;
            this.detectChanges();
          })
    } else {
      this.selectedPLT = this.pltCache[pltHeaderId];
    }
  }

  handleEpMetricsTabLoading(pltHeaderId,curve) {
    this.loadEpMetrics(pltHeaderId, curve);
    this.loadEpMetricsLosses(pltHeaderId);
  }

  loadEpMetrics(pltHeaderId, curve) {
    if(!this.epMetrics[pltHeaderId]) this.epMetrics[pltHeaderId] = {};
    if(!this.epMetrics[pltHeaderId] || !_.keys(this.epMetrics[pltHeaderId][curve]).length) {
      this.epMetrics[pltHeaderId][curve]= {};
      this.rps[pltHeaderId]= [];
      if(!this.epMetricsSubscriptions[pltHeaderId]) this.epMetricsSubscriptions[pltHeaderId]= {};
      this.epMetricsSubscriptions[pltHeaderId][curve] = this.calibrationAPI.loadSinglePltEpMetrics(this.inputs.pltHeaderId, 1, _.replace(curve, '-',''))
          .pipe(
              this.unsubscribeOnDestroy,
          )
          .subscribe((data: any[]) => {
            console.log(data);
            if(data.length) {
              const metrics = _.get(data, '0');

              const {
                pltId,
                curveType
              } = metrics;

              if(!this.rps[pltId].length) this.rps[pltId] = _.keys(_.omit(metrics, ['pltId', 'curveType', 'AAL']));

              if(curveType && (pltId || pltId === 0)) {
                this.epMetrics[pltId]= {
                  ...this.epMetrics,
                  [curveType]: metrics
                };
                this.appendColumn(curveType);
                console.log(this.epMetricsTableConfig.columns);
                console.log(this.epMetrics[pltId])
              }


            } else {
              if(curve && (pltHeaderId || pltHeaderId === 0)) {
                this.epMetrics[pltHeaderId]= {
                  ...this.epMetrics,
                  [curve]: {}
                };
                this.appendColumn(curve);
                console.log(this.epMetricsTableConfig.columns);
                console.log(this.epMetrics[pltHeaderId])
              }
            }
            this.detectChanges();
            this.epMetricsSubscriptions[pltHeaderId][curve] && this.epMetricsSubscriptions[pltHeaderId][curve].unsubscribe();
          });
    }
  }

  loadEpMetricsLosses(pltHeaderId) {
    if(!this.epMetricsLosses[pltHeaderId]) {
      this.epMetricsLosses[pltHeaderId]= {};

      this.calibrationAPI.loadSinglePLTSummaryStats(pltHeaderId)
          .pipe(
              this.unsubscribeOnDestroy
          )
          .subscribe(losses => {
            this.epMetricsLosses[pltHeaderId]= _.get(losses, '0');
            this.detectChanges();
          })

    }


  }

  handleEpMetrics(action: Message) {

    switch (action.type) {
      case "Show Metric":
        this.showMetric(action.payload);
        break;

      case "Hide Metric":
        this.hideMetric(action.payload);
        break;

      case "Select Financial Unit":
        this.selectFinancialUnit(action.payload);
        break;

      default:
        console.log(action);
        break;
    }

    console.log(this.epMetricsTableConfig, this.epMetrics);

  }

  generateCol(header) {
    return {field: header, header};
  }

  appendColumn(curveType) {
    this.epMetricsTableConfig= {
      columns: _.uniqBy([...this.epMetricsTableConfig.columns, this.generateCol(curveType)], col => col.field)
    }
  }

  showMetric({curveTypes, difference}) {

    _.forEach(difference, curveType => {
      this.handleEpMetricsTabLoading(this.inputs.pltHeaderId, curveType);
    });
    this.summaryEpMetricsConfig = {
      ...this.summaryEpMetricsConfig,
      selectedCurveType: curveTypes
    };
  }

  hideMetric({curveTypes, difference}) {
    this.epMetricsTableConfig= {
      columns: _.filter(this.epMetricsTableConfig.columns, col => !_.find(difference, column => column == col.header))
    };
    this.summaryEpMetricsConfig = {
      ...this.summaryEpMetricsConfig,
      selectedCurveType: curveTypes
    };
  }

  selectFinancialUnit(financialUnit) {
    this.summaryEpMetricsConfig = {
      ...this.summaryEpMetricsConfig,
      selectedFinancialUnit: financialUnit
    };
  }
}
