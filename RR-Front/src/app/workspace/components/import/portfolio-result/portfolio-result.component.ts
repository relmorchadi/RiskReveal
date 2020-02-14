import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import * as fromRiskLink from "../../../store/actions/risk_link.actions";
import {Store} from "@ngxs/store";
import componentData from './data';
import * as _ from 'lodash';

@Component({
  selector: 'portfolio-result',
  templateUrl: './portfolio-result.component.html',
  styleUrls: ['./portfolio-result.component.scss']
})
export class PortfolioResultComponent implements OnInit, OnChanges {


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


  contextSelectedItem: any;
  itemCm = [
    // {
    //   label: 'Edit', icon: 'pi pi-pencil', command: (event) => {
    //     // this.editRowPopUp = true;
    //   }
    // },
    {
      label: 'Delete Item',
      icon: 'pi pi-trash',
      command: (event) => this.deletePortfolioFromBasket(event)
    },
  ];

  constructor(private store:Store) { }

  ngOnInit() {
    this.loadTablesCols();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes.context){
      this.loadTablesCols();
    }
  }

  private loadTablesCols(){
    if(_.toUpper(this.context) == 'FAC'){
      this.scrollableColsSummary= componentData.FAC.scrollableCols;
      this.frozenColsSummary= componentData.FAC.fixedCols;
    } else if(_.toUpper(this.context) == 'TREATY') {
      this.scrollableColsSummary= componentData.Treaty.scrollableCols;
      this.frozenColsSummary= componentData.Treaty.fixedCols;
    } else {
      console.error('Unknown Context ', this.context);
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

  deletePortfolioFromBasket(event){
    const ids = _.map(
          _.filter(this.portfolios, item => item.selected),
          (item:any) => item.rlPortfolioId
        );
    this.store.dispatch(new fromRiskLink.DeleteFromImportBasketAction({
      type: 'PORTFOLIO',
      ids: _.size(ids) ? ids :  [this.contextSelectedItem.rlPortfolioId]
    }))
  }
}
