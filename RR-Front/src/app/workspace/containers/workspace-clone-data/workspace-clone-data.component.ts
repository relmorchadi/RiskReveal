import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Store} from '@ngxs/store';
import {MessageService} from 'primeng/api';
import {combineLatest} from 'rxjs';
import {WorkspaceMainState} from '../../../core/store/states';
import * as _ from 'lodash'
import {PltMainState, WorkspaceState} from '../../store/states';
import * as fromWS from '../../store'
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {take} from 'rxjs/operators';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';

interface SourceData {
  plts: any[];
  detail: string;
  wsId: string;
  uwYear: string
}

@Component({
  selector: 'app-workspace-clone-data',
  templateUrl: './workspace-clone-data.component.html',
  styleUrls: ['./workspace-clone-data.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [MessageService]
})
export class WorkspaceCloneDataComponent extends BaseContainer implements OnInit , StateSubscriber{

  constructor(
    private route$: ActivatedRoute,
    private prn: PreviousNavigationService,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.activeSubTitle= 0;
    this.cloningToItem= true;
    this.projectForClone= -1;
    this.searchWorkSpaceModal= false;
    this.data= [
      {
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },{
      id: 77897,
      year: 2019,
      wsContext: "02PY376",
      cedantName: "A.M.M.A",
      cedant: 87989,
      country: "Belgium"
    },];
    this.browesing= false;
    this.from = {
      detail: '',
      plts: [],
      uwYear: '',
      wsId: ''
    };
    this.to= {
      detail: '',
      plts: [],
      uwYear: '',
      wsId: ''
    };
    this.cloneConfig= {
      currentSourceOfItems: 'to',
      summary: {
        'Pre-Inured PLTs': {
          'icon': 'icon-assignment_24px',
          'color': '#c38fff',
          'value': 0
        },
        'Post-Inured PLTs': {
          'icon': 'fa fa-code-fork',
          'color': '#c38fff',
          'value': 0
        },
        'Inuring Packages': {
          'icon': 'icon-layer-group',
          'color': '#f5a623',
          'value': 0
        },
        'Sources Projects': {
          'icon': 'icon-assignment_24px',
          'color': '#33d0bb',
          'value': 0
        }
      }
    };
    this.multiSteps= true;
    this.stepConfig= {
      wsId: '',
      uwYear: '',
      plts: []
    }
  }

  subTitle= {
    0: 'Clone Workspaces Assets',
    1: 'Source Workspace Selection',
    2: 'Target Workspace Selection'
  };

  activeSubTitle: number;
  cloningToItem: boolean;
  projectForClone: number;
  searchWorkSpaceModal: boolean;
  data: any[];
  from: SourceData;
  fromCache: SourceData;
  to: SourceData;
  toCache: SourceData;
  browesing: boolean;
  showDeleted: boolean;
  workspaceId: string;
  uwy: number;
  subs: any[]= [];

  cloneConfig: {
    currentSourceOfItems: string,
    summary: any;
  }
  multiSteps: boolean;
  stepConfig: {
    wsId: string,
    uwYear: string,
    plts: any[]
  };

  ngOnInit() {
    this.subs.push(
      combineLatest(
        this.select(WorkspaceState.getCloneConfig),
        this.route$.params,
        this.select(WorkspaceState.getCurrentWS).pipe(take(2))
      ).pipe(this.unsubscribeOnDestroy).subscribe(([navigationPayload, {wsId, year}, currentWS]: any) => {
        const url = this.prn.getPreviousUrl();
        this.workspaceId= wsId;
        this.uwy= year;
        console.log({
          prn: url,
          navigationPayload, wsId, year, currentWS
        });
        if (url == 'PltBrowser' && _.get(navigationPayload, 'payload.wsId', null) && _.get(navigationPayload, 'payload.uwYear', null)) {
          this.from = {
            ...navigationPayload.payload,
            detail: currentWS && currentWS.cedantName + ' | ' + currentWS.workspaceName + ' | ' + currentWS.uwYear + ' | ' + currentWS.wsId
          };
          this.to = {
            detail: '',
            plts: [],
            wsId: '',
            uwYear: ''
          };
          this.setCloneConfig('currentSourceOfItems', 'to');
          this.multiSteps = false;
          this.stepConfig = {
            wsId: '',
            uwYear: '',
            plts: []
          };
          this.searchWorkSpaceModal = true;
        } else {
          this.to = {
            wsId: wsId,
            uwYear: year,
            plts: [],
            detail: currentWS && currentWS.cedantName + ' | ' + currentWS.workspaceName + ' | ' + currentWS.uwYear + ' | ' + currentWS.wsId
          };
          this.from = {
            detail: '',
            plts: [],
            wsId: '',
            uwYear: ''
          };
        }
        if (this.from.plts.length > 0) {
          this.cloneConfig = {
            ...this.cloneConfig,
            summary: {...this.summaryCache}
          }
        } else {
          const k = {};

          _.forEach(this.cloneConfig.summary, (v, key) => {
            k[key] = {...v, value: 0};
          })

          this.cloneConfig = {
            ...this.cloneConfig,
            summary: {...k}
          }
        }
        this.fromCache = {...this.from};
        this.toCache = {...this.to};
        this.detectChanges();
      })
    )
  }

  setSubTitle(number: number) {
    this.activeSubTitle= number;
  }

  onRadioChange($event) {
    this.projectForClone= $event;
  }

  handleModalClick() {
    if(!this.browesing) {
      this.browesing= true;
    }else {
      this.searchWorkSpaceModal= false;
      this.browesing= false;
    }
  }

  goToSearchWorkspace() {
    this.searchWorkSpaceModal=true;
    this.browesing= false;
  }

  cancelModal() {
    this.browesing= false;
    this.searchWorkSpaceModal= false;
  }

  getPlts() {
    this.browesing=true;
  }

  ngOnDestroy(): void {
    this.dispatch(new fromWS.setCloneConfig({
      cloneConfig: {},
      wsIdentifier: this.workspaceId + '-'+ this.uwy
    }));
    this.destroy();
  }

  setSelectedWs(currentSourceOfItems: string,$event: any) {
    if(currentSourceOfItems == 'from') {
      this.from = {...this.from, wsId: $event.workSpaceId, uwYear: $event.uwYear, detail: $event.cedantName+' | '+$event.workspaceName+' | '+$event.uwYear+' | '+$event.workSpaceId}
    }
    if(currentSourceOfItems == 'to') {
      this.to = {...this.to, wsId: $event.workSpaceId, uwYear: $event.uwYear, detail: $event.cedantName+' | '+$event.workspaceName+' | '+$event.uwYear+' | '+$event.workSpaceId}
    }
  }

  summaryCache= {
    'Pre-Inured PLTs': {
      'icon': 'icon-assignment_24px',
      'color': '#c38fff',
      'value': 14
    },
    'Post-Inured PLTs': {
      'icon': 'fa fa-code-fork',
      'color': '#c38fff',
      'value': 7
    },
    'Inuring Packages': {
      'icon': 'icon-layer-group',
      'color': '#f5a623',
      'value': 5
    },
    'Sources Projects': {
      'icon': 'icon-assignment_24px',
      'color': '#33d0bb',
      'value': 8
    }
  }

  swapCloneItems() {
    const t= {...this.from};
    this.from= {...this.to};
    this.to= t;

    if(this.from.plts.length > 0) {
      this.cloneConfig= {
        ...this.cloneConfig,
        summary: {...this.summaryCache}
      }
    }else {
      const k= {};

      _.forEach(this.cloneConfig.summary, (v,key) => {
        k[key] = {...v, value: 0};
      })

      this.cloneConfig= {
        ...this.cloneConfig,
        summary: {...k}
      }
    }
  }

  setSelectedPlts(currentSourceOfItems: string, $event: any) {
    if(currentSourceOfItems == 'from') {
      this.from = {...this.from, plts: $event}
    }
    if(currentSourceOfItems == 'to') {
      this.to = {...this.to, plts: $event}
    }

    if(this.from.plts.length > 0) {
      this.cloneConfig= {
        ...this.cloneConfig,
        summary: {...this.summaryCache}
      }
    }else {
      const k= {};

      _.forEach(this.cloneConfig.summary, (v,key) => {
        k[key] = {...v, value: 0};
      })

      this.cloneConfig= {
        ...this.cloneConfig,
        summary: {...k}
      }
    }
  }

  openSearchPopUp(destination: string = 'from') {
    this.multiSteps= destination == 'from';
    this.stepConfig = {
      wsId: '',
      uwYear: '',
      plts: []
    };
    this.searchWorkSpaceModal= true;
    this.setCloneConfig('currentSourceOfItems', destination);
  }

  setCloneConfig(key,value) {
    this.cloneConfig= { ...this.cloneConfig, [key]: value };
  }

  getCloneConfig(key) {
    return this.cloneConfig[key];
  }

  protected detectChanges() {
    super.detectChanges();
  }

  editPlts() {
    this.stepConfig = {
      wsId: this.from.wsId,
      uwYear: this.from.uwYear,
      plts: this.from.plts
    };
    this.multiSteps= true;
    this.setCloneConfig('currentSourceOfItems', 'from');
    this.searchWorkSpaceModal= true;
  }

  reset() {
    if(this.activeSubTitle === 0) {
      this.from= {...this.fromCache};
      this.to= {...this.toCache};

      if(this.from.plts.length > 0) {
        this.cloneConfig= {
          ...this.cloneConfig,
          summary: {...this.summaryCache}
        }
      }else {
        const k= {};

        _.forEach(this.cloneConfig.summary, (v,key) => {
          k[key] = {...v, value: 0};
        })

        this.cloneConfig= {
          ...this.cloneConfig,
          summary: {...k}
        }
      }
    }
  }

  patchState(state: any): void {
  }
}
