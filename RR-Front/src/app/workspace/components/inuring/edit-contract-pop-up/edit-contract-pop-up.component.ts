import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from "lodash";

@Component({
  selector: 'app-edit-contract-pop-up',
  templateUrl: './edit-contract-pop-up.component.html',
  styleUrls: ['./edit-contract-pop-up.component.scss']
})
export class EditContractPopUpComponent implements OnInit {
  @Input() showEditContractPopup: any;
  currencyEntity={};
  @Output('closePop') closePopEmitter: EventEmitter<any> = new EventEmitter();
  layersColumns = [
    {field: 'num', header: 'Num'},
    {field: 'seq', header: 'Seq'},
    {field: 'code', header: 'Code'},
    {field: 'desc', header: 'Desc'},
    {field: 'limit', header: 'Limit'},
    {field: 'excess', header: 'Excess'},
    {field: 'prem', header: 'Prem'},
    {field: 'pct', header: 'Pct'},
    {field: 'placed', header: 'Placed'},
    {field: 'inception', header: 'Inception'},
    {field: 'expiration', header: 'Expiration'},
    {field: 'actionDelete', header: ''},
  ];
  reinstatementsColumns = [
    {field: 'reinsRank', header: 'Reins Rank'},
    {field: 'reinsNum', header: 'Reins Num'},
    {field: 'reinsCharge', header: 'Reins Charge'},
    {field: 'PRTemporis', header: 'PR Temporis'},
    {field: 'actionDelete', header: ''},
  ];
  perilColumns = [
    {field: 'peril', header: 'Peril'},
    {field: 'perilLimit', header: 'Peril Limit'},
    {field: 'actionDelete', header: ''},
  ];
  layers: any = [];
  perilLimits: any = [];
  reinstatements: any = [];
  perilFilters: any = [];

  filters: any = [];
  perilFiltersTypes: any = ['Peril', 'Region Peril', 'Grain'];
  perils: any = ['CS', 'EQ', 'FL', 'FR', 'OP', 'TC', 'TO', 'TR', 'TY', 'WS', 'WT'];
  currency: any;
  currencies: any = [
    {id: '1', name: 'Euro', label: 'EUR'},
    {id: '2', name: 'Us Dollar', label: 'USD'},
    {id: '3', name: 'Britsh Pound', label: 'GBP'},
    {id: '4', name: 'Canadian Dollar', label: 'CAD'},
    {id: '5', name: 'Moroccan Dirham', label: 'MAD'},
    {id: '5', name: 'Swiss Franc', label: 'CHF'},
    {id: '5', name: 'Saudi Riyal', label: 'SAR'},
    {id: '6', name: 'Bitcoin', label: 'XBT'},
    {id: '7', name: 'Hungarian forint', label: 'HUF'},
    {id: '8', name: 'Singapore Dollars', label: 'SGD'}
  ];

  constructor() {
  }

  ngOnInit() {
  }

  closePopup() {
    this.closePopEmitter.emit();
  }

  addLayer() {
    let lastAddedNum = this.layers.length > 0 ? this.layers[this.layers.length - 1].num : 0;
    let layer = {
      num: lastAddedNum + 1,
      seq: lastAddedNum + 1,
      code: 'Undefined',
      desc: 'Undefined',
      limit: '0',
      excess: '0',
      prem: '0',
      pct: '0',
      placed: '100,00',
      inception: '01-01-2019',
      expiration: '01-01-2019'
    }
    this.layers.push(layer);
  }

  removeLayer(num) {
    let index = _.findIndex(this.layers, (layer: any) => layer.num == num);
    this.layers.splice(index, 1);
  }

  addPerilLimit() {
    let lastAddedId = this.perilLimits.length > 0 ? this.perilLimits[this.perilLimits.length - 1].id : 0;
    let perilLimit = {
      id: lastAddedId + 1,
      peril: '',
      perilLimit: '',
    }
    this.perilLimits.push(perilLimit);
  }

  removePerilLimit(id: any) {
    let index = _.findIndex(this.perilLimits, (limit: any) => limit.id == id);
    this.perilLimits.splice(index, 1);
  }

  addReinstatement() {
    let lastAddedId = this.reinstatements.length > 0 ? this.reinstatements[this.reinstatements.length - 1].id : 0;
    let reinstatement = {
      id: lastAddedId + 1,
      reinsRank: lastAddedId + 1,
      reinsNum: 'Unlimited',
      reinsCharge: 100.00,
      PRTemporis: false,
    }
    this.reinstatements.push(reinstatement);
  }

  removeRreinstatement(id: any) {
    let index = _.findIndex(this.reinstatements, (reinstatement: any) => reinstatement.id == id);
    this.reinstatements.splice(index, 1);
  }

  addPeriFilter() {
    let lastAddedId = this.perilFilters.length > 0 ? this.perilFilters[this.perilFilters.length - 1].id : 0;
    let perilFilter = {
      id: lastAddedId + 1,
      type: null,
      filter: null,
      includeExclude: 'include',
      PRTemporis: false,
    }
    this.perilFilters.push(perilFilter);
  }

  saveFilter() {

  }

  removeFilter(id: any) {
    let index = _.findIndex(this.perilFilters, (perilFilter: any) => perilFilter.id == id);
    this.perilFilters.splice(index, 1);
  }

}

