import {Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import * as _ from 'lodash'

@Component({
  selector: 'app-hyper-links',
  templateUrl: './hyper-links.component.html',
  styleUrls: ['./hyper-links.component.scss']
})
export class HyperLinksComponent implements OnInit {

  @Input('links') links: string[];
  @Input('activeItem') activeItem: string;
  @Input('RoutingConfig') RoutingConfig: {
    wsId: string,
    uwYear: string
  };
  @Input('routes') routes: any;

  constructor(private _router: Router) { }

  ngOnInit() {
  }

  navigate(to){
    if(this.activeItem != to) {
      this._router.navigate([`workspace/${this.RoutingConfig.wsId}/${this.RoutingConfig.uwYear}/${this.routes[to]}`]);
    }
  }

  filter(){
    return _.filter(this.links, e => e != this.activeItem);
  }

}
