import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Actions, ofActionSuccessful, Select, Store} from '@ngxs/store';
import {WorkspaceState} from '../../store/states';
import {BaseContainer} from '../../../shared/base';
import * as fromHeader from '../../../core/store/actions/header.action';
import * as fromInuring from '../../store/actions/inuring.actions';
import * as fromWs from '../../store/actions';
import {debounceTime} from "rxjs/operators";
import * as _ from 'lodash';

@Component({
  selector: 'app-workspace-inuring',
  templateUrl: './workspace-inuring.component.html',
  styleUrls: ['./workspace-inuring.component.scss']
})
export class WorkspaceInuringComponent extends BaseContainer implements OnInit {

  wsIdentifier;
  workspace: any;
  index: any;
  check = true;

  @Select(WorkspaceState.getPlts)
  data$;

  currentTabIndex: any;

  columns = [
    {
      field: 'checkbox',
      header: '',
      width: '20px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'checkbox',
      class: 'icon-check_24px'
    },
    {
      field: 'id',
      header: 'ID',
      width: '50px',
      display: true,
      sorted: true,
      filtered: false,
    },
    {
      field: 'name',
      header: 'Name',
      width: '90px',
      display: true,
      sorted: true,
      filtered: true,
    },
    {
      field: 'description',
      header: 'Description',
      width: '150px',
      display: true,
      sorted: true,
      filtered: true,
    },
    {
      field: 'statsExchangeRate',
      header: 'Stats Exchange Rate',
      width: '50px',
      display: true,
      sorted: true,
      filtered: true,
    },
    {
      field: 'status',
      header: 'Status',
      width: '20px',
      display: true,
      sorted: true,
      filtered: true,
    },
    {
      field: 'creationDate',
      header: 'Creation Date',
      width: '50px',
      display: true,
      sorted: true,
      filtered: true,
      type: 'date'
    },
    {
      field: 'lastModifiedDate',
      header: 'Last Modified Date',
      width: '50px',
      display: true,
      sorted: true,
      filtered: true,
      type: 'date'
    },
    {
      field: 'lock',
      header: '',
      width: '10px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'icon',
      class: 'icon-lock_24px-2',
    },
    {
      field: 'actions',
      header: '',
      width: '30px',
      display: true,
      sorted: false,
      filtered: false,
      type: 'multi-icons',
      icons: [
        {class: 'icon-check_24px', handler: (col, row) => null},
        {class: 'icon-copy_24px', handler: (col, row) => null},
        {class: 'icon-trash-alt', handler: (col, row) => null}
      ]
    }
  ];

  data: any;

  packages;

  openedTabs: Array<any> = [];

  addPackageVisibility: boolean = false;

  paginationOption = {
    total: 3
  };

  constructor(private actions: Actions, private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.actions.pipe(ofActionSuccessful(fromInuring.OpenInuringPackage))
      .pipe(this.unsubscribeOnDestroy, debounceTime(500))
      .subscribe(({payload}) => {
        console.log('[Inuring] open success', payload);
        this._openPackageById(payload.data.id);
      });
  }

  patchState({wsIdentifier, data}: any): void {
    this.wsIdentifier = wsIdentifier;
    this.workspace = data;
    this.packages = _.cloneDeep(_.get(data, 'inuring.packages', []));
  }

  pinWorkspace() {
    const {wsId, uwYear, workspaceName, programName, cedantName} = this.workspace;
    this.dispatch([
      new fromHeader.PinWs({
        wsId,
        uwYear,
        workspaceName,
        programName,
        cedantName
      }), new fromWs.MarkWsAsPinned({wsIdentifier: this.wsIdentifier})]);
  }

  unPinWorkspace() {
    const {wsId, uwYear} = this.workspace;
    this.dispatch([
      new fromHeader.UnPinWs({wsId, uwYear}),
      new fromWs.MarkWsAsNonPinned({wsIdentifier: this.wsIdentifier})
    ]);
  }

  filterData(value, target) {
    console.log('[Inuring] ->  search value, targer', value, target);
  }

  openInuringPackage(p) {
    console.log('[Inuring] ->  Open Inuring package', p);
    this.dispatch(new fromInuring.OpenInuringPackage({wsIdentifier: this.wsIdentifier, data: p}));
  }

  close(id) {
    console.log('[Inuring] close package', id);
    this.dispatch(new fromInuring.CloseInuringPackage({wsIdentifier: this.wsIdentifier, id}));
  }

  private _openPackageById(packageId: string) {
    this.currentTabIndex = _.findIndex(this.openedTabs, (val, key) => val.id == packageId) + 1;

  }

  ngOnDestroy(): void {
    this.destroy();
  }

}
