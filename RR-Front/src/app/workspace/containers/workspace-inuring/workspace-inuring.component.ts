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
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

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
      width: '2%',
      display: true,
      sorted: false,
      filtered: false,
      type: 'checkbox',
      class: 'icon-check_24px'
    },
    {
      field: 'id',
      header: 'ID',
      width: '7%',
      display: true,
      sorted: true,
      filtered: false,
    },
    {
      field: 'name',
      header: 'Name',
      width: '10%',
      display: true,
      sorted: true,
      filtered: true,
    },
    {
      field: 'description',
      header: 'Description',
      width: '15%',
      display: true,
      sorted: true,
      filtered: true,
    },
    {
      field: 'statsExchangeRate',
      header: 'Stats Exchange Rate',
      width: '5%',
      display: true,
      sorted: true,
      filtered: true,
    },
    {
      field: 'status',
      header: 'Status',
      width: '2%',
      display: true,
      sorted: true,
      filtered: true,
      type: 'status'
    },
    {
      field: 'creationDate',
      header: 'Creation Date',
      width: '4%',
      display: true,
      sorted: true,
      filtered: true,
      type: 'date'
    },
    {
      field: 'lastModifiedDate',
      header: 'Last Modified Date',
      width: '4%',
      display: true,
      sorted: true,
      filtered: true,
      type: 'date'
    },
    {
      field: 'lock',
      header: '',
      width: '2%',
      display: true,
      sorted: false,
      filtered: false,
      type: 'icon',
      class: 'icon-lock_24px-2 red',
    },
    {
      field: 'actions',
      header: '',
      width: '5%',
      display: true,
      sorted: false,
      filtered: false,
      type: 'multi-icons',
      icons: [
        {
          class: 'icon-edit',
          handler: (col, row) => {
            console.log('Handle Edit ', col, row);
            this.inuringPackageFG.patchValue({..._.pick(row, ['id', 'name', 'description']), edit: true});
            this.addPackageVisibility = true;
          }
        },
        {class: 'icon-copy_24px', handler: (col, row) => null},
        {
          class: 'icon-trash-alt',
          handler: (col, row) => {
            this.dispatch(new fromInuring.DeleteInuringPackage({wsIdentifier: this.wsIdentifier, inuringPackage: row}))
          }
        }
      ]
    }
  ];

  data: any;

  packages;

  addPackageVisibility: boolean = false;

  paginationOption = {
    total: 3
  };

  inuringPackageFG: FormGroup;

  constructor(private actions: Actions, private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef, private _fb: FormBuilder) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.inuringPackageFG = this._fb.group({
      'id': [, Validators.required],
      'name': [, Validators.required],
      'description': [],
      'edit': [false]
    });
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

  showAddPackagePopup() {
    this.addPackageVisibility = true;
    this.inuringPackageFG.patchValue({edit: false});
  }

  submitInuringPackageForm() {
    const inuringPackage = this.inuringPackageFG.value;
    console.log('Submit inuring Form', inuringPackage);
    if (inuringPackage.edit)
      this.dispatch(new fromInuring.EditInuringPackage({wsIdentifier: this.wsIdentifier, inuringPackage}));
    else
      this.dispatch(new fromInuring.AddInuringPackage({wsIdentifier: this.wsIdentifier, inuringPackage}));
    this.addPackageVisibility = false;
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
    setTimeout(() =>
      this.currentTabIndex = _.findIndex(_.toArray(this.workspace.inuring.content), (val, key) => val.id == packageId) + 1
    );
  }

  ngOnDestroy(): void {
    this.destroy();
  }

}
