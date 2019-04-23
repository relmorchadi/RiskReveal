import { Component, OnInit } from '@angular/core';
import {SearchService} from '../../../service/search.service';

@Component({
  selector: 'user-menu-item',
  templateUrl: './user-menu-item.component.html',
  styleUrls: ['./user-menu-item.component.scss']
})
export class UserMenuItemComponent implements OnInit {
  visible: boolean;
  constructor( private _searchService: SearchService) { }

  ngOnInit() {
    this._searchService.infodropdown.subscribe( dt => this.visible = this._searchService.getvisibleDropdown());
  }

}
