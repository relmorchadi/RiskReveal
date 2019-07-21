import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as _ from 'lodash';

@Component({
  selector: 'app-plt-left-menu',
  templateUrl: './plt-left-menu.component.html',
  styleUrls: ['./plt-left-menu.component.scss']
})
export class PltLeftMenuComponent implements OnInit {

  @Input() menuInputs: {
    tagContextMenu: any;
    _tagModalVisible: boolean;
    _modalSelect: [];
    tagForMenu: any,
    _editingTag: boolean;
    wsId: string;
    uwYear: string;
    projects: any[];
    showDeleted: boolean;
    filterData: any;
    filters: {
      systemTag: any,
      userTag: any[]
    };
    addTagModalIndex: number;
    fromPlts: boolean;
    deletedPltsLength: number;
    userTags: any[];
    selectedListOfPlts: any[];
    systemTagsCount: any;
    wsHeaderSelected: boolean;
    pathTab: boolean;
    selectedItemForMenu: any;
  }

  @Output() onResetPath= new EventEmitter();
  @Output() onToggleDeletedPlts= new EventEmitter();
  @Output() onProjectFilter= new EventEmitter();
  @Output() onSelectProjects= new EventEmitter();
  @Output() setTagModalIndex= new EventEmitter();
  @Output() onSetFromPlts= new EventEmitter();
  @Output() onSetFilters= new EventEmitter();
  @Output() emitFilters= new EventEmitter();
  @Output() onSetSelectedUserTags= new EventEmitter();
  @Output() onSelectSysTagCount= new EventEmitter();
  @Output() onEditTag= new EventEmitter();
  @Output() onAssignPltsToTag= new EventEmitter();
  @Output() onCreateTag= new EventEmitter();
  @Output() setTagModalVisibility= new EventEmitter();
  @Output() onSetTagForMenu= new EventEmitter();
  @Output() onSetRenameTag= new EventEmitter();
  @Output() unCkeckAllPlts= new EventEmitter();
  @Output() onWsHeaderSelection= new EventEmitter();
  @Output() onModalSelect= new EventEmitter();
  @Output() emitModalInputValue= new EventEmitter();

  _modalInput: string;
  colorPickerIsVisible: boolean;

  perilColors = {
    'EQ': 'red',
    'FL': '#0b99cc',
    'WS': '#62ec07',
    'CS': '#62ec07'
  }
  presetColors: string[]= ['#0700CF', '#ef5350', '#d81b60', '#6a1b9a', '#880e4f', '#64ffda', '#00c853', '#546e7a'];

  constructor() {

  }

  ngOnInit() {
  }

  toDate(d) {
    return new Date(d);
  }

  resetPath(){
    this.onResetPath.emit(false);
    this.onWsHeaderSelection.emit(true)
    this.onProjectFilter.emit(_.omit(this.menuInputs.filterData, ['project']))
    this.onToggleDeletedPlts.emit(false);
  }

  filter(key, filterData, value){
    if (key == 'project') {
      this.unCkeckAllPlts.emit();
      if (this.menuInputs.filterData['project'] && this.menuInputs.filterData['project'] != '' && value == this.menuInputs.filterData['project']) {
        this.onWsHeaderSelection.emit(true);
        this.onProjectFilter.emit(_.omit(this.menuInputs.filterData, [key]))
      } else {
        this.onWsHeaderSelection.emit(false);
        this.onProjectFilter.emit(_.merge({}, this.menuInputs.filterData, {[key]: value}))
      }
      this.onSelectProjects.emit(_.map(this.menuInputs.projects, t => {
        if(t.projectId == value){
          return ({...t,selected: !t.selected})
        }else if(t.selected) {
          return ({...t,selected: false})
        }else return t;
      }))
    }
  }

  toggleDeletedPlts() {
    this.onToggleDeletedPlts.emit(!this.menuInputs.showDeleted);
  }

  toggleModal(){
    this.tagModal( false);
    if(!this.menuInputs._tagModalVisible){
      this.modalInput(null);
      this.modalSelect(null);
      this.renamingTag(false);
      this.setTagModalIndex.emit(0);
    }
  }

  tagModal(value: boolean) {
    this.setTagModalVisibility.emit(value);
  }

  modalInput(value: string) {
    this._modalInput = value;
  }

  modalSelect(value: any) {
    this.onModalSelect.emit(value);
  }

  renamingTag(value: boolean) {
    this.onSetRenameTag.emit(value)
  }

  initColorPicker(){
    this.colorPickerIsVisible = false;
    this.emitTagValues('tagColor','#fe45cd')
  }

