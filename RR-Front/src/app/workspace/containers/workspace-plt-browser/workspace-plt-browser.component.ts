import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  NgZone,
  OnDestroy,
  OnInit,
  TemplateRef,
  ViewChild
} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Select, Store} from '@ngxs/store';
import * as fromWorkspaceStore from '../../store';
import {PltMainState} from '../../store';
import {map, mapTo, mergeMap, switchMap, tap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {Table} from 'primeng/table';
import {combineLatest} from 'rxjs';
import {WorkspaceMainState} from '../../../core/store/states';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Message} from '../../../shared/message';
import * as rightMenuStore from '../../../shared/components/plt/plt-right-menu/store/';
import * as tableStore from '../../../shared/components/plt/plt-main-table/store';
import {Actions as rightMenuActions} from '../../../shared/components/plt/plt-right-menu/store/actionTypes'
import {Actions as tableActions} from '../../../shared/components/plt/plt-main-table/store/actionTypes'
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {BaseContainer} from '../../../shared/base';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent extends BaseContainer implements OnInit, OnDestroy {

  rightMenuInputs: rightMenuStore.Input;
  tableInputs: tableStore.Input;

  private dropdown: NzDropdownContextComponent;
  searchAddress: string;
  listOfPlts: any[];
  listOfDeletedPlts: any[];
  listOfPltsData: any[];
  listOfDeletedPltsData: any[];
  listOfDeletedPltsCache: any[];
  listOfPltsCache: any[];
  selectedListOfPlts: any[];
  selectedListOfDeletedPlts: any[];
  filterData: any;
  sortData;
  activeCheckboxSort: boolean;
  @ViewChild('dt')
  private table: Table;

  workspaceId: string;
  uwy: number;
  projects: any[];

  params: any;
  lastSelectedId;
  managePopUp: boolean;

  tagContextMenu = [
    {
      label: 'Delete Tag',
      icon: 'pi pi-trash',
      command: (event) => this.dispatch(new fromWorkspaceStore.deleteUserTag({
        wsIdentifier: this.workspaceId + '-' + this.uwy,
        userTagId: this.tagForMenu.tagId
      }))
    },
    {
      label: 'Edit Tag', icon: 'pi pi-pencil', command: (event) => {
        this.editingTag = true;
        this.fromPlts = false;
        this.tagModalVisible = true;
      }
    }
  ];

  pltColumnsForConfig= [
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '60',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'checkbox',
      active: true
    },
    {sortDir: 1, fields: '', header: 'User Tags', width: '60', sorted: false, filtred: false, icon: null, type: 'tags',active: true},
    {sortDir: 1, fields: 'pltId', header: 'PLT ID', width: '80', sorted: true, filtred: true, icon: null, type: 'id',active: true},
    {sortDir: 1, fields: 'pltName', header: 'PLT Name', width: '60', sorted: true, filtred: true, icon: null, type: 'field', active: true
    },
    {sortDir: 1, fields: 'peril', header: 'Peril', width: '80', sorted: true, filtred: true, icon: null, type: 'field', textAlign: 'center',active: true
    },
    {sortDir: 1, fields: 'regionPerilCode', header: 'Region Peril Code', width: '130', sorted: true, filtred: true, icon: null, type: 'field', active: true
    },
    {sortDir: 1, fields: 'regionPerilName', header: 'Region Peril Name', width: '160', sorted: true, filtred: true, icon: null, type: 'field',active: true
    },
    {sortDir: 1, fields: 'grain', header: 'Grain', width: '90', sorted: true, filtred: true, icon: null, type: 'field',active: true},
    {
      sortDir: 1,
      fields: 'deletedBy',
      forDelete: true,
      header: 'Deleted By',
      width: '50',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',active: false
    },
    {
      sortDir: 1,
      fields: 'deletedAt',
      forDelete: true,
      header: 'Deleted On',
      width: '50',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'date',active: false
    },
    {
      sortDir: 1,
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '90',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',active: true
    },
    {sortDir: 1, fields: 'rap', header: 'RAP', width: '52', sorted: true, filtred: true, icon: null, type: 'field',active: true},
    {sortDir: 1, fields: '', header: '', width: '25', sorted: false, filtred: false, icon: 'icon-note', type: 'icon',active: true},
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-dollar-alt',
      type: 'icon',active: true
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-focus-add',
      type: 'icon',active: true
    },
  ];

  pltColumnsCache = [
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '60',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'checkbox',
      active: true
    },
    {sortDir: 1, fields: '', header: 'User Tags', width: '60', sorted: false, filtred: false, icon: null, type: 'tags',active: true},
    {sortDir: 1, fields: 'pltId', header: 'PLT ID', width: '80', sorted: true, filtred: true, icon: null, type: 'id',active: true},
    {sortDir: 1, fields: 'pltName', header: 'PLT Name', width: '60', sorted: true, filtred: true, icon: null, type: 'field', active: true
    },
    {sortDir: 1, fields: 'peril', header: 'Peril', width: '80', sorted: true, filtred: true, icon: null, type: 'field', textAlign: 'center',active: true
    },
    {sortDir: 1, fields: 'regionPerilCode', header: 'Region Peril Code', width: '130', sorted: true, filtred: true, icon: null, type: 'field', active: true
    },
    {sortDir: 1, fields: 'regionPerilName', header: 'Region Peril Name', width: '160', sorted: true, filtred: true, icon: null, type: 'field',active: true
    },
    {sortDir: 1, fields: 'grain', header: 'Grain', width: '90', sorted: true, filtred: true, icon: null, type: 'field',active: true},
    {
      sortDir: 1,
      fields: 'deletedBy',
      forDelete: true,
      header: 'Deleted By',
      width: '50',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',active: false
    },
    {
      sortDir: 1,
      fields: 'deletedAt',
      forDelete: true,
      header: 'Deleted On',
      width: '50',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'date',active: false
    },
    {
      sortDir: 1,
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '90',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',active: true
    },
    {sortDir: 1, fields: 'rap', header: 'RAP', width: '52', sorted: true, filtred: true, icon: null, type: 'field',active: true},
    {sortDir: 1, fields: '', header: '', width: '25', sorted: false, filtred: false, icon: 'icon-note', type: 'icon',active: true},
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-dollar-alt',
      type: 'icon',active: true
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-focus-add',
      type: 'icon',active: true
    },
  ];
  size = 'large';
  filters: {
    systemTag: {},
    userTag: []
  };

  systemTags: any;

  systemTagsCount: any;

  userTags: any;

  userTagsCount: any;

  someItemsAreSelected: boolean;
  selectAll: boolean;
  drawerIndex: any;
  private pageSize: number = 20;
  private lastClick: string;
  editingTag: boolean;
  private modalInputCache: any;


  constructor(
    private nzDropdownService: NzDropdownService,
    private zone: NgZone,
    private route$: ActivatedRoute,
    private prn: PreviousNavigationService,
    _baseStore:Store,_baseRouter: Router, _baseCdr: ChangeDetectorRef
    ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.listOfPlts = [];
    this.listOfPltsData = [];
    this.listOfDeletedPltsData = [];
    this.selectedListOfPlts = [];
    this.selectedListOfDeletedPlts = [];
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.params = {};
    this.loading = true;
    this.activeCheckboxSort = false;
    this.loading = true;
    this.tagModalVisible = false;
    this.tagModalIndex = 0;
    this.systemTagsCount = {};
    this.userTagsCount = {};
    this.fromPlts = false;
    this.editingTag = false;
    this.selectedUserTags = {};
    this.colorPickerIsVisible = false;
    this.addTagModalPlaceholder = 'Select a Tag';
    this.wsHeaderSelected = true;
    this.tagForMenu = {
      tagId: null,
      tagName: '',
      tagColor: '#0700e4'
    };
    this.generateContextMenu(this.getTableInputKey('showDeleted'));
    this.generateColumns(this.getTableInputKey('showDeleted'));
    this.managePopUp = false;
    this.rightMenuInputs = {
      basket: [],
      pltDetail: null,
      selectedTab: {
        index: 0,
        title: 'pltDetail',
      },
      tabs: {'pltDetail': true},
      visible: false,
      mode: "default"
    };
    this.tableInputs= {
      contextMenuItems: [
        {
          label: 'View Detail', command: () => {
            this.openPltInDrawer(this.selectedPlt)
          }
        },
        {
          label: 'Delete', command: () => {
            this.dispatch(new fromWorkspaceStore.deletePlt({
              wsIdentifier: this.workspaceId + '-' + this.uwy,
              pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]
            }));
          }
        },
        {
          label: 'Clone To',
          command: () => {
            this.dispatch(new fromWorkspaceStore.setCloneConfig({from: 'pltBrowser', payload: { wsId: this.workspaceId, uwYear: this.uwy,plts: this.selectedListOfPlts}}));
            this.navigate([`workspace/${this.workspaceId}/${this.uwy}/CloneData`]);
          }
        },
        {
          label: 'Manage Tags', command: () => {
            this.tagModalVisible = true;
            this.tagForMenu = {
              ...this.tagForMenu,
              tagName: ''
            };
            this.fromPlts = true;
            this.editingTag = false;
            let d = _.map(this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu], k => _.find(this.listOfPltsData, e => e.pltId == k).userTags);
            this.modalSelect = _.intersectionBy(...d, 'tagId');
            this.oldSelectedTags = _.uniqBy(_.flatten(d), 'tagId');
          }
        },
        {
          label: 'Restore',
          command: () => {
            this.dispatch(new fromWorkspaceStore.restorePlt({
              wsIdentifier: this.workspaceId + '-' + this.uwy,
              pltIds: this.selectedListOfDeletedPlts.length > 0 ? this.selectedListOfDeletedPlts : [this.selectedItemForMenu]
            }))
            this.updateTable('showDeleted', !(this.listOfDeletedPlts.length === 0) ? this.getTableInputKey('showDeleted') : false);
            this.generateContextMenu(this.getTableInputKey('showDeleted'));
          }
        }
      ],
      filterData: {},
      filters: {
        userTag: [],
        systemTag: {}
      },
      sortData: {},
      selectAll: false,
      someItemsAreSelected: false,
      showDeleted: false,
      pltColumns: [
        {
          sortDir: 1,
          fields: '',
          header: '',
          width: '60',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'checkbox',
          active: true
        },
        {sortDir: 1, fields: '', header: 'User Tags', width: '60', sorted: false, filtred: false, icon: null, type: 'tags',active: true},
        {sortDir: 1, fields: 'pltId', header: 'PLT ID', width: '80', sorted: true, filtred: true, icon: null, type: 'id',active: true},
        {sortDir: 1, fields: 'pltName', header: 'PLT Name', width: '60', sorted: true, filtred: true, icon: null, type: 'field', active: true
        },
        {sortDir: 1, fields: 'peril', header: 'Peril', width: '80', sorted: true, filtred: true, icon: null, type: 'field', textAlign: 'center',active: true
        },
        {sortDir: 1, fields: 'regionPerilCode', header: 'Region Peril Code', width: '130', sorted: true, filtred: true, icon: null, type: 'field', active: true
        },
        {sortDir: 1, fields: 'regionPerilName', header: 'Region Peril Name', width: '160', sorted: true, filtred: true, icon: null, type: 'field',active: true
        },
        {sortDir: 1, fields: 'grain', header: 'Grain', width: '90', sorted: true, filtred: true, icon: null, type: 'field',active: true},
        {
          sortDir: 1,
          fields: 'deletedBy',
          forDelete: true,
          header: 'Deleted By',
          width: '50',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field',active: false
        },
        {
          sortDir: 1,
          fields: 'deletedAt',
          forDelete: true,
          header: 'Deleted On',
          width: '50',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'date',active: false
        },
        {
          sortDir: 1,
          fields: 'vendorSystem',
          header: 'Vendor System',
          width: '90',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field',active: true
        },
        {sortDir: 1, fields: 'rap', header: 'RAP', width: '52', sorted: true, filtred: true, icon: null, type: 'field',active: true},
        {sortDir: 1, fields: '', header: '', width: '25', sorted: false, filtred: false, icon: 'icon-note', type: 'icon',active: true},
        {
          sortDir: 1,
          fields: '',
          header: '',
          width: '25',
          sorted: false,
          filtred: false,
          icon: 'icon-dollar-alt',
          type: 'icon',active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          width: '25',
          sorted: false,
          filtred: false,
          icon: 'icon-focus-add',
          type: 'icon',active: true
        },
      ],
      filterInput: '',
      listOfDeletedPlts: [],
      listOfDeletedPltsCache: [],
      listOfDeletedPltsData: [],
      listOfPlts: [],
      listOfPltsCache: [],
      listOfPltsData: [],
      selectedListOfDeletedPlts: [],
      selectedListOfPlts: []
    };
    this.setRightMenuSelectedTab('pltDetail');
  }

  @Select(PltMainState.getUserTags) userTags$;
  data$: any;
  deletedPlts$: any;
  deletedPlts: any;
  loading: boolean;
  selectedUserTags: any;

  generateColumns = (showDelete) => this.updateTable('pltColumns', showDelete ? _.omit(_.omit(this.pltColumnsCache, '7'), '7') : this.pltColumnsCache);

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

  contextMenuItemsCache = this.getTableInputKey('contextMenuItems');

  generateContextMenu(toRestore) {
    const t = ['Delete', 'Manage Tags', 'Clone To'];
    this.updateTable( 'contextMenuItems',_.filter(this.contextMenuItemsCache, e => !toRestore ? ('Restore' != e.label) : !_.find(t, el => el == e.label)));
  }

  ngOnInit() {
      this.route$.params.pipe(
        switchMap(({wsId, year}) => {
          this.workspaceId = wsId;
          this.uwy = year;
          this.loading = true;
          this.data$ = this.select(PltMainState.getPlts(this.workspaceId + '-' + this.uwy));
          this.deletedPlts$ = this.select(PltMainState.getDeletedPlts(this.workspaceId + '-' + this.uwy));
          this.dispatch(new fromWorkspaceStore.loadAllPlts({
            params: {
              workspaceId: wsId, uwy: year
            }
          }));
          return combineLatest(
            this.data$,
            this.deletedPlts$
          );
        })
      ).pipe(this.unsubscribeOnDestroy).subscribe(([data, deletedData]: any) => {
        let d1 = [];
        let dd1 = [];
        let d2 = [];
        let dd2 = [];
        this.loading = false;
        this.systemTagsCount = {};

        if (data) {
          if (_.keys(this.systemTagsCount).length == 0) {
            _.forEach(data, (v, k) => {
              //Init Tags Counters

              //Grouped Sys Tags
              _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
                this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
                const tag = _.toString(v[section]);
                if (tag) {
                  this.systemTagsCount[sectionName][tag] = {selected: false, count: 0, max: 0}
                }
              });

              //NONE grouped Sys Tags
              _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
                this.systemTagsCount[sectionName] = this.systemTagsCount[sectionName] || {};
                this.systemTagsCount[sectionName][section] = {selected: false, count: 0};
                this.systemTagsCount[sectionName]['non-' + section] = {selected: false, count: 0, max: 0};
              });

            });
          }

          _.forEach(data, (v, k) => {
            d1.push({...v, pltId: k});
            d2.push(k);

            /*if (v.visible) {*/
            //Grouped Sys Tags
            _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
              const tag = _.toString(v[section]);
              if (tag) {
                if (this.systemTagsCount[sectionName][tag] || this.systemTagsCount[sectionName][tag].count === 0) {
                  const {
                    count,
                    max
                  } = this.systemTagsCount[sectionName][tag];

                  this.systemTagsCount[sectionName][tag] = {
                    ...this.systemTagsCount[sectionName][tag],
                    count: v.visible ? count + 1 : count,
                    max: max + 1
                  };
                }
              }
            });

            //NONE grouped Sys Tags
            _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
              const tag = v[section];
              if (this.systemTagsCount[sectionName][section] || this.systemTagsCount[sectionName][section] == 0) {
                const {
                  max,
                  count
                } = this.systemTagsCount[sectionName][section];
                this.systemTagsCount[sectionName][section] = {
                  ...this.systemTagsCount[sectionName][section],
                  count: v.visible ? count + 1 : count,
                  max: max + 1
                };
              }
              if (this.systemTagsCount[sectionName]['non-' + section] || this.systemTagsCount[sectionName]['non-' + section].count == 0) {
                const {
                  count,
                  max
                } = this.systemTagsCount[sectionName]['non-' + section];
                this.systemTagsCount[sectionName]['non-' + section] = {
                  ...this.systemTagsCount[sectionName]['non-' + section],
                  count: v.visible ? count + 1 : count,
                  max: max + 1
                };
              }
            });
            /*}*/

          });

          this.listOfPlts = d2;
          this.listOfPltsData = this.listOfPltsCache = d1;
          this.selectedListOfPlts = _.filter(d2, k => data[k].selected);

          _.forEach(data, (v, k) => {
            if (v.opened) {
              this.updateMenuKey('pltDetail', {pltId: k, ...v})
            }
          });
        }

        if (deletedData) {
          _.forEach(deletedData, (v, k) => {
            dd1.push({...v, pltId: k});
            dd2.push(k);
          });

          this.listOfDeletedPlts = dd2;
          this.listOfDeletedPltsData = this.listOfDeletedPltsCache = dd1;
          this.selectedListOfDeletedPlts = _.filter(dd2, k => deletedData[k].selected);
        }

        this.updateTable('selectAll', !this.getTableInputKey('showDeleted')
          ?
          (this.selectedListOfPlts.length > 0 || (this.selectedListOfPlts.length == this.listOfPlts.length)) && this.listOfPltsData.length > 0
          :
          (this.selectedListOfDeletedPlts.length > 0 || (this.selectedListOfDeletedPlts.length == this.listOfDeletedPlts.length)) && this.listOfDeletedPltsData.length > 0);

        this.updateTable("someItemsAreSelected", !this.getTableInputKey('showDeleted') ?
          this.selectedListOfPlts.length < this.listOfPlts.length && this.selectedListOfPlts.length > 0
          :
          this.selectedListOfDeletedPlts.length < this.listOfDeletedPlts.length && this.selectedListOfDeletedPlts.length > 0)
        this.detectChanges();
      })
      this.select(PltMainState.getProjects()).pipe(this.unsubscribeOnDestroy).subscribe((projects: any) => {
        this.projects = _.map(projects, p => ({...p, selected: false}));
        this.detectChanges();
      })
      this.getAttr('loading').pipe(this.unsubscribeOnDestroy).subscribe(l => this.loading = l)
      this.userTags$.pipe(this.unsubscribeOnDestroy).subscribe(userTags => {
        this.userTags = userTags || {};
        this.detectChanges();
      })
      this.select(WorkspaceMainState.getLeftNavbarIsCollapsed).pipe(this.unsubscribeOnDestroy).subscribe(l => {
        this.leftIsHidden = l;
        this.detectChanges();
      })
  }

  getAttr(path) {
    return this.select(PltMainState.getAttr).pipe(map(fn => fn(path)));
  }

  sort(sort: { key: string, value: string }): void {
    if (sort.value) {
      this.updateTable('sortData', _.merge({}, this.getTableInputKey('sortData'), {
        [sort.key]: sort.value === 'descend' ? 'desc' : 'asc'
      }))
    } else {
      this.updateTable('sortData', _.omit(this.getTableInputKey('sortData'), [sort.key]))
    }
  }

  selectedPlt: any;
  tagModalIndex: any;
  tagModalVisible: boolean;
  modalSelect: any;
  modalSelectCache: any;
  oldSelectedTags: any;
  tagForMenu: any;
  fromPlts: any;
  colorPickerIsVisible: any;
  initColor: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  wsHeaderSelected: boolean;
  leftIsHidden: any;
  filterInput: string = "";

  selectPltById(pltId) {
    return this.select(state => _.get(state, `pltMainModel.data.${this.workspaceId + '-' + this.uwy}.${pltId}`));
  }

  deletePlt() {
    this.dispatch(new fromWorkspaceStore.deletePlt({pltIds: this.selectedListOfPlts.length > 0 ? this.selectedListOfPlts : [this.selectedItemForMenu]}))
  }

  editTags() {
    this.tagModalVisible = true;
    this.fromPlts = true;
    let d = _.map(this.selectedListOfPlts, k => _.find(this.listOfPltsData, e => e.pltId == k).userTags);

    /* _.forEach( this.listOfPltsData, (v,k) => {
       if(v.selected) d.push(v.userTags);
     })*/

    //this.selectedUserTags = _.keyBy(_.intersectionBy(...d, 'tagId'), 'tagId')

    this.modalSelect = this.modalSelectCache = _.intersectionBy(...d, 'tagId');
    this.oldSelectedTags = _.uniqBy(_.flatten(d), 'tagId');
  }

  restore() {
    this.dispatch(new fromWorkspaceStore.restorePlt({pltIds: this.selectedListOfDeletedPlts.length > 0 ? this.selectedListOfDeletedPlts : [this.selectedItemForMenu]}))
    this.updateTable('showDelete', !(this.listOfDeletedPlts.length === 0) ? this.getTableInputKey('showDeleted') : false);
  }

  closePltInDrawer(pltId) {
    this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltId
    }));
  }

  openPltInDrawer(plt) {
    if(this.getRightMenuKey('pltDetail')) {
      this.closePltInDrawer(this.getRightMenuKey('pltDetail').pltId)
    }
    this.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltId: plt
    }));
    this.updateMenuKey('pltDetail', _.find(this.listOfPltsData, e => e.pltId == plt))
    this.updateMenuKey('visible', true);
  }

  resetPath() {
    this.updateTable('filterData', _.omit(this.getTableInputKey('filterData'), 'project'));
    this.projects = _.map(this.projects, p => ({...p, selected: false}))
    this.updateTable('showDeleted', false);
  }

  setSelectedProjects($event) {
    this.projects = $event;
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  checkAll($event) {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(!$event ? this.listOfPlts : this.listOfDeletedPlts, plt => plt),
        _.range(!$event ? this.listOfPlts.length : this.listOfDeletedPlts.length).map(el => ({type: !this.getTableInputKey('selectAll') && !this.getTableInputKey("someItemsAreSelected")}))
      )
    );
  }


  toggleSelectPlts(plts: any) {
    this.dispatch(new fromWorkspaceStore.ToggleSelectPlts({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts,
      forDeleted: this.getTableInputKey('showDeleted')
    }));
  }

  selectSinglePLT($event) {
    this.toggleSelectPlts($event);
  }

  checkBoxSort($event) {
    this.listOfPltsData = $event;
  }

  editTag() {
    this.dispatch(new fromWorkspaceStore.editTag({
      tag: this.tagForMenu,
      workspaceId: this.workspaceId,
      uwy: this.uwy
    }))
  }


  selectSystemTag({section, tag}) {
    _.forEach(this.systemTagsCount, (s, sKey) => {
      _.forEach(s, (t, tKey) => {
        if (tag == tKey && section == sKey) {
          this.systemTagsCount[sKey][tKey] = {...t, selected: !t.selected}
        }
      })
    })

  }

  setSelectedMenuItem($event: any) {
    this.selectedPlt = $event;
    this.selectedItemForMenu = $event;
  }

  emitFilters(filters: any) {
    this.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      filters: filters
    }))
  }

  setFromPlts($event) {
    this.fromPlts = $event;
  }

  setUserTags($event) {
    this.userTags = $event;
  }

  setModalIndex($event) {
    this.tagModalIndex = $event;
  }

  assignPltsToTag($event: any) {
    const {
      selectedTags
    } = $event;
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      ...$event,
      selectedTags: _.map(selectedTags, (el: any) => el.tagId),
      unselectedTags: _.map(_.differenceBy(this.oldSelectedTags, selectedTags, 'tagId'), (e: any) => e.tagId),
      type: 'assignOrRemove'
    }))

  }

  setTagModal($event: any) {
    this.tagModalVisible = $event;
  }

  setTagForMenu($event: any) {
    this.tagForMenu = $event;
  }

  setRenameTag($event: any) {
    this.editingTag = $event;
  }

  toggleDeletePlts($event) {
    this.updateTable('showDeleted', $event)
    this.generateContextMenu(this.getTableInputKey('showDeleted'));
    this.updateTable('selectAll', !this.getTableInputKey('showDeleted')
      ?
      (this.selectedListOfPlts.length > 0 || (this.selectedListOfPlts.length == this.listOfPlts.length)) && this.listOfPltsData.length > 0
      :
      (this.selectedListOfDeletedPlts.length > 0 || (this.selectedListOfDeletedPlts.length == this.listOfDeletedPlts.length)) && this.listOfDeletedPltsData.length > 0);

    this.updateTable("someItemsAreSelected", !this.getTableInputKey('showDeleted') ?
      this.selectedListOfPlts.length < this.listOfPlts.length && this.selectedListOfPlts.length > 0
      :
      this.selectedListOfDeletedPlts.length < this.listOfDeletedPlts.length && this.selectedListOfDeletedPlts.length > 0)
    // this.generateContextMenu(this.showDeleted);
  }

  unCheckAll() {
    this.toggleSelectPlts(
      _.zipObject(
        _.map([...this.listOfPlts, ...this.listOfDeletedPlts], plt => plt),
        _.range(this.listOfPlts.length + this.listOfDeletedPlts.length).map(el => ({type: false}))
      )
    );
  }

  setWsHeaderSelect($event: any) {
    this.wsHeaderSelected = $event;
  }

  setModalSelectedItems($event: any) {
    this.modalSelect = $event;
  }

  resetSort() {
    this.updateTable('sortData', {});
  }

  resetFilters() {
    this.updateTable('filterData', {});
    this.emitFilters({
      userTag: [],
      systemTag: []
    })
    this.userTags = _.map(this.userTags, t => ({...t, selected: false}))
    this.updateTable("filters",{
      userTag: [],
      systemTag: {}
    })
    this.filterInput = "";
  }

  createTag($event: any) {
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      ...$event,
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      type: 'createTag'
    }))
  }

  toggleColumnsManager() {
    this.managePopUp= !this.managePopUp;
    if(this.managePopUp) this.pltColumnsForConfig= [...this.getTableInputKey('pltColumns')];
  }

  saveColumns() {
    this.toggleColumnsManager();
    this.updateTable('pltColumns', [...this.pltColumnsForConfig])
  }

  drop(event: CdkDragDrop<string[]>) {
    console.log(event.previousIndex, event.currentIndex);
    moveItemInArray(this.pltColumnsForConfig, event.previousIndex + 1, event.currentIndex + 1);
  }

  toggleCol(i: number) {
    this.pltColumnsForConfig= _.merge(
      [],
      this.pltColumnsForConfig,
      { [i]: {...this.pltColumnsForConfig[i], active: !this.pltColumnsForConfig[i].active} }
      )
  }

  rightMenuActionDispatcher(action: Message) {
    switch (action.type) {
      case rightMenuStore.closeDrawer:
        this.closePltInDrawer(this.getRightMenuKey('pltDetail').pltId);
        this.rightMenuInputs = rightMenuActions.closeDrawer.handler(this.rightMenuInputs);
        break;
      default:
        console.log('default right menu action');
    }
  }

  setRightMenuSelectedTab(tab) {
    this.rightMenuInputs = rightMenuActions.setSelectedTab.handler(this.rightMenuInputs, tab)
  }

  getRightMenuKey(key) {
    return _.get(this.rightMenuInputs, key);
  }

  updateMenuKey(key: string, value: any) {
    this.rightMenuInputs = rightMenuActions.updateKey.handler(this.rightMenuInputs, key, value);
  }



  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

  tableActionDispatcher(action: Message) {
    switch (action.type) {
      case tableStore.filterData:
        this.updateTable('filterData', action.payload);
        break;
      case tableStore.setFilters:
        this.updateTable('filters', action.payload);
        break;
      case tableStore.sortChange:
        this.updateTable('sortData', action.payload);
        break;
      default:
        console.log('table action dispatcher')
    }
  }

  updateTable(key: string, value: any) {
    this.tableInputs= tableActions.updateKey.handler(this.tableInputs, key, value);
  }

  getTableInputKey(key) {
    return _.get(this.tableInputs, key);
  }

}
