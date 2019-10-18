import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceResultsComponent } from './workspace-results.component';

describe('WorkspaceResultsComponent', () => {
  let component: WorkspaceResultsComponent;
  let fixture: ComponentFixture<WorkspaceResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceResultsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
