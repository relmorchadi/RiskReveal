import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Store} from '@ngxs/store';
import {BaseContainer} from '../../../shared/base';

@Component({
  selector: 'app-workspace-file-base-import',
  templateUrl: './workspace-file-base-import.component.html',
  styleUrls: ['./workspace-file-base-import.component.scss']
})
export class WorkspaceFileBaseImportComponent extends BaseContainer implements OnInit {

  hyperLinks: string[]= ['Risk link', 'File-based'];
  hyperLinksRoutes: any= {
    'Risk link': '/RiskLink',
    'File-based': '/FileBasedImport'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };

  constructor(private route: ActivatedRoute, _baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
  }

  ngOnInit() {
    this.route.params.pipe(this.unsubscribeOnDestroy).subscribe(({wsId, year}) => {
      this.hyperLinksConfig= {
        wsId,
        uwYear: year
      }
    })
  }

  ngOnDestroy(): void {
    this.destroy();
  }

  protected detectChanges() {
    super.detectChanges();
  }

}
