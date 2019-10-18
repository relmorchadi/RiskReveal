import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LastAdjustmentMatrixComponent} from './last-adjustment-matrix.component';

describe('LastAdjustmentMatrixComponent', () => {
  let component: LastAdjustmentMatrixComponent;
  let fixture: ComponentFixture<LastAdjustmentMatrixComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LastAdjustmentMatrixComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LastAdjustmentMatrixComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
