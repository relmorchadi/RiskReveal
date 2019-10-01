import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import * as leftMenuStore from './store';
import {Message} from "../../../message";

@Component({
  selector: 'app-plt-left-menu',
  templateUrl: './plt-left-menu.component.html',
  styleUrls: ['./plt-left-menu.component.scss']
})
export class PltLeftMenuComponent implements OnInit {
  @Input() tagsInput: leftMenuStore.Input;

  @Output() actionDispatcher= new EventEmitter<Message>();

  _modalInput: string;

  perilColors = {
    'EQ': 'red',
    'FL': '#0b99cc',
    'WS': '#62ec07',
    'CS': '#62ec07'
  };
  presetColors: string[] = ['#0700CF', '#ef5350', '#d81b60', '#6a1b9a', '#880e4f', '#64ffda', '#00c853', '#546e7a'];

  tagForm: FormGroup;
  drawer: boolean;

  constructor(private _fb: FormBuilder) {
    this.drawer= false;
  }

  get title() {
    return this.tagForm.get('tagName');
  }

  get color() {
    return this.tagForm.get('tagColor');
  }

  get visible() {
    return this.tagForm.get('visible');
  }

  ngOnInit() {
    this.initTagForm();
  }

  initTagForm() {
    this.tagForm = this._fb.group({
      tagId: [null],
      tagName: ['', Validators.required],
      tagColor: ['#ae1675'],
      userId: [17, Validators.required],
      editorId: [''],
      visible: [false]
    })
  }

