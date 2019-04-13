import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspacesMenuItemComponent } from './workspaces-menu-item.component';

describe('WorkspacesMenuItemComponent', () => {
  let component: WorkspacesMenuItemComponent;
  let fixture: ComponentFixture<WorkspacesMenuItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspacesMenuItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspacesMenuItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
