import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BaseContainer} from '../../../shared/base';
import {Store} from '@ngxs/store';
import {Router} from '@angular/router';

@Component({
  selector: 'app-workspace-exposures',
  templateUrl: './workspace-exposures.component.html',
  styleUrls: ['./workspace-exposures.component.scss']
})
export class WorkspaceExposuresComponent extends BaseContainer implements OnInit {

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
