import {Injectable} from '@angular/core';
import { LocalStorage } from '@ngx-pwa/local-storage';

@Injectable({
  providedIn: 'root'
})
export class tagStorage {


  constructor(private ls: LocalStorage) {

  }

  get(key: string){
    return this.ls.getItem(key);
  }

  getUserTags(wsId: string, uwYear: string | number, userId: string) {
    this.get(wsId + "-" + uwYear + "-" + userId);
  }

  set(key: string, value: any) {
    return this.ls.setItem(key, value);
  }

  remove(key: string) {
    return this.ls.removeItem(key);
  }

  clear(){
    this.ls.clear();
  }

}
