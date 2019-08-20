import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {jsPlumbService} from "jsplumbtoolkit-angular";
import {jsPlumbToolkit, jsPlumbUtil} from "jsplumbtoolkit";
import {SimpleNodeComponent} from "../simple-node/simple-node.component";

@Component({
  selector: 'inuring-graph',
  templateUrl: './inuring-graph.component.html',
  styleUrls: ['./inuring-graph.component.scss']
})
export class InuringGraphComponent implements OnInit {

  // @ViewChild(FlowchartComponent) flowchart:FlowchartComponent;
  // @ViewChild(DatasetComponent) dataset:DatasetComponent;

  toolkitId:string;
  toolkit:jsPlumbToolkit;
  toolkitParams= {
    nodeFactory: (type: string, data: any, callback: Function) => {
      data.id = jsPlumbUtil.uuid();
      callback(data);
      // Dialogs.show({
      //   id: 'dlgText',
      //   title: 'Enter ' + type + ' name:',
      //   onOK: (d: any) => {
      //     data.text = d.text;
      //     // if the user entered a name...
      //     if (data.text) {
      //       // and it was at least 2 chars
      //       if (data.text.length >= 2) {
      //         // set an id and continue.
      //         data.id = jsPlumbUtil.uuid();
      //         callback(data);
      //       }
      //       else
      //       // else advise the user.
      //         alert(type + ' names must be at least 2 characters!');
      //     }
      //     // else...do not proceed.
      //   }
      // });
    },
    beforeStartConnect: (node: any, edgeType: string) => {
      return {label: ''};
    }
  };
  surfaceId='rr';

  constructor(private $jsplumb:jsPlumbService, private elementRef:ElementRef) {
    // this.toolkitId = this.elementRef.nativeElement.getAttribute("toolkitId");
    this.toolkitId = 'flowchart';
  }

  ngOnInit() {
    this.toolkit = this.$jsplumb.getToolkit(this.toolkitId, this.toolkitParams)

    this.toolkit.load({
      data: {
        nodes:[ {id:"1", name:"one"}, {id:"2", name:"two"}],
        edges:[ {source:"1", target:"2" } ]
      }
    })
  }

  view= {
    nodes:{
      default:{
        component:SimpleNodeComponent
      }
    }
  };

  renderParams= {
    layout:{
      type:"Spring"
    }
  };

}
