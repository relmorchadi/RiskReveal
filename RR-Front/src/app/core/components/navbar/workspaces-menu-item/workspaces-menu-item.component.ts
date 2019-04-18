import {Component, OnInit} from '@angular/core';
import {HelperService} from '../../../../shared/helper.service';
import {Router} from '@angular/router';
import {SearchService} from '../../../service/search.service'
import {FormBuilder, FormGroup} from "@angular/forms";
import * as _ from 'lodash';
import {forkJoin} from "rxjs";
import {PatchSearchStateAction} from "../../../store/actions";

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
  lastOnes = 10;
  visible: boolean = false;
  workspaceData: any;

  constructor(private _helperService: HelperService,private router:Router, private _searchService: SearchService,
              private _fb: FormBuilder) {
    this.setForm();
  }

  ngOnInit() {
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
    this._searchService.searchContracts(this.contractFilterFormGroup.value, size)
      .subscribe((data: any) => {
        console.log(data);
        data.content.forEach(
          ws => {
            const item = {
              id: ws.workSpaceId,
              workspaceName: ws.workspaceName,
              programName: ws.programName,
              countryName: ws.countryName,
              expiryDate: ws.expiryDate,
              subsidiaryLedgerid: ws.subsidiaryLedgerid,
              subsidiaryid: ws.subsidiaryid,
              treatyName: ws.treatyName,
              cedantName: ws.cedantName,
              year: ws.uwYear,
              selected: false
            };
            this.workspaces = [...this.workspaces, item];
          }
        );
        if (this.selectedItems.length === 0) {
          this.workspaces[0].selected = true;
          this.selectedItems.push(this.workspaces[0]);
        } else {
          this.workspaces.forEach((ws) => {
              this.selectedItems.forEach((si) => ws.id === si.id ? ws.selected = true : null);
            }
          );
        }
      });
  }

  updateWorkspaces(event) {
    this.searchWorkspace(event);
  }

  searchNewWorkspace(search) {
    // let selectedItems =[ ...this.workspaces.filter(ws => ws.selected)];
    this.contractFilterFormGroup.patchValue({cedant: search.target.value});
    this.searchWorkspace();
  }

  toggleWorkspace(workspace) {
    if (workspace.selected === true) {
      this.selectedItems.push(workspace);
    } else {
      this.selectedItems.filter(ws => ws.id !== workspace.id);
    }
    workspace.selected = !workspace.selected;
  }

  popOutWorkspaces() {
    this.visible = false;
    this.workspaces.filter(ws => ws.selected).forEach(ws => {
      window.open('/workspace/' + ws.id);
      console.log('try to open', ws);
    });
  }

  async openWorkspaces() {
    let selectedItems =[ ...this.workspaces.filter(ws => ws.selected)];
    let workspaces = [];
    selectedItems.forEach(
      (SI) => {
        this.searchData(SI.id, SI.year).subscribe(
          (dt:any) => {
            let workspace = {
              uwYear: SI.id,
              workSpaceId: SI.year,
              cedantCode: dt.cedantCode,
              cedantName: dt.cedantName,
              ledgerName: dt.ledgerName,
              subsidiaryId: dt.subsidiaryId,
              subsidiaryName: dt.subsidiaryName,
              treatySections: dt.treatySections,
              workspaceName: dt.worspaceName,
              years: dt.years
            };
            workspaces = [workspace,...workspaces];
            if(workspaces.length === selectedItems.length){
              this._helperService.affectItems(workspaces)
              this.router.navigate(['/workspace']);
              this.visible = false;
            }
          }
        );
      }
    );
  }
}
