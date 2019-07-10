import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-workspace-file-base-import',
  templateUrl: './workspace-file-base-import.component.html',
  styleUrls: ['./workspace-file-base-import.component.scss']
})
export class WorkspaceFileBaseImportComponent implements OnInit {

  hyperLinks: string[]= ['Risk link', 'File-based'];
  hyperLinksRoutes: any= {
    'Risk link': '/RiskLink',
    'File-based': '/FileBasedImport'
  };
  hyperLinksConfig: {
    wsId: string,
    uwYear: string
  };

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe( ({wsId, year}) => {
      this.hyperLinksConfig= {
        wsId,
        uwYear: year
      }
    })
  }

}
