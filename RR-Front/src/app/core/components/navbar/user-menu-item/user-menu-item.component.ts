import { Component, OnInit } from '@angular/core';
import {SearchService} from '../../../service/search.service';
import {ActivatedRoute, Router} from "@angular/router";
import {HelperService} from "../../../../shared/helper.service";

@Component({
  selector: 'user-menu-item',
  templateUrl: './user-menu-item.component.html',
  styleUrls: ['./user-menu-item.component.scss']
})
export class UserMenuItemComponent implements OnInit {
  visible: boolean;
  defaultImport;
  isVisible = false;
  isOkLoading = false;

  constructor( private _searchService: SearchService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.defaultImport = localStorage.getItem('importConfig');
    this._searchService.infodropdown.subscribe( dt => this.visible = this._searchService.getvisibleDropdown());
  }

  handleOk(): void {
    localStorage.setItem('importConfig', this.defaultImport);
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  navigateToUserPreference() {
    this.router.navigateByUrl(`/userPreference`);
  }

}
