import {Component, OnInit} from '@angular/core';
import {SearchService} from '../../../service/search.service';
import {Router} from '@angular/router';
import * as _ from 'lodash';
import * as moment from 'moment';
import {HelperService} from '../../../../shared/helper.service';
import {Select, Store} from '@ngxs/store';
import {HeaderState} from '../../../store/states';
import {DeleteNotification} from "../../../store/actions/header.action";

@Component({
  selector: 'notifications-menu-item',
  templateUrl: './notifications-menu-item.component.html',
  styleUrls: ['./notifications-menu-item.component.scss']
})
export class NotificationsMenuItemComponent implements OnInit {

  readonly componentName: string = 'notifications-pop-in';

  lastOnes = 5;
  visible: boolean;
  notifCount = 5;
  cleared = false;
  date = 'all';
  searchValue = '';
  filteredNotification: any;

  @Select(HeaderState.getNotif) notif$;
  notif: any;

  constructor(private _searchService: SearchService, private router: Router, private store: Store) {
  }

  ngOnInit() {
    this.notif$.subscribe(value => {
      this.notif = _.merge([], value);
      this.filteredNotification = [...this.notif];
    });
    this.getDateIntervals();
    this._searchService.infodropdown.subscribe(dt => this.visible = this._searchService.getvisibleDropdown());
    HelperService.headerBarPopinChange$.subscribe(({from}) => {
      if (from != this.componentName)
        this.visible = false;
    });
  }

  navigateToNotification() {
    this.router.navigateByUrl(`/notificationManager`);
    this.visible = false;
  }

  toggleNotification(notification) {

  }

  changeDate(event) {
    if (event === 'all') {
      this.filteredNotification = [...this.notif];
    } else {
      this.filteredNotification = this.notif.filter(dt => {
        let compareDate: any = new Date();
        if (event === 'today') {
          compareDate = compareDate.setDate(compareDate.getDate() - 1);
        } else if (event === 'yesterday') {
          compareDate = compareDate.setDate(compareDate.getDate() - 2);
        } else if (event === 'lastWeek') {
          compareDate = compareDate.setDate(compareDate.getDate() - 8);
        } else if (event === 'lastMonth') {
          compareDate = compareDate.setDate(compareDate.getDate() - 30);
        }
        return dt.date > compareDate;
      });
    }

  }

  formatString(text) {
    return _.camelCase(text);
  }

  searchNotification(event) {
    this.filteredNotification = this.notif.filter(text => _.includes(_.toLower(text.content), _.toLower(event.target.value)));
  }

  updateNotif() {
    this.notifCount = 0;
    this.cleared ? this.navigateToNotification() : null;
    this.togglePopup();
  }

  clearNotif(target) {
    this.store.dispatch(new DeleteNotification({target}));
  }

  countNotif(type) {
    if (type === 'all') {
      return this.notif.length;
    } else {
      return this.notif.filter(dt => dt.type === type).length;
    }
  }

  getDates() {
    const dateArray = [];
    const listElement = [...this.notif.map(dt => dt.date)];

    const currentDate = new Date(_.min(listElement));
    const lastDate = new Date(_.max(listElement) + 1000000);
    while (currentDate <= lastDate) {
      dateArray.unshift(new Date(currentDate));
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
    } else {
      return moment(date).format('DD MMMM YYYY');
    }
  }

  getDateIntervals() {
    const dateIntervals = ['All'];
    let oldestDate = this.getDates()[this.getDates().length - 1];
    const todayDate = new Date();
    if (oldestDate) {
      oldestDate = todayDate.valueOf() - oldestDate.valueOf();
      if (oldestDate > 0) {
        dateIntervals.push('Today');
        oldestDate = oldestDate - 1000 * 60 * 60 * 24;
      }
      if (oldestDate > 0) {
        dateIntervals.push('Yesterday');
        oldestDate = oldestDate - 1000 * 60 * 60 * 24;
      }
      if (oldestDate > 0) {
        dateIntervals.push('Last Week');
        oldestDate = oldestDate - 1000 * 60 * 60 * 24 * 6;
      }
      if (oldestDate > 0) {
        dateIntervals.push('Last Month');
        oldestDate = oldestDate - 1000 * 60 * 60 * 24 * 25;
      }
    }
    return dateIntervals;
  }

  togglePopup() {
    HelperService.headerBarPopinChange$.next({from: this.componentName});
  }

  /** TO BE REMOVED */
  filterByToDay(date, type = null) {
    if (type === null) {
      return this.filteredNotification.filter(dt => dt.today === date);
    } else {
      return this.filteredNotification.filter(dt => dt.today === date && dt.type === type);
    }
  }

  changeToDate(event) {
    if (event === 'all') {
      this.filteredNotification = [...this.notif];
    } else {
      this.filteredNotification = this.notif.filter(dt => dt.today === event);
    }
  }

}
