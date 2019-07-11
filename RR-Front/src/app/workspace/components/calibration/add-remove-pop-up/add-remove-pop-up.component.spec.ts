import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddRemovePopUpComponent} from './add-remove-pop-up.component';

describe('AddRemovePopUpComponent', () => {
  let component: AddRemovePopUpComponent;
  let fixture: ComponentFixture<AddRemovePopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddRemovePopUpComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddRemovePopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
