import {Component, Input, OnInit} from '@angular/core';
import {AddInputNode, EditInputNode} from "../../../store/actions";
import {Store} from "@ngxs/store";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'inuring-package-details',
  templateUrl: './inuring-package-details.component.html',
  styleUrls: ['./inuring-package-details.component.scss']
})
export class InuringPackageDetailsComponent implements OnInit {

  @Input('data') data;
  appendedNodes: any = [];
  showCreationPopup: any = false;
  selectedPlts: any = [];
  stepConfig = {wsId: '05PA753', uwYear: '2019', plts: []};
  showEditContractPopup: boolean = false;
  showEditEdgePopup: boolean = false;
  private editInputNode: boolean = false;
  private editableNode: any;
  collapsedCanvas: boolean = false;

  ngOnInit() {
    console.log('this is details', this.data);
    this.stepConfig = {wsId: this.data.wsId, uwYear: this.data.year, plts: []}
  }

  constructor(private _store: Store, private route$: ActivatedRoute) {
  }

  setSelectedWs = ($event) => {
  };

  addNode(type, name, plts = null, top = 0, left = 0) {
    let today = new Date();
    let id = today.getMilliseconds() + today.getSeconds() + today.getHours();
    let node = {nodeId: id, type: type, top: top, left: left, name: name, plts: plts};
    this._store.dispatch(new AddInputNode({
      wsIdentifier: this.stepConfig.wsId + '-' + this.stepConfig.uwYear,
      inuringPackage: this.data.details.id,
      node: node
    }));
    this.appendedNodes.push(node);
  }

  setSelectedPlts(selectedPlts) {
    if (this.editInputNode) {
      let editedData = {...this.editableNode.data};
      editedData.plts = selectedPlts;
      console.log({...this.editableNode})
      this._store.dispatch(new EditInputNode({
        wsIdentifier: this.stepConfig.wsId + '-' + this.stepConfig.uwYear,
        inuringPackage: this.data.details.id,
        node: editedData
      }));
      this.editableNode = {};
    } else {
      this.addNode('inputNode', 'Input Node', selectedPlts)
    }
  }

  onShowCreationPopup($event: any) {
    this.showCreationPopup = $event;
    this.stepConfig.plts = [];
    this.editInputNode = false;
    this.stepConfig = {wsId: this.data.wsId, uwYear: this.data.year, plts: []}
  }

  editNode($event: any) {
    if ($event.type == 'inputNode') {
      this.showCreationPopup = $event.popup;
      this.editInputNode = true;
      let params = $event.params;
      this.editableNode = {...params.node};
      this.stepConfig.plts = {...params.node.data.plts};
    } else if ($event.type == 'contractNode') {
      this.showEditContractPopup = $event.popup;
    }

  }

  editEdge($event: any) {
    this.showEditEdgePopup = $event.popup;
  }

  collapseCanvas() {
    this.collapsedCanvas = !this.collapsedCanvas;
  }

  appendNode(node: any) {
    this.appendedNodes.push(node);
  }
}
