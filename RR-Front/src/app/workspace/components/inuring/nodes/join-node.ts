import {Component, Input, OnInit} from '@angular/core';
import {BaseNodeComponent} from "jsplumbtoolkit-angular";

@Component({
  selector: 'join-node',
  template: `
    <div class="flowchart-object flowchart-start oval-node d-flex justify-content-between align-items-center">
      <div class="node-text">
        <i class="icon-plus"></i>
        N
      </div>
      
      <div class="outer inner div-style"></div>
      <jtk-target port-type="target"></jtk-target>
      <jtk-source filter=".outer" port-type="source"></jtk-source>
    </div>
  `,
  styles: [`
    .div-style {
      width: 10px;
      height: 10px;
      background-color: #FFFFFF;
      position: absolute;
      bottom: 0px;
      /* left: 0px; */
      right: 38%;
      border-radius: 10px;
      font-weight: bold;
    }
    
    .oval-node {
      height: 46px;
      width: 46px;
      background-color: #0700CF;
      box-shadow: 0 9px 12px 1px rgba(0, 0, 0, 0.07), 0 3px 16px 2px rgba(0, 0, 0, 0.06), 0 5px 6px -3px rgba(0, 0, 0, 0.1);
      border-radius: 30px;
      color: white;
      font-size: 0.9rem;
    }
    
    .node-text{
        width: 85%;  
    }
    
    i.icon-plus{
      color: rgba(255,255,255,0.5);
    }
    

    
  `]
})
export class JoinNodeComponent extends BaseNodeComponent implements OnInit {

  @Input('number')
  nodeNumber = '1';

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
