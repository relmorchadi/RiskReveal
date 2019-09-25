import {Component, Input, OnInit} from '@angular/core';
import {BaseNodeComponent} from "jsplumbtoolkit-angular";

@Component({
  selector: 'contract-node',
  template: `
    <div class="flowchart-object flowchart-start input-node-card">
      <div class="node-header d-flex justify-content-between align-items-center">
        <div>
            {{contractName}}
        </div>
        <div>
          <i class="icon-map-pin-alt"></i>
        </div>
      </div>
      <div class="node-content">
        Undefined <br/>
        L: U AL: null @ 100%<br/>
        EUR
      </div>
      <div class="node-footer d-flex justify-content-between align-items-center">
        <i class="icon-corner-right-down"></i>
        <nz-dropdown [nzPlacement]="'bottomRight'" [nzTrigger]="'click'">
          <a nz-dropdown>
            <i class="icon-menu_24px"></i>
          </a>
          <ul nz-menu style="font-size: 12px">
            <li nz-menu-item>Clone</li>
            <li (click)="deleteNode()" nz-menu-item>Delete</li>
            <li nz-menu-item>Unpin to Curve</li>
            <li nz-menu-divider></li>
            <li nz-menu-item>Edit</li>
            <li nz-menu-item>Details</li>
            <li nz-menu-item>Add Node</li>
          </ul>
        </nz-dropdown>
      </div>
      <div class="outer inner div-style"></div>
      <jtk-target port-type="target"></jtk-target>
      <jtk-source filter=".outer" port-type="source"></jtk-source>
    </div>
  `,
  styles: [`
    .div-style {
      height: 10px;
      background-color: #4468c4;
    }

    .input-node-card {
      height: 117.5px;
      width: 198px;
      background-color: rgba(255,170,6,0.15);
      border: 1px solid #F5A623;
    }

    .input-node-card .node-header {
      /*width: 100%;*/
      font-weight: bold;
      text-align: left;
      margin: 5px 10px;
      /*padding: 10px 20px;*/
    }

    .input-node-card .node-content {
      font-weight: bold;
      margin: 0px 20px;
      background-color: rgba(255,255,255,0.8);
      color: rgba(0,0,0,0.6);
      padding: 2px;
      font-size: 12px;
    }

    .input-node-card .node-footer {
      width: 100%;
      font-weight: bold;
      padding: 5px 20px;
      font-weight: bold;
      color: black;
      padding-bottom: 0px;
    }
  `]
})
export class ContractNodeComponent extends BaseNodeComponent implements OnInit {

  @Input('number')
  contractName= 'XOL_EVT';

  constructor() {
    super();
  }

  ngOnInit() {
  }

  deleteNode(){
    this.removeNode();
  }

}
