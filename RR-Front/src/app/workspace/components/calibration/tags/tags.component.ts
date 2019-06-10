import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SYSTEM_TAGS, USER_TAGS} from "../../../containers/workspace-calibration/data";
import {Select, Store} from "@ngxs/store";
import {collapseTags} from "../../../store/actions";
import {CalibrationState} from "../../../store/states";
import {Observable} from "rxjs";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss']
})
export class TagsComponent implements OnInit {

  @Output() notify: EventEmitter<any> = new EventEmitter<any>();
  @Output() collapse: EventEmitter<any> = new EventEmitter<any>();
  ColpasBool: boolean = true;
  @Input() extended;
  pltSpan: number = 7;
  templateSpan: number = 14;
  systemTags = [];
  userTags = [];
  currentSystemTag = null;
  currentUserTag = null;
  @Select(CalibrationState) state$: Observable<any>;

  constructor(private store$: Store) {

    this.systemTags = SYSTEM_TAGS;

    this.userTags = USER_TAGS;

  }

  ngOnInit() {
    this.state$.pipe(take(1)).subscribe((data: any) => {
      if (!data.collapseTags) {
        this.Colpa();
      }
    });
  }

  Colpa() {
    this.ColpasBool = !this.ColpasBool;
    if (this.ColpasBool == true) {
      if (this.extended) {
        this.pltSpan = 14;
        this.templateSpan = 7;
      } else {
        this.pltSpan = 7;
        this.templateSpan = 14;
      }
    } else {
      if (this.extended) {
        this.pltSpan = 14;
        this.templateSpan = 9;
      } else {
        this.pltSpan = 8;
        this.templateSpan = 15;
      }
    }
    console.log('tags collapse', this.ColpasBool);
    this.store$.dispatch(new collapseTags(this.ColpasBool));
    this.collapse.emit({templateSpan: this.templateSpan, pltSpan: this.pltSpan});

  }

  changeFilter(tag, type) {
    this.currentSystemTag = tag;
    this.notify.emit({tag: tag, type: type});
  }

  resetFilterByTags() {
    this.currentUserTag = null;
    this.currentSystemTag = null;
    this.notify.emit({tag: null, type: 'system'});
    this.notify.emit({tag: null, type: 'user'});
  }
}
