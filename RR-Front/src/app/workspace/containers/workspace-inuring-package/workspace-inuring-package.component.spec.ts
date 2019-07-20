import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {WorkspaceInuringPackageComponent} from './workspace-inuring-package.component';

describe('WorkspqceInuringPackageComponent', () => {
  let component: WorkspaceInuringPackageComponent;
  let fixture: ComponentFixture<WorkspaceInuringPackageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [WorkspaceInuringPackageComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceInuringPackageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
