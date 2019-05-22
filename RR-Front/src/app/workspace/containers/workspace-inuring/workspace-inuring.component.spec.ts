import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceInuringComponent } from './workspace-inuring.component';

describe('WorkspaceInuringComponent', () => {
  let component: WorkspaceInuringComponent;
  let fixture: ComponentFixture<WorkspaceInuringComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceInuringComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceInuringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
