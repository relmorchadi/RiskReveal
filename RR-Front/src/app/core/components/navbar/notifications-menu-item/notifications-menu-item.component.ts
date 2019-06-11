import {Component, OnInit} from '@angular/core';
import {SearchService} from "../../../service/search.service";
import {Router} from '@angular/router';
import * as _ from 'lodash';
import * as moment from 'moment';

@Component({
  selector: 'notifications-menu-item',
  templateUrl: './notifications-menu-item.component.html',
  styleUrls: ['./notifications-menu-item.component.scss']
})
export class NotificationsMenuItemComponent implements OnInit {
  lastOnes = 5;
  visible: boolean;
  notifCount = 5;
  cleared = false;
  notification: any = {
    all: [
      {
        avatar: 'H.P',
        icon: null,
        iconColor: '#7ED321',
        content: 'Project P-6458 has been assigned to you by Huw Parry',
        date: 1560155333216,
        type: 'informational',
        backgroundColor: '#06B8FF',
        textColor: '#FFFFFF'
      },
      {
        avatar: null,
        icon: 'icon-check_24px',
        iconColor: '#7ED321',
        content: 'Accumulation Package AP-3857 has been successfully published to ARC',
        date: 1560069150008,
        type: 'informational',
        backgroundColor: '#FCF9D6',
        textColor: 'rgba(0,0,0,0.6)'
      },
      {
        avatar: null,
        icon: 'icon-check_circle_24px',
        iconColor: '#7ED321',
        content: 'Calculation on Inuring Package IP-2645 is now complete',
        date: 1560069150008,
        type: 'informational',
        backgroundColor: '#F4F6FC',
        textColor: 'rgba(0,0,0,0.6)'
      },
      {
        avatar: null,
        icon: 'icon-poll_24px',
        iconColor: '#FFFFFF',
/*        content: 'Project P-8687 : <br/>7PLTs have been successfully published to pricing',*/
        content: 'Project P-8687 : 7PLTs have been successfully published to pricing',
        date: 1560155333216,
        type: 'informational',
        backgroundColor: '#0700CF',
        textColor: '#FFFFFF'
      },
      {
        avatar: null,
        icon: 'icon-warning_amber_24px',
        iconColor: '#FFFFFF',
        content: 'Project P-003458 Has been paused for 1 week please resume or cancel Job.',
        date: 1559467950008,
        type: 'Error',
        backgroundColor: '#D0021B',
        textColor: '#FFFFFF'
      },
      {
        avatar: null,
        icon: 'icon-graph-bar',
        iconColor: '#DCAA2B',
        content: 'You have many jobs in need of completion.',
        date: 1559467950008,
        type: 'warning',
        backgroundColor: '#D1F4DA',
        textColor: '#477938'
      },
      {
        avatar: null,
        icon: 'icon-check_circle_24px',
        iconColor: '#7ED321',
        content: 'You need to resume Action From yesterday',
        date: 1560069150008,
        type: 'warning',
        backgroundColor: '#F4F6FC',
        textColor: 'rgba(0,0,0,0.6)'
      },
    ],
  };
  filteredNotification: any;

  constructor(private _searchService: SearchService, private router: Router) {
  }

  ngOnInit() {
    this.filteredNotification = [...this.notification.all];
    this.getDateIntervals();
    this._searchService.infodropdown.subscribe(dt => this.visible = this._searchService.getvisibleDropdown());
  }

  navigateToNotification() {
    this.router.navigateByUrl(`/notificationManager`);
    this.visible = false;
  }

  toggleNotification(notification) {

  }

  changeDate(event) {
    if (event === 'all') {
      this.filteredNotification = [...this.notification.all]
    } else {
      this.filteredNotification = this.notification.all.filter(dt => {
        let compareDate: any = new Date();
        if (event === 'today') {
          compareDate = compareDate.setDate(compareDate.getDate());
        } else if (event === 'yesterday') {
          compareDate = compareDate.setDate(compareDate.getDate() - 1);
        } else if (event === 'lastWeek') {
          compareDate = compareDate.setDate(compareDate.getDate() - 6);
        } else if (event === 'lastMonth') {
          compareDate = compareDate.setDate(compareDate.getDate() - 30);
        }
        return dt.date > compareDate;
      })
    }

  }

  formatString(text) {
    return _.camelCase(text);
  }

  searchNotification(event) {
    this.filteredNotification = this.notification.all.filter(text => _.includes( _.toLower(text.content), _.toLower(event.target.value)));
  }

  updateNotif() {
    this.notifCount = 0;
    this.cleared ? this.navigateToNotification() : null;
  }

  clearNotif(target) {
    if (target === 'all') {
      this.notification = {};
      this.cleared = true;
      this.visible = false;
    } else {
      this.notification = {all : [...this.notification.all.filter(dt => dt.type !== target)]};
    }
  }

  countNotif(type) {
    if (type === 'all') {
      return this.notification.all.length ;
    } else { return this.notification.all.filter(dt => dt.type === type).length; }
  }

  getDates() {
    const dateArray = [];
    const listElement = [...this.notification.all.map(dt => dt.date)];

    const currentDate = new Date(_.min(listElement));
    const lastDate = new Date(_.max(listElement) + 1000000);
    while (currentDate <= lastDate) {
      dateArray.unshift(new Date (currentDate));
      currentDate.setDate(currentDate.getDate() + 1);
    }

    return dateArray;
  }

  getItemWithDate(date, type) {
    let notification = [];
    if (type === 'all') {
      notification = this.filteredNotification;
    } else if (type === 'warning') {
      notification = this.filteredNotification.filter(dt => dt.type === 'warning');
    } else if (type === 'Error') {
      notification = this.filteredNotification.filter(dt => dt.type === 'Error');
    } else if (type === 'Informational') {
      notification = this.filteredNotification.filter(dt => dt.type === 'informational');
    }
    return notification.filter(dt => {
      date = moment(date).format('MM/D/YYYY');
      const timeStamp = moment(dt.date).format('MM/D/YYYY');
      // console.log(date, timeStamp)
      return date === timeStamp;
    });
  }

  getDayFormat(date) {
    const dateNow = moment(date).format('MM/D/YYYY');
    const today = moment().format('MM/D/YYYY');
    const yesterday = moment().subtract(1, 'days').format('MM/D/YYYY');
    if (dateNow === today) {
      return 'Today';
    } else if (dateNow === yesterday) {
      return 'Yesterday';
    } else { return moment(date).format('DD MMMM YYYY'); }
  }

  getDateIntervals() {
    const dateIntervals = ['All'];
    let oldestDate = this.getDates()[this.getDates().length - 1];
    const todayDate = new Date();
    oldestDate = todayDate.valueOf() - oldestDate.valueOf();
    if (oldestDate > 0) {
      dateIntervals.push('Today');
      oldestDate = oldestDate - 1000 * 60 * 60 * 24;
    }
    if ( oldestDate > 0) {
      dateIntervals.push('Yesterday');
      oldestDate = oldestDate - 1000 * 60 * 60 * 24;
    }
    if ( oldestDate > 0) {
      dateIntervals.push('Last Week');
      oldestDate = oldestDate - 1000 * 60 * 60 * 24 * 6;
    }
    if (oldestDate > 0) {
      dateIntervals.push('Last Month');
      oldestDate = oldestDate - 1000 * 60 * 60 * 24 * 25;
    }
    return dateIntervals;
  }

}
