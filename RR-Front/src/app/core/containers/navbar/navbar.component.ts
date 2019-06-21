import {Component, OnInit} from '@angular/core';
import {fromEvent} from 'rxjs';
import {debounceTime} from 'rxjs/operators';
import {Select, Store} from '@ngxs/store';
import {SearchNavBarState} from "../../store/states";

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

  constructor(private store: Store) {
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
    window.open('http://dcvprdwiki/rrwiki/index.php/Main_Page', '_blank');
  }


}
