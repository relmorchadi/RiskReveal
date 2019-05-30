import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';

@Component({
  selector: 'app-user-preference',
  templateUrl: './user-preference.component.html',
  styleUrls: ['./user-preference.component.scss']
})
export class UserPreferenceComponent implements OnInit {

  constructor(public location: Location) { }

  ngOnInit() {
  }

  navigateBack() {
    this.location.back();
  }

}
