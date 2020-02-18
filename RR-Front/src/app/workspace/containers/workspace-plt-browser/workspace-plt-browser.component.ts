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
import {Actions as rightMenuActions} from '../../../shared/components/plt/plt-right-menu/store/actionTypes'
import {PreviousNavigationService} from '../../services/previous-navigation.service';
import {BaseContainer} from '../../../shared/base';
import {SystemTagsService} from '../../../shared/services/system-tags.service';
import {StateSubscriber} from '../../model/state-subscriber';
import {ExcelService} from '../../../shared/services/excel.service';
import produce from "immer";
import {TableHandlerImp} from "../../../shared/implementations/table-handler.imp";
import {TableServiceImp} from "../../../shared/implementations/table-service.imp";

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss'],
  providers: [ TableHandlerImp, TableServiceImp ]
})
export class WorkspacePltBrowserComponent extends BaseContainer implements OnInit, OnDestroy, StateSubscriber {

  rightMenuInputs: rightMenuStore.Input;
  leftMenuInputs: leftMenuStore.Input;
  collapsedTags: boolean= true;
  @ViewChild('leftMenu') leftMenu: any;

  isColManagerVisible: boolean;

  private dropdown: NzDropdownContextComponent;
  searchAddress: string;
  workspaceId: string;
  uwy: number;
  params: any;
  lastSelectedId;
  managePopUp: boolean;
  size = 'large';
  drawerIndex: any;

  availableColumns: any[];
  visibleColumns: any[];

  constructor(
    private nzDropdownService: NzDropdownService,
    private zone: NgZone,
    private route$: ActivatedRoute,
    private prn: PreviousNavigationService,
    private systemTagService: SystemTagsService,
    private excel: ExcelService,
    _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef,
    private actions$: Actions,
    private handler: TableHandlerImp

  ) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.lastSelectedId = null;
    this.drawerIndex = 0;
    this.addTagModalPlaceholder = 'Select a Tag';
    this.managePopUp = false;
    this.rightMenuInputs = {
      basket: [],
      pltHeaderId: '',
      selectedTab: {
        index: 0,
        title: 'pltDetail',
      },
      tabs: [{title: "Summary"}, {title: "EP Metrics"}],
      visible: false,
      mode: "default"
    };
    this.leftMenuInputs= {
      wsId: this.workspaceId,
      uwYear: this.uwy,
      projects: [],
      showDeleted: false,
      filterData: {},
      filters: { systemTag: {}, userTag: []},
      deletedPltsLength: 0,
      userTags: [],
      selectedListOfPlts: [],
      systemTagsCount: {},
      wsHeaderSelected: true,
      pathTab: true,
      isTagsTab: false
    };

    this.setRightMenuSelectedTab('pltDetail');
    this.isColManagerVisible= false;
  }

  observeRouteParams() {
    return this.route$.params.pipe(
        tap(({wsId, year}) => {
          this.workspaceId = wsId;
          this.uwy = year;
          this.params = {
            workspaceContextCode: wsId,
            workspaceUwYear: year
          };
          this.updateLeftMenuInputs('wsId', this.workspaceId);
          this.updateLeftMenuInputs('uwYear', this.uwy);
          this.updateMenuKey('wsIdentifier', this.workspaceId+"-"+this.uwy);
        })
    )
  }

  observeRouteParamsWithSelector(operator) {
    return this.observeRouteParams()
      .pipe(
        switchMap(() => operator()),
        this.unsubscribeOnDestroy
      )
  }

  getProjects() {
    return this.select(WorkspaceState.getProjectsPlt(this.workspaceId + '-' + this.uwy));
  }

  getOpenedPlt() {
    return this.select(WorkspaceState.getOpenedPlt(this.workspaceId + '-' + this.uwy));
  }

  ngOnInit() {

    this.handler.visibleColumns$.subscribe((cols) => {
      this.visibleColumns = cols;
      this.detectChanges();
    });

    this.handler.availableColumns$.subscribe((cols) => {
      this.availableColumns = cols;
      this.detectChanges();
    });

    this.observeRouteParams().pipe(
      this.unsubscribeOnDestroy
    ).subscribe(() => {
      /*this.dispatch(new fromWorkspaceStore.loadAllPlts({
        params: {
          workspaceId: this.workspaceId, uwy: this.uwy
        },
        wsIdentifier: this.workspaceId + '-' + this.uwy
      }));*/
    });

    this.observeRouteParamsWithSelector(() => this.getProjects()).subscribe((projects: any) => {
      this.updateLeftMenuInputs('projects', _.map(projects, p => ({...p, selected: false})));
      this.detectChanges();
    });

    this.observeRouteParamsWithSelector(() => this.getOpenedPlt()).subscribe(openedPlt => {
      this.updateMenuKey('visible', openedPlt && !openedPlt.pltId ? false : this.getRightMenuKey('visible'));
      this.detectChanges();
    });

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

  selectedPlt: any;
  tagForMenu: any;
  addTagModalPlaceholder: any;
  showDeleted: boolean;
  selectedItemForMenu: string;
  leftIsHidden: any;

  resetPath() {
    this.updateLeftMenuInputs('projects', _.map(this.leftMenuInputs.projects, p => ({...p, selected: false})));
    this.updateTableAndLeftMenuInputs('showDeleted', false);
  }

  setSelectedProjects($event) {
    this.updateLeftMenuInputs('projects', $event);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
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

  setWsHeaderSelect($event: any) {
    this.updateLeftMenuInputs('wsHeaderSelected', $event);
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
        break;

      case rightMenuStore.setSelectedTabByIndex:
        this.updateMenuKey('selectedTab', action.payload);
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

  patchState({data: {leftNavbarCollapsed}}): void {
    this.leftIsHidden = leftNavbarCollapsed;
    this.detectChanges();
  }

  protected detectChanges() {
    super.detectChanges();
  }

  toggleColumnsManager() {
    this.isColManagerVisible= !this.isColManagerVisible;
  }

  resetColumns() {
    this.managePopUp = false;
  }

  leftMenuActionDispatcher(action: Message) {

    switch (action.type) {

      case leftMenuStore.emitFilters:
        this.emitFilters(action.payload);
        break;
      case leftMenuStore.resetPath:
        this.resetPath();
        break;
      case leftMenuStore.headerSelection:
        this.setWsHeaderSelect(action.payload);
        break;
      case leftMenuStore.onSelectProjects:
        this.setSelectedProjects(action.payload);
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

  updateLeftMenuInputs(key, value) {
    this.leftMenuInputs= {...this.leftMenuInputs, [key]: value};
  }

  updateTableAndLeftMenuInputs(key, value) {
    this.updateLeftMenuInputs(key, value);
  }

  collapseLeftMenu() {
    this.collapsedTags= !this.collapsedTags;
    console.log(this.leftMenu);
    this.detectChanges();
  }

  resizing() {
    this.detectChanges();
  }
}
