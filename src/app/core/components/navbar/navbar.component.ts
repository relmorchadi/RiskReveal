import { Component, OnInit } from '@angular/core';
import {fromEvent} from "rxjs";
import {debounceTime} from "rxjs/operators";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  expandMenu = false;
  formatter = (_)=> ""
  constructor() { }

  ngOnInit() {
    fromEvent(window,'resize')
      .pipe(debounceTime(200))
      .subscribe(({target:{innerWidth}}:any)=>{

        this.expandMenu = this.expandMenu ? !(innerWidth > 768) : this.expandMenu
    })
  }

}
