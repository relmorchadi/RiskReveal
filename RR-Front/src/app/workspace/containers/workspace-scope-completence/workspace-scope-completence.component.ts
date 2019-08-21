import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {combineLatest} from 'rxjs';
import * as _ from 'lodash';
import {dataTable,dataTable2, trestySections} from './data';
import {ActivatedRoute, Router} from '@angular/router';
import {BaseContainer} from '../../../shared/base';
import {StateSubscriber} from '../../model/state-subscriber';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromWs from '../../store/actions';
import {tap} from "rxjs/operators";
import {Message} from "../../../shared/message";
import * as tableStore from "../../../shared/components/plt/plt-main-table/store";
import * as fromWorkspaceStore from "../../store";

@Component({
  selector: 'app-workspace-scope-completence',
  templateUrl: './workspace-scope-completence.component.html',
  styleUrls: ['./workspace-scope-completence.component.scss']
})
export class WorkspaceScopeCompletenceComponent extends BaseContainer implements OnInit, StateSubscriber {
  check = true;
  @Select(WorkspaceState.getPlts) data$;

  @ViewChild('pTable') pTable: any;

  wsIdentifier;
  addRemoveModal: boolean = false;
  listOfPltsData = [];
  selectionForOverride = [];

  dataSource: any;
  workspaceId: any;
  uwy: any;
  grains = {}
  regionCodes = {};
  selectedSortBy: string = 'Minimum Grain / RAP';

  treatySections: any;

  workspaceInfo: any;
  serviceSubscription: any;
  @Select(WorkspaceState.getScopeCompletenessData) state$;
  state: any;

  workspace: any;
  index: any;
  workspaceUrl: any;

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }


  ngOnInit() {
    this.observeRouteParams().pipe(
      this.unsubscribeOnDestroy
    ).subscribe(() => {
      this.dispatch(new fromWs.LoadScopeCompletenessDataSuccess({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));
    });

    this.state$.pipe(this.unsubscribeOnDestroy).subscribe(value => {
      this.state = value;
      this.listOfPltsData = _.map(this.getSortedPlts(this.state), (e,k) => ({[k]: e}));
      // this.dataSource = this.dataSource.map((dt, k) => dt = {...dt, id: k});
      this.dataSource = dataTable;
      this.treatySections = _.toArray(trestySections);
      this.detectChanges();
    });

    combineLatest(
      this.route.params
    ).pipe(this.unsubscribeOnDestroy)
      .subscribe((dt: any) => {
        const {wsId, year} = dt[0];
        this.workspaceUrl = {wsId, uwYear: year};
      });
  }

  observeRouteParams() {
    return this.route.params.pipe(tap(({wsId, year}) => {
      this.workspaceId = wsId;
      this.uwy = year;
    }))
  }


  patchState({wsIdentifier, data}: any): void {
    this.workspaceInfo = data;
    this.wsIdentifier = wsIdentifier;
  }



  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);
  }

  getSortedPlts(data, type = 'grain') {
    let SortConfig = {
      grain: ['regionPerilCode', 'grain'],
      regionPerilCode: ['grain', 'regionPerilCode']
    }
    let res = {};
    _.forEach(_.groupBy(data, e => e[SortConfig[type][0]]), (e, k) => res[k] = _.groupBy(e, dt => dt[SortConfig[type][1]]));
    return res;
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspaceInfo;
    this.dispatch([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ]);
  }

  sortByRegionPeril() {
  }

  perilZone(peril) {
    if (peril === 'YY') {
      return {peril: 'EQ', color: '#E70010'};
    }
    if (peril === 'WS') {
      return {peril: 'WS', color: '#7BBE31'};
    }
    if (peril === 'FL') {
      return {peril: 'FL', color: '#008694'};
    }
  }

  ngOnDestroy(): void {
    this.destroy();
  }


  toggleExpanded(item: any) {
    this.grains[item.id] = !this.grains[item.id];
  }

  toggleParentExpand(item: any) {
    this.regionCodes[item.id] = !this.regionCodes[item.id];

  }


  changeSortBy(item) {
    this.selectedSortBy = item;
    this.pTable.expandedRowKeys = {};
    this.grains = {};
    this.regionCodes = {};
    if (item == 'Minimum Grain / RAP') {
      this.dataSource = dataTable
    }
    if (item == 'RAP / Minimum Grain') {
      this.dataSource = dataTable2;

    }


  }

  checkExpected(item, rowData) {
    let checked = 'not';
    let holder = [];
    if(this.selectedSortBy == 'Minimum Grain / RAP'){
    item.regionPerils.forEach(reg => {
        if (reg.id == rowData.id) {

          reg.targetRaps.forEach(res => {
            rowData.child.forEach(des => {
              if (des.id == res.id) {
                if(des.attached){
                  holder.push('attached');
                }
                if(des.overridden){
                  holder.push('overridden')
                }
                if(!des.attached && !des.overridden){
                  holder.push('checked')
                }
              }
            })

          })

        }
      }
    )
    if(_.includes(holder,'checked')){
      checked = 'checked';
    }else if(_.includes(holder, 'attached')){
      checked = 'attached';}
      else if(_.includes(holder,  'overridden')){
        checked = 'overridden';
    }
    }
    if(this.selectedSortBy == 'RAP / Minimum Grain'){
      item.targetRaps.forEach(reg => {
          if (reg.id == rowData.id) {

            reg.regionPerils.forEach(res => {
              rowData.child.forEach(des => {
                if (des.id == res.id) {
                  if(des.attached){
                    holder.push('attached');
                  }
                  if(des.overridden){
                    holder.push('overridden')
                  }
                  if(!des.attached && !des.overridden){
                    holder.push('checked')
                  }
                  }
              })

            })

          }
        }
      )
    }
    if(_.includes(holder,'checked')){
      checked = 'checked';
    }else if(_.includes(holder, 'attached')){
      checked = 'attached';}
    else if(_.includes(holder,  'overridden')){
      checked = 'overridden';
    }
    if(rowData.override){
      if(checked == 'checked'){
        checked = 'override'
      }
    }
    return checked;
  }
  checkExpectedTwo(item,grain, rowData){
    let checked = 'not';

    if(this.selectedSortBy == 'Minimum Grain / RAP'){
      item.regionPerils.forEach(reg => {
        if (reg.id == rowData.id) {
      reg.targetRaps.forEach( des => {
        if(des.id == grain.id){
          if(grain.attached){
            checked = 'attached';
          }
          if(grain.overridden){
            checked = 'overridden';
          }
          if(!grain.attached && !grain.overridden){
            checked = 'checked'
          }
          }
      })

    }})}
    if(this.selectedSortBy == 'RAP / Minimum Grain'){
      item.targetRaps.forEach(reg => {
        if (reg.id == rowData.id) {
        reg.regionPerils.forEach( des => {
          if(des.id == grain.id){
            if(grain.attached){
              checked = 'attached';
            }
            if(grain.overridden){
              checked = 'overridden';
            }
            if(!grain.attached && !grain.overridden){
              checked = 'checked'
            }
          }
        })
    }})}

  if(rowData.override){
    if(checked == 'checked'){
      checked = 'override'
    }
  }
    return checked;
  }

  overrideSelectionTwo(item,rowData,grain){
 let holder = [item.id, rowData.id, grain.id];
  this.selectionForOverride.push(holder);
  // console.log('this is the selectionForOverride', this.selectionForOverride);
  }

  overrideSelection(item,rowData){


  }
}
