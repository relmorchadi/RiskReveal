import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-add-remove-pop-up-new',
  templateUrl: './add-remove-pop-up-new.component.html',
  styleUrls: ['./add-remove-pop-up-new.component.scss']
})
export class AddRemovePopUpNewComponent implements OnInit {
  @Output() actionDispatcher : EventEmitter<any> = new EventEmitter<any>();
  @Input() addRemovePopUpConfig;
  @Input() data;
  isVisible: any = true;
  leftMenuInputs: any;

  constructor() { }

  ngOnInit() {

  }

    onShow() {

    }

  onHide() {
    this.actionDispatcher.emit({
      type: 'Hide Add Remove Pop Up'
    })
  }

  leftMenuActionDispatcher($event) {

  }

  onSave() {

  }
}
