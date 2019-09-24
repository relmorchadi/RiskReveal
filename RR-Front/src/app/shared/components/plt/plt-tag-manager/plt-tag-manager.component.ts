import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Message} from "../../../message";
import * as tagsStore from './store'
import * as _ from "lodash";

@Component({
  selector: 'plt-tag-manager',
  templateUrl: './plt-tag-manager.component.html',
  styleUrls: ['./plt-tag-manager.component.scss']
})
export class PltTagManagerComponent implements OnInit {

  @Input() tagsInputs: tagsStore.Input;
  @Output() actionDispatcher= new EventEmitter<Message>();

  tagForm: FormGroup;
  presetColors: string[]= ['#0700CF', '#ef5350', '#d81b60', '#6a1b9a', '#880e4f', '#64ffda', '#00c853', '#546e7a'];


  constructor(private _fb: FormBuilder) { }

  ngOnInit() {
    this.initTagForm();
  }

  initTagForm(){
    this.tagForm= this._fb.group({
      tagId: [null],
      tagName: ['', Validators.required],
      tagColor: ['#ae1675'],
      userId: [ 17, Validators.required],
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

  reset() {
    this.initTagForm();
  }

  tagModal(value: boolean) {
    this.actionDispatcher.emit({
      type: tagsStore.setTagModalVisibility,
      payload: value
    });
  }

  toggleColorPicker(i: number){
    event.stopPropagation();
    event.preventDefault();
    this.tagForm.patchValue({ visible: !this.visible.value})
  }

  closeColorPicker() {
    event.stopPropagation();
    event.preventDefault();
    this.tagForm.patchValue({ visible: false});
  }

  handlePopUpCancel() {
    this.tagModal(false);
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

  handlePopUpConfirm() {
    /*if(this.leftMenuInputs._editingTag) {
        this.onEditTag.emit()
    }else {

      if(this.leftMenuInputs.addTagModalIndex === 1 ){
        this.onAssignPltsToTag.emit({
          plts: _.map(this.leftMenuInputs.selectedListOfPlts.length > 0 ? this.leftMenuInputs.selectedListOfPlts : [this.leftMenuInputs.selectedItemForMenu], plt => plt.pltId),
          wsId: this.leftMenuInputs.wsId,
          uwYear: this.leftMenuInputs.uwYear,
          selectedTags: this.leftMenuInputs._modalSelect
        })
      }

      if(this.leftMenuInputs.addTagModalIndex === 0) {
        this.onCreateTag.emit({
          plts: this.leftMenuInputs.fromPlts ? _.map((this.leftMenuInputs.selectedListOfPlts.length > 0 ? this.leftMenuInputs.selectedListOfPlts : [this.leftMenuInputs.selectedItemForMenu]), plt => plt.pltId) : [],
          wsId: this.leftMenuInputs.wsId,
          uwYear: this.leftMenuInputs.uwYear,
          tag: _.omit(this.leftMenuInputs.tagForMenu, 'tagId')
        });
      }

    }

    this.toggleModal();*/
  }

  addNewTag() {
    if(this.tagForm.valid) this.actionDispatcher.emit({
      type: tagsStore.addNewTag,
      payload: _.omit(this.tagForm.value, ['visible', 'editorId', 'tagId'])
    })
  }

  deleteTag() {
    if (this.tagForm.get('tagId')) this.actionDispatcher.emit({
      type: tagsStore.deleteTag,
      payload: _.omit(this.tagForm.value, ['tagName', 'tagColor', 'visible'])
    })
  }

  confirmSelection() {
    this.actionDispatcher.emit({
      type: tagsStore.confirmSelection,
    })
  }

  toggleTag(i: number, operation: string, source: string) {
    this.actionDispatcher.emit({
      type: tagsStore.toggleTag,
      payload: {
        i,
        operation,
        source
      }
    })
  }

  save() {
    this.actionDispatcher.emit({
      type: tagsStore.save
    })
  }

  clearSelection() {
    this.actionDispatcher.emit({
      type: tagsStore.clearSelection,
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

}
