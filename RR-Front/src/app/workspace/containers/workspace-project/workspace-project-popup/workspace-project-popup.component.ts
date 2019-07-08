import {ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';
import {ActivatedRoute, Router} from '@angular/router';
import {combineLatest, Observable, of, Subject, Subscription} from 'rxjs';
import {Actions, Select, Store, ofAction, ofActionDispatched} from '@ngxs/store';
import {WorkspaceMainState} from '../../../../core/store/states';
import {LazyLoadEvent, MessageService} from 'primeng/api';
import {SearchService} from '../../../../core/service';
import {Debounce} from '../../../../shared/decorators';
import {NotificationService} from '../../../../shared/notification.service';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {debounceTime, filter, first, last, mergeMap, take, takeUntil} from 'rxjs/operators';
import * as fromWorkspaceStore from '../../../store';
import {PltMainState} from '../../../store';

@Component({
  selector: 'app-workspace-project-popup',
  templateUrl: './workspace-project-popup.component.html',
  styleUrls: ['./workspace-project-popup.component.scss'],
})
export class WorkspaceProjectPopupComponent implements OnInit, OnDestroy {

  data$: Observable<any>;
  deletedPlts$: Observable<any>;
  subscriptions: Subscription;
  @Select(WorkspaceMainState.getData) selectWsData$;
  @Select(WorkspaceMainState.getProjects) projects$;
  unSubscribe$: Subject<void>;
  private pltTableSubscription: Subscription;
  private pltProjectSubscription: Subscription;
  private pltUserTagsSubscription: Subscription;

  @Output('onVisibleChange') onVisibleChange: EventEmitter<any> = new EventEmitter();
  @Output('onSelectProjectNext') onSelectProjectNext: EventEmitter<any> = new EventEmitter();
  @Output('onSelectItems') onSelectItems: EventEmitter<any> = new EventEmitter();
  @Output('onSelectWorkspace') onSelectWorkspace: EventEmitter<any> = new EventEmitter();
  @Input() isVisible;
  @Input('selectionStep') selectionStep: string;
  @Input() multiSteps: boolean;
  @Input() stepConfig: {
    wsId: string,
    uwYear: string,
    plts: any[]
  };

  workspace: any;
  index: any;
  newProject = false;
  existingProject = false;
  mgaProject = false;
  searchWorkspace = false;
  selectedWorkspace: any = null;
  selectedWorkspaceProjects: any = null;
  selectExistingProject = false;
  selectedProject: any = null;
  selectedWs: any;
  globalSearchItem = '';
  private _filter = {};
  keywordFormGroup: FormGroup;
  selectWsTable = [
    {
      field: 'countryName',
      header: 'Country',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'countryName'
    },
    {
      field: 'cedantName',
      header: 'Cedent Name',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'cedantName'
    },
    {
      field: 'cedantCode',
      header: 'Cedant Code',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'cedantCode'
    },
    {
      field: 'uwYear',
      header: 'Uw Year',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'year'
    },
    {
      field: 'workspaceName',
      header: 'Workspace Name',
      width: '160px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'workspaceName'
    },
    {
      field: 'workSpaceId',
      header: 'Workspace Context',
      width: '90px',
      display: true,
      sorted: false,
      filtered: true,
      filterParam: 'workspaceId'
    }
  ];
  paginationOption = {currentPage: 0, page: 0, size: 40, total: '-'};
  contracts = [];
  loading;
  browesing: boolean;

  Inputs: {
    filterInput: string;
    pltColumns: any[];
    listOfPltsData: [];
    listOfDeletedPltsData: [];
    listOfPltsCache: [];
    listOfDeletedPltsCache: [];
    listOfPlts: [];
    listOfDeletedPlts: [];
    selectedListOfPlts: any;
    selectedListOfDeletedPlts: any;
    selectAll: boolean;
    someItemsAreSelected: boolean;
    showDeleted: boolean;
    filterData: any;
    filters: {
      systemTag: any,
      userTag: []
    };
    sortData: any;
    _tagModalVisible: boolean;
    _modalSelect: [];
    tagForMenu: any;
    _editingTag: boolean;
    wsId: string;
    uwYear: string;
    projects: any[];
    addTagModalIndex: number;
    fromPlts: boolean;
    deletedPltsLength: number;
    userTags: any[];
    systemTagsCount: any;
    wsHeaderSelected: boolean;
    pathTab: boolean;
    selectedItemForMenu: any;
  };

  tableInputs= ['filterInput', 'pltColumns', 'listOfPltsData', 'listOfDeletedPltsData', 'listOfPltsCache', 'listOfDeletedPltsCache', 'listOfPlts', 'listOfDeletedPlts', 'selectedListOfPlts', 'selectedListOfDeletedPlts', 'selectAll', 'someItemsAreSelected', 'showDeleted', 'filterData', 'filters', 'sortData'];

  menuInputs= ['_tagModalVisible','_modalSelect','tagForMenu','_editingTag', 'wsId','uwYear', 'projects', 'showDeleted','filterData','filters', 'addTagModalIndex', 'fromPlts', 'deletedPltsLength', 'userTags', 'selectedListOfPlts', 'systemTagsCount', 'wsHeaderSelected', 'pathTab', 'selectedItemForMenu'];

  systemTagsMapping = {
    grouped: {
      peril: 'Peril',
      regionDesc: 'Region',
      currency: 'Currency',
      sourceModellingVendor: 'Modelling Vendor',
      sourceModellingSystem: 'Modelling System',
      targetRapCode: 'Target RAP',
      userOccurrenceBasis: 'User Occurence Basis',
      xActAvailable: 'Published To Pricing',
      xActUsed: 'Priced',
      accumulated: 'Accumulated',
      financial: 'Financial Perspectives'
    },
    nonGrouped: {}
  };
  private d: Subscription;

  constructor(
    private route: ActivatedRoute,
    private store$: Store,
    private router: Router,
    private actions$: Actions,
    private notificationService: NotificationService,
    private cdRef: ChangeDetectorRef,
    private searchService: SearchService,
    private fb: FormBuilder,
  ) {
    this.Inputs= {
      filterInput: '',
      pltColumns: [
        {
          width: '60',
          filtred: false,
          icon: null,
          type: 'checkbox',active: true
        },
        {fields: 'pltId', header: 'PLT ID', width: '80', sorted: false, filtred: true, icon: null, type: 'id',active: true},
        {
          fields: 'pltName',
          header: 'PLT Name',
          width: '160',
          sorted: false,
          filtred: true,
          icon: null,
          type: 'field',active: true
        },
        {
          fields: 'peril',
          header: 'Peril',
          width: '40',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field',
          textAlign: 'center',active: true
        },
        {
          fields: 'regionPerilCode',
          header: 'Region Peril Code',
          width: '70',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field',active: true
        },
        {
          fields: 'regionPerilName',
          header: 'Region Peril Name',
          width: '160',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field',active: true
        },
        {sortDir: 1, fields: 'grain', header: 'Grain', width: '90', sorted: false, filtred: false, icon: null, type: 'field',active: true},
        {
          sortDir: 1,
          fields: 'vendorSystem',
          header: 'Vendor System',
          width: '90',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'field',active: true
        },
        {sortDir: 1, fields: 'rap', header: 'RAP', width: '52', sorted: false, filtred: false, icon: null, type: 'field',active: true}
      ],
      listOfPltsData: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfDeletedPltsCache: [],
      listOfPlts: [],
      listOfDeletedPlts: [],
      selectedListOfPlts: [],
      selectedListOfDeletedPlts: [],
      selectAll: false,
      someItemsAreSelected: false,
      showDeleted: false,
      filterData: {},
      filters: {
        systemTag: {},
        userTag: []
      },
      sortData: {},
      _tagModalVisible: false,
      _modalSelect: [],
      tagForMenu: {},
      _editingTag: false,
      wsId: '',
      uwYear: '',
      projects: [],
      addTagModalIndex: 0,
      fromPlts: false,
      deletedPltsLength: 0,
      userTags: [],
      systemTagsCount: {},
      wsHeaderSelected: true,
      pathTab: true,
      selectedItemForMenu: null
  };
    this.unSubscribe$ = new Subject<void>();
    this.keywordFormGroup = new FormGroup({
      keyword: new FormControl(null)
    });
    this.browesing= false;
  }

  ngOnInit() {

  }

  getBrowesingItemsDirectly() {
    this.browesing= true;
    this.data$ = this.store$.select(PltMainState.getPlts(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
    this.deletedPlts$ = this.store$.select(PltMainState.getDeletedPlts(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
    this.store$.dispatch(new fromWorkspaceStore.loadAllPlts({
      params: {
        workspaceId: this.getInputs('wsId'),
        uwy: this.getInputs('uwYear')
      }
    }));
    this.pltTableSubscription = combineLatest(
      this.data$,
      this.deletedPlts$
    ).subscribe(([data, deletedData]: any) => {
      let d1 = [];
      let dd1 = [];
      let d2 = [];
      let dd2 = [];
      this.Inputs['systemTagsCount'] = {};

      if (data) {
        if (_.keys(this.Inputs['systemTagsCount']).length == 0) {
          _.forEach(data, (v, k) => {
            //Init Tags Counters

            //Grouped Sys Tags
            _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
              this.Inputs['systemTagsCount'][sectionName] = this.Inputs['systemTagsCount'][sectionName] || {};
              const tag = _.toString(v[section]);
              if (tag) {
                this.Inputs['systemTagsCount'][sectionName][tag] = {selected: false, count: 0, max: 0}
              }
            });

            //NONE grouped Sys Tags
            _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
              this.Inputs['systemTagsCount'][sectionName] = this.Inputs['systemTagsCount'][sectionName] || {};
              this.Inputs['systemTagsCount'][sectionName][section] = {selected: false, count: 0};
              this.Inputs['systemTagsCount'][sectionName]['non-' + section] = {selected: false, count: 0, max: 0};
            })

          })
        }

        _.forEach(data, (v, k) => {
          d1.push({...v, pltId: k});
          d2.push(k);

          /*if (v.visible) {*/
          //Grouped Sys Tags
          _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
            const tag = _.toString(v[section]);
            if (tag) {
              if (this.Inputs['systemTagsCount'][sectionName][tag] || this.Inputs['systemTagsCount'][sectionName][tag].count === 0) {
                const {
                  count,
                  max
                } = this.Inputs['systemTagsCount'][sectionName][tag];

                this.Inputs['systemTagsCount'][sectionName][tag] = {
                  ...this.Inputs['systemTagsCount'][sectionName][tag],
                  count: v.visible ? count + 1 : count,
                  max: max + 1
                };
              }
            }
          })

          //NONE grouped Sys Tags
          _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
            const tag = v[section];
            if (this.Inputs['systemTagsCount'][sectionName][section] || this.Inputs['systemTagsCount'][sectionName][section] == 0) {
              const {
                max,
                count
              } = this.Inputs['systemTagsCount'][sectionName][section];
              this.Inputs['systemTagsCount'][sectionName][section] = {
                ...this.Inputs['systemTagsCount'][sectionName][section],
                count: v.visible ? count + 1 : count,
                max: max + 1
              };
            }
            if (this.Inputs['systemTagsCount'][sectionName]['non-' + section] || this.Inputs['systemTagsCount'][sectionName]['non-' + section].count == 0) {
              const {
                count,
                max
              } = this.Inputs['systemTagsCount'][sectionName]['non-' + section];
              this.Inputs['systemTagsCount'][sectionName]['non-' + section] = {
                ...this.Inputs['systemTagsCount'][sectionName]['non-' + section],
                count: v.visible ? count + 1 : count,
                max: max + 1
              };
            }
          })
          /*}*/

        });

        this.setInputs('listOfPlts', d2);
        this.setInputs('listOfPltsData', d1);
        this.setInputs('listOfPltsCache', d1);
        this.setInputs('selectedListOfPlts', _.filter(d2, k => data[k].selected));
      }

      if (deletedData) {
        _.forEach(deletedData, (v, k) => {
          dd1.push({...v, pltId: k});
          dd2.push(k);
        });

        this.setInputs('listOfDeletedPlts', dd2);
        this.setInputs('listOfDeletedPltsData', dd1);
        this.setInputs('listOfDeletedPltsCache', dd1);
        this.setInputs('selectedListOfDeletedPlts', _.filter(dd2, k => deletedData[k].selected))
      }

      this.setInputs(
        'selectAll',
        !this.getInputs('showDeleted')
          ?
          (this.getInputs('selectedListOfPlts').length > 0 || (this.getInputs('selectedListOfPlts').length == this.getInputs('listOfPlts').length)) && this.getInputs('listOfPltsData').length > 0
          :
          (this.getInputs('selectedListOfDeletedPlts').length > 0 || (this.getInputs('selectedListOfDeletedPlts').length == this.getInputs('listOfDeletedPlts').length)) && this.getInputs('listOfDeletedPltsData').length > 0
      );

      this.setInputs(
        'someItemsAreSelected' ,
        !this.getInputs('showDeleted') ?
          this.getInputs('selectedListOfPlts').length < this.getInputs('listOfPlts').length && this.getInputs('selectedListOfPlts').length > 0
          :
          this.getInputs('selectedListOfDeletedPlts').length < this.getInputs('listOfDeletedPlts').length && this.getInputs('selectedListOfDeletedPlts').length > 0
      );
      console.log('END');
      this.detectChanges();
    });

    this.d =this.actions$.pipe(
      ofActionDispatched(fromWorkspaceStore.loadAllPltsSuccess),
      mergeMap( () => {
        this.toggleSelectPlts(_.zipObject(
          _.map(_.map(this.stepConfig.plts, id => _.find(this.getInputs('listOfPltsData'), plt => id == plt.pltId)), plt => plt.pltId),
          _.map(this.stepConfig.plts, plt =>   ({type: true }))
        ));
        return of(null);
      })
    ).subscribe( () => this.d.unsubscribe());

    this.pltProjectSubscription = this.store$.select(PltMainState.getProjects()).subscribe((projects: any) => {
      this.setInputs('projects', _.map(projects, p => ({...p, selected: false})));
      this.detectChanges();
    });

    this.pltUserTagsSubscription = this.store$.select(PltMainState.getUserTags).subscribe(userTags => {
      this.setInputs('userTags', userTags || {});
      console.log(this.getInputs('userTags'))
      this.detectChanges();
    });
  }

  ngOnDestroy(): void {
    this.unSubscribe$.next();
    this.unSubscribe$.complete();
  }

  openWorkspaceInSlider(event?) { console.log(event); }
  @Debounce(500)
  filterData($event, target) {
    this._filter = {...this._filter, [target]: $event || null};
    this._loadData();
  }

  private _loadData(offset = '0', size = '100') {
    this.loading = true;
    const keyword = this.keywordFormGroup.get('keyword').value === '' ? null : this.keywordFormGroup.get('keyword').value
    const params = {
      keyword,
      filter: this.filter,
      offset,
      size
    };
    this.searchService.expertModeSearch(params)
      .subscribe((data: any) => {
        this.contracts = data.content.map(item => ({...item, selected: false}));
        this.loading = false;
        this.paginationOption = {
          ...this.paginationOption,
          page: data.number,
          size: data.numberOfElements,
          total: data.totalElements
        };
        this.detectChanges();
      });
  }

  loadMore(event: LazyLoadEvent) {
    this.paginationOption.currentPage = event.first;
    this._loadData(String(event.first));
  }

  get filter() {
    // let tags = _.isString(this.searchContent) ? [] : (this.searchContent || []);
    const tableFilter = _.map(this._filter, (value, key) => ({key, value}));
    // return _.concat(tags ,  tableFilter).filter(({value}) => value).map((item: any) => ({
    return tableFilter.filter(({value}) => value).map((item: any) => ({
      ...item,
      field: _.camelCase(item.key),
      operator: item.operator || 'LIKE'
    }));
  }

  onHide() {
    this.subscriptions && this.subscriptions.unsubscribe();
    this.pltTableSubscription && this.pltTableSubscription.unsubscribe();
    this.pltProjectSubscription && this.pltProjectSubscription.unsubscribe();
    this.pltProjectSubscription && this.pltProjectSubscription.unsubscribe();
    this.searchWorkspace = false;
    this.selectedWorkspace = null;
    this._filter= {};
    this.browesing= false;
    this.onVisibleChange.emit(false);
  }
  onRowSelect(d,event) {
    console.log(event);
   if(d == 'db') {
     this.selectedWorkspace = event;
     this.onSelectWorkspace.emit(event);
     this.setInputs('wsId', event.workSpaceId)
   }else {
     this.selectedWorkspace = event.data;
     this.onSelectWorkspace.emit(event.data);
     this.setInputs('wsId', event.data.workSpaceId)
   }
  }
  onRowUnselect(event) {
    // this.selectedWorkspace = null;
  }
  getBrowesingItems(workspace) {
    this.setInputs('wsId', workspace.workSpaceId);
    this.setInputs('uwYear', workspace.uwYear);
    this.onSelectWorkspace.emit(workspace);
    if(this.selectionStep == 'project') {
      this.searchService.searchWorkspace(workspace.workSpaceId, workspace.uwYear).subscribe((data: any) => {
          this.selectedWorkspaceProjects = data.projects;
          if (!data.projects.length) {
            this.browesing = false;
            this.notificationService.createNotification('Information',
              'This workspace contains no project',
              'error', 'topRight', 4000);
          }
          this.detectChanges();
        }
      );
    }

    if(this.selectionStep == 'plt') {
      if(this.multiSteps) {
        this.data$ = this.store$.select(PltMainState.getPlts(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
        this.deletedPlts$ = this.store$.select(PltMainState.getDeletedPlts(this.getInputs('wsId') + '-' + this.getInputs('uwYear')));
        this.store$.dispatch(new fromWorkspaceStore.loadAllPlts({
          params: {
            workspaceId: this.getInputs('wsId'),
            uwy: this.getInputs('uwYear')
          }
        }));

        this.pltTableSubscription = combineLatest(
          this.data$,
          this.deletedPlts$
        ).subscribe(([data, deletedData]: any) => {
          let d1 = [];
          let dd1 = [];
          let d2 = [];
          let dd2 = [];
          this.Inputs['systemTagsCount'] = {};

          if (data) {
            if (_.keys(this.Inputs['systemTagsCount']).length == 0) {
              _.forEach(data, (v, k) => {
                //Init Tags Counters

                //Grouped Sys Tags
                _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
                  this.Inputs['systemTagsCount'][sectionName] = this.Inputs['systemTagsCount'][sectionName] || {};
                  const tag = _.toString(v[section]);
                  if (tag) {
                    this.Inputs['systemTagsCount'][sectionName][tag] = {selected: false, count: 0, max: 0}
                  }
                });

                //NONE grouped Sys Tags
                _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
                  this.Inputs['systemTagsCount'][sectionName] = this.Inputs['systemTagsCount'][sectionName] || {};
                  this.Inputs['systemTagsCount'][sectionName][section] = {selected: false, count: 0};
                  this.Inputs['systemTagsCount'][sectionName]['non-' + section] = {selected: false, count: 0, max: 0};
                })

              })
            }

            _.forEach(data, (v, k) => {
              d1.push({...v, pltId: k});
              d2.push(k);

              /*if (v.visible) {*/
              //Grouped Sys Tags
              _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
                const tag = _.toString(v[section]);
                if (tag) {
                  if (this.Inputs['systemTagsCount'][sectionName][tag] || this.Inputs['systemTagsCount'][sectionName][tag].count === 0) {
                    const {
                      count,
                      max
                    } = this.Inputs['systemTagsCount'][sectionName][tag];

                    this.Inputs['systemTagsCount'][sectionName][tag] = {
                      ...this.Inputs['systemTagsCount'][sectionName][tag],
                      count: v.visible ? count + 1 : count,
                      max: max + 1
                    };
                  }
                }
              })

              //NONE grouped Sys Tags
              _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
                const tag = v[section];
                if (this.Inputs['systemTagsCount'][sectionName][section] || this.Inputs['systemTagsCount'][sectionName][section] == 0) {
                  const {
                    max,
                    count
                  } = this.Inputs['systemTagsCount'][sectionName][section];
                  this.Inputs['systemTagsCount'][sectionName][section] = {
                    ...this.Inputs['systemTagsCount'][sectionName][section],
                    count: v.visible ? count + 1 : count,
                    max: max + 1
                  };
                }
                if (this.Inputs['systemTagsCount'][sectionName]['non-' + section] || this.Inputs['systemTagsCount'][sectionName]['non-' + section].count == 0) {
                  const {
                    count,
                    max
                  } = this.Inputs['systemTagsCount'][sectionName]['non-' + section];
                  this.Inputs['systemTagsCount'][sectionName]['non-' + section] = {
                    ...this.Inputs['systemTagsCount'][sectionName]['non-' + section],
                    count: v.visible ? count + 1 : count,
                    max: max + 1
                  };
                }
              })
              /*}*/

            });

            this.setInputs('listOfPlts', d2);
            this.setInputs('listOfPltsData', d1);
            this.setInputs('listOfPltsCache', d1);
            this.setInputs('selectedListOfPlts', _.filter(d2, k => data[k].selected));
          }

          if (deletedData) {
            _.forEach(deletedData, (v, k) => {
              dd1.push({...v, pltId: k});
              dd2.push(k);
            });

            this.setInputs('listOfDeletedPlts', dd2);
            this.setInputs('listOfDeletedPltsData', dd1);
            this.setInputs('listOfDeletedPltsCache', dd1);
            this.setInputs('selectedListOfDeletedPlts', _.filter(dd2, k => deletedData[k].selected))
          }

          this.setInputs(
            'selectAll',
            !this.getInputs('showDeleted')
              ?
              (this.getInputs('selectedListOfPlts').length > 0 || (this.getInputs('selectedListOfPlts').length == this.getInputs('listOfPlts').length)) && this.getInputs('listOfPltsData').length > 0
              :
              (this.getInputs('selectedListOfDeletedPlts').length > 0 || (this.getInputs('selectedListOfDeletedPlts').length == this.getInputs('listOfDeletedPlts').length)) && this.getInputs('listOfDeletedPltsData').length > 0
          );

          this.setInputs(
            'someItemsAreSelected' ,
            !this.getInputs('showDeleted') ?
              this.getInputs('selectedListOfPlts').length < this.getInputs('listOfPlts').length && this.getInputs('selectedListOfPlts').length > 0
              :
              this.getInputs('selectedListOfDeletedPlts').length < this.getInputs('listOfDeletedPlts').length && this.getInputs('selectedListOfDeletedPlts').length > 0
          );
          this.detectChanges();
        })

        this.pltProjectSubscription = this.store$.select(PltMainState.getProjects()).subscribe((projects: any) => {
          this.setInputs('projects', _.map(projects, p => ({...p, selected: false})));
          this.detectChanges();
        })

        this.pltUserTagsSubscription = this.store$.select(PltMainState.getUserTags).subscribe(userTags => {
          this.setInputs('userTags', userTags || {});
          console.log(this.getInputs('userTags'))
          this.detectChanges();
        })
      } else {
        this.onVisibleChange.emit(false);
      }
    }

    this.browesing= true;
  }

  setBrowesingItems() {
    if(this.selectionStep == 'plt') {
      this.onSelectItems.emit(this.Inputs['selectedListOfPlts']);
      this.onVisibleChange.emit(false);
    }else {
      this.searchWorkspace = false;
      this.newProject = true;
      this.onSelectProjectNext.emit(_.omit(this.selectedProject, ['receptionDate', 'dueDate', 'createdBy']));
    }
  }

  selectThisProject(project) {
    if (this.selectedProject === project) {
      this.selectedProject = null;
      return;
    }
    this.selectedProject = project;
  }

  onShow() {
    this.subscriptions = this.keywordFormGroup.get('keyword')
      .valueChanges
      .pipe(debounceTime(400))
      .subscribe((value) => {
        this.loading = true;
        this._loadData();
      });

    if(this.stepConfig.uwYear && this.stepConfig.wsId) {
      this.setInputs('wsId', this.stepConfig.wsId);
      this.setInputs('uwYear', this.stepConfig.uwYear);
      this.getBrowesingItemsDirectly();
    }else {
      this._loadData();
    }

  }

  onEnter() { this._loadData(); }
  search() { this._loadData(); }
  clearSearchValue() {
    this.keywordFormGroup.get('keyword').setValue(null);
    this._loadData();
  }

  goToSearchWorkspace() {
    this._filter = {};
    this._loadData();
    this.browesing= false;
  }

  checkAll($event) {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(!$event ? this.getInputs('listOfPlts') : this.getInputs('listOfDeletedPlts'), plt => plt),
        _.range(!$event ? this.getInputs('listOfPlts').length : this.getInputs('listOfDeletedPlts').length).map(el => ({type: !this.getInputs('selectAll') && !this.getInputs('someItemsAreSelected')}))
      )
    );
  }

  toggleSelectPlts(plts: any) {
    this.store$.dispatch(new fromWorkspaceStore.ToggleSelectPlts({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      plts,
      forDeleted: this.getInputs('showDeleted')
    }));
  }

  selectSinglePLT($event) {
    this.toggleSelectPlts($event);
  }

  filterPlts(filterData) {
    console.log(filterData);
    this.setInputs('filterData', filterData);
  }

  getInputs(key){
    return this.Inputs[key];
  }

  setInputs(key, value) {
    this.Inputs= {...this.Inputs, [key]: value };
  }

  setFilters($event) {
    console.log($event);
    this.setInputs('filters', $event)
  }

  setUserTags($event) {
    console.log($event)
    this.setInputs('userTags', $event)
  }

  emitFilters(filters: any) {
    console.log(filters);
    this.store$.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.getInputs('wsId') + '-' + this.getInputs('uwYear'),
      filters: filters
    }))
  }

  setWsHeaderSelect($event: any) {
    this.setInputs('wsHeaderSelected', $event);
  }

  unCheckAll() {
    console.log('uncheck');
    this.toggleSelectPlts(
      _.zipObject(
        _.map([...this.getInputs('listOfPlts'), ...this.getInputs('listOfDeletedPlts')], plt => plt),
        _.range(this.getInputs('listOfPlts').length + this.getInputs('listOfDeletedPlts').length).map(el => ({type: false}))
      )
    );
  }

  setSelectedProjects($event) {
    this.setInputs('projects', $event);
  }

  detectChanges() {
    if (!this.cdRef['destroyed'])
      this.cdRef.detectChanges();
  }

}
