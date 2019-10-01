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
import {AddInputNode, AddJoinNode, EditInputNode} from "../../../store/actions";
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
        overlays: [
          ['Arrow', {location: 1, width: 10, length: 10}],
          ['Arrow', {location: 0.3, width: 10, length: 10}]
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

  constructor(private $jsplumb: jsPlumbService, private _router: Router, private _cdRef: ChangeDetectorRef, private _store: Store, private _actions: Actions) {
    super(_router, _cdRef, _store);
  }

  ngOnInit(): void {
    this._actions.pipe(
      this.unsubscribeOnDestroy,
      ofActionDispatched(AddInputNode, AddJoinNode, EditInputNode)
    ).subscribe(action => {
      console.log('Actions here')
      if (action instanceof AddInputNode){
        return this.toolkit.addNode({type: 'inputNode', plts: action.payload.plts, index: action.payload.index});
      }
      else if (action instanceof AddJoinNode)
        return this.toolkit.addNode({type: 'joinNode'});
      else if (action instanceof EditInputNode) {
        let data = action.payload.data;
        console.log('data => ', data);
        console.log('node => ', this.editableNode);
        return this.toolkit.updateNode(this.editableNode, data);

      }


    })
    this.surface.refresh();
    this.surface.reload()
  }

  ngAfterViewInit(): void {
    this.surface = window['surface'] = this.surfaceComponent.surface;
    this.toolkit = window['toolkit'] = this.$jsplumb.getToolkit(this.toolkitId);
    // this.toolkit.addNode({type: 'inputNode'});
    // this.toolkit.addNode({type: 'contractNode'});
    // this.toolkit.addNode({type: 'joinNode'});
    this.toolkit.addNode({type: 'finalNode', top : 600, left: 450});
  }

  drop(param) {
    this.toolkit.addNode({type: 'contractNode'});
  }

  refreshToolkit() {
    this.toolkit.clear();
    this.toolkit.addNode({type: 'finalNode', top : 600, left: 450});
    this.dispatch(new fromInuring.RefreshInuringGraph(null))
  }

  addJoinNode() {
    this.toolkit.addNode({type: 'joinNode'});
  }

  addNoteNode(){
    this.toolkit.addNode({type: 'noteNode', content: 'Heey !!'});
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
}
