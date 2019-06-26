import { Component, OnInit } from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {filter, map} from 'rxjs/operators';

@Component({
  selector: 'app-workspace-clone-data',
  templateUrl: './workspace-clone-data.component.html',
  styleUrls: ['./workspace-clone-data.component.scss']
})
export class WorkspaceCloneDataComponent implements OnInit {

  constructor(
    private router$: Router
  ) {
    this.activeSubTitle= 0;
  }

  subTitle= {
    0: 'Clone Workspaces Assets',
    1: 'Source Workspace Selection',
    2: 'Target Workspace Selection'
  };

  activeSubTitle: number;

  ngOnInit() {
    this.router$.events.pipe(
      filter(e => e instanceof NavigationStart),
      map(() => this.router$.getCurrentNavigation().extras.state)
    ).subscribe( d => console.log(d))
  }

  setSubTitle(number: number) {
    this.activeSubTitle= number;
  }
}
