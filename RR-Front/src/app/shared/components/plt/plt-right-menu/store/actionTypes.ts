import {Input} from './input';
import * as Types from './types';
import * as _ from 'lodash';

export const Actions = {
  updateKey: {
    type: Types.updateKey,
    handler: (rightMenuInput: Input, key, value): Input => ({
      ...rightMenuInput,
      [key]: value
    })
  },
  closeDrawer: {
    type: Types.closeDrawer,
    handler: (rightMenuInput: Input): Input => ({
      ...rightMenuInput,
      visible: false
    })
  }
  ,
  openDrawer: {
    type: Types.openDrawer,
    handler: (rightMenuInput: Input): Input => ({
      ...rightMenuInput,
      visible: true
    })
  },
  setSelectedTab: {
    type: Types.setSelectedTab,
    handler: (rightMenuInput: Input, tab: string) => {
      let index=0;

      _.forEach(_.keys(rightMenuInput.tabs), (el, i) => {
        if(el == tab) index= i;
      });
      return {
        ...rightMenuInput,
        selectedTab: {
          index,
          title: tab
        }
      }
    }
  },
  setSelectedTabByIndex: {
    type: Types.setSelectedTabByIndex,
    handler: (rightMenuInput: Input, index: number) => {
      let tab = "basket";

      _.forEach(_.keys(rightMenuInput.tabs), (el, i) => {
        if (i == index) tab = el;
      });

      return {
        ...rightMenuInput,
        selectedTab: {
          index,
          title: tab
        }
      }
    }
  }
}
