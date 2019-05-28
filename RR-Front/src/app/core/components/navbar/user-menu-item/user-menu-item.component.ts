import { Component, OnInit } from '@angular/core';
import {SearchService} from '../../../service/search.service';

@Component({
  selector: 'user-menu-item',
  templateUrl: './user-menu-item.component.html',
  styleUrls: ['./user-menu-item.component.scss']
})
export class UserMenuItemComponent implements OnInit {
  visible: boolean;
  defaultImport;

  constructor( private _searchService: SearchService) {}

  ngOnInit() {
    this.defaultImport = localStorage.getItem('importConfig');
    this._searchService.infodropdown.subscribe( dt => this.visible = this._searchService.getvisibleDropdown());
  }


  isVisible = false;
  isOkLoading = false;


  handleOk(): void {
    localStorage.setItem('importConfig', this.defaultImport);
    this.isVisible = false;
  }

  handleCancel(): void {
    this.isVisible = false;
  }

}
