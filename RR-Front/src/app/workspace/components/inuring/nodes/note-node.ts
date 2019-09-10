import {Component, Input, OnInit} from '@angular/core';
import {BaseNodeComponent} from "jsplumbtoolkit-angular";

@Component({
  selector: 'note-node',
  template: `
    <div class="flowchart-object flowchart-start input-node-card">
      <div class="node-header d-flex justify-content-between align-items-center">
        <div>
          Note Sample
        </div>
        <div>
          <i class="icon-trash-alt"></i>
        </div>
      </div>
      <div class="node-content">
        <textarea tabindex="-1"></textarea>
      </div>
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
      background-color: rgba(248, 231, 28, 0.34);
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
      max-height: 85px;
    }
    
  `]
})
export class NoteNodeComponent extends BaseNodeComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
