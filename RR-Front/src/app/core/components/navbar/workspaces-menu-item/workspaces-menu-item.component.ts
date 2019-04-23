import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../../shared/helper.service';
import {Router} from '@angular/router';
import {SearchService} from '../../../service/search.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import * as _ from 'lodash';
import {forkJoin, Observable, of} from 'rxjs';
import {PatchSearchStateAction} from '../../../store/actions';
import {Store} from '@ngxs/store';

@Component({
  selector: 'workspaces-menu-item',
  templateUrl: './workspaces-menu-item.component.html',
  styleUrls: ['./workspaces-menu-item.component.scss']
})
export class WorkspacesMenuItemComponent implements OnInit {
  contractFilterFormGroup: FormGroup;
  workspaces: any = [];
  selectedWorkspace = null;
  selectedItems = [];
  numberofElement: number;
  lastOnes = 10;
  visible: any;
  labels: any = [];

  constructor(private _helperService: HelperService, private router:Router, private _searchService: SearchService,
              private _fb: FormBuilder, private store: Store) {
    this.setForm();
  }

  ngOnInit() {
    this._helperService.test$.subscribe((ws: any) => {
      this.workspaces = ws;
      this.numberofElement = this.workspaces.length;
      this.workspaces[0].selected = true;
    });
    this._searchService.infodropdown.subscribe( dt => this.visible = this._searchService.getvisibleDropdown());

    if (this.numberofElement > 10) {
      this.labels.push('Last 10');
    }
    if (this.numberofElement > 50) {
      this.labels.push('Last 50');
    }
    if (this.numberofElement > 100) {
      this.labels.push('Last 100');
    }

    this.searchWorkspace('10');
  }

  private searchData(id, year) {
    return this._searchService.searchWorkspace(id || '', year || '2019');
  }

  setForm() {
    this.contractFilterFormGroup = this._fb.group({
      globalKeyword: [],
      workspaceId: [],
      workspaceName: [],
      year: [],
      treaty: [],
      cedantCode: [],
      cedant: [],
      country: []
    });
  }

  searchWorkspace(size: string = '10') {
    this.workspaces = [];
    const items = JSON.parse(localStorage.getItem('usedWorkspaces'));
    items.forEach(
      ws => {
        this.workspaces = [...this.workspaces, {...ws, selected: false, timeStamp: Date.now()}];
    });
    if (this.selectedItems.length === 0) {
      this.workspaces[0].selected = true;
      this.selectedItems = [...this.selectedItems, this.workspaces[0]];
    } else {
      this.workspaces.forEach((ws) => {
          this.selectedItems.forEach((si) => ws.workSpaceId === si.workSpaceId ? ws.selected = true : null);
        }
      );
    }
  }

  searchNewWorkspace(search) {
    // let selectedItems =[ ...this.workspaces.filter(ws => ws.selected)];
    this.contractFilterFormGroup.patchValue({cedant: search.target.value});
    // this.searchWorkspace();
  }

  toggleWorkspace(workspace) {
    if (workspace.selected === true) {
      this.selectedItems = [...this.selectedItems, workspace];
    } else {
      this.selectedItems.filter(ws => ws.workSpaceId !== workspace.workSpaceId);
    }
    workspace.selected = !workspace.selected;
  }

  popOutWorkspaces() {
    this.visible = false;
    this.workspaces.filter(ws => ws.selected).forEach(ws => {
      window.open('/workspace/' + ws.workSpaceId + '/' + ws.uwYear);
      console.log('try to open', ws);
    });
  }

  async openWorkspaces() {
    const selectedItems = [ ...this.workspaces.filter(ws => ws.selected)];
    let workspaces = [];
    selectedItems.forEach(
      (SI) => {
        this.searchData(SI.workSpaceId, SI.uwYear).subscribe(
          (dt: any) => {
            const workspace = {
              workSpaceId: SI.workSpaceId,
              uwYear: SI.uwYear,
              cedantCode: dt.cedantCode,
              cedantName: dt.cedantName,
              ledgerName: dt.ledgerName,
              ledgerId: dt.subsidiaryLedgerId,
              subsidiaryName: dt.subsidiaryName,
              subsidiaryId: dt.subsidiaryId,
              expiryDate: dt.expiryDate,
              inceptionDate: dt.inceptionDate,
              treatySections: dt.treatySections,
              workspaceName: dt.worspaceName,
              years: dt.years
            };
            workspaces = [workspace, ...workspaces];
            if (workspaces.length === selectedItems.length) {
              this._helperService.affectItems(workspaces);
              if ( JSON.parse(localStorage.getItem('usedWorkspaces')) === null ) {
                this._helperService.updateRecentWorkspaces(workspaces);
                // localStorage.setItem('usedWorkspaces', JSON.stringify(workspaces));
              } else {
                let usedWorkspaces = JSON.parse(localStorage.getItem('usedWorkspaces'));
                usedWorkspaces = _.pullAllWith(usedWorkspaces, workspaces, _.isEqual);
                usedWorkspaces.unshift(...workspaces);
                this._helperService.updateRecentWorkspaces(usedWorkspaces);
                // localStorage.setItem('usedWorkspaces', JSON.stringify(usedWorkspaces));
              }
              this.router.navigate(['/workspace']);
              this.visible = false;
              this.selectedItems = [];
              this.workspaces.forEach(ws => ws.selected = false);
              this.searchWorkspace('10');
            }
          }
        );
      }
    );

  }
}
