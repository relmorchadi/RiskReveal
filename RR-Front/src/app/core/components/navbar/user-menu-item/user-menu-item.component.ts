import { Component, OnInit } from '@angular/core';
import {SearchService} from '../../../service/search.service';
import {ActivatedRoute, Router} from "@angular/router";
import {HelperService} from "../../../../shared/helper.service";
import {Select} from "@ngxs/store";
import {AuthState} from "../../../store/states";

@Component({
  selector: 'user-menu-item',
  templateUrl: './user-menu-item.component.html',
  styleUrls: ['./user-menu-item.component.scss']
})
export class UserMenuItemComponent implements OnInit {

  readonly componentName:string= 'user-pop-in';

  @Select(AuthState.getUser) user$;
  user: string = '';
  userInitials: string = '';

  visible: boolean;
  defaultImport;
  isVisible = false;
  isOkLoading = false;
  searchTarget = 'TREATY';

  constructor( private _searchService: SearchService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.defaultImport = localStorage.getItem('importConfig');
    this._searchService.infodropdown.subscribe( dt => this.visible = this._searchService.getvisibleDropdown());
    HelperService.headerBarPopinChange$.subscribe(({from}) => {
      if (from != this.componentName)
        this.visible = false;
    });
    this.user$.pipe().subscribe(value => {
      this.user = value;
      let initials = value.match(/\b\w/g) || [];
      initials = ((initials.shift() || '') + ' ' + (initials.pop() || '')).toUpperCase();
      this.userInitials = initials;
    });
  }

  navigateToUserPreference() {
    this.visible = false;
    this.router.navigateByUrl(`/userPreference`);
  }

  navigateToBulkImport() {
    this.visible = false;
    this.router.navigateByUrl('/bulkImport');
  }

  changeSearch() {

  }

  togglePopup() {
    HelperService.headerBarPopinChange$.next({from: this.componentName});
  }

}
