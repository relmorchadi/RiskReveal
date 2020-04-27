import {ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {BaseContainer} from "../../../shared/base";
import {Select, Store} from "@ngxs/store";
import {WorkspaceState} from "../../store/states";


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.scss']
})
export class LeftMenuComponent extends BaseContainer implements OnInit, OnDestroy {

  @Input('isCollapsed')
  isCollapsed;

  @Output('toggleCollapse')
  toggleCollapseEmitter: EventEmitter<boolean>;

  @Output('navigate')
  navigationEmitter: EventEmitter<{ route: string }>;
  wsId: string;
  uwYear: string;

  @Select(WorkspaceState.getCurrentTabStatus) status$;
  status;

  constructor(private _router: Router, private _store: Store, private changeRef: ChangeDetectorRef) {
    super(_router, changeRef, _store);
    this.toggleCollapseEmitter = new EventEmitter();
    this.navigationEmitter = new EventEmitter();
  }

  ngOnInit() {
    this.status$.subscribe(value => this.status = value);
    /*this._store.select(WorkspaceState.getCurrentTab).subscribe( ({wsIdentifier}): any => {
      this.wsId= wsIdentifier.split('-')[0];
      this.uwYear= wsIdentifier.split('-')[1];
      console.log(wsIdentifier);
    })*/
    // this.state$.subscribe(value => this.state = _.merge({}, value));
  }

  collapse(isOutside) {
    event.stopPropagation();
    event.preventDefault();
    this.toggleCollapseEmitter.emit(isOutside || !this.isCollapsed);
  }

  routerNavigate(route) {
    //this.navigationEmitter.emit({route: null})
    this.navigationEmitter.emit({route});
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
