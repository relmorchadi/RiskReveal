import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-pop-up-plt-table-new',
  templateUrl: './pop-up-plt-table-new.component.html',
  styleUrls: ['./pop-up-plt-table-new.component.scss']
})
export class PopUpPltTableNewComponent implements OnInit {
  @Input() addRemovePopUpConfig;
  @Input() data;
  constructor() { }

  ngOnInit() {

  }

}
