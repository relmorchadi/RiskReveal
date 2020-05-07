import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {fromEvent} from 'rxjs';
import {debounceTime, take} from 'rxjs/operators';
import {Select, Store} from '@ngxs/store';
import {AuthState, SearchNavBarState} from '../../store/states';
import {HelperService} from "../../../shared/helper.service";
import {UpdateWsRouting} from "../../../workspace/store/actions";
import {Navigate} from "@ngxs/router-plugin";
import {WorkspaceState} from "../../../workspace/store/states";
import {NotificationService} from "../../../shared/notification.service";
import {Router} from "@angular/router";
import {BaseContainer} from "../../../shared/base";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent extends BaseContainer implements OnInit {
  expandMenu = false;
  formatter = (_) => '';
  defaultImport;

  @Select(SearchNavBarState) searchState$;
  @Select(WorkspaceState.getLastWorkspace) lastWorkspace$;
  componentName: string = 'global-nav-bar';

  constructor(_baseStore: Store,
              _baseRouter: Router,
              _baseCdr: ChangeDetectorRef,
              private router: Router,
              private notificationService: NotificationService) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  collapseLeftNavbar() {
    // @TODO
  }

  ngOnInit() {
    fromEvent(window, 'resize')
      .pipe(debounceTime(200))
      .subscribe(({target: {innerWidth}}: any) => {
        this.expandMenu = this.expandMenu ? !(innerWidth > 768) : this.expandMenu;
      });
  }

  wiki() {
    this.closeOpenedPopins();
    window.open('http://dcvprdwiki/rrwiki/index.php/Main_Page', '_blank');
  }

  closeOpenedPopins() {
    HelperService.headerBarPopinChange$.next({from: this.componentName});
  }

  redirectWorkspace() {
    this.lastWorkspace$
      .pipe(take(1))
      .subscribe(data => {
        if (data) {
          const {wsId, uwYear, route} = data;
          return this.dispatch(
            [new UpdateWsRouting(wsId.concat('-', uwYear), route),
              new Navigate(route ? [`workspace/${wsId}/${uwYear}/${route}`] : [`workspace/${wsId}/${uwYear}/projects`])]
          );
        } else {
          return this.notificationService.createNotification('Information',
            'You have no opened workspaces to display.',
            'error', 'bottomRight', 4000);
        }
      });
  }

  navigateToJOBManager() {
    this.router.navigateByUrl(`/jobManager`);
  }

}
