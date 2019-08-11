import { Component, OnInit } from '@angular/core';
import {BaseNodeComponent} from "jsplumbtoolkit-angular";

@Component({
  selector: 'app-simple-node',
  templateUrl: './simple-node.component.html',
  styleUrls: ['./simple-node.component.scss']
})
export class SimpleNodeComponent extends BaseNodeComponent  implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
