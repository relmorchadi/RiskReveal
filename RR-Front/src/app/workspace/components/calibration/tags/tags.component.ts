import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SYSTEM_TAGS, USER_TAGS} from "../../../containers/workspace-calibration/data";
import {Store} from "@ngxs/store";
import {collapseTags} from "../../../store/actions";

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

  constructor(private store$: Store) {

    this.systemTags = SYSTEM_TAGS;

    this.userTags = USER_TAGS;

  }

  ngOnInit() {
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
