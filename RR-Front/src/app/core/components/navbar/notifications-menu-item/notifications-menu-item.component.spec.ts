import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationsMenuItemComponent } from './notifications-menu-item.component';

describe('NotificationsMenuItemComponent', () => {
  let component: NotificationsMenuItemComponent;
  let fixture: ComponentFixture<NotificationsMenuItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NotificationsMenuItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationsMenuItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
