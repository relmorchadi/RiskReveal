import {Component, EventEmitter, OnInit, Output, Input, TemplateRef} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';
import * as _ from 'lodash';

@Component({
  selector: 'renewal-contract-scope',
  templateUrl: './renewal-contract-scope.component.html',
  styleUrls: ['./renewal-contract-scope.component.scss']
})
export class RenewalContractScopeComponent implements OnInit {
  @Output('delete') delete: any = new EventEmitter<any>();
  @Output('duplicate') duplicate: any = new EventEmitter<any>();
  @Output('changeName') changeName: any = new EventEmitter<any>();
  private dropdown: NzDropdownContextComponent;
  renewalPeriod = '1';
  uwyUnits = '1';
  activeProject = '1';

  @Input()
  itemName = 'Renewal Contract Scope';
  @Input()
  dashboard: any;
  @Input()
  identifier: number;

  newDashboard: any;
  editName = false;

  cols = [
    {field: 'country', header: 'Country', width: '50px', display: true, sorted: false, type: 'text'},
    {field: 'cedant', header: 'Cedant', width: '100px', display: true, sorted: false, type: 'text'},
    {field: 'treaty', header: 'Treaty', width: '100px', display: true, sorted: false, type: 'text'},
    {field: 'year', header: 'Uw Year', width: '50px', display: true, sorted: false, type: 'text'},
    {field: 'pricingVersion', header: 'Pricing Version', width: '50px', display: true, sorted: false, type: 'text'},
    {field: 'rrStatus', header: 'RR Status', width: '50px', display: true, sorted: false, type: 'text'},
    {field: 'owner', header: 'Owner', width: '50px', display: true, sorted: false, type: 'text'},
    {field: 'date', header: 'Date', width: '50px', display: true, sorted: false, type: 'date'},
    {field: 'exposures', header: 'Exposures', width: '50px', display: true, sorted: false, type: 'text'},
    {field: 'published', header: 'Published to Pricing', width: '50px', display: true, sorted: false, type: 'text'},
    {field: 'used', header: 'Used in Pricing', width: '50px', display: true, sorted: false, type: 'text'},
    {field: 'accumulation', header: 'accumulation', width: '50px', display: true, sorted: false, type: 'text'},
  ];
  mockData = [];
/*    {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
      treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
    },
    {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
      treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
    },
    {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
      treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
    },
    {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
      treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date:'18/12/23'
    },
    {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
      treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
    },
    {year: 2019, country: 'Italy', cedant: '383477 Lorem Ipsum Colores',
      treaty: '334675 Donec et nulla', pricingVersion: 'Final', owner: 'Nathalie Dulac', date: '18/12/23'
    }*/

  constructor(private nzDropdownService: NzDropdownService) {
  }

  ngOnInit() {
    this.newDashboard = this.dashboard;
  }

  duplicateItem(itemName: any): void {
    this.duplicate.emit(itemName);
  }

  deleteItem(id): void {
    this.delete.emit(id);
  }

  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
    console.log(this.dropdown);
  }

  close(e: NzMenuItemDirective): void {
    this.dropdown.close();
  }

  validateName(keyboardMap, id) {

    if (keyboardMap.key === 'Enter') {
      this.changeName.emit({itemId: id, newName: keyboardMap.target.value});
      this.editName = false ;
    /*  const newItem = this.newDashboard.items.filter(ds => ds.id === id);
      const copy = Object.assign({}, newItem[0], {
        name: this.itemName,
        id: this.newDashboard.items.length + 1
      });
      this.newDashboard.items.push(copy);
      newItem[0].selected = false;
      this.editName = false;*/
    }
  }

  activeEdit() {
    this.editName = true;
  }
}
