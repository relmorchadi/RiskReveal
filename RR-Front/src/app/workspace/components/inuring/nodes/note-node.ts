import {Component, OnInit} from '@angular/core';
import {BaseNodeComponent} from "jsplumbtoolkit-angular";

@Component({
  selector: 'note-node',
  template: `
    <div class="flowchart-object flowchart-start input-node-card">
      <div class="node-header d-flex justify-content-between align-items-center">
        <input class="input-name" (ngModelChange)="changeNoteName()" [(ngModel)]="name">
        <div>
          <i class="icon-trash-alt" (click)="deleteNode()"></i>
        </div>
      </div>
      <div class="node-content">
        <textarea tabindex="-1"></textarea>
      </div>
    </div>
  `,
  styles: [`
    .input-node-card {
      min-height: 100px;
      width: 198px;
      background-color: rgba(248, 231, 28, 0.34);
      border: 1px solid #F5A623;
    }

    .input-name {
      border: none !important;
      background-color: rgba(253, 247, 178, 0.34);
    }

    i.icon-trash-alt {
      font-weight: bold;
      font-size: 16px;
    }

    .input-node-card .node-header {
      /*width: 100%;*/
      font-weight: bold;
      text-align: left;
      margin: 5px 10px;
      /*padding: 10px 20px;*/
    }

    .input-node-card .node-content {
      /*font-weight: bold;*/
      /*margin: 0px 20px;*/
      /*background-color: rgba(255, 255, 255, 0.8);*/
      /*color: rgba(0, 0, 0, 0.6);*/
      padding: 2px;
      font-size: 12px;
    }

    .input-node-card .node-content textarea {
      background-color: transparent;
      color: #000;
      font-size: 12px;
      width: 95%;
      border: none;
      /*max-height: 85px;*/
    }

  `]
})
export class NoteNodeComponent extends BaseNodeComponent implements OnInit {
  index: any;
  id: any;
  name: any;

  constructor() {
    super();
  }

  ngOnInit() {
    console.log('Here i m', this.getNode().data.content);
    this.name = this.getNode().data.name;
    /*  this.index= this.getNode().data.index;
      this.id= this.getNode().data.id;
      this.name= this.getNode().data.name;

      window['toolkit'].bind('nodeUpdated', (data) => {
        if (data.node.data.type === 'noteNode' && data.node.data.index == this.index) {
          this.name = data.node.data.name;
        }
      })*/
  }

  deleteNode(){
    this.removeNode();
  }

  changeNoteName() {
    window['toolkit'].updateNode(this.getNode().data.id, {name: this.name})
  }
}
