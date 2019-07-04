import {ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {debounceTime, filter, map, mergeMap, switchMap, tap} from 'rxjs/operators';
import { Store} from '@ngxs/store';
import { MessageService} from 'primeng/api';
import {combineLatest, of} from 'rxjs';
import {WorkspaceMainState} from '../../../core/store/states';
import {NavigationTransition} from '@angular/router/src/router';
import * as _ from 'lodash'
import {PltMainState} from '../../store/states';
import * as fromWS from '../../store'
import {PreviousNavigationService} from '../../services/previous-navigation.service';

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
export class WorkspaceCloneDataComponent implements OnInit, OnDestroy {

  constructor(
    private router$: Router,
    private route$: ActivatedRoute,
    private store$: Store,
    private cdRef: ChangeDetectorRef,
    private prn: PreviousNavigationService
  ) {
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
      currentSourceOfItems: 'to'
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
  to: SourceData;
  browesing: boolean;
  showDeleted: boolean;
  workspaceId: string;
  uwy: number;
  subs: any[]= [];

  cloneConfig: {
    currentSourceOfItems: string
  }

  ngOnInit() {
    this.subs.push(
    combineLatest(
      this.store$.select(PltMainState.getCloneConfig),
      this.route$.params,
      this.store$.select(WorkspaceMainState.getCurrentWS)
    ).subscribe( ([navigationPayload, {wsId, year}, currentWS] :any) => {
      console.log({
        prn: this.prn.getPreviousUrl(),
        navigationPayload,wsId,year,currentWS
      });
      if(this.prn.getPreviousUrl() == 'PltBrowser' && _.keys(navigationPayload).length){
        this.from = {
          ...navigationPayload.payload,
          detail: currentWS.cedantName+' | '+currentWS.workspaceName+' | '+currentWS.uwYear+' | '+currentWS.workSpaceId
        }
        this.to = {
          detail: '',
          plts: [],
          wsId: '',
          uwYear: ''
        }
      }else {
        this.to = {
          wsId: wsId,
          uwYear: year,
          plts: [],
          detail: currentWS.cedantName+' | '+currentWS.workspaceName+' | '+currentWS.uwYear+' | '+currentWS.workSpaceId
        }
        this.from = {
          detail: '',
          plts: [],
          wsId: '',
          uwYear: ''
        }
      }
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

  detectChanges() {
    if (!this.cdRef['destroyed']) {
      this.cdRef.detectChanges();
    }
  }

  setSelectedWs($event: any) {

  }

  swapCloneItems() {
    const t= {...this.from};
    this.from= {...this.to};
    this.to= t;
  }

  setSelectedPlts(currentSourceOfItems: string, $event: any) {
    if(this.getCloneConfig('currentSourceOfItems') == 'from') {
      this.from = {...this.from, plts: $event}
    }
    if(this.getCloneConfig('currentSourceOfItems') == 'to') {
      this.to = {...this.to, plts: $event}
    }
  }

  openSearchPopUp(destination: string = 'from') {
    this.searchWorkSpaceModal=true;
    this.setCloneConfig('currentSourceOfItems', destination);
  }

  setCloneConfig(key,value) {
    this.cloneConfig= { ...this.cloneConfig, [key]: value };
  }

  getCloneConfig(key) {
    return this.cloneConfig[key];
  }

  ngOnDestroy(): void {
    this.store$.dispatch(new fromWS.setCloneConfig({}));
    _.each(this.subs, e => e && e.unsubscribe());
  }
}
