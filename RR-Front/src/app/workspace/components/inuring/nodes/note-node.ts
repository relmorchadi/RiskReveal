import {Component, OnInit} from '@angular/core';
import {BaseNodeComponent} from "jsplumbtoolkit-angular";

@Component({
  selector: 'note-node',
  template: `
    <div class="flowchart-object flowchart-start input-node-card"
         [ngStyle]="{'background-color':color,'overflow': 'visible'}">
      <div class="node-header d-flex justify-content-between align-items-center">
        <input class="input-name" (ngModelChange)="changeNoteName()" [(ngModel)]="name"
               [ngStyle]="{'background-color':color}">
        <div>
          <i class="icon-format_color_fill_24px" style="position: relative" (click)="colorPicker = true">
            <color-github *ngIf="colorPicker"
                          [colors]="presetColors"
                          (onChangeComplete)="changeNoteColor($event.color.hex)"
            >
            </color-github>
          </i>

          <i class="icon-trash-alt" (click)="deleteNode()"></i>
        </div>
      </div>
      <div class="node-content">
        <textarea tabindex="-1"></textarea>
      </div>
    </div>
  `,
  styles: [`
    ::ng-deep .github-picker {
      width: max-content !important;
      position: absolute !important;
      left: -10px;
      top: 20px;
    }
    .input-node-card {
      min-height: 100px;
      width: 198px;
      /*background-color: rgba(248, 231, 28, 0.34);*/
      border: 1px solid #F5A623;
    }

    .input-name {
      border: none !important;
      /*background-color: rgba(253, 247, 178, 0.34);*/
    }

    i.icon-trash-alt {
      font-weight: bold;
      font-size: 16px;
    }

    .input-node-card .node-header {
      /*width: 100%;*/
      font-weight: bold;
      text-align: left;
      margin: 5px 5px;
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
  color: any;
  colorPicker: any = false;
  presetColors: string[] = ['#ffed78', '#a8ed85', '#809a7a', '#7f98d8', '#64ffda', '#c2baf7', '#f5a4c6'];
  constructor() {
    super();
  }

  ngOnInit() {
    console.log('Here i m', this.getNode().data.content);
    this.name = this.getNode().data.name;
    this.color = this.getNode().data.color;
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

  closeColorPicker() {
    event.stopPropagation();
    event.preventDefault();
    this.colorPicker = false
  }

  changeNoteName() {
    window['toolkit'].updateNode(this.getNode().data.id, {name: this.name})
  }

  changeNoteColor(color) {
    window['toolkit'].updateNode(this.getNode().data.id, {color: color})
    this.color = color;
    this.colorPicker = false
  }
}
