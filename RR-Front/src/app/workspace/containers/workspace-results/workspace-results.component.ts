import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BaseContainer} from '../../../shared/base';
import {Store} from '@ngxs/store';
import {Router} from '@angular/router';

@Component({
  selector: 'app-workspace-results',
  templateUrl: './workspace-results.component.html',
  styleUrls: ['./workspace-results.component.scss']
})
export class WorkspaceResultsComponent extends BaseContainer implements OnInit {

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
