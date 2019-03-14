import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'renewal-contract-scope',
  templateUrl: './renewal-contract-scope.component.html',
  styleUrls: ['./renewal-contract-scope.component.scss']
})
export class RenewalContractScopeComponent implements OnInit {

  renewalPeriod = '1';
  uwyUnits = '1';
  activeProject = '1';

  constructor() {
  }

  ngOnInit() {
  }

}
