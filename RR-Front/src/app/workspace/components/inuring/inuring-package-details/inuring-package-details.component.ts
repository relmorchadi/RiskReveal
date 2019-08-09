import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'inuring-package-details',
  templateUrl: './inuring-package-details.component.html',
  styleUrls: ['./inuring-package-details.component.scss']
})
export class InuringPackageDetailsComponent implements OnInit {

  @Input('data') data;

  constructor() {
  }

  ngOnInit() {
  }


}
