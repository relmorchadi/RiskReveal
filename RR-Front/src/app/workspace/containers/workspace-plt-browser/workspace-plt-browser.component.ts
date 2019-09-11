import {ChangeDetectorRef, Component, Input, NgZone, OnDestroy, OnInit} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';
import {Actions, ofActionSuccessful, Store} from '@ngxs/store';
import * as fromWorkspaceStore from '../../store';
import {WorkspaceState} from '../../store';
import {switchMap, tap} from 'rxjs/operators';
import {ActivatedRoute, Router} from '@angular/router';
import {Message} from '../../../shared/message';
import * as rightMenuStore from '../../../shared/components/plt/plt-right-menu/store/';
import * as leftMenuStore from '../../../shared/components/plt/plt-left-menu/store';
import {Actions as rightMenuActions} from '../../../shared/components/plt/plt-right-menu/store/actionTypes'
import {Actions as tableActions} from '../../../shared/components/plt/plt-main-table/store/actionTypes';
import {Actions as leftMenuActions} from '../../../shared/components/plt/plt-left-menu/store/actionTypes';
import * as tableStore from '../../../shared/components/plt/plt-main-table/store';
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {BaseContainer} from '../../../shared/base';
import {SystemTagsService} from '../../../shared/services/system-tags.service';
import {StateSubscriber} from '../../model/state-subscriber';
import {ExcelService} from '../../../shared/services/excel.service';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  rightMenuInputs: rightMenuStore.Input;
  tableInputs: tableStore.Input;
  tagsInput: leftMenuStore.Input;

  private dropdown: NzDropdownContextComponent;
  searchAddress: string;
  workspaceId: string;
  uwy: number;
  params: any;
  lastSelectedId;
  managePopUp: boolean;
  pltColumnsCache: any[] = [
    {
      sortDir: 1,
      fields: '',
      header: '',
      width: '60',
      sorted: false,
      filtred: false,
      resizable: false,
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
      resizable: false,
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
      resizable: true,
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
      resizable: true,
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
      resizable: true,
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
      resizable: true,
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
      resizable: true,
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
      resizable: true,
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
      resizable: true,
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
      resizable: true,
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
      resizable: true,
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
      resizable: true,
      icon: null,
      type: 'field',
      active: true
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      sorted: false,
      filtred: false,
      icon: 'icon-note',
      type: 'icon',
      width: '50',
      active: true,
      tooltip: "Published for Pricing"
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      sorted: false,
      filtred: false,
      icon: 'icon-dollar-alt',
      type: 'icon',
      width: '50',
      active: true,
      tooltip: "Priced"
    },
    {
      sortDir: 1,
      fields: '',
      header: '',
      sorted: false,
      filtred: false,
      icon: 'icon-focus-add',
      type: 'icon',
      width: '50',
      active: true,
      tooltip: "Published for Accumulation"
    },
  ];
  size = 'large';
  drawerIndex: any;
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

  presetColors: string[]= ['#0700CF', '#ef5350', '#d81b60', '#6a1b9a', '#880e4f', '#64ffda', '#00c853', '#546e7a'];

  constructor(
    private nzDropdownService: NzDropdownService,
    private zone: NgZone,
    private route$: ActivatedRoute,
    private prn: PreviousNavigationService,
    private systemTagService: SystemTagsService,
    private excel: ExcelService,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
    private actions$: Actions
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.params = {};
    this.addTagModalPlaceholder = 'Select a Tag';
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
      scrollHeight: 'calc(100vh - 480px)',
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
          sorted: false,
          filtred: false,
          resizable: false,
          width: '25%',
          icon: null,
          type: 'checkbox',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: 'User Tags',
          sorted: false,
          filtred: false,
          resizable: false,
          width: '24%',
          icon: null,
          type: 'tags',
          active: true
        },
        {
          sortDir: 1,
          fields: 'pltId',
          header: 'PLT ID',
          sorted: true,
          filtred: true,
          resizable: true,
          icon: null,
          width: '28%',
          type: 'id',
          active: true
        },
        {
          sortDir: 1,
          fields: 'pltName',
          header: 'PLT Name',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '80%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'peril',
          header: 'Peril',
          sorted: true,
          filtred: true,
          resizable: false,
          width: '22%',
          icon: null,
          type: 'field',
          textAlign: 'center',
          active: true
        },
        {
          sortDir: 1,
          fields: 'regionPerilCode',
          header: 'Region Peril Code',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '35%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'regionPerilName',
          header: 'Region Peril Name',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '60%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'grain',
          header: 'Grain',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '70%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: 'deletedBy',
          forDelete: true,
          header: 'Deleted By',
          sorted: true,
          filtred: true,
          resizable: true,
          icon: null,
          type: 'field', active: false
        },
        {
          sortDir: 1,
          fields: 'deletedAt',
          forDelete: true,
          header: 'Deleted On',
          sorted: true,
          filtred: true,
          resizable: true,
          icon: null,
          type: 'date', active: false
        },
        {
          sortDir: 1,
          fields: 'vendorSystem',
          header: 'Vendor System',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '25%',
          icon: null,
          type: 'field', active: true
        },
        {
          sortDir: 1,
          fields: 'rap',
          header: 'RAP',
          sorted: true,
          filtred: true,
          resizable: true,
          width: '25%',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          width: '25px',
          icon: 'icon-note',
          type: 'icon',
          active: true,
          tooltip: "Published for Pricing"
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          width: '25px',
          icon: 'icon-dollar-alt',
          type: 'icon',
          active: true,
          tooltip: "Priced"
        },
        {
          sortDir: 1,
          fields: '',
          header: '',
          sorted: false,
          filtred: false,
          width: '25px',
          icon: 'icon-focus-add',
          type: 'icon',
          active: true,
          tooltip: "Published for Accumulation"
        },
      ],
      filterInput: '',
      listOfDeletedPltsCache: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfPltsData: [],
      selectedListOfDeletedPlts: [],
      selectedListOfPlts: [],
      status: {
        Published: {
          selected: false
        },
        Priced: {
          selected: false
        },
        Accumulated: {
          selected: false
        },
      }
    };
    this.tagsInput= {
      wsId: this.workspaceId,
      uwYear: this.uwy,
      projects: [],
      showDeleted: this.tableInputs['showDeleted'],
      filterData: this.tableInputs['filterData'],
      filters: this.tableInputs['filters'],
      deletedPltsLength: this.tableInputs['listOfDeletedPltsData'].length,
      userTags: [],
      selectedListOfPlts: this.tableInputs['selectedListOfPlts'],
      systemTagsCount: {},
      _tagModalVisible: false,
      wsHeaderSelected: true,
      pathTab: true,
      assignedTags: [],
      assignedTagsCache: [],
      toAssign: [],
      toRemove: [],
      usedInWs: [],
      allTags: [],
      suggested: [],
      selectedTags: {},
      operation: null
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

      this.updateTagsInput('wsId', this.workspaceId);
      this.updateTagsInput('uwYear', this.uwy);
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

  getUserTagManager() {
    return this.select(WorkspaceState.getUserTagManager(this.workspaceId + '-' + this.uwy))
  }

  ngOnInit() {
    console.log('ngOnInit')

    this.observeRouteParams().pipe(
      this.unsubscribeOnDestroy
    ).subscribe(() => {
      this.dispatch(new fromWorkspaceStore.loadAllPlts({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));
    });

    this.observeRouteParamsWithSelector(() => this.getPlts()).subscribe((data) => {
      this.updateTagsInput('systemTagsCount', this.systemTagService.countSystemTags(data));
      this.updateTable('listOfPltsCache', _.map(data, (v, k) => ({...v, pltId: k})));
      this.updateTable('listOfPltsData', [...this.getTableInputKey('listOfPltsCache')]);
      this.updateTable('selectedListOfPlts', _.filter(data, (v, k) => v.selected));

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

    this.observeRouteParamsWithSelector(() => this.getDeletedPlts())
      .subscribe((deletedData) => {
        this.updateTable('listOfDeletedPltsCache', _.map(deletedData, (v, k) => ({...v, pltId: k})));
        this.updateTable('listOfDeletedPltsData', [...this.getTableInputKey('listOfDeletedPltsCache')]);
        this.updateTable('selectedListOfDeletedPlts', _.filter(deletedData, (v, k) => v.selected));

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
      this.updateTagsInput('projects', _.map(projects, p => ({...p, selected: false})));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getOpenedPlt()).subscribe(openedPlt => {
      this.updateMenuKey('pltDetail', openedPlt);
      this.updateTable('openedPlt', openedPlt && openedPlt.pltId);
      this.updateMenuKey('visible', openedPlt && !openedPlt.pltId ? false : this.getRightMenuKey('visible'));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getUserTags()).subscribe(userTags => {
      this.updateTagsInput('userTags', userTags);
      this.detectChanges();
    })

    this.observeRouteParamsWithSelector(() => this.getUserTagManager()).subscribe( userTagManager => {
      this.updateTagsInput('allTags', _.toArray(userTagManager.allTags));
      this.updateTagsInput('suggested', _.toArray(userTagManager.suggested));
      this.updateTagsInput('usedInWs', _.toArray(userTagManager.usedInWs));
      this.detectChanges();
    })

    this.actions$
      .pipe(
        ofActionSuccessful(fromWorkspaceStore.AddNewTag)
      ).subscribe( (userTag) => {
        console.log(userTag);
        this.detectChanges();
    })

    this.actions$
      .pipe(
        ofActionSuccessful(fromWorkspaceStore.DeleteTag)
      ).subscribe( userTag => {
        console.log(userTag);
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
  tagForMenu: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  leftIsHidden: any;

  viewDetail() {
    this.openPltInDrawer(this.selectedPlt)
  }

  deletePlt() {
    this.dispatch(new fromWorkspaceStore.deletePlt({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltIds: this.getTableInputKey('selectedListOfPlts').length > 0 ? this.getTableInputKey('selectedListOfPlts').map(plt => plt.pltId) : [this.selectedItemForMenu]
    }));
  }

  cloneTo() {
    this.dispatch(new fromWorkspaceStore.setCloneConfig({
      cloneConfig: {
        from: 'pltBrowser',
        type: 'cloneTo',
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
    this.tagsInput= {
      ...this.tagsInput,
      wsId: this.workspaceId,
      uwYear: this.uwy,
      showDeleted: this.tableInputs['showDeleted'],
      deletedPltsLength: this.tableInputs['listOfDeletedPltsData'].length,
      selectedListOfPlts: this.tableInputs['selectedListOfPlts'],
      _tagModalVisible: true,
      pathTab:true,
    };

    let d = _.map(this.getTableInputKey('selectedListOfPlts').length > 0 ? this.getTableInputKey('selectedListOfPlts') : [this.selectedItemForMenu], k => _.find(this.getTableInputKey('listOfPltsData'), e => e.pltId == (this.getTableInputKey('selectedListOfPlts').length > 0 ? k.pltId : k)).userTags);

    d= _.uniqBy(_.flatten(d), 'tagId');
    this.updateTagsInput('assignedTags', this.updateTagsType(d));
    this.updateTagsInput('assignedTagsCache', _.cloneDeep(d));

    this.dispatch(new fromWorkspaceStore.GetTagsBySelection({
      userId: 1,
      uwYear: this.tagsInput.uwYear,
      wsId: this.tagsInput.wsId,
      selectedTags: this.tagsInput.assignedTags
    }));

    //this.updateTagsInput('assignedTagsCache', _.uniqBy(_.flatten(d), 'tagId'));
  }

  restore() {
    this.dispatch(new fromWorkspaceStore.restorePlt({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltIds: this.getTableInputKey('selectedListOfDeletedPlts').length > 0 ? this.getTableInputKey('selectedListOfDeletedPlts').map(plt => plt.pltId) : [this.selectedItemForMenu]
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
    this.updateTableAndTagsInputs('filterData', _.omit(this.getTableInputKey('filterData'), 'project'));
    this.updateTagsInput('projects', _.map(this.tagsInput.projects, p => ({...p, selected: false})));
    this.updateTableAndTagsInputs('showDeleted', false);
  }

  setSelectedProjects($event) {
    this.updateTagsInput('projects', $event);
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


  selectSystemTag({section, tag}) {
    let newSysTagsCount= {};
    _.forEach(this.tagsInput.systemTagsCount, (s, sKey) => {
      _.forEach(s, (t, tKey) => {
        if (tag == tKey && section == sKey) {
          newSysTagsCount[sKey][tKey] = {...t, selected: !t.selected}
        }
      })
    });

    this.updateTagsInput('systemTagsCount', newSysTagsCount);
  }

  emitFilters(filters: any) {
    this.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      filters: filters
    }))
  }

  setUserTags($event) {
    this.updateTagsInput('userTags', $event);
  }

  assignPltsToTag($event: any) {
    const {
      selectedTags
    } = $event;
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      ...$event,
      selectedTags: _.map(selectedTags, (el: any) => el.tagId),
      unselectedTags: _.map(_.differenceBy(this.tagsInput.assignedTagsCache, selectedTags, 'tagId'), (e: any) => e.tagId),
      type: 'assignOrRemove'
    }))

  }

  setTagModal($event: any) {
    this.updateTagsInput('_tagModalVisible', $event);
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
    this.updateTagsInput('wsHeaderSelected', $event);
  }

  resetSort() {
    this.updateTable('sortData', {});
  }

  resetFilters() {
    this.updateTableAndTagsInputs('filterData', {});
    this.emitFilters({
      userTag: [],
      systemTag: []
    })
    this.updateTagsInput('userTags', _.map(this.tagsInput.userTags, t => ({...t, selected: false})));
    this.updateTableAndTagsInputs("filters", {
      userTag: [],
      systemTag: {}
    })
  }

  createTag($event: any) {
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      ...$event,
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      type: 'createTag'
    }))
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
        this.updateTableAndTagsInputs('filterData', action.payload);
        break;
      case tableStore.setFilters:
        this.updateTableAndTagsInputs('filters', action.payload);
        break;
      case tableStore.sortChange:
        this.updateTable('sortData', action.payload);
        break;
      case tableStore.checkBoxSort:
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
        console.log(action.payload);
        this.toggleSelectPlts(action.payload);
        break;

      case tableStore.filterByStatus:
        const status= this.getTableInputKey('status');

        this.updateTable('status', {
          ...status,
          [action.payload]: {
            selected: !status[action.payload].selected
          }
        });
        this.dispatch(new fromWorkspaceStore.FilterPltsByStatus({
          wsIdentifier: this.workspaceId+"-"+this.uwy,
          status: this.getTableInputKey('status')
        }));
        break;



      default:
        console.log('table action dispatcher')
    }
  }

  patchState({data: {leftNavbarCollapsed}}): void {
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
    this.updateTableAndTagsInputs('filterData', filterData);
  }

  setFilters(filters) {
    this.updateTableAndTagsInputs('filters', filters);
  }

  protected detectChanges() {
    super.detectChanges();
  }

  cloneFrom() {
    this.dispatch(new fromWorkspaceStore.setCloneConfig({
      cloneConfig: {
        from: 'pltBrowser',
        type: 'cloneFrom',
        payload: {
          wsId: this.workspaceId,
          uwYear: this.uwy,
        }
      },
      wsIdentifier: this.workspaceId + "-" + this.uwy
    }));
    this.navigate([`workspace/${this.workspaceId}/${this.uwy}/CloneData`])
  }

  exportTable() {
    this.excel.exportAsExcelFile(this.tableInputs.listOfPltsData.map(e => _.omit(e, ['userTags','selected', 'visible', 'tagFilterActive', 'opened', 'deleted'])), 'sample')
  }

  toggleColumnsManager() {
    this.managePopUp= !this.managePopUp;
  }

  resetColumns() {
    this.managePopUp= false;
  }

  saveColumns(listOfUsedColumns) {
    this.toggleColumnsManager();
    this.updateTable('pltColumns', [...listOfUsedColumns]);
  }

  leftMenuActionDispatcher(action: Message) {
    console.log(action);

    switch (action.type) {

      case leftMenuStore.unCkeckAllPlts:
        this.unCheckAll();
        break;
      case leftMenuStore.emitFilters:
        this.emitFilters(action.payload);
        break;
      case leftMenuStore.setFilters:
        this.setFilters(action.payload);
        break;
      case leftMenuStore.resetPath:
        this.resetPath();
        break;
      case leftMenuStore.headerSelection:
        this.setWsHeaderSelect(action.payload);
        break;
      case leftMenuStore.filterByProject:
        this.filter(action.payload);
        break;
      case leftMenuStore.onSelectProjects:
        this.setSelectedProjects(action.payload);
        break;
      case leftMenuStore.setTagModalVisibility:
        this.setTagModal(action.payload);
        break;
      case leftMenuStore.toggleDeletedPlts:
        this.toggleDeletePlts(action.payload);
        break;
      case leftMenuStore.onSelectSysTagCount:
        this.selectSystemTag(action.payload);
        break;
      case leftMenuStore.onSetSelectedUserTags:
        this.setUserTags(action.payload);
        break;

        //Tag Manager

      case leftMenuStore.addNewTag:
        this.addNewTag(action.payload)
        break;
      case leftMenuStore.toggleTag:
        this.toggleTag(action.payload);
        break;
      case leftMenuStore.confirmSelection:
        this.confirmSelection();
        break;
      case leftMenuStore.clearSelection:
        this.clearSelection();
        break;
      case leftMenuStore.save:
        this.save();
        break;
    }
  }

  updateTagsInput(key, value) {
    this.tagsInput= {...this.tagsInput, [key]: value};
  }

  updateTableAndTagsInputs(key, value) {
    this.updateTagsInput(key, value);
    this.updateTable(key,value);
  }

  addNewTag(tag) {
    /*this.updateTagsInput('toAssign', _.concat(this.tagsInput.toAssign, tag));
    this.updateTagsInput('assignedTags', _.concat(this.tagsInput.assignedTags, tag));*/
    this.updateTagsInput('assignedTags', _.concat(this.tagsInput.assignedTags, tag));
    this.updateTagsInput('toAssign', _.concat(this.tagsInput.toAssign, tag));
  }

  toggleTag({i, operation, source}) {
    if(operation == this.tagsInput['operation']) {
      if(!_.find(this.tagsInput.selectedTags, tag => tag.tagId == this.tagsInput[source][i].tagId)) {
        this.updateTagsInput('selectedTags', _.merge({}, this.tagsInput.selectedTags, { [this.tagsInput[source][i].tagId]: {...this.tagsInput[source][i]} }));
      }else {
        this.updateTagsInput('selectedTags', _.omit(this.tagsInput.selectedTags, this.tagsInput[source][i].tagId));
      }
    }else {
      this.updateTagsInput('operation', operation);
      this.updateTagsInput('selectedTags', _.merge({}, { [this.tagsInput[source][i].tagId]: {...this.tagsInput[source][i]} }));
    }

    if(!_.keys(this.tagsInput.selectedTags).length) this.updateTagsInput('operation', null);
  }

  confirmSelection() {
    const tags = _.map(this.tagsInput.selectedTags, t => ({...t, type: 'full'}));
    if(this.tagsInput.operation == 'assign') {
      this.updateTagsInput('toAssign', _.uniqBy(_.concat(this.tagsInput.toAssign, tags), 'tagId'))
      this.updateTagsInput('assignedTags', _.uniqBy(_.concat(this.tagsInput.assignedTags, tags), 'tagId'))
    }

    if(this.tagsInput.operation == 'de-assign') {
      this.updateTagsInput('toAssign', _.filter(this.tagsInput.toAssign, tag => !_.find(tags, t => tag.tagId == t.tagId)));
      this.updateTagsInput('assignedTags', _.filter(this.tagsInput.assignedTags, tag => !_.find(tags, t => tag.tagId == t.tagId)));
    }

    this.clearSelection();
  }

  checkTagType( tag ) {
    return _.every(this.getTableInputKey('selectedListOfPlts'), (plt) =>  _.some(plt.userTags, t => t.tagId == tag.tagId)) ? 'full' : 'half';
  }

  updateTagsType(d) {
    return _.map(d, tag => ({...tag, type: this.checkTagType(tag)}))
  }

  clearSelection() {
    this.updateTagsInput('selectedTags', {});
    this.updateTagsInput('operation', null);
  }

  save() {
    this.dispatch(new fromWorkspaceStore.AssignPltsToTag({
      userId: 1,
      wsId: this.workspaceId,
      uwYear: this.uwy,
      plts: _.map(this.getTableInputKey('selectedListOfPlts'), plt => plt.pltId),
      selectedTags: this.tagsInput.toAssign,
      unselectedTags: _.differenceBy(this.tagsInput.assignedTagsCache, this.tagsInput.assignedTags, 'tagId')
    }));
    this.tagsInput = {
      ...this.tagsInput,
      _tagModalVisible: false,
      wsHeaderSelected: true,
      assignedTags: [],
      assignedTagsCache: [],
      usedInWs: [],
      allTags: [],
      suggested: [],
      selectedTags: {},
      operation: null
    };
  }
}

