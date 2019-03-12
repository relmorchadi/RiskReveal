import {Injectable} from '@angular/core';
import {BehaviorSubject, Subject,} from 'rxjs';

@Injectable({providedIn:'root'})
export class HelperService{
  constructor() {}

  collapseLeftMenu$:Subject<void> = new Subject<void>();
}
