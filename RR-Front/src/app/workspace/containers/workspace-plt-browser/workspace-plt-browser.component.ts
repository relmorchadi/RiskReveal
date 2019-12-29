import {
  ChangeDetectorRef,
  Component,
  NgZone,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
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
import * as tagsStore from '../../../shared/components/plt/plt-tag-manager/store';
import {Actions as rightMenuActions} from '../../../shared/components/plt/plt-right-menu/store/actionTypes'
import {Actions as tableActions} from '../../../shared/components/plt/plt-main-table/store/actionTypes';
import * as tableStore from '../../../shared/components/plt/plt-main-table/store';
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {BaseContainer} from '../../../shared/base';
import {SystemTagsService} from '../../../shared/services/system-tags.service';
import {StateSubscriber} from '../../model/state-subscriber';
import {ExcelService} from '../../../shared/services/excel.service';
import produce from "immer";
import {ResizedEvent} from "angular-resize-event";
import {PltTableService} from "../../services/helpers/plt-table.service";

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  rightMenuInputs: rightMenuStore.Input;
  tableInputs: tableStore.Input;
  leftMenuInputs: leftMenuStore.Input;
  tagsInputs: tagsStore.Input;
  collapsedTags: boolean= true;
  @ViewChild('leftMenu') leftMenu: any;

  private dropdown: NzDropdownContextComponent;
  searchAddress: string;
  workspaceId: string;
  uwy: number;
  params: any;
  lastSelectedId;
  managePopUp: boolean;
  pltColumnsCache: any[];
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

  generateColumns = (showDelete) => this.updateTable('pltColumns', !showDelete ? _.toArray(_.omit(this.pltColumnsCache, this.pltColumnsCache.length - 1)) : this.pltColumnsCache);

  constructor(
    private nzDropdownService: NzDropdownService,
    private zone: NgZone,
    private route$: ActivatedRoute,
    private prn: PreviousNavigationService,
    private systemTagService: SystemTagsService,
    private excel: ExcelService,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
    private actions$: Actions,
    private pltTableService: PltTableService
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.params = {};
    this.addTagModalPlaceholder = 'Select a Tag';
    this.pltColumnsCache = PltTableService.pltColumns;
    this.generateContextMenu(this.getTableInputKey('showDeleted'));
    this.generateColumns(this.getTableInputKey('showDeleted'));
    this.managePopUp = false;
    this.rightMenuInputs = {
      basket: [],
      pltDetail: null,
      pltHeaderId: '',
      selectedTab: {
        index: 0,
        title: 'pltDetail',
      },
      tabs: {'pltDetail': true},
      visible: false,
      mode: "default",
      summary: {}
    };
    this.tableInputs = {
      scrollConfig: {
        containerHeight: null,
        containerGap: '49px',
      },
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
      pltColumns: PltTableService.pltColumns,
      filterInput: '',
      listOfDeletedPltsCache: [],
      listOfDeletedPltsData: [],
      listOfPltsCache: [],
      listOfPltsData: [],
      selectedListOfDeletedPlts: [],
      selectedListOfPlts: [],
      status: {
        arcPublication: {
          selected: false
        },
        xactPublication: {
          selected: false
        },
        xactPriced: {
          selected: false
        },
        groupedPlt: {
          selected: false
        },
        clonedPlt: {
          selected: false
        },
        clientAdjustment: {
          selected: false
        },
        defaultAdjustment: {
          selected: false
        },
        baseAdjustment: {
          selected: false
        }
      }
    };
    this.leftMenuInputs= {
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
      wsHeaderSelected: true,
      pathTab: true,
    };
    this.tagsInputs= {
      _tagModalVisible: false,
      toRemove: [],
      toAssign: [],
      assignedTags: [],
      assignedTagsCache: [],
      operation: null,
      selectedTags: [],
      allTags: [],
      suggested: [],
      usedInWs: []
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

      this.updateLeftMenuInputs('wsId', this.workspaceId);
      this.updateLeftMenuInputs('uwYear', this.uwy);
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

  getSummary() {
    return this.select(WorkspaceState.getSummary(this.workspaceId + '-' + this.uwy));
  }

  ngOnInit() {

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
      this.updateLeftMenuInputs('systemTagsCount', this.systemTagService.countSystemTags(data));
      this.updateTable('listOfPltsCache', _.toArray(data));
      this.updateTable('listOfPltsData', this.getTableInputKey('listOfPltsCache'));
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
        this.updateTable('listOfDeletedPltsCache', _.toArray(deletedData));
        this.updateTable('listOfDeletedPltsData', this.getTableInputKey('listOfDeletedPltsCache'));
        this.updateTable('selectedListOfDeletedPlts', _.filter(deletedData, (v, k) => v.selected));

        this.updateLeftMenuInputs('deletedPltsLength', this.getTableInputKey('listOfDeletedPltsData').length);

        this.detectChanges();
      });

    this.observeRouteParamsWithSelector(() => this.getDeletedPlts()).subscribe(deletedPlts => {
      this.updateTable('selectAllDeletedPlts',
        (this.getTableInputKey('selectedListOfDeletedPlts').length > 0 || (this.getTableInputKey('selectedListOfDeletedPlts').length == this.getTableInputKey('listOfDeletedPltsData').length))
        &&
        this.getTableInputKey('listOfDeletedPltsData').length > 0);

      this.updateTable("someDeletedItemsAreSelected", this.getTableInputKey('selectedListOfDeletedPlts').length < this.getTableInputKey('listOfDeletedPltsData').length && this.getTableInputKey('selectedListOfDeletedPlts').length > 0);
      this.updateTableAndLeftMenuInputs('showDeleted', !(this.getTableInputKey('listOfDeletedPltsData').length === 0) ? this.getTableInputKey('showDeleted') : false);
      this.generateColumns(this.getTableInputKey('showDeleted'));
      this.generateContextMenu(this.getTableInputKey('showDeleted'));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getProjects()).subscribe((projects: any) => {
      this.updateLeftMenuInputs('projects', _.map(projects, p => ({...p, selected: false})));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getUserTags()).subscribe(userTags => {
      this.updateLeftMenuInputs('userTags', userTags);
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getOpenedPlt()).subscribe(openedPlt => {
      this.updateTable('openedPlt', openedPlt && openedPlt.pltId);
      this.updateMenuKey('visible', openedPlt && !openedPlt.pltId ? false : this.getRightMenuKey('visible'));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getUserTagManager()).subscribe( userTagManager => {
      this.updateTagsInput('allTags', _.toArray(userTagManager.allTags || []));
      this.updateTagsInput('suggested', _.toArray(userTagManager.suggested || []));
      this.updateTagsInput('usedInWs', _.toArray(userTagManager.usedInWs || []));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getSummary()).subscribe( summary => {
      this.updateMenuKey('pltDetail', summary);
      this.updateMenuKey('summary', summary);
      this.detectChanges();
    })

    this.actions$
      .pipe(
        ofActionSuccessful(fromWorkspaceStore.AddNewTag)
      ).subscribe( (userTag) => {
      this.detectChanges();
    });

    this.actions$
      .pipe(
        ofActionSuccessful(fromWorkspaceStore.DeleteTag)
      ).subscribe( userTag => {
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
      params: {
        workspaceId: this.workspaceId, uwy: this.uwy
      },
      request: {
        pltHeaderIds: this.getTableInputKey('selectedListOfPlts').length > 0 ? this.getTableInputKey('selectedListOfPlts').map(plt => plt.pltId) : [this.selectedItemForMenu],
        deletedBy: "NAJIH DRISS",
        deletedDue: "WRONG RESULT",
        deletedOn: new Date()
      }
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
    this.updateTagsInput('_tagModalVisible', true);

    let d = _.map(this.getTableInputKey('selectedListOfPlts').length > 0 ? this.getTableInputKey('selectedListOfPlts') : [this.selectedItemForMenu], k => _.find(this.getTableInputKey('listOfPltsData'), e => e.pltId == (this.getTableInputKey('selectedListOfPlts').length > 0 ? k.pltId : k)).userTags);

    d= _.uniqBy(_.flatten(d), 'tagId');
    this.updateTagsInput('assignedTags', this.updateTagsType(d));
    this.updateTagsInput('assignedTagsCache', _.cloneDeep(d));

    this.dispatch(new fromWorkspaceStore.GetTagsBySelection({
      userId: 1,
      uwYear: this.leftMenuInputs.uwYear,
      wsId: this.leftMenuInputs.wsId,
      selectedTags: this.tagsInputs.assignedTags
    }));

    //this.updateTagsInput('assignedTagsCache', _.uniqBy(_.flatten(d), 'tagId'));
  }

  restore() {
    this.dispatch(new fromWorkspaceStore.restorePlt({
      params: {
        workspaceId: this.workspaceId, uwy: this.uwy
      },
      pltHeaderIds: this.getTableInputKey('selectedListOfDeletedPlts').length > 0 ? this.getTableInputKey('selectedListOfDeletedPlts').map(plt => plt.pltId) : [this.selectedItemForMenu]
    }));
    this.updateLeftMenuInputs('showDeleted', !(this.getTableInputKey('listOfDeletedPltsData').length === 0) ? this.getTableInputKey('showDeleted') : false);
    this.generateContextMenu(this.getTableInputKey('showDeleted'));
  }

  closePltInDrawer() {
    this.dispatch(new fromWorkspaceStore.ClosePLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy
    }));
  }

  openPltInDrawer(plt) {
    if(this.getRightMenuKey('pltDetail')) {
      this.closePltInDrawer();
    }
    this.dispatch(new fromWorkspaceStore.OpenPLTinDrawer({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      pltId: plt
    }));
    this.updateMenuKey('pltHeaderId', plt);
    this.updateMenuKey('visible', true);
  }

  resetPath() {
    this.updateTableAndLeftMenuInputs('filterData', _.omit(this.getTableInputKey('filterData'), 'project'));
    this.updateLeftMenuInputs('projects', _.map(this.leftMenuInputs.projects, p => ({...p, selected: false})));
    this.updateTableAndLeftMenuInputs('showDeleted', false);
  }

  setSelectedProjects($event) {
    this.updateLeftMenuInputs('projects', $event);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }


  toggleSelectPlts(plts: any) {
    console.log(plts);
    this.dispatch(new fromWorkspaceStore.ToggleSelectPlts({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts,
      forDelete: this.getTableInputKey('showDeleted')
    }));
  }

  selectSinglePLT($event) {
    this.toggleSelectPlts($event);
  }


  selectSystemTag({section, tag}) {

    this.updateLeftMenuInputs('systemTagsCount', produce( this.leftMenuInputs.systemTagsCount, draft => {
      _.forEach(this.leftMenuInputs.systemTagsCount, (s, sKey) => {
        _.forEach(s, (t, tKey) => {
          if (tag == tKey && section == sKey) {
            draft[sKey][tKey] = {...t, selected: !t.selected}
          }
        })
      });
    }));
  }

  emitFilters(filters: any) {
    this.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      filters: filters
    }))
  }

  setUserTags($event) {
    this.updateLeftMenuInputs('userTags', $event);
  }

  assignPltsToTag($event: any) {
    const {
      selectedTags
    } = $event;
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      ...$event,
      selectedTags: _.map(selectedTags, (el: any) => el.tagId),
      unselectedTags: _.map(_.differenceBy(this.tagsInputs.assignedTagsCache, selectedTags, 'tagId'), (e: any) => e.tagId),
      type: 'assignOrRemove'
    }))

  }

  setTagModal($event: any) {
    this.updateTagsInput('_tagModalVisible', $event);
  }

  toggleDeletePlts($event) {
    this.updateTableAndLeftMenuInputs('showDeleted', $event)
    this.generateContextMenu(this.getTableInputKey('showDeleted'));
    this.generateColumns(this.getTableInputKey('showDeleted'));

    this.updateTable('selectAll',
      (this.getTableInputKey('selectedListOfPlts').length > 0 || (this.getTableInputKey('selectedListOfPlts').length == this.getTableInputKey('listOfPltsData').length))
      &&
      this.getTableInputKey('listOfPltsData').length > 0);

    this.updateTable('selectAllDeletedPlts', (this.getTableInputKey('selectedListOfDeletedPlts').length > 0 || (this.getTableInputKey('selectedListOfDeletedPlts').length == this.getTableInputKey('listOfDeletedPltsData').length)) && this.getTableInputKey('listOfDeletedPltsData').length > 0);

    this.updateTable("someDeletedItemsAreSelected", this.getTableInputKey('selectedListOfDeletedPlts').length < this.getTableInputKey('listOfDeletedPltsData').length && this.getTableInputKey('selectedListOfDeletedPlts').length > 0)

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
    this.updateLeftMenuInputs('wsHeaderSelected', $event);
  }

  resetSort() {
    this.updateTable('sortData', {});
  }

  resetFilters() {
    this.updateTableAndLeftMenuInputs('filterData', {});
    this.emitFilters({
      userTag: [],
      systemTag: []
    });
    this.updateLeftMenuInputs('userTags', _.map(this.leftMenuInputs.userTags, t => ({...t, selected: false})));
    this.updateTableAndLeftMenuInputs("filters", {
      userTag: [],
      systemTag: {}
    });
    this.updateTable("status", {"Published":{"selected":false},"Priced":{"selected":false},"Accumulated":{"selected":false}});
    this.dispatch(new fromWorkspaceStore.FilterByFalesely({
      wsIdentifier: this.workspaceId+"-"+this.uwy,
      status: this.getTableInputKey('status')
    }));
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

      case rightMenuStore.loadTab:
        this.loadPLTDetailsTab(action.payload);
        break;
      default:
        console.log('default right menu action');
    }
  }

  loadPLTDetailsTab(tabIndex) {
    switch (tabIndex) {
      case 0:
        this.loadSummaryTab(this.getRightMenuKey('pltHeaderId'))
        break;
    }
  }

  loadSummaryTab(pltHeaderId) {
    this.dispatch(new fromWorkspaceStore.loadSummaryDetail(pltHeaderId));
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
        this.updateTableAndLeftMenuInputs('filterData', action.payload);
        break;
      case tableStore.setFilters:
        this.updateTableAndLeftMenuInputs('filters', action.payload);
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
        this.toggleSelectPlts(action.payload);
        break;

      case tableStore.filterByFalesely:
        const status= this.getTableInputKey('status');

        console.log(status, action.payload);

        this.updateTable('status', {
          ...status,
          [action.payload]: {
            selected: !status[action.payload].selected
          }
        });

        /*this.dispatch(new fromWorkspaceStore.FilterByFalesely({
          wsIdentifier: this.workspaceId+"-"+this.uwy,
          status: this.getTableInputKey('status')
        }));*/
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
    this.updateTableAndLeftMenuInputs('filterData', filterData);
  }

  setFilters(filters) {
    this.updateTableAndLeftMenuInputs('filters', filters);
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
    this.managePopUp = !this.managePopUp;
  }

  resetColumns() {
    this.managePopUp = false;
  }

  saveColumns(listOfUsedColumns) {
    this.toggleColumnsManager();
    this.updateTable('pltColumns', [...listOfUsedColumns]);
  }

  leftMenuActionDispatcher(action: Message) {

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
      case leftMenuStore.toggleDeletedPlts:
        this.toggleDeletePlts(action.payload);
        break;
      case leftMenuStore.onSelectSysTagCount:
        this.selectSystemTag(action.payload);
        break;
      case leftMenuStore.onSetSelectedUserTags:
        this.setUserTags(action.payload);
        break;

      case 'Detect Parent Changes':
        this.detectChanges();
    }
  }

  updateTagsInput(key, value) {
    this.tagsInputs= {...this.tagsInputs, [key]: value};
  }

  updateLeftMenuInputs(key, value) {
    this.leftMenuInputs= {...this.leftMenuInputs, [key]: value};
  }

  updateTableAndLeftMenuInputs(key, value) {
    this.updateLeftMenuInputs(key, value);
    this.updateTable(key,value);
  }

  addNewTag(tag) {
    this.updateTagsInput('assignedTags', _.uniqBy(_.concat(this.tagsInputs.assignedTags, tag), 'tagName'));
    this.updateTagsInput('toAssign',  _.uniqBy(_.concat(this.tagsInputs.toAssign, tag), 'tagName'));
  }

  toggleTag({i, operation, source}) {
    if(operation == this.tagsInputs['operation']) {
      if(!_.find(this.tagsInputs.selectedTags, tag => tag.tagId == this.tagsInputs[source][i].tagId)) {
        this.updateTagsInput('selectedTags', _.merge({}, this.tagsInputs.selectedTags, { [this.tagsInputs[source][i].tagId]: {...this.tagsInputs[source][i]} }));
      }else {
        this.updateTagsInput('selectedTags', _.omit(this.tagsInputs.selectedTags, this.tagsInputs[source][i].tagId));
      }
    }else {
      this.updateTagsInput('operation', operation);
      this.updateTagsInput('selectedTags', _.merge({}, { [this.tagsInputs[source][i].tagId]: {...this.tagsInputs[source][i]} }));
    }

    if(!_.keys(this.tagsInputs.selectedTags).length) this.updateTagsInput('operation', null);
  }

  confirmSelection() {
    const tags = _.map(this.tagsInputs.selectedTags, t => ({...t, type: 'full'}));
    if(this.tagsInputs.operation == 'assign') {
      this.updateTagsInput('toAssign', _.uniqBy(_.concat(this.tagsInputs.toAssign, tags), 'tagId'))
      this.updateTagsInput('assignedTags', _.uniqBy(_.concat(this.tagsInputs.assignedTags, tags), 'tagId'))
    }

    if(this.tagsInputs.operation == 'de-assign') {
      this.updateTagsInput('toAssign', _.filter(this.tagsInputs.toAssign, tag => !_.find(tags, t => tag.tagId == t.tagId || tag.tagName == t.tagName)));
      this.updateTagsInput('assignedTags', _.filter(this.tagsInputs.assignedTags, tag => !_.find(tags, t => tag.tagId == t.tagId || tag.tagName == t.tagName)));
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
    console.log({
      selectedTags: this.tagsInputs.toAssign,
      unselectedTags: _.differenceBy(this.tagsInputs.assignedTagsCache, _.uniqBy([...this.tagsInputs.assignedTags, ...this.tagsInputs.toAssign], t => t.tagId || t.tagName), 'tagName')
    })
    this.dispatch(new fromWorkspaceStore.AssignPltsToTag({
      userId: 1,
      wsId: this.workspaceId,
      uwYear: this.uwy,
      plts: _.map(this.getTableInputKey('selectedListOfPlts'), plt => plt.pltId),
      selectedTags: this.tagsInputs.toAssign,
      unselectedTags: _.differenceBy(this.tagsInputs.assignedTagsCache, _.uniqBy([...this.tagsInputs.assignedTags, ...this.tagsInputs.toAssign], t => t.tagId || t.tagName), 'tagName')
    }));
    this.tagsInputs = {
      ...this.tagsInputs,
      _tagModalVisible: false,
      assignedTags: [],
      assignedTagsCache: [],
      usedInWs: [],
      allTags: [],
      suggested: [],
      toAssign: [],
      toRemove: [],
      selectedTags: {},
      operation: null
    };
  }

  collapseLeftMenu() {
    this.collapsedTags= !this.collapsedTags;
    console.log(this.leftMenu);
    this.detectChanges();
  }

  resizing() {
    this.detectChanges();
  }

  tagsActionDispatcher(action: Message) {

    switch (action.type) {
      case tagsStore.setTagModalVisibility:
        this.setTagModal(action.payload);
        break;
      case tagsStore.addNewTag:
        this.addNewTag(action.payload)
        break;
      case tagsStore.toggleTag:
        this.toggleTag(action.payload);
        break;
      case tagsStore.confirmSelection:
        this.confirmSelection();
        break;
      case tagsStore.clearSelection:
        this.clearSelection();
        break;
      case tagsStore.save:
        this.save();
        break;
    }
  }

  onTableContainerResize($event: ResizedEvent) {
    console.log($event)
    this.updateTable('scrollConfig', {
      containerHeight: $event.newHeight,
      containerGap: '49px',
    })
  }
}
