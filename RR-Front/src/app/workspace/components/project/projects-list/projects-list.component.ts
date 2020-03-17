import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';
import {Select, Store} from "@ngxs/store";
import {Router} from "@angular/router";
import {BaseContainer} from "../../../../shared/base";
import * as fromGeneralConfig from "../../../../core/store/actions";
import {GeneralConfigState} from "../../../../core/store/states";

@Component({
  selector: 'projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectsListComponent extends BaseContainer implements OnInit {

  @Input('projects') projects;
  @Input('status') status;

  @Output('select') selectEmitter;
  @Output('delete') deleteEmitter;
  @Output('edit') editEmitter;

  backgroundPalet = {
    "NEW": 'lightblue',
    "IN PROGRESS": 'linear-gradient(to right, rgb(222, 109, 130) 0%, rgb(228, 158, 92) 100%)',
    "Completed": 'linear-gradient(to top, rgb(113, 226, 116) 0%, rgb(0, 227, 174) 100%)',
    "SUPERSEDED": 'linear-gradient(#b7b7b7 0%, #c6c6c6 1%, rgb(195, 195, 195) 26%,' +
      ' rgb(191, 191, 191) 48%, rgb(201, 201, 201) 75%, rgb(165, 165, 165) 100%)',
    "Cancelled": 'linear-gradient(-225deg, rgb(158, 17, 96) 0%, rgb(117, 16, 91) 29%, rgb(151, 17, 96) 67%, rgb(163, 17, 96) 100%)',
    "Priced": 'linear-gradient(to top, rgb(0, 118, 66) 0%, rgb(26, 192, 139) 100%)'
  };

  @Select(GeneralConfigState.getAllUsers) users$;
  users: any[] = [];

  constructor(_baseStore: Store, _baseRouter: Router, _baseCdr: ChangeDetectorRef) {
    super(_baseRouter, _baseCdr, _baseStore);
    this.selectEmitter = new EventEmitter();
    this.deleteEmitter = new EventEmitter();
    this.editEmitter = new EventEmitter();
  }

  ngOnInit() {
    this.users$.pipe().subscribe(value => {
      this.users = value;
      this.detectChanges();
    });
  }

  select = (project) => this.selectEmitter.emit(project);
  delete = (project) => this.deleteEmitter.emit(project);
  edit = (project) => this.editEmitter.emit(project);

  setBackground(scope) {
    return _.get(this.backgroundPalet, `${scope}`, 'lightblue');
  }

  capitalize(string) {
    return _.capitalize(string);
  }

  getUserName(id) {
    const user = _.find(this.users, item => item.userId == id);
    return _.get(user, 'firstName', 'Unassigned') + ' ' + _.get(user, 'lastName', '');
  }

  getUserNameByUserId(id) {
    const user = _.find(this.users, item => item.userCode == id);
    return _.get(user, 'firstName', '') + ' ' + _.get(user, 'lastName', '');
  }
}
