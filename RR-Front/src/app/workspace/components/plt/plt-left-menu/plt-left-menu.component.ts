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
    systemTags: any[];
    selectedListOfPlts: any[];
    systemTagsCount: any;
  }
  
  @Output() onResetPath= new EventEmitter();
  @Output() onToggleDeletedPlts= new EventEmitter();
  @Output() onProjectFilter= new EventEmitter();
  @Output() onSelectProjects= new EventEmitter();
  @Output() setTagModalIndex= new EventEmitter();
  @Output() onSetFromPlts= new EventEmitter();
  @Output() onSetFilters= new EventEmitter();
  @Output() onSetSelectedUserTags= new EventEmitter();
  @Output() onSetSelectedSystemTags= new EventEmitter();
  @Output() onSelectSysTagCount= new EventEmitter();
  @Output() onRenameTag= new EventEmitter();
  @Output() onAssignPltsToTag= new EventEmitter();

  private _tagModal: boolean;
  private _modalInput: string;
  private _modalSelect: any;
  private _renamingTag: boolean;
  private _modalInputCache: string;
  private colorPickerIsVisible: boolean;
  private initColor: string;
  private tagForMenu: any;

  constructor() {

  }

  ngOnInit() {
  }

  toDate(d) {
    return new Date(d);
  }

  resetPath(){
    this.onResetPath.emit(false);
  }
  
  filter(key, filterData, value){
    if (key == 'project') {
      if (this.menuInputs.filterData['project'] && this.menuInputs.filterData['project'] != '' && value == this.menuInputs.filterData['project']) {
        this.onProjectFilter.emit(_.omit(this.menuInputs.filterData, [key]))
      } else {
        this.onProjectFilter.emit(_.merge({}, this.menuInputs.filterData, {[key]: value}))
      }
      this.onSelectProjects.emit(_.map(this.menuInputs.projects, t => {
        if(t.value == value){
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
    if(!this._tagModal){
      this.modalInput(null);
      this.modalSelect(null);
      this.renamingTag(false);
      this.setTagModalIndex.emit(0);
    }
  }

  tagModal(value: boolean) {
    this._tagModal = value;
  }

  modalInput(value: string) {
    this._modalInput = value;
  }

  modalSelect(value: any) {
    this._modalSelect = value;
  }

  renamingTag(value: boolean) {
    this._renamingTag = value;
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
    this.tagModal(false);
    this.modalInput('');
    this.modalSelect('');
    this.renamingTag(false);
  }

  handlePopUpConfirm() {
    if(this._renamingTag) {
      if(this._modalInput != this._modalInputCache){
        this.onRenameTag.emit({
          ...this.tagForMenu,
          tagName: this._modalInput
        })
      }
    }else {
      if(this.menuInputs.addTagModalIndex === 1 ){
        this.onAssignPltsToTag.emit({
          plts: this.menuInputs.selectedListOfPlts,
          wsId: this.menuInputs.wsId,
          uwYear: this.menuInputs.uwYear,
          tags: this._modalSelect,
          type: 'many'
        })
      }

      if(this.menuInputs.addTagModalIndex === 0) {
        this.onAssignPltsToTag.emit({
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
    this.onSetFilters.emit({
      systemTag: [],
      userTag: []
    });
    this.onSetSelectedUserTags.emit(_.map(this.menuInputs.userTags, t => ({...t, selected: false})));
    this.onSetSelectedSystemTags.emit(_.map(this.menuInputs.systemTags, t => ({...t, selected: false})))
  }

  setFilter(filter: string, tag,section) {
    if(filter === 'userTag'){
      this.onSetFilters.emit(_.findIndex(this.menuInputs.filters[filter], e => e == tag.tagId) < 0 ?
        _.merge({}, this.menuInputs.filters, { [filter]: _.merge([], this.menuInputs.filters[filter], {[this.menuInputs.filters[filter].length] : tag.tagId} ) }) :
        _.assign({}, this.menuInputs.filters, {[filter]: _.filter(this.menuInputs.filters[filter], e => e != tag.tagId)}));

      this.onSetSelectedUserTags.emit(_.map(this.menuInputs.userTags, t => t.tagId == tag.tagId ? {...t,selected: !t.selected} : t))
    }else{
      const {
        systemTag
      } = this.menuInputs.filters;

      this.onSetFilters.emit(_.findIndex(systemTag, sectionFilter => sectionFilter[tag] === section ) < 0 ?
        _.merge({},this.menuInputs.filters,{
          systemTag: _.merge([], systemTag, {
            [systemTag.length]: { [tag] : section }
          })
        }) : _.assign({}, this.menuInputs.filters,{ systemTag: _.filter( systemTag, sectionFilter => sectionFilter[tag] != section)}))
    }
  }

  selectSystemTag(section,tag) {
    this.onSelectSysTagCount.emit({
      section,
      tag
    })
  }
}
