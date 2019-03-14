import {Component, EventEmitter, OnInit, Output, TemplateRef} from '@angular/core';
import {NzDropdownContextComponent, NzDropdownService, NzMenuItemDirective} from 'ng-zorro-antd';

@Component({
  selector: 'renewal-contract-scope',
  templateUrl: './renewal-contract-scope.component.html',
  styleUrls: ['./renewal-contract-scope.component.scss']
})
export class RenewalContractScopeComponent implements OnInit {
  @Output('delete') delete:any = new EventEmitter<any>();
  @Output('duplicate') duplicate:any = new EventEmitter<any>();
  private dropdown: NzDropdownContextComponent;
  renewalPeriod = '1';
  uwyUnits = '1';
  activeProject = '1';
  cols = [
    {field:'country',header:'Country'},
    {field:'cedent',header:'Cedent'},
    {field:'treaty',header:'Treaty'},
    {field:'year',header:'Uw Year'},
    {field:'pricingVersion',header:'Pricing Version'},
    {field:'rrStatus',header:'RR Status'},
    {field:'owner',header:'Owner'},
    {field:'date',header:'Date'},
    {field:'exposures',header:'Exposures'},
    {field:'published',header:'Published to Pricing'},
    {field:'used',header:'Used in Pricing'},
    {field:'accumulation',header:'accumulation'},
  ]
  mockData = [
    {year:2019,country:'Italy',cedent:'383477 Lorem Ipsum Colores',
      treaty:'334675 Donec et nulla',pricingVersion:'Final',owner:'Nathalie Dulac', date:'18/12/23'
    },
    {year:2019,country:'Italy',cedent:'383477 Lorem Ipsum Colores',
      treaty:'334675 Donec et nulla',pricingVersion:'Final',owner:'Nathalie Dulac', date:'18/12/23'
    },
    {year:2019,country:'Italy',cedent:'383477 Lorem Ipsum Colores',
      treaty:'334675 Donec et nulla',pricingVersion:'Final',owner:'Nathalie Dulac', date:'18/12/23'
    },
    {year:2019,country:'Italy',cedent:'383477 Lorem Ipsum Colores',
      treaty:'334675 Donec et nulla',pricingVersion:'Final',owner:'Nathalie Dulac', date:'18/12/23'
    },
    {year:2019,country:'Italy',cedent:'383477 Lorem Ipsum Colores',
      treaty:'334675 Donec et nulla',pricingVersion:'Final',owner:'Nathalie Dulac', date:'18/12/23'
    },
    {year:2019,country:'Italy',cedent:'383477 Lorem Ipsum Colores',
      treaty:'334675 Donec et nulla',pricingVersion:'Final',owner:'Nathalie Dulac', date:'18/12/23'
    }
  ]

  constructor(private nzDropdownService: NzDropdownService) {
  }

  ngOnInit() {
  }
  contextMenu($event: MouseEvent, template: TemplateRef<void>): void {
    this.dropdown = this.nzDropdownService.create($event, template);
    console.log(this.dropdown)
  }
  close(e: NzMenuItemDirective): void {

    this.dropdown.close();
  }

}
