import { openDb } from "idb";
import * as _ from "lodash"
import {from, Observable} from "rxjs";
import {Injectable} from "@angular/core";

const appConfig: any = {
  databaseName: "RR",
  dbVersion: 1,
  tables: ['selectionId']
};

@Injectable()
export class ReactiveIndexedDbService {

  protected static _dbInstance: any = openDb(appConfig.databaseName, appConfig.dbVersion, (db) =>{
    _.forEach(appConfig.tables,(table)=> db.createObjectStore(table))
  });


  public static get(table: string, primaryKey: string | number): Observable<any> {
    return from((async ():Promise<any> => {
      return (await ReactiveIndexedDbService._dbInstance).transaction(table,'readwrite').objectStore(table).get(primaryKey);
    })())
  }
  public static getAll(table: string): Observable<any> {
    return from((async ():Promise<any> => {
      return (await ReactiveIndexedDbService._dbInstance).transaction(table,'readwrite').objectStore(table).getAll();
    })())
  }
  public static put(table: string, primaryKey: string | number, value: any): Observable<any> {
    return from((async ():Promise<any> => {
      return (await ReactiveIndexedDbService._dbInstance).transaction(table,'readwrite').objectStore(table).put( value, primaryKey);
    })())
  }

  public static delete(table: string, primaryKey: string | number) {
    return from((async ():Promise<any> => {
      return (await ReactiveIndexedDbService._dbInstance).transaction(table,'readwrite').objectStore(table).delete(primaryKey);
    })())
  }

  public static keys(table: string): Observable<any> {
    return from((async ():Promise<any> => {
      return (await ReactiveIndexedDbService._dbInstance).transaction(table,'readwrite').objectStore(table).getAllKeys();
    })())
  }

  public static drop(table: string): Observable<any> {
    return from((async ():Promise<any> => {
      return (await ReactiveIndexedDbService._dbInstance).transaction(table,'readwrite').objectStore(table).clear();
    })())
  }

}
