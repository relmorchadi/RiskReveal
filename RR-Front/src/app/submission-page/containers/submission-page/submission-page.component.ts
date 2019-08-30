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

  constructor() { }

  ngOnInit() {
    this.data = Regions.regionPeril;
  }

}
