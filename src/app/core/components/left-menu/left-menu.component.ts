import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-left-menu',
  templateUrl: './left-menu.component.html',
  styleUrls: ['./left-menu.component.scss']
})
export class LeftMenuComponent implements OnInit {
  isCollapsed = false;
  constructor() { }
  toggleCollapsed(): void {
    this.isCollapsed = !this.isCollapsed;
  }
  ngOnInit() {
  }

}