  toggleColorPicker(from?: string){
    if(from == 'color') {
      event.stopPropagation();
      event.preventDefault();
    }
    this.colorPickerIsVisible=!this.colorPickerIsVisible;
    if(!this.colorPickerIsVisible) this.emitTagValues('tagColor','#fe45cd');
  }

  handlePopUpCancel() {
    this.tagModal(false);
    this.modalInput('');
    this.modalSelect('');
    this.renamingTag(false);
  }

  handlePopUpConfirm() {
    if(this.menuInputs._editingTag) {
        this.onEditTag.emit()
    }else {

      if(this.menuInputs.addTagModalIndex === 1 ){
        this.onAssignPltsToTag.emit({
          plts: this.menuInputs.selectedListOfPlts.length > 0 ? this.menuInputs.selectedListOfPlts : [this.menuInputs.selectedItemForMenu] ,
          wsId: this.menuInputs.wsId,
          uwYear: this.menuInputs.uwYear,
          selectedTags: this.menuInputs._modalSelect
        })
      }

      if(this.menuInputs.addTagModalIndex === 0) {
        this.onCreateTag.emit({
          plts: this.menuInputs.fromPlts ? (this.menuInputs.selectedListOfPlts.length > 0 ? this.menuInputs.selectedListOfPlts : [this.menuInputs.selectedItemForMenu]) : [],
          wsId: this.menuInputs.wsId,
          uwYear: this.menuInputs.uwYear,
          tag: _.omit(this.menuInputs.tagForMenu, 'tagId')
        });
      }

    }

    this.toggleModal();
  }

  setFromPlts(b: boolean) {
    this.onSetFromPlts.emit(b)
  }

  resetFilterByTags() {
    this.onSetFilters.emit( {
        systemTag: [],
        userTag: []
      });
    this.emitFilters.emit({
      systemTag: [],
      userTag: []
    });
    this.onSetSelectedUserTags.emit(_.map(this.menuInputs.userTags, t => ({...t, selected: false})));
  }

  setFilter(filter: string, tag,section) {
    if(filter === 'userTag'){
      const filters = _.findIndex(this.menuInputs.filters[filter], e => e == tag.tagId) < 0 ?
        _.merge({}, this.menuInputs.filters, { [filter]: _.merge([], this.menuInputs.filters[filter], {[this.menuInputs.filters[filter].length] : tag.tagId} ) }) :
        _.assign({}, this.menuInputs.filters, {[filter]: _.filter(this.menuInputs.filters[filter], e => e != tag.tagId)});

      this.onSetFilters.emit(filters);

      this.onSetSelectedUserTags.emit(_.map(this.menuInputs.userTags, t => t.tagId == tag.tagId ? {...t,selected: !t.selected} : t))

      this.emitFilters.emit(filters);
    }else{
      const {
        systemTag
      } = this.menuInputs.filters;

      this.onSetFilters.emit(
        !systemTag[section] ?
          _.merge({}, this.menuInputs.filters, {
            systemTag: _.merge({},systemTag, { [section]: [tag]})
          })
          :
          _.findIndex(systemTag[section], sysTagValue =>  sysTagValue == tag) < 0 ?
            _.merge({}, this.menuInputs.filters, {
              systemTag: _.merge({},systemTag, { [section]: this.toggleArrayItem(tag, systemTag[section], (a,b) => a == b) })
            })
            :
            (
              systemTag[section].length == 1 ?
                _.assign({}, this.menuInputs.filters, {
                  systemTag: _.omit(systemTag, `${section}`)
                })
                :
                _.assign({}, this.menuInputs.filters, {
                  systemTag: _.assign({},systemTag, { [section]: this.toggleArrayItem(tag, systemTag[section], (a,b) => a == b) })
                })
            )
      )

    }
  }

  toggleArrayItem(item,arr, compare){
    const i = _.findIndex(arr, e => compare(e,item));
    return i >= 0 ? [...arr.slice(0, i), ...arr.slice(i+1)] :  [...arr, item];
  }

  selectSystemTag(section,tag) {
    this.onSelectSysTagCount.emit({
      section,
      tag
    })
  }

  setTagForMenu(any: any) {
    this.onSetTagForMenu.emit(_.pick(any, ['tagName','tagColor','tagId']));
  }

  tagModalIndexChange($event: number) {
    this.setTagModalIndex.emit($event);
  }

  getProjectID(projectId: string | string) {
    const str= _.split(projectId,'-');
    return _.join([str[0],_.trimStart(str[1],'0')],'-')
  }

  emitTagValues(key, value) {
    this.onSetTagForMenu.emit({
      ...this.menuInputs.tagForMenu,
      [key]: value
    })
  }
}
