import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output
} from '@angular/core';
import * as tableStore from "../../../../../shared/components/plt/plt-main-table/store";
import {Actions as tableActions} from "../../../../../shared/components/plt/plt-main-table/store";
import {Message} from "../../../../../shared/message";
import * as _ from "lodash";
import * as fromWorkspaceStore from "../../../../store";
import {WorkspaceState} from "../../../../store";
import {BaseContainer} from "../../../../../shared/base";
import {Select, Store} from "@ngxs/store";
import {ActivatedRoute, Router} from "@angular/router";
import {mergeMap, switchMap, tap} from "rxjs/operators";
import {SystemTagsService} from "../../../../../shared/services/system-tags.service";
import {trestySections} from '../../../../containers/workspace-scope-completence/data';
import {of} from "rxjs";
import * as leftMenuStore from "../../../../../shared/components/plt/plt-left-menu/store";

@Component({
  selector: 'app-attach-plt-pop-up',
  templateUrl: './attach-plt-pop-up.component.html',
  styleUrls: ['./attach-plt-pop-up.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AttachPltPopUpComponent extends BaseContainer implements OnInit, OnDestroy {

  tableInputs: tableStore.Input;
  tagsInput: leftMenuStore.Input;
  workspaceId: string;
  uwy: number;
  userTags: any;
  projects: any[];
  selectedItemForMenu: string;
  selectedPlt: any;
  attachPltsContainer = [];
  tagForMenu: any;
  fromPlts: any;
  editingTag: boolean;
  tagModalIndex: any;
  tagModalVisible: boolean;
  systemTags: any;
  systemTagsCount: any;
  modalSelect: any;
  wsHeaderSelected: boolean;
  oldSelectedTags: any;
  treatySections: any;
  pltContainer = [];
  pltContainerDelete = [];
  pltContainerTwo = [];
  state: any;

  @Output('attachArray') attachArray: EventEmitter<any> = new EventEmitter();
  @Output('deleteArray') deleteArray: EventEmitter<any> = new EventEmitter();

  @Select(WorkspaceState.getScopeCompletenessData) state$;


  @Input() isVisible;
  @Input('rowData') rowData: any;

  @Output('onVisibleChange') onVisibleChange: EventEmitter<any> = new EventEmitter();

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
  contextMenuItemsCache = null;

  constructor(private route$: ActivatedRoute,
              private systemTagService: SystemTagsService,
              _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef
  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.tagModalVisible = false;
    this.tagModalIndex = 0;
    this.treatySections = _.toArray(trestySections)
    this.systemTagsCount = {};
    this.fromPlts = false;
    this.editingTag = false;
    this.wsHeaderSelected = true;
    this.tagForMenu = {
      tagId: null,
      tagName: '',
      tagColor: '#0700e4'
    };
    this.tableInputs = {
      scrollHeight: 'calc(100vh - 480px)',
      dataKey: "pltId",
      openedPlt: "",
      contextMenuItems: null,
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
          width: '45px',
          icon: null,
          type: 'checkbox-scope',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: 'User Tags',
          sorted: false,
          filtred: false,
          resizable: false,
          width: '75px',
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
          width: '60',
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
          width: '150',
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
          width: '40',
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
          width: '70',
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
          width: '100',
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
          width: '100',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: '17T008583/ 1',
          header: '17T008583/ 1',
          subHeader: '1st Cat XL',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        },
        {
          sortDir: 1,
          fields: '17T010540 / 1',
          header: '17T010540 / 1',
          subHeader: '2nd Cat XL',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        },
        {
          sortDir: 1,
          fields: '20T002794 / 1',
          header: '20T002794 / 1',
          subHeader: 'Property/Engineering CAT 1st XL (All Perils)',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        }

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
    this.tagsInput = {
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
  }

  manageTags() {
    this.tagModalVisible = true;
    this.tagForMenu = {
      ...this.tagForMenu,
      tagName: ''
    };
    this.fromPlts = true;
    this.editingTag = false;
    let d = _.map(this.getTableInputKey('selectedListOfPlts').length > 0 ? this.getTableInputKey('selectedListOfPlts') : [this.selectedItemForMenu], k => _.find(this.getTableInputKey('listOfPltsData'), e => e.pltId == (this.getTableInputKey('selectedListOfPlts').length > 0 ? k.pltId : k)).userTags);
    this.modalSelect = _.intersectionBy(...d, 'tagId');
    this.oldSelectedTags = _.uniqBy(_.flatten(d), 'tagId');
  }

  ngOnInit() {
  }

  onHide() {
    this.tableInputs = {
      scrollHeight: 'calc(100vh - 480px)',
      dataKey: "pltId",
      openedPlt: "",
      contextMenuItems: null,
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
          width: '45px',
          icon: null,
          type: 'checkbox-scope',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: 'User Tags',
          sorted: false,
          filtred: false,
          resizable: false,
          width: '75px',
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
          width: '60',
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
          width: '150',
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
          width: '40',
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
          width: '70',
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
          width: '100',
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
          width: '100',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: '17T008583/ 1',
          header: '17T008583/ 1',
          subHeader: '1st Cat XL',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        },
        {
          sortDir: 1,
          fields: '17T010540 / 1',
          header: '17T010540 / 1',
          subHeader: '2nd Cat XL',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        },
        {
          sortDir: 1,
          fields: '20T002794 / 1',
          header: '20T002794 / 1',
          subHeader: 'Property/Engineering CAT 1st XL (All Perils)',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        }

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
    this.onVisibleChange.emit(false);
    this.rowData = null;
    this.ngOnDestroy();
  }

  ngOnDestroy() {
    this.destroy();
  }

  observeRouteParams() {
    return this.route$.params.pipe(tap(({wsId, year}) => {
      this.workspaceId = wsId;
      this.uwy = year;
    }))
  }


  onShow() {
    this.pltContainerTwo = [];
    this.pltContainer = [];
    this.tableInputs = {
      scrollHeight: 'calc(100vh - 480px)',
      dataKey: "pltId",
      openedPlt: "",
      contextMenuItems: null,
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
          width: '45px',
          icon: null,
          type: 'checkbox-scope',
          active: true
        },
        {
          sortDir: 1,
          fields: '',
          header: 'User Tags',
          sorted: false,
          filtred: false,
          resizable: false,
          width: '75px',
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
          width: '60',
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
          width: '150',
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
          width: '40',
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
          width: '70',
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
          width: '100',
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
          width: '100',
          icon: null,
          type: 'field',
          active: true
        },
        {
          sortDir: 1,
          fields: '17T008583/ 1',
          header: '17T008583/ 1',
          subHeader: '1st Cat XL',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        },
        {
          sortDir: 1,
          fields: '17T010540 / 1',
          header: '17T010540 / 1',
          subHeader: '2nd Cat XL',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        },
        {
          sortDir: 1,
          fields: '20T002794 / 1',
          header: '20T002794 / 1',
          subHeader: 'Property/Engineering CAT 1st XL (All Perils)',
          sorted: true,
          filtred: false,
          resizable: true,
          width: '50',
          icon: null,
          type: 'checkbox-col',
          active: true
        }

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
    this.attachPltsContainer = [];


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
      this.systemTagsCount = this.systemTagService.countSystemTags(data);
      this.updateTable('listOfPltsCache', data);
      this.updateTable('listOfPltsData', [...this.getTableInputKey('listOfPltsCache')]);
      this.updateTable('selectedListOfPlts', _.filter(data, (v, k) => v.selected));

      this.initialiseContainer(this.tableInputs);

      this.updatePltsSelection();

      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getUserTags()).subscribe(userTags => {
      this.userTags = userTags;
      this.detectChanges();
    })

    this.observeRouteParamsWithSelector(() => this.getProjects()).subscribe((projects: any) => {
      this.projects = _.map(projects, p => ({...p, selected: false}));
      this.detectChanges();
    });
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
        this.updateTable('listOfPltsData', action.payload);
        break;

      case tableStore.checkBoxSortScope:
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

      case tableStore.attachPlt:
        this.handleContainer(action.payload);
        break;

      case tableStore.onCheckAllScope:
        this.selectAllPltsContainer(action.payload);
        break;


      case tableStore.attachToAllTs:
        this.handleContainerTwo(action.payload);
        break;

      case tableStore.attachToJustThisTs:
        this.handleContainerThree(action.payload);
        break;

      case tableStore.deselectPlt:
        this.deselectThePlt(action.payload);
        break;

      case tableStore.toggleSelectedPlts:
        this.toggleSelectPlts(action.payload);
        break;

      case tableStore.filterByStatus:
        const status = this.getTableInputKey('status');

        this.updateTable('status', {
          ...status,
          [action.payload]: {
            selected: !status[action.payload].selected
          }
        });
        this.dispatch(new fromWorkspaceStore.FilterPltsByStatus({
          wsIdentifier: this.workspaceId + "-" + this.uwy,
          status: this.getTableInputKey('status')
        }));
        break;
      default:
    }
  }

  updateTable(key: string, value: any) {
    this.tableInputs = tableActions.updateKey.handler(this.tableInputs, key, value);
  }

  observeRouteParamsWithSelector(operator) {
    return this.observeRouteParams()
      .pipe(
        switchMap(() => operator()),
        this.unsubscribeOnDestroy
      )
  }

  getPlts() {
    if (this.rowData == null) {
      return this.state$
        .pipe(
          this.unsubscribeOnDestroy,
          mergeMap(plts => of(_.map(plts, (plt: any) => {
              return {
                ..._.pick(plt, ['userTags', 'pltName', 'peril', 'regionPerilCode', 'regionPerilName', 'grain']),
                pltId: plt.pltId,
                visible: true,
                selected: false,
                treatySectionsState: this.getTreatyStateForPlts(plt)
              }
            }
          )))
        )
    } else {
      return this.state$
        .pipe(
          this.unsubscribeOnDestroy,
          mergeMap(plts => of(_.map(_.filter(plts, (pt: any) => {
              if (this.rowData.sort == "1") {
                if (pt.regionPerilCode == this.rowData.rowData) {
                  if (_.includes(this.rowData.child, pt.grain)) {
                    return true
                  } else {
                    return false;
                  }
                } else {
                  return false;
                }
              }
              if (this.rowData.sort == "2") {
                if (pt.grain == this.rowData.rowData) {
                  if (_.includes(this.rowData.child, pt.regionPerilCode)) {
                    return true
                  } else {
                    return false;
                  }
                } else {
                  return false;
                }

              }
            }), (plt: any) => {
              return {
                ..._.pick(plt, ['userTags', 'pltName', 'peril', 'regionPerilCode', 'regionPerilName', 'grain']),
                pltId: plt.pltId,
                visible: true,
                selected: false,
                treatySectionsState: this.getTreatyStateForPlts(plt)
              }
            }
          )))
        )
    }
  }

  initialiseContainer(tableInputs) {

    tableInputs.listOfPltsData.forEach(plt => {
      plt.treatySectionsState.forEach(tsState => {
        if (tsState.state == 'attached') {
          this.pltContainer.push({
            pltId: plt.pltId,
            regionPeril: plt.regionPerilCode,
            targetRap: plt.grain,
            tsId: tsState.tsId
          })
        }
      })
    })
    this.pltContainerTwo = _.merge([], this.pltContainer);
    console.log("pltContainerTwo", this.pltContainerTwo);

  }

  getTreatyStateForPlts(plt) {
    let treatySectionsField = [];
    this.treatySections[0].forEach(treatySection => {
      const Rp = treatySection.regionPerils.map(item => item.id);
      const exist = _.includes(Rp, plt.regionPerilCode);
      if (exist) {
        const index = _.findIndex(treatySection.regionPerils, (res: any) => res.id == plt.regionPerilCode)
        const Tr = treatySection.regionPerils[index].targetRaps.map(item => item.id);
        const exist2 = _.includes(Tr, plt.grain);
        if (exist2) {
          const index2 = _.findIndex(treatySection.regionPerils[index].targetRaps, (res: any) => res.id == plt.grain)
          const item = treatySection.regionPerils[index].targetRaps[index2];
          if (item.overridden) {
            treatySectionsField.push({'tsId': treatySection.id, 'state': 'disabled'})
          } else {
            if (item.attached) {
              if (_.includes(_.map(item.pltsAttached, pltss => pltss.pltId), plt.pltId)) {
                treatySectionsField.push({'tsId': treatySection.id, 'state': 'attached'})
              } else {
                treatySectionsField.push({'tsId': treatySection.id, 'state': 'expected'})
              }
            } else {
              treatySectionsField.push({'tsId': treatySection.id, 'state': 'expected'})
            }
          }

        } else {
          treatySectionsField.push({'tsId': treatySection.id, 'state': 'disabled'})
        }
      } else {
        treatySectionsField.push({'tsId': treatySection.id, 'state': 'disabled'})
      }
    });
    return treatySectionsField;
  }

  getTableInputKey(key) {
    return _.get(this.tableInputs, key);
  }

  getUserTags() {
    return this.select(WorkspaceState.getUserTagsPlt(this.workspaceId + '-' + this.uwy));
  }

  getProjects() {
    return this.select(WorkspaceState.getProjectsPlt(this.workspaceId + '-' + this.uwy));
  }

  handleContainer(content) {
    if (_.findIndex(this.pltContainer, content) == -1) {
      this.pltContainer.push(content);
    } else {
      this.pltContainer.splice(_.findIndex(this.pltContainer, content), 1);
    }

    this.updatePltSingleSelection(content.pltId);

    this.detectChanges();
  }

  handleContainerTwo(content) {
    this.tableInputs.listOfPltsData.forEach(plt => {
      if (plt.pltId == content.pltId) {
        plt.treatySectionsState.forEach(state => {
          if (state.state != 'disabled') {
            content = {...content, tsId: state.tsId};
            if (_.findIndex(this.pltContainer, content) == -1) {
              this.pltContainer.push(content);
            }
          }

        })
      }
    })
    this.updatePltSingleSelection(content.pltId);

    this.detectChanges();
  }

  selectAllPltsContainer(event) {
    if(event){
    this.pltContainer = [];
    this.tableInputs.listOfPltsData.forEach(plt => {
      plt.treatySectionsState.forEach(ts => {
        if (ts.state != 'disabled') {
          let object = {
            pltId: plt.pltId,
            regionPeril: plt.regionPerilCode,
            targetRap: plt.grain,
            tsId: ts.tsId
          }
          this.pltContainer.push(object)
          this.updatePltSingleSelection(object.pltId);
        }
      })
    })}else{
      this.pltContainer = [];
      this.tableInputs.listOfPltsData.forEach(plt => {
        this.updatePltSingleSelection(plt.pltId);
      })
    }
    this.detectChanges();
  }


  handleContainerThree(content) {
    _.remove(this.pltContainer, plt => plt.pltId == content.pltId);


    this.pltContainer.push(content);

    this.updatePltSingleSelection(content.pltId);

    this.detectChanges();
  }

  updatePltsSelection() {
    this.updateTable("listOfPltsData", _.map(this.getTableInputKey('listOfPltsData'), plt => ({
      ...plt,
      selected: this.checkSelection(plt.pltId)
    })))
  }

  updatePltSingleSelection(pltId) {
    let index = _.findIndex(this.getTableInputKey('listOfPltsData'), {pltId: pltId});

    this.updateTable("listOfPltsData", _.merge([], this.getTableInputKey('listOfPltsData'), {[index]: {selected: this.checkSelection(pltId)}}))

  }


  checkSelection(pltId) {
    return _.filter(this.pltContainer, item => item.pltId == pltId).length > 0;
  }

  deselectThePlt(pltId) {
    let index = _.findIndex(this.getTableInputKey('listOfPltsData'), {pltId: pltId});
    this.updateTable("listOfPltsData", _.merge([], this.getTableInputKey('listOfPltsData'), {[index]: {selected: false}}))
    this.pltContainer = _.filter(this.pltContainer, plt => plt.pltId != pltId);
  }

  toggleSelectPlts(plts: any) {
    this.dispatch(new fromWorkspaceStore.ToggleSelectPlts({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      plts,
      forDeleted: this.getTableInputKey('showDeleted')
    }));
  }

  /** LeftMenu functions **/

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

  createTag($event: any) {
    this.dispatch(new fromWorkspaceStore.createOrAssignTags({
      ...$event,
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      type: 'createTag'
    }))
  }

  filter(filterData) {
    this.updateTable('filterData', filterData);
  }

  editTag() {
    this.dispatch(new fromWorkspaceStore.editTag({
      tag: this.tagForMenu,
      workspaceId: this.workspaceId,
      uwy: this.uwy
    }))
  }

  resetPath() {
    this.updateTable('filterData', _.omit(this.getTableInputKey('filterData'), 'project'));
    this.projects = _.map(this.projects, p => ({...p, selected: false}));
    this.updateTable('showDeleted', false);
  }

  setSelectedProjects($event) {
    this.projects = $event;
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


  setFilters(filters) {
    this.updateTable('filters', filters);
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

  setTagModal($event: any) {
    this.tagModalVisible = $event;
  }

  setTagForMenu($event: any) {
    this.tagForMenu = $event;
  }

  setRenameTag($event: any) {
    this.editingTag = $event;
  }

  emitFilters(filters: any) {
    this.dispatch(new fromWorkspaceStore.setUserTagsFilters({
      wsIdentifier: this.workspaceId + '-' + this.uwy,
      filters: filters
    }))
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


  getDifference(containerTwo, container) {
    let deleteArray = [];
    containerTwo.forEach(plt => {
      if (_.findIndex(container, plt) != -1) {
        container.splice(_.findIndex(container, plt), 1);
      } else {
        deleteArray.push(plt);
      }

    })
    return deleteArray;
  }

  dispatchAttachTable() {
    this.pltContainerDelete = this.getDifference(this.pltContainerTwo, this.pltContainer);
    this.attachArray.emit(this.pltContainer);
    this.deleteArray.emit(this.pltContainerDelete);
    this.onHide();
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
      /*case leftMenuStore.toggleDeletedPlts:
        this.toggleDeletePlts(action.payload);
        break;*/
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
    this.tagsInput = {...this.tagsInput, [key]: value};
  }

  updateTableAndTagsInputs(key, value) {
    this.updateTagsInput(key, value);
    this.updateTable(key, value);
  }

  addNewTag(tag) {
    /*this.updateTagsInput('toAssign', _.concat(this.tagsInput.toAssign, tag));
    this.updateTagsInput('assignedTags', _.concat(this.tagsInput.assignedTags, tag));*/
    this.updateTagsInput('assignedTags', _.concat(this.tagsInput.assignedTags, tag));
    this.updateTagsInput('toAssign', _.concat(this.tagsInput.toAssign, tag));
  }

  toggleTag({i, operation, source}) {
    if (operation == this.tagsInput['operation']) {
      if (!_.find(this.tagsInput.selectedTags, tag => tag.tagId == this.tagsInput[source][i].tagId)) {
        this.updateTagsInput('selectedTags', _.merge({}, this.tagsInput.selectedTags, {[this.tagsInput[source][i].tagId]: {...this.tagsInput[source][i]}}));
      } else {
        this.updateTagsInput('selectedTags', _.omit(this.tagsInput.selectedTags, this.tagsInput[source][i].tagId));
      }
    } else {
      this.updateTagsInput('operation', operation);
      this.updateTagsInput('selectedTags', _.merge({}, {[this.tagsInput[source][i].tagId]: {...this.tagsInput[source][i]}}));
    }

    if (!_.keys(this.tagsInput.selectedTags).length) this.updateTagsInput('operation', null);
  }

  confirmSelection() {
    const tags = _.map(this.tagsInput.selectedTags, t => ({...t, type: 'full'}));
    if (this.tagsInput.operation == 'assign') {
      this.updateTagsInput('toAssign', _.uniqBy(_.concat(this.tagsInput.toAssign, tags), 'tagId'))
      this.updateTagsInput('assignedTags', _.uniqBy(_.concat(this.tagsInput.assignedTags, tags), 'tagId'))
    }

    if (this.tagsInput.operation == 'de-assign') {
      this.updateTagsInput('toAssign', _.filter(this.tagsInput.toAssign, tag => !_.find(tags, t => tag.tagId == t.tagId)));
      this.updateTagsInput('assignedTags', _.filter(this.tagsInput.assignedTags, tag => !_.find(tags, t => tag.tagId == t.tagId)));
    }

    this.clearSelection();
  }

  checkTagType(tag) {
    return _.every(this.getTableInputKey('selectedListOfPlts'), (plt) => _.some(plt.userTags, t => t.tagId == tag.tagId)) ? 'full' : 'half';
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
