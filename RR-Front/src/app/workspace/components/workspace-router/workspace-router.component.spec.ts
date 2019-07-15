import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceRouterComponent } from './workspace-router.component';

describe('WorkspaceRouterComponent', () => {
  let component: WorkspaceRouterComponent;
  let fixture: ComponentFixture<WorkspaceRouterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceRouterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceRouterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
