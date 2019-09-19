import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AdjustmentPopUpComponent} from './adjustment-pop-up.component';

describe('AdjustmentPopUpComponent', () => {
  let component: AdjustmentPopUpComponent;
  let fixture: ComponentFixture<AdjustmentPopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AdjustmentPopUpComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdjustmentPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
