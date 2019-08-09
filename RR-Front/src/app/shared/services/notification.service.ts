import {Injectable} from '@angular/core';
import {NzNotificationDataFilled, NzNotificationService} from 'ng-zorro-antd';

@Injectable({providedIn: 'root'})
export class NotificationService {
  constructor(private notification: NzNotificationService) {
  }

  createNotification(title: string, message: string, type: string, placement: string, duration: number): NzNotificationDataFilled {
    this.notification.remove();
    this.notification.config({
      nzPlacement: placement
    });
    return this.notification.create(
      type, title,
      message,
      {nzDuration: duration}
    );
  }

}
