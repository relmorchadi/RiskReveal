import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceCloneDataComponent } from './workspace-clone-data.component';

describe('WorkspaceCloneDataComponent', () => {
  let component: WorkspaceCloneDataComponent;
  let fixture: ComponentFixture<WorkspaceCloneDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceCloneDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceCloneDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
