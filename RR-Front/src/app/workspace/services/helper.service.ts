import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable()
export class HelperService {

  public scrollSubject: Subject<{ topic: string, scroll: number }> = new Subject();

  constructor() {
  }

}
