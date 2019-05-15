import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {fromEvent} from 'rxjs';
import {debounceTime} from 'rxjs/operators';
import {HelperService} from '../../../shared/helper.service';
import {Store} from '@ngxs/store';
import {PatchSearchStateAction} from '../../store/actions';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  expandMenu = false;
  formatter = (_) => '';

  constructor(private _helper: HelperService, private store: Store) { }

  collapseLeftNavbar() {
    this._helper.collapseLeftMenu$.next();
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
