import { Component, OnInit } from '@angular/core';
import { Regions } from '../../../shared/data/region-peril';

@Component({
  selector: 'app-submission-page',
  templateUrl: './submission-page.component.html',
  styleUrls: ['./submission-page.component.scss']
})
export class SubmissionPageComponent implements OnInit {
  uwAnalysis = 'Submission Data';
  contractId = '02F091155';
  uwYear = '2019';
  insured = 'Global Partners';
  contractLabel = '';
  subsidiary = {
    option: [
      'SCOR',
      'SCOR ASIA PACIF.',
      'SCOR CANADA',
      'SCOR GERMANY',
      'SCOR RE US',
      'SCOR REASS.',
      'SCOR SWITZERLAND',
      'SCOR UK'],
    value: 'SCOR REASS.'
  };
  businessType = {
    option: ['New Business', 'Renewal'],
    value: 'New Business'
  };
  lob = {
    option: ['Property', 'Construction'],
    value: 'Property'
  };
  sector = '';
  raisedBy = {
    option: [
      'Guillaume POULET',
      'Juan SANTANA',
      'Maelle DANIEL',
      'Melanie ROBINSON',
      'Richard DEEM',
      'Soon Ling MACK'],
    value: 'Richard DEEM'
  };
  data: any;
  metaData: any;

  coverageTemplate = [
    {field: 'divisionNo', header: 'Division No', width: '40px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: false},
    {field: 'principal', header: 'Principal', width: '150px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: true},
    {field: 'lob', header: 'LOB', width: '150px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: true},
    {field: 'coverage', header: 'Coverage', width: '150px', type: 'text', sorted: false, filtered: true, highlight: false, visible: true, edit: true},
    {field: 'action', header: 'Action', width: '40px', type: 'action', sorted: false, filtered: false, highlight: false, visible: true, edit: false},
  ];

  dataCoverage = [
    {
      divisionNo: 1,
      principal: true,
      lob: 'Property',
      coverage: 'PD, BI'
    }
  ];

  constructor() { }

  ngOnInit() {
    this.data = Regions.regionPeril;
  }

  addRow() {
    this.dataCoverage = [...this.dataCoverage, {
      divisionNo: this.dataCoverage.length + 1,
      principal: false,
      lob: 'Property',
      coverage: 'PD, BI'
    }];
  }

  deleteRow(index) {
    this.dataCoverage.splice(index, 1);
  }
}
