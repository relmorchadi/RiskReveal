import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {Dialogs, Surface} from "jsplumbtoolkit";
import {jsPlumbService, jsPlumbSurfaceComponent} from "jsplumbtoolkit-angular";
import {
  ContractNodeComponent,
  FinalNodeComponent,
  InputNodeComponent,
  JoinNodeComponent,
  NoteNodeComponent
} from "../nodes";
import {BaseContainer} from "../../../../shared/base";
import {Router} from "@angular/router";
import {Actions, ofActionDispatched, Store} from "@ngxs/store";
import {AddInputNode, AddJoinNode, AddNoteNode, EditInputNode} from "../../../store/actions";
import * as fromInuring from "../../../store/actions/inuring.actions"

@Component({
  selector: 'inuring-graph',
  templateUrl: './inuring-graph.component.html',
  styleUrls: ['./inuring-graph.component.scss']
})
export class InuringGraphComponent extends BaseContainer implements OnInit, AfterViewInit, OnDestroy {

  surfaceId = 'flowchart';
  toolkitId = 'flowchartSurface';
  toggleGrip;

  surface: Surface;
  @Output('editNode') editNodeEmitter: EventEmitter<any> = new EventEmitter();
  @Output('editEdge') editEdgeEmitter: EventEmitter<any> = new EventEmitter();

  @Input('wsIdentifier') wsIdentifier;
  @Input('inuringPackage') inuringPackage;

  @Input('data')
  set setInuringData(d){
    console.log('Data Inuring Graph', d);
  }
  view = {
    nodes: {
      'selectable': {
        events: {
          tap: (params: any) => {
            console.log('Tap Param', params);
            if (params.node.data.ofType === 'stats') {
              if (params.e.which === 3) return;
              // this.store$.dispatch(new SelectLayoutToShowAction({id: 4, type: 'DEFAULT'}));
              return;
            }
            // this.nodeName = params.node.data.name;
            // this.toggleSelection(params.node);
          },
          dblclick: (params: any) => {
            this.dblclick(params)
          }
        }
      },
      'inputNode': {
        parent: 'selectable',
        component: InputNodeComponent,
        events: {}
      },
      'contractNode': {
        parent: 'selectable',
        component: ContractNodeComponent,
        events: {},
      },
      'joinNode': {
        parent: 'selectable',
        component: JoinNodeComponent,
        events: {},
      },
      'finalNode': {
        parent: 'selectable',
        component: FinalNodeComponent,
        events: {},
      },
      'noteNode': {
        parent: 'selectable',
        component: NoteNodeComponent,
        events: {},
      }

    },
    groups: {
      default: {
        component: InputNodeComponent,
        template: 'group',
        endpoint: 'Blank',
        anchor: 'Continuous',
        revert: true,
        orphan: true,
        constrain: false,
        autoSize: false,
        droppable: true
      },
      constrained: {
        parent: 'default',
        constrain: true
      }
    },
    edges: {
      default: {
        anchor: 'Continuous',
        endpoint: 'Blank',
        // connector: ['Flowchart', {cornerRadius: 5}],
        paintStyle: {strokeWidth: 2, stroke: 'rgb(132, 172, 179)', outlineWidth: 3, outlineStroke: 'transparent'},
        hoverPaintStyle: {strokeWidth: 2, stroke: '#b95124'}, // hover paint style for this edge type.
        events: {
          dblclick: (params: any) => {
            // console.log('***************',params)
          }
        },
        overlays: [
          ['Arrow', {location: 1, width: 10, length: 10}],
          ['Arrow', {location: 0.3, width: 10, length: 10}],
          ['Label', {
            cssClass: "edge-config-point",
            label: '+  N',
            events: {
              click: (params) => {
                this.edgeLabelClick(params)
              }
            }
          }],
        ]
      },
      dashed: {
        Anchor: 'Continuous',
        Endpoint: 'Blank',
        Connector: ['StateMachine', {
          cssClass: 'connectorClass',
          hoverClass: 'connectorHoverClass'
        }],
        PaintStyle: {strokeWidth: 4, stroke: '#89bcde'},
        HoverPaintStyle: {stroke: '#4490de'},
        events: {
          dblclick: (params: any) => {
            Dialogs.show({
              id: 'dlgConfirm',
              data: {
                msg: 'Delete Edge'
              },
              onOK: () => {
                // this.removeEdge(params.edge);
              }
            });
          }
        },
        Overlays: [
          [
            'Label',
            {
              cssClass: 'delete-relationship',
              id: function (params) {
                console.log(params);
              },
              label: function (params) {
                return '';
              },

              events: {}
            }],
          [
            'Arrow', {
            cssClass: 'arrow dashArrow',
            fillStyle: '#445566',
            width: 10,
            length: 10,
            location: 1
          }]
        ]
      },
    },
    ports: {
      start: {
        endpoint: 'Blank',
        anchor: 'Continuous',
        uniqueEndpoint: true,
        edgeType: 'default',
        allowLoopback: false,
        allowNodeLoopback: false,
      },
      source: {
        endpoint: 'Blank',
        paintStyle: {fill: '#84acb3'},
        anchor: 'Continuous',
        maxConnections: -1,
        edgeType: 'connection',
        allowLoopback: false,
        allowNodeLoopback: false,
      },
      target: {
        maxConnections: -1,
        endpoint: 'Blank',
        anchor: 'Continuous',
        paintStyle: {fill: '#84acb3'},
        isTarget: true,
        allowLoopback: false,
        allowNodeLoopback: false,
      },
    }
  };
  private editableNode: any;

