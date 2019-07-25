import {ChangeDetectorRef, Component, NgZone, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Store} from '@ngxs/store';
import * as fromWorkspaceStore from '../../store';
import {WorkspaceState} from '../../store';
import {switchMap, tap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {Table} from 'primeng/table';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {Message} from '../../../shared/message';
import * as rightMenuStore from '../../../shared/components/plt/plt-right-menu/store/';
import {Actions as rightMenuActions} from '../../../shared/components/plt/plt-right-menu/store/actionTypes'
import {Actions as tableActions} from '../../../shared/components/plt/plt-main-table/store/actionTypes';
import * as tableStore from '../../../shared/components/plt/plt-main-table/store';
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {BaseContainer} from '../../../shared/base';
import {SystemTagsService} from '../../../shared/services/system-tags.service';
import {StateSubscriber} from '../../model/state-subscriber';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  rightMenuInputs: rightMenuStore.Input;
  tableInputs: tableStore.Input;

  private dropdown: NzDropdownContextComponent;
  searchAddress: string;
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
  pltColumnsForConfig = [
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
    {
      sortDir: 1,
      fields: '',
      header: 'User Tags',
      width: '60',
      sorted: false,
      filtred: false,
      icon: null,
      type: 'tags',
      active: true
    },
    {
      sortDir: 1,
      fields: 'pltId',
      header: 'PLT ID',
      width: '80',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'id',
      active: true
    },
    {
      sortDir: 1,
      fields: 'pltName',
      header: 'PLT Name',
      width: '60',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      active: true
    },
    {
      sortDir: 1,
      fields: 'peril',
      header: 'Peril',
      width: '80',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      textAlign: 'center',
      active: true
    },
    {
      sortDir: 1,
      fields: 'regionPerilCode',
      header: 'Region Peril Code',
      width: '130',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      active: true
    },
    {
      sortDir: 1,
      fields: 'regionPerilName',
      header: 'Region Peril Name',
      width: '160',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      active: true
    },
    {
      sortDir: 1,
      fields: 'grain',
      header: 'Grain',
      width: '90',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      active: true
    },
    {
      sortDir: 1,
      fields: 'deletedBy',
      forDelete: true,
      header: 'Deleted By',
      width: '50',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field', active: false
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
      type: 'date', active: false
    },
    {
      sortDir: 1,
      fields: 'vendorSystem',
      header: 'Vendor System',
      width: '90',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field', active: true
    },
    {
      sortDir: 1,
      fields: 'rap',
      header: 'RAP',
      width: '52',
      sorted: true,
      filtred: true,
      icon: null,
      type: 'field',
      active: true
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-note',
      type: 'icon',
      active: true
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-dollar-alt',
      type: 'icon', active: true
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '25',
      sorted: false,
      filtred: false,
      icon: 'icon-focus-add',
      type: 'icon', active: true
    },
  ];
  size = 'large';
  systemTags: any;
  systemTagsCount: any;
  userTags: any;
  userTagsCount: any;
  drawerIndex: any;
  editingTag: boolean;
  deletedPlts: any;
  selectedUserTags: any;
  contextMenuItemsCache = [
    {
      label: 'View Detail', command: () => this.viewDetail()
    },
    {
      label: 'Delete', command: () => this.deletePlt()
    },
    {
      label: 'Clone To',
      command: () => this.cloneTo()
    },
    {
      label: 'Manage Tags', command: () => this.manageTags()
    },
    {
      label: 'Restore',
      command: () => this.restore()
    }
  ];

  generateColumns = (showDelete) => this.updateTable('pltColumns', showDelete ? _.omit(_.omit(this.pltColumnsCache, '7'), '7') : this.pltColumnsCache);

  constructor(
    private nzDropdownService: NzDropdownService,
    private zone: NgZone,
    private route$: ActivatedRoute,
    private prn: PreviousNavigationService,
    private systemTagService: SystemTagsService,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.params = {};
    this.activeCheckboxSort = false;
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
    this.tableInputs = {
      dataKey: "pltId",
      openedPlt: "",
      contextMenuItems: [
        {
          label: 'View Detail', command: () => this.viewDetail()
        },
        {
          label: 'Delete', command: () => this.deletePlt()
        },
        {
          label: 'Clone To',
          command: () => this.cloneTo()
        },
        {
          label: 'Manage Tags', command: () => this.manageTags()
        }
      ],
      filterData: {},
      filters: {
        userTag: [],
        systemTag: {}
      },
      sortData: {},
      selectAll: false,
      selectAllDeletedPlts: false,
      someItemsAreSelected: false,
      someDeletedItemsAreSelected: false,
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
        {
          sortDir: 1,
          fields: '',
          header: 'User Tags',
          width: '60',
          sorted: false,
          filtred: false,
          icon: null,
          type: 'tags',
          active: true
        },
        {
          sortDir: 1,
          fields: 'pltId',
          header: 'PLT ID',
          width: '80',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'id',
          active: true
        },
        {
          sortDir: 1,
          fields: 'pltName',
          header: 'PLT Name',
          width: '60',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'peril',
          header: 'Peril',
          width: '80',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field',
          textAlign: 'center',
          active: true
        },
        {
          sortDir: 1,
          fields: 'regionPerilCode',
          header: 'Region Peril Code',
          width: '130',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'regionPerilName',
          header: 'Region Peril Name',
          width: '160',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'grain',
          header: 'Grain',
          width: '90',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'deletedBy',
          forDelete: true,
          header: 'Deleted By',
          width: '50',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field', active: false
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
          type: 'date', active: false
        },
        {
          sortDir: 1,
          fields: 'vendorSystem',
          header: 'Vendor System',
          width: '90',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field', active: true
        },
        {
          sortDir: 1,
          fields: 'rap',
          header: 'RAP',
          width: '52',
          sorted: true,
          filtred: true,
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          width: '25',
          sorted: false,
          filtred: false,
          icon: 'icon-note',
          type: 'icon',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          width: '25',
          sorted: false,
          filtred: false,
          icon: 'icon-dollar-alt',
          type: 'icon', active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          width: '25',
          sorted: false,
          filtred: false,
          icon: 'icon-focus-add',
          type: 'icon', active: true
        },
      ],
      filterInput: '',
      listOfDeletedPltsCache: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfPltsData: [],
      selectedListOfDeletedPlts: [],
      selectedListOfPlts: []
    };
    this.setRightMenuSelectedTab('pltDetail');
  }

  generateContextMenu(toRestore) {
    const t = ['Delete', 'Manage Tags', 'Clone To'];
    this.updateTable('contextMenuItems', _.filter(this.contextMenuItemsCache, e => !toRestore ? ('Restore' != e.label) : !_.find(t, el => el == e.label)));
  }

  observeRouteParams() {
    return this.route$.params.pipe(tap(({wsId, year}) => {
      this.workspaceId = wsId;
      this.uwy = year;
    }))
  }

  observeRouteParamsWithSelector(operator) {
    return this.observeRouteParams()
      .pipe(
        switchMap(() => operator()),
        this.unsubscribeOnDestroy
      )
  }

  getPlts() {
    return this.select(WorkspaceState.getPltsForPlts(this.workspaceId + '-' + this.uwy));
  }

  getDeletedPlts() {
    return this.select(WorkspaceState.getDeletedPltsForPlt(this.workspaceId + '-' + this.uwy));
  }

  getProjects() {
    return this.select(WorkspaceState.getProjectsPlt(this.workspaceId + '-' + this.uwy));
  }

  getOpenedPlt() {
    return this.select(WorkspaceState.getOpenedPlt(this.workspaceId + '-' + this.uwy));
  }

  getUserTags() {
    return this.select(WorkspaceState.getUserTagsPlt(this.workspaceId + '-' + this.uwy));
  }

  ngOnInit() {
    console.log('ngOnInit')

    this.observeRouteParams().pipe(
      this.unsubscribeOnDestroy
    ).subscribe(() => {
      console.log("LOADING PLTS")
      this.dispatch(new fromWorkspaceStore.loadAllPlts({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));
    });

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe((data) => {
      this.systemTagsCount = this.systemTagService.countSystemTags(data);
      this.updateTable('listOfPltsCache', _.map(data, (v, k) => ({...v, pltId: k})));
      this.updateTable('listOfPltsData', [...this.getTableInputKey('listOfPltsCache')]);
      this.updateTable('selectedListOfPlts', _.filter(data, (v, k) => v.selected));

      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getDeletedPlts())
      .subscribe((deletedData) => {
        this.updateTable('listOfDeletedPltsCache', _.map(deletedData, (v, k) => ({...v, pltId: k})));
        this.updateTable('listOfDeletedPltsData', [...this.getTableInputKey('listOfDeletedPltsCache')]);
        this.updateTable('selectedListOfDeletedPlts', _.filter(deletedData, (v, k) => v.selected));

        this.detectChanges();
      });

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe(data => {
      this.updateTable('selectAll',
        (this.getTableInputKey('selectedListOfPlts').length > 0 || (this.getTableInputKey('selectedListOfPlts').length == this.getTableInputKey('listOfPltsData').length))
        &&
        this.getTableInputKey('listOfPltsData').length > 0);

      this.updateTable("someItemsAreSelected", this.getTableInputKey('selectedListOfPlts').length < this.getTableInputKey('listOfPltsData').length && this.getTableInputKey('selectedListOfPlts').length > 0);
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getDeletedPlts()).subscribe(deletedPlts => {
      this.updateTable('selectAllDeletedPlts',
        (this.getTableInputKey('selectedListOfDeletedPlts').length > 0 || (this.getTableInputKey('selectedListOfDeletedPlts').length == this.getTableInputKey('listOfDeletedPltsData').length))
        &&
        this.getTableInputKey('listOfDeletedPltsData').length > 0);

      this.updateTable("someDeletedItemsAreSelected", this.getTableInputKey('selectedListOfDeletedPlts').length < this.getTableInputKey('listOfDeletedPltsData').length && this.getTableInputKey('selectedListOfDeletedPlts').length > 0);
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getProjects()).subscribe((projects: any) => {
      this.projects = _.map(projects, p => ({...p, selected: false}));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getOpenedPlt()).subscribe(openedPlt => {
      this.updateMenuKey('pltDetail', openedPlt);
      this.updateTable('openedPlt', openedPlt && openedPlt.pltId);
      this.updateMenuKey('visible', openedPlt && !openedPlt.pltId ? false : this.getRightMenuKey('visible'));
      this.detectChanges();
    })

    this.observeRouteParamsWithSelector(() => this.getUserTags()).subscribe(userTags => {
      this.userTags = userTags;
      this.detectChanges();
    })
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
  oldSelectedTags: any;
  tagForMenu: any;
  fromPlts: any;
  colorPickerIsVisible: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  wsHeaderSelected: boolean;
  leftIsHidden: any;
  filterInput: string = "";

  viewDetail() {
    this.openPltInDrawer(this.selectedPlt)
  }

  deletePlt() {
    this.dispatch(new fromWorkspaceStore.deletePlt({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltIds: this.getTableInputKey('selectedListOfPlts').length > 0 ? this.getTableInputKey('selectedListOfPlts') : [this.selectedItemForMenu]
    }));
  }

  cloneTo() {
    this.dispatch(new fromWorkspaceStore.setCloneConfig({
      cloneConfig: {
        from: 'pltBrowser',
        payload: {
          wsId: this.workspaceId,
          uwYear: this.uwy,
          plts: _.map(this.getTableInputKey('selectedListOfPlts'), plt => plt.pltId)
        }
      },
      wsIdentifier: this.workspaceId + "-" + this.uwy
    }));
    this.navigate([`workspace/${this.workspaceId}/${this.uwy}/CloneData`]);
  }

  manageTags() {
    this.tagModalVisible = true;
    this.tagForMenu = {
      ...this.tagForMenu,
      tagName: ''
    };
    this.fromPlts = true;
    this.editingTag = false;
    console.log(this.selectedItemForMenu);
    let d = _.map(this.getTableInputKey('selectedListOfPlts').length > 0 ? this.getTableInputKey('selectedListOfPlts') : [this.selectedItemForMenu], k => _.find(this.getTableInputKey('listOfPltsData'), e => e.pltId == (this.getTableInputKey('selectedListOfPlts').length > 0 ? k.pltId : k)).userTags);
    this.modalSelect = _.intersectionBy(...d, 'tagId');
    this.oldSelectedTags = _.uniqBy(_.flatten(d), 'tagId');
  }

  restore() {
    this.dispatch(new fromWorkspaceStore.restorePlt({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltIds: this.getTableInputKey('selectedListOfDeletedPlts').length > 0 ? this.getTableInputKey('selectedListOfDeletedPlts') : [this.selectedItemForMenu]
    }));
    this.updateTable('showDeleted', !(this.getTableInputKey('listOfDeletedPltsData').length === 0) ? this.getTableInputKey('showDeleted') : false);
    this.generateContextMenu(this.getTableInputKey('showDeleted'));
  }

  closePltInDrawer() {
    this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy
    }));
  }

  openPltInDrawer(plt) {
    console.log(plt);
    if(this.getRightMenuKey('pltDetail')) {
      this.closePltInDrawer();
    }
    this.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltId: plt
    }));
    this.updateMenuKey('visible', true);
  }

  resetPath() {
    this.updateTable('filterData', _.omit(this.getTableInputKey('filterData'), 'project'));
    this.projects = _.map(this.projects, p => ({...p, selected: false}));
    this.updateTable('showDeleted', false);
  }

  setSelectedProjects($event) {
    this.projects = $event;
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
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
    console.log('showDeleted', $event);
    this.updateTable('showDeleted', $event)
    this.generateContextMenu(this.getTableInputKey('showDeleted'));

    this.updateTable('selectAll',
      (this.getTableInputKey('selectedListOfPlts').length > 0 || (this.getTableInputKey('selectedListOfPlts').length == this.getTableInputKey('listOfPltsData').length))
      &&
      this.getTableInputKey('listOfPltsData').length > 0);

    this.updateTable('selectAllDeletedPlts', (this.getTableInputKey('selectedListOfDeletedPlts').length > 0 || (this.getTableInputKey('selectedListOfDeletedPlts').length == this.getTableInputKey('listOfDeletedPltsData').length)) && this.getTableInputKey('listOfDeletedPltsData').length > 0);

    this.updateTable("someDeletedItemsAreSelected", this.getTableInputKey('selectedListOfDeletedPlts').length < this.getTableInputKey('listOfDeletedPltsData').length && this.getTableInputKey('selectedListOfDeletedPlts').length > 0)

    console.log(this.tableInputs);
    // this.generateContextMenu(this.showDeleted);
  }

  unCheckAll() {
    this.toggleSelectPlts(
      _.zipObject(
        _.map([...this.getTableInputKey('listOfPltsData'), ...this.getTableInputKey('listOfDeletedPltsData')], plt => plt.pltId),
        _.range(this.getTableInputKey('listOfPltsData').length + this.getTableInputKey('listOfDeletedPltsData').length).map(el => ({type: false}))
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
    this.updateTable("filters", {
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
    if (this.managePopUp) this.pltColumnsForConfig = [...this.getTableInputKey('pltColumns')];
  }

  saveColumns() {
    this.toggleColumnsManager();
    this.updateTable('pltColumns', [...this.pltColumnsForConfig])
  }

  drop(event: CdkDragDrop<string[]>) {
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
        this.updateMenuKey('visible', false);
        this.closePltInDrawer();
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
      case tableStore.checkBoxSort:
        console.log(action.payload);
        this.updateTable('listOfPltsData', action.payload);
        break;
      case tableStore.onCheckAll:
        this.toggleSelectPlts(
          _.zipObject(
            _.map(!action.payload ? this.getTableInputKey('listOfPltsData') : this.getTableInputKey('listOfDeletedPltsData'), plt => plt.pltId),
            _.range(!action.payload ? this.getTableInputKey('listOfPltsData').length : this.getTableInputKey('listOfDeletedPltsData').length).map(el => ({type: !this.getTableInputKey('showDeleted') ? !this.getTableInputKey('selectAll') && !this.getTableInputKey("someItemsAreSelected") : !this.getTableInputKey('selectAllDeletedPlts') && !this.getTableInputKey("someDeletedItemsAreSelected")}))
          )
        );
        break;
      case tableStore.setSelectedMenuItem:
        this.selectedItemForMenu = action.payload;
        this.selectedPlt = action.payload;
        break;

      case tableStore.toggleSelectedPlts:
        this.toggleSelectPlts(action.payload);
        break;
      default:
        console.log('table action dispatcher')
    }
  }

  patchState({data: {leftNavbarCollapsed}}): void {
    console.log('patchState')
    this.leftIsHidden = leftNavbarCollapsed;
    this.detectChanges();
  }

  updateTable(key: string, value: any) {
    this.tableInputs = tableActions.updateKey.handler(this.tableInputs, key, value);
  }

  getTableInputKey(key) {
    return _.get(this.tableInputs, key);
  }

  unselectAll() {
    this.toggleSelectPlts(
      _.zipObject(
        _.map(!this.getTableInputKey('showDeleted') ? this.getTableInputKey('listOfPltsData') : this.getTableInputKey('listOfDeletedPltsData'), plt => plt.pltId),
        _.range(!this.getTableInputKey('showDeleted') ? this.getTableInputKey('listOfPltsData').length : this.getTableInputKey('listOfDeletedPltsData').length).map(el => ({type: false}))
      )
    )
  }

  filter(filterData) {
    this.updateTable('filterData', filterData);
  }

  setFilters(filters) {
    this.updateTable('filters', filters);
  }

  protected detectChanges() {
    super.detectChanges();
  }
}
