import { Pipe, PipeTransform } from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'systemTagFilter'
})
export class SystemTagFilterPipe implements PipeTransform {

  reverseSystemTagsMapping = {
    grouped: {
      'Peril': 'peril',
      'Region': 'regionPerilCode',
      'Currency': 'currency',
      'Modelling Vendor': 'sourceModellingVendor',
      'Model System': 'sourceModellingSystem',
      'Target RAP':'targetRapCode',
      'User Occurence Basis': 'userOccurrenceBasis',
      'Loss Asset Type': 'pltType',
    },
    nonGrouped: {
    }
  };

  transform(data: any, args?: any): any {

    const systemTag= args[0];

    return systemTag.length > 0 ? _.map(data, plt => plt.visible ?
      (_.some(systemTag, (systemTag) => {
        const key = _.keys(systemTag)[0];
        return plt[this.reverseSystemTagsMapping.grouped[systemTag[key]]] === key
      }) ? ({...plt, tagFilterActive: true}): ({...plt, tagFilterActive: false}))
      : ({...plt, tagFilterActive: false})
    ) : _.map(data, plt => ({...plt, tagFilterActive: false}));
  }

}
