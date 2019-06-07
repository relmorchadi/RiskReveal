import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss']
})
export class TagsComponent implements OnInit {

  @Output() notify: EventEmitter<any> = new EventEmitter<any>();
  ColpasBool: boolean = true;
  extended: boolean = false;
  pltSpan: number = 7;
  templateSpan: number = 14;
  systemTags = [];
  userTags = [];
  currentSystemTag = null;
  currentUserTag = null;

  constructor() {

    this.systemTags = [
      {
        tagId: '1',
        tagName: 'TC',
        tagColor: '#7bbe31',
        innerTagContent: '1',
        innerTagColor: '#a2d16f',
        selected: false
      },
      {
        tagId: '2',
        tagName: 'NATC-USM',
        tagColor: '#7bbe31',
        innerTagContent: '2',
        innerTagColor: '#a2d16f',
        selected: false
      },
      {
        tagId: '3',
        tagName: 'Post-Inured',
        tagColor: '#006249',
        innerTagContent: '9',
        innerTagColor: '#4d917f',
        selected: false
      },
      {
        tagId: '4',
        tagName: 'Pricing',
        tagColor: '#009575',
        innerTagContent: '0',
        innerTagColor: '#4db59e',
        selected: false
      },
      {
        tagId: '5',
        tagName: 'Accumulation',
        tagColor: '#009575',
        innerTagContent: '2',
        innerTagColor: '#4db59e',
        selected: false
      },
      {
        tagId: '6',
        tagName: 'Default',
        tagColor: '#06b8ff',
        innerTagContent: '1',
        innerTagColor: '#51cdff',
        selected: false
      },
      {
        tagId: '7',
        tagName: 'Non-Default',
        tagColor: '#f5a623',
        innerTagContent: '0',
        innerTagColor: '#f8c065',
        selected: false
      },
    ];

    this.userTags = [
      {
        tagId: '1',
        tagName: 'Pricing V1',
        tagColor: '#893eff',
        innerTagContent: '1',
        innerTagColor: '#ac78ff',
        selected: false
      },
      {
        tagId: '2',
        tagName: 'Pricing V2',
        tagColor: '#06b8ff',
        innerTagContent: '2',
        innerTagColor: '#51cdff',
        selected: false
      },
      {
        tagId: '3',
        tagName: 'Final Princing',
        tagColor: '#c38fff',
        innerTagContent: '5',
        innerTagColor: '#d5b0ff',
        selected: false
      }
    ];

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
        this.pltSpan = 9;
        this.templateSpan = 14;
      }
    }

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
