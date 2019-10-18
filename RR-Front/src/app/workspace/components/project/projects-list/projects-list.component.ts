import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectsListComponent implements OnInit {

  @Input('projects') projects;

  @Output('select') selectEmitter;
  @Output('delete') deleteEmitter;

  constructor() {
    this.selectEmitter = new EventEmitter();
    this.deleteEmitter = new EventEmitter();
  }

  ngOnInit() {
  }

  select = (project) => this.selectEmitter.emit(project);
  delete = (project) => this.deleteEmitter.emit(project);


}
