import {StorageEngine} from "@ngxs/storage-plugin";
import * as _ from "lodash";

export class MyStorageEngine implements StorageEngine {
  get length(): number {
    return localStorage.length;
  }

  getItem(key: string): any {
    if (key == '@@STATE') {
      let state = JSON.parse(localStorage.getItem(key));
      // console.info('[Storage Engine] state --> ', state);
      _.set(state,'searchBar.visible',false);
      _.set(state,'searchBar.visibleSearch',false);
      // console.info('[Storage Engine] state after --> ', state);
      return JSON.stringify(_.omit(state, ['router']));
    }
    return localStorage.getItem(key);
  }

  setItem(key: string, val: any): void {
    localStorage.setItem(key, val);
  }

  removeItem(key: string): void {
    localStorage.removeItem(key);
  }

  clear(): void {
    localStorage.clear();
  }

}
