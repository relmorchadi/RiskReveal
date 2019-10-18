import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceActivityComponent } from './workspace-activity.component';

describe('WorkspaceActivityComponent', () => {
  let component: WorkspaceActivityComponent;
  let fixture: ComponentFixture<WorkspaceActivityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceActivityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
