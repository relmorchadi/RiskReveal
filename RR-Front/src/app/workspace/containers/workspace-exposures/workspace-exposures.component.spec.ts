import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceExposuresComponent } from './workspace-exposures.component';

describe('WorkspaceExposuresComponent', () => {
  let component: WorkspaceExposuresComponent;
  let fixture: ComponentFixture<WorkspaceExposuresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceExposuresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceExposuresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
