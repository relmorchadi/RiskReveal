import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import * as leftMenuStore from './store';
import {Message} from "../../../message";

@Component({
  selector: 'app-plt-left-menu',
  templateUrl: './plt-left-menu.component.html',
  styleUrls: ['./plt-left-menu.component.scss']
})
export class PltLeftMenuComponent implements OnInit {
  @Input() leftMenuInputs: leftMenuStore.Input;

  @Output() actionDispatcher= new EventEmitter<Message>();


  perilColors = {
    'EQ': 'red',
    'FL': '#0b99cc',
    'WS': '#62ec07',
    'CS': '#62ec07'
  };
  drawer: boolean;

  constructor(private _fb: FormBuilder) {
    this.drawer= false;
  }

  ngOnInit() {

  }

  toDate(d) {
    return new Date(d);
  }

  selectWorkspace() {
    this.actionDispatcher.emit({
      type: 'filterByProject',
      payload: null
    })
  }

  selectProject(project) {
    this.actionDispatcher.emit({
      type: 'filterByProject',
      payload: project
    })
  }

  resetPath(){
    this.actionDispatcher.emit({
      type: leftMenuStore.resetPath,
      payload: false
    })
    this.actionDispatcher.emit({
      type: leftMenuStore.headerSelection,
      payload: true
    })
    this.actionDispatcher.emit({
      type: leftMenuStore.filterByProject,
      payload: _.omit(this.leftMenuInputs.filterData, ['projectId'])
    });

    this.actionDispatcher.emit({
      type: leftMenuStore.toggleDeletedPlts,
      payload: false
    });
  }

  filter(key, filterData, value){

    if (key == 'projectId') {

      this.actionDispatcher.emit({
        type: leftMenuStore.onSelectProjects,
        payload: value
      })

    }
  }

  toggleDeletedPlts() {
    this.actionDispatcher.emit({
      type: leftMenuStore.toggleDeletedPlts,
      payload: !this.leftMenuInputs.showDeleted
    });
  }

  resetFilterByTags() {
    this.actionDispatcher.emit({
      payload: {
        systemTag: [],
        userTag: []
      },
      type: leftMenuStore.setFilters
    });

    this.actionDispatcher.emit({
      payload: {
        systemTag: [],
        userTag: []
      },
      type: leftMenuStore.emitFilters
    });

    this.actionDispatcher.emit({
      type: leftMenuStore.onSetSelectedUserTags,
      payload: _.map(this.leftMenuInputs.userTags, t => ({...t, selected: false}))
    });
  }

  setFilter(filter: string, tag,section) {
    if(filter === 'userTag'){
      const filters = _.findIndex(this.leftMenuInputs.filters[filter], e => e == tag.tagId) < 0 ?
        _.merge({}, this.leftMenuInputs.filters, { [filter]: _.merge([], this.leftMenuInputs.filters[filter], {[this.leftMenuInputs.filters[filter].length] : tag.tagId} ) }) :
        _.assign({}, this.leftMenuInputs.filters, {[filter]: _.filter(this.leftMenuInputs.filters[filter], e => e != tag.tagId)});

      this.actionDispatcher.emit({
        payload: filters,
        type: leftMenuStore.setFilters
      });

      this.actionDispatcher.emit({
        type: leftMenuStore.onSetSelectedUserTags,
        payload: _.map(this.leftMenuInputs.userTags, t => t.tagId == tag.tagId ? {...t,selected: !t.selected} : t)
      });

      this.actionDispatcher.emit({
        payload: filters,
        type: leftMenuStore.emitFilters
      });
    }else{
      const {
        systemTag
      } = this.leftMenuInputs.filters;

      this.actionDispatcher.emit({
        payload: !systemTag[section] ?
          _.merge({}, this.leftMenuInputs.filters, {
            systemTag: _.merge({},systemTag, { [section]: [tag]})
          })
          :
          _.findIndex(systemTag[section], sysTagValue =>  sysTagValue == tag) < 0 ?
            _.merge({}, this.leftMenuInputs.filters, {
              systemTag: _.merge({},systemTag, { [section]: this.toggleArrayItem(tag, systemTag[section], (a,b) => a == b) })
            })
            :
            (
              systemTag[section].length == 1 ?
                _.assign({}, this.leftMenuInputs.filters, {
                  systemTag: _.omit(systemTag, `${section}`)
                })
                :
                _.assign({}, this.leftMenuInputs.filters, {
                  systemTag: _.assign({},systemTag, { [section]: this.toggleArrayItem(tag, systemTag[section], (a,b) => a == b) })
                })
            ),
        type: leftMenuStore.setFilters
      });

    }
  }

  toggleArrayItem(item,arr, compare){
    const i = _.findIndex(arr, e => compare(e,item));
    return i >= 0 ? [...arr.slice(0, i), ...arr.slice(i+1)] :  [...arr, item];
  }

  selectSystemTag(section,tag) {
    this.actionDispatcher.emit({
      type: leftMenuStore.onSelectSysTagCount,
      payload: {
        section,
        tag
      }
    });
  }

  getProjectID(projectId: string | string) {
    const str= _.split(projectId,'-');
    return _.join([str[0],_.trimStart(str[1],'0')],'-')
  }

  closeDrawer() {
    this.drawer= false;
  }

  openDrawer() {
    this.drawer= true;
  }

  detectParentChanges() {
    this.actionDispatcher.emit({
      type: 'Detect Parent Changes',
    })
  }
}
