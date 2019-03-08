import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {fromEvent} from "rxjs";
import {debounceTime} from "rxjs/operators";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  expandMenu = false;
  @Output('expandLeftMenu') expandLeftMenu:EventEmitter<boolean> = new EventEmitter<boolean>();
  leftNavbarCollapsed:boolean = false;

  formatter = (_)=> ""

  selectedWorkspace: any;

  constructor() { }

  collapseLeftNavbar(){
    this.leftNavbarCollapsed = !this.leftNavbarCollapsed;
    this.expandLeftMenu.emit(this.leftNavbarCollapsed)
  }

  ngOnInit() {
    fromEvent(window,'resize')
      .pipe(debounceTime(200))
      .subscribe(({target:{innerWidth}}:any)=>{

        this.expandMenu = this.expandMenu ? !(innerWidth > 768) : this.expandMenu
    })
  }

}
