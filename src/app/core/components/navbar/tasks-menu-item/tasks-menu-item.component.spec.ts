import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TasksMenuItemComponent } from './tasks-menu-item.component';

describe('TasksMenuItemComponent', () => {
  let component: TasksMenuItemComponent;
  let fixture: ComponentFixture<TasksMenuItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TasksMenuItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TasksMenuItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
