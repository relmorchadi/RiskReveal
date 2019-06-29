import {Pipe, PipeTransform} from '@angular/core';
import * as _ from 'lodash';

@Pipe({
  name: 'systemTagFilter'
})
export class SystemTagFilterPipe implements PipeTransform {

  reverseSystemTagsMapping = {
    grouped: {
      'Peril': 'peril',
      'Region': 'regionDesc',
      'Currency': 'currency',
      'Modelling Vendor': 'sourceModellingVendor',
      'Modelling System': 'sourceModellingSystem',
      'Target RAP':'targetRapCode',
      'User Occurence Basis': 'userOccurrenceBasis',
      'Published To Pricing': 'xActAvailable',
      'Priced': 'xActUsed',
      'Accumulated': 'accumulated',
      'Financial Perspectives': 'financial'
    },
    nonGrouped: {
    }
  };

  transform(data: any, args?: any): any {

    const systemTag= args[0];

    const filterKeys= _.keys(systemTag);

    let dt= [];

    if(filterKeys.length > 0 ) {
      dt = _.map(data, plt => plt.visible ?
        (
          _.every(systemTag, (tags, section) => {
            const column= this.reverseSystemTagsMapping.grouped[section];
            return _.some(tags, tag => plt[column] == tag);
          })
            ?
            ({...plt, tagFilterActive: true})
            :
            ({...plt, tagFilterActive: false})
        )
          :
        ({...plt, tagFilterActive: false})
      )
    } else {
      dt = _.map(data, plt => ({...plt, tagFilterActive: false}));
    }
    return dt;
  }

}
