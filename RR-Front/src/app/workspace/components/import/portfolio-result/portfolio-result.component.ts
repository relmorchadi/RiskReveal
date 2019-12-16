import {Component, Input, OnInit} from '@angular/core';
import * as fromRiskLink from "../../../store/actions/risk_link.actions";
import {Store} from "@ngxs/store";

@Component({
  selector: 'portfolio-result',
  templateUrl: './portfolio-result.component.html',
  styleUrls: ['./portfolio-result.component.scss']
})
export class PortfolioResultComponent implements OnInit {


  isCollapsed;

  @Input('data')
  portfolios;

  scrollableColsSummary = [
    // {field: 'exposedLocation', header: 'Exposed Location', width: '90px', type: 'indicator', sorting: ', filtered: true, highlight: false, visible: true},
    {field: 'agCurrency', header: 'Source Currency', width: '100px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
    {field: 'targetCurrency', header: 'Target Currency', width: '100px', type: 'text', sorting: '', filtered: true, highlight: true, visible: true, edit: true},
    {field: 'unitMultiplier', header: 'Unit Multiplier', width: '50px', type: 'number', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
    {field: 'proportion', header: 'Proportion', width: '50px', type: 'percentage', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
    {field: 'edmName', header: 'EDM', width: '250px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true, edit: false},
    {field: 'importLocationLevel', header: 'Location Level Scan', width: '50px', type: 'selection', sorting: '', filtered: false, highlight: false, visible: true},
    /*  {field: 'action', header: '', width: '25px', type: 'icon', sorting: ', filtered: false, highlight: false},*/
  ];

  frozenColsSummary = [
    {field: 'selected', header: '', width: '25px', type: 'selection', sorting: '', filtered: false, highlight: false, visible: true},
    {field: 'scan', header: '', width: '25px', type: 'scan', sorting: '', filtered: false, highlight: false, visible: true},
    {field: 'status', header: 'Status', width: '40px', type: 'progress', sorting: '', filtered: false, highlight: false, visible: true},
    {field: 'rlId', header: 'ID', width: '40px', type: 'text', sorting: '', filtered: false, highlight: false, visible: true},
    {field: 'number', header: 'Number', width: '190px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
    {field: 'name', header: 'Name', width: '190px', type: 'text', sorting: '', filtered: true, highlight: false, visible: true},
  ];

  refs = {
    currencies: [
      {label: "Main Liablity Currency (MLC)", value: "USD"},
      {label: "Underlying Analysis Currency (UAC)", value: "UAC"},
      {label: "AED", value: "AED"},
      {label: "AFA", value: "AFA"},
      {label: "AFN", value: "AFN"},
      {label: "ALK", value: "ALK"},
      {label: "ALL", value: "ALL"},
      {label: "BBT", value: "BBT"},
      {label: "BBL", value: "BBL"},
      {label: "BMD", value: "BMD"},
      {label: "MGA", value: "MGA"},
      {label: "USD", value: "USD"},
      {label: "XXC", value: "XXC"}
    ]
  };

  constructor(private store:Store) { }

  ngOnInit() {
  }

  updateRowData(key, value, index) {
    console.log('Update Row data', {key,value,index})
    this.store.dispatch(new fromRiskLink.PatchPortfolioResultAction({
      key, value, index
    }))
  }

  checkRow($event, rowData, type){

  }

  selectRows(rowData, index){
    this.updateRowData('selected', rowData.selected, index);
  }

  deleteFromBasket(id,type){

  }
}
