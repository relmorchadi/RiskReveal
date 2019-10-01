import {ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {BaseContainer} from "../../../../shared/base";
import {Router} from "@angular/router";
import {Actions, ofActionDispatched, Store} from "@ngxs/store";
import {RefreshInuringGraph} from "../../../store/actions";

@Component({
  selector: 'inuring-canvas-tab',
  templateUrl: './inuring-canvas-tab.component.html',
  styleUrls: ['./inuring-canvas-tab.component.scss'],
})
export class InuringCanvasTabComponent extends BaseContainer implements OnInit, OnDestroy {

  readonly contractTypes: Array<any> = [
    // {label: 'XoL Event'},
    {label: 'Pro-Rata Quota Share'},
    {label: 'XoL Annual Aggregate'},
    {label: 'XoL Stop Loss'},
    {label: 'XoL Aggregate (Inner) Deductible'},
    {label: 'Filter N Events Excess Treshold'},
    {label: 'Filter N Events Excess T1 and Exclusive'},
    {label: 'XoL Drop Down Cascade'},
    {label: 'Franchise Capped'},
    {label: 'Franchise Groundup'},
    {label: 'Franchise Excess'},
  ];

  showCreationPopup = false;

  stepConfig = {
    wsId: 'TB01735', uwYear: '2019', plts: []
  };

  collapses = {
    contractDetails: true,
    inboundRoutedLoss: true
  };

  epMetricsCurrencySelected: any = 'EUR';

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

  epMetricsFinancialUnitSelected: any = 'Million';

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

  @Input() appendedNodes;

  @Input('wsConfig')
  wsConfig: { wsId: string, year: number };
  @Output('showCreationPopup') showCreationPopupEmitter: EventEmitter<any> = new EventEmitter();
  @Output('changeAppendedNodes') changeAppendedNodesEmitter: EventEmitter<any> = new EventEmitter();

  constructor(private _router: Router, private _cdRef: ChangeDetectorRef, private _store: Store, private _actions: Actions) {
    super(_router, _cdRef, _store)
  }

  ngOnInit() {
    this._actions.pipe(
      this.unsubscribeOnDestroy,
      ofActionDispatched(RefreshInuringGraph)
    ).subscribe(action => {
      console.log('Actions here')
      if (action instanceof RefreshInuringGraph) {
        this.changeAppendedNodesEmitter.emit([]);
      }
    });

  }

  onContractDrop($event) {
    console.log('[Inuring Details] Contact dop evt', $event);
  }

  // handleSave($event) {
  //   this._store.dispatch(new AddInputNode($event));
  // }

  createNode() {
    console.log('Dispatch node creation action');

  }

  /* setSelectedWs = ($event) => {
   };
 */

  /*setSelectedPlts(selectedPlts) {
    this.appendedNodes.push(selectedPlts);
    this._store.dispatch(new AddInputNode({plts: selectedPlts, index: this.appendedNodes.length}));
  }*/

  ngOnDestroy(): void {
    this.destroy();
  }


  onShowCreationPopup() {
    this.showCreationPopupEmitter.emit(true)
  }
}
