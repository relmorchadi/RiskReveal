import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceProjectComponent } from './workspace-project.component';

describe('WorkspaceProjectComponent', () => {
  let component: WorkspaceProjectComponent;
  let fixture: ComponentFixture<WorkspaceProjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceProjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
