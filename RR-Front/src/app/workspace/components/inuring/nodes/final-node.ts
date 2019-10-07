import {Component, Input, OnInit} from '@angular/core';
import {BaseNodeComponent} from "jsplumbtoolkit-angular";

@Component({
  selector: 'final-node',
  template: `
    <div class="flowchart-object flowchart-start input-node-card">
      <div class="node-header d-flex justify-content-between align-items-center">
        <div>Final Node</div>
        <i class="icon-check"></i>
      </div>
      <div class="node-content">
        Final Node
      </div>
      <div class="node-footer d-flex justify-content-between align-items-center">
        <i class="icon-corner-right-down"></i>
        <nz-dropdown [nzPlacement]="'bottomRight'" [nzTrigger]="'click'">
          <a nz-dropdown>
            <i class="icon-menu_24px"></i>
          </a>
          <ul nz-menu style="font-size: 12px">
            <li nz-menu-item>Clone</li>
            <li nz-menu-item>Delete</li>
            <li nz-menu-item>Unpin to Curve</li>
            <li nz-menu-divider></li>
            <li nz-menu-item>Edit</li>
            <li nz-menu-item>Details</li>
            <li nz-menu-item (click)="addNote()">Add Note</li>
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
      /*width: 15px;*/
      height: 10px;
      /*border-radius: 15px;*/
      background-color: #4468c4;
      /*position: relative;*/
      /*bottom: 5px;*/
    }

    .input-node-card {
      height: 117.5px;
      width: 198px;
      background-color: #F4F6FC;
      border: 1px solid #00C4AA;
    }

    .input-node-card .node-header {
      /*width: 100%;*/
      font-weight: bold;
      margin: 10px 20px;
    }

    .input-node-card .node-content {
      font-weight: bold;
      margin: 0px 10px;
      /* border-radius: 10px; */
      background-color: #E5E9FA;
      color: rgba(0,0,0,0.6);
      font-size: 12px;
      padding: 2px;
      height: 40px;
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
export class FinalNodeComponent extends BaseNodeComponent implements OnInit {

  @Input('number')
  nodeNumber = '1';

  constructor() {
    super();
  }

  ngOnInit() {
  }

  addNote() {
    window['toolkit'].addNode({type: 'noteNode', name: this.getNode().data.name})
  }
}