  renderParams = {
    layout: {
      type: 'Spring'
    },
    events: {
      edgeAdded: (params: any) => {
        if (params.addedByMouse) {
          // this.editLabel(params.edge);
        }
      },
      nodeDropped: (info) => {
        console.log('node ', info.source.id, 'dropped on ', info.target.id);
      }
    },
    consumeRightClick: false,
    elementsDroppable: true,
    dragOptions: {
      start: (params) => {
        console.log('drag Start', params);
      },
      stop: (params) => {
        console.log('drag End', params);
      },
      filter: '.jtk-draw-handle, .node-action, .node-action i'
    }
  };

  @ViewChild(jsPlumbSurfaceComponent)
  surfaceComponent: jsPlumbSurfaceComponent;

  toolkit;
  afterViewInit = false;

  constructor(private $jsplumb: jsPlumbService, private _router: Router, private _cdRef: ChangeDetectorRef, private _store: Store, private _actions: Actions) {
    super(_router, _cdRef, _store);
  }

  ngOnInit(): void {
    this._actions.pipe(
      this.unsubscribeOnDestroy,
      ofActionDispatched(AddInputNode, AddJoinNode, EditInputNode, AddNoteNode)
    ).subscribe(action => {
      console.log('Actions here')
      if (action instanceof AddInputNode){
        return this.toolkit.addNode({
          type: 'inputNode',
          plts: action.payload.plts,
          index: action.payload.index,
          name: 'Input Node'
        });
      }
      else if (action instanceof AddJoinNode)
        return this.toolkit.addNode({type: 'joinNode'});
      else if (action instanceof EditInputNode) {
        let data = action.payload.data;
        return this.toolkit.updateNode(this.editableNode, data);
      } else if (action instanceof AddNoteNode) {
        this.toolkit.addNode({type: 'noteNode', name: action.payload.name});
      }

    })
  }

  ngAfterViewInit(): void {
    this.surface = window['surface'] = this.surfaceComponent.surface;
    this.toolkit = window['toolkit'] = this.$jsplumb.getToolkit(this.toolkitId);
    console.log('toolkit ===> ', this.toolkit.getNodes());
    // this.toolkit.addNode({type: 'inputNode'});
    // this.toolkit.addNode({type: 'contractNode'});
    // this.toolkit.addNode({type: 'joinNode'});
    if (this.toolkit.getNodes().length == 0) {
      /*this.addNode('inputNode','Input Node');
      this.addNode('finalNode','Final Node');*/
      this.toolkit.addNode({type: 'inputNode', top: 0, left: 0, name: 'Input Node'});
      this.toolkit.addNode({type: 'finalNode', top: 600, left: 450, name: 'Final Node'});
      this.afterViewInit = true;
    }

    console.log('============>', this.toolkit.getNodes().map(row => row.data));
  }

  drop(param) {
    this.toolkit.addNode({type: 'contractNode'});
  }

  addNode(type, name, top = 0, left = 0, plts = null) {
    let today = new Date();
    let id = today.getMilliseconds() + today.getSeconds() + today.getHours();
    let node = {nodeId: id, type: type, top: top, left: left, name: name, plts: plts};
    this._store.dispatch(new AddInputNode({
      wsIdentifier: this.wsIdentifier,
      inuringPackage: this.inuringPackage,
      node: node
    }));
    this.toolkit.addNode(node);
  }
  refreshToolkit() {
    this.toolkit.clear();
    this.toolkit.addNode({type: 'inputNode', top: 0, left: 0, name: 'Input Node'});
    this.toolkit.addNode({type: 'finalNode', top: 600, left: 450, name: 'Final Node'});
    this.dispatch(new fromInuring.RefreshInuringGraph(null))
  }

  addJoinNode() {
    this.toolkit.addNode({type: 'joinNode'});
  }

  addNoteNode(){
    this.toolkit.addNode({type: 'noteNode', content: 'Heey !!', name: 'New Note'});
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  private dblclick(params) {
    if (params.node.data.type == 'inputNode') {
      console.log('CLicked inputNode 2 times', params);
      this.editableNode = {...params.node};
      this.editNodeEmitter.emit({type: 'inputNode', popup: true, params: {...params}});
    } else if (params.node.data.type == 'contractNode') {
      console.log('CLicked contractNode 2 times ', params.node);
      this.editNodeEmitter.emit({type: 'contractNode', popup: true, params: {...params}});
    }


  }

  private edgeLabelClick(params: any) {
    console.log('labelOverlay ======> ', params);
    this.editEdgeEmitter.emit({popup: true, params: params})
  }
}
