import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {BaseContainer} from "../../../shared/base";


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.scss']
})
export class LeftMenuComponent extends BaseContainer implements OnInit, OnDestroy {

  @Input('isCollapsed')
  isCollapsed;

  @Output('toggleCollapse')
  toggleCollapseEmitter: EventEmitter<void>;

  @Output('navigate')
  navigationEmitter: EventEmitter<{ route: string }>;


  constructor(private _router: Router) {
    super(_router, null, null);
    this.toggleCollapseEmitter = new EventEmitter();
    this.navigationEmitter = new EventEmitter();
  }

  ngOnInit() {
    // this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  collapse($event) {
    $event.stopPropagation();
    $event.preventDefault();
    this.toggleCollapseEmitter.emit();
  }

  routerNavigate(route) {
    this.navigationEmitter.emit({route});

    // let patchRouting;
    // if (routerLink) {
    //   this._router.navigate([`workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}/${routerLink}`]);
    //   patchRouting = _.merge({}, this.state.openedWs, {routing: routerLink});
    // } else {
    //   this._router.navigate([`workspace/${this.state.openedWs.workSpaceId}/${this.state.openedWs.uwYear}`]);
    //   patchRouting = _.merge({}, this.state.openedWs, {routing: ''});
    // }
    // this._store.dispatch(new SetWsRoutingAction(patchRouting));
    // this._helper.updateWorkspaceItems();
  }

  riskLinkImportNavigation() {
    let userPref = localStorage.getItem('importConfig');
    if (userPref && ['RiskLink', 'FileBasedImport', 'CloneData'].includes(userPref)) {
      this.routerNavigate(userPref);
    }
  }

  ngOnDestroy(): void {
    this.destroy();
  }



}
