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
    _tagModal: boolean;
    _modalSelect: [];
    _renamingTag: boolean;
    wsId: string;
    uwYear: string;
    projects: any[];
    showDeleted: boolean;
    filterData: any;
    filters: any;
    addTagModalIndex: number;
    fromPlts: boolean;
    deletedPltsLength: number;
    userTags: any[];
    selectedListOfPlts: any[];
    systemTagsCount: any;
    wsHeaderSelected: boolean;
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
  @Output() onRenameTag= new EventEmitter();
  @Output() onAssignPltsToTag= new EventEmitter();
  @Output() setTagModalVisibility= new EventEmitter();
  @Output() onSetTagForMenu= new EventEmitter();
  @Output() onSetRenameTag= new EventEmitter();
  @Output() unCkeckAllPlts= new EventEmitter();
  @Output() onWsHeaderSelection= new EventEmitter();
  @Output() onModalSelect= new EventEmitter();
  @Output() onAssignPltsToSingleTag= new EventEmitter();

  _modalInput: string;
  _renamingTag: boolean;
  _modalInputCache: string;
  colorPickerIsVisible: boolean;
  initColor: string;
  tagForMenu: any;

  perilColors = {
    'EQ': 'red',
    'FL': '#0b99cc',
    'WS': '#62ec07',
    'CS': '#62ec07'
  }

  constructor() {
    this.initColor= "blue"
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
    this.onToggleDeletedPlts.emit();
  }

  toggleModal(){
    this.tagModal( false);
    if(!this.menuInputs._tagModal){
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
    console.log(value);
    this.onModalSelect.emit(value);
  }

  renamingTag(value: boolean) {
    this.onSetRenameTag.emit(value)
  }

  initColorPicker(){
    this.colorPickerIsVisible = false;
    this.initColor = '#fe45cd'
  }

  toggleColorPicker(from?: string){
    if(from == 'color') {
      event.stopPropagation();
      event.preventDefault();
    }
    this.colorPickerIsVisible=!this.colorPickerIsVisible;
    if(!this.colorPickerIsVisible) this.initColor= '#fe45cd';
  }

  handlePopUpCancel() {
    console.log(this.menuInputs._renamingTag,this.menuInputs.fromPlts,this.menuInputs.addTagModalIndex)
    this.tagModal(false);
    this.modalInput('');
    this.modalSelect('');
    this.renamingTag(false);
  }

  handlePopUpConfirm() {
    if(this.menuInputs._renamingTag) {
      if(this._modalInput != this._modalInputCache){
        this.onRenameTag.emit({
          ...this.tagForMenu,
          tagName: this._modalInput
        })
      }
    }else {

      if(this.menuInputs.addTagModalIndex === 1 ){
<<<<<<< HEAD
        console.log(this.menuInputs._modalSelect)
=======
        console.log(this.menuInputs._modalSelect);
>>>>>>> fa02d59e6bdc1694449f665e76cb43571a2c4dc4
        this.onAssignPltsToTag.emit({
          plts: this.menuInputs.selectedListOfPlts,
          wsId: this.menuInputs.wsId,
          uwYear: this.menuInputs.uwYear,
          tags: this.menuInputs._modalSelect,
<<<<<<< HEAD
          type: 'assignAndRemove'
=======
          type: 'assignOrRemove'
>>>>>>> fa02d59e6bdc1694449f665e76cb43571a2c4dc4
        })
      }

      if(this.menuInputs.addTagModalIndex === 0) {
        this.onAssignPltsToSingleTag.emit({
          plts: this.menuInputs.fromPlts ? this.menuInputs.selectedListOfPlts : [],
          wsId: this.menuInputs.wsId,
          uwYear: this.menuInputs.uwYear,
          tag: {
            tagName: this._modalInput,
            tagColor: this.initColor
          }
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
<<<<<<< HEAD
        _.assign({}, this.menuInputs.filters, {[filter]: _.filter(this.menuInputs.filters[filter], e => e != tag.tagId)});

=======
        _.assign({}, this.menuInputs.filters, {[filter]: _.filter(this.menuInputs.filters[filter], e => e != tag.tagId)})
>>>>>>> fa02d59e6bdc1694449f665e76cb43571a2c4dc4
      this.onSetFilters.emit(filters);

      this.onSetSelectedUserTags.emit(_.map(this.menuInputs.userTags, t => t.tagId == tag.tagId ? {...t,selected: !t.selected} : t))

      this.emitFilters.emit(filters);
    }else{
      const {
        systemTag
      } = this.menuInputs.filters;

      this.onSetFilters.emit(_.findIndex(systemTag, sectionFilter => sectionFilter[tag] === section ) < 0 ?
          _.merge({},this.menuInputs.filters,{
            systemTag: _.merge([], systemTag, {
              [systemTag.length]: { [tag] : section }
            })
          }) : _.assign({}, this.menuInputs.filters,{ systemTag: _.filter( systemTag, sectionFilter => sectionFilter[tag] != section)})
      )
    }
  }

  selectSystemTag(section,tag) {
    this.onSelectSysTagCount.emit({
      section,
      tag
    })
  }

  setTagForMenu(any: any) {
    this.tagForMenu =any;
    this.onSetTagForMenu.emit(any);
  }

  tagModalIndexChange($event: number) {
    this.setTagModalIndex.emit($event);
  }

  getProjectID(projectId: string | string) {
    const str= _.split(projectId,'-');
    console.log(_.join([str[0],_.trimStart(str[1],'0')],'-'));
    return _.join([str[0],_.trimStart(str[1],'0')],'-')
  }
}
