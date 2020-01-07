import {Component, Input, OnInit} from '@angular/core';
import * as fromRiskLink from "../../../store/actions/risk_link.actions";
import {Store} from "@ngxs/store";
import componentData from './data';

@Component({
  selector: 'portfolio-result',
  templateUrl: './portfolio-result.component.html',
  styleUrls: ['./portfolio-result.component.scss']
})
export class PortfolioResultComponent implements OnInit {


  isCollapsed;

  @Input('data')
  portfolios;

  @Input('context')
  context;

  scrollableColsSummary;

  frozenColsSummary;

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
    if(this.context == 'FAC'){
      this.scrollableColsSummary= componentData.FAC.scrollableCols;
      this.frozenColsSummary= componentData.FAC.fixedCols;
    } else {
      this.scrollableColsSummary= componentData.Treaty.scrollableCols;
      this.frozenColsSummary= componentData.Treaty.fixedCols;
    }
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
