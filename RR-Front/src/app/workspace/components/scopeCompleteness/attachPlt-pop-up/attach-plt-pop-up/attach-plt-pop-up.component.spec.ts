import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AttachPltPopUpComponent} from './attach-plt-pop-up.component';

describe('AttachPltPopUpComponent', () => {
  let component: AttachPltPopUpComponent;
  let fixture: ComponentFixture<AttachPltPopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AttachPltPopUpComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttachPltPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
