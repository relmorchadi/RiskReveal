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

  @Input() inputs: leftMenuStore.Input;
  @Input() tagsInput: leftMenuStore.Input;

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

  @Output() actionDispatcher= new EventEmitter<Message>();

  _modalInput: string;
  colorPickerIsVisible: boolean;

  perilColors = {
    'EQ': 'red',
    'FL': '#0b99cc',
    'WS': '#62ec07',
    'CS': '#62ec07'
  }
  presetColors: string[]= ['#0700CF', '#ef5350', '#d81b60', '#6a1b9a', '#880e4f', '#64ffda', '#00c853', '#546e7a'];

  tagForm: FormGroup;
  drawer: boolean;

  constructor(private _fb: FormBuilder) {
    this.drawer= false;
  }

  ngOnInit() {
    this.initTagForm();
  }

  initTagForm(){
    this.tagForm= this._fb.group({
      tagId: [null],
      tagName: ['', Validators.required],
      tagColor: ['#ae1675'],
      userId: ['', Validators.required],
      editorId: [''],
      visible: [false]
    })
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
      payload: false
    })
    this.onWsHeaderSelection.emit(true)
    this.onProjectFilter.emit(_.omit(this.inputs.filterData, ['project']))
    this.onToggleDeletedPlts.emit(false);
  }

  filter(key, filterData, value){
    if (key == 'project') {
      this.unCkeckAllPlts.emit();
      if (this.inputs.filterData['project'] && this.inputs.filterData['project'] != '' && value == this.inputs.filterData['project']) {
        this.onWsHeaderSelection.emit(true);
        this.onProjectFilter.emit(_.omit(this.inputs.filterData, [key]))
      } else {
        this.onWsHeaderSelection.emit(false);
        this.onProjectFilter.emit(_.merge({}, this.inputs.filterData, {[key]: value}))
      }
      this.onSelectProjects.emit(_.map(this.inputs.projects, t => {
        if(t.projectId == value){
          return ({...t,selected: !t.selected})
        }else if(t.selected) {
          return ({...t,selected: false})
        }else return t;
      }))
    }
  }

  toggleDeletedPlts() {
    this.onToggleDeletedPlts.emit(!this.inputs.showDeleted);
  }

  toggleModal(){
    this.tagModal( false);
    if(!this.inputs._tagModalVisible){
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

  toggleColorPicker(i: number){
    event.stopPropagation();
    event.preventDefault();
    this.tagForm.patchValue({ visible: !this.visible.value})
  }

  handlePopUpCancel() {
    this.tagModal(false);
    this.modalInput('');
    this.modalSelect('');
    this.renamingTag(false);
  }

  handlePopUpConfirm() {
    if(this.inputs._editingTag) {
        this.onEditTag.emit()
    }else {

      if(this.inputs.addTagModalIndex === 1 ){
        this.onAssignPltsToTag.emit({
          plts: _.map(this.inputs.selectedListOfPlts.length > 0 ? this.inputs.selectedListOfPlts : [this.inputs.selectedItemForMenu], plt => plt.pltId),
          wsId: this.inputs.wsId,
          uwYear: this.inputs.uwYear,
          selectedTags: this.inputs._modalSelect
        })
      }

      if(this.inputs.addTagModalIndex === 0) {
        this.onCreateTag.emit({
          plts: this.inputs.fromPlts ? _.map((this.inputs.selectedListOfPlts.length > 0 ? this.inputs.selectedListOfPlts : [this.inputs.selectedItemForMenu]), plt => plt.pltId) : [],
          wsId: this.inputs.wsId,
          uwYear: this.inputs.uwYear,
          tag: _.omit(this.inputs.tagForMenu, 'tagId')
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
    this.onSetSelectedUserTags.emit(_.map(this.inputs.userTags, t => ({...t, selected: false})));
  }

  setFilter(filter: string, tag,section) {
    if(filter === 'userTag'){
      const filters = _.findIndex(this.inputs.filters[filter], e => e == tag.tagId) < 0 ?
        _.merge({}, this.inputs.filters, { [filter]: _.merge([], this.inputs.filters[filter], {[this.inputs.filters[filter].length] : tag.tagId} ) }) :
        _.assign({}, this.inputs.filters, {[filter]: _.filter(this.inputs.filters[filter], e => e != tag.tagId)});

      this.onSetFilters.emit(filters);

      this.onSetSelectedUserTags.emit(_.map(this.inputs.userTags, t => t.tagId == tag.tagId ? {...t,selected: !t.selected} : t))

      this.emitFilters.emit(filters);
    }else{
      const {
        systemTag
      } = this.inputs.filters;

      this.onSetFilters.emit(
        !systemTag[section] ?
          _.merge({}, this.inputs.filters, {
            systemTag: _.merge({},systemTag, { [section]: [tag]})
          })
          :
          _.findIndex(systemTag[section], sysTagValue =>  sysTagValue == tag) < 0 ?
            _.merge({}, this.inputs.filters, {
              systemTag: _.merge({},systemTag, { [section]: this.toggleArrayItem(tag, systemTag[section], (a,b) => a == b) })
            })
            :
            (
              systemTag[section].length == 1 ?
                _.assign({}, this.inputs.filters, {
                  systemTag: _.omit(systemTag, `${section}`)
                })
                :
                _.assign({}, this.inputs.filters, {
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
      ...this.inputs.tagForMenu,
      [key]: value
    })
  }

  /*onEnter(i: number, event: KeyboardEvent) {
    if (event.key === "Enter" && (this.tagArray.controls[i] as FormGroup).controls.title.valid) {
      this.usedInWs.push({
        tagName: (this.tagArray.controls[i] as FormGroup).controls.title.value,
        tagColor: (this.tagArray.controls[i] as FormGroup).controls.color.value,
        count: 0,
        selected: false
      });
      this.tagArray.removeAt(i);
    }
  }*/
  closeDrawer() {
    this.drawer= false;
  }

  openDrawer() {
    this.drawer= true;
  }

  addNewTag() {
    if(this.tagForm.valid) this.actionDispatcher.emit({
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
}
