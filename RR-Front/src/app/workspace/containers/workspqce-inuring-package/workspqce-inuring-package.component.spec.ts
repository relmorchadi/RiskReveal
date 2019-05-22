import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspqceInuringPackageComponent } from './workspqce-inuring-package.component';

describe('WorkspqceInuringPackageComponent', () => {
  let component: WorkspqceInuringPackageComponent;
  let fixture: ComponentFixture<WorkspqceInuringPackageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspqceInuringPackageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspqceInuringPackageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
