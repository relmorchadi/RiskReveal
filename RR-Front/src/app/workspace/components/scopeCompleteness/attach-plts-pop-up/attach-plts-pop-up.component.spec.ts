import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttachPltsPopUpComponent } from './attach-plts-pop-up.component';

describe('AttachPltsPopUpComponent', () => {
  let component: AttachPltsPopUpComponent;
  let fixture: ComponentFixture<AttachPltsPopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttachPltsPopUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttachPltsPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
