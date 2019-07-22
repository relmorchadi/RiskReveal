import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { Router } from '@angular/router';
import * as _ from 'lodash';
import {UpdateWsRouting} from "../../../workspace/store/actions";
import {Navigate} from "@ngxs/router-plugin";

@Component({
  selector: 'app-hyper-links',
  templateUrl: './hyper-links.component.html',
  styleUrls: ['./hyper-links.component.scss']
})
export class HyperLinksComponent implements OnInit {

  @Input('links') links: string[];
  @Input('activeItem') activeItem: string;
  @Input('routes') routes: any;

  @Output('navigate') navigate:EventEmitter<any>;

  constructor() {
    this.navigate= new EventEmitter();
  }

  ngOnInit() {
  }

  onNavigate(to) {
    if (this.activeItem != to) {
      this.navigate.emit({route: this.routes[to]});
      // this._router.navigate([`workspace/${this.RoutingConfig.wsId}/${this.RoutingConfig.uwYear}/${this.routes[to]}`]);
    }
  }

  filter() {
    return _.filter(this.links, e => e != this.activeItem);
  }

}