  toDate(d) {
    return new Date(d);
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
      payload: _.omit(this.tagsInput.filterData, ['project'])
    });

    this.actionDispatcher.emit({
      type: leftMenuStore.toggleDeletedPlts,
      payload: false
    });
  }

  filter(key, filterData, value){
    if (key == 'project') {
      this.actionDispatcher.emit({
        type: leftMenuStore.unCkeckAllPlts
      });
      if (this.tagsInput.filterData['project'] && this.tagsInput.filterData['project'] != '' && value == this.tagsInput.filterData['project']) {
        this.actionDispatcher.emit({
          type: leftMenuStore.headerSelection,
          payload: true
        })
        this.actionDispatcher.emit({
          type: leftMenuStore.filterByProject,
          payload: _.omit(this.tagsInput.filterData, [key])
        });
      } else {
        this.actionDispatcher.emit({
          type: leftMenuStore.headerSelection,
          payload: false
        });
        this.actionDispatcher.emit({
          type: leftMenuStore.filterByProject,
          payload: _.merge({}, this.tagsInput.filterData, {[key]: value})
        });
      }
      this.actionDispatcher.emit({
        type: leftMenuStore.onSelectProjects,
        payload: _.map(this.tagsInput.projects, t => {
          if(t.projectId == value){
            return ({...t,selected: !t.selected})
          }else if(t.selected) {
            return ({...t,selected: false})
          }else return t;
        })
      })
    }
  }

  toggleDeletedPlts() {
    this.actionDispatcher.emit({
      type: leftMenuStore.toggleDeletedPlts,
      payload: !this.tagsInput.showDeleted
    });
  }

  tagModal(value: boolean) {
    this.actionDispatcher.emit({
      type: leftMenuStore.setTagModalVisibility,
      payload: value
    });
  }

  modalInput(value: string) {
    this._modalInput = value;
  }

  toggleColorPicker(i: number) {
    event.stopPropagation();
    event.preventDefault();
    this.tagForm.patchValue({visible: !this.visible.value})
  }

  closeColorPicker() {
    event.stopPropagation();
    event.preventDefault();
    this.tagForm.patchValue({visible: false});
  }

  handlePopUpCancel() {
    this.tagModal(false);
    this.modalInput('');
  }

  handlePopUpConfirm() {
    /*if(this.tagsInput._editingTag) {
        this.onEditTag.emit()
    }else {

      if(this.tagsInput.addTagModalIndex === 1 ){
        this.onAssignPltsToTag.emit({
          plts: _.map(this.tagsInput.selectedListOfPlts.length > 0 ? this.tagsInput.selectedListOfPlts : [this.tagsInput.selectedItemForMenu], plt => plt.pltId),
          wsId: this.tagsInput.wsId,
          uwYear: this.tagsInput.uwYear,
          selectedTags: this.tagsInput._modalSelect
        })
      }

      if(this.tagsInput.addTagModalIndex === 0) {
        this.onCreateTag.emit({
          plts: this.tagsInput.fromPlts ? _.map((this.tagsInput.selectedListOfPlts.length > 0 ? this.tagsInput.selectedListOfPlts : [this.tagsInput.selectedItemForMenu]), plt => plt.pltId) : [],
          wsId: this.tagsInput.wsId,
          uwYear: this.tagsInput.uwYear,
          tag: _.omit(this.tagsInput.tagForMenu, 'tagId')
        });
      }

    }

    this.toggleModal();*/
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
      payload: _.map(this.tagsInput.userTags, t => ({...t, selected: false}))
    });
  }

  setFilter(filter: string, tag,section) {
    if(filter === 'userTag'){
      const filters = _.findIndex(this.tagsInput.filters[filter], e => e == tag.tagId) < 0 ?
        _.merge({}, this.tagsInput.filters, {[filter]: _.merge([], this.tagsInput.filters[filter], {[this.tagsInput.filters[filter].length]: tag.tagId})}) :
        _.assign({}, this.tagsInput.filters, {[filter]: _.filter(this.tagsInput.filters[filter], e => e != tag.tagId)});

      this.actionDispatcher.emit({
        payload: filters,
        type: leftMenuStore.setFilters
      });

      this.actionDispatcher.emit({
        type: leftMenuStore.onSetSelectedUserTags,
        payload: _.map(this.tagsInput.userTags, t => t.tagId == tag.tagId ? {...t, selected: !t.selected} : t)
      });

      this.actionDispatcher.emit({
        payload: filters,
        type: leftMenuStore.emitFilters
      });
    }else{
      const {
        systemTag
      } = this.tagsInput.filters;

      this.actionDispatcher.emit({
        payload: !systemTag[section] ?
          _.merge({}, this.tagsInput.filters, {
            systemTag: _.merge({},systemTag, { [section]: [tag]})
          })
          :
          _.findIndex(systemTag[section], sysTagValue =>  sysTagValue == tag) < 0 ?
            _.merge({}, this.tagsInput.filters, {
              systemTag: _.merge({},systemTag, { [section]: this.toggleArrayItem(tag, systemTag[section], (a,b) => a == b) })
            })
            :
            (
              systemTag[section].length == 1 ?
                _.assign({}, this.tagsInput.filters, {
                  systemTag: _.omit(systemTag, `${section}`)
                })
                :
                _.assign({}, this.tagsInput.filters, {
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

  onEnter(i: number, event: KeyboardEvent) {
    /*if (event.key === "Enter" && (this.tagArray.controls[i] as FormGroup).controls.title.valid) {
      this.usedInWs.push({
        tagName: (this.tagArray.controls[i] as FormGroup).controls.title.value,
        tagColor: (this.tagArray.controls[i] as FormGroup).controls.color.value,
        count: 0,
        selected: false
      });
      this.tagArray.removeAt(i);
    }*/
  }

  closeDrawer() {
    this.drawer= false;
  }

  openDrawer() {
    this.drawer= true;
  }

  addNewTag() {
    if (this.tagForm.valid) this.actionDispatcher.emit({
      type: leftMenuStore.addNewTag,
      payload: _.omit(this.tagForm.value, ['visible', 'editorId', 'tagId'])
    })
  }

  deleteTag() {
    if (this.tagForm.get('tagId')) this.actionDispatcher.emit({
      type: leftMenuStore.deleteTag,
      payload: _.omit(this.tagForm.value, ['tagName', 'tagColor', 'visible'])
    })
  }

  /*assignTag(tag: any) {
    event.preventDefault();
    event.stopPropagation();
    this.actionDispatcher.emit({
      type: leftMenuStore.assignTag,
      payload: tag
    })
  }

  deassignTag(index: number) {
    event.preventDefault();
    event.stopPropagation();
    this.actionDispatcher.emit({
      type: leftMenuStore.deassignTag,
      payload: index
    })
  }

  toggleAssignedTag(i: number) {
    event.preventDefault();
    event.stopPropagation();
    this.actionDispatcher.emit({
      type: leftMenuStore.toggleAssignedTag,
      payload: i
    })
  }*/

  clearSelection() {
    this.actionDispatcher.emit({
      type: leftMenuStore.clearSelection,
    })
  }

  confirmSelection() {
    this.actionDispatcher.emit({
      type: leftMenuStore.confirmSelection,
    })
  }

  toggleTag(i: number, operation: string, source: string) {
    this.actionDispatcher.emit({
      type: leftMenuStore.toggleTag,
      payload: {
        i,
        operation,
        source
      }
    })
  }

  save() {
    this.actionDispatcher.emit({
      type: leftMenuStore.save
    })
  }

  reset() {
    this.initTagForm();
  }
}
