import { Component, OnInit } from '@angular/core';
import {ICellRendererAngularComp} from "@ag-grid-community/angular";
import {IAfterGuiAttachedParams, ICellRendererParams, RowNode, GridApi, ColumnApi} from '@ag-grid-community/core';
import * as _ from 'lodash'

@Component({
  selector: 'app-grouped-cell-renderer',
  templateUrl: './grouped-cell-renderer.component.html',
  styleUrls: ['./grouped-cell-renderer.component.scss']
})
export class GroupedCellRenderer implements ICellRendererAngularComp {
  params: any;
  api: GridApi;
  columnApi: ColumnApi;

  isGroup: boolean;
  colId: string;
  offsetMargin: string;

  data: {
    value: string,
    childCount: number
  };

  isSelected: boolean;
  isIndeterminate: boolean;
  isExpanded: boolean;

  node: RowNode;

  constructor() {
    this.offsetMargin = '0px';
  }

  afterGuiAttached(params?: IAfterGuiAttachedParams): void {
  }

  agInit(params: ICellRendererParams): void {
    this.params = params;
    this.api = params.api;
    this.columnApi = params.columnApi;
    this.isGroup = params.node.group;
    this.colId = params.node.field;
    this.data = {
      value: params.value,
      childCount: params.node.allChildrenCount
    };
    this.node = params.node;
    this.isSelected = params.node.isSelected();
    this.isIndeterminate = false;
    this.isExpanded = params.node.expanded;
    this.offsetMargin = (this.node.uiLevel * 16) + 'px';
    this.updateIsIndeterminate();
  }

  refresh(params: any): boolean {
    console.log('refresh');
    this.node = params.node;
    this.isSelected = params.node.isSelected();
    this.isIndeterminate = false;
    this.isExpanded = params.node.expanded;
    return true;
  }

  onCheckChange() {
    if(this.isGroup) {
      let isSelected = this.isIndeterminate ? !this.isSelected : true;
      _.forEach(this.node.allLeafChildren, node => node.setSelected(isSelected));
      this.node.setSelected(isSelected);
    } else {
      let isSelected = !this.isSelected;
      this.node.setSelected(isSelected);
      this.node.parent.setSelected(_.every(this.node.parent.allLeafChildren, node => node.isSelected()))
    }

    this.detectChange();
  }

  collapse() {
    this.node.setExpanded(false);
    this.detectChange();
  }

  expand() {
    this.node.setExpanded(true);
    this.detectChange();
  }

  updateIsIndeterminate() {
    this.isIndeterminate = this.isGroup ? _.filter(this.node.allLeafChildren, node => node.isSelected()).length == this.data.childCount : false;
  }

  detectChange() {
    this.api.redrawRows({ rowNodes: this.isGroup ? [this.node,  ...this.node.allLeafChildren] : [this.node, this.node.parent]});
  }

}
