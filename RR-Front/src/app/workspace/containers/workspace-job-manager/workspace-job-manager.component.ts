import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common'

@Component({
  selector: 'app-workspace-job-manager',
  templateUrl: './workspace-job-manager.component.html',
  styleUrls: ['./workspace-job-manager.component.scss']
})
export class WorkspaceJobManagerComponent implements OnInit {

  constructor(public _location: Location) { }

  ngOnInit() {
  }

  navigateBack() {
    this._location.back();
  }
}
