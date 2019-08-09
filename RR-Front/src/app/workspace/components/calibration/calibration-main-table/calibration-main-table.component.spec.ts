import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CalibrationMainTableComponent} from './calibration-main-table.component';

describe('CalibrationMainTableComponent', () => {
  let component: CalibrationMainTableComponent;
  let fixture: ComponentFixture<CalibrationMainTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CalibrationMainTableComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalibrationMainTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
