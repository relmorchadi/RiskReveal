import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'contractFilter'
})
export class ContractFilterPipe implements PipeTransform {

  transform(value: any, filter?: any): any {
    return value.filter(item => this._filterItem(item,filter) );
  }

  private _filterItem(item, {cedantCode,cedant, country, treaty, workspaceId, workspaceName, year}):boolean{
    return cedantCode ? (item.cedantCode||'').match(new RegExp(cedantCode, 'i')) : true &&
      cedant ? (item.cedantName||'').match(new RegExp(cedant,'i')) : true &&
      country ? (item.countryName||'').match(new RegExp(country,'i')) : true &&
      // treaty ? item.treatyName.match(new RegExp(treaty,'i')) : true &&
      workspaceId ? (item.workSpaceId||'').match(new RegExp(workspaceId,'i')) : true &&
      workspaceName ? (item.workspaceName||'').match(new RegExp(workspaceName,'i')) : true &&
      year ? (String(item.uwYear)||'').match(new RegExp(year, 'i')) : true;
  }


}
