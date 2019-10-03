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


  ngOnInit() {
    console.log('this is details', this.data);
  }

  constructor(private _store: Store, private route$: ActivatedRoute) {
  }

  setSelectedWs = ($event) => {
  };

  setSelectedPlts(selectedPlts) {
    if (this.editInputNode) {
      // this.editableNode.data.plts = selectedPlts;
      let editedData = {...this.editableNode.data};
      editedData.plts = selectedPlts;
      console.log({...this.editableNode})
      this._store.dispatch(new EditInputNode({data: editedData}));
      this.editableNode = {};
    } else {
      this.appendedNodes.push(selectedPlts);
      this._store.dispatch(new AddInputNode({plts: selectedPlts, index: this.appendedNodes.length}));
    }

  }

  onShowCreationPopup($event: any) {
    this.showCreationPopup = $event;
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
}
