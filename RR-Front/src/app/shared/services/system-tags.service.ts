import {Injectable} from '@angular/core';
import * as _ from 'lodash';

@Injectable({
  providedIn: 'root'
})
export class SystemTagsService {

  systemTagsMapping = {
    grouped: {
      peril: 'Peril',
      regionDesc: 'Region',
      currency: 'Currency',
      sourceModellingVendor: 'Modelling Vendor',
      sourceModellingSystem: 'Modelling System',
      targetRapCode: 'Target RAP',
      userOccurrenceBasis: 'User Occurence Basis',
      xActAvailable: 'Published To Pricing',
      xActUsed: 'Priced',
      accumulated: 'Accumulated',
      financial: 'Financial Perspectives'
    },
    nonGrouped: {}
  };

  constructor() {
  }

  countSystemTags(data) {
    let newSysTagCount = {};

    _.forEach(data, (v, k) => {
      //Init Tags Counters

      //Grouped Sys Tags
      _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
        newSysTagCount[sectionName] = newSysTagCount[sectionName] || {};
        const tag = _.toString(v[section]);
        if (tag) {
          newSysTagCount[sectionName][tag] = {selected: false, count: 0, max: 0}
        }
      });

      //NONE grouped Sys Tags
      _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
        newSysTagCount[sectionName] = newSysTagCount[sectionName] || {};
        newSysTagCount[sectionName][section] = {selected: false, count: 0};
        newSysTagCount[sectionName]['non-' + section] = {selected: false, count: 0, max: 0};
      });

    });

    _.forEach(data, (v, k) => {

      /*if (v.visible) {*/
      //Grouped Sys Tags
      _.forEach(this.systemTagsMapping.grouped, (sectionName, section) => {
        const tag = _.toString(v[section]);
        if (tag) {
          if (newSysTagCount[sectionName][tag] || newSysTagCount[sectionName][tag].count === 0) {
            const {
              count,
              max
            } = newSysTagCount[sectionName][tag];

            newSysTagCount[sectionName][tag] = {
              ...newSysTagCount[sectionName][tag],
              count: v.visible ? count + 1 : count,
              max: max + 1
            };
          }
        }
      });

      //NONE grouped Sys Tags
      _.forEach(this.systemTagsMapping.nonGrouped, (section, sectionName) => {
        const tag = v[section];
        if (newSysTagCount[sectionName][section] || newSysTagCount[sectionName][section] == 0) {
          const {
            max,
            count
          } = newSysTagCount[sectionName][section];
          newSysTagCount[sectionName][section] = {
            ...newSysTagCount[sectionName][section],
            count: v.visible ? count + 1 : count,
            max: max + 1
          };
        }
        if (newSysTagCount[sectionName]['non-' + section] || newSysTagCount[sectionName]['non-' + section].count == 0) {
          const {
            count,
            max
          } = newSysTagCount[sectionName]['non-' + section];
          newSysTagCount[sectionName]['non-' + section] = {
            ...newSysTagCount[sectionName]['non-' + section],
            count: v.visible ? count + 1 : count,
            max: max + 1
          };
        }
      });
      /*}*/

    });

    return newSysTagCount;
  }
}
