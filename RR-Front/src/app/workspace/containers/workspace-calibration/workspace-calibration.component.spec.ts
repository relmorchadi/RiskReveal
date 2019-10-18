import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceCalibrationComponent } from './workspace-calibration.component';

describe('WorkspaceCalibrationComponent', () => {
  let component: WorkspaceCalibrationComponent;
  let fixture: ComponentFixture<WorkspaceCalibrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkspaceCalibrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkspaceCalibrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
