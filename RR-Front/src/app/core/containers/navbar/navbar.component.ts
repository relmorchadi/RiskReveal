import {Component, OnInit} from '@angular/core';
import {fromEvent} from 'rxjs';
import {debounceTime} from 'rxjs/operators';
import {Select} from '@ngxs/store';
import {SearchNavBarState} from "../../store/states";
import {HelperService} from "../../../shared/helper.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  expandMenu = false;
  formatter = (_) => '';
  defaultImport;

  @Select(SearchNavBarState)
  searchState$;
  componentName: string = 'global-nav-bar';

  constructor() {
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

}
