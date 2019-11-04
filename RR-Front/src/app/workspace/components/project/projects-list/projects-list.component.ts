import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';

@Component({
  selector: 'projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectsListComponent implements OnInit {

  @Input('projects') projects;

  @Output('select') selectEmitter;
  @Output('delete') deleteEmitter;

  backgroundPalet = {
    "New": 'lightblue',
    "In Progress": 'linear-gradient(to right, rgb(222, 109, 130) 0%, rgb(228, 158, 92) 100%)',
    "Completed": 'linear-gradient(to top, rgb(113, 226, 116) 0%, rgb(0, 227, 174) 100%)',
    "Superseded": 'linear-gradient(to bottom, lightgrey 0%, lightgrey 1%, rgb(224, 224, 224) 26%,' +
      ' rgb(211, 211, 211) 48%, rgb(217, 217, 217) 75%, rgb(188, 188, 188) 100%)',
    "Canceled": 'linear-gradient(-225deg, rgb(158, 17, 96) 0%, rgb(117, 16, 91) 29%, rgb(151, 17, 96) 67%, rgb(163, 17, 96) 100%)'
  };

  constructor() {
    this.selectEmitter = new EventEmitter();
    this.deleteEmitter = new EventEmitter();
  }

  ngOnInit() {
  }

  select = (project) => this.selectEmitter.emit(project);
  delete = (project) => this.deleteEmitter.emit(project);

  setBackground(scope) {
    return _.get(this.backgroundPalet, `${scope}`, 'lightblue');
  }

}
