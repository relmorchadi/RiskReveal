import {Component, OnInit} from '@angular/core';
import {BaseNodeComponent} from "jsplumbtoolkit-angular";

@Component({
  selector: 'input-node',
  template: `
    <div class="flowchart-object flowchart-start input-node-card" (dblclick)="openPopup()">
      <div class="node-header">
        Input Node {{index}}
      </div>
      <div class="node-content">
        {{plts?.length}} PLTs
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
            <li nz-menu-item (click)="editNode()">Edit</li>
            <li nz-menu-item>Details</li>
            <li nz-menu-item>Add Note</li>
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
      background-color: #F4F6FC;
      border: 1px solid #C38FFF;
    }

    .input-node-card .node-header {
      width: 100%;
      font-weight: bold;
      padding: 10px 20px;
    }

    .input-node-card .node-content {
      font-weight: bold;
      margin: 10px 20px;
      border-radius: 10px;
      background-color: #E5E9FA;
      color: rgba(7, 0, 207, 0.5);
      padding: 2px;
    }

    .input-node-card .node-footer {
      width: 100%;
      font-weight: bold;
      padding: 10px 20px;
      font-weight: bold;
      color: black;
      padding-bottom: 0px;
    }
  `]
})
export class InputNodeComponent extends BaseNodeComponent implements OnInit {

  index;
  plts = [];

  constructor() {
    super();
  }

  ngOnInit() {
    this.plts= this.getNode().data.plts;
    this.index= this.getNode().data.index;
    console.log(this.plts);

    window['toolkit'].bind('nodeUpdated', (data) => {
      if (data.node.data.type === 'inputNode' && data.node.data.index == this.index) {
        this.plts = data.node.data.plts;
      }
    })
  }

  deleteNode(){
    this.removeNode();
  }

  openPopup(){

  }

  editNode() {
    console.log(this);
  }
}
