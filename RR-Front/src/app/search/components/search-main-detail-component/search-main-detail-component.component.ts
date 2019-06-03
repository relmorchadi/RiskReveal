import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'app-search-main-detail-component',
  templateUrl: './search-main-detail-component.component.html',
  styleUrls: ['./search-main-detail-component.component.scss']
})
export class SearchMainDetailComponentComponent implements OnInit {
  @Output('openWorkspaceEvent') openWorkspaceEvent: any = new EventEmitter<any>();
  @Output('popOutWorkspaceEvent') popOutWorkspaceEvent: any = new EventEmitter<any>();

  @Input()
  expandWorkspaceDetails = false;
  @Input()
  currentWorkspace = null;
  @Input()
  selectedWorkspace: any;
  @Input()
  sliceValidator = true;

  constructor() { }

  ngOnInit() {
  }

  openWorkspace(wsId, year) {
    this.openWorkspaceEvent({wsId, year});
  }

  popUpWorkspace(wsId, year) {
    this.popOutWorkspaceEvent({wsId, year});
  }

  sliceContent(content: any, valid: boolean) {
    if (valid && content) {
      return content.slice(0, 3);
    } else {
      return content;
    }
  }
}
