import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'notifications-menu-item',
  templateUrl: './notifications-menu-item.component.html',
  styleUrls: ['./notifications-menu-item.component.scss']
})
export class NotificationsMenuItemComponent implements OnInit {
  lastOnes= 5;
  notification= {
    all: {
      today: [
          {avatar: 'H.P', icon: null, iconColor: '#7ED321', content: 'Project P-6458 has been assigned to you by Huw Parry', backgroundColor: '#06B8FF', textColor: '#FFFFFF'},
          {avatar: null,icon: 'icon-check_24px', iconColor: '#7ED321', content: 'Accumulation Package AP-3857 has been successfully published to ARC', backgroundColor: '#FCF9D6', textColor: 'rgba(0,0,0,0.6)'}
      ],
      yesterday: [
        {avatar: null, icon: 'icon-check_circle_24px', iconColor: '#7ED321', content: 'Calculation on Inuring Package IP-2645 is now complete', backgroundColor: '#F4F6FC', textColor: 'rgba(0,0,0,0.6)'},
        {avatar: null, icon: 'icon-poll_24px', iconColor: '#FFFFFF', content: 'Project P-8687 : <br/>7PLTs have been successfully published to pricing' , backgroundColor: '#0700CF', textColor: '#FFFFFF'}
      ]
    }
  };

  constructor() { }

  ngOnInit() {
  }

  toggleNotificaion(notification){

  }

}
